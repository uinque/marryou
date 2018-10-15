package com.marryou.controller.manufacturer;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.common.collect.Lists;
import com.marryou.commons.utils.base.BUtils;
import com.marryou.commons.utils.collections.Collections3;
import com.marryou.commons.utils.json.GsonUtils;
import com.marryou.metadata.dto.MftDto;
import com.marryou.metadata.entity.ManufacturerEntity;
import com.marryou.metadata.entity.OperateLogEntity;
import com.marryou.metadata.enums.LogTypeEnum;
import com.marryou.metadata.enums.OperateTypeEnum;
import com.marryou.metadata.service.ManufacturerService;
import com.marryou.utils.Constants;
import com.marryou.utils.JwtUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Preconditions;
import com.marryou.conf.WebSecurityConfig;
import com.marryou.dto.response.BaseResponse;
import com.marryou.metadata.entity.CompanyEntity;
import com.marryou.metadata.entity.UserEntity;
import com.marryou.metadata.enums.RoleEnum;
import com.marryou.metadata.enums.StatusEnum;
import com.marryou.metadata.service.UserService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * Created by linhy on 2018/6/6.
 */
@Controller
@RequestMapping("/api/mft")
public class ManufacturerController {

	private static final Logger logger = LoggerFactory.getLogger(ManufacturerController.class);

	@Autowired
	private UserService userService;
	@Autowired
	private ManufacturerService manufacturerService;

	@ApiOperation(value = "创建生厂商信息", notes = "提交生厂商基础信息")
	@ApiImplicitParam(name = "mft", value = "生产商基础信息", required = true, dataType = "Object")
	@PostMapping("/create")
	public @ResponseBody BaseResponse create(@RequestBody MftDto mft, HttpServletRequest request) {
		try {
			String token = request.getHeader(Constants.TOKEN_FIELD);
			String loginName = JwtUtils.parseJWT(token).getSubject();
			UserEntity operator = userService.getUserByLoginName(loginName);
			Preconditions.checkState(operator.getRole().equals(RoleEnum.SUPER_ADMIN), "非超级管理员，无权限操作");
			Preconditions.checkNotNull(mft, "manufacturer为null");
			Preconditions.checkState(StringUtils.isNotBlank(mft.getName()), "生厂商名称为null");
			ManufacturerEntity m = new ManufacturerEntity();
			BUtils.copyPropertiesIgnoreNull(mft, m);
			m.setStatus(StatusEnum.getEnum(mft.getStatus()));
			m.setCreateBy(loginName);
			m.setCreateTime(new Date());
			manufacturerService.saveMft(m, GsonUtils.buildGson().toJson(m), OperateTypeEnum.CREATE, loginName);
			return new BaseResponse(BaseResponse.CODE_SUCCESS, "创建生产商");
		} catch (Exception e) {
			logger.info("创建生厂商失败:{}", e.getMessage(), e);
			return new BaseResponse(BaseResponse.CODE_FAILED, e.getMessage());
		}
	}

	@ApiOperation(value = "生厂商数据", notes = "获取生厂商数据")
	@ApiImplicitParam(name = "id", value = "生厂商ID", required = true, dataType = "Long")
	@PostMapping("/info/{id}")
	public @ResponseBody BaseResponse<MftDto> getMft(@PathVariable("id") Long id, HttpServletRequest request) {
		try {
			Preconditions.checkNotNull(id, "id参数异常");
			ManufacturerEntity mft = manufacturerService.findOne(id);
			Preconditions.checkNotNull(mft, "查无对应生厂商数据");
			MftDto dto = new MftDto();
			BUtils.copyPropertiesIgnoreNull(mft, dto);
			dto.setStatus(mft.getStatus().getValue());
			return new BaseResponse(BaseResponse.CODE_SUCCESS, "success", dto);
		} catch (Exception e) {
			logger.info("获取生厂商失败:{}", e.getMessage(), e);
			return new BaseResponse(BaseResponse.CODE_FAILED, "获取生厂商失败");
		}
	}

	@ApiOperation(value = "生厂商列表", notes = "获取生厂商列表数据")
	@ApiImplicitParam(name = "search", value = "查询生厂商信息", required = false, dataType = "Object")
	@PostMapping("/list")
	public @ResponseBody BaseResponse<List<MftDto>> list() {
		try {
			List<ManufacturerEntity> mfts = (List<ManufacturerEntity>) manufacturerService.findAll();
			List<MftDto> list = Lists.newArrayList();
			if (Collections3.isNotEmpty(mfts)) {
				list = mfts.stream().map(m -> {
					MftDto mft = new MftDto();
					BUtils.copyPropertiesIgnoreNull(m, mft);
					mft.setStatus(m.getStatus().getValue());
					return mft;
				}).collect(Collectors.toList());
			}
			return new BaseResponse(BaseResponse.CODE_SUCCESS, "success", list);
		} catch (Exception e) {
			logger.info("获取公司列表失败:{}", e.getMessage(), e);
			return new BaseResponse(BaseResponse.CODE_FAILED, "获取公司列表失败");
		}
	}

	@ApiOperation(value = "更新生厂商信息", notes = "更新生厂商基础信息")
	@ApiImplicitParam(name = "mft", value = "公司信息", required = true, dataType = "Object")
	@PostMapping("/update")
	public @ResponseBody BaseResponse update(@RequestBody MftDto mft, HttpServletRequest request) {
		try {
			String token = request.getHeader(Constants.TOKEN_FIELD);
			String loginName = JwtUtils.parseJWT(token).getSubject();
			UserEntity operator = userService.getUserByLoginName(loginName);
			Preconditions.checkState(operator.getRole().equals(RoleEnum.SUPER_ADMIN), "非超级管理员，无权限操作");
			Preconditions.checkNotNull(mft, "mft为null");
			Preconditions.checkNotNull(mft.getId(), "生厂商ID为null");
			ManufacturerEntity m = manufacturerService.findOne(mft.getId());
			Preconditions.checkNotNull(m, "查生厂商数据");
			BUtils.copyPropertiesIgnoreNull(mft, m);
			m.setStatus(StatusEnum.getEnum(mft.getStatus()));
			m.setModifyBy(loginName);
			m.setModifyTime(new Date());
			manufacturerService.saveMft(m, GsonUtils.buildGson().toJson(m), OperateTypeEnum.UPDATE, loginName);
			return new BaseResponse(BaseResponse.CODE_SUCCESS, "更新成功");
		} catch (Exception e) {
			logger.info("更新生厂商数据失败:{}", e.getMessage(), e);
			return new BaseResponse(BaseResponse.CODE_FAILED, e.getMessage());
		}
	}

	@ApiOperation(value = "删除生产商", notes = "删除生厂商")
	@PostMapping("/delete/{id}")
	public @ResponseBody BaseResponse delete(@PathVariable("id") Long id, HttpServletRequest request) {
		try {
			String token = request.getHeader(Constants.TOKEN_FIELD);
			String loginName = JwtUtils.parseJWT(token).getSubject();
			UserEntity operator = userService.getUserByLoginName(loginName);
			Preconditions.checkNotNull(operator, "操作用户异常");
			Preconditions.checkState(operator.getRole().equals(RoleEnum.SUPER_ADMIN), "非超级管理员，无权限操作");
			Preconditions.checkNotNull(id, "id参数异常");
			ManufacturerEntity mft = manufacturerService.findOne(id);
			Preconditions.checkNotNull(mft,"查无对应生产商信息");
			manufacturerService.deleteMft(mft, "删除生厂商:" + mft.getName(), OperateTypeEnum.DELETE, loginName);
			return new BaseResponse(BaseResponse.CODE_SUCCESS, "success");
		} catch (Exception e) {
			logger.info("删除生厂商信息失败:{}", e.getMessage(), e);
			return new BaseResponse(BaseResponse.CODE_FAILED, e.getMessage());
		}
	}

}

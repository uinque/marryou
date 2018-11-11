package com.marryou.controller.entrepot;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.marryou.commons.utils.base.BUtils;
import com.marryou.commons.utils.collections.Collections3;
import com.marryou.commons.utils.json.GsonUtils;
import com.marryou.dto.request.BasePageRequest;
import com.marryou.dto.request.VaildateRequest;
import com.marryou.dto.response.BaseResponse;
import com.marryou.dto.response.PageResponse;
import com.marryou.metadata.dto.EntrepotDto;
import com.marryou.metadata.entity.DeliveryOrderEntity;
import com.marryou.metadata.entity.EntrepotEntity;
import com.marryou.metadata.entity.UserEntity;
import com.marryou.metadata.enums.OperateTypeEnum;
import com.marryou.metadata.enums.RoleEnum;
import com.marryou.metadata.enums.StatusEnum;
import com.marryou.metadata.persistence.SearchFilters;
import com.marryou.metadata.persistence.Searcher;
import com.marryou.metadata.service.DeliveryService;
import com.marryou.metadata.service.EntrepotService;
import com.marryou.metadata.service.UserService;
import com.marryou.utils.Constants;
import com.marryou.utils.JwtUtils;
import com.marryou.utils.RoleUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by xph on 2018/10/23.
 */
@Controller
@RequestMapping("/api/entrepot")
public class EntrepotController {

	private static final Logger logger = LoggerFactory.getLogger(EntrepotController.class);

	@Autowired
	private UserService userService;
	@Autowired
	private EntrepotService entrepotService;
	@Autowired
	private DeliveryService deliveryService;

	@ApiOperation(value = "仓库名验证", notes = "新增仓库名验证")
	@ApiImplicitParam(name = "vaildate", value = "验证参数", required = true, dataType = "Object")
	@PostMapping("/vaildate")
	public @ResponseBody BaseResponse vaild(@RequestBody VaildateRequest vaildate, HttpServletRequest request) {
		try {
			Preconditions.checkNotNull(vaildate,"验证参数异常");
			Preconditions.checkState(StringUtils.isNotBlank(vaildate.getEntrepotName()),"仓库名参数为null");
			EntrepotEntity entrepot = entrepotService.findEntrepotByEntrepotName(vaildate.getEntrepotName());
			if(null != entrepot){
				return new BaseResponse(BaseResponse.CODE_FAILED, "该仓库名称名已被使用");
			}else{
				return new BaseResponse(BaseResponse.CODE_SUCCESS, "该仓库名称尚未被使用");
			}
		}catch (Exception e){
			logger.info("仓库名验证异常:{}", e.getMessage(), e);
			return new BaseResponse(BaseResponse.CODE_FAILED, "验证仓库名称异常:" + e.getMessage());
		}
	}

	@ApiOperation(value = "创建仓库信息", notes = "提交仓库基础信息")
	@ApiImplicitParam(name = "entrepot", value = "仓库基础信息", required = true, dataType = "Object")
	@PostMapping("/create")
	public @ResponseBody BaseResponse create(@RequestBody EntrepotDto entrepotDto, HttpServletRequest request) {
		try {
			String token = request.getHeader(Constants.TOKEN_FIELD);
			String loginName = JwtUtils.parseJWT(token).getSubject();
			UserEntity operator = userService.getUserByLoginName(loginName);
			Preconditions.checkState(operator.getRole().equals(RoleEnum.SUPER_ADMIN), "非超级管理员，无权限操作");
			Preconditions.checkNotNull(entrepotDto, "entrepotDto为null");
			Preconditions.checkState(StringUtils.isNotBlank(entrepotDto.getName()), "仓库名称为null");
			EntrepotEntity entrepot = new EntrepotEntity();
			BUtils.copyPropertiesIgnoreNull(entrepotDto, entrepot);
			entrepot.setTenantCode(operator.getTenantCode());
			entrepot.setStatus(StatusEnum.getEnum(entrepotDto.getStatus()));
			entrepot.setCreateBy(loginName);
			entrepot.setCreateTime(new Date());
			entrepotService.saveEntrepot(entrepot, GsonUtils.buildGson().toJson(entrepot), OperateTypeEnum.CREATE, loginName);
			return new BaseResponse(BaseResponse.CODE_SUCCESS, "创建仓库信息成功");
		} catch (Exception e) {
			logger.info("创建仓库信息失败:{}", e.getMessage(), e);
			return new BaseResponse(BaseResponse.CODE_FAILED, e.getMessage());
		}
	}

	@ApiOperation(value = "仓库数据", notes = "获取仓库数据")
	@ApiImplicitParam(name = "id", value = "仓库ID", required = true, dataType = "Long")
	@PostMapping("/info/{id}")
	public @ResponseBody BaseResponse<EntrepotDto> getEntrepot(@PathVariable("id") Long id, HttpSession session) {
		try {
			Preconditions.checkNotNull(id, "id参数异常");
			EntrepotEntity entrepot = entrepotService.findOne(id);
			Preconditions.checkNotNull(entrepot, "查无对应仓库数据");
			EntrepotDto entrepotDto = new EntrepotDto();
			BUtils.copyPropertiesIgnoreNull(entrepot, entrepotDto);
			entrepotDto.setStatus(entrepot.getStatus().getValue());
			return new BaseResponse(BaseResponse.CODE_SUCCESS, "success", entrepotDto);
		} catch (Exception e) {
			logger.info("获取仓库失败:{}", e.getMessage(), e);
			return new BaseResponse(BaseResponse.CODE_FAILED, "获取仓库失败");
		}
	}

	@ApiOperation(value = "有效仓库列表", notes = "获取有效仓库列表数据")
	@ApiImplicitParam(name = "search", value = "查询仓库信息", required = false, dataType = "Object")
	@PostMapping("/listAll")
	public @ResponseBody BaseResponse<List<EntrepotDto>> listAll(HttpServletRequest request) {
		try {
			String token = request.getHeader(Constants.TOKEN_FIELD);
			String loginName = JwtUtils.parseJWT(token).getSubject();
			UserEntity operator = userService.getUserByLoginName(loginName);
			List<EntrepotDto> list = Lists.newArrayList();
			SearchFilters searchFilters = new SearchFilters();
			searchFilters.add(Searcher.eq("status", StatusEnum.EFFECTIVE.getValue()));
			if(!RoleUtils.isPlatformAdmin(operator.getTenantCode())){
				searchFilters.add(Searcher.eq("tenantCode", operator.getTenantCode()));
			}
			List<EntrepotEntity> entrepotEntities = entrepotService.findAll(searchFilters);
			if(Collections3.isNotEmpty(entrepotEntities)){
				list = entrepotEntities.stream().map(c -> {
					EntrepotDto entrepotDto = new EntrepotDto();
					BUtils.copyPropertiesIgnoreNull(c, entrepotDto);
					entrepotDto.setStatus(c.getStatus().getValue());
					return entrepotDto;
				}).collect(Collectors.toList());
			}
			return new BaseResponse(BaseResponse.CODE_SUCCESS, "success", list);
		} catch (Exception e) {
			logger.info("获取有效仓库列表失败:{}", e.getMessage(), e);
			return new BaseResponse(BaseResponse.CODE_FAILED, "获取有效仓库列表失败");
		}
	}

	@ApiOperation(value = "仓库列表", notes = "获取仓库列表数据")
	@ApiImplicitParam(name = "search", value = "查询仓库信息", required = false, dataType = "Object")
	@PostMapping("/list")
	public @ResponseBody BaseResponse<PageResponse<EntrepotDto>> list(@RequestBody BasePageRequest<EntrepotDto> search,HttpServletRequest request) {
		try {
			String token = request.getHeader(Constants.TOKEN_FIELD);
			String loginName = JwtUtils.parseJWT(token).getSubject();
			UserEntity operator = userService.getUserByLoginName(loginName);
			Preconditions.checkNotNull(search, "查询参数异常");
			if(!RoleUtils.isPlatformAdmin(operator.getTenantCode())){
				if(null==search.getParams()){
					search.setParams(new EntrepotDto());
				}
				search.getParams().setTenantCode(operator.getTenantCode());
			}
			Page<EntrepotEntity> page = entrepotService.findEntrepots(search.toPageRequest(), search.getParams());
			List<EntrepotDto> list = Lists.newArrayList();
			if (Collections3.isNotEmpty(page.getContent())) {
				list = page.getContent().stream().map(c -> {
					EntrepotDto entrepotDto = new EntrepotDto();
					BUtils.copyPropertiesIgnoreNull(c, entrepotDto);
					entrepotDto.setStatus(c.getStatus().getValue());
					return entrepotDto;
				}).collect(Collectors.toList());
			}
			return new BaseResponse(BaseResponse.CODE_SUCCESS, "success", new PageResponse(page.getTotalElements(),
					page.getTotalPages(), page.getNumber(), page.getSize(), list));
		} catch (Exception e) {
			logger.info("获取公司列表失败:{}", e.getMessage(), e);
			return new BaseResponse(BaseResponse.CODE_FAILED, "获取公司列表失败");
		}
	}

	@ApiOperation(value = "更新仓库信息", notes = "更新仓库基础信息")
	@ApiImplicitParam(name = "entpot", value = "仓库信息", required = true, dataType = "Object")
	@PostMapping("/update")
	public @ResponseBody BaseResponse update(@RequestBody EntrepotDto entrepotDto, HttpServletRequest request) {
		try {
			String token = request.getHeader(Constants.TOKEN_FIELD);
			String loginName = JwtUtils.parseJWT(token).getSubject();
			UserEntity operator = userService.getUserByLoginName(loginName);
			Preconditions.checkState(operator.getRole().equals(RoleEnum.SUPER_ADMIN), "非超级管理员，无权限操作");
			Preconditions.checkNotNull(entrepotDto, "entrepotDto为null");
			Preconditions.checkNotNull(entrepotDto.getId(), "entrepotId为null");
			EntrepotEntity entrepot = entrepotService.findOne(entrepotDto.getId());
			Preconditions.checkNotNull(entrepot, "查无仓库数据");
			BUtils.copyPropertiesIgnoreNull(entrepotDto, entrepot);
			entrepot.setStatus(StatusEnum.getEnum(entrepotDto.getStatus()));
			entrepot.setModifyBy(loginName);
			entrepot.setModifyTime(new Date());
			entrepotService.saveEntrepot(entrepot, GsonUtils.buildGson().toJson(entrepot), OperateTypeEnum.UPDATE, loginName);
			return new BaseResponse(BaseResponse.CODE_SUCCESS, "更新成功");
		} catch (Exception e) {
			logger.info("更新仓库数据失败:{}", e.getMessage(), e);
			return new BaseResponse(BaseResponse.CODE_FAILED, e.getMessage());
		}
	}

	@ApiOperation(value = "删除仓库", notes = "删除仓库")
	@PostMapping("/delete/{id}")
	public @ResponseBody BaseResponse delete(@PathVariable("id") Long id, HttpServletRequest request) {
		try {
			String token = request.getHeader(Constants.TOKEN_FIELD);
			String loginName = JwtUtils.parseJWT(token).getSubject();
			UserEntity operator = userService.getUserByLoginName(loginName);
			Preconditions.checkNotNull(operator, "操作用户异常");
			Preconditions.checkState(operator.getRole().equals(RoleEnum.SUPER_ADMIN), "非超级管理员，无权限操作");
			Preconditions.checkNotNull(id, "id参数异常");
			SearchFilters searchFilters = new SearchFilters();
			searchFilters.add(Searcher.eq("entrepotId", id));
			List<DeliveryOrderEntity> deliveryOrderList = deliveryService.findAll(searchFilters);
			Preconditions.checkState(Collections3.isEmpty(deliveryOrderList), "此仓库正在使用中，无法执行删除");
			EntrepotEntity entrepot = entrepotService.findOne(id);
			Preconditions.checkNotNull(entrepot, "查无对应的仓库信息");
			entrepotService.deleteEntrepot(entrepot, "删除仓库数据", OperateTypeEnum.DELETE, loginName);
			return new BaseResponse(BaseResponse.CODE_SUCCESS, "success");
		} catch (Exception e) {
			logger.info("删除仓库信息失败:{}", e.getMessage(), e);
			return new BaseResponse(BaseResponse.CODE_FAILED, e.getMessage());
		}
	}

}

package com.marryou.controller.standard;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.reflect.TypeToken;
import com.marryou.commons.utils.json.GsonUtils;
import com.marryou.metadata.dto.SpDto;
import com.marryou.metadata.dto.StandardParamsDto;
import com.marryou.metadata.dto.StandardTableDto;
import com.marryou.metadata.entity.StandardParamsEntity;
import com.marryou.metadata.service.StandardParamsService;
import com.marryou.metadata.service.StandardTitleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Preconditions;
import com.marryou.dto.response.BaseResponse;
import com.marryou.metadata.entity.StandardTitleEntity;
import com.marryou.metadata.entity.UserEntity;
import com.marryou.metadata.enums.RoleEnum;
import com.marryou.metadata.service.ProductService;
import com.marryou.metadata.service.UserService;
import com.marryou.utils.Constants;
import com.marryou.utils.JwtUtils;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * Created by linhy on 2018/6/6.
 */
@Controller
@RequestMapping("/api/standardParams")
public class StandardParamsController {

	private static final Logger logger = LoggerFactory.getLogger(StandardParamsController.class);

	@Autowired
	private UserService userService;
	@Autowired
	private ProductService productService;
	@Autowired
	private StandardParamsService standardParamsService;
	@Autowired
	private StandardTitleService standardTitleService;


	@ApiOperation(value = "创建产品指标", notes = "提交产品指标参数创建信息")
	@ApiImplicitParam(name = "standardParams", value = "指标标题参数信息", required = true, dataType = "Object")
	@PostMapping("/create")
	public @ResponseBody BaseResponse create(@RequestBody SpDto spd, HttpServletRequest request) {
		try {
			Type type = new TypeToken<List<StandardParamsDto>>() {}.getType();
			List<StandardParamsDto> standardParamsList = GsonUtils.buildGson().fromJson(spd.getStr(),type);
			String token = request.getHeader(Constants.TOKEN_FIELD);
			String loginName = JwtUtils.parseJWT(token).getSubject();
			UserEntity operator = userService.getUserByLoginName(loginName);
			Preconditions.checkState(operator.getRole().equals(RoleEnum.SUPER_ADMIN), "非超级管理员，无权限操作");
			List<StandardParamsEntity> list = standardParamsList.stream().map(s->{
				Preconditions.checkNotNull(s.getRowId(),"rowId为null");
				Preconditions.checkNotNull(s.getColumnId(),"columnId为null");
				Preconditions.checkNotNull(s.getType(),"type为null");
				StandardParamsEntity sp = new StandardParamsEntity();
				BeanUtils.copyProperties(s,sp,"id");
				sp.setCreateBy(loginName);
				sp.setCreateTime(new Date());
				sp.setTenantCode(operator.getTenantCode());
				return sp;
			}).collect(Collectors.toList());
			standardParamsService.saveStandardParamsList(list);
			return new BaseResponse(BaseResponse.CODE_SUCCESS, "创建模板指标成功");
		} catch (Exception e) {
			logger.info("创建模板指标失败:{}", e.getMessage(), e);
			return new BaseResponse(BaseResponse.CODE_FAILED, e.getMessage());
		}
	}

	@ApiOperation(value = "更新产品指标", notes = "更新产品指标参数建信息")
	@ApiImplicitParam(name = "standardParams", value = "指标标题参数信息", required = true, dataType = "Object")
	@PostMapping("/update")
	public @ResponseBody BaseResponse update(@RequestBody SpDto spd, HttpServletRequest request) {
		try {
			Type type = new TypeToken<List<StandardParamsDto>>() {}.getType();
			List<StandardParamsDto> standardParamsList = GsonUtils.buildGson().fromJson(spd.getStr(),type);
			String token = request.getHeader(Constants.TOKEN_FIELD);
			String loginName = JwtUtils.parseJWT(token).getSubject();
			UserEntity operator = userService.getUserByLoginName(loginName);
			Preconditions.checkState(operator.getRole().equals(RoleEnum.SUPER_ADMIN), "非超级管理员，无权限操作");
			List<StandardParamsEntity> list = standardParamsList.stream().map(s->{
				Preconditions.checkNotNull(s.getId(),"id为null");
				StandardParamsEntity sp = standardParamsService.findOne(s.getId());
				Preconditions.checkNotNull(sp,"查无对应指标数据");
				BeanUtils.copyProperties(s,sp);
				sp.setModifyBy(loginName);
				sp.setModifyTime(new Date());
				return sp;
			}).collect(Collectors.toList());
			standardParamsService.updateStandardParamsList(list);
			return new BaseResponse(BaseResponse.CODE_SUCCESS, "更新模板指标成功");
		} catch (Exception e) {
			logger.info("更新模板指标失败:{}", e.getMessage(), e);
			return new BaseResponse(BaseResponse.CODE_FAILED, e.getMessage());
		}
	}


	@ApiOperation(value = "产品模板指标列表", notes = "产品模板指标列表")
	@PostMapping("/list/{productId}")
	public @ResponseBody BaseResponse<List<StandardTitleEntity>> list(@PathVariable("productId") Long productId,
																	  HttpServletRequest request) {
		try {
			String token = request.getHeader(Constants.TOKEN_FIELD);
			String loginName = JwtUtils.parseJWT(token).getSubject();
			UserEntity operator = userService.getUserByLoginName(loginName);
			Preconditions.checkNotNull(operator, "操作用户异常");
			Preconditions.checkNotNull(productId, "productId参数异常");
			List<StandardTableDto> list = standardParamsService.buildStandardParams(productId);
			return new BaseResponse(BaseResponse.CODE_SUCCESS, "success", list);
		} catch (Exception e) {
			logger.info("获取模板指标标题列表失败:{}", e.getMessage(), e);
			return new BaseResponse(BaseResponse.CODE_FAILED, e.getMessage());
		}
	}

	public static void main(String[] args) {
		//String str = "{\"0\":{\"rowId\":213,\"columnId\":211,\"type\":0,\"productId\":47,\"pointNum\":1,\"val\":\"1\",\"rowTitle\":\"1\",\"id\":\"\"},\"1\":{\"rowId\":213,\"columnId\":212,\"type\":0,\"productId\":47,\"pointNum\":1,\"val\":\"11\",\"rowTitle\":\"1\",\"id\":\"\"}}";
		String str = "[{\"rowId\":213,\"columnId\":211,\"type\":0,\"productId\":47,\"pointNum\":1,\"val\":\"1\",\"rowTitle\":\"1\",\"id\":1},{\"rowId\":213,\"columnId\":212,\"type\":0,\"productId\":47,\"pointNum\":1,\"val\":\"11\",\"rowTitle\":\"1\",\"id\":2}]";
		Type type = new TypeToken<List<StandardParamsDto>>() {}.getType();
		List<StandardParamsDto> standardParamsList = GsonUtils.buildGson().fromJson(str,type);
		System.out.println("111111");
	}

}

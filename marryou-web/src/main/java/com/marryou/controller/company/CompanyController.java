package com.marryou.controller.company;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.marryou.commons.utils.base.BUtils;
import com.marryou.commons.utils.collections.Collections3;
import com.marryou.commons.utils.json.GsonUtils;
import com.marryou.dto.request.BasePageRequest;
import com.marryou.dto.request.VaildateRequest;
import com.marryou.dto.response.BaseResponse;
import com.marryou.dto.response.PageResponse;
import com.marryou.metadata.dto.CompanyDto;
import com.marryou.metadata.entity.CompanyEntity;
import com.marryou.metadata.entity.UserEntity;
import com.marryou.metadata.enums.OperateTypeEnum;
import com.marryou.metadata.enums.RoleEnum;
import com.marryou.metadata.enums.StatusEnum;
import com.marryou.metadata.persistence.SearchFilters;
import com.marryou.metadata.persistence.Searcher;
import com.marryou.metadata.service.CompanyService;
import com.marryou.metadata.service.UserService;
import com.marryou.utils.Constants;
import com.marryou.utils.JwtUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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
 * Created by linhy on 2018/6/6.
 */
@Controller
@RequestMapping("/api/company")
public class CompanyController {

	private static final Logger logger = LoggerFactory.getLogger(CompanyController.class);

	@Autowired
	private UserService userService;
	@Autowired
	private CompanyService companyService;

	@ApiOperation(value = "公司名验证", notes = "新增公司名验证")
	@ApiImplicitParam(name = "vaildate", value = "验证参数", required = true, dataType = "Object")
	@PostMapping("/vaildate")
	public @ResponseBody BaseResponse vaild(@RequestBody VaildateRequest vaildate, HttpServletRequest request) {
		try {
			Preconditions.checkNotNull(vaildate,"验证参数异常");
			Preconditions.checkState(StringUtils.isNotBlank(vaildate.getCompanyName()),"公司名参数为null");
			String token = request.getHeader(Constants.TOKEN_FIELD);
			String loginName = JwtUtils.parseJWT(token).getSubject();
			UserEntity operator = userService.getUserByLoginName(loginName);
			CompanyEntity company = companyService.findCompanyByCompanyName(vaildate.getCompanyName(),operator.getTenantCode());
			if(null!=company){
				return new BaseResponse(BaseResponse.CODE_FAILED, "该公司名称名已被使用");
			}else{
				return new BaseResponse(BaseResponse.CODE_SUCCESS, "该公司名称尚未被使用");
			}
		}catch (Exception e){
			logger.info("公司名验证异常:{}", e.getMessage(), e);
			return new BaseResponse(BaseResponse.CODE_FAILED, "新增公司名称异常:" + e.getMessage());
		}
	}

	@ApiOperation(value = "创建公司信息", notes = "提交公司基础信息")
	@ApiImplicitParam(name = "company", value = "公司基础信息", required = true, dataType = "Object")
	@PostMapping("/create")
	public @ResponseBody BaseResponse create(@RequestBody CompanyDto company, HttpServletRequest request) {
		try {
			String token = request.getHeader(Constants.TOKEN_FIELD);
			String loginName = JwtUtils.parseJWT(token).getSubject();
			UserEntity operator = userService.getUserByLoginName(loginName);
			Preconditions.checkState(operator.getRole().equals(RoleEnum.SUPER_ADMIN), "非超级管理员，无权限操作");
			Preconditions.checkNotNull(company, "company为null");
			Preconditions.checkState(StringUtils.isNotBlank(company.getName()), "公司名称为null");
			CompanyEntity cp = new CompanyEntity();
			BUtils.copyPropertiesIgnoreNull(company, cp);
			cp.setStatus(StatusEnum.getEnum(company.getStatus()));
			cp.setTenantCode(operator.getTenantCode());
			cp.setCreateBy(loginName);
			cp.setCreateTime(new Date());
			companyService.saveCompany(cp, GsonUtils.buildGson().toJson(cp), OperateTypeEnum.CREATE, loginName);
			return new BaseResponse(BaseResponse.CODE_SUCCESS, "创建公司信息成功");
		} catch (Exception e) {
			logger.info("创建公司信息失败:{}", e.getMessage(), e);
			return new BaseResponse(BaseResponse.CODE_FAILED, e.getMessage());
		}
	}

	@ApiOperation(value = "公司数据", notes = "获取公司数据")
	@ApiImplicitParam(name = "id", value = "公司ID", required = true, dataType = "Long")
	@PostMapping("/info/{id}")
	public @ResponseBody BaseResponse<CompanyDto> getUser(@PathVariable("id") Long id, HttpSession session) {
		try {
			Preconditions.checkNotNull(id, "id参数异常");
			CompanyEntity company = companyService.findOne(id);
			Preconditions.checkNotNull(company, "查无对应公司数据");
			CompanyDto p = new CompanyDto();
			BUtils.copyPropertiesIgnoreNull(company, p);
			p.setStatus(company.getStatus().getValue());
			return new BaseResponse(BaseResponse.CODE_SUCCESS, "success", p);
		} catch (Exception e) {
			logger.info("获取公司失败:{}", e.getMessage(), e);
			return new BaseResponse(BaseResponse.CODE_FAILED, "获取公司失败");
		}
	}

	@ApiOperation(value = "公司列表", notes = "获取公司列表数据")
	@ApiImplicitParam(name = "search", value = "查询公司信息", required = false, dataType = "Object")
	@PostMapping("/listAll")
	public @ResponseBody BaseResponse<List<CompanyDto>> listAll(HttpServletRequest request) {
		try {
			String token = request.getHeader(Constants.TOKEN_FIELD);
			String loginName = JwtUtils.parseJWT(token).getSubject();
			UserEntity operator = userService.getUserByLoginName(loginName);
			List<CompanyEntity> companyEntities = Lists.newArrayList();
			if(!RoleUtils.isPlatformAdmin(operator.getTenantCode())){
				SearchFilters searchFilters = new SearchFilters();
				searchFilters.add(Searcher.eq("tenantCode", operator.getTenantCode()));
				companyEntities = (List<CompanyEntity>) companyService.findAll(searchFilters);
			}else {
				companyEntities = (List<CompanyEntity>) companyService.findAll();
			}
			List<CompanyDto> list = Lists.newArrayList();
			if(Collections3.isNotEmpty(companyEntities)){
				list = companyEntities.stream().map(c -> {
					CompanyDto p = new CompanyDto();
					BUtils.copyPropertiesIgnoreNull(c, p);
					p.setStatus(c.getStatus().getValue());
					return p;
				}).collect(Collectors.toList());
			}
			return new BaseResponse(BaseResponse.CODE_SUCCESS, "success", list);
		} catch (Exception e) {
			logger.info("获取公司列表失败:{}", e.getMessage(), e);
			return new BaseResponse(BaseResponse.CODE_FAILED, "获取公司列表失败");
		}
	}

	@ApiOperation(value = "公司列表", notes = "获取公司列表数据")
	@ApiImplicitParam(name = "search", value = "查询公司信息", required = false, dataType = "Object")
	@PostMapping("/list")
	public @ResponseBody BaseResponse<PageResponse<CompanyDto>> list(@RequestBody BasePageRequest<CompanyDto> search,HttpServletRequest request) {
		try {
			String token = request.getHeader(Constants.TOKEN_FIELD);
			String loginName = JwtUtils.parseJWT(token).getSubject();
			UserEntity operator = userService.getUserByLoginName(loginName);
			Preconditions.checkNotNull(search, "查询参数异常");
			if(!RoleUtils.isPlatformAdmin(operator.getTenantCode())){
				if(null==search.getParams()){
					search.setParams(new CompanyDto());
				}
				search.getParams().setTenantCode(operator.getTenantCode());
			}
			Page<CompanyEntity> page = companyService.findCompanys(search.toPageRequest(), search.getParams());
			List<CompanyDto> list = Lists.newArrayList();
			if (Collections3.isNotEmpty(page.getContent())) {
				list = page.getContent().stream().map(c -> {
					CompanyDto p = new CompanyDto();
					BUtils.copyPropertiesIgnoreNull(c, p);
					p.setStatus(c.getStatus().getValue());
					return p;
				}).collect(Collectors.toList());
			}
			return new BaseResponse(BaseResponse.CODE_SUCCESS, "success", new PageResponse(page.getTotalElements(),
					page.getTotalPages(), page.getNumber(), page.getSize(), list));
		} catch (Exception e) {
			logger.info("获取公司列表失败:{}", e.getMessage(), e);
			return new BaseResponse(BaseResponse.CODE_FAILED, "获取公司列表失败");
		}
	}

	@ApiOperation(value = "更新公司信息", notes = "更新公司基础信息")
	@ApiImplicitParam(name = "company", value = "公司信息", required = true, dataType = "Object")
	@PostMapping("/update")
	public @ResponseBody BaseResponse update(@RequestBody CompanyDto company, HttpServletRequest request) {
		try {
			String token = request.getHeader(Constants.TOKEN_FIELD);
			String loginName = JwtUtils.parseJWT(token).getSubject();
			UserEntity operator = userService.getUserByLoginName(loginName);
			Preconditions.checkState(operator.getRole().equals(RoleEnum.SUPER_ADMIN), "非超级管理员，无权限操作");
			Preconditions.checkNotNull(company, "company为null");
			Preconditions.checkNotNull(company.getId(), "companyId为null");
			CompanyEntity cp = companyService.findOne(company.getId());
			Preconditions.checkNotNull(cp, "查无产品指标数据");
			if(!RoleUtils.isPlatformAdmin(operator.getTenantCode())){
				Preconditions.checkState(StringUtils.equals(operator.getTenantCode(),cp.getTenantCode()),"非本租户下的公司，无权操作");
			}
			BUtils.copyPropertiesIgnoreNull(company, cp);
			cp.setStatus(StatusEnum.getEnum(company.getStatus()));
			cp.setModifyBy(loginName);
			cp.setModifyTime(new Date());
			companyService.saveCompany(cp, GsonUtils.buildGson().toJson(cp), OperateTypeEnum.UPDATE, loginName);
			return new BaseResponse(BaseResponse.CODE_SUCCESS, "更新成功");
		} catch (Exception e) {
			logger.info("更新公司数据失败:{}", e.getMessage(), e);
			return new BaseResponse(BaseResponse.CODE_FAILED, e.getMessage());
		}
	}

	@ApiOperation(value = "删除公司", notes = "删除公司")
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
			searchFilters.add(Searcher.eq("companyId", id));
			List<UserEntity> users = userService.findAll(searchFilters);
			Preconditions.checkState(Collections3.isEmpty(users), "此公司正在使用中，无法执行删除");
			CompanyEntity company = companyService.findOne(id);
			Preconditions.checkNotNull(company, "查无对应的公司信息");
			if(!RoleUtils.isPlatformAdmin(operator.getTenantCode())){
				Preconditions.checkState(StringUtils.equals(operator.getTenantCode(),company.getTenantCode()),"非本租户下的公司，无权操作");
			}
			companyService.deleteCompany(company, "删除公司数据", OperateTypeEnum.DELETE, loginName);
			return new BaseResponse(BaseResponse.CODE_SUCCESS, "success");
		} catch (Exception e) {
			logger.info("删除公司信息失败:{}", e.getMessage(), e);
			return new BaseResponse(BaseResponse.CODE_FAILED, e.getMessage());
		}
	}

}

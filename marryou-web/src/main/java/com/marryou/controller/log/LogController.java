package com.marryou.controller.log;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.marryou.commons.utils.base.BUtils;
import com.marryou.commons.utils.collections.Collections3;
import com.marryou.dto.request.BasePageRequest;
import com.marryou.dto.response.BaseResponse;
import com.marryou.dto.response.PageResponse;
import com.marryou.metadata.dto.LogDto;
import com.marryou.metadata.entity.OperateLogEntity;
import com.marryou.metadata.entity.UserEntity;
import com.marryou.metadata.service.OperateLogService;
import com.marryou.metadata.service.UserService;
import com.marryou.utils.Constants;
import com.marryou.utils.JwtUtils;
import com.marryou.utils.RoleUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @date 2017/4/22
 */
@Controller
@RequestMapping("/api/log")
public class LogController {

	private static final Logger logger = LoggerFactory.getLogger(LogController.class);

	@Autowired
	private OperateLogService operateLogService;
	@Autowired
	private UserService userService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		df.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(df, true));
	}

	@ApiOperation(value = "操作日志列表", notes = "操作日志列表数据")
	@ApiImplicitParam(name = "search", value = "查询操作日志信息", required = false, dataType = "Object")
	@PostMapping("/list")
	public @ResponseBody BaseResponse<PageResponse<LogDto>> list(@RequestBody BasePageRequest<LogDto> search,
																 HttpServletRequest request) {
		try {
			Preconditions.checkNotNull(search, "查询参数异常");
			String token = request.getHeader(Constants.TOKEN_FIELD);
			String loginName = JwtUtils.parseJWT(token).getSubject();
			UserEntity operator = userService.getUserByLoginName(loginName);
			if(!RoleUtils.isPlatformAdmin(operator.getTenantCode())){
				if(null==search.getParams()){
					search.setParams(new LogDto());
				}
				search.getParams().setTenantCode(operator.getTenantCode());
			}
			Page<OperateLogEntity> page = operateLogService.findLogs(search.toPageRequest(), search.getParams());
			List<LogDto> logs = Lists.newArrayList();
			if (Collections3.isNotEmpty(page.getContent())) {
				logs = page.getContent().stream().map(log -> {
					LogDto operateLog = new LogDto();
					BUtils.copyPropertiesIgnoreNull(log,operateLog,"operateType","type");
					operateLog.setOperateType(log.getOperateType().getValue());
					operateLog.setType(log.getType().getValue());
					return operateLog;
				}).collect(Collectors.toList());
			}
			return new BaseResponse(BaseResponse.CODE_SUCCESS, "success", new PageResponse(page.getTotalElements(),
					page.getTotalPages(), page.getNumber(), page.getSize(), logs));
		} catch (Exception e) {
			logger.info("获取操作日志列表失败:{}", e.getMessage(), e);
			return new BaseResponse(BaseResponse.CODE_FAILED, "获取操作日志列表失败");
		}
	}



}

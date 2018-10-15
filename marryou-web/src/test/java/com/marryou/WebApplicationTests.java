package com.marryou;

import com.marryou.commons.utils.json.GsonUtils;
import com.marryou.dto.request.BasePageRequest;
import com.marryou.metadata.dto.DeliveryDto;
import com.marryou.metadata.dto.StandardParamsDto;
import com.marryou.metadata.dto.UserSearchDto;
import com.marryou.metadata.entity.UserEntity;
import com.marryou.metadata.service.RedisService;
import com.marryou.metadata.service.UserService;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WebApplicationTests {

	private static final Logger logger = LoggerFactory.getLogger(WebApplicationTests.class);

	// 模拟MVC对象，通过MockMvcBuilders.webAppContextSetup(this.wac).build()初始化
	private MockMvc mockMvc;

	// 注入WebApplicationContext
	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private RedisService redisService;

	@Autowired
	private UserService userService;

	@Before // 在测试开始前初始化工作
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void userContorllerTest(){

		UserSearchDto userDto = new UserSearchDto();
		userDto.setId(1L);
		userDto.setUserName("linhongyu");
		userDto.setSex(1);
		//userDto.setBirth(new Date());
		userDto.setStartTime("2018-06-13");

		/*Map<String,Object> map = Maps.newHashMap();
		map.put("user",userDto);*/
		String json = GsonUtils.buildGson().toJson(userDto);

		MvcResult result = null;// 返回执行请求的结果
		try {
			result = mockMvc.perform(post("/user/update")
					/*.param("id","1")
					.param("userName","linhongyu")
					.param("sex","1")
					.param("birth","2018-06-13 06:12:12")*/
					.content(json)
					.sessionAttr("user","unique"))
                    .andExpect(MockMvcResultMatchers.status().isOk())// 模拟向testRest发送get请求
                    //.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                    .andReturn();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			System.out.println(result.getResponse().getContentAsString());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	private MockHttpServletRequestBuilder post(String uri){
		return MockMvcRequestBuilders.post(uri);
	}

	@Test
	public void contextLoads() {
		for(int i=0;i<25;i++){
			UserEntity user = new UserEntity();
			user.setLoginName("uniqueLhy_"+i);
			user.setPassword("123456");
			user.setUserName("林宏宇");
			user.setCreateBy("test");
			user.setCreateTime(new Date());
			user.setCompanyId(123456789L);
			userService.save(user);
		}
	}

	@Test
	public void listTest(){
		PageRequest pageRequest = new PageRequest(0, 10);
		Page<UserEntity> page = userService.findUsers(pageRequest,null);
		logger.info("totalPage:{},totalElements:{},currentNum:{},content:{}",page.getTotalPages(),page.getTotalElements(),
				page.getNumber(),page.getContent().toString());
	}

	@Test
	public void DeliveryTest(){
		DeliveryDto dto = new DeliveryDto();
		dto.setDistributorId(1L);
		dto.setDistributorName("testDistributor");
		dto.setSupplierId(2L);
		dto.setSupplierName("testSupplier");
		dto.setAuditor("lhy");
		dto.setChecker("lhy");
		dto.setBatchNo("zs-123");
		dto.setCarNo("闽D21034");
		dto.setDeliveryTime("2018-06-16 10:00:21");
		dto.setDeliveryNo("wms123456");
		dto.setLevel(0);
		dto.setGrossWeight(new BigDecimal(12.12));
		dto.setNetWeight(new BigDecimal(21.4));
		dto.setTareWeight(new BigDecimal(10.887));
		dto.setStatus(0);

		List<StandardParamsDto> standards = Lists.newArrayList();
		for(int i=0;i<3;i++){
			StandardParamsDto standardDto = new StandardParamsDto();
			standardDto.setStandardId(1L+i);
			standardDto.setStandardName("水泥指标"+i);
			standardDto.setParameter("1.23");
			standards.add(standardDto);
		}
		dto.setStandards(standards);

		String json = GsonUtils.buildGson().toJson(dto);

		MvcResult result = null;// 返回执行请求的结果
		try {
			result = mockMvc.perform(post("/delivery/create")
					.content(json).contentType(MediaType.APPLICATION_JSON)
					.sessionAttr("user","unique"))
					.andExpect(MockMvcResultMatchers.status().isOk())// 模拟向testRest发送get请求
					//.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
					.andReturn();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			System.out.println(result.getResponse().getContentAsString());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void tokenRedisTest(){
		//String val = (String) redisService.get("1");
		//System.out.print("-----------val:"+val+"-------------");

		BasePageRequest<DeliveryDto> search = new BasePageRequest<>();
		String json = GsonUtils.buildGson().toJson(search);
		System.out.print("-----------val:"+json+"-------------");
	}

}

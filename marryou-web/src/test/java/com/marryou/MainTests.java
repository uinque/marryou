package com.marryou;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.marryou.metadata.dto.StandardTableDto;
import com.marryou.metadata.entity.DeliveryOrderEntity;
import com.marryou.metadata.entity.StandardParamsEntity;
import com.marryou.metadata.entity.StandardTitleEntity;
import com.marryou.metadata.service.DeliveryService;
import com.marryou.metadata.service.StandardParamsService;
import com.marryou.metadata.service.StandardTitleService;
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

import com.marryou.commons.utils.json.GsonUtils;
import com.marryou.dto.request.BasePageRequest;
import com.marryou.metadata.dto.DeliveryDto;
import com.marryou.metadata.dto.StandardValDto;
import com.marryou.metadata.dto.UserSearchDto;
import com.marryou.metadata.entity.UserEntity;
import com.marryou.metadata.service.RedisService;
import com.marryou.metadata.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MainTests {

	private static final Logger logger = LoggerFactory.getLogger(MainTests.class);

	@Autowired
	private StandardTitleService standardTitleService;
	@Autowired
	private StandardParamsService standardParamsService;
	@Autowired
	private DeliveryService deliveryService;

	@Test
	public void standardTitleTest(){
		Long productId = 41L;
		List<StandardTitleEntity> rows = standardTitleService.findByProductIdAndType(productId,0);
		rows.forEach(r->{
			logger.info("rowId:{},rowTitle:{},sort:{}",r.getId(),r.getName(),r.getOrderSort());
		});
		List<StandardTitleEntity> column = standardTitleService.findByProductIdAndTypeAndTenantCode(productId,1,"yicai");
		column.forEach(c->{
			logger.info("columnId:{},columnTitle:{},sort:{}",c.getId(),c.getName(),c.getOrderSort());
		});
		List<StandardTitleEntity> titles = standardTitleService.findByProductId(productId);
		titles.forEach(t->{
			logger.info("titleId:{},titleName:{},sort:{}",t.getId(),t.getName(),t.getOrderSort());
		});
	}

	@Test
	public void standardParamsTest(){
		Long productId = 41L;

		List<StandardTableDto> list = standardParamsService.buildStandardParams(productId);
		list.forEach(l->{
			logger.info("---rowId:{},rowTitle:{},sort:{}---",l.getRowId(),l.getRowTitle(),l.getOrderSort());
			l.getParams().forEach(p->{
				logger.info("rowId:{},columnId:{},type:{},productId:{},val:{},point:{}",
						p.getRowId(),p.getColumnId(),p.getType(),p.getProductId(),p.getVal(),p.getPointNum());
			});
		});

		/*List<StandardParamsEntity> params = standardParamsService.findByProductId(productId);
		params.forEach(p->{
			logger.info("rowId:{},columnId:{},type:{},val:{},point:{}",p.getRowId(),p.getColumnId(),p.getType(),p.getVal(),p.getPointNum());
		});
		logger.info("-------------------------------------------------");
		List<StandardParamsEntity> rowParams = standardParamsService.findByProductIdAndRowId(productId,12L);
		rowParams.forEach(p->{
			logger.info("rowId:{},columnId:{},type:{},val:{},point:{}",p.getRowId(),p.getColumnId(),p.getType(),p.getVal(),p.getPointNum());
		});
		logger.info("-------------------------------------------------");
		List<StandardParamsEntity> columnParams = standardParamsService.findByProductIdAndColumnId(productId,1L);
		columnParams.forEach(p->{
			logger.info("rowId:{},columnId:{},type:{},val:{},point:{}",p.getRowId(),p.getColumnId(),p.getType(),p.getVal(),p.getPointNum());
		});
		logger.info("-------------------------------------------------");
		StandardParamsEntity p = standardParamsService.findByProductIdAndRowIdAndColumnId(productId,12L,1L);
		logger.info("param[rowId:{},columnId:{},type:{},val:{},point:{}]",p.getRowId(),p.getColumnId(),p.getType(),p.getVal(),p.getPointNum());
*/
	}

	@Test
	public void deliveryTest(){
		int count = deliveryService.countByProductId(41L);
		logger.info("ProductId:41->count:{}",count);
	}

	@Test
	public void deliveryOrdersTest(){
		Page<DeliveryOrderEntity> page = deliveryService.findDeliveryOrders(new PageRequest(0,10),new DeliveryDto());
		if(null!=page){
			page.getContent().forEach(p->{
				System.out.println(p.getId()+"---"+p.getColumnId());
			});
		}
	}



}

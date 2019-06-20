package com.marryou.metadata.service.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.marryou.commons.utils.collections.Collections3;
import com.marryou.metadata.entity.DeliveryOrderEntity;
import com.marryou.metadata.entity.DeliveryStandardEntity;
import com.marryou.metadata.entity.ProductEntity;
import com.marryou.metadata.entity.StandardEntity;
import com.marryou.metadata.entity.StandardParamsEntity;
import com.marryou.metadata.entity.StandardTitleEntity;
import com.marryou.metadata.enums.StatusEnum;
import com.marryou.metadata.service.DeliveryService;
import com.marryou.metadata.service.DeliveryStandardService;
import com.marryou.metadata.service.MoveService;
import com.marryou.metadata.service.ProductService;
import com.marryou.metadata.service.StandardParamsService;
import com.marryou.metadata.service.StandardService;
import com.marryou.metadata.service.StandardTitleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by linhy on 2019/2/17.
 */
@Service
public class MoveServiceImpl implements MoveService {

    private static final Logger logger = LoggerFactory.getLogger(MoveServiceImpl.class);

	@Autowired
	private ProductService productService;
	@Autowired
	private StandardTitleService standardTitleService;
	@Autowired
	private StandardParamsService standardParamsService;
	@Autowired
	private StandardService standardService;
	@Autowired
	private DeliveryService deliveryService;
	@Autowired
	private DeliveryStandardService deliveryStandardService;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void moveStandardData() {
		//1、查找所有的产品，根据产品去新增对应的standardTitle与standardParam数据
		List<ProductEntity> products = (List<ProductEntity>) productService.findAll();
		if (Collections3.isNotEmpty(products)) {
			products.forEach(p -> {
				if (p.getStatus().equals(StatusEnum.EFFECTIVE)) {
					//2、新增columnTitle
					Map<String, StandardTitleEntity> columnMap = this.createStandardTitle(p.getId(), p.getTenantCode());
					//3、新增rowTitle与standardParams
					List<StandardEntity> standards = standardService.findByPrdouctId(p.getId());
					if (Collections3.isNotEmpty(standards)) {
						//rowTitle
						for (int i = 0; i < standards.size(); i++) {
						    StandardEntity s = standards.get(i);
							StandardTitleEntity rowTitle = this.createStandardTitle(s.getName(),
									p.getId(), i + 1, s.getTenantCode());
							//params
							this.createStandardParams(s, rowTitle, columnMap);
							//update standard
                            s.setRowId(rowTitle.getId());
                            standardService.save(s);
						}
					}
				}
			});
		}

	}

	@Async("taskExecutor")
	@Override
    public void moveDeliveryStandardData(Map<String, StandardTitleEntity> columnMap, List<DeliveryOrderEntity> list) {
        if(Collections3.isNotEmpty(list)){
            list.forEach(d->{
                try{
                    Integer num = d.getLevel().getValue()+1;
                    String key = d.getProductId()+"_"+num;
                    StandardTitleEntity columnTitle = columnMap.get(key);
                    if(null!=columnTitle){
                        d.setColumnId(columnTitle.getId());
                        d.setColumnTitle(columnTitle.getName());
                    }
                    List<DeliveryStandardEntity> standars = d.getStandards();
                    standars.forEach(s->{
                        StandardEntity st = standardService.findOne(s.getStandardId());
                        if(null!=st){
                            StandardTitleEntity rowTitle = standardTitleService.findOne(st.getRowId());
                            Preconditions.checkNotNull(rowTitle,"【数据迁移】查无对应的标准模板【行标题】数据");
                            StandardParamsEntity param = standardParamsService.findByProductIdAndRowIdAndColumnId(
                                    st.getProductId(),st.getRowId(),columnTitle.getId());
                            Preconditions.checkNotNull(param,"【数据迁移】查无对应的标准模板值数据");
                            s.setStandardId(param.getId());
                            s.setStandardName(rowTitle.getName());
                        }
                    });
                    d.setStandards(standars);
                    deliveryService.save(d);
                }catch (Exception e){
                    logger.info("[数据迁移]出库单ID:{}迁移失败",d.getId(),e);
                }
            });
        }
    }

	private void createStandardParams(StandardEntity standard, StandardTitleEntity rowTilte,
			Map<String, StandardTitleEntity> map) {
		for (StandardTitleEntity columTitle : map.values()) {
			String val = "";
			if (columTitle.getName().equals("一级")) {
				val = standard.getOneLevel();
			} else if (columTitle.getName().equals("二级")) {
				val = standard.getTwoLevel();
			} else if (columTitle.getName().equals("三级")) {
				val = standard.getThreeLevel();
			}
			StandardParamsEntity param = new StandardParamsEntity(rowTilte.getId(), columTitle.getId(),
					standard.getType(), standard.getProductId(), val, standard.getPointNum());
			param.setCreateBy("system");
			param.setCreateTime(new Date());
			param.setTenantCode(standard.getTenantCode());
			standardParamsService.save(param);
		}
	}

	private StandardTitleEntity createStandardTitle(String rowTitle, Long productId, Integer orderSort,
			String tenantCode) {
		StandardTitleEntity title = new StandardTitleEntity(rowTitle, StandardTitleEntity.ROW, productId, orderSort);
		title.setCreateBy("system");
		title.setCreateTime(new Date());
		title.setTenantCode(tenantCode);
		return standardTitleService.save(title);
	}

	private Map<String, StandardTitleEntity> createStandardTitle(Long productId, String tenantCode) {
		Map<String, StandardTitleEntity> map = Maps.newHashMap();
		StandardTitleEntity title1 = new StandardTitleEntity("一级", StandardTitleEntity.COLUMN, productId, 1);
		StandardTitleEntity title2 = new StandardTitleEntity("二级", StandardTitleEntity.COLUMN, productId, 2);
		StandardTitleEntity title3 = new StandardTitleEntity("三级", StandardTitleEntity.COLUMN, productId, 3);
		StandardTitleEntity titleOne = this.createStandardTitle(title1, tenantCode);
		StandardTitleEntity titleTwo = this.createStandardTitle(title2, tenantCode);
		StandardTitleEntity titleThree = this.createStandardTitle(title3, tenantCode);
		map.put(productId + "_1", titleOne);
		map.put(productId + "_2", titleTwo);
		map.put(productId + "_3", titleThree);
		return map;
	}

	@Override
	public Map<String, StandardTitleEntity> bulidColumnTitleMap() {
        Map<String, StandardTitleEntity> map = Maps.newHashMap();
        List<ProductEntity> products = (List<ProductEntity>) productService.findAll();
        products.forEach(p->{
            List<StandardTitleEntity> columnTitles = standardTitleService.findByProductIdAndType(p.getId(),
                    StandardTitleEntity.COLUMN);
            columnTitles.forEach(c->{
                if(c.getName().equals("一级")){
                    map.put(c.getProductId()+"_1",c);
                }else if(c.getName().equals("二级")){
                    map.put(c.getProductId()+"_2",c);
                }else if(c.getName().equals("三级")){
                    map.put(c.getProductId()+"_3",c);
                }
            });
        });
		return map;
    }

	private StandardTitleEntity createStandardTitle(StandardTitleEntity t, String tenantCode) {
		t.setCreateBy("system");
		t.setCreateTime(new Date());
		t.setTenantCode(tenantCode);
		return standardTitleService.save(t);
	}
}

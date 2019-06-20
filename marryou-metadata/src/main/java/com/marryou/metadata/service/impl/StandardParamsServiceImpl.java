package com.marryou.metadata.service.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.marryou.commons.utils.collections.Collections3;
import com.marryou.metadata.dao.StandardParamsDao;
import com.marryou.metadata.dto.StandardParamsDto;
import com.marryou.metadata.dto.StandardTableDto;
import com.marryou.metadata.entity.OperateLogEntity;
import com.marryou.metadata.entity.StandardParamsEntity;
import com.marryou.metadata.entity.StandardTitleEntity;
import com.marryou.metadata.enums.LogTypeEnum;
import com.marryou.metadata.enums.OperateTypeEnum;
import com.marryou.metadata.service.OperateLogService;
import com.marryou.metadata.service.StandardParamsService;
import com.marryou.metadata.service.StandardTitleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by linhy on 2019/2/11.
 */
@Service
public class StandardParamsServiceImpl extends AbsBaseService<StandardParamsEntity, StandardParamsDao>
		implements StandardParamsService {

	private static final Logger logger = LoggerFactory.getLogger(StandardParamsServiceImpl.class);

	@Autowired
	private StandardTitleService standardTitleService;
	@Autowired
	private OperateLogService operateLogService;
	@Autowired
	private StandardParamsDao standardParamsDao;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void saveStandardParamsList(List<StandardParamsEntity> params) {
		Preconditions.checkState(Collections3.isNotEmpty(params), "params数据为null");
		params.forEach(p -> {
			StandardTitleEntity rowTitle = standardTitleService.findOne(p.getRowId());
			Preconditions.checkNotNull(rowTitle, "查无对应rowTitle数据");
			Preconditions.checkState(rowTitle.getType().equals(StandardTitleEntity.ROW), "rowTitle非【行】标题数据");
			StandardTitleEntity columnTitle = standardTitleService.findOne(p.getColumnId());
			Preconditions.checkNotNull(columnTitle, "查无对应columnTitle数据");
			Preconditions.checkState(columnTitle.getType().equals(StandardTitleEntity.COLUMN), "columnTitle非【列】标题数据");
			Preconditions.checkState((rowTitle.getProductId().equals(columnTitle.getProductId())
					&& p.getProductId().equals(rowTitle.getProductId())), "productId不一致");
			this.save(p);
		});
		operateLogService.save(new OperateLogEntity("新增产品模板指标数据", OperateTypeEnum.CREATE, params.get(0).getProductId(),
				LogTypeEnum.STANDARD, params.get(0).getCreateBy(), new Date(), params.get(0).getTenantCode()));
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void updateStandardParamsList(List<StandardParamsEntity> params) {
		Preconditions.checkState(Collections3.isNotEmpty(params), "params数据为null");
		params.forEach(p -> {
			this.save(p);
		});
		operateLogService.save(new OperateLogEntity("更新产品模板指标数据", OperateTypeEnum.UPDATE, params.get(0).getProductId(),
				LogTypeEnum.STANDARD, params.get(0).getModifyBy(), new Date(), params.get(0).getTenantCode()));
	}

	@Override
	public List<StandardTableDto> buildStandardParams(Long productId) {
		Preconditions.checkNotNull(productId, "productId为null");
		List<StandardTableDto> list = Lists.newArrayList();
		//获取排序过的标题数据
		List<StandardTitleEntity> columnTitles = standardTitleService.findByProductIdAndType(productId,
				StandardTitleEntity.COLUMN);
		if (Collections3.isNotEmpty(columnTitles)) {
			List<StandardTitleEntity> rowTitles = standardTitleService.findByProductIdAndType(productId,
					StandardTitleEntity.ROW);
			if (Collections3.isNotEmpty(rowTitles)) {
				//验证 行*列=标准数据值的量
				List<StandardParamsEntity> datas = standardParamsDao.findByProductId(productId);
				if (Collections3.isNotEmpty(datas)) {
					int totalDatas = rowTitles.size() * columnTitles.size();
					Preconditions.checkState(datas.size() == totalDatas, "设置的产品模板标准参数量与应有的数量不一致，数据异常");
					Map<String,StandardParamsEntity> map = bulidParamsMap(datas);
                    //遍历rows，构建有序的column数据的行
					rowTitles.forEach(r -> {
						StandardTableDto table = new StandardTableDto(r.getId(), r.getName(), r.getOrderSort());
						List<StandardParamsDto> params = Lists.newArrayList();
						columnTitles.forEach(c -> {
                            String key = r.getId()+"_"+c.getId();
                            StandardParamsEntity s = map.get(key);
                            Preconditions.checkNotNull(s,"根据rowId+columnId获取指标参数值数据为null");
                            StandardParamsDto dto = new StandardParamsDto();
                            BeanUtils.copyProperties(s,dto);
                            dto.setRowTitle(r.getName());
                            params.add(dto);
						});
                        table.setParams(params);
                        list.add(table);
					});
				} else {
					logger.info("构建指标表格数,据产品ID:{},尚未设置【标准参数值】", productId);
					throw new RuntimeException("构建指标表格数,据产品ID:" + productId + ",尚未设置【标准参数值】");
				}
			} else {
				logger.info("构建指标表格数,据产品ID:{},尚未设置【行】标题", productId);
				throw new RuntimeException("构建指标表格数,据产品ID:" + productId + ",尚未设置【行】标题");
			}
		} else {
			logger.info("构建指标表格数,据产品ID:{},尚未设置【列】标题", productId);
			throw new RuntimeException("构建指标表格数,据产品ID:" + productId + ",尚未设置【列】标题");
		}
		return list;
	}

	@Override
	public List<StandardParamsEntity> findByProductIdAndRowId(Long productId, Long rowId) {
		Preconditions.checkNotNull(productId,"productId为null");
		Preconditions.checkNotNull(rowId,"rowId为null");
		return standardParamsDao.findByProductIdAndRowId(productId,rowId);
	}

	@Override
	public List<StandardParamsEntity> findByProductIdAndColumnId(Long productId, Long columnId) {
		Preconditions.checkNotNull(productId,"productId为null");
		Preconditions.checkNotNull(columnId,"columnId为null");
		return standardParamsDao.findByProductIdAndColumnId(productId,columnId);
	}

	@Override
	public List<StandardParamsEntity> findByProductId(Long productId) {
		Preconditions.checkNotNull(productId,"productId为null");
		return standardParamsDao.findByProductId(productId);
	}

	@Override
	public StandardParamsEntity findByProductIdAndRowIdAndColumnId(Long productId, Long rowId, Long columnId) {
		Preconditions.checkNotNull(productId,"productId为null");
		Preconditions.checkNotNull(columnId,"columnId为null");
		Preconditions.checkNotNull(rowId,"rowId为null");
		return standardParamsDao.findByProductIdAndRowIdAndColumnId(productId,rowId,columnId);
	}


	private Map<String,StandardParamsEntity> bulidParamsMap(List<StandardParamsEntity> datas) {
        Map<String,StandardParamsEntity> map = Maps.newHashMap();
        for(StandardParamsEntity s : datas){
            String key = s.getRowId()+"_"+s.getColumnId();
            map.put(key,s);
        }
        return map;
    }
}

package com.marryou.metadata.service.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.marryou.commons.utils.collections.Collections3;
import com.marryou.commons.utils.qrcode.wrapper.QrCodeGenWrapper;
import com.marryou.commons.utils.qrcode.wrapper.QrCodeOptions;
import com.marryou.commons.utils.time.DateUtils;
import com.marryou.metadata.dao.DeliveryDao;
import com.marryou.metadata.dto.DeliveryCountDto;
import com.marryou.metadata.dto.DeliveryDto;
import com.marryou.metadata.entity.DeliveryOrderEntity;
import com.marryou.metadata.entity.DeliveryStandardEntity;
import com.marryou.metadata.entity.OperateLogEntity;
import com.marryou.metadata.enums.LevelEnum;
import com.marryou.metadata.enums.LogTypeEnum;
import com.marryou.metadata.enums.OperateTypeEnum;
import com.marryou.metadata.enums.StatusEnum;
import com.marryou.metadata.enums.TechnoEnum;
import com.marryou.metadata.service.DeliveryService;
import com.marryou.metadata.service.DeliveryStandardService;
import com.marryou.metadata.service.OperateLogService;
import com.marryou.metadata.service.ailiyun.OSSService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by linhy on 2018/6/6.
 */
@Service
public class DeliveryServiceImpl extends AbsBaseService<DeliveryOrderEntity, DeliveryDao> implements DeliveryService {

	private static final Logger logger = LoggerFactory.getLogger(DeliveryServiceImpl.class);

	@Value("${base.domain.url}")
	private String baseUrl;
	@Value("${img.middle.url}")
	private String middleUrl;

	@Autowired
	private OperateLogService operateLogService;
	@Autowired
	private DeliveryStandardService deliveryStandardService;
	@Autowired
	private OSSService ossService;

	@Override
	public Page<DeliveryOrderEntity> findDeliveryOrders(PageRequest pageRequest, DeliveryDto search) {
		Preconditions.checkNotNull(pageRequest, "pageRequest为null");
		Page<DeliveryOrderEntity> page = dao.findAll(new Specification<DeliveryOrderEntity>() {
			@Override
			public Predicate toPredicate(Root<DeliveryOrderEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> where = Lists.newArrayList();
				Path<Long> id = root.get("id");
				Path<String> deliveryNo = root.get("deliveryNo");
				Path<Long> distributorId = root.get("distributorId");
				Path<String> distributorName = root.get("distributorName");
				Path<Long> supplierId = root.get("supplierId");
				Path<String> supplierName = root.get("supplierName");
				Path<String> checker = root.get("checker");
				Path<String> auditor = root.get("auditor");
				Path<String> carNo = root.get("carNo");
				Path<String> batchNo = root.get("batchNo");
				Path<LevelEnum> level = root.get("level");
				Path<Long> productId = root.get("productId");
				Path<StatusEnum> status = root.get("status");
				Path<Date> outTime = root.get("outTime");
				//Path<Date> createTime = root.get("createTime");
				Path<String> tenantCode = root.get("tenantCode");
				if (null != search) {
					if (null != search.getId()) {
						where.add(cb.and(cb.equal(id, search.getId())));
					}
					if (StringUtils.isNotBlank(search.getDeliveryNo())) {
						where.add(cb.and(cb.like(deliveryNo, search.getDeliveryNo() + "%")));
					}
					if (StringUtils.isNotBlank(search.getBatchNo())) {
						where.add(cb.and(cb.like(batchNo, search.getBatchNo() + "%")));
					}
					if (null != search.getLevel()) {
						where.add(cb.and(cb.equal(level, LevelEnum.getEnum(search.getLevel()))));
					}
					if (null != search.getDistributorId()) {
						where.add(cb.and(cb.equal(distributorId, search.getDistributorId())));
					}
					if (null != search.getSupplierId()) {
						where.add(cb.and(cb.equal(supplierId, search.getSupplierId())));
					}
					if (StringUtils.isNotBlank(search.getSupplierName())) {
						where.add(cb.and(cb.like(supplierName, search.getSupplierName() + "%")));
					}
					if (StringUtils.isNotBlank(search.getDistributorName())) {
						where.add(cb.and(cb.like(distributorName, search.getDistributorName() + "%")));
					}
					if (StringUtils.isNotBlank(search.getChecker())) {
						where.add(cb.and(cb.like(checker, search.getChecker() + "%")));
					}
					if (StringUtils.isNotBlank(search.getAuditor())) {
						where.add(cb.and(cb.like(auditor, search.getAuditor() + "%")));
					}
					if (StringUtils.isNotBlank(search.getCarNo())) {
						where.add(cb.and(cb.like(carNo, search.getCarNo() + "%")));
					}
					if (null != search.getProductId()) {
						where.add(cb.and(cb.equal(productId, search.getProductId())));
					}
					if (null != search.getStatus()) {
						where.add(cb.and(cb.equal(status, StatusEnum.getEnum(search.getStatus()))));
					}
					if (StringUtils.isNotBlank(search.getTenantCode())) {
						where.add(cb.and(cb.equal(tenantCode, search.getTenantCode())));
					}
					if (StringUtils.isNotBlank(search.getStartTime()) && StringUtils.isNotBlank(search.getEndTime())) {
						where.add(cb.and(cb.between(outTime, DateUtils.convertToDateTime(search.getStartTime()),
								DateUtils.convertToDateTime(search.getEndTime()))));
					}
					/*if (StringUtils.isNotBlank(search.getStartTime()) && StringUtils.isNotBlank(search.getEndTime())) {
						where.add(cb.and(cb.between(createTime, DateUtils.convertToDateTime(search.getStartTime()),
								DateUtils.convertToDateTime(search.getEndTime()))));
					}*/
				}
				query.where(where.toArray(new Predicate[] {}));
				return null;
			}
		}, pageRequest);
		return page;
	}

	@Override
	public DeliveryCountDto statisticsDelivery(DeliveryDto search) {
		List<DeliveryOrderEntity> list = Lists.newArrayList();
		if (null != search) {
			list = dao.findAll(new Specification<DeliveryOrderEntity>() {
				@Override
				public Predicate toPredicate(Root<DeliveryOrderEntity> root, CriteriaQuery<?> query,
						CriteriaBuilder cb) {
					List<Predicate> where = Lists.newArrayList();
					Path<Long> id = root.get("id");
					Path<String> deliveryNo = root.get("deliveryNo");
					Path<Long> distributorId = root.get("distributorId");
					Path<String> distributorName = root.get("distributorName");
					Path<Long> supplierId = root.get("supplierId");
					Path<String> supplierName = root.get("supplierName");
					Path<String> checker = root.get("checker");
					Path<String> auditor = root.get("auditor");
					Path<String> carNo = root.get("carNo");
					Path<String> batchNo = root.get("batchNo");
					Path<LevelEnum> level = root.get("level");
					Path<Long> productId = root.get("productId");
					Path<StatusEnum> status = root.get("status");
					Path<Date> outTime = root.get("outTime");
					//Path<Date> createTime = root.get("createTime");
					Path<String> tenantCode = root.get("tenantCode");
					if (null != search) {
						if (null != search.getId()) {
							where.add(cb.and(cb.equal(id, search.getId())));
						}
						if (StringUtils.isNotBlank(search.getDeliveryNo())) {
							where.add(cb.and(cb.like(deliveryNo, search.getDeliveryNo() + "%")));
						}
						if (StringUtils.isNotBlank(search.getBatchNo())) {
							where.add(cb.and(cb.like(batchNo, search.getBatchNo() + "%")));
						}
						if (null != search.getLevel()) {
							where.add(cb.and(cb.equal(level, LevelEnum.getEnum(search.getLevel()))));
						}
						if (null != search.getDistributorId()) {
							where.add(cb.and(cb.equal(distributorId, search.getDistributorId())));
						}
						if (null != search.getSupplierId()) {
							where.add(cb.and(cb.equal(supplierId, search.getSupplierId())));
						}
						if (StringUtils.isNotBlank(search.getSupplierName())) {
							where.add(cb.and(cb.like(supplierName, search.getSupplierName() + "%")));
						}
						if (StringUtils.isNotBlank(search.getDistributorName())) {
							where.add(cb.and(cb.like(distributorName, search.getDistributorName() + "%")));
						}
						if (StringUtils.isNotBlank(search.getChecker())) {
							where.add(cb.and(cb.like(checker, search.getChecker() + "%")));
						}
						if (StringUtils.isNotBlank(search.getAuditor())) {
							where.add(cb.and(cb.like(auditor, search.getAuditor() + "%")));
						}
						if (StringUtils.isNotBlank(search.getCarNo())) {
							where.add(cb.and(cb.like(carNo, search.getCarNo() + "%")));
						}
						if (null != search.getProductId()) {
							where.add(cb.and(cb.equal(productId, search.getProductId())));
						}
						if (null != search.getStatus()) {
							where.add(cb.and(cb.equal(status, StatusEnum.getEnum(search.getStatus()))));
						}
						if (StringUtils.isNotBlank(search.getTenantCode())) {
							where.add(cb.and(cb.equal(tenantCode, search.getTenantCode())));
						}
						if (StringUtils.isNotBlank(search.getStartTime())
								&& StringUtils.isNotBlank(search.getEndTime())) {
							where.add(cb.and(cb.between(outTime, DateUtils.convertToDateTime(search.getStartTime()),
									DateUtils.convertToDateTime(search.getEndTime()))));
						} else {
							Date when = DateUtils.getCurrentDateTime();
							where.add(cb.and(
									cb.between(outTime, DateUtils.getMonthStart(when), DateUtils.getMonthEnd(when))));
						}
						/*if (StringUtils.isNotBlank(search.getStartTime()) && StringUtils.isNotBlank(search.getEndTime())) {
							where.add(cb.and(cb.between(createTime, DateUtils.convertToDateTime(search.getStartTime()),
									DateUtils.convertToDateTime(search.getEndTime()))));
						}*/
					}
					query.where(where.toArray(new Predicate[] {}));
					return null;
				}
			});
		} else {
			//list = dao.findAll();
			logger.info("统计出库单数据，查询条件为null");
		}
		BigDecimal sumTareWeight = BigDecimal.ZERO;
		BigDecimal sumGrossWeight = BigDecimal.ZERO;
		BigDecimal sumNetweight = BigDecimal.ZERO;
		Long totalRecords = 0L;
		if (Collections3.isNotEmpty(list)) {
			totalRecords = Long.valueOf(list.size());
			for (DeliveryOrderEntity s : list) {
				if (null != s.getTareWeight()) {
					sumTareWeight = sumTareWeight.add(s.getTareWeight());
				}
				if (null != s.getGrossWeight()) {
					sumGrossWeight = sumGrossWeight.add(s.getGrossWeight());
				}
				if (null != s.getNetWeight()) {
					sumNetweight = sumNetweight.add(s.getNetWeight());
				}
			}
		}
		return new DeliveryCountDto(sumTareWeight, sumGrossWeight, sumNetweight, totalRecords);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void createDeliverOrder(DeliveryOrderEntity deliveryOrder) {
		Preconditions.checkNotNull(deliveryOrder, "deliveryOrder为null");
		DeliveryOrderEntity d = this.save(deliveryOrder);
		if (LevelEnum.isNormal(d.getLevel().getValue())) {
			this.createQrCodeUrl(d);
			this.save(d);
		}
		operateLogService.save(new OperateLogEntity("创建出库单id:" + d.getId(), OperateTypeEnum.CREATE, d.getId(),
				LogTypeEnum.DELIVERY, d.getCreateBy(), new Date(), d.getTenantCode()));
	}

	private void createQrCodeUrl(DeliveryOrderEntity d) {
		//生成二维码图片
		try {
			String content = baseUrl + "/scanResult/" + d.getId();
			String tenantCode = d.getTenantCode();
			String yyyyMM = DateUtils.formatDate(DateUtils.getCurrentDateTime(), "yyyyMM");
			String fileName = tenantCode + "_" + d.getId() + ".jpg";
			String imgUrl = middleUrl + "/" + tenantCode + "/" + yyyyMM + "/" + fileName;
			boolean ans = QrCodeGenWrapper.of(content).setW(300).setDrawBgColor(0xffffffff).setPadding(0)
					.setLogoStyle(QrCodeOptions.LogoStyle.ROUND).asFile(imgUrl);
			Preconditions.checkState(ans, "生成二维码图片失败");
			String url = ossService.uploadFileAndDeleteLocalFile(fileName, imgUrl);
			d.setQrcodeUrl(url);
		} catch (Exception e) {
			logger.info("生成二维码图片异常", e);
			throw new RuntimeException("生成二维码图片异常", e);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveDelivery(DeliveryOrderEntity delivery, List<DeliveryStandardEntity> needDelete, String logContent,
			OperateTypeEnum type, String operate) {
		if (StringUtils.isBlank(delivery.getQrcodeUrl()) && LevelEnum.isNormal(delivery.getLevel().getValue())) {
			this.createQrCodeUrl(delivery);
		}
		this.save(delivery);
		if (Collections3.isNotEmpty(needDelete)) {
			for (DeliveryStandardEntity d : needDelete) {
				deliveryStandardService.delete(d);
			}
		}
		operateLogService.save(new OperateLogEntity(logContent, type, delivery.getId(), LogTypeEnum.DELIVERY, operate,
				new Date(), delivery.getTenantCode()));
	}
}

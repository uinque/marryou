package com.marryou.metadata.service.impl;

import com.google.common.base.Preconditions;
import com.marryou.commons.utils.collections.Collections3;
import com.marryou.commons.utils.qrcode.wrapper.QrCodeGenWrapper;
import com.marryou.commons.utils.qrcode.wrapper.QrCodeOptions;
import com.marryou.commons.utils.time.DateUtils;
import com.marryou.metadata.entity.DeliveryOrderEntity;
import com.marryou.metadata.service.DeliveryService;
import com.marryou.metadata.service.QrCodeService;
import com.marryou.metadata.service.ailiyun.OSSService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by xph on 2018/11/8.
 */
@Service
public class QrCodeServiceImpl implements QrCodeService {

	private static final Logger logger = LoggerFactory.getLogger(QrCodeServiceImpl.class);

	@Value("${base.domain.url}")
	private String baseUrl;
	@Value("${img.middle.url}")
	private String middleUrl;

	@Autowired
	private DeliveryService deliveryService;
	@Autowired
	private OSSService ossService;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void batchCreateQrCode() {
		List<DeliveryOrderEntity> deliveryOrderList = (List<DeliveryOrderEntity>)deliveryService.findAll();
		Preconditions.checkState(Collections3.isNotEmpty(deliveryOrderList), "查无出库单数据");
		//生成二维码图片,关联二维码路径
		deliveryOrderList.forEach(deliveryOrder -> {
			try {
				String content = baseUrl + "/scanResult/" + deliveryOrder.getId();
				String tenantCode = deliveryOrder.getTenantCode();
				String yyyyMM = DateUtils.formatDate(DateUtils.getCurrentDateTime(), "yyyyMM");
				String fileName = tenantCode + "_" + deliveryOrder.getId() + ".jpg";
				String imgUrl = middleUrl + "/" + tenantCode + "/" + yyyyMM + "/" + fileName;
				boolean ans = QrCodeGenWrapper.of(content).setW(300).setDrawBgColor(0xffffffff).setPadding(0)
                        .setLogoStyle(QrCodeOptions.LogoStyle.ROUND).asFile(imgUrl);
				Preconditions.checkState(ans, "生成二维码图片失败");
				String url = ossService.uploadFileAndDeleteLocalFile(fileName, imgUrl);
				deliveryOrder.setQrcodeUrl(url);
				deliveryService.save(deliveryOrder);
			} catch (Exception e) {
				logger.info("根据出库单批量生成二维码，上传OSS异常", e);
				throw new RuntimeException("根据出库单批量生成二维码，上传OSS异常", e);
			}
		});
		logger.info("总共生成了{}张二维码图片，上传到OSS");
	}
}

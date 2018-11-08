package com.marryou.metadata.service;

/**
 * @author xph
 * @date 2018/11/8
 */
public interface QrCodeService {

	/**
	 * 根据出库单批量生成二维码，上传OSS
	 */
	void batchCreateQrCode();
}

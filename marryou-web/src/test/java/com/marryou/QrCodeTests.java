package com.marryou;

import com.marryou.commons.utils.qrcode.wrapper.QrCodeGenWrapper;
import com.marryou.commons.utils.qrcode.wrapper.QrCodeOptions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QrCodeTests {

	private static final Logger logger = LoggerFactory.getLogger(QrCodeTests.class);

	private String msg = "http://www.yicaijituan.cn/scanResult/1696";

	@Test
	public void testGenWxQrcode() {
		File path = null;
		try {
			path = new File(ResourceUtils.getURL("classpath:").getPath());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if (!path.exists())
			path = new File("");
		System.out.println("path:" + path.getAbsolutePath());

		// 根据本地文件生成待logo的二维码
		try {
			//String logo = path.getAbsolutePath()+"/static/images/logo.jpg";
			boolean ans = QrCodeGenWrapper.of(msg).setW(300)
					//.setDrawPreColor(0xffff0000)
					.setDrawBgColor(0xffffffff).setPadding(0)
					//.setLogo(logo)
					.setLogoStyle(QrCodeOptions.LogoStyle.ROUND)
					//.setLogoBgColor(0xff00ff00)
					//.setLogoBorder(true)
					.asFile("src/main/resources/static/images/1696.jpg");
			System.out.println(ans);
		} catch (Exception e) {
			System.out.println("create qrcode error! e: " + e);
		}
	}
}

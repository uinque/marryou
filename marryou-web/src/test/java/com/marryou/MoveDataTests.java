package com.marryou;

import com.marryou.metadata.service.MoveService;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MoveDataTests {

	private static final Logger logger = LoggerFactory.getLogger(MoveDataTests.class);

	@Autowired
	private MoveService moveService;


	@Test
	public void testMoveData() {
		StopWatch watch = new StopWatch();
		/*watch.start();
		logger.info("############【数据迁移】——模板数据迁移开始############");
		moveService.moveStandardData();
		logger.info("############【数据迁移】——模板数据迁移结束，耗时：{}############", watch.getTime());
		watch.stop();*/
		StopWatch watch2 = new StopWatch();
		watch2.start();
		logger.info("############【数据迁移】——出库单数据迁移开始############");
		moveService.moveDeliveryStandardData();
		logger.info("############【数据迁移】——出库单数据迁移结束，耗时：{}############", watch2.getTime());
		watch2.stop();
	}
}

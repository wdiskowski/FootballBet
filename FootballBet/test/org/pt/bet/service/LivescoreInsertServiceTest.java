package org.pt.bet.service;

import javax.inject.Inject;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@Transactional
public class LivescoreInsertServiceTest extends AbstractJUnit4SpringContextTests {

	@Inject
	private ILivescoreInsertDataService livescoreInsertDataService;


	@Test
	public void testInsertData() {
		String result = livescoreInsertDataService.insertData();
		System.out.println(result);
		Assert.assertTrue(result.length() > 0);
	}

}
package org.pt.bet.service;

import javax.annotation.Resource;

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
public class GameServiceTest extends AbstractJUnit4SpringContextTests {

	@Resource(name = "GameService")
	private IGameService gameService;


	@Test
	public void testFindById() {
		Assert.assertNotNull(gameService.getMaxGameDate());
	}

}
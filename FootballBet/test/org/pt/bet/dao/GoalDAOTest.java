package org.pt.bet.dao;

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
public class GoalDAOTest extends AbstractJUnit4SpringContextTests {

	@Resource(name = "GoalDAO")
	private IGoalDAO goalDAO;


	@Test
	public void testFindById() {
		Assert.assertNotNull(goalDAO.findById(30000));
	}

}
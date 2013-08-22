package org.pt.bet.service;

import java.io.Serializable;

import javax.annotation.Resource;

import org.pt.bet.dao.IGoalDAO;
import org.pt.bet.domain.Goal;

public class GoalServiceImpl implements IGoalService {

	
    @Resource
	private IGoalDAO goalDAO;
	
	@Override
	public Serializable save(Goal goal) {
		return goalDAO.save(goal);
	}

}

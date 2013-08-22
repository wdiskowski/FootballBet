package org.pt.bet.dao;

import java.util.List;

import org.pt.bet.domain.Goal;

public interface IGoalDAO extends IAbstractDAO {







	public abstract Goal findById(java.lang.Integer id);

	public abstract List<Goal> findByPMinute(Integer PMinute);

	public abstract List<Goal> findByTeamNr(Byte teamNr);

	public abstract List<Goal> findByPlayer(String player);

	public abstract List<Goal> findByScore(String score);

	public abstract List<Goal> findByPenalty(Byte penalty);
	
	public abstract List<Goal> findAll();


}
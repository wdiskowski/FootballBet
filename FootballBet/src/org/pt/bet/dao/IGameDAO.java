package org.pt.bet.dao;

import java.util.Date;
import java.util.List;

import org.pt.bet.domain.Game;

public interface IGameDAO extends IAbstractDAO {

	public abstract Game findById(java.lang.Integer id);

	public abstract List<Game> findByScore(String score);

	public abstract List<Game> findByDuration(String duration);

	public abstract List<Game> findByTournamentLevel(Integer level);

	public abstract Date getMaxGdate();
	
	public abstract List<Game> findAll();


}
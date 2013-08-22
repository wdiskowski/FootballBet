package org.pt.bet.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.pt.bet.dao.IGameDAO;
import org.pt.bet.domain.Game;




public class GameServiceImpl implements IGameService {

    static Logger logger = Logger.getLogger(GameServiceImpl.class.getName());

    @Resource
	private IGameDAO gameDAO;

	
    
    @Override
	public List<Game> findGamesByTournamentLevel(Integer level) {
		return gameDAO.findByTournamentLevel(level);
	}

	@Override
	public Date getMaxGameDate() {
		return gameDAO.getMaxGdate();
	}


	@Override
	public Serializable save(Game game) {
		return gameDAO.save(game);
	}
	
}

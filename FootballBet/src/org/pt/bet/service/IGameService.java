package org.pt.bet.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.pt.bet.domain.Game;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.REPEATABLE_READ)
public interface IGameService {
	
	public abstract List<Game> findGamesByTournamentLevel(Integer level);
	
	public abstract Date getMaxGameDate();
	
	public abstract Serializable save(Game game);
	
}
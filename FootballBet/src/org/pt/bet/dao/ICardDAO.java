package org.pt.bet.dao;

import java.util.List;

import org.pt.bet.domain.Card;
import org.pt.bet.domain.ECardType;

public interface ICardDAO extends IAbstractDAO {

	public abstract Card findById(java.lang.Integer id);

	public abstract List<Card> findByPMinute(Integer PMinute);

	public abstract List<Card> findByType(ECardType type);

	public abstract List<Card> findByPlayer(String player);

	public abstract List<Card> findByTeamNr(Byte teamNr);
	
	public abstract List<Card> findAll();


}
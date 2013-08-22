package org.pt.bet.service;

import java.io.Serializable;

import javax.annotation.Resource;

import org.pt.bet.dao.ICardDAO;
import org.pt.bet.domain.Card;

public class CardServiceImpl implements ICardService {

	
    @Resource
	private ICardDAO cardDAO;
	
	@Override
	public Serializable save(Card card) {
		return cardDAO.save(card);
	}

}

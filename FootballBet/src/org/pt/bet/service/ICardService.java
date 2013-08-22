package org.pt.bet.service;

import java.io.Serializable;

import org.pt.bet.domain.Card;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.REPEATABLE_READ)
public interface ICardService {
	
	public abstract Serializable save(Card card);
	
}
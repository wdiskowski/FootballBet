package org.pt.bet.service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation=Propagation.NOT_SUPPORTED)
public interface ILivescoreInsertDataService {

	public String insertData();

}
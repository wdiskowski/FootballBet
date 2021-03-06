package org.pt.bet.service;

import java.util.List;

import org.pt.bet.domain.Country;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * Die CustomerService-Schnittstelle.
 * @author g. bachlmayer
 *
 */
@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.REPEATABLE_READ)
public interface ICountryService {
	
	public List<Country> findAllCountries();
	
	public Country findById(Integer id);

}
package org.pt.bet.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.pt.bet.dao.ICountryDAO;
import org.pt.bet.domain.Country;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class CountryServiceImpl implements ICountryService {

	static Logger logger = Logger.getLogger(CountryServiceImpl.class.getName());

	@Resource
	private ICountryDAO countryDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Country> findAllCountries() {
		return countryDAO.findAll();
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public Country findById(Integer id) {
		return countryDAO.findById(id);
	}
	
}

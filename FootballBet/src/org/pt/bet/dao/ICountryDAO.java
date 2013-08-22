package org.pt.bet.dao;

import java.util.List;

import org.pt.bet.domain.Country;

public interface ICountryDAO extends IAbstractDAO {

	public abstract Country findById(java.lang.Integer id);

	public abstract List<Country> findByName(String name);

	public abstract List<Country> findAll();

}
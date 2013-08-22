package org.pt.bet.dao;

import java.util.List;

import org.pt.bet.domain.Continent;

public interface IContinentDAO extends IAbstractDAO {

	public abstract Continent findById(java.lang.Integer id);

	public abstract List<Continent> findByName(String name);
	
	public List<Continent> findAll();

}
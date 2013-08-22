package org.pt.bet.dao;

import java.util.List;

import org.pt.bet.domain.Tournament;

public interface ITournamentDAO extends IAbstractDAO {

	public abstract Tournament findById(java.lang.Integer id);
	
	public abstract List<Tournament> findByName(String name);

	public abstract List<Tournament> findByLevel(Integer level);

	public abstract List<Tournament> findAll();

}
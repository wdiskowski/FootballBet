package org.pt.bet.dao;

import java.util.List;

import org.pt.bet.domain.CompetitionType;

public interface ICompetitionTypeDAO extends IAbstractDAO {


	public abstract CompetitionType findById(java.lang.Integer id);

	public abstract List<CompetitionType> findByName(String name);

	public abstract List<CompetitionType> findByDescription(String description);

	public abstract List<CompetitionType> findAll();
}
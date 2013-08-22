package org.pt.bet.dao;

import java.util.List;

import org.pt.bet.domain.Country;
import org.pt.bet.domain.Team;

public interface ITeamDAO extends IAbstractDAO {

	public abstract Team findById(java.lang.Integer id);

	public abstract List<Team> findByName(String name);

	public abstract List<Team> findByDescription(String description);

	public abstract List<Team> findByCountryId(Integer countryId);

	public abstract List<Team> findByNameAndCountry(String name, Country country);

	public abstract List<Team> findAll();

}
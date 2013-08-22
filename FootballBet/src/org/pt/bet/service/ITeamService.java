package org.pt.bet.service;

import java.util.List;

import org.pt.bet.domain.Country;
import org.pt.bet.domain.Team;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * Die CustomerService-Schnittstelle.
 * @author g. bachlmayer
 *
 */
@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.REPEATABLE_READ)
public interface ITeamService {
	
	public Team getTeam(Country country, String teamName, boolean createNew);

	public List<Team> findByCountryId(Integer countryId);
	
}
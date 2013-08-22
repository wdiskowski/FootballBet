package org.pt.bet.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.pt.bet.dao.ITeamDAO;
import org.pt.bet.domain.Country;
import org.pt.bet.domain.Team;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;



// TODO Javadoc
public class TeamServiceImpl implements ITeamService {

    static Logger logger = Logger.getLogger(TeamServiceImpl.class.getName());
    
    @Resource
    private ITeamDAO teamDAO;

	public void setTeamDAO(ITeamDAO teamDAO) {
		this.teamDAO = teamDAO;
	}


    
	@Transactional(propagation = Propagation.REQUIRED)
	public Team getTeam(Country country, String teamName, boolean createNew) {
		Team team = null;
		Team teamExample = new Team(country, teamName);
		List<Team> teamList = teamDAO.findByNameAndCountry(teamName, country);
		if (teamList.size() > 0) {
			team = teamList.get(0);
		} else {
			team = teamExample;
			teamDAO.save(team);
		}

		return team;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Team> findByCountryId(Integer countryId) {
		return teamDAO.findByCountryId(countryId);
	}
	
}

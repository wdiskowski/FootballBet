package org.pt.bet.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.pt.bet.dao.ITournamentDAO;
import org.pt.bet.domain.Tournament;



// TODO Javadoc
public class TournamentServiceImpl implements ITournamentService {

    static Logger logger = Logger.getLogger(TournamentServiceImpl.class.getName());

    @Resource
    private ITournamentDAO tournamentDAO;

	@Override
	public List<Tournament> findAll() {
		return tournamentDAO.findAll();
	}
    
	
}

package org.pt.bet.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pt.bet.domain.FailedPenalty;
import org.springframework.transaction.annotation.Transactional;

/**
 * A data access object (DAO) providing persistence and search support for
 * FailedPenalty entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see org.pt.bet.domain.FailedPenalty
 * @author MyEclipse Persistence Tools
 */

@Transactional
public class FailedPenaltyDAOImpl extends AbstractDAO implements IFailedPenaltyDAO {
	
	// property constants
	public static final String PLAYER = "player";
	public static final String _PMINUTE = "PMinute";
	public static final String TEAM_NR = "teamNr";
	
	private static final Log log = LogFactory.getLog(FailedPenaltyDAOImpl.class);
	
	protected void initDao() {
		log.debug("init FailedPenaltyDAO");
	}



	/* (non-Javadoc)
	 * @see org.pt.bet.dao.FailedPenaltyDAO#findById(java.lang.Integer)
	 */
	public FailedPenalty findById(java.lang.Integer id) {
		return findById(FailedPenalty.class, id);
	}



	/* (non-Javadoc)
	 * @see org.pt.bet.dao.FailedPenaltyDAO#findByPlayer(java.lang.Object)
	 */
	public List<FailedPenalty> findByPlayer(String player) {
		return findByProperty(FailedPenalty.class, PLAYER, player);
	}

	/* (non-Javadoc)
	 * @see org.pt.bet.dao.FailedPenaltyDAO#findByPMinute(java.lang.Object)
	 */
	public List<FailedPenalty> findByPMinute(Integer PMinute) {
		return findByProperty(FailedPenalty.class, _PMINUTE, PMinute);
	}

	/* (non-Javadoc)
	 * @see org.pt.bet.dao.FailedPenaltyDAO#findByTeamNr(java.lang.Object)
	 */
	public List<FailedPenalty> findByTeamNr(Byte teamNr) {
		return findByProperty(FailedPenalty.class, TEAM_NR, teamNr);
	}

	/* (non-Javadoc)
	 * @see org.pt.bet.dao.FailedPenaltyDAO#findAll()
	 */
	public List<FailedPenalty> findAll() {
		return findAll(FailedPenalty.class);
	}

}
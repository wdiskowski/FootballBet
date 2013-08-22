package org.pt.bet.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pt.bet.domain.Goal;
import org.springframework.transaction.annotation.Transactional;

/**
 * A data access object (DAO) providing persistence and search support for Goal
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see org.pt.bet.domain.Goal
 * @author MyEclipse Persistence Tools
 */

@Transactional
public class GoalDAOImpl extends AbstractDAO implements IGoalDAO {
	// property constants
	public static final String _PMINUTE = "PMinute";
	public static final String TEAM_NR = "teamNr";
	public static final String PLAYER = "player";
	public static final String SCORE = "score";
	public static final String PENALTY = "penalty";
	
	private static final Log log = LogFactory.getLog(GoalDAOImpl.class);
	
	protected void initDao() {
		log.debug("init GoalDAO");
	}



	/* (non-Javadoc)
	 * @see org.pt.bet.dao.IGoalDAO#findById(java.lang.Integer)
	 */
	public Goal findById(java.lang.Integer id) {
		return findById(Goal.class, id);
	}



	/* (non-Javadoc)
	 * @see org.pt.bet.dao.IGoalDAO#findByPMinute(java.lang.Object)
	 */
	public List<Goal> findByPMinute(Integer PMinute) {
		return findByProperty(Goal.class, _PMINUTE, PMinute);
	}

	/* (non-Javadoc)
	 * @see org.pt.bet.dao.IGoalDAO#findByTeamNr(java.lang.Object)
	 */
	public List<Goal> findByTeamNr(Byte teamNr) {
		return findByProperty(Goal.class, TEAM_NR, teamNr);
	}

	/* (non-Javadoc)
	 * @see org.pt.bet.dao.IGoalDAO#findByPlayer(java.lang.Object)
	 */
	public List<Goal> findByPlayer(String player) {
		return findByProperty(Goal.class, PLAYER, player);
	}

	/* (non-Javadoc)
	 * @see org.pt.bet.dao.IGoalDAO#findByScore(java.lang.Object)
	 */
	public List<Goal> findByScore(String score) {
		return findByProperty(Goal.class, SCORE, score);
	}

	/* (non-Javadoc)
	 * @see org.pt.bet.dao.IGoalDAO#findByPenalty(java.lang.Object)
	 */
	public List<Goal> findByPenalty(Byte penalty) {
		return findByProperty(Goal.class, PENALTY, penalty);
	}

	/* (non-Javadoc)
	 * @see org.pt.bet.dao.IGoalDAO#findAll()
	 */
	public List<Goal> findAll() {
		return findAll(Goal.class);
	}


}
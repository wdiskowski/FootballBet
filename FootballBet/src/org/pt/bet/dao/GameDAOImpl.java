package org.pt.bet.dao;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pt.bet.domain.Game;
import org.springframework.transaction.annotation.Transactional;

/**
 * A data access object (DAO) providing persistence and search support for Game
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see org.pt.bet.domain.Game
 * @author MyEclipse Persistence Tools
 */

@Transactional
public class GameDAOImpl extends AbstractDAO implements IGameDAO {
	
	// property constants
	public static final String SCORE = "score";
	public static final String DURATION = "duration";
	public static final String TOURNAMENT_LEVEL = "tournament.level";
	public static final String G_DATE = "GDate";
	
	private static final Log log = LogFactory.getLog(GameDAOImpl.class);
	protected void initDao() {
		log.debug("init GameDAO");
	}


	/* (non-Javadoc)
	 * @see org.pt.bet.dao.IGameDAO#findById(java.lang.Integer)
	 */
	public Game findById(java.lang.Integer id) {
		return findById(Game.class, id);
	}


	/* (non-Javadoc)
	 * @see org.pt.bet.dao.IGameDAO#findByScore(java.lang.Object)
	 */
	public List<Game> findByScore(String score) {
		return findByProperty(Game.class, SCORE, score);
	}

	/* (non-Javadoc)
	 * @see org.pt.bet.dao.IGameDAO#findByDuration(java.lang.Object)
	 */
	public List<Game> findByDuration(String duration) {
		return findByProperty(Game.class, DURATION, duration);
	}
	/* (non-Javadoc)
	 * @see org.pt.bet.dao.IGameDAO#findByDuration(java.lang.Object)
	 */

	public List<Game> findByTournamentLevel(Integer level) {
		return findByProperty(Game.class, TOURNAMENT_LEVEL, level);
	}

	/* (non-Javadoc)
	 * @see org.pt.bet.dao.IGameDAO#findAll()
	 */
	public List<Game> findAll() {
		return findAll(Game.class);
	}
	
	public Date getMaxGdate() {
		log.debug("finding max Game date");
		try {
			String queryString = "SELECT MAX(model." + G_DATE + ") from Game as model";
			return (Date)getHibernateTemplate().find(queryString).get(0);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
}
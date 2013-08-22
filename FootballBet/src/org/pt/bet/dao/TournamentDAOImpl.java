package org.pt.bet.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pt.bet.domain.Tournament;
import org.springframework.transaction.annotation.Transactional;

/**
 * A data access object (DAO) providing persistence and search support for
 * Tournament entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see org.pt.bet.domain.Tournament
 * @author MyEclipse Persistence Tools
 */

@Transactional
public class TournamentDAOImpl extends AbstractDAO implements ITournamentDAO {
	
	// property constants
	public static final String NAME = "name";
	public static final String LEVEL = "level";
	
	private static final Log log = LogFactory.getLog(TournamentDAOImpl.class);
	
	protected void initDao() {
		log.debug("init TournamentDAO");
	}

	/* (non-Javadoc)
	 * @see org.pt.bet.dao.ITournamentDAO#findById(java.lang.Integer)
	 */
	public Tournament findById(java.lang.Integer id) {
		return findById(Tournament.class, id);
	}

	/* (non-Javadoc)
	 * @see org.pt.bet.dao.ITournamentDAO#findByName(java.lang.Object)
	 */
	public List<Tournament> findByName(String name) {
		return findByProperty(Tournament.class, NAME, name);
	}

	/* (non-Javadoc)
	 * @see org.pt.bet.dao.ITournamentDAO#findByLevel(java.lang.Object)
	 */
	public List<Tournament> findByLevel(Integer level) {
		return findByProperty(Tournament.class, LEVEL, level);
	}

	/* (non-Javadoc)
	 * @see org.pt.bet.dao.ITournamentDAO#findAll()
	 */
	public List<Tournament> findAll() {
		return findAll(Tournament.class);
	}

}
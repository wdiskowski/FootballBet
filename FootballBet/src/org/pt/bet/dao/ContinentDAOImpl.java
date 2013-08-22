package org.pt.bet.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pt.bet.domain.Continent;
import org.springframework.transaction.annotation.Transactional;

/**
 * A data access object (DAO) providing persistence and search support for
 * Continent entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see org.pt.bet.domain.Continent
 * @author MyEclipse Persistence Tools
 */

@Transactional
public class ContinentDAOImpl extends AbstractDAO implements IContinentDAO {
	
	// property constants
	public static final String NAME = "name";
	
	private static final Log log = LogFactory.getLog(ContinentDAOImpl.class);
	
	protected void initDao() {
		log.debug("init ContinentDAO");
	}


	/* (non-Javadoc)
	 * @see org.pt.bet.dao.ContinentDAO#findById(java.lang.Integer)
	 */
	public Continent findById(java.lang.Integer id) {
		return findById(Continent.class, id);
	}


	/* (non-Javadoc)
	 * @see org.pt.bet.dao.ContinentDAO#findByName(java.lang.Object)
	 */
	public List<Continent> findByName(String name) {
		return findByProperty(Continent.class, NAME, name);
	}

	/* (non-Javadoc)
	 * @see org.pt.bet.dao.ContinentDAO#findAll()
	 */
	public List<Continent> findAll() {
		return findAll(Continent.class);
	}


}
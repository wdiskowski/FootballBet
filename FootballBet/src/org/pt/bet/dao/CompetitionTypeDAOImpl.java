package org.pt.bet.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pt.bet.domain.CompetitionType;
import org.springframework.transaction.annotation.Transactional;

/**
 * A data access object (DAO) providing persistence and search support for
 * CompetitionType entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see org.pt.bet.domain.CompetitionType
 * @author MyEclipse Persistence Tools
 */

@Transactional
public class CompetitionTypeDAOImpl extends AbstractDAO implements ICompetitionTypeDAO {
	
	// property constants
	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	private static final Log log = LogFactory.getLog(CompetitionTypeDAOImpl.class);
	protected void initDao() {
		log.debug("init CompetitionTypeDAO");
	}



	/* (non-Javadoc)
	 * @see org.pt.bet.dao.ICompetitionTypeDAO#findById(java.lang.Integer)
	 */
	public CompetitionType findById(java.lang.Integer id) {
		return findById(CompetitionType.class, id);
	}



	/* (non-Javadoc)
	 * @see org.pt.bet.dao.ICompetitionTypeDAO#findByName(java.lang.Object)
	 */
	public List<CompetitionType> findByName(String name) {
		return findByProperty(CompetitionType.class, NAME, name);
	}

	/* (non-Javadoc)
	 * @see org.pt.bet.dao.ICompetitionTypeDAO#findByDescription(java.lang.Object)
	 */
	public List<CompetitionType> findByDescription(String description) {
		return findByProperty(CompetitionType.class, DESCRIPTION, description);
	}

	/* (non-Javadoc)
	 * @see org.pt.bet.dao.ICompetitionTypeDAO#findAll()
	 */
	public List<CompetitionType> findAll() {
		return findAll(CompetitionType.class);
	}

}
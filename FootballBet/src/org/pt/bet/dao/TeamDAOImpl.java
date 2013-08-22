package org.pt.bet.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pt.bet.domain.Country;
import org.pt.bet.domain.Team;
import org.springframework.transaction.annotation.Transactional;

/**
 * A data access object (DAO) providing persistence and search support for Team
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see org.pt.bet.domain.Team
 * @author MyEclipse Persistence Tools
 */

@Transactional
public class TeamDAOImpl extends AbstractDAO implements ITeamDAO {
	// property constants
	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String COUNTRY_ID = "country.pk";
	
	private static final Log log = LogFactory.getLog(TeamDAOImpl.class);
	
	protected void initDao() {
		log.debug("init TeamDAO");
	}

	/* (non-Javadoc)
	 * @see org.pt.bet.dao.ITeamDAO#findById(java.lang.Integer)
	 */
	public Team findById(java.lang.Integer id) {
		return findById(Team.class, id);
	}

	/* (non-Javadoc)
	 * @see org.pt.bet.dao.ITeamDAO#findByName(java.lang.Object)
	 */
	public List<Team> findByName(String name) {
		return findByProperty(Team.class, NAME, name);
	}

	/* (non-Javadoc)
	 * @see org.pt.bet.dao.ITeamDAO#findByDescription(java.lang.Object)
	 */
	public List<Team> findByDescription(String description) {
		return findByProperty(Team.class, DESCRIPTION, description);
	}

	/* (non-Javadoc)
	 * @see org.pt.bet.dao.ITeamDAO#findByCountryId(java.lang.Object)
	 */
	public List<Team> findByCountryId(Integer countryId) {
		if(Integer.valueOf(0).equals(countryId))
			return findAll();
		else
			return findByProperty(Team.class, COUNTRY_ID, countryId);
	}

	public List<Team> findByNameAndCountry(String name, Country country) {
		try {
			String queryString = "from Team as model where model.name"
					+ "= :name AND model.country= :country"
					+ " order by model.name";
			// Query " from Team " provide return List of Teams so cast is correct
			@SuppressWarnings("unchecked")
			List<Team> teamList = getHibernateTemplate().findByNamedParam(queryString, 
					new String[]{"name", "country"}, new Object[]{name, country});
			return teamList;
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see org.pt.bet.dao.ITeamDAO#findAll()
	 */
	public List<Team> findAll() {
		return findAll(Team.class);
	}

}
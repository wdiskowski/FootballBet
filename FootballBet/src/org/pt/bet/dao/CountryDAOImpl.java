package org.pt.bet.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pt.bet.domain.Country;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

/**
 * A data access object (DAO) providing persistence and search support for
 * Country entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see org.pt.bet.domain.Country
 * @author MyEclipse Persistence Tools
 */

@Transactional
@Configurable(value="CountryDAO")
public class CountryDAOImpl extends AbstractDAO implements ICountryDAO {
	
	// property constants
	public static final String NAME = "name";
	
	private static final Log log = LogFactory.getLog(CountryDAOImpl.class);
	
	protected void initDao() {
		log.debug("init CountryDAO");
	}

	/* (non-Javadoc)
	 * @see org.pt.bet.dao.CountryDAO#findById(java.lang.Integer)
	 */
	public Country findById(java.lang.Integer id) {
		return findById(Country.class, id);
	}


	/* (non-Javadoc)
	 * @see org.pt.bet.dao.CountryDAO#findByName(java.lang.Object)
	 */
	public List<Country> findByName(String name) {
		return findByProperty(Country.class, NAME, name);
	}

	/* (non-Javadoc)
	 * @see org.pt.bet.dao.CountryDAO#findAll()
	 */
	public List<Country> findAll() {
		return findAll(Country.class);
	}
}
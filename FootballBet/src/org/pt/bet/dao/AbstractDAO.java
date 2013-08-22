package org.pt.bet.dao;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract class AbstractDAO extends HibernateDaoSupport implements IAbstractDAO {

	private static Log log = LogFactory.getLog(AbstractDAO.class);
	
	@Resource(name = "sessionFactory")
	public void setDAOSessionFactory(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}

	/* (non-Javadoc)
	 * @see org.pt.bet.dao.IAbstractDAO#save(java.lang.Object)
	 */
	public Serializable save(Object transientInstance) {
		log.debug("saving " + transientInstance.getClass().getSimpleName() + " instance: ");
		try {
			Serializable pk = getHibernateTemplate().save(transientInstance);
			log.debug("save successful key: " + pk);
			return pk;
		} catch (RuntimeException re) {
			if(re instanceof DataIntegrityViolationException){
				log.debug("save failed", re);
			}else{
				log.error("save failed", re);
			}
			throw re;
		}
	}
	
	/* (non-Javadoc)
	 * @see org.pt.bet.dao.IAbstractDAO#saveOrUpdate(java.lang.Object)
	 */
	public void saveOrUpdate(Object transientInstance) {
		log.debug("saving or update " + transientInstance.getClass().getSimpleName() + " instance: ");
		try {
			getHibernateTemplate().saveOrUpdate(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}


	/* (non-Javadoc)
	 * @see org.pt.bet.dao.IAbstractDAO#delete(java.lang.Object)
	 */
	public void delete(Object persistentInstance) {
		log.debug("deleting " + persistentInstance.getClass().getSimpleName() + " instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}
	
	/* (non-Javadoc)
	 * @see org.pt.bet.dao.IAbstractDAO#findById(java.lang.Class, java.io.Serializable)
	 */
	public <T> T findById(Class<T> entityClass, Serializable id) {
		log.debug("getting " + entityClass.getSimpleName() + " instance with id: " + id);
		try {
			T instance = getHibernateTemplate().get(entityClass, id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	/* (non-Javadoc)
	 * @see org.pt.bet.dao.IAbstractDAO#findByExample(T)
	 */
	public <T> List<T> findByExample(T instance) {
		log.debug("finding " + instance.getClass().getSimpleName() + " instance by example");
		try {
			//Class of returned Objects equals parameter Class. So cast is correct
			@SuppressWarnings("unchecked")
			List<T> results = getHibernateTemplate().findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	protected <T> List<T> findByProperty(Class<T> entityClass, String propertyName, Object value) {
		String className = entityClass.getSimpleName();
		log.debug("finding " + className + " instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from " + className + " as model where model."
					+ propertyName + "= ?";
			// Class of returned Objects equals parameter Class. So cast is correct
			@SuppressWarnings("unchecked")
			List<T> results = getHibernateTemplate().find(queryString, value);
			return results;
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	protected <T> List<T> findByProperties(Class<T> entityClass, String[] propertyNames, Object[] values) {
		String className = entityClass.getSimpleName();
		log.debug("finding " + className + " instance with properties: " + Arrays.toString(propertyNames)
				+ ", values: " + Arrays.toString(values));
		try {
			String queryString = "from " + className + " as model where ";
			for(int i = 0; i<propertyNames.length; i++)
				queryString += "model."+ propertyNames[i] + "= ?"+ " and ";
			queryString=queryString.substring(0, queryString.length()-5);
			// Class of returned Objects equals parameter Class. So cast is correct
			@SuppressWarnings("unchecked")
			List<T> results = getHibernateTemplate().find(queryString, values);
			return results;
		} catch (RuntimeException re) {
			log.error("find by properties names failed", re);
			throw re;
		}
	}
	

	
	/* (non-Javadoc)
	 * @see org.pt.bet.dao.IAbstractDAO#findAll(java.lang.Class)
	 */
	protected <T> List<T> findAll(Class<T> entityClass) {
		log.debug("finding all AAttribute instances");
		try {
			String queryString = "from " + entityClass.getSimpleName();
			// Class of returned Object equals parameter Class. So cast is correct
			@SuppressWarnings("unchecked")
			List<T> results = getHibernateTemplate().find(queryString);
			return results;
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	

	/* (non-Javadoc)
	 * @see org.pt.bet.dao.IAbstractDAO#merge(T)
	 */
	public <T> T merge(T detachedInstance) {
		log.debug("merging " + detachedInstance.getClass().getSimpleName() + " instance");
		try {
			T result = getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see org.pt.bet.dao.IAbstractDAO#attachDirty(java.lang.Object)
	 */
	public void attachDirty(Object instance) {
		log.debug("attaching dirty " + instance.getClass().getSimpleName() + " instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see org.pt.bet.dao.IAbstractDAO#attachClean(java.lang.Object)
	 */
	public void attachClean(Object instance) {
		log.debug("attaching clean " + instance.getClass().getSimpleName() + " instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

}
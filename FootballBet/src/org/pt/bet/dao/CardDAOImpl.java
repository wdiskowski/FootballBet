package org.pt.bet.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pt.bet.domain.Card;
import org.pt.bet.domain.ECardType;
import org.springframework.transaction.annotation.Transactional;

/**
 * A data access object (DAO) providing persistence and search support for Card
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see org.pt.bet.domain.Card
 * @author MyEclipse Persistence Tools
 */

@Transactional
public class CardDAOImpl extends AbstractDAO implements ICardDAO {
	
	// property constants
	public static final String _PMINUTE = "PMinute";
	public static final String TYPE = "type";
	public static final String PLAYER = "player";
	public static final String TEAM_NR = "teamNr";
	
	private static final Log log = LogFactory.getLog(CardDAOImpl.class);
	protected void initDao() {
		log.debug("init CardDAO");
	}



	/* (non-Javadoc)
	 * @see org.pt.bet.dao.ICardDAO#findById(java.lang.Integer)
	 */
	public Card findById(java.lang.Integer id) {
		return findById(Card.class, id);
	}

	/* (non-Javadoc)
	 * @see org.pt.bet.dao.ICardDAO#findByPMinute(java.lang.Object)
	 */
	public List<Card> findByPMinute(Integer PMinute) {
		return findByProperty(Card.class, _PMINUTE, PMinute);
	}

	/* (non-Javadoc)
	 * @see org.pt.bet.dao.ICardDAO#findByType(java.lang.Object)
	 */
	public List<Card> findByType(ECardType type) {
		return findByProperty(Card.class, TYPE, type);
	}

	/* (non-Javadoc)
	 * @see org.pt.bet.dao.ICardDAO#findByPlayer(java.lang.Object)
	 */
	public List<Card> findByPlayer(String player) {
		return findByProperty(Card.class, PLAYER, player);
	}

	/* (non-Javadoc)
	 * @see org.pt.bet.dao.ICardDAO#findByTeamNr(java.lang.Object)
	 */
	public List<Card> findByTeamNr(Byte teamNr) {
		return findByProperty(Card.class, TEAM_NR, teamNr);
	}

	/* (non-Javadoc)
	 * @see org.pt.bet.dao.ICardDAO#findAll()
	 */
	public List<Card> findAll() {
		return findAll(Card.class);
	}

}
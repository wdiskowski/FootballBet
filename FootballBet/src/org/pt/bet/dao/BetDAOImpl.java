package org.pt.bet.dao;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.pt.bet.domain.ECardType;
import org.pt.bet.web.dto.BetResultDTO;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;



@Transactional(propagation=Propagation.SUPPORTS)
public class BetDAOImpl extends HibernateDaoSupport implements IBetDAO {
	private static final Log log = LogFactory.getLog(BetDAOImpl.class);
	protected void initDao() {
		log.debug("init BetDAO");
	}
	
	@Resource(name = "sessionFactory")
	public void setDAOSessionFactory(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}
	
	public BetResultDTO findGoalCountRate(final Integer level, final String competitionType,
			final Integer fromMinute, final Integer toMinute, final Integer goalCount, final Integer countryKey,
			final Integer teamKey) {
		final String queryString = 
				"SELECT alias1.basis/alias1.hits AS kf, alias1.basis " +
				"FROM " +
				"( " +
				"	SELECT " +
				"		(" +
				"		SELECT COUNT(g1.pk) " +
				"		FROM game g1, tournament tn1, competition_type ct1 " +
				"		WHERE tn1.pk=g1.tn_fk " +
				"			AND tn1.level <= :level " +
				"			AND tn1.cmt_fk=ct1.pk " +
				"			AND (ct1.name = :competitionType OR COALESCE(:competitionType, '') = '') " +
				"			AND (0 = :countryKey OR tn1.cn_fk = :countryKey) " +
				"			AND (0 = :teamKey " +
				"					OR " +
				"					( " +
				"					g1.tm_fk1 = :teamKey " +
				"					OR g1.tm_fk2 = :teamKey " +
				"					) " +
				"				) " +
				"		) AS basis, " +
				"		COUNT(g.pk) AS hits " +
				"	FROM game g, tournament tn, competition_type ct " +
				"	WHERE tn.pk=g.tn_fk " +
				"		AND tn.level <= :level  " +
				"		AND tn.cmt_fk=ct.pk " +
				"		AND (ct.name = :competitionType  OR COALESCE(:competitionType, '') = '') " +
				"		AND (0 = :countryKey OR tn.cn_fk = :countryKey) " +
				"		AND (0 = :teamKey " +
				"				OR " +
				"				( " +
				"				g.tm_fk1 = :teamKey " +
				"				OR g.tm_fk2 = :teamKey " +
				"				) " +
				"			) " +
				"		AND " +
				"		(" +
				"		SELECT COUNT(gl.pk) " +
				"		FROM goal gl " +
				"		WHERE gl.gm_fk=g.pk " +
				"			AND gl.p_minute > :fromMinute " +
				"			AND gl.p_minute < :toMinute " +
				"		) < :goalCount " +
				") alias1";
		Object[] resultArray = getHibernateTemplate().execute(
				new HibernateCallback<Object[]>(){
					public Object[] doInHibernate(Session session){
						Query sqlQuery=session.createSQLQuery(queryString);
						sqlQuery.setInteger("level", level);
						sqlQuery.setString("competitionType", competitionType);
						sqlQuery.setInteger("fromMinute", fromMinute);
						sqlQuery.setInteger("toMinute", toMinute);
						sqlQuery.setInteger("goalCount", goalCount);
						sqlQuery.setInteger("countryKey", countryKey);
						sqlQuery.setInteger("teamKey", teamKey);
						Object[] result = (Object[])sqlQuery.uniqueResult();
						return result;
					}
				}
		);
		return new BetResultDTO(level, fromMinute, (Number)resultArray[0], (Number)resultArray[1]);
	}
	
	public BetResultDTO findGoalCountRateWithAddCondition(final Integer level, final String competitionType,
			final Integer fromMinute, final Integer toMinute, final Integer goalCount, final Integer countryKey,
			final Integer teamKey, final Integer conditionFromMinute, final Integer conditionToMinute, 
			final Integer conditionGoalCountFrom, final Integer conditionGoalCountTo) {
		
		final StringBuilder basicQuery = new StringBuilder();
		
		basicQuery
			.append("SELECT ")
			.append("	gm.pk ")
			.append("FROM ")
			.append("	game gm ")
			.append("	LEFT OUTER JOIN ")
			.append("	goal gl ")
			.append("	ON ")
			.append("		gl.gm_fk = gm.pk ")
			.append("		AND gl.p_minute > :conditionFromMinute ")
			.append("		AND gl.p_minute < :conditionToMinute, ")
			.append("	tournament tn ")
			.append("	JOIN ")
			.append("	( ")
			.append("		SELECT ")
			.append("			pk ")
			.append("		FROM ")
			.append("			competition_type ")
			.append("		WHERE ")
			.append("			( ")
			.append("				name = :competitionType ")
			.append("				OR COALESCE(:competitionType, '') = '' ")
			.append("			) ")
			.append("	) ct ")
			.append("	ON  ")
			.append("		tn.cmt_fk = ct.pk ")
			.append("WHERE ")
			.append("	tn.pk = gm.tn_fk ")
			.append("	AND tn.level <= :level ")
			.append("	AND ")
			.append("		( ")
			.append("			0 = :countryKey ")
			.append("			OR tn.cn_fk = :countryKey ")
			.append("		) ")
			.append("	AND ")
			.append("		( ")
			.append("			0 = :teamKey ")
			.append("			OR ")
			.append("			 ( ")
			.append("			   gm.tm_fk1 = :teamKey ")
			.append("			   OR gm.tm_fk2 = :teamKey ")
			.append("			 ) ")
			.append("		) ")
			.append("GROUP BY ")
			.append("	gm.pk ")
			.append("HAVING ")
			.append("	COUNT(gl.pk) BETWEEN :conditionGoalCountFrom + 1 AND :conditionGoalCountTo - 1 ");
		
		final StringBuilder query = new StringBuilder();
		
		query
			.append("SELECT ")
			.append("	basis.cnt/hits.cnt AS kf, ")
			.append("	basis.cnt ")
			.append("FROM ")
			.append("	( ")
			.append("		SELECT ")
			.append("			COUNT(basis_int.pk) cnt ")
			.append("		FROM ")
			.append("			( ")
			.append(basicQuery)
			.append("			) basis_int ")
			.append("	) basis, ")
			.append("	( ")
			.append("		SELECT ")
			.append("			COUNT(hits_int.pk) cnt ")
			.append("		FROM ")
			.append("			( ")
			.append("				SELECT ")
			.append("					basis_int.pk ")
			.append("				FROM ")
			.append("					( ")
			.append(basicQuery)
			.append("					) basis_int ")
			.append("					LEFT OUTER JOIN ")
			.append("					goal gl ")
			.append("					ON ")
			.append("						gl.gm_fk = basis_int.pk ")
			.append("						AND gl.p_minute > :fromMinute ")
			.append("						AND gl.p_minute < :toMinute ")
			.append("				GROUP BY ")
			.append("					basis_int.pk ")
			.append("				HAVING ")
			.append("					COUNT(gl.pk) < :goalCount ")
			.append("			) hits_int ")
			.append("	) hits ");
		Object[] resultArray = getHibernateTemplate().execute(
				new HibernateCallback<Object[]>(){
					public Object[] doInHibernate(Session session){
						Query sqlQuery=session.createSQLQuery(query.toString());
						sqlQuery.setInteger("level", level);
						sqlQuery.setString("competitionType", competitionType);
						sqlQuery.setInteger("fromMinute", fromMinute);
						sqlQuery.setInteger("toMinute", toMinute);
						sqlQuery.setInteger("goalCount", goalCount);
						sqlQuery.setInteger("conditionFromMinute", conditionFromMinute);
						sqlQuery.setInteger("conditionToMinute", conditionToMinute);
						sqlQuery.setInteger("conditionGoalCountFrom", conditionGoalCountFrom);
						sqlQuery.setInteger("conditionGoalCountTo", conditionGoalCountTo);
						sqlQuery.setInteger("countryKey", countryKey);
						sqlQuery.setInteger("teamKey", teamKey);
						Object[] result = (Object[])sqlQuery.uniqueResult();
						return result;
					}
				}
		);
		return new BetResultDTO(level, fromMinute, (Number)resultArray[0], (Number)resultArray[1]);
	}
	
	
	
	public BetResultDTO findGoalCountRateWithAddConditionOld(final Integer level, final String competitionType,
			final Integer fromMinute, final Integer toMinute, final Integer goalCount, final Integer countryKey,
			final Integer teamKey, final Integer conditionFromMinute, final Integer conditionToMinute, 
			final Integer conditionGoalCountFrom, final Integer conditionGoalCountTo) {
			
		final String queryString = 
				"SELECT alias1.basis/alias1.hits AS kf, alias1.basis " +
				"FROM " +
				"( " +
				"	SELECT " +
				"		(" +
				"		SELECT COUNT(g1.pk) " +
				"		FROM game g1, tournament tn1, competition_type ct1 " +
				"		WHERE tn1.pk=g1.tn_fk " +
				"			AND tn1.level <= :level " +
				"			AND tn1.cmt_fk=ct1.pk " +
				"			AND (ct1.name = :competitionType OR COALESCE(:competitionType, '') = '') " +
				"			AND (0 = :countryKey OR tn1.cn_fk = :countryKey) " +
				"			AND (0 = :teamKey " +
				"					OR " +
				"					( " +
				"					g1.tm_fk1 = :teamKey " +
				"					OR g1.tm_fk2 = :teamKey " +
				"					) " +
				"				) " +
				"			AND ( " +
				"				SELECT COUNT(g3.pk) " +
				"		 		FROM goal g3 " +
				"				WHERE " +
				"					g3.gm_fk=g1.pk " +
				"					AND g3.p_minute > :conditionFromMinute " +
				"					AND g3.p_minute < :conditionToMinute " +
				"				) BETWEEN :conditionGoalCountFrom + 1 AND :conditionGoalCountTo -1 " +
				"		) AS basis," +
				"		COUNT(g.pk) AS hits " +
				"	FROM game g, tournament tn, competition_type ct " +
				"	WHERE tn.pk=g.tn_fk " +
				"		AND tn.level <= :level  " +
				"		AND tn.cmt_fk=ct.pk " +
				"		AND (ct.name = :competitionType  OR COALESCE(:competitionType, '') = '') " +
				"		AND (0 = :countryKey OR tn.cn_fk = :countryKey) " +
				"		AND (0 = :teamKey " +
				"				OR " +
				"				( " +
				"				g.tm_fk1 = :teamKey " +
				"				OR g.tm_fk2 = :teamKey " +
				"				) " +
				"			) " +
				"		AND ( " +
				"			SELECT COUNT(g3.pk) " +
				"			FROM goal g3 " +
				"			WHERE " +
				"				g3.gm_fk=g.pk " +
				"				AND g3.p_minute > :conditionFromMinute " +
				"				AND g3.p_minute < :conditionToMinute " +
				"			) BETWEEN :conditionGoalCountFrom + 1 AND :conditionGoalCountTo -1 " +
				"		AND (" +
				"		SELECT COUNT(gl.pk) " +
				"		FROM goal gl " +
				"		WHERE gl.gm_fk=g.pk " +
				"			AND gl.p_minute > :fromMinute " +
				"			AND gl.p_minute < :toMinute " +
				"		) < :goalCount " +
				") alias1";
		Object[] resultArray = getHibernateTemplate().execute(
				new HibernateCallback<Object[]>(){
					public Object[] doInHibernate(Session session){
						Query sqlQuery=session.createSQLQuery(queryString);
						sqlQuery.setInteger("level", level);
						sqlQuery.setString("competitionType", competitionType);
						sqlQuery.setInteger("fromMinute", fromMinute);
						sqlQuery.setInteger("toMinute", toMinute);
						sqlQuery.setInteger("goalCount", goalCount);
						sqlQuery.setInteger("conditionFromMinute", conditionFromMinute);
						sqlQuery.setInteger("conditionToMinute", conditionToMinute);
						sqlQuery.setInteger("conditionGoalCountFrom", conditionGoalCountFrom);
						sqlQuery.setInteger("conditionGoalCountTo", conditionGoalCountTo);
						sqlQuery.setInteger("countryKey", countryKey);
						sqlQuery.setInteger("teamKey", teamKey);
						Object[] result = (Object[])sqlQuery.uniqueResult();
						return result;
					}
				}
		);
		return new BetResultDTO(level, fromMinute, (Number)resultArray[0], (Number)resultArray[1]);
	}	
	
	public double findGoalCountRateWithAddConditionTest(final Integer level, final String competitionType,
			final Integer fromMinute, final Integer toMinute, final Integer goalCount,
			final Integer conditionFromMinute, final Integer conditionToMinute, 
			final Integer conditionGoalCountFrom, final Integer conditionGoalCountTo) {
			
		final String queryString = 
				"SELECT " +
				"	(" +
				"	SELECT COUNT(g1.pk) " +
				"	FROM game g1 " +
				"		LEFT OUTER JOIN " +
				"			( " +
				"			SELECT COUNT(g3.pk) goalCount, g3.gm_fk gm_fk " +
				"			FROM goal g3 " +
				"			WHERE " +
				"				g3.p_minute > :conditionFromMinute " +
				"				AND g3.p_minute < :conditionToMinute " +
				"			GROUP BY gm_fk " +
				"			) conditionGoal " +
				"		ON conditionGoal.gm_fk = g1.pk, " +
				"		tournament tn1, competition_type ct1 " +
				"	WHERE tn1.pk=g1.tn_fk " +
				"		AND tn1.level < :level " +
				"		AND tn1.cmt_fk=ct1.pk " +
				"		AND (ct1.name = :competitionType OR :competitionType IS NULL) " +
				"		AND COALESCE(conditionGoal.goalCount, 0) > :conditionGoalCountFrom " +
				"		AND COALESCE(conditionGoal.goalCount, 0) < :conditionGoalCountTo " +
				"	)" +
				"	/ COUNT(g.pk) kf " +
				"FROM game g" +
				"	LEFT OUTER JOIN " +
				"		( " +
				"		SELECT COUNT(g3.pk) goalCount, g3.gm_fk gm_fk " +
				"		FROM goal g3 " +
				"		WHERE " +
				"			g3.p_minute > :conditionFromMinute " +
				"			AND g3.p_minute < :conditionToMinute " +
				"		GROUP BY gm_fk " +
				"		) conditionGoal2 " +
				"	ON conditionGoal2.gm_fk = g.pk " +
				"	LEFT OUTER JOIN " +
				"		( " +
				"		SELECT COUNT(g3.pk) goalCount, g3.gm_fk gm_fk " +
				"		FROM goal g3 " +
				"		WHERE " +
				"			g3.p_minute > :fromMinute " +
				"			AND g3.p_minute > :fromMinute " +
				"			AND g3.p_minute < :toMinute " +
				"		GROUP BY gm_fk " +
				"		) gameGoal " +
				"	ON gameGoal.gm_fk = g.pk, " +
				"	tournament tn, competition_type ct " +
				"WHERE tn.pk=g.tn_fk " +
				"	AND tn.level < :level  " +
				"	AND tn.cmt_fk=ct.pk " +
				"	AND (ct.name = :competitionType  OR :competitionType IS NULL) " +
				"	AND COALESCE(conditionGoal2.goalCount, 0) > :conditionGoalCountFrom " +
				"	AND COALESCE(conditionGoal2.goalCount, 0) < :conditionGoalCountTo " +
				"	AND COALESCE(gameGoal.goalCount, 0) < :goalCount ";
		return getHibernateTemplate().execute(
				new HibernateCallback<Number>(){
					public Number doInHibernate(Session session){
						Query sqlQuery=session.createSQLQuery(queryString);
						sqlQuery.setInteger("level", level);
						sqlQuery.setString("competitionType", competitionType);
						sqlQuery.setInteger("fromMinute", fromMinute);
						sqlQuery.setInteger("toMinute", toMinute);
						sqlQuery.setInteger("goalCount", goalCount);
						sqlQuery.setInteger("conditionFromMinute", conditionFromMinute);
						sqlQuery.setInteger("conditionToMinute", conditionToMinute);
						sqlQuery.setInteger("conditionGoalCountFrom", conditionGoalCountFrom);
						sqlQuery.setInteger("conditionGoalCountTo", conditionGoalCountTo);
						Number result = (Number)sqlQuery.uniqueResult();
						return result;
					}
				}
		).doubleValue();
	}
	
	public Integer findGoalCountRateTest(Integer level, String competitionType,
			Integer fromMinute, Integer toMinute, Integer goalCount) {
		
		String queryString = 
				"SELECT (" +
				"	SELECT COUNT(g1) " +
				"	FROM org.pt.bet.domain.Game g1 " +
				"	WHERE g1.tournament.level < :level AND g1.tournament.competitionType.name = :competitionType " +
				"	) / COUNT(alias2.g) " +
				"FROM " +
				"	(" +
				"	SELECT alias1.g g" +
				"	FROM " +
				"		(" +
				" 		SELECT org.pt.bet.domain.Game g " +
				"			LEFT OUTER JOIN g.goals gl" +
				"			WITH gl.PMinute > :fromMinute AND gl.PMinute < :toMinute" +
				"		WHERE g.tournament.level < :level AND g.tournament.competitionType.name = :competitionType " +
				"		) alias1" +
				"	GROUP BY alias1.g " +
				"	HAVING COUNT(alias1.gl) < :goalCount " +
				"	) alias2";
		return ((Number)getHibernateTemplate().findByNamedParam(queryString, 
				new String[]{"level", "competitionType", "fromMinute", "toMinute", "goalCount"}, 
				new Object[]{level, competitionType, fromMinute, toMinute, goalCount}).get(0)).intValue();
	}
	
	public BetResultDTO findRedCardRateWithAddCondition(final Integer level, final String competitionType,
			final Integer fromMinute, final Integer toMinute, final Integer countryKey,
			final Integer teamKey, final Integer conditionYellowCount) {
			
		final String queryString = 
			"SELECT alias1.basis/alias1.hits AS kf, alias1.basis " +
			"FROM " +
			"( " +
			"	SELECT " +
			"		( " +
			"		SELECT COUNT(g1.pk) " +
			"		FROM game g1, tournament tn1, competition_type ct1 " +
			"		WHERE tn1.pk=g1.tn_fk " +
			"			AND tn1.level <= :level " +
			"			AND tn1.cmt_fk=ct1.pk " +
			"			AND (ct1.name = :competitionType OR COALESCE(:competitionType, '') = '') " +
			"			AND (0 = :countryKey OR tn1.cn_fk = :countryKey) " +
			"			AND (0 = :teamKey " +
			"					OR " +
			"					( " +
			"					g1.tm_fk1 = :teamKey " +
			"					OR g1.tm_fk2 = :teamKey " +
			"					) " +
			"				) " +
			"			AND ( " +
			"				SELECT COUNT(c3.pk) " +
			"		 		FROM card c3 " +
			"				WHERE " +
			"					c3.gm_fk=g1.pk " +
			"					AND c3.p_minute < :conditionToMinute " +
			"				) = :conditionYellowCount " +
			"			AND ( " +
			"				SELECT COUNT(c4.pk) " +
			"		 		FROM card c4 " +
			"				WHERE " +
			"					c4.gm_fk=g1.pk " +
			"					AND c4.p_minute < :conditionToMinute " +
            "  					AND c4.type = :cardTypeRed " +
			"				) = 0 " +
			"		) AS basis, " +
			"		COUNT(g.pk) AS hits " +
			"	FROM game g, tournament tn, competition_type ct " +
			"	WHERE tn.pk=g.tn_fk " +
			"		AND tn.level <= :level " +
			"		AND tn.cmt_fk=ct.pk " +
			"		AND (ct.name = :competitionType  OR COALESCE(:competitionType, '') = '') " +
			"		AND (0 = :countryKey OR tn.cn_fk = :countryKey) " +
			"		AND (0 = :teamKey " +
			"				OR " +
			"				( " +
			"				g.tm_fk1 = :teamKey " +
			"				OR g.tm_fk2 = :teamKey " +
			"				) " +
			"			) " +
			"		AND ( " +
			"			SELECT COUNT(c3.pk) " +
			"			FROM card c3 " +
			"			WHERE " +
			"				c3.gm_fk=g.pk " +
			"				AND c3.p_minute < :conditionToMinute " +
			"			) = :conditionYellowCount " +
			"		AND ( " +
			"		SELECT COUNT(c4.pk) " +
			"		FROM card c4 " +
			"		WHERE c4.gm_fk=g.pk " +
			"			AND c4.p_minute < :conditionToMinute " +
			"			AND c4.type = :cardTypeRed " +
			"		) = 0 " +
			"		AND ( " +
			"		SELECT COUNT(c.pk) " +
			"		FROM card c " +
			"		WHERE c.gm_fk=g.pk " +
			"			AND c.p_minute >= :conditionToMinute " +
			"			AND c.p_minute < :lastMinute " +
			"			AND c.type = :cardTypeRed " +
			"		) = 0 " +
			") alias1 ";
		Object[] resultArray = getHibernateTemplate().execute(
				new HibernateCallback<Object[]>(){
					public Object[] doInHibernate(Session session){
						Query sqlQuery=session.createSQLQuery(queryString);
						sqlQuery.setInteger("level", level);
						sqlQuery.setString("competitionType", competitionType);
						sqlQuery.setInteger("lastMinute", toMinute);
						sqlQuery.setInteger("conditionYellowCount", conditionYellowCount);
						sqlQuery.setInteger("conditionToMinute", fromMinute);
						sqlQuery.setString("cardTypeRed", ECardType.RED.name());
						sqlQuery.setInteger("countryKey", countryKey);
						sqlQuery.setInteger("teamKey", teamKey);
						Object[] result = (Object[])sqlQuery.uniqueResult();
						return result;
					}
				}
		);
		return new BetResultDTO(level, fromMinute, (Number)resultArray[0], (Number)resultArray[1]);
	}

}
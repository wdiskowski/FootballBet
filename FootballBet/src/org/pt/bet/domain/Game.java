package org.pt.bet.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Game entity.
 * 
 * @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
@Entity
@Table(name = "game")
public class Game implements java.io.Serializable {

	// Fields

	private Integer pk;
	private Team teamByTmFk1;
	private Tournament tournament;
	private Team teamByTmFk2;
	private String score;
	private String duration;
	private Date gDate;
	private Set<Goal> goals = new HashSet<Goal>(0);
	private Set<FailedPenalty> failedPenalties = new HashSet<FailedPenalty>(0);
	private Set<Card> cards = new HashSet<Card>(0);

	// Constructors

	/** default constructor */
	public Game() {
	}

	/** minimal constructor */
	public Game(Team teamByTmFk1, Tournament tournament, Team teamByTmFk2,
			String score, String duration, Date gDate) {
		this.teamByTmFk1 = teamByTmFk1;
		this.tournament = tournament;
		this.teamByTmFk2 = teamByTmFk2;
		this.score = score;
		this.duration = duration;
		this.gDate = gDate;
	}

	/** full constructor */
	public Game(Team teamByTmFk1, Tournament tournament, Team teamByTmFk2,
			String score, String duration, Date gDate, Set<Goal> goals,
			Set<FailedPenalty> failedPenalties, Set<Card> cards) {
		this.teamByTmFk1 = teamByTmFk1;
		this.tournament = tournament;
		this.teamByTmFk2 = teamByTmFk2;
		this.score = score;
		this.duration = duration;
		this.gDate = gDate;
		this.goals = goals;
		this.failedPenalties = failedPenalties;
		this.cards = cards;
	}

	// Property accessors

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getPk() {
		return this.pk;
	}

	public void setPk(Integer pk) {
		this.pk = pk;
	}

	@ManyToOne(optional = false)
	@JoinColumn(name = "tm_fk1")
	public Team getTeamByTmFk1() {
		return this.teamByTmFk1;
	}

	public void setTeamByTmFk1(Team teamByTmFk1) {
		this.teamByTmFk1 = teamByTmFk1;
	}

	@ManyToOne(optional = false)
	@JoinColumn(name = "tn_fk")
	public Tournament getTournament() {
		return this.tournament;
	}

	public void setTournament(Tournament tournament) {
		this.tournament = tournament;
	}

	@ManyToOne(optional = false)
	@JoinColumn(name = "tm_fk2")
	public Team getTeamByTmFk2() {
		return this.teamByTmFk2;
	}

	public void setTeamByTmFk2(Team teamByTmFk2) {
		this.teamByTmFk2 = teamByTmFk2;
	}

	@Column(name = "score", length = 10, nullable = false)
	@Basic(optional = false)
	public String getScore() {
		return this.score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	@Column(name = "duration", length = 5, nullable = false)
	@Basic(optional = false)
	public String getDuration() {
		return this.duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	@Column(name = "g_date", nullable = false)
	@Temporal(TemporalType.DATE)
	@Basic(optional = false)
	public Date getGDate() {
		return this.gDate;
	}

	public void setGDate(Date gDate) {
		this.gDate = gDate;
	}

	@OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
	public Set<Goal> getGoals() {
		return this.goals;
	}

	public void setGoals(Set<Goal> goals) {
		this.goals = goals;
	}

	@OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
	public Set<FailedPenalty> getFailedPenalties() {
		return this.failedPenalties;
	}

	public void setFailedPenalties(Set<FailedPenalty> failedPenalties) {
		this.failedPenalties = failedPenalties;
	}

	@OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
	public Set<Card> getCards() {
		return this.cards;
	}

	public void setCards(Set<Card> cards) {
		this.cards = cards;
	}

	@Override
	public String toString() {
		return tournament.toString() + "\n" 
		+ gDate + "\n" 
		+ teamByTmFk1.toString() + " - " + teamByTmFk2.toString() 
		+ "\n" + score;
	}
}
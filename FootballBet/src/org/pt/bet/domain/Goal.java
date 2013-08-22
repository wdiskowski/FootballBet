package org.pt.bet.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * Goal entity.
 * 
 * @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
@Entity
@Table(name = "goal")
public class Goal implements java.io.Serializable {

	// Fields

	private Integer pk;
	private Game game;
	private Integer PMinute;
	private Byte teamNr;
	private String player;
	private String score;
	private Byte penalty;

	// Constructors

	/** default constructor */
	public Goal() {
	}

	/** full constructor */
	public Goal(Game game, Integer PMinute, Byte teamNr, String player,
			String score, Byte penalty) {
		this.game = game;
		this.PMinute = PMinute;
		this.teamNr = teamNr;
		this.player = player;
		this.score = score;
		this.penalty = penalty;
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
	@JoinColumn(name = "gm_fk")
	public Game getGame() {
		return this.game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	@Column(name = "p_minute")
	@Basic(optional = false)
	public Integer getPMinute() {
		return this.PMinute;
	}

	public void setPMinute(Integer PMinute) {
		this.PMinute = PMinute;
	}

	@Column(name = "team_nr")
	@Basic(optional = false)
	public Byte getTeamNr() {
		return this.teamNr;
	}

	public void setTeamNr(Byte teamNr) {
		this.teamNr = teamNr;
	}

	@Basic(optional = false)
	public String getPlayer() {
		return this.player;
	}

	public void setPlayer(String player) {
		this.player = player;
	}

	@Column(name = "score", length = 10, nullable = false)
	@Basic(optional = false)
	public String getScore() {
		return this.score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	@Basic(optional = false)
	public Byte getPenalty() {
		return this.penalty;
	}

	public void setPenalty(Byte penalty) {
		this.penalty = penalty;
	}

}
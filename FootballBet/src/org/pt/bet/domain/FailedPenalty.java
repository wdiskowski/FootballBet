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
 * FailedPenalty entity.
 * 
 * @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
@Entity
@Table(name = "failed_penalty")
public class FailedPenalty implements java.io.Serializable {

	// Fields

	private Integer pk;
	private Game game;
	private String player;
	private Integer PMinute;
	private Byte teamNr;

	// Constructors

	/** default constructor */
	public FailedPenalty() {
	}

	/** full constructor */
	public FailedPenalty(Game game, String player, Integer PMinute, Byte teamNr) {
		this.game = game;
		this.player = player;
		this.PMinute = PMinute;
		this.teamNr = teamNr;
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

	@Basic(optional = false)
	public String getPlayer() {
		return this.player;
	}

	public void setPlayer(String player) {
		this.player = player;
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

}
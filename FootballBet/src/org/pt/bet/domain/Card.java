package org.pt.bet.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Card entity.
 * 
 * @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
@Entity
@Table(name = "card")
public class Card implements java.io.Serializable {

	// Fields

	private Integer pk;
	private Game game;
	private Integer PMinute;
	private ECardType type;
	private String player;
	private Byte teamNr;

	// Constructors

	/** default constructor */
	public Card() {
	}

	/** full constructor */
	public Card(Game game, Integer PMinute, ECardType type, String player,
			Byte teamNr) {
		this.game = game;
		this.PMinute = PMinute;
		this.type = type;
		this.player = player;
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

	@Column(name = "p_minute")
	@Basic(optional = false)
	public Integer getPMinute() {
		return this.PMinute;
	}

	public void setPMinute(Integer PMinute) {
		this.PMinute = PMinute;
	}

	@Enumerated(EnumType.STRING)
	public ECardType getType() {
		return this.type;
	}

	public void setType(ECardType type) {
		this.type = type;
	}

	@Basic(optional = false)
	public String getPlayer() {
		return this.player;
	}

	public void setPlayer(String player) {
		this.player = player;
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
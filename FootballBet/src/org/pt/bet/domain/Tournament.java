package org.pt.bet.domain;

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

/**
 * Tournament entity.
 * 
 * @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
@Entity
@Table(name = "tournament")
public class Tournament implements java.io.Serializable {

	public static final Integer DEFAULT_LEVEL = new Integer(1);

	// Fields

	private Integer pk;
	private Country country;
	private CompetitionType competitionType;
	private String name;
	private Integer level;
	private Set<Game> games = new HashSet<Game>(0);

	// Constructors

	/** default constructor */
	public Tournament() {
	}

	/** minimal constructor */
	public Tournament(Country country, CompetitionType competitionType,
			String name, Integer level) {
		this.country = country;
		this.competitionType = competitionType;
		this.name = name;
		this.level = level;
	}

	/** full constructor */
	public Tournament(Country country, CompetitionType competitionType,
			String name, Integer level, Set<Game> games) {
		this.country = country;
		this.competitionType = competitionType;
		this.name = name;
		this.level = level;
		this.games = games;
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
	@JoinColumn(name = "cn_fk")
	public Country getCountry() {
		return this.country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	@ManyToOne(optional = false)
	@JoinColumn(name = "cmt_fk")
	public CompetitionType getCompetitionType() {
		return this.competitionType;
	}

	public void setCompetitionType(CompetitionType competitionType) {
		this.competitionType = competitionType;
	}

	@Column(name = "name", length = 45, nullable = false)
	@Basic(optional = false)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Basic(optional = false)
	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	@OneToMany(mappedBy = "tournament", cascade = CascadeType.ALL)
	public Set<Game> getGames() {
		return this.games;
	}

	public void setGames(Set<Game> games) {
		this.games = games;
	}
	
	@Override
	public String toString() {
		return name + " (" + country.toString() + ")";
	}

}
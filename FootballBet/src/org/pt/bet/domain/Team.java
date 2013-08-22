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
 * Team entity.
 * 
 * @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
@Entity
@Table(name = "team")
public class Team implements java.io.Serializable {

	// Fields

	private Integer pk;
	private Country country;
	private String name;
	private String description;
	private Set<Game> gamesForTmFk2 = new HashSet<Game>(0);
	private Set<Game> gamesForTmFk1 = new HashSet<Game>(0);

	// Constructors

	/** default constructor */
	public Team() {
	}

	/** minimal constructor */
	public Team(Country country, String name) {
		this.country = country;
		this.name = name;
	}

	/** full constructor */
	public Team(Country country, String name, String description,
			Set<Game> gamesForTmFk2, Set<Game> gamesForTmFk1) {
		this.country = country;
		this.name = name;
		this.description = description;
		this.gamesForTmFk2 = gamesForTmFk2;
		this.gamesForTmFk1 = gamesForTmFk1;
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

	@Column(name = "name", length = 45, nullable = false)
	@Basic(optional = false)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "description", length = 100)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@OneToMany(mappedBy = "teamByTmFk2", cascade = CascadeType.ALL)
	public Set<Game> getGamesForTmFk2() {
		return this.gamesForTmFk2;
	}

	public void setGamesForTmFk2(Set<Game> gamesForTmFk2) {
		this.gamesForTmFk2 = gamesForTmFk2;
	}

	@OneToMany(mappedBy = "teamByTmFk1", cascade = CascadeType.ALL)
	public Set<Game> getGamesForTmFk1() {
		return this.gamesForTmFk1;
	}

	public void setGamesForTmFk1(Set<Game> gamesForTmFk1) {
		this.gamesForTmFk1 = gamesForTmFk1;
	}

	@Override
	public String toString() {
		return name;
	}
}
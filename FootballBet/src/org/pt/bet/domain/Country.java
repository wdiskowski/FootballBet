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
 * Country entity.
 * 
 * @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
@Entity
@Table(name = "country")
public class Country implements java.io.Serializable {

	// Fields

	private Integer pk;
	private Continent continent;
	private String name;
	private Set<Tournament> tournaments = new HashSet<Tournament>(0);
	private Set<Team> teams = new HashSet<Team>(0);

	// Constructors

	/** default constructor */
	public Country() {
	}

	/** minimal constructor */
	public Country(Continent continent, String name) {
		this.continent = continent;
		this.name = name;
	}

	/** full constructor */
	public Country(Continent continent, String name, Set<Tournament> tournaments, Set<Team> teams) {
		this.continent = continent;
		this.name = name;
		this.tournaments = tournaments;
		this.teams = teams;
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
	@JoinColumn(name = "ct_fk")
	public Continent getContinent() {
		return this.continent;
	}

	public void setContinent(Continent continent) {
		this.continent = continent;
	}

	@Column(name = "name", length = 45, nullable = false)
	@Basic(optional = false)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(mappedBy = "country", cascade = CascadeType.ALL)
	public Set<Tournament> getTournaments() {
		return this.tournaments;
	}

	public void setTournaments(Set<Tournament> tournaments) {
		this.tournaments = tournaments;
	}

	@OneToMany(mappedBy = "country", cascade = CascadeType.ALL)
	public Set<Team> getTeams() {
		return this.teams;
	}

	public void setTeams(Set<Team> teams) {
		this.teams = teams;
	}

	@Override
	public String toString() {
		return name;
	}
}
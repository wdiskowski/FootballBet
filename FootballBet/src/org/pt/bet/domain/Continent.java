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
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Continent entity.
 * 
 * @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
@Entity
@Table(name = "continent")
public class Continent implements java.io.Serializable {

	// Fields

	private Integer pk;
	private String name;
	private Set<Country> countries = new HashSet<Country>(0);

	// Constructors

	/** default constructor */
	public Continent() {
	}

	/** minimal constructor */
	public Continent(String name) {
		this.name = name;
	}

	/** full constructor */
	public Continent(String name, Set<Country> countries) {
		this.name = name;
		this.countries = countries;
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

	@Column(name = "name", length = 45, nullable = false)
	@Basic(optional = false)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(mappedBy = "continent", cascade = CascadeType.ALL)
	public Set<Country> getCountries() {
		return this.countries;
	}

	public void setCountries(Set<Country> countries) {
		this.countries = countries;
	}

}
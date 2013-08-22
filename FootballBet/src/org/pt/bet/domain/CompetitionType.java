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
 * CompetitionType entity.
 * 
 * @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
@Entity
@Table(name = "competition_type")
public class CompetitionType implements java.io.Serializable {

	public static final String DEFAULT_NAME = "	championship";

	// Fields

	private Integer pk;
	private String name;
	private String description;
	private Set<Tournament> tournaments = new HashSet<Tournament>(0);

	// Constructors

	/** default constructor */
	public CompetitionType() {
	}

	/** minimal constructor */
	public CompetitionType(String name) {
		this.name = name;
	}

	/** full constructor */
	public CompetitionType(String name, String description, Set<Tournament> tournaments) {
		this.name = name;
		this.description = description;
		this.tournaments = tournaments;
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

	@Column(name = "description", length = 100)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@OneToMany(mappedBy = "competitionType", cascade = CascadeType.ALL)
	public Set<Tournament> getTournaments() {
		return this.tournaments;
	}

	public void setTournaments(Set<Tournament> tournaments) {
		this.tournaments = tournaments;
	}

}
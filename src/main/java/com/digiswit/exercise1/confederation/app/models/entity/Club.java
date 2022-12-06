package com.digiswit.exercise1.confederation.app.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "clubs")
public class Club implements Serializable {

	private static final long serialVersionUID = -7974061740948907220L;

	public Club() {
		 players = new ArrayList<Player>();
	}
	
	public Club(Long id, UserClub user, String officialName, String popularName, 
			    Federation federation, Boolean publicDetails, Integer numberOfPlayers) {
		this.id = id;
		this.user = user;
		this.officialName = officialName;
		this.popularName = popularName;
		this.federation = federation;
		this.publicDetails = publicDetails;
		this.numberOfPlayers = numberOfPlayers;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private Long id;

	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private UserClub user;

	@Column(name = "official_name")
	@NotBlank(message = "Official name of the club is mandatory")
	private String officialName;

	@Column(name = "popular_name")
	private String popularName;

	@OneToOne
	@JoinColumn(name = "federation_id", referencedColumnName = "id")
	private Federation federation;

	@Column(name = "public_details")
	private Boolean publicDetails;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "club")
	@JsonIgnore
	private List<Player> players;

	@Column(name = "number_of_players")
	private Integer numberOfPlayers;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserClub getUser() {
		return user;
	}

	public void setUser(UserClub user) {
		this.user = user;
	}

	public String getOfficialName() {
		return officialName;
	}

	public void setOfficialName(String officialName) {
		this.officialName = officialName;
	}

	public String getPopularName() {
		return popularName;
	}

	public void setPopularName(String popularName) {
		this.popularName = popularName;
	}

	public Federation getFederation() {
		return federation;
	}

	public void setFederation(Federation federation) {
		this.federation = federation;
	}

	public Boolean getPublicDetails() {
		return publicDetails;
	}

	public void setPublicDetails(Boolean publicDetails) {
		this.publicDetails = publicDetails;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public Integer getNumberOfPlayers() {
		return numberOfPlayers;
	}

	public void setNumberOfPlayers(Integer numberOfPlayer) {
		this.numberOfPlayers = numberOfPlayer;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((officialName == null) ? 0 : officialName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Club other = (Club) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (officialName == null) {
			if (other.officialName != null)
				return false;
		} else if (!officialName.equals(other.officialName))
			return false;
		return true;
	}

}

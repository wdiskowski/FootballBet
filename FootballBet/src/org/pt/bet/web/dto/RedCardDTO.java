package org.pt.bet.web.dto;

public class RedCardDTO {

	private String competitionType;
	private Integer fromMinute;
	private Integer toMinute;
	private Double limitK;
	private Integer conditionYellowCount;
	private boolean limit;
	private Integer countryKey;
	private Integer teamKey;

	public Integer getTeamKey() {
		return teamKey;
	}

	public void setTeamKey(Integer teamKey) {
		this.teamKey = teamKey;
	}

	public Integer getCountryKey() {
		return countryKey;
	}

	public void setCountryKey(Integer countryKey) {
		this.countryKey = countryKey;
	}

	public String getCompetitionType() {
		return competitionType;
	}

	public void setCompetitionType(String competitionType) {
		this.competitionType = competitionType;
	}

	public Integer getFromMinute() {
		return fromMinute;
	}

	public void setFromMinute(Integer fromMinute) {
		this.fromMinute = fromMinute;
	}

	public Integer getToMinute() {
		return toMinute;
	}

	public void setToMinute(Integer toMinute) {
		this.toMinute = toMinute;
	}

	public Double getLimitK() {
		return limitK;
	}

	public void setLimitK(Double limitK) {
		this.limitK = limitK;
	}

	public Integer getConditionYellowCount() {
		return conditionYellowCount;
	}

	public void setConditionYellowCount(Integer conditionYellowCount) {
		this.conditionYellowCount = conditionYellowCount;
	}

	public boolean isLimit() {
		return limit;
	}

	public void setLimit(boolean limit) {
		this.limit = limit;
	}

}

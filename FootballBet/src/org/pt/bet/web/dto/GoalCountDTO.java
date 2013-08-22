package org.pt.bet.web.dto;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class GoalCountDTO {

	private String competitionType;
	private Integer fromMinute;
	private Integer toMinute;
	private Integer goalCount;
	private Double limitK;
	private Integer conditionFromMinute;
	private Integer conditionToMinute;
	private Integer conditionGoalCountFrom;
	private Integer conditionGoalCountTo;
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

	public Integer getGoalCount() {
		return goalCount;
	}

	public void setGoalCount(Integer goalCount) {
		this.goalCount = goalCount;
	}

	public Double getLimitK() {
		return limitK;
	}

	public void setLimitK(Double limitK) {
		this.limitK = limitK;
	}

	public Integer getConditionFromMinute() {
		return conditionFromMinute;
	}

	public void setConditionFromMinute(Integer conditionFromMinute) {
		this.conditionFromMinute = conditionFromMinute;
	}

	public Integer getConditionToMinute() {
		return conditionToMinute;
	}

	public void setConditionToMinute(Integer conditionToMinute) {
		this.conditionToMinute = conditionToMinute;
	}

	public Integer getConditionGoalCountFrom() {
		return conditionGoalCountFrom;
	}

	public void setConditionGoalCountFrom(Integer conditionGoalCountFrom) {
		this.conditionGoalCountFrom = conditionGoalCountFrom;
	}

	public Integer getConditionGoalCountTo() {
		return conditionGoalCountTo;
	}

	public void setConditionGoalCountTo(Integer conditionGoalCountTo) {
		this.conditionGoalCountTo = conditionGoalCountTo;
	}

	public boolean isLimit() {
		return limit;
	}

	public void setLimit(boolean limit) {
		this.limit = limit;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

}

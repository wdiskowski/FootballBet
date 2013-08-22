package org.pt.bet.dao;

import org.pt.bet.web.dto.BetResultDTO;


public interface IBetDAO {

	public BetResultDTO findGoalCountRate(final Integer level, final String competitionType,
			final Integer fromMinute, final Integer toMinute, final Integer goalCount, final Integer countryKey,
			final Integer teamKey );
	
	public BetResultDTO findGoalCountRateWithAddCondition(final Integer level, final String competitionType,
			final Integer fromMinute, final Integer toMinute, final Integer goalCount, final Integer countryKey,
			final Integer teamKey, final Integer conditionFromMinute, final Integer conditionToMinute, 
			final Integer conditionGoalCountFrom, final Integer conditionGoalCountTo);
	
	public BetResultDTO findRedCardRateWithAddCondition(final Integer level, final String competitionType,
			final Integer fromMinute, final Integer toMinute, final Integer countryKey,
			final Integer teamKey, final Integer conditionYellowCount);

}
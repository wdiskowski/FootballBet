package org.pt.bet.service;

import java.util.List;

import org.pt.bet.web.dto.BetResultDTO;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * Die CustomerService-Schnittstelle.
 * @author g. bachlmayer
 *
 */
@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.REPEATABLE_READ, readOnly=true)
public interface IBetService {
	
	
	public List<BetResultDTO> getGoalCountRate(String competitionType,
			Integer fromMinute, Integer toMinute, Integer goalCount, Integer countryKey, Integer teamKey);
	public List<BetResultDTO> getGoalCountFrom(String competitionType, Integer startFromMinute,
			Integer toMinute, Integer goalCount, Integer countryKey, Integer teamKey, Double limitK);
	public List<BetResultDTO> getGoalCountRateWithAddCondition(String competitionType,
			Integer fromMinute, Integer toMinute, Integer goalCount, Integer countryKey,
			Integer teamKey, Integer conditionFromMinute, Integer conditionToMinute, 
			Integer conditionGoalCountFrom, Integer conditionGoalCountTo);
	public List<BetResultDTO> getGoalCountFromWithAddCondition(String competitionType, Integer startFromMinute,
			Integer toMinute, Integer goalCount, Integer countryKey, Integer teamKey, Double limitK,
			Integer conditionFromMinute, Integer conditionToMinute, 
			Integer conditionGoalCountFrom, Integer conditionGoalCountTo);
	public List<BetResultDTO> getRedCardRateWithAddCondition(String competitionType,
			Integer fromMinute, Integer toMinute, Integer countryKey,
			Integer teamKey, Integer conditionYellowCount);
	public List<BetResultDTO> getRedCardFromWithAddCondition(String competitionType,
			Integer startFromMinute, Integer toMinute, Integer countryKey,
			Integer teamKey, Double limitK, Integer conditionYellowCount);
	
}
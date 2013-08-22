package org.pt.bet.service;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.pt.bet.dao.IBetDAO;
import org.pt.bet.web.dto.BetResultDTO;



// TODO Javadoc
public class BetServiceImpl implements IBetService {

    static Logger logger = Logger.getLogger(BetServiceImpl.class.getName());
    
    @Resource
    private IBetDAO betDAO;
    
    private int maxGoalCountIterCount;
    
    NumberFormat rateFormat;

	public void setMaxGoalCountIterCount(int maxIterCount) {
		this.maxGoalCountIterCount = maxIterCount;
	}

	public void setRateFormat(NumberFormat format) {
		rateFormat = format;
	}

	public List<BetResultDTO> getGoalCountFrom(String competitionType, Integer startFromMinute,
			Integer toMinute, Integer goalCount, Integer countryKey, Integer teamKey, Double limitK) {
		List<BetResultDTO> returnList = new ArrayList<BetResultDTO>();
		for(int i=1; i<=3; i++){
			for(int j=startFromMinute; j<startFromMinute+maxGoalCountIterCount; j++){
				BetResultDTO betResultDTO = betDAO.findGoalCountRate(new Integer(i), competitionType, j, toMinute, goalCount, countryKey, teamKey);
				if(betResultDTO.getRate() == null || betResultDTO.getRate().doubleValue() < limitK){
					betResultDTO.setRateFormat(rateFormat);
					returnList.add(betResultDTO);
					break;
					
				}
			}
		}
		
		return returnList;
	}


	public List<BetResultDTO> getGoalCountRate(String competitionType, Integer fromMinute,
			Integer toMinute, Integer goalCount, Integer countryKey, Integer teamKey) {
		List<BetResultDTO> returnList = new ArrayList<BetResultDTO>();
		for(int i=1; i<=3; i++){
			BetResultDTO betResultDTO = betDAO.findGoalCountRate(new Integer(i), competitionType, fromMinute, toMinute, goalCount, countryKey, teamKey);
			betResultDTO.setRateFormat(rateFormat);
			returnList.add(betResultDTO);
		}
		
		return returnList;
	}
	public List<BetResultDTO> getGoalCountFromWithAddCondition(String competitionType,
			Integer startFromMinute, Integer toMinute, Integer goalCount, Integer countryKey,
			Integer teamKey, Double limitK, Integer conditionFromMinute,
			Integer conditionToMinute, Integer conditionGoalCountFrom,
			Integer conditionGoalCountTo) {
		List<BetResultDTO> returnList = new ArrayList<BetResultDTO>();
		for (int i = 1; i <= 3; i++) {
			for (int j = startFromMinute; j < startFromMinute
					+ maxGoalCountIterCount; j++) {
				BetResultDTO betResultDTO = betDAO.findGoalCountRateWithAddCondition(
						new Integer(i), competitionType, j, toMinute,
						goalCount, countryKey, teamKey, conditionFromMinute, conditionToMinute,
						conditionGoalCountFrom, conditionGoalCountTo);
				if(betResultDTO.getRate()==null || betResultDTO.getRate().doubleValue() < limitK){
					betResultDTO.setRateFormat(rateFormat);
					returnList.add(betResultDTO);
					break;

				}
			}
		}
		
		return returnList;
	}

	public List<BetResultDTO> getGoalCountRateWithAddCondition(String competitionType,
			Integer fromMinute, Integer toMinute, Integer goalCount, Integer countryKey,
			Integer teamKey, Integer conditionFromMinute, Integer conditionToMinute,
			Integer conditionGoalCountFrom, Integer conditionGoalCountTo) {
		List<BetResultDTO> returnList = new ArrayList<BetResultDTO>();
		for (int i = 1; i <= 3; i++) {
			BetResultDTO betResultDTO = betDAO.findGoalCountRateWithAddCondition(new Integer(i),
					competitionType, fromMinute, toMinute, goalCount, countryKey,
					teamKey, conditionFromMinute, conditionToMinute,
					conditionGoalCountFrom, conditionGoalCountTo);
			betResultDTO.setRateFormat(rateFormat);
			returnList.add(betResultDTO);
		}
		
		return returnList;
	}

	public List<BetResultDTO> getRedCardRateWithAddCondition(String competitionType,
			Integer fromMinute, Integer toMinute, Integer countryKey,
			Integer teamKey, Integer conditionYellowCount) {
		List<BetResultDTO> returnList = new ArrayList<BetResultDTO>();
		for (int i = 1; i <= 3; i++) {
			BetResultDTO betResultDTO = betDAO.findRedCardRateWithAddCondition(new Integer(i), 
					competitionType, fromMinute, toMinute, countryKey, teamKey, conditionYellowCount);
			betResultDTO.setRateFormat(rateFormat);
			returnList.add(betResultDTO);
		}
		
		return returnList;
	}

	public List<BetResultDTO> getRedCardFromWithAddCondition(String competitionType,
			Integer startFromMinute, Integer toMinute, Integer countryKey,
			Integer teamKey, Double limitK, Integer conditionYellowCount) {
		List<BetResultDTO> returnList = new ArrayList<BetResultDTO>();
		for (int i = 1; i <= 3; i++) {
			for (int j = startFromMinute; j < startFromMinute
					+ maxGoalCountIterCount; j++) {
				BetResultDTO betResultDTO = betDAO.findRedCardRateWithAddCondition(new Integer(i), 
						competitionType, j, toMinute, countryKey, teamKey, conditionYellowCount);
				if(betResultDTO.getRate()==null || betResultDTO.getRate().doubleValue() < limitK){
					betResultDTO.setRateFormat(rateFormat);
					returnList.add(betResultDTO);
					break;

				}
			}
		}
		
		return returnList;
	}


	
}

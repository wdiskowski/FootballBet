package org.pt.bet.web;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pt.bet.service.IBetService;
import org.pt.bet.web.dto.GoalCountDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class GoalCountWithConditionController {
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource
    private IBetService betService;
    
    private String viewName;

    public void setViewName(String viewName) {
		this.viewName = viewName;
	}

    @RequestMapping("/goalcountwithcondition.disp")
    public ModelAndView onSubmit(GoalCountDTO goalCountDTO) {

        ModelAndView modelAndView = null;
        if(!goalCountDTO.isLimit()){
        	logger.info("simple count");
        	modelAndView = new ModelAndView(viewName, "rateList", 
        			betService.getGoalCountRateWithAddCondition(goalCountDTO.getCompetitionType(), 
        					goalCountDTO.getFromMinute(),  goalCountDTO.getToMinute(),  
        					goalCountDTO.getGoalCount(), goalCountDTO.getCountryKey(), 
        					goalCountDTO.getTeamKey(), goalCountDTO.getConditionFromMinute(),
        					goalCountDTO.getConditionToMinute(), goalCountDTO.getConditionGoalCountFrom(),
        					goalCountDTO.getConditionGoalCountTo()));
        } else{
        	logger.info("count with limit");
        	modelAndView = new ModelAndView(viewName, "rateList", betService.getGoalCountFromWithAddCondition(goalCountDTO.getCompetitionType(), 
					goalCountDTO.getFromMinute(),  goalCountDTO.getToMinute(),  
					goalCountDTO.getGoalCount(), goalCountDTO.getCountryKey(), 
					goalCountDTO.getTeamKey(), goalCountDTO.getLimitK(), goalCountDTO.getConditionFromMinute(),
					goalCountDTO.getConditionToMinute(), goalCountDTO.getConditionGoalCountFrom(),
					goalCountDTO.getConditionGoalCountTo()));
        }

        return modelAndView;
	}

	

}

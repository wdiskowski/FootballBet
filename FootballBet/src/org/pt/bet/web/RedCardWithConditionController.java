package org.pt.bet.web;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pt.bet.service.IBetService;
import org.pt.bet.web.dto.RedCardDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RedCardWithConditionController {
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource
    private IBetService betService;

    private String viewName;

    public void setViewName(String viewName) {
		this.viewName = viewName;
	}
    
    @RequestMapping("/redcardwithcondition.disp")
    public ModelAndView onSubmit(RedCardDTO redCardDTO) {

        ModelAndView modelAndView = null;
        if(!redCardDTO.isLimit()){
        	logger.info("simple count");
        	modelAndView = new ModelAndView(viewName, "rateList", 
        			betService.getRedCardRateWithAddCondition(redCardDTO.getCompetitionType(), 
        					redCardDTO.getFromMinute(),  redCardDTO.getToMinute(),  
        					redCardDTO.getCountryKey(), 
        					redCardDTO.getTeamKey(), redCardDTO.getConditionYellowCount()));
        } else{
        	logger.info("count with limit");
        	modelAndView = new ModelAndView(viewName, "rateList", betService.getRedCardFromWithAddCondition(
        			redCardDTO.getCompetitionType(), 
					redCardDTO.getFromMinute(),  redCardDTO.getToMinute(),  
					redCardDTO.getCountryKey(), 
					redCardDTO.getTeamKey(), redCardDTO.getLimitK(), redCardDTO.getConditionYellowCount()));
        }

        return modelAndView;
	}

	

}

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
public class GoalCountController {
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource
    private IBetService betService;
    
    private String viewName;

    public void setViewName(String viewName) {
		this.viewName = viewName;
	}

    @RequestMapping("/goalcount.disp")
    public ModelAndView onSubmit(GoalCountDTO goalCountDTO) {
        logger.info(">");

        ModelAndView modelAndView = null;
        if(!goalCountDTO.isLimit()){
        	logger.info("simple couont");
        	try {
				modelAndView = new ModelAndView(viewName, "rateList",
						betService.getGoalCountRate(goalCountDTO
								.getCompetitionType(), goalCountDTO
								.getFromMinute(), goalCountDTO.getToMinute(),
								goalCountDTO.getGoalCount(), goalCountDTO.getCountryKey(),
								goalCountDTO.getTeamKey()));
			} catch (Exception e) {
				logger.info(e);
			}
        } else{
        	logger.info("couont with limit");
        	modelAndView = new ModelAndView(viewName, "rateList", 
        			betService.getGoalCountFrom(goalCountDTO.getCompetitionType(), 
					goalCountDTO.getFromMinute(),  goalCountDTO.getToMinute(),  
					goalCountDTO.getGoalCount(), goalCountDTO.getCountryKey(),
					goalCountDTO.getTeamKey(), goalCountDTO.getLimitK()));
        }
        return modelAndView;
	}

	

}

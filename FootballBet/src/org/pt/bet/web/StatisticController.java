package org.pt.bet.web;

import java.text.DateFormat;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pt.bet.service.IGameService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class StatisticController {
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    
    @Resource
    private IGameService gameService;

    private String viewName;
    
    private DateFormat updateFormat; 
    
	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public void setUpdateFormat(DateFormat updateFormat) {
		this.updateFormat = updateFormat;
	}

	@RequestMapping("/statistic.disp")
    public ModelAndView handleRequestInternal() {

        ModelAndView modelAndView = new ModelAndView(viewName, "lastUpdate", updateFormat.format(gameService.getMaxGameDate()));
        
        modelAndView.addObject("countGamesLevel1", gameService.findGamesByTournamentLevel(1).size());
        modelAndView.addObject("countGamesLevel2", gameService.findGamesByTournamentLevel(2).size());
        modelAndView.addObject("countGamesLevel3", gameService.findGamesByTournamentLevel(3).size());

        return modelAndView;
	}

	

}

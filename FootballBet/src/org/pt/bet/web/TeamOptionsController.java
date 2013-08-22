package org.pt.bet.web;

import java.rmi.server.UID;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mw.bi.util.Option;
import org.mw.bi.util.Options;
import org.pt.bet.domain.Team;
import org.pt.bet.service.ITeamService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TeamOptionsController {
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    
    @Resource
    private ITeamService teamService;
    
    private String viewName;

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	@RequestMapping("/teamoptions.disp")
    public ModelAndView handleRequestInternal(@RequestParam("countryKey") int countryKey) {

		List<Team> teamList = teamService.findByCountryId(countryKey);
		Options teamOptions = new Options();
		teamOptions.addOption(new Option("0", "all", true));
		for(Team team: teamList){
			teamOptions.addOption(new Option(team.getPk().toString(), StringUtils.abbreviate(team.getName(), 20)));
		}
        logger.info("team options size: " + teamList.size());
        String teamOptionsWeb = teamOptions.getOptions("teamKey" +new UID(), "teamKey", 1);

        
        ModelAndView modelAndView = new ModelAndView(viewName, "webContent", teamOptionsWeb);
 
        return modelAndView;
	}

	

}

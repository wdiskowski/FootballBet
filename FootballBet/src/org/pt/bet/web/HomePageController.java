package org.pt.bet.web;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mw.bi.util.Option;
import org.mw.bi.util.Options;
import org.pt.bet.domain.Country;
import org.pt.bet.service.ICountryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class HomePageController {
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    
    @Resource
    private ICountryService countryService;
    
    private String viewName;

    public void setViewName(String viewName) {
		this.viewName = viewName;
	}

    @RequestMapping("/index.disp")
    public ModelAndView handleRequestInternal(){

		List<Country> countryList = countryService.findAllCountries();
		Options countryOptions = new Options();
		countryOptions.addOption(new Option("0", "all", true));
		for(Country country: countryList){
			countryOptions.addOption(new Option(country.getPk().toString(), country.getName()));
		}
        logger.info("country options size: " + countryList.size());
        String countryOptionsWeb = countryOptions.getOptions("countryKey", 1);

        String countryOptionsWebRedCard = countryOptions.getOptions("countryKeyRedCard", "countryKey", 1);
        
        ModelAndView modelAndView = new ModelAndView(viewName, "countryOptions", countryOptionsWeb);
        modelAndView.addObject("countryOptionsRedCard", countryOptionsWebRedCard);
        return modelAndView;
	}

	

}

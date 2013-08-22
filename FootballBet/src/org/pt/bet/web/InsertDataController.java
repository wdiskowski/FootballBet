package org.pt.bet.web;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pt.bet.service.ILivescoreInsertDataService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class InsertDataController {
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    
    @Resource
    private ILivescoreInsertDataService livescoreInsertDataService;
    
    private String viewName;

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	@RequestMapping("/insertdata.disp")
    public ModelAndView handleRequestInternal() {
		String insertionMessage = livescoreInsertDataService.insertData();
        logger.info(insertionMessage);

        return new ModelAndView(viewName, "webContent", insertionMessage);
	}

	

}

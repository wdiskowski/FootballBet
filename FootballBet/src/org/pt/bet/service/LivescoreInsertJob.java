package org.pt.bet.service;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;

public class LivescoreInsertJob extends org.springframework.scheduling.quartz.QuartzJobBean {

	ILivescoreInsertDataService livescoreInsertDataService;

	static Logger logger = Logger.getLogger(LivescoreInsertJob.class.getName());

	
	public void setLivescoreInsertDataService(
			ILivescoreInsertDataService livescoreInsertDataService) {
		this.livescoreInsertDataService = livescoreInsertDataService;
	}

	protected void executeInternal(JobExecutionContext jobContext){
		logger.info("it was inserted: " + livescoreInsertDataService.insertData() + " games");
	}
}

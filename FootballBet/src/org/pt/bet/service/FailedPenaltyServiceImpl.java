package org.pt.bet.service;

import java.io.Serializable;

import javax.annotation.Resource;

import org.pt.bet.dao.IFailedPenaltyDAO;
import org.pt.bet.domain.FailedPenalty;

public class FailedPenaltyServiceImpl implements IFailedPenaltyService {

	
    @Resource
	private IFailedPenaltyDAO failedPenaltyDAO;
	
	@Override
	public Serializable save(FailedPenalty failedPenalty) {
		return failedPenaltyDAO.save(failedPenalty);
	}

}

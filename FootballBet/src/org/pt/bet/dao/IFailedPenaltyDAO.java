package org.pt.bet.dao;

import java.util.List;

import org.pt.bet.domain.FailedPenalty;

public interface IFailedPenaltyDAO extends IAbstractDAO {

	public abstract FailedPenalty findById(java.lang.Integer id);

	public abstract List<FailedPenalty> findByPlayer(String player);

	public abstract List<FailedPenalty> findByPMinute(Integer PMinute);

	public abstract List<FailedPenalty> findByTeamNr(Byte teamNr);

	public abstract List<FailedPenalty> findAll();

}
package org.pt.bet.service;

import java.util.Date;
import java.util.List;

import org.pt.bet.domain.Card;
import org.pt.bet.domain.FailedPenalty;
import org.pt.bet.domain.Goal;
import org.pt.bet.domain.Tournament;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.REPEATABLE_READ)
public interface IGameResultService {
	public abstract void saveGameResult(String teamName1,
			String teamName2, Tournament tournament, String score,
			String duration, Date date, List<Goal> goals, List<Card> cards,
			List<FailedPenalty> failedPenalties);
}

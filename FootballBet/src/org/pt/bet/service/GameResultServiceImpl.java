package org.pt.bet.service;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pt.bet.domain.Card;
import org.pt.bet.domain.Country;
import org.pt.bet.domain.FailedPenalty;
import org.pt.bet.domain.Game;
import org.pt.bet.domain.Goal;
import org.pt.bet.domain.Team;
import org.pt.bet.domain.Tournament;
import org.pt.bet.exception.ScoreValidationException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class GameResultServiceImpl implements IGameResultService {

	private static Log log = LogFactory.getLog(GameResultServiceImpl.class);
	
	@Resource
	private ITeamService teamService;
	
	@Resource
	private IGameService gameService;
	
	@Resource
	private IGoalService goalService;
	
	@Resource
	private ICardService cardService;
	
	@Resource
	private IFailedPenaltyService failedPenaltyService;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void saveGameResult(String teamName1, String teamName2,
			Tournament tournament, String score, String duration, Date date,
			List<Goal> goals, List<Card> cards,
			List<FailedPenalty> failedPenalties) {
		Country country = tournament.getCountry();
		Team team1 = teamService.getTeam(country, teamName1, true);
		Team team2 = teamService.getTeam(country, teamName2, true);
		if(tournament!=null && team1!=null && team2!=null){
			if(validateScore(score, goals.size())){

				Game game = new Game(team1, tournament, team2, score, duration, date );
				gameService.save(game);
				for(int i=0; i<goals.size(); i++){
					Goal nextGoal = goals.get(i);
					game.getGoals().add(nextGoal);
					nextGoal.setGame(game);
					goalService.save(nextGoal);
				}
				for(int i=0; i<cards.size(); i++){
					Card nextCard = cards.get(i);
					game.getCards().add(nextCard);
					nextCard.setGame(game);
					cardService.save(nextCard);
				}
				for(int i=0; i<failedPenalties.size(); i++){
					FailedPenalty nextFailedPenalty = failedPenalties.get(i);
					game.getFailedPenalties().add(nextFailedPenalty);
					nextFailedPenalty.setGame(game);
					failedPenaltyService.save(nextFailedPenalty);
				}
				log.info("Save game: " + game 
						+ " goals: " + goals.size() + " cards: " + cards.size() 
						+ " failed penalties: " + failedPenalties.size());
				
			}else{
				throw new ScoreValidationException("Invalid score: " + score + " goal count: " + goals.size());
			}
		}

	}

    
	private boolean validateScore(String score, int goalsSize){
		int goalsCount = -1;
		Pattern pattern = Pattern.compile("(\\d+)-(\\d+)");
		Matcher matcher = pattern.matcher(score);
		if (matcher.find()) {
			goalsCount = Integer.parseInt(matcher.group(1)) + Integer.parseInt(matcher.group(2));
		}
		return (goalsCount == goalsSize);
	}
}

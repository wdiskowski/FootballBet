package org.pt.bet.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mw.bi.util.FileCreateCopy;
import org.pt.bet.domain.Card;
import org.pt.bet.domain.ECardType;
import org.pt.bet.domain.FailedPenalty;
import org.pt.bet.domain.Goal;
import org.pt.bet.domain.Tournament;
import org.pt.bet.exception.CountryNotFoundException;
import org.pt.bet.exception.DataTransferException;
import org.pt.bet.exception.LivescoreParserException;
import org.pt.bet.exception.ScoreValidationException;
import org.pt.bet.exception.TournamentNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;


public class LivescoreInsertServiceImpl implements ILivescoreInsertDataService {

	private static Log log = LogFactory.getLog(LivescoreInsertServiceImpl.class);
	
	private static final String PROTOCOL = "http";
	//private static final String HOST = "www.livescore.com";
	private static final String HOST = "livescore.com";
	
	private static final String PENALTY_PATTERN = "\\(pen\\.\\)";
	
	private static final String SOCCER_URL_PREFIX = "/soccer/";
	
//	private static final String EVENT_FAILED_PENALTY = "nogoal";
//	private static final String EVENT_YELLOW_CARD = "yellow";
//	private static final String EVENT_RED_CARD = "red";
//	private static final String EVENT_YELLOW_RED_CARD = "yellow-red";

	@Resource
	private ITournamentService tournamentService;
	
	@Resource
	private IGameResultService gameResultService;
	
	/* (non-Javadoc)
	 * @see org.pt.bet.service.ILivescoreInsertDataService#insertData()
	 */
	public String insertData() {
		int gameInserted = 0;
		int gameFound = 0;
		int invalidScoreCount = 0;
		int insertionError = 0;
		// find sid
		List<Tournament> tournamentList = tournamentService.findAll();
		Set<String> tournamentFound = new TreeSet<String>();
		Set<String> tournamentAll = new TreeSet<String>();
		for(int i=0; i<tournamentList.size(); i++){
			Tournament nextTournament = tournamentList.get(i);
			tournamentAll.add(nextTournament.getName());
			log.info(String.format("parse tournament: %s (%s)", nextTournament
					.getName(), nextTournament.getCountry().getName()));
			//if(nextTournament.getPk().intValue()!=124)
			//	continue;
			
			
			//String pageUrl = "?page=";
			
			String land = nextTournament.getCountry().getName().replaceAll(" ", "");
//			String urlString = commonUrlString + pageUrl + land;
			String answer = null;
			Pattern pattern;
			Matcher matcher;
			String regExp;
			String tournamentLink = null;
			try {
				tournamentLink = getTournamentLink(land, nextTournament.getName());
			} catch (DataTransferException e2) {
				log.info(String.format("Can not find tournament: %s (%s)",
						nextTournament.getName(), nextTournament.getCountry()
								.getName()));
				continue;
			}
			if(tournamentLink != null) {
				tournamentFound.add(nextTournament.getName());
			}else{
				log.info(String.format("Can not find Tournament: %s (%s)",
						nextTournament.getName(), nextTournament.getCountry()
								.getName()));
				continue;
			}
			answer = getTournamentDialogContent(tournamentLink);
			//System.out.println(answer.replaceAll(">", ">\n"));
			try {
				
				
				regExp = buildTournamentDialogParserRegExp(land);
				pattern = Pattern.compile(regExp);
				matcher = pattern.matcher(answer);
				String duration = null;
				Date gdate = null;
				String teamName1 = null;
				String teamName2 = null;
				String score = null;
				List<Goal> goals = new ArrayList<Goal>();
				List<Card> cards = new ArrayList<Card>();
				List<FailedPenalty> failedPenalties = new ArrayList<FailedPenalty>();
				while (matcher.find()) {
					String gameDate = matcher.group(2);
					String gameLink = matcher.group(4);
					if (gameDate != null){
						gdate = parseGameDate(gameDate);
					}
					if (gameLink != null) {
						duration = null;
						teamName1 = null;
						score = null;
						teamName2 = null;
						goals.clear();
						cards.clear();
						failedPenalties.clear();
						try {
							answer = getLivescoreDialogContent(gameLink);
	
							//System.out.println(answer.replaceAll(">", ">\n"));

							String regExpScore = "<th\\s+class=\"sco\">\\s*(\\d+\\s-\\s\\d+)\\s*</th>";
							String regExpTeamHome = "<th\\s+class=\"home\"\\s+title=\"([^\"]+)\"\\s*>";
							String regExpTeamAwy = "<th\\s+class=\"awy\"\\s+title=\"([^\"]+)\"\\s*>";
							String regExpDuration ="<th\\s+class=\"sts\">\\s*(A?[F|E]T)\\s*</th>";
							String regExpEvent = "<span\\s+class=\"inc\\s+((goal)|(goal-miss)|(yellowcard)|(redcard)|(redyellowcard))\\s+((right)|(left))\">";
							String regExpPlayer = "<span\\s+class=\"((right)|(left))\\s+name\">([^<]+)</span>";
							String regExpMinute = "<td\\s+class=\"min\">\\s*(\\d{1,3})'\\s*</td>";
							String regExpDynamicScore = "<td\\s+class=\"sco\">\\s*(\\d+\\s-\\s\\d+)\\s*</td>";
							
							score = removeWhitespaces(getMatchesFirstGroup(regExpScore, answer));
							teamName1 = decodeAmpersand(getMatchesFirstGroup(regExpTeamHome, answer).trim());
							teamName2 = decodeAmpersand(getMatchesFirstGroup(regExpTeamAwy, answer).trim());
							duration = getMatchesFirstGroup(regExpDuration, answer);
							

							Pattern patternLine = Pattern.compile("(" + regExpMinute + ")|(" + regExpEvent + ")|(" + regExpPlayer + ")|(" + regExpDynamicScore + ")");
							Matcher matcherLine = patternLine.matcher(answer);
							String minute = null;
							String eventType = null;
							String eventRelation =  null;
							String playerRelation =  null;
							String playerName =  null;
							String dynamicScore =  null;
							Byte teamNumber = null;
							Byte penalty = null;							
							while(matcherLine.find()){
								String nextMinute = matcherLine.group(2);
								String nextEventType = matcherLine.group(4);
								String nextEventRelation =  matcherLine.group(10);
								String nextPlayerRelation =  matcherLine.group(14);
								String nextPlayerName =  matcherLine.group(17);
								String nextDynamicScore =  removeWhitespaces(matcherLine.group(19));

								if(nextMinute != null){
									minute = nextMinute;
									eventType = null;
									eventRelation = null;
									playerRelation = null;
									playerName = null;
									dynamicScore =  null;
									teamNumber = null;
									penalty = null;
								}
								if(nextEventType != null && nextEventRelation != null){
									eventType = nextEventType;
									eventRelation = nextEventRelation;
									playerRelation = null;
									playerName = null;
									teamNumber = null;
									penalty = null;
								}
								if(nextDynamicScore != null){
									dynamicScore = nextDynamicScore;
									if(teamNumber != null && teamNumber == 0 && eventType.equals("goal")){
										goals.add(new Goal(null, Integer.valueOf(minute), teamNumber, playerName, dynamicScore, penalty));
										eventType = null;
										eventRelation = null;
										playerRelation = null;
										playerName = null;
										dynamicScore =  null;
										teamNumber = null;
										penalty = null;
									}
								}
								if(nextPlayerRelation != null && nextPlayerName != null && nextPlayerRelation.equals(eventRelation)){
									playerName = nextPlayerName;
									playerRelation = nextPlayerRelation;
									if(playerRelation.equals("right")){
										teamNumber = 0;
									}else{
										teamNumber = 1;
									}
									if(eventType.equals("goal")){
										penalty = 0;
										if(playerName.matches(".*"+PENALTY_PATTERN)){
											playerName = playerName.replaceAll(PENALTY_PATTERN, "");
											log.debug("penalty");
											penalty = 1;
										}
										if(teamNumber == 1){
											goals.add(new Goal(null, Integer.valueOf(minute), teamNumber, playerName, dynamicScore, penalty));
											minute = null;
										}
									}else if(eventType.equals("goal-miss")){
										failedPenalties.add(new FailedPenalty(null, playerName, Integer.valueOf(minute), teamNumber));
									}else if(eventType.equals("yellowcard")){
										cards.add(new Card(null, Integer.valueOf(minute), ECardType.YELLOW, playerName, teamNumber));
									}else if(eventType.equals("redcard")){
										cards.add(new Card(null, Integer.valueOf(minute), ECardType.RED, playerName, teamNumber));
									}else if(eventType.equals("redyellowcard")){
										cards.add(new Card(null, Integer.valueOf(minute), ECardType.YELLOW, playerName, teamNumber));
										cards.add(new Card(null, Integer.valueOf(minute), ECardType.RED, playerName, teamNumber));
									}
									if(!eventType.equals("goal") || teamNumber == 1){
										eventType = null;
										eventRelation = null;
										playerRelation = null;
										playerName = null;
										dynamicScore =  null;
										teamNumber = null;
										penalty = null;
									}
								}
							}
							
							if(teamName1!=null && teamName2!=null && nextTournament!=null
									&& score!=null && duration!=null && gdate!=null)
								try {
									gameResultService.saveGameResult(teamName1, teamName2, nextTournament, score, duration, gdate, goals, cards, failedPenalties);
									gameInserted++;
								} catch (Exception e) {
									if(e instanceof ScoreValidationException){
										log.warn(e.getMessage());
										invalidScoreCount++;
									}else if(e instanceof DataIntegrityViolationException){
										log.debug(e.getMessage());
										gameFound++;
									}else{
										log.error(ExceptionUtils.getFullStackTrace(e));
										insertionError++;
										
									}
								}
							duration = null;
							teamName1 = null;
							teamName2 = null;
							score = null;
							goals = new ArrayList<Goal>();
							cards = new ArrayList<Card>();
							failedPenalties = new ArrayList<FailedPenalty>();
							
							
							
	
						} catch (Exception e1) {
							log.warn("Error: " + ExceptionUtils.getFullStackTrace(e1));
						}
						

					}
				}
	
			} catch (Exception e) {
				log.warn(e.getMessage());
			}
		}
		log.warn("Tournament ignored: " + CollectionUtils.subtract(tournamentAll, tournamentFound));
		return "GAMES inserted: " + gameInserted 
			+ " found: " + gameFound 
			+ " invalid score: " + invalidScoreCount 
			+ " insertion error: " + insertionError
			+ " TOURNAMENT found: " + tournamentFound.size()
			+ " ignored: " + (tournamentAll.size() - tournamentFound.size());
			
	}

	private String getTournamentLink(String land, String tournamentName)
			throws DataTransferException {
		String link = getLandDialogLink(land);
		String livescoreDialogContent = getLivescoreDialogContent(link);
		if (livescoreDialogContent == null) {
			throw new CountryNotFoundException(land);
		}
		// System.out.println(answer.replaceAll(">", ">\n"));
		String tournamentLink = findTournamentLinkInDialogContent(land,
				tournamentName, livescoreDialogContent);
		if (tournamentLink == null) {
			throw new TournamentNotFoundException(tournamentName);
		}
		return tournamentLink;
	}

	private String getLandDialogLink(String land) {
		return SOCCER_URL_PREFIX + land;
	}

	
	private String findTournamentLinkInDialogContent(String land,
			String tournamentName, String dialogContent) {
		String regExp = buildLandDialogParserRegExp(land, tournamentName);
		return getMatchesFirstGroup(regExp, dialogContent);
	}

	private String buildLandDialogParserRegExp(String land, String tournamentName) {
		String regExpTournamentLink = "href=\"("+ SOCCER_URL_PREFIX + land + "/[^\"]+)\"\\s+title=\""+tournamentName+"\"";
		return regExpTournamentLink;
	}
	
	
	private String getTournamentDialogContent(String tournamentLink){
		return getLivescoreDialogContent(tournamentLink);
	}



	private String buildTournamentDialogParserRegExp(String land) {
		String regExp;
		String regExpGameLink = buildGameLinkRegExp(land);
		String regExpDate = buildGameDateRegExp();
		regExp = "(" + regExpDate + ")|(" + regExpGameLink + ")";
		return regExp;
	}

//	<a href="/soccer/ireland/1-division/wexford-youths-vs-cobh-ramblers/1-1404385/" class="scorelink">
	private String buildGameLinkRegExp(String land) {
		return "href=\"("+ SOCCER_URL_PREFIX + land + "/[^\"]+)\"\\s+class=\"scorelink\"";
	}
	
	//<span class="date">	August 16</span>
	private String buildGameDateRegExp() {
		String regExpDate = "<span class=\"date\">\\s*(\\w+\\s\\d{1,2})\\s*</span>";
		return regExpDate;
	}

	private Date parseGameDate(String gameDateAsString) throws ParseException {
		Date gdate = null;
		log.debug(gameDateAsString);
		gdate = new SimpleDateFormat("MMM dd yyyy", Locale.ENGLISH).parse(gameDateAsString + " " + new GregorianCalendar().get(Calendar.YEAR));
		if(gdate.after(new Date())){
			GregorianCalendar calendar = new GregorianCalendar();
			calendar.setTime(gdate);
			calendar.add(Calendar.YEAR, -1);
			gdate = calendar.getTime();
		}
		log.debug(gdate);
		return gdate;
	}

	
	
	private String getLivescoreDialogContent(String link){
		return getDialogContent(getLivescoreUrl(link));
	}
	
	private URL getLivescoreUrl(String link) {
		URL url = null;
		try {
			url = new URL(PROTOCOL, HOST, link);
		} catch (MalformedURLException e) {
			throw new LivescoreParserException(e);
		}
		return url;
	}

	private String getDialogContent(URL url) {
		String answer = null;
		InputStream inputStream = null;
		try {
			inputStream = url.openStream();
			answer = new String(FileCreateCopy.getFileContent(inputStream));
		} catch (IOException e) {
			log.error("Can't open URL: " + url);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					log.error("Can't close inputStream.");
				}
			}
		}
		return answer;
	}

	
	private String getMatchesFirstGroup(String expWithGroup, String text){
		String foundGroup = null;
		Pattern pattern = Pattern.compile(expWithGroup);
		Matcher matcher = pattern.matcher(text);
		if(matcher.find()){
			foundGroup = matcher.group(1);
		}

		log.debug("foundGroup: " + foundGroup);
		return foundGroup;

	}
	
	private String removeWhitespaces(String text){
		return text != null ? text.replaceAll("\\s", "") : null;
	}
	
	private String decodeAmpersand(String text){
		return text != null ? text.replaceAll("&amp;", "&") : null;
	}

}

package org.pt.bet.service;

import java.io.InputStream;
import java.net.URL;
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
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mw.bi.util.FileCreateCopy;
import org.pt.bet.domain.Card;
import org.pt.bet.domain.ECardType;
import org.pt.bet.domain.FailedPenalty;
import org.pt.bet.domain.Goal;
import org.pt.bet.domain.Tournament;
import org.pt.bet.exception.ScoreValidationException;
import org.springframework.dao.DataIntegrityViolationException;


public class LivescoreInsertDataServiceImpl implements ILivescoreInsertDataService {

	private static Log log = LogFactory.getLog(LivescoreInsertDataServiceImpl.class);
	
	private static final String PROTOCOL = "http";
	//private static final String HOST = "www.livescore.com";
	private static final String HOST = "livescore.com";
	
	private static final String PENALTY_PATTERN = "\\(pen\\.\\)";
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
		for(int i=0; i<1; i++){
///		for(int i=0; i<tournamentList.size(); i++){
			Tournament nextTournament = tournamentList.get(i);
//			if(nextTournament.getPk().intValue()!=30)
//				continue;
			
			URL url = null;
			String commonUrlString = "/default.dll";
			String pageUrl = "?page=";
			
			String land = nextTournament.getCountry().getName().replaceAll(" ", "");
//			String urlString = commonUrlString + pageUrl + land;
			String landUrl = "";
			String answer = null;
			Pattern pattern;
			Matcher matcher;
			String regExp;
			InputStream inputStream = null;
			try {
				/*
				url = new URL(PROTOCOL, HOST, urlString);
				inputStream = url.openStream();
				answer = new String(FileCreateCopy.getFileContent(inputStream));
				regExp = "page=(" + land + "&sid=[^\"]+)\"";
				pattern = Pattern.compile(regExp);
				matcher = pattern.matcher(answer);
				if (matcher.find()) {
					landUrl = matcher.group(1);
				}
				*/
				landUrl = land;
				url = new URL(PROTOCOL, HOST, commonUrlString + pageUrl + landUrl);
	
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			try {
				inputStream = url.openStream();
				answer = new String(FileCreateCopy.getFileContent(inputStream));
				//System.out.println(answer.replaceAll(">", ">\n"));
				answer = answer.replaceAll(">(\\d+\\s-\\s\\d+)<.+?>([^<]{2,50})<", ">- $1 -$2<");
				answer = answer.replaceAll(">([^<]+)<[^<]+<[^<]+(<a[^>]+?/[Gg]ame\\?comp=[^>]+?>)",">$2$1");
				answer = answer.replaceAll(">([^<]+)<[^<]+<[^<]+(- 0 - 0 -)",">$1$2");
				System.out.println(answer.replaceAll(">", ">\n"));
			} catch (Exception e) {
				log.error(e.getMessage());
			}
	
			try {
				String regExpLink = "(/[Gg]ame\\?comp=[^\"]+)\"";
				String regExpLandTournament = "&nbsp;<b>\\s*\\w+\\s{0,1}\\w*</b>\\s+-\\s+([^<]+)</td>";
				String regExpTournamentNew = "<a[^<]+>\\s*([^<]+?)\\s*\\(Tabelle\\)</a>";
				String regExpDate = ">(\\w+\\s\\d{1,2})&nbsp;</td>";
				String regExpFoolTime = "&nbsp;(A{0,1}[F|E]T)</td>";
				String regExpScore = ">([^>]+)-\\s(\\d+\\s-\\s\\d+)\\s-([^<]+)<";
				regExp = "(" + regExpLandTournament + ")|(" + regExpTournamentNew + ")|(" + regExpDate + ")|("
						+ regExpFoolTime + ")|(" + regExpScore + ")|(" 
						+ regExpLink + ")";
				pattern = Pattern.compile(regExp);
				matcher = pattern.matcher(answer);
				boolean parse = false;
				String duration = null;
				Date gdate = null;
				String teamName1 = null;
				String teamName2 = null;
				String score = null;
				List<Goal> goals = new ArrayList<Goal>();
				List<Card> cards = new ArrayList<Card>();
				List<FailedPenalty> failedPenalties = new ArrayList<FailedPenalty>();
				while (matcher.find()) {
					// System.out.println(matcher.group(1));
					if (matcher.group(2) != null || matcher.group(4) != null){
						String tournamentName = StringUtils.defaultString(matcher.group(2), matcher.group(4)).trim();
						tournamentAll.add(nextTournament.getCountry().getName() + "/" + tournamentName);
						if(tournamentName.equals(tournamentList.get(i).getName())){
							log.info("Scan: " + tournamentList.get(i));
							tournamentFound.add(nextTournament.getCountry().getName() + "/" + tournamentName);
							parse =true;
						}else{
							parse = false;
						}
						gdate = null;
					}
					if(!parse){
						continue;
					}
					if (matcher.group(6) != null){
						String dateString = matcher.group(6);
						log.debug(dateString);
						gdate = new SimpleDateFormat("MMM dd yyyy", Locale.ENGLISH).parse(dateString+" "+new GregorianCalendar().get(Calendar.YEAR));
						if(gdate.after(new Date())){
							GregorianCalendar calendar = new GregorianCalendar();
							calendar.setTime(gdate);
							calendar.add(Calendar.YEAR, -1);
							gdate = calendar.getTime();
						}
						log.debug(gdate);
					}
					if (matcher.group(8) != null){
						duration = matcher.group(8);
						log.debug(duration);
					}
					if (matcher.group(10) != null){
						log.debug(matcher.group(10) + " " + matcher.group(11) + " " + matcher.group(12));
						teamName1 = matcher.group(10).replaceAll("&amp;", "&");
						score = matcher.group(11).replaceAll(
								"\\s", "");
						teamName2 = matcher.group(12).replaceAll("&amp;", "&");

						if(teamName1!=null && teamName2!=null && nextTournament!=null
								&& score!=null && duration!=null && gdate!=null)
							try {
///								gameResultService.saveGameResult(teamName1, teamName2, nextTournament, score, duration, gdate, goals, cards, failedPenalties);
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

					
					}
					if (matcher.group(14) != null) {
						url = new URL(PROTOCOL, HOST, commonUrlString
								+ matcher.group(14));
	//					System.out
	//							.println("*************************************************************************");
						try {
							inputStream = url.openStream();
							answer = new String(FileCreateCopy
									.getFileContent(inputStream));
	
							//System.out.println(answer.replaceAll("/tr>", "/tr>\n"));

							String regExpScore_ = ">([^><]+)\\[(\\d+\\s-\\s\\d+)\\]([^><]+)<";
							Pattern pattern1 = Pattern.compile(regExpScore_);
							Matcher matcher1 = pattern1.matcher(answer);
							// Teams, endstand
							if (matcher1.find()) {
								log.debug(matcher1.group(1).trim());
								if(teamName1 == null)
									teamName1 = matcher1.group(1).replaceAll("&amp;", "&").trim();
								log.debug(matcher1.group(2).replaceAll(
										"\\s", ""));
								if(score == null)
									score = matcher1.group(2).replaceAll(
											"\\s", "");
								log.debug(matcher1.group(3).trim());
								if(teamName2 == null)
									teamName2 = matcher1.group(3).replaceAll("&amp;", "&").trim();
							}
							String regExpLine = "<tr.*?((yellow)|(red)|(yellow-red)|(nogoal)|(goal)).*?</tr>";
							Pattern patternLine = Pattern.compile(regExpLine);
							Matcher matcherLine = patternLine.matcher(answer);
							while(matcherLine.find()){
								List<String> gifs = new ArrayList<String>();
								List<String> names = new ArrayList<String>();
								List<Integer> teamNummer = new ArrayList<Integer>();
								int minute = 0;
								String nextScore = null;
								String nextLine = matcherLine.group().trim();
								
								//System.out.println(nextLine);
								String regExpGif = "(\\w+)\\.gif";
								Pattern patternGif = Pattern.compile(regExpGif);
								Matcher matcherGif = patternGif.matcher(nextLine);
								while(matcherGif.find()){
									log.debug(matcherGif.group(1));
									gifs.add(matcherGif.group(1));
								}
								String regExpMin = "(\\d+)'";
								Pattern patternMin = Pattern.compile(regExpMin);
								Matcher matcherMin = patternMin.matcher(nextLine);
								if(matcherMin.find()){
									log.debug(matcherMin.group(1));
									minute = Integer.parseInt(matcherMin.group(1));
								}
								String regExpNextScore = "(\\d+\\s-\\s\\d+)";
								Pattern patternScore = Pattern.compile(regExpNextScore);
								Matcher matcherScore = patternScore.matcher(nextLine);
								if(matcherScore.find()){
									log.debug(matcherScore.group(1).replaceAll("\\s", ""));
									nextScore = matcherScore.group(1).replaceAll("\\s", "");
								}
								String regExpName = "<td([^>]+?)>([^<]+?)<img";
								Pattern patternName = Pattern.compile(regExpName);
								Matcher matcherName = patternName.matcher(nextLine);
								while(matcherName.find()){
									if(matcherName.group(1).indexOf("left")>0){
										log.debug("first team");
										teamNummer.add(new Integer(0));
									}else{
										log.debug("second team");
										teamNummer.add(new Integer(1));
									}
									log.debug(matcherName.group(2).replaceAll("\\s", ""));

									names.add(matcherName.group(2).replaceAll("\\s", ""));
								}
								if(gifs.size() == names.size() && gifs.size() == teamNummer.size() && minute>0){
									for(int j = 0; j<gifs.size(); j++){
										String nextGif = gifs.get(j);
										String player = names.get(j);
										byte teamNr = teamNummer.get(j).byteValue();
										byte penalty = 0;
										if(nextGif.equals("goal")){
											if(player.matches(".*"+PENALTY_PATTERN)){
												player = player.replaceAll(PENALTY_PATTERN, "");
												log.debug("penalty");
												penalty = 1;
											}else{
												penalty = 0;
											}
											goals.add(new Goal(null, new Integer(minute), new Byte(teamNr), player, nextScore, new Byte(penalty)));
										}else if(nextGif.equals("nogoal")){
											failedPenalties.add(new FailedPenalty(null, player, new Integer(minute), new Byte(teamNr)));
										}else if(nextGif.equals("yellow")){
											cards.add(new Card(null, new Integer(minute), ECardType.YELLOW, player, new Byte(teamNr)));
										}else if(nextGif.equals("red")){
											cards.add(new Card(null, new Integer(minute), ECardType.RED, player, new Byte(teamNr)));
										}else if(nextGif.equals("yellow-red")){
											cards.add(new Card(null, new Integer(minute), ECardType.YELLOW, player, new Byte(teamNr)));
											cards.add(new Card(null, new Integer(minute), ECardType.RED, player, new Byte(teamNr)));
										}
									}
								}
							}
							
							
							
	
						} catch (Exception e1) {
							log.warn("Error: " + e1.getMessage());
							parse = false;
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




}

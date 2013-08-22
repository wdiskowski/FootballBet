package org.mw.bi.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.OrderedMap;
import org.apache.commons.collections.map.LinkedMap;
import org.apache.log4j.Logger;

public class Options {

	public static final Logger logger = Logger
			.getLogger(Option.class.getName());

	private List<Option> optionsList;
	
	private OrderedMap groupMap = new LinkedMap();
	

	public Options() {
		logger.debug(">");
		this.optionsList=new ArrayList<Option>();
		logger.debug("<");
	}

	public Options(List<Option> optionsList) {
		logger.debug(">");
		this.optionsList=optionsList;
		logger.debug("<");
	}

	public Options(String[][] optionsArray) {
		logger.debug(">");
		this.optionsList=new ArrayList<Option>();
		if(NoNull.noNullLength(optionsArray)>1){
			if(NoNull.noNullLength(optionsArray[0])>0
					&& NoNull.noNullLength(optionsArray[1])>0){
				for(int i=0; i<Math.min(optionsArray[0].length, optionsArray[1].length);i++){
					Option option =new Option(optionsArray[0][i],optionsArray[1][i]);
					optionsList.add(option);
				}
			}
		}
//		this.optionsList=optionsList;
		logger.debug("<");
	}
	
	
	public List<Option> getOptionsList() {
		logger.debug("<>");
		return optionsList;
	}

	public void setOptionsList(List<Option> optionsList) {
		logger.debug(">");
		this.optionsList = optionsList;
		logger.debug("<");
	}

	public OrderedMap getGroupMap() {
		return this.groupMap;
	}

	public void setGroupMap(OrderedMap groupMap) {
		this.groupMap = groupMap;
	}

	public void addOption(Option option) {
		logger.debug(">");
		addOption(NoNull.noNullSize(optionsList),option);
		logger.debug("<");
	}

	public void addOption(int optionNummer, Option option) {
		logger.debug(">");
		if (option != null) {
			if (optionsList == null)
				optionsList = new ArrayList<Option>();
			if (optionNummer < 0)
				optionNummer = 0;
			else if (optionNummer > optionsList.size())
				optionNummer = optionsList.size();
			optionsList.add(optionNummer, option);
		}
		logger.debug("<");
	}
	
	// common-collections in version 3.2.1 is not generic. 
	// In next version group map will be Map<String, Options>. So put is correct
	@SuppressWarnings("unchecked")
	public void addGroup(Options options, String groupKey){
		logger.debug(">");
		groupMap.put(groupKey, options);
		logger.debug("<");
	}
	
	public void addAll(Options options) {
		logger.debug(">");
		if (options != null && options.getOptionsList()!=null) {
			if (optionsList == null)
				optionsList = new ArrayList<Option>();
			optionsList.addAll(options.getOptionsList());
		}
		logger.debug("<");
	}
	
	public void removeOption(int optionNummer) {
		logger.debug(">");
		if (optionNummer >= 0 && NoNull.noNullSize(optionsList) > optionNummer)
			optionsList.remove(optionNummer);
		logger.debug("<");
	}

	public void removeOption(Option option) {
		logger.debug(">");
		optionsList.remove(searchOption(option));
		logger.debug("<");
	}

	public void removeOption(String text) {
		logger.debug(">");
		optionsList.remove(new Option(text));
		logger.debug("<");
	}
	
	
	public void removeLastOption() {
		logger.debug(">");
		optionsList.remove(NoNull.noNullSize(optionsList)-1);
		logger.debug("<");
	}
	
	public boolean hasOption(Option option){
		logger.debug("<>");
		return (searchOption(option)>0);
	}

	public boolean hasOption(String text){
		logger.debug("<>");
		return hasOption(new Option(text));
	}
	
	public void selectOption(int optionNummer){
		logger.debug(">");
		if(optionNummer>=0 && optionNummer<NoNull.noNullSize(optionsList))
			for (int i = 0; i < NoNull.noNullSize(optionsList); i++){
				if(i!=optionNummer)
					optionsList.get(i).setSelected(false);
				else
					optionsList.get(i).setSelected(true);
			}
		logger.debug("<");
		
	}
	
	public void selectOption(Option option){
		logger.debug(">");
		selectOption(searchOption(option));
		logger.debug("<");
	}

	public void selectOption(String text){
		logger.debug(">");
		selectOption(searchOption(new Option(text)));
		logger.debug("<");
	}
	
	
	private int searchOption(Option option) {
		logger.debug(">"+option);
		int selectedOption=-1;
		for (int i = 0; i < NoNull.noNullSize(optionsList); i++) {
			if (option.equals(optionsList.get(i))){
				selectedOption=i;
				break;
			}
		}
		logger.debug("<"+selectedOption);
		return selectedOption;
	}
	
	public String getOptions(){
		logger.debug(">");
		String htmlOptions="";
		for (Option iterOption: optionsList) {
			htmlOptions+="<OPTION ";
			if(iterOption.getValue()!=null)
				htmlOptions+="value='"+iterOption.getValue()+"' ";
			if(iterOption.isSelected())
				htmlOptions+="selected";
			htmlOptions+=">"+iterOption.getText()+"</OPTION>\n";	
		}
		logger.debug("<");
		return htmlOptions;
	}
	
	public String getOptions(String id){
		logger.debug(">");
		String htmlOptions="<SELECT id='" + id + "' " +
		"name='" + id + "'>\n";
		htmlOptions += getOptions();
		htmlOptions+="</SELECT>\n";
		logger.debug("<");
		return htmlOptions;
	}	
	public String getOptions(String id, int size){
		logger.debug(">");
		String htmlOptions="<SELECT id='" + id + "' " +
		"name='" + id + "' size='" + size + "'>\n";
		htmlOptions += getOptions();
		htmlOptions+="</SELECT>\n";
		logger.debug("<");
		return htmlOptions;
	}
	
	public String getOptions(String id, String name, int size){
		logger.debug(">");
		String htmlOptions="<SELECT id='" + id + "' " +
		"name='" + name + "' size='" + size + "'>\n";
		htmlOptions += getOptions();
		htmlOptions+="</SELECT>\n";
		logger.debug("<");
		return htmlOptions;
	}

	
	public String getNestedOptions(){
		logger.debug(">");
		String htmlNestedOptions=getOptions();
		if(!groupMap.isEmpty()){
			String groupKey = (String)groupMap.firstKey();
			while (groupKey != null) {
				Options nextOptions =(Options)groupMap.get(groupKey);
				htmlNestedOptions += "<optgroup label='" + groupKey +"'>";
				htmlNestedOptions += nextOptions.getOptions();
				htmlNestedOptions+="</optgroup>\n";
				groupKey = (String)groupMap.nextKey(groupKey);
			}
		}
		logger.debug("<");
		return htmlNestedOptions;
	}
	
	public boolean isEmpty(){
		logger.debug("<>");
		return (this.optionsList.size() == 0);
	}
	
	
}

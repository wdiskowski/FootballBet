package org.mw.bi.util;

import org.apache.log4j.Logger;

public class Option {

	private final String value;

	private final String text;

	private boolean selected;
	
	public static final String OPTION_NOT_SELECTED = "Not selected";

	public static final Logger logger = Logger
			.getLogger(Option.class.getName());

	public Option(String value, String text, boolean selected) {
		logger.debug(">");
		this.selected = selected;
		this.text = text;
		this.value = value;
		logger.debug("<");
	}

	public Option(String value, String text) {
		logger.debug(">");
		this.text = text;
		this.value = value;
		this.selected = false;
		logger.debug("<");
	}

	public Option(String text) {
		logger.debug(">");
		this.text = text;
		this.value = "";
		this.selected = false;
		logger.debug("<");
	}

	public Option(int value, String text, boolean selected) {
		logger.debug(">");
		this.selected = selected;
		this.text = text;
		this.value = String.valueOf(value);
		logger.debug("<");
	}

	public Option(int value, String text) {
		logger.debug(">");
		this.text = text;
		this.value = String.valueOf(value);
		this.selected = false;
		logger.debug("<");
	}

	
	public String getText() {
		logger.debug("<>");
		return text;
	}

	public String getValue() {
		logger.debug("<>");
		return value;
	}

	public boolean isSelected() {
		logger.debug("<>");
		return selected;
	}

	public void setSelected(boolean selected) {
		logger.debug(">");
		this.selected = selected;
		logger.debug("<");
	}

	public boolean equals(Option option) {
		logger.debug("<>");
		return (text!=null && text.equals(option.getText()));
	}

	public String toString() {
		logger.debug("<>");
		return "text: "+text+" value: "+value+" selected: "+selected;
	}

	
}

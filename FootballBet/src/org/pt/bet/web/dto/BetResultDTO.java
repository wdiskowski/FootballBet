package org.pt.bet.web.dto;

import java.text.NumberFormat;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.log4j.Logger;

public class BetResultDTO {

	private Integer level;
	private Number rate;
	private Number basis;
	private Integer minute;
	
	private NumberFormat rateFormat;
	
    static Logger logger = Logger.getLogger(BetResultDTO.class.getName());
	
	public BetResultDTO(Integer level, Integer minute, Number rate, Number basis) {
		super();
		this.level = level;
		this.rate = rate;
		this.basis = basis;
		this.minute = minute;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Number getRate() {
		return rate;
	}
	public String getRateAsString() {
		return (rate!=null?rateFormat.format(rate):"-");
	}
	public void setRate(Number rate) {
		this.rate = rate;
	}
	public Number getBasis() {
		return basis;
	}
	public void setBasis(Number basis) {
		this.basis = basis;
	}
	public Integer getMinute() {
		return minute;
	}
	public void setMinute(Integer minute) {
		this.minute = minute;
	}
	
	public String getBackingRate(){
		return (rate!=null?rateFormat.format(rate.doubleValue()/(rate.doubleValue()-1.)):"-");
	}
	public void setRateFormat(NumberFormat rateFormat) {
		this.rateFormat = rateFormat;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}
}

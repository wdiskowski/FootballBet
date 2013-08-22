<%@ page language="java" session="true" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="org.pt.bet.web.dto.BetResultDTO"%>
<% 
	// In Controllers is setting List of BetResultDTO as request attribute. So cast is correct
	@SuppressWarnings("unchecked")
	List<BetResultDTO> rateList = (List<BetResultDTO>)request.getAttribute("rateList");
%>

			   	<table border="1">
		    		<col width="20">
		    		<col width="20">
		    		<col width="40">
		    		<col width="40">
		    		<col width="40">
		    		<tr>
		     			<th>lev 
		     			</th>
		     			<th>min 
		     			</th>
		     			<th>k 
		     			</th>
		     			<th>k<sup>-1</sup> 
		     			</th>
		     			<th>basis 
		     			</th>
		    		</tr>
<%  
	for(BetResultDTO nextBetResult : rateList){
%>		    		
		    		<tr>
		    			<td><%=nextBetResult.getLevel().intValue() %></td>
		    			<td><%=nextBetResult.getMinute().intValue() %></td>
		    			<td class="bold"><%=nextBetResult.getRateAsString() %></td>
		    			<td><%=nextBetResult.getBackingRate() %></td>
		    			<td><%=nextBetResult.getBasis() %></td>
		     		</tr>
<% 
	}
%>
		    	</table>

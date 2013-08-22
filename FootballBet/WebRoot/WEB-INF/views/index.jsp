<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
	<meta name="author" content="Volodymyr Diskovskyy, ModulWare (http://www.modulware.de)">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	
	<title>Football home</title>
	
	<script language="javascript" src="script/jquery-1.4.4.js"></script>
	<script language="javascript" src="script/js_lib.js"></script>
	
	<style type="text/css">
      body {background-color: #a9a9a9;}
      input, select, td, th {font-size: 12px}
      input.textinput {width: 30px;}
      .gray {background-color: gray; border-width: 1px; border-style: solid; color: white;}
      .bold {font-weight: bold;}
      .result {width: 150px; height: 100px; display: block; border-width: 1px; border-style: solid; border-color: gray; background-image: url("image/ball2_a.gif"); background-position: center; background-repeat: no-repeat;}
      select#countryKey {width: 100px;}
      select#teamKey {width: 100px;}
    </style>
  </head>
  <body>
 	<table border="1">
    		<col width="270">
    		<col width="270">

		<tr>
			<td>

			   	<table border="0">
		    		<col width="130">
		    		<col width="130">
		    		<tr>
		     			<th colspan="2">actions
		     			</th>
		    		</tr>
		    		<tr>
		    			<td><input type="button" id="refreshStatistic" value="refresh statistic"  />
		    			</td>
		    			<td>
						    <input type="button" id="insertData" value="livescore insert"  />
						    <img id="ball" src="image/ball2.gif" />
		    			</td>
		     		</tr>
		    	</table>
 
			</td>
			<td align="center">
				<span id="gameStatistic"><img src="image/ball2_a.gif" /></span>
			</td>
		</tr>
	</table>   	
    	

   	<hr />
    <form id="goalCountForm" action="#">
 
   	
	<table border="1">
    		<col width="270">
    		<col width="270">

		<tr>
			<td colspan="2" align="left">
 
		    	<table border="1">
		    		
					    		<tr>
					     			<th >competitionType: </th>
					     			<th >from: </th>
					     			<th >to: </th>
					     			<th class="gray">goal (more as): </th>
					     			<th >goal (less as): </th>
					     			<th class="gray">limit k: </th>
					     			<th>template</th>
					     			<th>country/team</th>
					    			
					    		</tr>
					    		<tr>
					    			<td>
					    				<select name="competitionType" id="competitionType" > 
					    					<option value="championship" selected="selected" >championship</option>
					    					<option value="coup"  >coup</option>
					    					<option value="">all</option>
					    				</select>
					    			</td>
					    			
					    			<td><input name="fromMinute" id="fromMinute" type="text" class="textinput" value="1" /></td>
					    			<td><input name="toMinute" id="toMinute" type="text" class="textinput" value="31" /></td>
					    			<td>&nbsp;</td>
					    			<td><input name="goalCount" id="goalCount" type="text" class="textinput" value="1" /></td>
					    			<td class="gray"><input name="limitK" id="limitK" class="textinput" type="text" value="1.5" /></td>
					     			<td>
					    				<select name="template" id="template"> 
					    					<option value="1_16" >1_16</option>
					    					<option value="16_31" >16_31</option>
					    					<option value="31_46" >31_46</option>
					    					<option value="46_61" selected="selected" >46_61</option>
					    					<option value="61_76" >61_76</option>
					    					<option value="76_91" >76_91</option>
					    				</select>
					     			</td>
					     			<td>
					     				${countryOptions}
					     			</td>
					     		</tr>
					    		<tr>
					    			<td>add condition</td>
					    			
					    			<td class="gray"><input name="conditionFromMinute" id="conditionFromMinute" type="text" class="textinput" value="0" /></td>
					    			<td class="gray"><input name="conditionToMinute" id="conditionToMinute" type="text" class="textinput" value="31" /></td>
					    			<td class="gray"><input name="conditionGoalCountFrom" id="conditionGoalCountFrom" type="text" class="textinput" value="-1" /></td>
					    			<td class="gray"><input name="conditionGoalCountTo" id="conditionGoalCountTo" type="text" class="textinput" value="1" /></td>
					    			<td>&nbsp;</td>
					    			<td><span id="goalCountReduce" style="float: left; cursor: pointer;">&lt;&lt;</span><span id="goalCountIncrease" style="float: right; cursor: pointer;">&gt;&gt;</span></td>
					    			<td><span id='teamOptionsTarget'></span></td>
					     		</tr>
		    	</table>
			</td>
		
		</tr>
		<tr>
			<td align="center">
			    <input id="sendSimple" type="button" value="send simple">
			    <span id="simpleRateTarget">
			    	<span class="result"></span>
			    </span>
			</td>
			<td align="center">
				<input id="sendLimitK" type="button" value="send limit k">
			    <span id="limitKRateTarget">
			    	<span class="result"></span>
			    </span>
			</td>
		</tr>
		<tr>
			<td align="center">
			    <input id="sendSimpleWithCondition" type="button" value="send simple">
			    <span id="simpleRateWithConditionTarget">
			    	<span class="result"></span>
			    </span>
			</td>
			<td align="center">
				<input id="sendLimitKWithCondition" type="button" value="send limit k">
			    <span id="limitKRateWithConditionTarget">
			    	<span class="result"></span>
			    </span>
			</td>
		</tr>
	</table>   	
    </form>


   	<hr />
    <form id="redCardForm" action="#">
 
   	
	<table border="1">
    		<col width="270">
    		<col width="270">

		<tr>
			<td colspan="2" align="left">
 
		    	<table border="1">
		    		
					    		<tr>
					     			<th >competitionType: </th>
					     			<th >from: </th>
					     			<th >to: </th>
					     			<th >yellow card: </th>
					     			<th class="gray">limit k: </th>
					     			<th>country</th>
					     			<th>team</th>
					    			
					    		</tr>
					    		<tr>
					    			<td>
					    				<select name="competitionType" id="competitionTypeRedCard" > 
					    					<option value="championship" selected="selected" >championship</option>
					    					<option value="coup"  >coup</option>
					    					<option value="">all</option>
					    				</select>
					    			</td>
					    			
					    			<td><input name="fromMinute" id="fromMinuteRedCard" type="text" class="textinput" value="1" /></td>
					    			<td><input name="toMinute" id="toMinuteRedCard" type="text" class="textinput" value="91" readonly="readonly" /></td>
					    			<td><input name="conditionYellowCount" id="conditionYellowCount" type="text" class="textinput" value="1" /></td>
					    			<td class="gray"><input name="limitK" id="limitKRedCard" class="textinput" type="text" value="1.5" /></td>
					     			<td>
					     				${countryOptionsRedCard}
					     			</td>
					     			<td><span id='teamOptionsTargetRedCard'></span></td>
					     		</tr>
		    	</table>
			</td>
		
		</tr>

		<tr>
			<td align="center">
			    <input id="sendSimpleRedCardWithCondition" type="button" value="send simple red card">
			    <span id="simpleRedCardWithConditionTarget">
			    	<span class="result"></span>
			    </span>
			</td>
			<td align="center">
				<input id="sendLimitKRedCardWithCondition" type="button" value="send limit k red card">
			    <span id="limitKRedCardWithConditionTarget">
			    	<span class="result"></span>
			    </span>
			</td>
		</tr>
	</table>   	
    </form>
     
    <script> 
    	var template = new Object();
    	template['1_16'] = new Array('0', '16', '1', '1.4', '0', '16', '-1', '1' );
    	template['16_31'] = new Array('15', '31', '1', '1.4', '0', '16', '-1', '1' );
    	template['31_46'] = new Array('30', '46', '1', '1.4', '0', '31', '-1', '1' );
    	template['46_61'] = new Array('45', '61', '1', '1.4', '0', '46', '-1', '1' );
    	template['61_76'] = new Array('60', '76', '1', '1.4', '0', '61', '-1', '1' );
    	template['76_91'] = new Array('75', '91', '1', '1.4', '0', '76', '-1', '1' );
    	
    	var getTemplate = function(){
    		var currentTemplate = template[$("#template").attr("options")[$("#template").attr("selectedIndex")].value];
    		$('#fromMinute').val(currentTemplate[0]);
    		$('#toMinute').val(currentTemplate[1]);
    		$('#goalCount').val(currentTemplate[2]);
    		$('#limitK').val(currentTemplate[3]);
   			$('#conditionFromMinute').val(currentTemplate[4]);
    		$('#conditionToMinute').val(currentTemplate[5]);
    		$('#conditionGoalCountFrom').val(currentTemplate[6]);
    		$('#conditionGoalCountTo').val(currentTemplate[7]);
    	}
		var getTeamOptions = function(){
			$('#teamOptionsTarget').load('teamoptions.disp', { countryKey: $('#countryKey').val() });
		}
    	var getTeamOptionsRedCard = function(){
			$('#teamOptionsTargetRedCard').load('teamoptions.disp', { countryKey: $('#countryKeyRedCard').val() });
		}
    	$('#template').change(getTemplate);
   		$('#template').focus(getTemplate);
   		
   		$('#countryKey').change(getTeamOptions);
   		$('#countryKeyRedCard').change(getTeamOptionsRedCard);
   		
    	
    	$('#goalCountReduce').click(function(){
    		$('#conditionGoalCountFrom').val(+$('#conditionGoalCountFrom').val() - 1);
    		$('#conditionGoalCountTo').val(+$('#conditionGoalCountTo').val() - 1);
    	});

    	$('#goalCountIncrease').click(function(){
    		$('#conditionGoalCountFrom').val(+$('#conditionGoalCountFrom').val() + 1);
    		$('#conditionGoalCountTo').val(+$('#conditionGoalCountTo').val() + 1);
    	});
     	var updateStatistic = function(){
			$('#gameStatistic').load('statistic.disp');
		}
		$('#insertData').click(function(){
    		$('#ball').attr("src", "image/ball2_a.gif");
			$.post("insertdata.disp",
				function(data){
					$('#ball').attr("src", "image/ball2.gif");
					alert(trim(data));
					updateStatistic();
				});    		
    	});
 
   		$('#sendSimple').click(function(){
			$('#simpleRateTarget').load("goalcount.disp", $('#goalCountForm').serialize());
    	});
   		$('#sendLimitK').click(function(){
			$('#limitKRateTarget').load("goalcount.disp", $('#goalCountForm').serialize() + "&limit=true");
    	});
  		$('#sendSimpleWithCondition').click(function(){
			$('#simpleRateWithConditionTarget').load("goalcountwithcondition.disp", $('#goalCountForm').serialize());
    	});
  		$('#sendLimitKWithCondition').click(function(){
			$('#limitKRateWithConditionTarget').load("goalcountwithcondition.disp", $('#goalCountForm').serialize() + "&limit=true");
    	});
  		$('#sendSimpleRedCardWithCondition').click(function(){
			$('#simpleRedCardWithConditionTarget').load("redcardwithcondition.disp", $('#redCardForm').serialize());
    	});
  		$('#sendLimitKRedCardWithCondition').click(function(){
			$('#limitKRedCardWithConditionTarget').load("redcardwithcondition.disp", $('#redCardForm').serialize() + "&limit=true");
    	});

    	
    	$('#refreshStatistic').click(updateStatistic);
    	
    	
    	$(document).ready(function(){
	     	getTemplate();
 		   	updateStatistic();
 		   	getTeamOptions();
 		   	getTeamOptionsRedCard();
    	});
    </script>
    
  </body>
</html>

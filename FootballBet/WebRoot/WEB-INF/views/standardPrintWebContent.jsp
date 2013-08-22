<%@ page language="java" session="true" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="org.apache.commons.lang.exception.ExceptionUtils" %>
<%
try {
	if(request.getAttribute("errorMessage")!=null)
		throw new Exception((String)request.getAttribute("errorMessage"));
	out.println(request.getAttribute("webContent"));	
} catch (Exception e) {
	out.print(ExceptionUtils.getFullStackTrace(e));
}		
%>

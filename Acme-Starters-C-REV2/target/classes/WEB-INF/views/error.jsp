<%--
- error.jsp
-
- Copyright (C) 2012-2026 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page 
	pageEncoding="utf-8" 
	contentType="text/html; charset=utf-8" 
	isErrorPage="true"%>
	
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>
	
<jstl:set var="locale" value="${pageContext.response.locale.language}"/>

<!DOCTYPE html>
<html lang="${locale}">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Debug error page</title>
</head>
<body>
	<h1>Debug error page</h1>
	
	<p>If you happen to see this page, then there's a serious bug in your application, 
	which is not likely to be easy to track down and diagnose. Sorry, no more info is available.</p>
	
	<p>Status code: <%= response.getStatus() %>.</p>
	<p>Content type: <%= response.getContentType() %>.</p>
	<p>Char encoding: <%= response.getCharacterEncoding() %>.</p>
	<p>Headers: <%= response.getHeaderNames() %>.</p> 	 	
</body>
</html>
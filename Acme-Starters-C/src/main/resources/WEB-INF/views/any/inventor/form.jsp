<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form readonly="true">

	<acme:form-textbox code="any.inventor.form.label.bio" path="name"/>
	
	<acme:form-textarea code="any.inventor.form.label.keyWord" path="description"/>
	
	<acme:form-money code="any.inventor.form.label.cost" path="cost"/>
	
	<acme:form-textbox code="any.inventor.form.label.kind" path="kind"/>

</acme:form>
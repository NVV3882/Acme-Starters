<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form readonly="true">

	<acme:form-textbox code="any.auditSection.form.label.name" path="name"/>
	
	<acme:form-textarea code="any.auditSection.form.label.notes" path="notes"/>
	
	<acme:form-integer code="any.auditSection.form.label.hours" path="hours"/>
	
	<acme:form-textbox code="any.auditSection.form.label.kind" path="kind"/>

</acme:form>
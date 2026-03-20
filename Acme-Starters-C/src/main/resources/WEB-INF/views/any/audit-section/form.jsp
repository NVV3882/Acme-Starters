<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form readonly="true">

	<acme:form-textbox code="any.audit-section.form.label.name" path="name"/>
	
	<acme:form-textarea code="any.audit-section.form.label.notes" path="notes"/>
	
	<acme:form-integer code="any.audit-section.form.label.hours" path="hours"/>
	
	<acme:form-textbox code="any.audit-section.form.label.kind" path="kind"/>

</acme:form>
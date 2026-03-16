<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form readonly="true">

	<acme:form-textbox code="any.auditor.form.label.firm" path="firm"/>
	
	<acme:form-textbox code="any.auditor.form.label.highlights" path="highlights"/>
	
	<acme:form-checkbox code="any.auditor.form.label.solicitor" path="solicitor"/>
	

</acme:form>
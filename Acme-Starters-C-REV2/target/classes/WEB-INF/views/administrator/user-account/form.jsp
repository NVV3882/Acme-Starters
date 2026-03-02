<%--
- form.jsp
-
- Copyright (C) 2012-2026 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="administrator.user-account.form.label.username" path="username" readonly="true"/>
	<acme:form-textbox code="administrator.user-account.form.label.name" path="identity.name" readonly="true"/>
	<acme:form-textbox code="administrator.user-account.form.label.surname" path="identity.surname" readonly="true"/>
	<acme:form-email code="administrator.user-account.form.label.email" path="identity.email" readonly="true"/>
	<acme:form-textbox code="administrator.user-account.form.label.role-list" path="roleList" readonly="true"/>
	<acme:form-checkbox code="administrator.user-account.form.label.status" path="status" readonly="${!canUpdate}}"/>
	
	<jstl:if test="${canUpdate}">			
		<acme:submit code="administrator.user-account.form.button.update" action="/administrator/user-account/update"/>
	</jstl:if>
</acme:form>

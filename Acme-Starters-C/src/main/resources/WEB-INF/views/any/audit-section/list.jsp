<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="any.auditSection.list.label.name" path="name" width="30%"/>
	<acme:list-column code="any.auditSection.list.label.money" path="hours" width="20%"/>
	<acme:list-column code="any.auditSection.list.label.kind" path="kind" width="20%"/>

	<acme:list-hidden path="notes"/>
</acme:list>
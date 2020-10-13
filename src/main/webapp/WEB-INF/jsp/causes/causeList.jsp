<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="causes">
	<h2>Causes</h2>

	<table id="causesTable" class="table table-striped">
		<thead>
			<tr>
				<th>Name</th>
				<th>Current budget</th>
				<th>Budget</th>
				<th>Description</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${causes}" var="cause">
				<tr>
					<td>
					
					<spring:url value="/causes/{causeId}" var="causeUrl">
                        <spring:param name="causeId" value="${cause.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(causeUrl)}" class=""><c:out value="${cause.name}"/></a>
                    </td>
				
					<td><c:out value="${cause.currentBudget}" /></td>
					<td><c:out value="${cause.budget}" /></td>
					
			
						<td><c:out value="${cause.description}" /></td>
				</tr>

			</c:forEach>
		</tbody>
	</table>

	<table class="table-buttons">
		<tr>
			<td><a class="btn btn-default" style="margin-right: 10px" href='<spring:url value="/causes/new" htmlEscape="true"/>'>Add
					Cause</a></td>
		</tr>
	</table>
	
</petclinic:layout>
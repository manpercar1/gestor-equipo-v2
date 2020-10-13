<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="causes">
<h2>Donations</h2>
	<table class="table table-striped">
	
		<thead>
			<tr>
				<th>Client</th>
				<th>Date</th>
				<th>Amount</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${cause.donations}" var="donation">
				<tr>
					<td><c:out value="${donation.client}" /></td>
					<td><c:out value="${donation.donationDate}" /></td>
					<td><c:out value="${donation.amount}" /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
		<spring:url value="/causes/{causeId}" var="causeUrl">
                        <spring:param name="causeId" value="${cause.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(causeUrl)}" class="btn btn-default">Return</a>
</petclinic:layout>
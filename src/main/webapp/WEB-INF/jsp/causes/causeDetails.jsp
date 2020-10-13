<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="cause">

<h2>Cause</h2>
        <table class="table table-striped">
            <thead>
            <tr>
                <th>Name</th>
                <th>Description</th>
                <th>Budget</th>
                <th>Current Budget</th>
                <th>Organization</th>
            </tr>
            </thead>
            <tr>
                <td><c:out value="${cause.name}"/></td>
                <td><c:out value="${cause.description}" /></td>
                <td><c:out value="${cause.budget}"/></td>
                <td><c:out value="${cause.currentBudget}"/></td>
                <td><c:out value="${cause.organization}"/></td>
            </tr>
        </table>

	
	<spring:url value="/causes/list" var="addUrl">
       </spring:url>
   
        <a href="${fn:escapeXml(addUrl)}" class="btn btn-default">Return</a>
        
        
        
        <spring:url value="/causes/{causeId}/donations/{causeId}" var="addU">
         <spring:param name="causeId" value="${cause.id}"/>
       </spring:url>
   
        <a href="${fn:escapeXml(addU)}" class="btn btn-default">Donations</a>
        
        
        
        
      <spring:url value="/causes/{causeId}/donations/new" var="donationUrl">
							<spring:param name="causeId" value="${cause.id}" />
							</spring:url> <a href="${fn:escapeXml(donationUrl)}" class="btn btn-default">New Donation</a>
        
        
</petclinic:layout>

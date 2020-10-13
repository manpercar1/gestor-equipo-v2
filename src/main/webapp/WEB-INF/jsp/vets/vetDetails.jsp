<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="owners">

    <h2>Vet Information</h2>

    <table class="table table-striped">
        <tr>
            <th>Name</th>
            <td><b><c:out value="${vet.firstName} ${vet.lastName}"/></b></td>
            <th>Specialties</th>
            <td><b>
            <c:forEach var="specialty" items="${vet.specialties}">
                        <c:out value="${specialty.name} "/>
            </c:forEach></b>
            </td>
        </tr>
        </table>

    <spring:url value="{vetId}/edit" var="editUrl">
        <spring:param name="vetId" value="${vet.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Edit Vet</a>

<a class="btn btn-default" href='<spring:url value="/vets/new" htmlEscape="true"/>'>Add Vet</a>

<a class="btn btn-default" href='<spring:url value="/vets" htmlEscape="true"/>'>List Vets</a>


</petclinic:layout>

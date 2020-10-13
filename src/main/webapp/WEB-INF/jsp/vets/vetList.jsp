<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="vets">
    <h2>Veterinarians</h2>


    <table id="vetsTable" class="table">
        <thead>
        <tr>
            <th>Name</th>
            <th>Specialties</th>
            <th>Options</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${vets.vetList}" var="vet">
            <tr>
                <td>
                    <c:out value="${vet.firstName} ${vet.lastName}"/>
                </td>
                <td>
                    <c:forEach var="specialty" items="${vet.specialties}">
                        <c:out value="${specialty.name} "/>
                    </c:forEach>
                    <c:if test="${vet.nrOfSpecialties == 0}">none</c:if>
                </td>
              
                <td>
                <spring:url value="/vets/{vetId}/edit" var="vetUrl">
                <spring:param name="vetId" value= "${vet.id}"/>
                </spring:url>
                <a href="${fn:escapeXml(vetUrl)}" class="btn deleteVetButton">Edit Vet</a>
                <br>
                <br>
                 <spring:url value="/vets/{vetId}/delete" var="vetUrl">
                 <spring:param name="vetId" value="${vet.id}"/>
                 </spring:url>
                 <a href="/vets/${vet.id}/delete" class="btn deleteVetButton"  >Delete Vet</a>
                </td>
              
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <table class="table-buttons">
    
    <a class="btn btn-default" href='<spring:url value="/vets/new" htmlEscape="true"/>'>Add Vet</a>
        
        <tr>
            <td>
                <a href="<spring:url value="/vets.xml" htmlEscape="true" />">View as XML</a>
            </td>            
        </tr>
    </table>
</petclinic:layout>
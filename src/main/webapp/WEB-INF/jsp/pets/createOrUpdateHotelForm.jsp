<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="owners">
	
	<jsp:attribute name="customScript">
        <script>
            $(function () {
                $("#startDate").datepicker({dateFormat: 'yy/mm/dd'});
                $("#endDate").datepicker({dateFormat: 'yy/mm/dd'});
            });
        </script>
    </jsp:attribute>

<jsp:body>
        <h2><c:if test="${hotel['new']}"><fmt:message key="newRoom"/></c:if></h2>

        <strong><fmt:message key="pet"/></strong>
        <table class="table table-striped">
            <thead>
            <tr>
                <th><fmt:message key="name"/></th>
                <th><fmt:message key="birthDate"/></th>
                <th><fmt:message key="type"/></th>
                <th><fmt:message key="owner"/></th>
            </tr>
            </thead>
            <tr>
                <td><c:out value="${hotel.pet.name}"/></td>
                <td><petclinic:localDate date="${hotel.pet.birthDate}" pattern="yyyy/MM/dd"/></td>
                <td><c:out value="${hotel.pet.type.name}"/></td>
                <td><c:out value="${hotel.pet.owner.firstName} ${hotel.pet.owner.lastName}"/></td>
            </tr>
        </table>
        
           <br/>
        <h2><fmt:message key="previousRoom"/></h2>
        <table class="table table-striped">
        <thead>
            <tr>
            	<th><fmt:message key="details"/></th>
                <th><fmt:message key="startDate"/></th>
                <th><fmt:message key="endDate"/></th>
                <th><fmt:message key="deleteRoom"/></th>
            </tr>
        </thead>
            <c:forEach var="hotel" items="${hotel.pet.hotels}">
                <c:if test="${!hotel['new']}">
                    <tr>
                     	<td><c:out value="${hotel.details}"/></td>
                        <td><petclinic:localDate date="${hotel.startDate}" pattern="yyyy/MM/dd"/></td>
                        <td><petclinic:localDate date="${hotel.endDate}" pattern="yyyy/MM/dd"/></td>
                        <td>
                                <spring:url value="/owners/{ownerId}/pets/{petId}/hotels/{hotelId}/delete" var="deleteHotel">
                                    <spring:param name="ownerId" value="${hotel.pet.owner.id}"/>
                                    <spring:param name="petId" value="${hotel.pet.id}"/>
                                    <spring:param name="hotelId" value="${hotel.id}"/>
                                </spring:url>
                                <a href="${fn:escapeXml(deleteHotel)}">Delete hotel</a>
                         </td>
                    </tr>
                </c:if>
            </c:forEach>
        </table>

        <form:form modelAttribute="hotel" class="form-horizontal">
            <div class="form-group has-feedback">
            	<fmt:message var="details" key="details"/>
            	<fmt:message var="startDate" key="startDate"/>
            	<fmt:message var="endDate" key="endDate"/>
                <petclinic:inputField label="${details}" name="details"/>
                <petclinic:inputField label="${startDate}" name="startDate"/>
                <petclinic:inputField label="${endDate}" name="endDate"/>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="petId" value="${hotel.pet.id}"/>
                    <button class="btn btn-default" type="submit"><fmt:message key="addRoom"/></button>
                </div>
            </div>
        </form:form>     
        
        
   </jsp:body>

</petclinic:layout>
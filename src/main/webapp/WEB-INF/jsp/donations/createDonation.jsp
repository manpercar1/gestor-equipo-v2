<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="donations">

    <h2>
        <c:if test="${donation['new']}"> New Donation</c:if>
    </h2>
    <form:form modelAttribute="donation" class="form-horizontal" id="add-donation-form">
        <div class="form-group has-feedback">
        
        	<div class="form-group">
                    <label class="col-sm-2 control-label">Cause</label>
                    <div class="col-sm-10">
                        <c:out value="${cause.name}" />
                    </div>
             </div>
      		<form:hidden path="donationDate" />
            <petclinic:inputField label="Amount" name="amount" />
			<div class="control-group">
           		<petclinic:selectField label="Client" name="client" names="${ownersName}" size="5"  />
        	</div>        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${donation['new']}">
                        <button class="btn btn-default" type="submit">Add Donation</button>
                    </c:when>
                </c:choose>
                <spring:url value="/causes/{causeId}" var="causeUrl">
                        <spring:param name="causeId" value="${cause.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(causeUrl)}" class="btn btn-default">Return</a>
            </div>
        </div>
    </form:form>
    
</petclinic:layout>
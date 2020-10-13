<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="causes">
	<h2>
		<c:if test="${cause['new']}">New Cause</c:if>
	</h2>
	<form:form modelAttribute="cause" class="form-horizontal" id="add-cause-form">
		<div class="form-group has-feedback">
			<petclinic:inputField label="Name" name="name" />
			<petclinic:inputField label="Description" name="description" />
			<petclinic:inputField label="Budget" name="budget" />
			<petclinic:inputField label="Organization" name="organization" />
		</div>
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<c:choose>
					<c:when test="${cause['new']}">
						<button class="btn btn-default" type="submit">Add cause</button>
					</c:when>
				</c:choose>
				<spring:url value="/causes/list" var="addUrl">
       </spring:url>
   
        <a href="${fn:escapeXml(addUrl)}" class="btn btn-default">Return</a>
			</div>
		</div>
	</form:form>
</petclinic:layout>
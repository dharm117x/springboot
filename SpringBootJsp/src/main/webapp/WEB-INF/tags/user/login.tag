<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="username" required="true" type="java.lang.String"%>
<%@ attribute name="password" required="true" type="java.lang.String"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:htmlEscape defaultHtmlEscape="true" />

<c:set var="username" value="${not empty username ? username : 'admin'}" />
<c:set var="password" value="${not empty password ? password : 'admin'}" />

<h3>Login Tag Files</h3>

<c:if test="${not empty  username}">
<c:out value="User not empty"/>
</c:if>

<c:out value="${username}"></c:out>
<br />
<c:out value="${password}"></c:out>





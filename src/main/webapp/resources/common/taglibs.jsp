<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="java.lang.*" %>
<%@page import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="static" value="${pageContext.request.contextPath}/resources/static"/>
<c:set var="curRequestURI" value="${pageContext.request.requestURI }"/>
<c:set var="curRequestURL" value="${pageContext.request.requestURL }"/>
<c:set var="title_prefix" value="乐活集 | "/>
<c:set var="company_name" value="乐活集科技有限公司"></c:set>
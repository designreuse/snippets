<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme"%>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>

<%-- <%@ page import="com.liferay.portal.kernel.captcha.CaptchaTextException"%>
<%@ page import="com.liferay.portal.kernel.captcha.CaptchaMaxChallengesException"%>
<%@ page import="com.liferay.portal.kernel.util.GetterUtil"%>
<%@ page import="com.liferay.portal.kernel.util.StringPool"%>
<%@ page import="com.liferay.portal.kernel.util.Constants"%> --%>
<%@ page import="javax.portlet.*" %>
<%@ page import="java.util.List"%>
<%@ page import="com.liferay.portal.kernel.util.PropsUtil"%>	
<%@ page import="com.liferay.portal.kernel.util.*" %>
<%@ page import="com.liferay.portal.theme.ThemeDisplay" %>  
<%@ page import="com.liferay.portal.model.User" %>
<%@ page import="com.liferay.portlet.*" %>

<portlet:defineObjects />
<liferay-theme:defineObjects />

<%
themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
String Id=themeDisplay.getPortletDisplay().getId();; 
String instanceId=themeDisplay.getPortletDisplay().getInstanceId();;
String pName=themeDisplay.getPortletDisplay().getPortletName();
%>



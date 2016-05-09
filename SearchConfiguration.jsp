<%@page import="com.liferay.portal.kernel.util.Constants"%>
<%@ include file="init.jsp"%>

<liferay-portlet:actionURL portletConfiguration="true" var="configurationURL" />

<%  
String solrUrl_cfg = GetterUtil.getString(portletPreferences.getValue("solrUrl",StringPool.BLANK));
%>

<%-- <h4><%= solrUrl_cfg %></h4> --%>


<aui:form action="<%= configurationURL %>" method="post" name="fm">
    <aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />

    <!-- Preference control goes here -->
    <%-- <aui:input name="solrUrl" type="checkbox" value="<%= solrUrl_cfg %>" /> --%>
    <aui:input name="solrUrl" value="<%= solrUrl_cfg %>" label="Solr URL"></aui:input>

    <aui:button-row>
        <aui:button type="submit" />
    </aui:button-row>
</aui:form>

package com.poc.search.controller;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletMode;
import com.poc.search.client.SolrClient;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.http.client.HttpClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;



@Controller(value = "SearchController")
@RequestMapping("VIEW")
public class SearchController {
	private static Log _log = LogFactoryUtil.getLog(SearchController.class
			.getName());

	@RenderMapping
	public String handleRenderRequest(RenderRequest request,
			RenderResponse response, Model model) {
		_log.info("handleRenderRequest : ");
		PortletPreferences portletPreferences = request.getPreferences();
		String solrUrl = portletPreferences.getValue("solrUrl", "");
		_log.info("solrUrl : " + solrUrl);
		if(solrUrl==null || solrUrl.trim().toString().equals("")){
			return "setup";
		}else
		{
			/*return "typeahead";*/
			return "bootStrapSearch";
			/*return "simpleSearch";*/
		}
	}
	

	
	
	@ActionMapping(value = "changeMode")
	public void changeMode(ActionRequest actionRequest, ActionResponse actionResponse)
			throws IOException, PortletException, PortalException,
			SystemException {
		
		_log.info("!!!   Changing mode Invoked  !!");
		
		actionResponse.setPortletMode(LiferayPortletMode.CONFIG);
			
	}
	
	
	
	@ResourceMapping(value = "myInfo")
	public void getResults(ResourceRequest request, ResourceResponse response) {
		_log.info("!!! getResults  Invoked  !!!");
		try {
			response.setCharacterEncoding("UTF-8");
			String token = request.getParameter("token");
			_log.info("token : " + token);
			//String url= "http://localhost:8984/solr/LREE/select?wt=jsonp&indent=true&rows=5000&fl=emailAddress,firstName,lastName&q=emailAddress:"+email;
			PortletPreferences portletPreferences = request.getPreferences();
			String solrUrl = portletPreferences.getValue("solrUrl", "");
			_log.info("solrUrl : " + solrUrl);
			HttpSolrServer solr = new HttpSolrServer(solrUrl);
			/*HttpSolrServer solr = new HttpSolrServer("http://localhost:8984/solr/LREE");*/
			
		    SolrQuery query = new SolrQuery();
		    query.setParam("wt", "json");
		    query.setQuery("emailAddress:"+token + " OR firstName:"+token+" OR lastName:" + token + " OR title:"+token);
		    //query.addFilterQuery("cat:electronics","store:amazon.com");
		    //query.setFields("emailAddress","firstName","lastName","title");
		    query.setFields("emailAddress","firstName","lastName");
		    query.setStart(0);    
		    query.set("defType", "edismax");
		    _log.info("Executing : " + query.getQuery());
		    QueryResponse queryResponse = solr.query(query);
		    SolrDocumentList results = queryResponse.getResults();
		    JSONArray docs=getJsonArray(results);
		    //_log.info(docs.toString());
		    response.getWriter().write(docs.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	public JSONArray getJsonArray(SolrDocumentList sdl) {
		JSONArray docs = new JSONArray();
		try {
			for (SolrDocument d : sdl) {
				JSONObject r = new JSONObject();
				for (Iterator<Map.Entry<String, Object>> i = d.iterator(); i.hasNext();) {
					Map.Entry<String, Object> el = i.next();
						r.put(el.getKey(), el.getValue());
				}
				docs.put(r);
			}
		}
		catch (JSONException  e) {
			e.printStackTrace();
		}
		return docs;
	}
	}

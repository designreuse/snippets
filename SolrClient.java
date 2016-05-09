package com.poc.search.client;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
 


import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.json.JSONException;
import org.json.JSONObject;
/*import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;*/
 
public class SolrClient {
 
    private static final Logger LOGGER = Logger.getLogger(SolrClient.class);
 
    private static final String SERVER_URL = "http://localhost:8984/solr/LREE";
 
    /**
     * This method is used to add new document to solr and index them for search.
     * 
     * @param id
     * @param name
     * @param price
     * @param popularity
     * @return
     * @throws SolrServerException
     * @throws IOException
     * @throws JSONException 
     */
    public JSONObject addDocument(String id, String name, String price, String popularity)
            throws SolrServerException, IOException, JSONException {
        JSONObject responseJson = new JSONObject();
 
        SolrServer solr = new HttpSolrServer(SolrClient.SERVER_URL);
        //Create the solr input document to add
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", id);
        document.addField("name", name);
        document.addField("price", price);
        document.addField("popularity", popularity);
        UpdateResponse response = solr.add(document);
 
        LOGGER.info(response.getStatus());
        // Remember to commit your changes!
        solr.commit();
 
        if(response.getStatus() ==0){
            responseJson.put("response", "Added Successfully");
        }else{
            responseJson.put("response", "Not added");
        }
        return responseJson;
    }
 
    /**
     * This method used to get the solr document list based on the search keyword.
     * 
     * @param keyword
     * @return
     * @throws SolrServerException
     * @throws MalformedURLException
     * @throws JSONException
     */
    public JSONObject getDocumentList(String keyword) throws SolrServerException,
            MalformedURLException, JSONException {
        LOGGER.info("getDocumentList is called");
        SolrServer solr = new HttpSolrServer(SolrClient.SERVER_URL);
        //Solr query params 
        ModifiableSolrParams params = new ModifiableSolrParams();
        params.set("wt", "json");
        params.set("q", keyword);
 
        QueryResponse response = solr.query(params);
        SolrDocumentList documentList = response.getResults();
        JSONObject responseObj = this.iterateSolrDocumentList(documentList);
        responseObj.put("numFound", documentList.getNumFound());
        return responseObj;
    }
 
    /**
     * This method is used to iterate solr document list and create the json response.
     * 
     * @param documentList
     * @return
     * @throws JSONException
     */
    private JSONObject iterateSolrDocumentList(SolrDocumentList documentList) throws JSONException {
        ArrayList<HashMap<String, Object>> hitsOnPage = new ArrayList<HashMap<String, Object>>();
        JSONObject documentListJson = new JSONObject();
        int count=1;
 
        for (SolrDocument d : documentList) {
            HashMap<String, Object> values = new HashMap<String, Object>();
 
            for (Iterator<Map.Entry<String, Object>> i = d.iterator(); i
                    .hasNext();) {
                Map.Entry<String, Object> e2 = i.next();
 
                values.put(e2.getKey(), e2.getValue());
            }
 
            hitsOnPage.add(values);
            String key = "doc-" + count ;
            documentListJson.put(key, values);
            count++;
 
        }
        return documentListJson;
    }
}

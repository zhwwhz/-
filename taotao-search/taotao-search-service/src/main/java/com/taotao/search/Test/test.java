package com.taotao.search.Test;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

public class test {
    public static void main(String[] args)throws  Exception {
        SolrServer solrServer = new HttpSolrServer("http://39.108.185.153:8145/solr/mycore");
//        SolrInputDocument document = new SolrInputDocument();
//        document.addField("id","testid");
//        document.addField("item_title","haha");
//        solrServer.add(document);
//        solrServer.commit();
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("阿尔卡特");
        solrQuery.set("df","item_title");
        QueryResponse query = solrServer.query(solrQuery);
        SolrDocumentList results = query.getResults();
        for(SolrDocument rs: results)
        {
            System.out.println(rs.get("id"));
            System.out.println(rs.get("item_title"));
        }

    }
}

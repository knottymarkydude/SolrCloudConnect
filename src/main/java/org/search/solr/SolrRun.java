/*
 * Solr 5 Connect
 */

package org.search.solr;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.SolrPingResponse;
import org.joda.time.LocalTime;
import org.search.solr.connect.SolrCloudConnect;
import org.slf4j.LoggerFactory;

/**
 *
 * @author mw8
 */
public class SolrRun {
    
    org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());
    
    public static void main(String[] args) {
        
        LocalTime currentTime = new LocalTime();
        
        try {
            
            System.out.println("The current local time is: " + currentTime);
            
            SolrCloudConnect solr = new SolrCloudConnect();
            
            SolrPingResponse ping = solr.pingServerDetails();
            
            long elapsedTime = ping.getElapsedTime();
            
            //System.out.println("Ping Elapsed Time: " + elapsedTime );
            
            String et = String.valueOf(elapsedTime);
            
            Greeter greeter = new Greeter();
            
            System.out.println(greeter.sayHello(et));
        } catch (SolrServerException | IOException ex) {
            Logger.getLogger(SolrRun.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
    
  }
}

/*
 * Solr 5 Connect
 */
package org.search.solr;

import org.joda.time.LocalTime;
import org.search.solr.service.solr.InputServiceTikaFile;
import org.slf4j.LoggerFactory;

/**
 *
 * @author mw8
 */
public class SolrRun {

    org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());

    public static void main(String[] args) {

        String collection;
        String document;
        boolean result = false;
        
        LocalTime startTime = new LocalTime();
        System.out.println("Start Time: " + startTime.toString("hh:mm:ss"));
        
        if (args.length > 0) {
            for (String s : args) {
                System.out.println("Args :" + s);
            }
            collection = args[0];
            document = args[1];
            
            InputServiceTikaFile inputServiceTikaFile = new InputServiceTikaFile(collection, document);
            
            result = inputServiceTikaFile.inputDataService();
            
            if (result){
                System.out.println("File successfully added");
            }else{
                System.out.println("Unable to add file");
            }
            
        }else{
            System.out.println("No arguments, you must add the collection and document to add, for example");
            System.out.println("java -jar build/libs/SolrCloudConnect-all.jar lego /mydir/thisdoc.pdf");
        }
        //String et = String.valueOf(currentTime.toString("hh:mm:ss"));
        //Greeter greeter = new Greeter();
        //System.out.println(greeter.sayHello(et));
        LocalTime endTime = new LocalTime();
         System.out.println("Finish Time: " + endTime.toString("hh:mm:ss"));
    }
}

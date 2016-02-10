
package org.sanger.search;

import org.joda.time.LocalTime;

/**
 *
 * @author mw8
 */
public class SolrRun {
    public static void main(String[] args) {
    LocalTime currentTime = new LocalTime();
    System.out.println("The current local time is: " + currentTime);

    Greeter greeter = new Greeter();
    System.out.println(greeter.sayHello());
  }
}

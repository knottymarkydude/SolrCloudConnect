# SolrCloudConnect. 
Connect to a Solr 5 Cloud Index
A Java/ Gradle Project to connect to a Solr Cloud Instance. 
The connection details are set in a properties file.

Run gradle publish 
to create a repo for Maven repository
then

mvn install:install-file -Dfile=/Users/mw8/NetBeansProjects/Sanger/SolrCloudConnect/build/repo/org/search/solr/SolrCloudConnect/1.2.5/SolrCloudConnect-1.2.5.jar -DgroupId=org.search.solr -DartifactId=SolrCloudConnect -Dversion=1.2.5 -Dpackaging=jar

For the big jar file
Run gradle shadowJar
then
mvn install:install-file -Dfile=/Users/mw8/NetBeansProjects/Sanger/SolrCloudConnect/build/libs/SolrCloudConnect-1.2.5-all.jar -DgroupId=org.search.solr -DartifactId=SolrCloudConnectAll -Dversion=1.2.5 -Dpackaging=jar

To use this in a Gradle Project include this in the dependencies section

compile("org.search.solr:SolrCloudConnectAll:1.2.5")

https://www.digitalocean.com/community/tutorials/spring-mvc-file-upload-example-single-multiple-files

workspace: /Users/mqzhu/eclipse-workspace
repository: /Users/mqzhu/Documents/work/SpringFileUpload

After import, add the following to pom.xml file to get rid of pom.xml errors:

```
<plugin> 
      <artifactId>maven-war-plugin</artifactId>
      <version>3.2.2</version>
</plugin>
```

Create maven run configuration:
clean install

Add tomcat server and add the project to the tomcat server too.

Modify log4j.xml file's first line to get rid of log4j dtd error:

<!DOCTYPE log4j:configuration PUBLIC "-//APACHE//DTD LOG4J 1.2//EN" "http://logging.apache.org/log4j/1.2/apidocs/org/apache/log4j/xml/doc-files/log4j.dtd"><log4j:configuration xmlns:log4j="http://jakarta.apache.org/log4j/">


start tomcat.

http://localhost:8080/SpringFileUpload/upload.jsp

*********************************************************
Find and Highlight Specific Text in PDF

https://www.e-iceblue.com/Knowledgebase/JAVA/Spire.PDF-for-JAVA/Program-Guide/Text/Find-and-Highlight-Text-in-PDF-in-Java.html
(this doesnÕt work).

https://stackoverflow.com/questions/49034774/highlight-words-inside-existing-pdf




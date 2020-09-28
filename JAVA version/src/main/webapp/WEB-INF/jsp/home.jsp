<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="ISO-8859-1">
        <title>Coding Challenge</title>
    </head>
    <body>
            Select an report to generate : <br>

            <br>Full report  <a href="report/0/generate">Link</a>  <br/>
            <br>Top 10 requested pages and the number of requests made for each   <a href="report/1/generate">Link</a>  <br/>
            <br>Percentage of successful requests (anything in the 200s and 300s range)    <a href="report/2/generate">Link</a>  <br/>
            <br>Percentage of unsuccessful requests (anything that is not in the 200s or 300s range)   <a href="report/3/generate">Link</a>  <br/>
            <br>Top 10 unsuccessful page requests   <a href="report/4/generate">Link</a>  <br/>
            <br>The top 10 hosts making the most requests, displaying the IP address and number of requests made.   <a href="report/5/generate">Link</a>  <br/>
    </body>
</html>

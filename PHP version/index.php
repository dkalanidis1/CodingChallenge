<!DOCTYPE html>
<html>
<body>

<h1>NASA REPORTS</h1>
 

Select an report to generate : <br>
  

<?php
 	echo " <br>Full report <a href=\"/results.php\">Link</a>  <br/>";
 	echo " <br>Top 10 requested pages and the number of requests made for each   <a href=\"results.php?option=1\">Link</a>  <br/>";
 	echo " <br>Percentage of successful requests (anything in the 200s and 300s range)   <a href=\"results.php?option=2\">Link</a>  <br/>";
 	echo " <br>Percentage of unsuccessful requests (anything that is not in the 200s or 300s range)h   <a href=\"results.php?option=3\">Link</a>  <br/>";
 	echo " <br>Top 10 unsuccessful page requests   <a href=\"results.php?option=4\">Link</a>  <br/>";
 	echo " <br>The top 10 hosts making the most requests, displaying the IP address and number of requests made.  <a href=\"results.php?option=5\">Link</a>  <br/>";
 ?>


 


</body>
</html>


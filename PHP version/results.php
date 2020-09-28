<?php
// Start the session
session_start();
?>
<!DOCTYPE html>
<html>
<head>
<script>
 
</script>
</head>
<body>

<h1>NASA REPORTS</h1>

<?php 
 
$servername = "localhost";
$username = "springbootuser";
$password = "springbootuser2020";
$dbname = "nasa";
set_time_limit(1000);

 
function validate_line($line_string) {
	$pattern = "/^([a-z0-9d]([-_]*[a-z0-9d])*)(.([a-z0-9d]([-_]*[a-z0-9d])*))* - - \[[0-9]{1,2}\/[A-Za-z]+\/[0-9]{4}:[0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2} -[0-9]{4}\] \"[a-z]{3,4} (\S{1,}) HTTP\/1.0\" [0-9]{3} [0-9-]+$/i";
	return preg_match($pattern, $line_string); 
}



//Top 10 requested pages and the number of requests made for each
function get_top_10_requested_pages($servername,$username,$password,$dbname) 
{
	$rslt   = array();
	$conn = new mysqli($servername, $username, $password, $dbname);
	// Check connection
	if ($conn->connect_error) {
	die("Connection failed: " . $conn->connect_error);
	} 

	 $sql = " 
		SELECT page, COUNT(page) as hits
		FROM log_data
		WHERE malformed_line = 1
		GROUP by page
		ORDER BY COUNT(page) DESC
		LIMIT 10;	";  
	
	$result = $conn->query($sql);
	if ($result->num_rows > 0) {   
		while($row = $result->fetch_assoc()) { 
			$rslt[$row["page"]]=$row["hits"]; 
		}
	}
	else { 
	}
	
	 $conn->close();
	return $rslt;
}

 


//2. Percentage of successful requests (anything in the 200s and 300s range)
function get_successful_requests_perc($servername,$username,$password,$dbname) 
{
	$successful  = 0;
	$total = 0;
	$perc = 0.0;
	$conn = new mysqli($servername, $username, $password, $dbname);
	// Check connection
	if ($conn->connect_error) {
	die("Connection failed: " . $conn->connect_error);
	} 

	$sql = " 
		SELECT COUNT(http_st_code) as counter
		FROM `log_data`
		WHERE http_st_code >= 200 and http_st_code <400 
		and malformed_line = 1; 
	";  
					
	$result = $conn->query($sql);
	if ($result->num_rows > 0) {   
		while($row = $result->fetch_assoc()) { 
			$successful=$row["counter"]; 
		}
	}
	else {
	echo "0 results";
	}
	 
	$sql = " 
		SELECT COUNT(http_st_code) as counter
		FROM `log_data`
		WHERE malformed_line = 1; 
	";  
					
	$result = $conn->query($sql);
	if ($result->num_rows > 0) {   
		while($row = $result->fetch_assoc()) { 
			$total=$row["counter"]; 
		}
	}
	else {
	echo "0 results";
	}
	
	if($total > 0){ 
		$perc = round(   ( ($successful  )/  $total ) , 4) * 100; 
	} 
	
	return $perc;
}


//3. Percentage of successful requests (anything in the 200s and 300s range)
function get_unsuccessful_requests_perc($servername,$username,$password,$dbname) 
{
	$unsuccessful  = 0;
	$total = 0;
	$perc = 0.0;
	$conn = new mysqli($servername, $username, $password, $dbname);
	// Check connection
	if ($conn->connect_error) {
	die("Connection failed: " . $conn->connect_error);
	} 

	$sql = " 
		SELECT COUNT(http_st_code) as counter
		FROM `log_data`
		WHERE http_st_code < 200 or http_st_code >=400 
		and malformed_line = 1; 
	";  
					
	$result = $conn->query($sql);
	if ($result->num_rows > 0) {   
		while($row = $result->fetch_assoc()) { 
			$unsuccessful=$row["counter"]; 
		}
	}
	else {
	echo "0 results";
	}
	 
	$sql = " 
		SELECT COUNT(http_st_code) as counter
		FROM `log_data`
		WHERE malformed_line = 1; 
	";  
					
	$result = $conn->query($sql);
	if ($result->num_rows > 0) {   
		while($row = $result->fetch_assoc()) { 
			$total=$row["counter"]; 
		}
	}
	else {
	echo "0 results";
	}
	
	if($total > 0){ 
		$perc = round(   ( ($unsuccessful  )/  $total ) , 4) * 100; 
	} 
	
	 
	return $perc;
	
}


//4. Top 10 unsuccessful page requests
function get_top_10_unsuccessful_requests($servername,$username,$password,$dbname) 
{
	$rslt   = array();;
	$conn = new mysqli($servername, $username, $password, $dbname);
	// Check connection
	if ($conn->connect_error) {
	die("Connection failed: " . $conn->connect_error);
	} 

	 $sql = " 
		SELECT page ,COUNT(page)  as hits
		FROM `log_data`
		WHERE http_st_code < 200 or http_st_code >=400 
		and malformed_line = 1
		GROUP by page
		ORDER BY COUNT(page) DESC
		LIMIT 10;	
		;	";  
			
	$result = $conn->query($sql);
	if ($result->num_rows > 0) {   
		while($row = $result->fetch_assoc()) { 
			$rslt[$row["page"]]=$row["hits"]; 
		}
	}
	else {
	echo "0 results";
	}
	
	 $conn->close();
	return $rslt;
 
}



// 5. The top 10 hosts making the most requests, displaying the IP address and number of requests made.
function get_top_10_hosts($servername,$username,$password,$dbname) 
{
	$rslt   = array();
	$conn = new mysqli($servername, $username, $password, $dbname);
	// Check connection
	if ($conn->connect_error) {
	die("Connection failed: " . $conn->connect_error);
	} 

	 $sql = " 
		SELECT host, COUNT(host) as hits
		FROM `log_data` 
		WHERE malformed_line = 1
		GROUP by host
		ORDER BY COUNT(host) DESC
		LIMIT 10;	";  
			
	$result = $conn->query($sql);
	if ($result->num_rows > 0) {   
		while($row = $result->fetch_assoc()) { 
			$rslt[$row["host"]]=$row["hits"]; 
		}
	}
	else {
	echo "0 results";
	}
	
	 $conn->close();
	return $rslt;
}


//7. Top 5 requested pages and the number of requests made for each
function get_top_5_requested_pages_of_host($servername,$username,$password,$dbname,$host) 
{
	$rslt   = array();
	$conn = new mysqli($servername, $username, $password, $dbname);
	// Check connection
	if ($conn->connect_error) {
	die("Connection failed: " . $conn->connect_error);
	}  
	 $sql = " 
		SELECT page,host ,COUNT(page) as hits
		FROM `log_data` 
		WHERE malformed_line = 1 and host =  '".$host."'
		GROUP by page,host
		ORDER BY COUNT(page) DESC
		LIMIT 5; 
		";  
	
	$result = $conn->query($sql);
	if ($result->num_rows > 0) {   
		while($row = $result->fetch_assoc()) { 
			$rslt[$row["page"]]=$row["hits"]; 
		}
	}
	else {
	echo "0 results";
	}
	
	 $conn->close();
	return $rslt;
}


 
 
$server_file = 'NASA_access_log_Aug95.gz';
$local_file = 'NASA_access_log_Aug95.gz';

$ftpserver = "ita.ee.lbl.gov";
$ftpusername = "anonymous";
$ftppassword = "1234";
$directory_path = "traces";

// Connection
$conn_id  = ftp_connect($ftpserver);
if($conn_id == false){
    echo "Could not connect to FTP server\n";
    die();
}

if (@ftp_login($conn_id, $ftpusername, $ftppassword)) {
    //echo "Connected as $ftpusername@$ftpserver\n";
} else {
    echo "Couldn't connect as $ftpusername\n";
}

// Change the current working directory
if(ftp_chdir($conn_id , $directory_path) == false){
    echo "The directory $directory_path does not exist\n";
    echo "Or the user has not permission to access it\n";
    die();
}

// try to download $server_file and save to $local_file
if (ftp_get($conn_id, $local_file, $server_file, FTP_BINARY)) {
    //echo "Successfully written to $local_file\n";
} else {
    echo "There was a problem\n";
}

// close the connection
ftp_close($conn_id);



$sfp = gzopen($local_file, "rb");
$fp = fopen("access_log_Aug95", "w");

while ($string = gzread($sfp, 4096)) {
	fwrite($fp, $string, strlen($string));
}
gzclose($sfp);
fclose($fp);
	  
$txt_file    = file_get_contents("access_log_Aug95");
$rows        = explode("\n", $txt_file);
 
$host_requests_rank = array();
$pages_rank = array();
$unsuccessful_requests_rank = array(); 
$successful_requests = 0;
$unsuccessful_requests = 0; 
$malformed_lines = 0;
$count = 0;
$malformed_lines_string = "";
$malformed_lines_flag = "";
$total_file_lines = count($rows); 
$return =   array();
$top_10_requested_pages =   array();
$top_10_hosts  =   array();
$success_reqs  = 0;
$unsuccess_reqs  = 0;
$top_10_unsuccessful_requests =   array(); 
$top_10_hosts_and_pages  =   array(); 
$output_text = "";

 

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($conn->connect_error) {
  die("Connection failed: " . $conn->connect_error);
}
$sql = "TRUNCATE TABLE   log_data  "; 
if ($conn->query($sql) === TRUE) {
  //echo "New record created successfully <br>";
} else {
  echo "Error: " . $sql . "<br>" . $conn->error;
} 
$sql =""; 
$line_number = 0;
$ttllines=count($rows);
foreach($rows as $row => $data)
{
	$line_number++;
	$row_data = explode(' ', $data); 
	if(validate_line($data))
	{
		//get row data
		$row_data = explode(' ', $data);
		
		$source_address = $row_data[0];     
		
		if (filter_var($source_address, FILTER_VALIDATE_DOMAIN)) {
		//echo("$source_address is a valid URL");
		} else {
		//echo("$source_address is not a valid URL");
		}

		$datetime = str_replace("[", "",$row_data[3]);
		$timeoffset = str_replace("-", "",$row_data[4]);
		$httpmethod = str_replace("\"", "", $row_data[5]);
		$page = $row_data[6];
		$http_statuscode = $row_data[8];
		$path = $row_data[9]; 
 
			
			if (array_key_exists($page  ,$pages_rank )) {
			 $pages_rank[$page]=$pages_rank[$page] +1;
			} else {
			  $pages_rank[$page]=1;
			}
 
		$malformed_lines_flag=1;
		$sql .= "INSERT INTO log_data (host, page,http_st_code,malformed_line,malformed_line_number)
		VALUES ('". $source_address ."','". $page ."','". $http_statuscode . "','". $malformed_lines_flag . "',''); "; 
	}
	else
	{
		$malformed_lines_flag=0;
		$sql .= "INSERT INTO log_data (host, page,http_st_code,malformed_line,malformed_line_number)
		VALUES (' ',' ',' ','". $malformed_lines_flag . "','". $line_number ."'); "; 

		// -- 8 -- The log file contains malformed entries; for each malformed line, display an error message and the line number.
		$malformed_lines_flag = 0;
		$malformed_lines++;   
		$malformed_lines_string .= " ERROR. Malformed entry on line " .   $line_number . "\n"; 
	}
	 

 

	if($line_number == $ttllines)
	{ 
		break;
	}
	/*
	if($line_number == 5455)
	{  
		break;
	}
	*/
	if( (intval($line_number) % 1000 )== 999)
	{   
		while ($conn->next_result()) {;}
		if ($conn->multi_query($sql) === TRUE) {
		  //echo "New records created successfully";
		} else {
		  echo "Error: " . $sql . "<br>" . $conn->error;
		}
		$sql ="";  
	}

}
	 echo $line_number . "<br>"; 
		while ($conn->next_result()) {;}
        if($sql != "")
		{
			if ($conn->multi_query($sql) === TRUE) {
			  //echo "New records created successfully";
			} else {
			  echo "Error: " . $sql . "<br>" . $conn->error;
			}
		}
	$conn->close(); 
 
$output_text = "";
if(isset($_GET['option'])) {
    switch (urlencode($_GET['option'])) {
	  case "1":
		$top_10_requested_pages = get_top_10_requested_pages($servername,$username,$password,$dbname);
		$output_text .= "----- top_10_requested_pages  (page - number of requests ) ---------- \n\n";
		foreach($top_10_requested_pages as $key => $value) {
			$output_text .= "$key \t $value \n";
		}
		$output_text .= "\n\n";
		break;
	  case "2":
		$success_reqs = get_successful_requests_perc($servername,$username,$password,$dbname);
		$output_text .= "----- successful requests percentage  ---------- \n\n";
		$output_text .=   "success_reqs " . $success_reqs  . "%  \n";
		$output_text .= "\n\n";
		break;
	  case "3":
		$unsuccess_reqs = get_unsuccessful_requests_perc($servername,$username,$password,$dbname);
		$output_text .= "----- unsuccessful requests_percentage   ---------- \n\n";
		$output_text .=   "unsuccess_reqs " . $unsuccess_reqs  . "%  \n";
		$output_text .= "\n\n";
		break;
	  case "4":
		$top_10_unsuccessful_requests = get_top_10_unsuccessful_requests($servername,$username,$password,$dbname);
 		$output_text .= "----- top_10_unsuccessful_requests  ---------- \n\n";
		foreach($top_10_unsuccessful_requests as $key => $value) {
			$output_text .= "$key \t $value \n";
		} 
		$output_text .= "\n\n";
		break;
	  case "5":
		$top_10_hosts = get_top_10_hosts($servername,$username,$password,$dbname);
		$output_text .= "----- The top 10 hosts making the most requests  ---------- \n\n";
		$counter = 1;  
		foreach($top_10_hosts as $key => $value) {
		  $output_text .= "$counter  $key \t $value \n";
		  $counter++;
		} 
		$output_text .= "\n\n";
		break;
	  default:
	}
}
else{
	 
		$top_10_requested_pages = get_top_10_requested_pages($servername,$username,$password,$dbname);
		$output_text .= "----- top_10_requested_pages  ---------- \n\n";
		foreach($top_10_requested_pages as $key => $value) {
			$output_text .= "$key \t $value \n";
		}
		$output_text .= "\n\n";
		$success_reqs = get_successful_requests_perc($servername,$username,$password,$dbname);
		$output_text .= "----- successful_requests_percentage  ---------- \n\n";
		$output_text .=   "success_reqs " . $success_reqs  . "%  \n";
		$output_text .= "\n\n";
		$unsuccess_reqs = get_unsuccessful_requests_perc($servername,$username,$password,$dbname); 
		$output_text .= "----- unsuccessful requests_percentage   ---------- \n\n";
		$output_text .=   "unsuccess_reqs " . $unsuccess_reqs  . "%  \n\n";
		$output_text .= "\n\n";
		$top_10_unsuccessful_requests = get_top_10_unsuccessful_requests($servername,$username,$password,$dbname);
 		$output_text .= "----- top_10_unsuccessful_requests  ---------- \n\n";
		foreach($top_10_unsuccessful_requests as $key => $value) {
			$output_text .= "$key \t $value \n";
		} 
		$output_text .= "\n\n\n";
		$top_10_hosts = get_top_10_hosts($servername,$username,$password,$dbname);
		$output_text .= "----- The top 10 hosts making the most requests  ---------- \n\n";
		$counter = 1;  
		foreach($top_10_hosts as $key => $value) {
		  $output_text .= "$counter  $key \t $value \n";
		  $counter++;
		} 
		$output_text .= "\n\n";


}

$top_10_hosts = get_top_10_hosts($servername,$username,$password,$dbname);
$output_text .= "----- The top 5 pages from the top 10 hosts ---------- \n\n";
$counter = 1;  
foreach($top_10_hosts as $key => $value) {
   $top_5_requested_pages_of_host = get_top_5_requested_pages_of_host($servername,$username,$password,$dbname,$key) ;
	foreach($top_5_requested_pages_of_host as $key2 => $value2) {
	   		  $output_text .= "$counter  $key \t $key2 \t  $value2 \n";
			  		  $counter++;
	} 
} 
$output_text .= "\n\n";


$output_text .= "----- Lines of log file that contains malformed entries; ---------- \n\n";
$output_text .= $malformed_lines_string;

$savefilename = "data_process_report.txt";
$f=fopen($savefilename,'w');
fwrite($f,"-----------  NASA LOG REPORT ----------------\n ");
fclose($f);

$f=fopen($savefilename,'a');
fwrite($f,$output_text);
fclose($f);

echo " <br> Full report was saved on file <b>". $savefilename ."</b>  <br/>";
echo " <br> <a href=\"/\">Back</a>  <br/>";
 
?> 

</body>
</html>
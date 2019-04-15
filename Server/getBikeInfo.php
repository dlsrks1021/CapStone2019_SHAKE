<?php

  $con = mysqli_connect('13.125.229.179:22','root','12345','SHAKE');

  if (mysqli_connect_errno($con))
  {
  	  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  }

  mysqli_set_charset($con, "utf8");

  $res = mysqli_query($con, "select location_latitude, location_longitude from bike where bikecode = 'road123'");

  $result = array();

  while ($row = mysqli_fetch_array($res)){
  	  echo $row . "\n";
  }

?>

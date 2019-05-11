<?php

  $json = file_get_contents ( 'php://input' );

  $obj = json_decode ( $json );

  include 'config.php';
 
  $con = mysqli_connect($HostName,$HostUser,$HostPass,$DatabaseName);
  
  $mobileno=$_POST["mobileno"];
 
  $password=$_POST["password"];

  //$mobileno=$obj->mobileno;
  //$password=$obj->password;

  $response = array();

  $update = "update customer_details set password = '$password' where mobileno = '$mobileno'";

  $sql_update = mysqli_query($con,$update);

  if (isset($sql_update)){

  $response["message"] ="Password Changed Successfully";
  
  $response["success"] = 1;
  }
  else
  {
  $response["success"] = 0;

  $response["message"] = "Invalid Details";
  }

 
  echo json_encode($response);   

?>
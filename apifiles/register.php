<?php

  $response = array();
  include 'config.php';

  $con = mysqli_connect($HostName,$HostUser,$HostPass,$DatabaseName);

  $fname =$_POST["name"];
 
  $mobileno = $_POST["mobileno"];
 
  $password = $_POST["password"];
 
  $emailid = $_POST["email"];

  $CheckSQL = "SELECT * FROM customer_details WHERE mobileno='$mobileno'";
 
  $check = mysqli_fetch_array(mysqli_query($con,$CheckSQL));
 
  if(isset($check)){

  $response["success"] = 0;
  $response["message"] = "Mobile Number Already Registered";
  echo json_encode($response); 

  }
  else
  {
 
  $Sql_Query = "INSERT INTO customer_details (name,mobileno,password,email,date) values 
  ('$fname','$mobileno','$password','$emailid',NOW())";

  if(mysqli_query($con,$Sql_Query))
  {
  $response["success"] = 1;

  $response["message"] = "Successfully Registered";
 
  echo json_encode($response);
  }

  else

  {
  $response["success"] = 0;

  $response["message"] = "Error";

  echo json_encode($response); 
 }
 }


?>
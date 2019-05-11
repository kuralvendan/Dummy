<?php

  $json = file_get_contents ( 'php://input' );

  $obj = json_decode ( $json );

  include 'config.php';
 
  $con = mysqli_connect($HostName,$HostUser,$HostPass,$DatabaseName);
 
  $mobileno=$_POST["username"];

  $password=$_POST["password"];

  //$mobileno=$obj->username;

  //$password=$obj->password;

  $response = array();

  $Sql_Query = "select * from customer_details where mobileno = '$mobileno' and password = '$password' ";
 
  $check = mysqli_fetch_array(mysqli_query($con,$Sql_Query));

  $id= $check[id];

  $name = $check[name];

  $mobileno = $check[mobileno];

  $emailid = $check[email];
 
  if(isset($check))
  {
 
  $update =  "update customer_details set lastVisit = now() where id = '$id'";
 
  $up_res = mysqli_query($con,$update);
 
  $response["id"]= $id;
  $response["name1"]= $name;
  $response["mobileno"]= $mobileno;  
  $response["email"]= $emailid;  
  $response["message"] ="Login Successfully";
  $response["success"] = 1;
  }
  else
  {
  $response["success"] = 0;
  $response["message"] = "Invalid Username and Password";
  }
  echo json_encode($response);   

?>
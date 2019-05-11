<?php

   $json = file_get_contents ( 'php://input' );
   
   $obj = json_decode ( $json );

   include 'config.php';
 
   $con = mysqli_connect($HostName,$HostUser,$HostPass,$DatabaseName);

   $mobileno=$_POST["mobileno"];
  
   $email =$_POST["email_id"];

   //$email = $obj->email_id;

   //$mobileno = $obj->mobileno;



    $response = array();


    $query = "select * from customer_details where mobileno = '$mobileno' AND email = '$email' ";
 
 
    $sql= mysqli_query($con,$query);
 
    $check = mysqli_fetch_array($sql);

    if(isset($check)){
   
    $response["message"] ="Enter your password";
    
    $response["success"] = 1;

    }

    else
    {

    $response["success"] = 0;
  
    $response["message"] = " Enter details are invalid";
    }
 
   echo json_encode($response);   

?>
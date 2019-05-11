<?php

  $json = file_get_contents ( 'php://input' );

  $obj = json_decode ( $json );

  include 'config.php';

  $output = array('status' => false);

  //$size=$obj->size;

  //$thickness=$obj->thickness;
 
  //$size=$_POST["size"];

  //$thickness=$_POST["thickness"];

  $con = mysqli_connect($HostName,$HostUser,$HostPass,$DatabaseName);

  $sql="SELECT * from price;

  $res=mysqli_query($con,$sql);

  $row=mysqli_fetch_assoc($res);
  
  $max1=$row['product_price'];
  $max2=$row['size'];
  $max3=$row['thickness'];


  if(mysqli_num_rows($row)>0)
  {
   $response['success']="1";
  $response['message']="success";
  $response['productprice']=$max1;
  $response['size1']=$max2;
  $response['thickness1']=$max3;

  }

  else
  {
  $response['error']="Error Response";
  }
  echo json_encode($response);



?>
<?php

  $json = file_get_contents ( 'php://input' );

  $obj = json_decode ( $json );

  include 'config.php';

  $output = array('status' => false);

  //$size=$obj->size;

  //$thickness=$obj->thickness;
 
  $size=$_POST["size"];

  $thickness=$_POST["thickness"];

  $con = mysqli_connect($HostName,$HostUser,$HostPass,$DatabaseName);

  $sql="SELECT product_price from price where size='$size' and thickness='$thickness'" ;

  $res=mysqli_query($con,$sql);

  $row=mysqli_fetch_assoc($res);

  if(mysqli_num_rows($res)>0)
  {
  $max=$row['product_price'];
  $response['success']="1";
  $response['message']="success";
  $response['productprice']=$max;

  }

  else
  {
  $response['error']="Error Response";
  }
  echo json_encode($response);

  mysqli_close($con);

?>
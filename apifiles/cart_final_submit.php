<?php 

 $json = file_get_contents ( 'php://input' );
 $obj = json_decode ( $json );

 include 'config.php';

 $output = array('status' => false);

 $con = mysqli_connect($HostName,$HostUser,$HostPass,$DatabaseName);
 
 $userid=$_POST["id"];
 //$userid=$obj->id;
 $date = new DateTime('now', new DateTimeZone('Asia/Kolkata'));
 $datee = $date->format('Y-m-d');
 
 $sql="select * from cart_details where user_id = '$userid' ";
 
 $res = mysqli_query($con, $sql) or die (mysqli_error());
 $rows=mysqli_num_rows($res);
 
 if($rows > 0)
 {
 $data=array();
 while ( $row = mysqli_fetch_assoc($res) ) 
 { 
 $user_id=$row['user_id'];
 $product=$row['product'];
 $type=$row['type'];
 $brand=$row['brand'];
 $thickness=$row['thickness'];
 $size=$row['size'];
 $quantity=$row['quantity'];
 $price=$row['price'];
 
 $sql="INSERT INTO purches_details (user_id,product,type,brand,thickness,size,quantity,price,currentdate) VALUES ('$user_id','$product','$type','$brand','$thickness','$size','$quantity','$price','$datee')";
 $query=mysqli_query($con,$sql);
 if($query)
 {
	 $response['success']=1;
	 $response['message']="Successfully Inserted";

 }
 else
 {
	 $response['failer']=0;
	 $response['message']="Error";
 }
 }
 }
 else
 {
	 $response['failer']=2;
	 $response['message']="NO data found";
 }
 
 //echo "<pre>";print_r($output);die;
 header('Content-Type: application/json; charset=utf-8');
 
 echo json_encode($response);


?>
<?php 

 $json = file_get_contents ( 'php://input' );
 $obj = json_decode ( $json );

 include 'config.php';

 $output = array('status' => false);

 $con = mysqli_connect($HostName,$HostUser,$HostPass,$DatabaseName);
 
 $userid=$_POST["id"];
 //$userid=$obj->id;
 
 
 $sql="DELETE from cart_details where id = '$userid' ";
 
 $res = mysqli_query($con, $sql) or die (mysqli_error());
 
 if($res)
 {
 $response['success']=1;
 $response['message']="Successfully Deleted";
	 
 }
 else
 {
 $response['failer']=0;
 $response['message']="Error";
 
 }
 
 //echo "<pre>";print_r($output);die;
 header('Content-Type: application/json; charset=utf-8');
 
 echo json_encode($response);


?>
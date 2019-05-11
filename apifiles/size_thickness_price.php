<?php 

 $json = file_get_contents ( 'php://input' );
 $obj = json_decode ( $json );

 include 'config.php';

 $output = array('status' => false);
 
 $brand=$_POST['productbrand'];
// $brand=$obj->productbrand;

 $con = mysqli_connect($HostName,$HostUser,$HostPass,$DatabaseName);
 
 $sql="select * from price where product_type = '$brand' ";
 
 $res = mysqli_query($con, $sql) or die (mysqli_error());
 $rows=mysqli_num_rows($res);

 $data=array();
 while ( $row = mysqli_fetch_assoc($res) ) 
 { 
 $array = array_merge($data,$row);
    

 $output ['status'] = "true";
 $output ['tbl_customer'][] = $array;

 }//echo "<pre>";print_r($output);die;
 header('Content-Type: application/json; charset=utf-8');
 echo json_encode ( $output );

?>
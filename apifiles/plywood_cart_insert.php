<?php 

  $json = file_get_contents ( 'php://input' );

  $obj = json_decode ( $json );

  include 'config.php';
  $con = mysqli_connect($HostName,$HostUser,$HostPass,$DatabaseName);
  
 
 
  $user_id = $_POST["id"];
  $username = $_POST["name"];
  $mobileno = $_POST["mobileno"];
  $product = $_POST["product"];
  //$type = $_POST["type"];
  $brand = $_POST["brand"];
  $thickness = $_POST["thickness"];
  $size = $_POST["size"];
  $quantity = $_POST["quantity"];
  $price = $_POST["price"];
  /*$user_id=$obj->id;
  $username=$obj->name;
  $mobileno=$obj->mobileno;
  $product=$obj->product;
  $type=$obj->type;
  $brand=$obj->brand;
  $thickness=$obj->thickness;
  $size=$obj->size;
  $quantity=$obj->quantity;
  $price=$obj->price;*/
  
  
  $date = new DateTime('now', new DateTimeZone('Asia/Kolkata'));
  $datee = $date->format('Y-m-d');
  $sql="INSERT INTO cart_details (user_id,name,mobileno,product,brand,thickness,size,quantity,price,currentdate)values('$user_id','$username','$mobileno','$product','$brand','$thickness','$size','$quantity','$price','$datee')";
 
  $query = mysqli_query($con,$sql);
  if($query)
  {
  $response["success"]=1;
  $response["message"]="Product Added to Cart";
  }
  else
  {
  $response["Error"]=0;
  $response["message"]="Error in Adding Cart";
  }
  
  echo json_encode($response);
  
?>
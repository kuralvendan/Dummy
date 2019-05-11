package com.binary2quantumtechbase.andapp.intpro;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.binary2quantumtechbase.andapp.intpro.databases.AppController;
import com.binary2quantumtechbase.andapp.intpro.databases.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.binary2quantumtechbase.andapp.intpro.plywoods.plywooddetails.EXTRA_PRICE;
import static com.binary2quantumtechbase.andapp.intpro.plywoods.plywooddetails.EXTRA_SIZE;
import static com.binary2quantumtechbase.andapp.intpro.plywoods.plywooddetails.EXTRA_THICK;

//import static com.binary2quantum.android.intpro.CartDetails.editmode;

public class productdetails extends AppCompatActivity implements TextWatcher {
    String psize,pthickness,pro_type,products,brands,quantity,price,strsuccess,strmessage,finalprice,totalprice;
    TextView t1,t3,t4,t5,t6;
    EditText ed1;
    Button add_cart,buy_now;
    int amt;
    String ply_product,ply_type,ply_brand,ply_thickness,ply_size,ply_price,struser,strmobile,strid;
    SessionManager sessionManager;
    HashMap<String,String> user;
    ProgressDialog dialog;
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productdetails);
//        singleObj.SCREEN_TAG = "HOME";
        sessionManager = new SessionManager(this);


        Intent intent = getIntent();
        psize = intent.getStringExtra(EXTRA_SIZE);
        Log.e("productdetails","g_size_1:"+psize);

        pthickness = intent.getStringExtra(EXTRA_THICK);
        Log.e("productdetails","g_thickness_1:"+pthickness);

        products = intent.getStringExtra("product");
        Log.e("productdetails","g_product_1:"+products);


        brands = intent.getStringExtra("brand");
        Log.e("productdetails","g_brand_1:"+brands);

        price = intent.getStringExtra(EXTRA_PRICE);
        Log.e("productdetails","g_price_1:"+price);



        t1 = (TextView)findViewById(R.id.productid);
        t3 = (TextView)findViewById(R.id.brandid);
        t4 = (TextView)findViewById(R.id.sizeid);
        t5 = (TextView)findViewById(R.id.thicknessid);
        t6 = (TextView)findViewById(R.id.priceid);
        add_cart = (Button)findViewById(R.id.btn_confrim);
        buy_now = findViewById(R.id.btn_buynow);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_home:
                        Intent in = new Intent(productdetails.this, First_activity.class);
                        startActivity(in);
                        break;
                    case R.id.action_cart:
                        Intent in1 = new Intent(productdetails.this, CartDetails.class);
                        startActivity(in1);
                        break;
                    case R.id.action_logout:
                        Intent in2 = new Intent(productdetails.this, Login.class);
                        startActivity(in2);
                        break;
                }
                return false;
            }
        });

        ed1 = (EditText)findViewById(R.id.quantity);
        user = sessionManager.getUserDetails();
        struser = user.get(sessionManager.KEY_NAME);
        strmobile = user.get(sessionManager.KEY_MOBILE);
        strid = user.get(sessionManager.KEY_ID);


        System.out.println("sssvalue"+struser+strid);

        t1.setText(products);
        t3.setText(brands);
        t4.setText(psize);
        t5.setText(pthickness);
        ed1.addTextChangedListener(this);


        add_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantity = ed1.getText().toString();
                ply_product = t1.getText().toString();
                ply_brand = t3.getText().toString();
                ply_thickness = t5.getText().toString();
                ply_size = t4.getText().toString();
                ply_price = t6.getText().toString();

                if (TextUtils.isEmpty(quantity)) {
                    ed1.setError("Enter the quantity");
                    ed1.requestFocus();
                    return;
                } else {
                    registerUser(strid,struser,strmobile,ply_product,ply_brand,ply_thickness,ply_size,quantity,ply_price);
                    System.out.println("sssscartvalue"+""+strid+strmobile+struser+ply_product+ply_type+ply_brand+ply_size+quantity+ply_price);
                }

            }
        });

        buy_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantity = ed1.getText().toString();
                ply_product = t1.getText().toString();
                ply_brand = t3.getText().toString();
                ply_thickness = t5.getText().toString();
                ply_size = t4.getText().toString();
                ply_price = t6.getText().toString();

                if (TextUtils.isEmpty(quantity)) {
                    ed1.setError("Enter the quantity");
                    ed1.requestFocus();
                    return;
                } else {
                    registerUser_2(strid,struser,strmobile,ply_product,ply_brand,ply_thickness,ply_size,quantity,ply_price);
                    System.out.println("sssscartvalue"+""+strid+strmobile+struser+ply_product+ply_type+ply_brand+ply_size+quantity+ply_price);
                }

            }
        });
    }

    private void registerUser_2(final String uid,final String uname,final String umobile,final String cproduct,final String cbrand,final String cthickness,final String csize,final String cquantity,final String cprice) {

        dialog = new ProgressDialog(productdetails.this);
        dialog.setMessage("Pleasewait....");
        dialog.show();

        // Tag used to cancel the request
        String tag_string_req = "req_register";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                "https://www.binary2quantumsolutions.com/intpro/plywood_cart_insert.php", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                //	Log.d(TAG, "Register Response: " + response.toString());

                try
                {
                    JSONObject jObj = new JSONObject(response);
                    strsuccess = jObj.getString("success");
                    // Check for error node in json
                    if (strsuccess.equals("1")) {


                        strmessage = jObj.getString("message");
                        System.out.println("responcevalue"+strsuccess+" "+strmessage);

                        Toast.makeText(productdetails.this, strmessage, Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                        Intent intent = new Intent(productdetails.this, CartDetails.class);
                        startActivity(intent);
                    } else {
                        String errorMsg = jObj.getString("message");
                        Toast.makeText(productdetails.this, errorMsg, Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //	Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), "Check the Internet Connection", Toast.LENGTH_LONG).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id",uid);
                Log.e("productdetails","uid:"+uid);
                params.put("name", uname);
                Log.e("productdetails","name:"+uname);
                params.put("mobileno",umobile);
                params.put("product",cproduct);
                Log.e("productdetails","product:"+cproduct);
                //params.put("type",ctype);
                params.put("brand",cbrand);
                Log.e("productdetails","brand:"+cbrand);
                params.put("thickness",cthickness);
                Log.e("productdetails","thickness:"+cthickness);
                params.put("size",csize);
                Log.e("productdetails","size:"+csize);
                params.put("quantity",cquantity);
                Log.e("productdetails","quantity:"+cquantity);
                params.put("price",cprice);
                Log.e("productdetails","price:"+cprice);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


    private void registerUser(final String uid,final String uname,final String umobile,final String cproduct,final String cbrand,final String cthickness,final String csize,final String cquantity,final String cprice)

    {
        dialog = new ProgressDialog(productdetails.this);
        dialog.setMessage("Pleasewait....");
        dialog.show();

        // Tag used to cancel the request
        String tag_string_req = "req_register";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                "https://www.binary2quantumsolutions.com/intpro/plywood_cart_insert.php", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                //	Log.d(TAG, "Register Response: " + response.toString());

                try
                {
                    JSONObject jObj = new JSONObject(response);
                    strsuccess = jObj.getString("success");
                    // Check for error node in json
                    if (strsuccess.equals("1")) {


                        strmessage = jObj.getString("message");
                        System.out.println("responcevalue"+strsuccess+" "+strmessage);

                        Toast.makeText(productdetails.this, strmessage, Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                        Intent intent = new Intent(productdetails.this, First_activity.class);
//                        intent.putExtra("Singleprice",price);
//                        Log.e("productdetails","g_price:"+price);

                        startActivity(intent);



                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("message");

                        // Toast.makeText(getApplicationContext()errorMsg, Toast.LENGTH_LONG).show();

						/*snackbar = Snackbar.make(cl, errorMsg, Snackbar.LENGTH_LONG);
                        snackbar.show();*/

                        Toast.makeText(productdetails.this, errorMsg, Toast.LENGTH_SHORT).show();


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //	Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), "Check the Internet Connection", Toast.LENGTH_LONG).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id",uid);
                params.put("name", uname);
                params.put("mobileno",umobile);
                params.put("product",cproduct);
                //params.put("type",ctype);
                params.put("brand",cbrand);
                params.put("thickness",cthickness);
                params.put("size",csize);
                params.put("quantity",cquantity);
                params.put("price",cprice);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

        quantity = ed1.getText().toString();

        if (TextUtils.isEmpty(quantity)) {
            ed1.setError("Enter the quantity");
            ed1.requestFocus();
            return;
        } else {
            amt = Integer.parseInt(quantity) * Integer.parseInt(price);
            totalprice = String.valueOf(amt);
            t6.setText(totalprice);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.about_us) {
            Intent in2 = new Intent(productdetails.this, About_us.class);
            startActivity(in2);
            return true;
        }  else if(id == R.id.contact){
            Intent in3 = new Intent(productdetails.this, Contact.class);
            startActivity(in3);
            return true;
        } else if (id == R.id.rate){
            Intent intent = new Intent(Intent.ACTION_VIEW);
            // https://play.google.com/store/apps/details?id=com.binary2quantum.android.intpro
            intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.binary2quantum.android.intpro"));
            startActivity(intent);
            return true;
        } else if (id == R.id.share){
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=venkatkural.travelmate&hl=en");
            intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Check out this site!");
            startActivity(Intent.createChooser(intent, "Share"));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(productdetails.this, First_activity.class);
        startActivity(intent);
        finish();
    }
}
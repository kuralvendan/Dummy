package com.binary2quantumtechbase.andapp.intpro;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.binary2quantumtechbase.andapp.intpro.Adapter.CartAdapter;
import com.binary2quantumtechbase.andapp.intpro.databases.AppController;
import com.binary2quantumtechbase.andapp.intpro.databases.SessionManager;
import com.binary2quantumtechbase.andapp.intpro.module.cartItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.binary2quantumtechbase.andapp.intpro.plywoods.plywooddetails.EXTRA_SIZE;
import static com.binary2quantumtechbase.andapp.intpro.plywoods.plywooddetails.EXTRA_THICK;

public class CartDetails extends AppCompatActivity implements CartAdapter.OnItemClickListener {

    private RecyclerView mRecyclerView;
    public Dialog dialog;
    private CartAdapter mcartadapter;
    private ArrayList<cartItem> mlist;
    private com.android.volley.RequestQueue mRequestQueue;
    SessionManager sessionManager;
    HashMap<String, String> user;
    String userid, product, brand, quantity, price, id, thickness,size;
    String strsuccess, strmessage, totalamount, finalamount;

    JSONObject jsonObject;
    JSONArray jsonArray;
    Button btn_order;
    int totalPrice = 0;
    TextView tv;
    BottomNavigationView bottomNavigationView;
//    ProgressDialog printing;
//    String url = "https://www.onlinesbi.com/sbicollect/icollecthome.htm?corpID=940159";

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_details);
        dialog = new Dialog(this);
        sessionManager = new SessionManager(this);
        user = sessionManager.getUserDetails();
        userid = user.get(sessionManager.KEY_ID);
        jsonObject = new JSONObject();
        jsonArray = new JSONArray();
        mlist = new ArrayList<>();

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_home:
                        Intent in = new Intent(CartDetails.this, First_activity.class);
                        startActivity(in);
                        break;
                    case R.id.action_cart:
//                        Intent in1 = new Intent(productdetails.this, CartDetails.class);
//                        startActivity(in1);
                        break;
                    case R.id.action_logout:
                        Intent in2 = new Intent(CartDetails.this, Login.class);
                        startActivity(in2);
                        break;
                }
                return false;
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_cart);
        tv = (TextView) findViewById(R.id.tv_total);
        btn_order = (Button) findViewById(R.id.btn_placeorder);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mlist.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "No Item Found", Toast.LENGTH_LONG).show();
                } else {
//                    finalamount = tv.getText().toString();
//                    deletetempdata(userid);
//
//                    Intent intent = new Intent(CartDetails.this, Userdetails.class);
//                    startActivity(intent);
                    ordersubmit(userid);
                }
            }
        });

        registerUser(userid);
    }

    private void registerUser(final String uid){

        // Tag used to cancel the request
        String tag_string_req = "req_register";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                "https://www.binary2quantumsolutions.com/intpro/cart_select.php", new Response.Listener<String>() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {
                //	Log.d(TAG, "Register Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);

                    jsonArray = jObj.getJSONArray("tbl_customer");

                    JSONObject j1 = new JSONObject();
                    System.out.println("sssscartval" + " " + jsonArray.toString());

                    for (int i = 0; i < jsonArray.length(); i++) {

                        j1 = jsonArray.getJSONObject(i);
                        id = j1.getString("id");
                        Log.e("Cartdetails","id:"+id);

                        product = j1.getString("product");
                        Log.e("Cartdetails","product:"+product);

                        brand = j1.getString("brand");
                        Log.e("Cartdetails","brand:"+brand);

                        quantity = j1.getString("quantity");
                        Log.e("Cartdetails","qty:"+quantity);

                        price = j1.getString("price");
                        Log.e("Cartdetails","price:"+price);

                        thickness = j1.getString(EXTRA_THICK);
                        Log.e("Cartdetails","thickness:"+thickness);

                        size = j1.getString(EXTRA_SIZE);
                        Log.e("Cartdetails","size:"+size);

                        System.out.println("aaaavcartval" + " " + product + brand + price + quantity);
                        mlist.add(new cartItem(id, product, brand, quantity, price, thickness, size));

                    }
                    mcartadapter = new CartAdapter(CartDetails.this, mlist);
                    mRecyclerView.setAdapter(mcartadapter);
                    mcartadapter.setOnItemClickListener(CartDetails.this);

                    for (int i = 0; i < mlist.size(); i++) {
                        try{
                            totalPrice += Integer.parseInt(mlist.get(i).getAmount());
                        } catch (NumberFormatException e){
                            e.printStackTrace();
                        }

                    }

                    totalamount = String.valueOf(totalPrice);
                    tv.setText("Rs." + totalamount);


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //	Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), "Check the Internet Connection", Toast.LENGTH_LONG).show();
                //  hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", uid);
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void ordersubmit(final String uid) {


        // Tag used to cancel the request
        String tag_string_req = "req_register";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                "https://www.binary2quantumsolutions.com/intpro/cart_final_submit.php", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                //	Log.d(TAG, "Register Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);

                    strsuccess = jObj.getString("success");
                    strmessage = jObj.getString("message");

                    if (strsuccess.equals("1")) {
                        finalamount = tv.getText().toString();

                        deletetempdata(uid);

                        Intent intent = new Intent(CartDetails.this, Userdetails.class);

                        intent.putExtra("our_product",product);
                        Log.e("firebase","our_product:"+product);

                        intent.putExtra("product_brand",brand);
                        Log.e("firebase","product_brand:"+brand);

                        intent.putExtra("product_type1",thickness);
                        Log.e("firebase","product_type1:"+thickness);

                        intent.putExtra("product_type2",size);
                        Log.e("firebase","product_type2:"+size);

                        intent.putExtra("product_quantity",quantity);
                        Log.e("firebase","product_qty:"+quantity);

                        intent.putExtra("single_price",price);
                        Log.e("firebase","single:"+price);

                        intent.putExtra("finalprice", finalamount);
                        Log.e("firebase","finalprice:"+finalamount);

                        startActivity(intent);
                    } else {
                        Toast.makeText(CartDetails.this, strmessage, Toast.LENGTH_SHORT).show();
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
                //  hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", uid);
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


    private void deletetempdata(final String uid) {

        // Tag used to cancel the request
        String tag_string_req = "req_register";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                "https://www.binary2quantumsolutions.com/intpro/cart_delete.php", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                //	Log.d(TAG, "Register Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);

                    strsuccess = jObj.getString("success");
                    strmessage = jObj.getString("message");

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //	Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), "Check the Internet Connection", Toast.LENGTH_LONG).show();
                //  hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", uid);
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


    @Override
    public void onItemClick_Delete(int position) {
        cartItem itemclick = mlist.get(position);

        id = itemclick.getId();

        final AlertDialog.Builder localBuilder = new AlertDialog.Builder(CartDetails.this);
        localBuilder.setCancelable(false);
        localBuilder.setMessage("Do you want to Delete an Item ");
        localBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface paramDialogInterface,
                                        int paramInt) {
                        deleteitem(id);


                    }

                });
        localBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface paramDialogInterface,
                                        int paramInt) {

                        finish();
                    }
                });
        localBuilder.create().show();


        System.out.println("ccccvalue" + " " + id);

    }



    private void deleteitem(final String id) {

        // Tag used to cancel the request
        String tag_string_req = "req_register";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                "https://www.binary2quantumsolutions.com/intpro/cart_item_delete.php", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                //	Log.d(TAG, "Register Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);

                    strsuccess = jObj.getString("success");
                    strmessage = jObj.getString("message");

                    if (strsuccess.equals("1")) {
                        Toast.makeText(CartDetails.this, strmessage, Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(CartDetails.this, CartDetails.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(CartDetails.this, strmessage, Toast.LENGTH_SHORT).show();
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
                //  hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", id);
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(CartDetails.this, First_activity.class);
        startActivity(intent);
        finish();
    }
}
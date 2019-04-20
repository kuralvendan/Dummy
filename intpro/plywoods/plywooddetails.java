package com.binary2quantum.android.intpro.plywoods;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.binary2quantum.android.intpro.About_us;
import com.binary2quantum.android.intpro.Adapter.PlywooddetailsAdapter;
import com.binary2quantum.android.intpro.CartDetails;
import com.binary2quantum.android.intpro.Contact;
import com.binary2quantum.android.intpro.First_activity;
import com.binary2quantum.android.intpro.Login;
import com.binary2quantum.android.intpro.R;
import com.binary2quantum.android.intpro.databases.AppController;
import com.binary2quantum.android.intpro.module.Pricemodule;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class plywooddetails extends AppCompatActivity implements PlywooddetailsAdapter.OnItemClickListener {

    public static final String EXTRA_THICK = "thickness";
    public static final String EXTRA_SIZE = "size";
    public static final String EXTRA_PRICE = "price";

    private RecyclerView mRecyclerView;
    private PlywooddetailsAdapter mplywoodadapter;
    private ArrayList<Pricemodule> mlist;
    private com.android.volley.RequestQueue mRequestQueue;
    String strproduct, strbrand, username, userid;

    JSONArray jsonArray;
    TextView t1;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plywooddetails);

        Intent intent = getIntent();
        strproduct = intent.getStringExtra("plywood");
        username = intent.getStringExtra("user_name");
        userid = intent.getStringExtra("user_id");
        strbrand = intent.getStringExtra("brand");


        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_home:
                        Intent in = new Intent(
                                plywooddetails.this, First_activity.class);
                        startActivity(in);
                        break;
                    case R.id.action_cart:
                        Intent in1 = new Intent(plywooddetails.this, CartDetails.class);
                        startActivity(in1);
                        break;
                    case R.id.action_logout:
                        Intent in2 = new Intent(plywooddetails.this, Login.class);
                        startActivity(in2);
                        break;
                }
                return false;
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        t1 = findViewById(R.id.brandname);
        t1.setText(strbrand);

        mlist = new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(this);

        registerUser(strbrand);


    }

    public void ParseJson(final String brand) {
        String url = "https://www.binary2quantumsolutions.com/intpro/size_thickness_price.php";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("tbl_customer");
                    System.out.println("ssssvalues" + jsonArray.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String sthickness = jsonObject.getString("thickness");
                        String ssize = jsonObject.getString("size");
                        String sprice = jsonObject.getString("product_price");

                        System.out.println("sssscheck" + ssize + sthickness + sprice);
                        mlist.add(new Pricemodule(sthickness, ssize, sprice));
                    }
                    mplywoodadapter = new PlywooddetailsAdapter(plywooddetails.this, mlist);
                    mRecyclerView.setAdapter(mplywoodadapter);
                    mplywoodadapter.setOnItemClickListener(plywooddetails.this);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }

        });
        mRequestQueue.add(request);
    }

    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(plywooddetails.this, productdetails.class);
        Pricemodule itemclick = mlist.get(position);
        detailIntent.putExtra(EXTRA_THICK, itemclick.getUthickness());
        Log.e("plywood_details","thick:"+itemclick.getUthickness());
        detailIntent.putExtra(EXTRA_SIZE, itemclick.getUsize());
        Log.e("plywood_details","size:"+itemclick.getUsize());
        detailIntent.putExtra(EXTRA_PRICE, itemclick.getUprice());
        Log.e("plywood_details","price:"+itemclick.getUprice());
        detailIntent.putExtra("plywood", strproduct);
        Log.e("plywood_details","product:"+strproduct);
//        detailIntent.putExtra("type", strtype);
//        Log.e("plywood_details","type:"+strtype);
        detailIntent.putExtra("brand", strbrand);
        Log.e("plywood_details","brand:"+strbrand);
        startActivity(detailIntent);

    }


    private void registerUser(final String uid) {

        // Tag used to cancel the request
        String tag_string_req = "req_register";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                "https://www.binary2quantumsolutions.com/intpro/size_thickness_price.php", new Response.Listener<String>() {

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
                        String sthickness = j1.getString("thickness");
                        String ssize = j1.getString("size");
                        String sprice = j1.getString("product_price");

                        System.out.println("sssscheck" + ssize + sthickness + sprice);
                        mlist.add(new Pricemodule(sthickness, ssize, sprice));
                    }
                    mplywoodadapter = new PlywooddetailsAdapter(plywooddetails.this, mlist);
                    mRecyclerView.setAdapter(mplywoodadapter);
                    mplywoodadapter.setOnItemClickListener(plywooddetails.this);

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
                params.put("productbrand", uid);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(plywooddetails.this, plywoodbrands.class);
        startActivity(intent);
        finish();
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
            Intent in2 = new Intent(plywooddetails.this, About_us.class);
            startActivity(in2);
            return true;
        }  else if(id == R.id.contact){
            Intent in3 = new Intent(plywooddetails.this, Contact.class);
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
            intent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.binary2quantum.android.intpro");
            intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Check out this site!");
            startActivity(Intent.createChooser(intent, "Share"));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

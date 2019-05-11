package com.binary2quantumtechbase.andapp.intpro;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.binary2quantumtechbase.andapp.intpro.databases.AppController;
import com.binary2quantumtechbase.andapp.intpro.databases.SessionManager;
import com.binary2quantumtechbase.andapp.intpro.module.OrdertoDelivery;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import instamojo.library.InstamojoPay;
import instamojo.library.InstapayListener;

public class Userdetails extends AppCompatActivity {

    public EditText user_name, user_mobno, user_mail, user_address;
    public TextView  term_condition, refer;
    public Button pay_btn;
    public String cname, cmobile, cemail, caddress, camount, price, user_id;
    public String purpose, our_brand, our_type1, our_type2, per_item_price, our_quantity, refer_co;
    public String[] price1;
    public CheckBox ch1;
    public Dialog dialog;
//    TextView totalamount;
    EditText totalamount;
    String delivery_status = "no";
    String strsuccess, strmessage;
    FirebaseDatabase mDatabase;

    String orderuserid;
    HashMap<String, String> user;
    SessionManager sessionManager;


    private void callInstamojoPay(String email, String phone, String amount, String purpose, String buyername) {
        final Activity activity = this;
        InstamojoPay instamojoPay = new InstamojoPay();
        IntentFilter filter = new IntentFilter("ai.devsupport.instamojo");
        registerReceiver(instamojoPay, filter);
        JSONObject pay = new JSONObject();
        try {
            pay.put("email", email);
            pay.put("phone", phone);
            pay.put("purpose", purpose);
            pay.put("amount", amount);
            pay.put("name", buyername);
            pay.put("send_sms", true);
            pay.put("send_email", true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        initListener();
        instamojoPay.start(activity, pay, listener);
    }

    InstapayListener listener;
    private void initListener() {
        listener = new InstapayListener() {
            @Override
            public void onSuccess(String response) {
                SavetoFirebasedb();

//                deletetempdata(user_id);

                Intent intent = new Intent(Userdetails.this, First_activity.class);
                startActivity(intent);
                finish();
                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
            }
            @Override
            public void onFailure(int code, String reason) {
                Toast.makeText(getApplicationContext(), "Failed: " + reason, Toast.LENGTH_LONG).show();
            }
        };
    }

    private void SavetoFirebasedb() {
        DatabaseReference mRefer = mDatabase.getReference("OrderList");
        OrdertoDelivery ordertoDelivery = new OrdertoDelivery();

        ordertoDelivery.setId(user_id);

        cname = user_name.getText().toString();
        ordertoDelivery.setCustumer_name(cname);

        cmobile = user_mobno.getText().toString();
        ordertoDelivery.setCustumer_mobile(cmobile);

        cemail = user_mail.getText().toString();
        ordertoDelivery.setCustumer_email(cemail);

        caddress = user_address.getText().toString();
        ordertoDelivery.setCustumer_address(caddress);

        ordertoDelivery.setProduct(purpose);

        ordertoDelivery.setBrand(our_brand);

        ordertoDelivery.setCategory1(our_type1);

        ordertoDelivery.setCategory2(our_type2);

        ordertoDelivery.setQuantity(our_quantity);

        ordertoDelivery.setSingle_price(per_item_price);

        camount = totalamount.getText().toString();
        ordertoDelivery.setAmount(camount);

        ordertoDelivery.setReference_code(refer_co);

        ordertoDelivery.setDelivery_status(delivery_status);

        mRefer.push().setValue(ordertoDelivery);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userdetails);

        sessionManager = new SessionManager(this);
        user = sessionManager.getUserDetails();
        orderuserid = user.get(sessionManager.KEY_ID);
        Log.e("order","orderuserid:"+ orderuserid);

        mDatabase = FirebaseDatabase.getInstance();

        dialog = new Dialog(this);
        Intent intent = getIntent();
        price = intent.getStringExtra("finalprice");
        user_id = intent.getStringExtra("useridenty");
        purpose = intent.getStringExtra("our_product");
        Log.e("firebase","our_product:"+purpose);

        our_brand = intent.getStringExtra("product_brand");
        Log.e("firebase","product_brand:"+our_brand);

        our_type1 = intent.getStringExtra("product_type1");
        our_type2 = intent.getStringExtra("product_type2");
        our_quantity = intent.getStringExtra("product_quantity");
        per_item_price = intent.getStringExtra("single_price");

        Log.e("Userdetails","user number:"+user_id);
        Log.e("Userdetails","amount:"+price);
        Log.e("Userdetails","product:"+purpose);
        Log.e("Userdetails", "brand:"+our_brand);



        price1 = price.split("\\.",2);
        totalamount =  findViewById(R.id.price);

        refer = findViewById(R.id.reference);

        user_name = (EditText) findViewById(R.id.user);
        user_mobno = (EditText) findViewById(R.id.mobile);
        user_mail = (EditText) findViewById(R.id.email);
        user_address = (EditText) findViewById(R.id.address);

        ch1 = (CheckBox)findViewById(R.id.checkbox);
        term_condition = (TextView)findViewById(R.id.text_terms);
        pay_btn = (Button) findViewById(R.id.btn_proceed);
        totalamount.setText(price1[1]);

        term_condition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Showdialog();
            }
        });
        pay_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pay_button();
            }
        });


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
            Intent in2 = new Intent(Userdetails.this, About_us.class);
            startActivity(in2);
            return true;
        }   else if(id == R.id.contact){
            Intent in3 = new Intent(Userdetails.this, Contact.class);
            startActivity(in3);
            return true;
        }else if (id == R.id.rate){
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


    public void pay_button() {
        cname = user_name.getText().toString();
        cemail = user_mail.getText().toString();
        cmobile = user_mobno.getText().toString();
        caddress = user_address.getText().toString();
        refer_co = refer.getText().toString();
        camount = totalamount.getText().toString();

        if (TextUtils.isEmpty(cname)) {
            user_name.setError("Please enter your Name");
            user_name.requestFocus();
        } else if (TextUtils.isEmpty(cmobile)) {
            user_mobno.setError("Please enter your Mobile NO");
            user_mobno.requestFocus();
        } else if (TextUtils.isEmpty(cemail)) {
            user_mail.setError("Please enter your Email Id");
            user_mail.requestFocus();
        } else if (TextUtils.isEmpty(caddress)) {
            user_address.setError("Please enter your Address");
            user_address.requestFocus();
        }
        else if(!ch1.isChecked()) {
            Toast.makeText(Userdetails.this, "Please Accept Conditions", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(Userdetails.this, "Proceeding to Payment", Toast.LENGTH_SHORT).show();
            callInstamojoPay(cemail, cmobile, camount, purpose, cname);
        }
    }

    public void Showdialog() {

        dialog.setContentView(R.layout.dialog_terms_conditions);
        Button dialogOk = (Button) dialog.findViewById(R.id.btn_ok);
        dialogOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Userdetails.this, First_activity.class);
        startActivity(intent);
        finish();
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
}
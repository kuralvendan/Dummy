package com.binary2quantum.android.intpro;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
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

import org.json.JSONException;
import org.json.JSONObject;

import instamojo.library.InstamojoPay;
import instamojo.library.InstapayListener;

public class Userdetails extends AppCompatActivity {

    public EditText user_name, user_mobno, user_mail, user_address;
    public TextView  term_condition;
    public Button pay_btn;
    public String cname, cmobile, cemail, caddress, camount, price, user_id;
    public String purpose = "Product Purchase";
    public String[] price1;
    public CheckBox ch1;
    public Dialog dialog;
    BottomNavigationView bottomNavigationView;
    EditText totalamount;
    String pay_status = "paid" ;
//    TextView totalamount;

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
          //  pay.put("address",address);
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userdetails);
        dialog = new Dialog(this);

        Intent intent = getIntent();
        price = intent.getStringExtra("finalprice");
        user_id = intent.getStringExtra("useridenty");
        purpose = intent.getStringExtra("product");
        Log.e("Userdetails","user number:"+user_id);
        Log.e("Userdetails","amount:"+price);
        Log.e("Userdetails","product:"+purpose);


        price1 = price.split("\\.",2);
//        totalamount =  findViewById(R.id.price);

        totalamount =  findViewById(R.id.price);

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

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_home:
                        Intent in = new Intent(
                                Userdetails.this, First_activity.class);
                        startActivity(in);
                        break;
                    case R.id.action_cart:

                        break;
                    case R.id.action_logout:
                        Intent in2 = new Intent(Userdetails.this, Login.class);
                        startActivity(in2);
                        break;
                }
                return false;
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

//            ((CartDetails) getParent()).deletetempdata(user_id);
//            Log.e("Userdetails","user number:"+user_id);
//             callInstamojoPay(cemail, cmobile, camount, purpose, cname, caddress);
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
}
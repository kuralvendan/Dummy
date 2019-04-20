package com.binary2quantum.android.intpro;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.binary2quantum.android.intpro.databases.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Registeration extends AppCompatActivity {

    ProgressBar progressBar;
    EditText name,mobileno,emailid,password,confirmpassword;
    String uname,umobile,uemailid,upassword,uconfirmpassword;
    Button register_btn;
    String strsuccess, strmessage, subject = "register detail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);
        name = (EditText)findViewById(R.id.user);
        mobileno = (EditText)findViewById(R.id.mobile);
        emailid = (EditText)findViewById(R.id.email);
        password =(EditText)findViewById(R.id.password);
        confirmpassword = (EditText)findViewById(R.id.confirmpassword);
        register_btn = (Button)findViewById(R.id.btn_register);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                uname = name.getText().toString();
                umobile = mobileno.getText().toString();
                uemailid = emailid.getText().toString();
                upassword = password.getText().toString();
                uconfirmpassword = confirmpassword.getText().toString();

                System.out.println("values2"+" "+uname+" "+umobile+" "+uemailid);

                if (TextUtils.isEmpty(uname)) {
                    name.setError("Please enter user name");
                    name.requestFocus();
                    return;
                }

                else if (TextUtils.isEmpty(umobile) || mobileno.length() < 9) {
                    mobileno.setError("Please enter the valid mobile number");
                    mobileno.requestFocus();
                    return;
                }

                else if (TextUtils.isEmpty(uemailid)) {
                    emailid.setError("Please enter the Email id");
                    emailid.requestFocus();
                    return;
                }

                else if (TextUtils.isEmpty(upassword)) {
                    password.setError("Please enter the Password");
                    password.requestFocus();
                    return;
                }
                else if (TextUtils.isEmpty(uconfirmpassword)) {
                    confirmpassword.setError("Please enter the Confirm Password");
                    confirmpassword.requestFocus();
                    return;
                }
                else if(!upassword.equals(uconfirmpassword)) {
                    Toast.makeText(Registeration.this, "Password Mismatch", Toast.LENGTH_SHORT).show();
                } else {
                    registerdetails(uname,umobile,uemailid,upassword);
                    System.out.println("values"+" "+uname+" "+umobile+" "+uemailid);
                }
            }
        });
    }

    public void cleartext() {
        name.setText("");
        mobileno.setText("");
        emailid.setText("");
        password.setText("");
        confirmpassword.setText("");

    }

    private void registerdetails(final String strname,final String strmobile,
                                 final String stremail,final String pass) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        progressBar.setVisibility(View.VISIBLE);


        StringRequest strReq = new StringRequest(Request.Method.POST,
                "https://www.binary2quantumsolutions.com/intpro/register.php", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                //	Log.d(TAG, "Register Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);


                    strsuccess = jObj.getString("success");

                    // Check for error node in json
                    if (strsuccess.equals("1")) {

                        strmessage = jObj.getString("message");

                        Toast.makeText(Registeration.this, "Registered Succesfully", Toast.LENGTH_SHORT).show();
                        cleartext();
                        hideDialog();
                        finish();
                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("message");

                        // Toast.makeText(getApplicationContext()errorMsg, Toast.LENGTH_LONG).show();

						/*snackbar = Snackbar.make(cl, errorMsg, Snackbar.LENGTH_LONG);
                        snackbar.show();*/

                        Toast.makeText(Registeration.this, errorMsg, Toast.LENGTH_SHORT).show();
                        hideDialog();
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
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("name",strname);
                params.put("mobileno", strmobile);
                params.put("email", stremail);
                params.put("password",pass);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void hideDialog() {
        progressBar.setVisibility(View.GONE);
    }
}

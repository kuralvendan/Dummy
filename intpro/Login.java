package com.binary2quantumtechbase.andapp.intpro;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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

public class Login extends AppCompatActivity {
    Button signup, login;
    TextView forgetpass;
    EditText user, pass;
    String username, password;
    SessionManager sessionManager;
    String Admin = "1";
    String struserid, strmessage, strsuccess, strname, strmobile, stremail;
    ProgressBar progressBar;
    String forgetmobile, forgetemail;
    String changepass, confirmpass, changemobile;
    Dialog dialog, dialog2;
    EditText ed1, ed2, ec1, ec2, ec3;
    ProgressDialog dialog1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signup = (Button) findViewById(R.id.btn_signup);
        login = (Button) findViewById(R.id.btn_login);
        forgetpass = (TextView) findViewById(R.id.tx_forgotpassword);
        user = (EditText) findViewById(R.id.usr);
        pass = (EditText) findViewById(R.id.psd);
        sessionManager = new SessionManager(getApplicationContext());
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = user.getText().toString();
                password = pass.getText().toString();

                if (TextUtils.isEmpty(username)) {
                    user.setError("Please enter your user mobile no");
                    user.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    pass.setError("Please enter your password");
                    pass.requestFocus();
                    return;
                }
                if (username.equals("9876543210") && password.equals("Admin")) {
                    sessionManager.createLoginSession(Admin, "Admin", "9876543210", "Administrator@gmail.com");
                    Toast.makeText(Login.this, "Admin Login Successfully", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Login.this, First_activity.class);
                    i.putExtra("admin", Admin);
                    startActivity(i);
                } else {
                    registerUser(username, password);

                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Registeration.class);
                startActivity(intent);
            }
        });

        forgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkdetails();
            }
        });
    }

    public void clearText() {
        user.setText("");
        pass.setText("");
    }

    private void registerUser(final String umobie,
                              final String upass) {

        // Tag used to cancel the request
        String tag_string_req = "req_register";

        progressBar.setVisibility(View.VISIBLE);


        StringRequest strReq = new StringRequest(Request.Method.POST,
                "https://www.binary2quantumsolutions.com/intpro/Login.php", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                //	Log.d(TAG, "Register Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    strsuccess = jObj.getString("success");
                    strname = jObj.getString("name1");
                    struserid = jObj.getString("id");
                    strmobile = jObj.getString("mobileno");
                    stremail = jObj.getString("email");

                    sessionManager.createLoginSession(struserid, strname, strmobile, stremail);

                    System.out.println("ssssessionvalue" + struserid + strname + strmobile + stremail);

                    // Check for error node in json
                    if (strsuccess.equals("1")) {

                        strmessage = jObj.getString("message");
                        System.out.println("responcevalue" + strsuccess + " " + strmessage);

                        Toast.makeText(Login.this, strname + " " + "Login Succesfully", Toast.LENGTH_SHORT).show();
                        hideDialog();

                        Intent i = new Intent(Login.this, First_activity.class);
                        i.putExtra("name", strname);
                        Log.e("login","name:"+strname);

                        i.putExtra("userid",struserid);
                        Log.e("login","name:"+struserid);

                        startActivity(i);
                    } else {
                        // Error occurred in registration. Get the error
                        // message
                        //String errorMsg = jObj.getString("message");
                        //Toast.makeText(Login.this, errorMsg, Toast.LENGTH_SHORT).show();
                        Toast.makeText(Login.this, "Invalid Account", Toast.LENGTH_SHORT).show();
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
                params.put("username", umobie);
                params.put("password", upass);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void hideDialog() {
        progressBar.setVisibility(View.GONE);
    }

    public void checkdetails() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.forget_password);
        dialog.setTitle("Forget Password");

        Button btn1, btn2;
        ed1 = (EditText) dialog.findViewById(R.id.et_forgetmobile);
        ed2 = (EditText) dialog.findViewById(R.id.et_forgetemail);

        btn1 = (Button) dialog.findViewById(R.id.btn_forgetsubmit);
        btn2 = (Button) dialog.findViewById(R.id.btn_forgetcancel);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgetmobile = ed1.getText().toString();
                forgetemail = ed2.getText().toString();
                forgetcheck(forgetmobile, forgetemail);
                System.out.println("checklogin" + forgetmobile + forgetemail);
            }
        });
        dialog.show();
    }

    public void forgetcheck(final String fmobile, final String femail) {
        String tag_string_req = "req_register";
        dialog1 = ProgressDialog.show(Login.this, "", "Please wait... ", true, false);

        //progressBar.setVisibility(View.VISIBLE);
        StringRequest strReq = new StringRequest(Request.Method.POST,
                "https://www.binary2quantumsolutions.com/intpro/forgetpasswordcheck.php", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                //	Log.d(TAG, "Register Response: " + response.toString());
                // hideDialog();
                dialog1.dismiss();
                try {
                    JSONObject jObj = new JSONObject(response);


                    strsuccess = jObj.getString("success");
                    System.out.println("strsucess" + strsuccess);
                    // Check for error node in json
                    if (strsuccess.equals("1")) {


                        strmessage = jObj.getString("message");
                        changepassword();

                        Toast.makeText(Login.this, strmessage, Toast.LENGTH_SHORT).show();

                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("message");

                        // Toast.makeText(getApplicationContext()errorMsg, Toast.LENGTH_LONG).show();

						/*snackbar = Snackbar.make(cl, errorMsg, Snackbar.LENGTH_LONG);
                        snackbar.show();*/

                        Toast.makeText(Login.this, errorMsg, Toast.LENGTH_SHORT).show();
                        dialog1.dismiss();
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
                dialog1.cancel();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("mobileno", fmobile);
                params.put("email_id", femail);
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public void changepassword() {
        dialog2 = new Dialog(this);
        dialog2.setContentView(R.layout.changepassword);
        dialog2.setTitle("      Change Password     ");
        ec1 = (EditText) dialog2.findViewById(R.id.et_d_password);
        ec2 = (EditText) dialog2.findViewById(R.id.et_d_confirmpassword);
        ec3 = (EditText) dialog2.findViewById(R.id.et_d_mobile);
        Button bc1, bc2;
        bc1 = (Button) dialog2.findViewById(R.id.btn_changesubmit);
        bc2 = (Button) dialog2.findViewById(R.id.btn_d_forgetcancel);
        bc1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changepass = ec1.getText().toString();
                confirmpass = ec2.getText().toString();
                changemobile = ec3.getText().toString();
                if (changepass.equals(confirmpass)) {
                    updatepassword(changepass, changemobile);
                } else {
                    Toast.makeText(Login.this, "Password Mismatch", Toast.LENGTH_SHORT).show();
                }

            }
        });
        bc2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2.dismiss();
            }
        });
        dialog2.show();
    }

    public void updatepassword(final String cmobile, final String cpass) {
        String tag_string_req = "req_register";
        dialog1 = ProgressDialog.show(Login.this, "", "Please wait... ", true, false);

        //progressBar.setVisibility(View.VISIBLE);
        StringRequest strReq = new StringRequest(Request.Method.POST,
                "https://www.binary2quantumsolutions.com/intpro/changepassword.php", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                //	Log.d(TAG, "Register Response: " + response.toString());
                // hideDialog();
                dialog1.dismiss();

                try {
                    JSONObject jObj = new JSONObject(response);

                    strsuccess = jObj.getString("success");

                    // Check for error node in json
                    if (strsuccess.equals("1")) {


                        strmessage = jObj.getString("message");

                        Toast.makeText(Login.this, "Password Changed Successfully", Toast.LENGTH_SHORT).show();
                        dialog2.dismiss();
                        dialog.dismiss();
                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("message");

                        // Toast.makeText(getApplicationContext()errorMsg, Toast.LENGTH_LONG).show();

						/*snackbar = Snackbar.make(cl, errorMsg, Snackbar.LENGTH_LONG);
                        snackbar.show();*/

                        Toast.makeText(Login.this, errorMsg, Toast.LENGTH_SHORT).show();
                        dialog1.dismiss();
                        dialog2.dismiss();
                        dialog.dismiss();
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
                params.put("mobileno", cmobile);
                params.put("password", cpass);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    @Override
    public void onBackPressed() {
       
    }
}

package com.binary2quantumtechbase.andapp.intpro;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.binary2quantumtechbase.andapp.intpro.databases.AppController;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class VerifyPhoneActivity extends AppCompatActivity {


    private String mVerificationId;
    private EditText editTextCode;
    public TextView nameF, mobnoF, mailF;
    String usermobileno, username,  usermail, userpassword;
    private FirebaseAuth mAuth;
    String code1 ,code2;
    public String strsuccess, strmessage;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone);

        //initializing objects
        mAuth = FirebaseAuth.getInstance();
        editTextCode = findViewById(R.id.editTextCode);
        nameF = findViewById(R.id.userF);
        mobnoF = findViewById(R.id.mobileF);
        mailF = findViewById(R.id.emailF);

        progressBar = findViewById(R.id.progressbar);

        Intent intent = getIntent();
        usermobileno = intent.getStringExtra("Mobile");
        Log.e("verify","usermobile:"+usermobileno);
        usermail = intent.getStringExtra("Email");
        username = intent.getStringExtra("Name");
        userpassword = intent.getStringExtra("Password");

        sendVerificationCode(usermobileno);

        nameF.setText(username);
        mobnoF.setText(usermobileno);
        Log.e("verify","usermobile:"+mobnoF);
        mailF.setText(usermail);

        //if the automatic sms detection did not work, user can also enter the code manually
        //so adding a click listener to the button
        findViewById(R.id.buttonSignIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                code1 = editTextCode.getText().toString().trim();
                if (code1.isEmpty() || code1.length() < 6) {
                    editTextCode.setError("Enter valid code");
                    editTextCode.requestFocus();
                    return;
                }
                //verifying the code entered manually
                verifyVerificationCode(code1);
            }
        });
    }

    //the method is sending verification code
    //the country id is concatenated
    //you can take the country id as user input as well
    private void sendVerificationCode(String usermobileno1) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + usermobileno1,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);
    }


    //the callback to detect the verification status
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            //Getting the code sent by SMS
            code2 = phoneAuthCredential.getSmsCode();

            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code
            if (code2 != null) {
                editTextCode.setText(code2);
                //verifying the code
                verifyVerificationCode(code2);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(VerifyPhoneActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            //storing the verification id that is sent to the user
            mVerificationId = s;
        }
    };


    private void verifyVerificationCode(String code) {
        //creating the credential
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);

        //signing the user
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(VerifyPhoneActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //verification successful we will start the profile activity

                            registerdetails(username, usermobileno, usermail, userpassword);

                            Intent intent = new Intent(VerifyPhoneActivity.this, Login.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                        } else {

                            //verification unsuccessful.. display an error message
                            Toast.makeText(VerifyPhoneActivity.this, "Somthing is wrong, we will fix it soon...", Toast.LENGTH_SHORT).show();
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(VerifyPhoneActivity.this, "Invalid code entered...", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                });
    }



    private void registerdetails(final String strname, final String strmobile,
                                 final String stremail, final String pass) {
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

                        Toast.makeText(VerifyPhoneActivity.this, "Registered Succesfully", Toast.LENGTH_SHORT).show();
                        hideDialog();
                        finish();
                    } else {
                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("message");

                        Toast.makeText(VerifyPhoneActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
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
                //  hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", strname);
                params.put("mobileno", strmobile);
                params.put("email", stremail);
                params.put("password", pass);

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
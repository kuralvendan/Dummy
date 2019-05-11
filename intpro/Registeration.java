package com.binary2quantumtechbase.andapp.intpro;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Registeration extends AppCompatActivity {

    ProgressBar progressBar;
    EditText name,mobileno,emailid,password,confirmpassword;
    String uname,umobile,uemailid,upassword,uconfirmpassword;
    Button register_btn, btn_submit;
    String strsuccess, strmessage, subject = "register detail";

    private FirebaseAuth mAuth;
    private String mVerificationId;
    public Dialog dialog;
    String  code;
    EditText otp;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);

        mAuth = FirebaseAuth.getInstance();
        dialog = new Dialog(this);

        name = (EditText)findViewById(R.id.user);
        mobileno = (EditText)findViewById(R.id.mobile);
        emailid = (EditText)findViewById(R.id.email);
        password =(EditText)findViewById(R.id.password);
        confirmpassword = (EditText)findViewById(R.id.confirmpassword);

        btn_submit = findViewById(R.id.btn_submit);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btn_submit.setOnClickListener(new View.OnClickListener() {
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
                    SendingOtp();
                }
            }
        });
    }

    private void SendingOtp() {
        Intent intent = new Intent(Registeration.this, VerifyPhoneActivity.class);
        intent.putExtra("Name",uname);
        intent.putExtra("Email",uemailid);
        intent.putExtra("Mobile", umobile);
        intent.putExtra("Password",uconfirmpassword);
        startActivity(intent);
    }

    public void cleartext() {
        name.setText("");
        mobileno.setText("");
        emailid.setText("");
        password.setText("");
        confirmpassword.setText("");

    }
}


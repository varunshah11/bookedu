package com.example.varun.bookedu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class sign_up extends AppCompatActivity {

    private Button signup_signin,sign_up;
    private EditText email_text,password_text,mobile_text,name;
    private ProgressDialog progressDialog;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth= FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);
        sign_up=findViewById(R.id.signup_btn);
        email_text=findViewById(R.id.email_editText);
        password_text=findViewById(R.id.password_editText);
        name=findViewById(R.id.name_editText);
        mobile_text=findViewById(R.id.mobile_editText);
        signup_signin=findViewById(R.id.signup_signinbtn);


        signup_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(sign_up.this,sign_in.class);
                startActivity(i);
            }
        });

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }
    private void registerUser()
    {
        String email= email_text.getText().toString().trim();
        String password = password_text.getText().toString().trim();
        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(this,"Email cannot be empty",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this,"Password cannot be empty",Toast.LENGTH_SHORT).show();
            return;
        }

        //if all okay
        progressDialog.setMessage("Registering...");
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email,password)
        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(sign_up.this,"Registration Successful",Toast.LENGTH_LONG).show();
                    progressDialog.cancel();
                }
                else
                {
                    Toast.makeText(sign_up.this,"Oops something went wrong, Try again!!",Toast.LENGTH_LONG).show();
                    progressDialog.cancel();
                }
            }
        });
    }
}

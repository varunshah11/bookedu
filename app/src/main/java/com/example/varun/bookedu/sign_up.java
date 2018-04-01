package com.example.varun.bookedu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class sign_up extends AppCompatActivity {

    Button signup_signin,sign_up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signup_signin=findViewById(R.id.signup_signinbtn);
        signup_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(sign_up.this,sign_in.class);
                startActivity(i);
            }
        });
    }
}

package com.example.administrator.streetfood;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTxtUsername, editTxtPassword;
    Button loginButton;
    TextView registerNowButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTxtUsername = this.findViewById(R.id.editText3);
        editTxtPassword = this.findViewById(R.id.editText4);
        loginButton = this.findViewById(R.id.button);
        registerNowButton = this.findViewById(R.id.textView4);

        registerNowButton.setOnClickListener(this);
        loginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch(id){
            case R.id.button:
                break;
            case R.id.textView4:
                Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
                finish();
        }
    }
}

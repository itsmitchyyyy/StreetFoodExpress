package com.example.administrator.streetfood;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.streetfood.Customer.Customer;
import com.example.administrator.streetfood.Customer.CustomerServer;
import com.example.administrator.streetfood.Shared.DBConfig;
import com.example.administrator.streetfood.Shared.Server;
import com.example.administrator.streetfood.Shared.Users;

import java.util.prefs.Preferences;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView registerNowButton;
    Button loginButton;
    NetworkJob networkJob;
    EditText editTextEmail, editTextPassword;
    CustomerServer customerServer;
    private Server server;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        customerServer = new CustomerServer(this);
        server = new Server(this);

        networkJob = new NetworkJob().getInstance();

        editTextEmail = findViewById(R.id.editText3);
        editTextPassword = findViewById(R.id.editText4);
        registerNowButton = this.findViewById(R.id.textView2);
        loginButton = this.findViewById(R.id.button);

        registerNowButton.setOnClickListener(this);
        loginButton.setOnClickListener(this);

        networkJob.scheduleJob(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch(id){
            case R.id.button:
                loginUser();
                break;
            case R.id.textView2:
                Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
                finish();
        }
    }

    public void loginUser() {
        final String email = editTextEmail.getText().toString();
        final String password = editTextPassword.getText().toString();
        String url = DBConfig.ServerURL+"login.php";
        Users users = new Users(email, password, null, null, null, null, null);
        //        Customer customer = new Customer(email,password, null, null, null, null);
        server.login(url, users);
    }

    @Override
    protected void onStop() {
        stopService(new Intent(this, NetworkSchedulerService.class));
        super.onStop();
    }

    @Override
    protected void onStart() {
        startService(new Intent(this, NetworkSchedulerService.class));
        super.onStart();
    }
}

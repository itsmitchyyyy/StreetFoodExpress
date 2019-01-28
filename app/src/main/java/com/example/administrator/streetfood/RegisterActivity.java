package com.example.administrator.streetfood;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    Button backBtn, nextBtn;
    LinearLayout frstStep, scndStep;
    EditText birthDate, firstName, lastName, emailAddress, password;
    RadioGroup radioGroup;
    RadioButton radioGenderButton;
    CustomProgressDialog customProgressDialog;
    private int currentStep = 1;
    private String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        customProgressDialog = new CustomProgressDialog().getInstance();

        backBtn = this.findViewById(R.id.button2);
        nextBtn = this.findViewById(R.id.button3);
        frstStep = this.findViewById(R.id.firstStep);
        scndStep = this.findViewById(R.id.secondStep);
        firstName = this.findViewById(R.id.editText);
        lastName = this.findViewById(R.id.editText2);
        emailAddress = this.findViewById(R.id.editText6);
        password = this.findViewById(R.id.editText8);
        radioGroup = this.findViewById(R.id.radioGroup);
        birthDate = this.findViewById(R.id.editText7);

        backBtn.setOnClickListener(this);
        nextBtn.setOnClickListener(this);
        radioGroup.setOnCheckedChangeListener(this);
        nextBtn.setEnabled(false);

        firstName.addTextChangedListener(textWatcher);
        lastName.addTextChangedListener(textWatcher);
        emailAddress.addTextChangedListener(textWatcher);
        password.addTextChangedListener(textWatcher);
        birthDate.addTextChangedListener(textWatcher);

    }

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            if(frstStep.getVisibility() == View.VISIBLE){
                if (!firstName.getText().toString().isEmpty() ||
                        !lastName.getText().toString().isEmpty()){
                    nextBtn.setEnabled(true);
                }
            }
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if(frstStep.getVisibility() == View.VISIBLE){
                if (firstName.getText().toString().isEmpty() ||
                        lastName.getText().toString().isEmpty() ||
                        radioGroup.getCheckedRadioButtonId() == -1
                        ){
                    nextBtn.setEnabled(false);
                } else {
                    nextBtn.setEnabled(true);
                }
            }
            else if (scndStep.getVisibility() == View.VISIBLE) {
                if (birthDate.getText().toString().isEmpty() ||
                        emailAddress.getText().toString().isEmpty() ||
                        password.getText().toString().isEmpty()){
                    nextBtn.setEnabled(false);
                } else {
                    nextBtn.setEnabled(true);
                }
            }
        }
    };

    private void registerUser() {
        final String frstName = firstName.getText().toString().trim();
        final String lstName = lastName.getText().toString().trim();
        final String email = emailAddress.getText().toString().trim();
        final String bday = birthDate.getText().toString().trim();
        final String pass = password.getText().toString().trim();

        customProgressDialog.showProgress(RegisterActivity.this);
        String url = "http://192.168.0.10/streetfood/customer/insert.php";
        RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(RegisterActivity.this, "Registration Success", Toast.LENGTH_SHORT).show();
                customProgressDialog.hideProgress();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterActivity.this, "An error occurred while registering", Toast.LENGTH_SHORT).show();
                customProgressDialog.hideProgress();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("firstName", frstName);
                map.put("lastName", lstName);
                map.put("gender", gender);
                map.put("birthdate", bday);
                map.put("email", email);
                map.put("password", pass);

                return map;
            }
        };
        queue.add(request);
    }

    public void showDatePickerDialog(View v){
        DialogFragment dialogFragment = new DatePickerFragment();
        dialogFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void visibleViews(String identifier, boolean status) {
        if(identifier.equals("layouts") && status){
            frstStep.setVisibility(View.VISIBLE);
            scndStep.setVisibility(View.GONE);
        } else if (identifier.equals("layouts") && !status) {
            frstStep.setVisibility(View.GONE);
            scndStep.setVisibility(View.VISIBLE);
        }

        if (identifier.equals("buttons") && status){
            backBtn.setText("Back");
            nextBtn.setText("Register");

        } else if (identifier.equals("buttons") && !status){
            backBtn.setText("Cancel");
            nextBtn.setText("Next");
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.button2:
                switch (currentStep){
                    case 1:
                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                        finish();
                    case 2:
                        currentStep = currentStep - 1;
                        visibleViews("layouts", true);
                        visibleViews("buttons", false);
                        break;
                }
                break;
            case R.id.button3:
                switch(currentStep){
                    case 1:
                        currentStep = currentStep + 1;
                        visibleViews("layouts", false);
                        visibleViews("buttons", true);
                        nextBtn.setEnabled(false);
                        break;
                    case 2:
                        registerUser();
                        break;
                }
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
         int radioGenderButtonId = group.getCheckedRadioButtonId();
         radioGenderButton = group.findViewById(radioGenderButtonId);
        if(checkedId == -1){
            nextBtn.setEnabled(false);
        }else {
            gender = radioGenderButton.getText().toString().trim();
            if(!firstName.getText().toString().isEmpty() ||
                    !lastName.getText().toString().isEmpty()) {
                nextBtn.setEnabled(true);
            }
        }

    }
}

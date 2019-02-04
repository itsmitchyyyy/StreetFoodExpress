package com.example.administrator.streetfood;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
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

import com.example.administrator.streetfood.Customer.Customer;
import com.example.administrator.streetfood.Customer.CustomerServer;
import com.example.administrator.streetfood.Shared.CustomProgressDialog;
import com.example.administrator.streetfood.Shared.DBConfig;
import com.example.administrator.streetfood.Shared.Server;
import com.example.administrator.streetfood.Shared.Users;
import com.example.administrator.streetfood.Vendor.Vendor;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    Button backBtn, nextBtn, stepOne, stepTwo;
    LinearLayout frstStep, scndStep;
    EditText birthDate, firstName, lastName, emailAddress, password, confirmPassword;
    RadioGroup radioGroup, radioGroupType;
    RadioButton radioGenderButton, radioUserTypeButton;
    private int currentStep = 1;
    private String gender;
    private String userType;
    CustomerServer customerServer;
    CustomProgressDialog customProgressDialog;
    private String url;
    private Server server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

//        customerServer = new CustomerServer(this);
        server = new Server(this);
        customProgressDialog = new CustomProgressDialog().getInstance();

        backBtn = this.findViewById(R.id.button2);
        nextBtn = this.findViewById(R.id.button3);
        stepOne = this.findViewById(R.id.button7);
        stepTwo = this.findViewById(R.id.button8);
        frstStep = this.findViewById(R.id.firstStep);
        scndStep = this.findViewById(R.id.secondStep);
        firstName = this.findViewById(R.id.editText);
        lastName = this.findViewById(R.id.editText2);
        emailAddress = this.findViewById(R.id.editText6);
        password = this.findViewById(R.id.editText8);
        radioGroup = this.findViewById(R.id.radioGroup);
        birthDate = this.findViewById(R.id.editText7);
        radioGroupType = this.findViewById(R.id.radioGroupType);
        confirmPassword = this.findViewById(R.id.editTextConfirmPassword);

        backBtn.setOnClickListener(this);
        nextBtn.setOnClickListener(this);
        radioGroup.setOnCheckedChangeListener(this);
        radioGroupType.setOnCheckedChangeListener(this);
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
                        !lastName.getText().toString().isEmpty() ||
                        birthDate.getText().toString().isEmpty()){
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
                        radioGroup.getCheckedRadioButtonId() == -1 ||
                        birthDate.getText().toString().isEmpty()
                        ){
                    nextBtn.setEnabled(false);
                } else {
                    nextBtn.setEnabled(true);
                }
            }
            else if (scndStep.getVisibility() == View.VISIBLE) {
                if (emailAddress.getText().toString().isEmpty() ||
                        password.getText().toString().isEmpty() ||
                        radioGroupType.getCheckedRadioButtonId() == -1 ||
                        confirmPassword.getText().toString().isEmpty()){
                    nextBtn.setEnabled(false);
                } else {
                    nextBtn.setEnabled(true);
                }
            }
        }
    };

    private void registerUser() {
        final String frstName = firstName.getText().toString();
        final String  lstName = lastName.getText().toString();
        final String email = emailAddress.getText().toString();
        final String bday = birthDate.getText().toString();
        final String pass = password.getText().toString();
        final String conPassword = confirmPassword.getText().toString();

        if(conPassword.equals(pass)) {
            if (userType.equals("Customer")) {
                url = DBConfig.ServerURL + "customer/insert.php";
            } else {
                url = DBConfig.ServerURL + "vendor/insert.php";
            }

            Users users = new Users(email, pass, gender, bday, frstName, lstName, userType);
            server.sendRequest(url, users);
//            if(userType.equals("Customer")){
//                String url = "http://192.168.0.10/streetfood/customer/insert.php";
//                Customer customer = new Customer(email, pass, gender, bday, frstName, lstName);
//                customerServer.sendRequest(url, customer);
//            } else {
//                String url = "http://192.168.0.10/streetfood/vendor/insert.php";
//                Vendor vendor = new Vendor(email, pass, gender, bday, frstName, lstName);
//                /* TODO: VENDOR SERVER */
//            }
        } else {
            Toast.makeText(this, "Password and Confirm password does not match", Toast.LENGTH_LONG).show();
        }
    }

    public void showDatePickerDialog(View v){
        DialogFragment dialogFragment = new DatePickerFragment();
        dialogFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @SuppressLint("NewApi")
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
            stepTwo.setBackground(getResources().getDrawable(R.drawable.rounded_button_active));

        } else if (identifier.equals("buttons") && !status){
            backBtn.setText("Cancel");
            nextBtn.setText("Next");
            stepTwo.setBackground(getResources().getDrawable(R.drawable.rounded_button));
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
                        customProgressDialog.showProgress(RegisterActivity.this);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                currentStep = currentStep + 1;
                                visibleViews("layouts", false);
                                visibleViews("buttons", true);
                                nextBtn.setEnabled(false);
                                customProgressDialog.hideProgress();
                            }
                        }, 3000);
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
        int radioGroupId = group.getId();
        switch (radioGroupId) {
            case R.id.radioGroup:
                checkRadioGroup(group, checkedId, "gender");
                break;
            case R.id.radioGroupType:
                checkRadioGroup(group, checkedId, "userType");
                break;
        }
    }


    public void checkRadioGroup(RadioGroup group, int checkedId, String radioGroupName) {
        if (radioGroupName.equals("userType")) {
            int radioUserTypeId = group.getCheckedRadioButtonId();
            radioUserTypeButton = group.findViewById(radioUserTypeId);
            if(checkedId == -1) {
                nextBtn.setEnabled(false);
            }
            else {
                userType = radioUserTypeButton.getText().toString().trim();
                if(!emailAddress.getText().toString().isEmpty() ||
                        !password.getText().toString().isEmpty() ||
                        !confirmPassword.getText().toString().isEmpty()) {
                    nextBtn.setEnabled(true);
                }
            }
        } else {
            int radioGenderButtonId = group.getCheckedRadioButtonId();
            radioGenderButton = group.findViewById(radioGenderButtonId);
            if(checkedId == -1){
                nextBtn.setEnabled(false);
            }else {
                gender = radioGenderButton.getText().toString().trim();
                if(!firstName.getText().toString().isEmpty() ||
                        !lastName.getText().toString().isEmpty() ||
                        !birthDate.getText().toString().isEmpty()) {
                    nextBtn.setEnabled(true);
                }
            }
        }
    }
}

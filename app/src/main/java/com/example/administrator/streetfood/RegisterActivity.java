package com.example.administrator.streetfood;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    Button backBtn, nextBtn;
    LinearLayout frstStep, scndStep;
    EditText birthDate, firstName, lastName, emailAddress, password;
    RadioGroup radioGroup;
    RadioButton radioGenderButton;

    private int currentStep = 1;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

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
        nextBtn.setEnabled(false);

        firstName.addTextChangedListener(textWatcher);
        lastName.addTextChangedListener(textWatcher);
        emailAddress.addTextChangedListener(textWatcher);
        password.addTextChangedListener(textWatcher);
        birthDate.addTextChangedListener(textWatcher);

        firebaseAuth = FirebaseAuth.getInstance();
    }

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if(frstStep.getVisibility() == View.VISIBLE){
                if (firstName.getText().toString().isEmpty() ||
                        lastName.getText().toString().isEmpty()){
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
//
//    @Override
//    protected void onStar
//
//
// t() {
//        super.onStart();
//        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
//        if (firebaseUser != null) {
//            //Loggedin user
//        }
//    }

    private void sendVerificationEmail() {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        currentUser.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this,"A verification has been sent to your email", Toast.LENGTH_SHORT).show();
                            firebaseAuth.signOut();
                            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Email not sent", Toast.LENGTH_SHORT).show();
                            overridePendingTransition(0,0);
                            finish();
                            overridePendingTransition(0,0);
                            startActivity(getIntent());
                        }
                    }
                });
    }

    private void registerUser() {
        final String frstName = firstName.getText().toString().trim();
        final String lstName = lastName.getText().toString().trim();
        final String email = emailAddress.getText().toString().trim();
        final String bday = birthDate.getText().toString().trim();
        String pass = password.getText().toString().trim();
        int selectedGender = radioGroup.getCheckedRadioButtonId();
        radioGenderButton = this.findViewById(selectedGender);
        final String gender = radioGenderButton.getText().toString().trim();

        progressLoader().show();
        firebaseAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            //save data
                            Map<String, Object> user = new HashMap<>();
                            user.put("firstname", frstName);
                            user.put("lastname", lstName);
                            user.put("birthday", bday);
                            user.put("gender", gender);
                            user.put("email", email);

                            FirebaseFirestore.getInstance()
                                    .collection("users")
                                    .add(user)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            sendVerificationEmail();
//                                            Toast.makeText(RegisterActivity.this, "Registration successful. A verification has been sent to your email", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(RegisterActivity.this, "An error occured", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else {
                            Toast.makeText(RegisterActivity.this, "Error registering user", Toast.LENGTH_SHORT).show();
                        }
                        progressLoader().dismiss();
                    }
                });

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

    private ProgressDialog progressLoader() {
        final ProgressDialog pd = new ProgressDialog(RegisterActivity.this);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pd.setIndeterminate(true);

        return pd;
    }
}

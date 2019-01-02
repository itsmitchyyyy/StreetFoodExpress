package com.example.administrator.streetfood;

import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    Button backBtn, nextBtn;
    LinearLayout frstStep, scndStep;
    private int steps = 3;
    private int currentStep = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        backBtn = this.findViewById(R.id.button2);
        nextBtn = this.findViewById(R.id.button3);
        frstStep = this.findViewById(R.id.firstStep);
        scndStep = this.findViewById(R.id.secondStep);

        backBtn.setEnabled(false);

        backBtn.setOnClickListener(this);
        nextBtn.setOnClickListener(this);

    }

    public void showDatePickerDialog(View v){
        DialogFragment dialogFragment = new DatePickerFragment();
        dialogFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.button2:
                switch (currentStep){
                    case 2:
                        currentStep = currentStep - 1;
                        frstStep.setVisibility(View.VISIBLE);
                        scndStep.setVisibility(View.GONE);
                        backBtn.setEnabled(false);
                        break;
                }
                break;
            case R.id.button3:
                switch(currentStep){
                    case 1:
                        currentStep = currentStep + 1;
                        frstStep.setVisibility(View.GONE);
                        scndStep.setVisibility(View.VISIBLE);
                        backBtn.setEnabled(true);
                        break;
                    case 2:
                        backBtn.setEnabled(true);
                        break;
                }

        }
    }
}

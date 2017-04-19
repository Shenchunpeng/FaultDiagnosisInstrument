package com.example.mao.faultdiagnosisinstrument;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DiagnosticReport extends AppCompatActivity {
Button backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnostic_report);
        backButton=(Button)findViewById(R.id.back);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DiagnosticReport.this,SecondActivity.class);
                startActivity(intent);
            }
        });
    }
}

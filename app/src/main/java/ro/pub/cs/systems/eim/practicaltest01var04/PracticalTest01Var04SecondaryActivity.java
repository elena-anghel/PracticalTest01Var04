package ro.pub.cs.systems.eim.practicaltest01var04;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PracticalTest01Var04SecondaryActivity extends Activity {
    private EditText firstText, secondText;
    private Button okButton, cancelButton;
    private int totalClicks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var04_secondary);

        firstText = findViewById(R.id.firstField);
        secondText = findViewById(R.id.secondField);
        okButton = findViewById(R.id.okButton);
        cancelButton = findViewById(R.id.cancelButton);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("FIRST_TEXT")) {
            String firstText = intent.getStringExtra("FIRST_TEXT");
            this.firstText.setText(firstText);
        }
        if (intent != null && intent.hasExtra("SECOND_TEXT")) {
            String secondText = intent.getStringExtra("SECOND_TEXT");
            this.secondText.setText(secondText);
        }

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK, null);
                finish();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED, null);
                finish();
            }
        });
    }

}

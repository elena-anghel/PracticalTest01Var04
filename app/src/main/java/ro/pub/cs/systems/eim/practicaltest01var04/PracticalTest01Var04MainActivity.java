package ro.pub.cs.systems.eim.practicaltest01var04;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PracticalTest01Var04MainActivity extends AppCompatActivity {

    private static final int SECONDARY_ACTIVITY_REQUEST_CODE = 0;
    private Button displayButton = null;
    private Button navigateButton = null;
    private CheckBox checkBox1 = null;
    private CheckBox checkBox2 = null;
    private EditText editText1 = null;
    private EditText editText2 = null;
    private EditText viewInformation = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var04_main);

        displayButton = findViewById(R.id.displayInformation);
        navigateButton = findViewById(R.id.navigate_button);
        navigateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PracticalTest01Var04MainActivity.this, PracticalTest01Var04SecondaryActivity.class);
                intent.putExtra("FIRST_TEXT", editText1.getText().toString());
                intent.putExtra("SECOND_TEXT", editText2.getText().toString());
                startActivityForResult(intent, SECONDARY_ACTIVITY_REQUEST_CODE);
            }
        });
        displayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkBox1 = findViewById(R.id.checkBox1);
                editText1 = findViewById(R.id.editText1);
                checkBox2 = findViewById(R.id.checkBox2);
                editText2 = findViewById(R.id.editText2);
                viewInformation = findViewById(R.id.viewInformation);

                StringBuilder information = new StringBuilder();
                boolean allFieldsValid = true;

                if (checkBox1.isChecked()) {
                    if (editText1.getText().toString().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "The first text field is checked but not filled.", Toast.LENGTH_SHORT).show();
                        allFieldsValid = false;
                    } else {
                        information.append(editText1.getText().toString());
                    }
                }

                if (checkBox2.isChecked()) {
                    if (editText2.getText().toString().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "The second text field is checked but not filled.", Toast.LENGTH_SHORT).show();
                        allFieldsValid = false;
                    } else {
                        if (information.length() > 0) {
                            information.append(" ");
                        }
                        information.append(editText2.getText().toString());
                    }
                }

                if (allFieldsValid) {
                    viewInformation.setText(information.toString());
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SECONDARY_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Returned with OK", Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Returned with Cancel", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        EditText editText1 = findViewById(R.id.editText1);
        EditText editText2 = findViewById(R.id.editText2);

        outState.putString("editText1", editText1.getText().toString());
        outState.putString("editText2", editText2.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        EditText editText1 = findViewById(R.id.editText1);
        EditText editText2 = findViewById(R.id.editText2);

        editText1.setText(savedInstanceState.getString("editText1"));
        editText2.setText(savedInstanceState.getString("editText2"));
    }

}
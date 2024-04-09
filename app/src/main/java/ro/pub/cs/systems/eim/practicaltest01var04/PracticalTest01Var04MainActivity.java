package ro.pub.cs.systems.eim.practicaltest01var04;

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
}
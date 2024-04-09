package ro.pub.cs.systems.eim.practicaltest01var04;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Objects;

public class PracticalTest01Var04MainActivity extends AppCompatActivity {

    public static final String BROADCAST_RECEIVER_TAG = "[Message]";
    public static final String BROADCAST_RECEIVER_EXTRA =  "broadcast_receiver_extra";
    private static final int SECONDARY_ACTIVITY_REQUEST_CODE = 0;
    private Button displayButton = null;
    private Button navigateButton = null;
    private CheckBox checkBox1 = null;
    private CheckBox checkBox2 = null;
    private EditText editText1 = null;
    private EditText editText2 = null;
    private EditText viewInformation = null;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private final IntentFilter intentFilter = new IntentFilter();
    private final MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();

    private static class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(BROADCAST_RECEIVER_TAG, Objects.requireNonNull(intent.getStringExtra(BROADCAST_RECEIVER_EXTRA)));
        }
    }

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

                if (!editText1.getText().toString().isEmpty() && !editText2.getText().toString().isEmpty()) {
                    if (!editText1.getText().toString().isEmpty() && !editText2.getText().toString().isEmpty()) {
                        Intent serviceIntent = new Intent(PracticalTest01Var04MainActivity.this, PracticalTest01Var04Service.class);
                        startService(serviceIntent);
                    }
                }


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

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                if (result.getData() == null) {
                    return;
                }
                String text = editText1.getText().toString() + " " + editText2.getText().toString();
                Toast.makeText(this, "The activity returned with OK  " + text, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "The activity returned with CANCEL " , Toast.LENGTH_LONG).show();
            }
        });
        for (int index = 0; index < Constants.actionTypes.length; index++) {
            intentFilter.addAction(Constants.actionTypes[index]);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(messageBroadcastReceiver, intentFilter, Context.RECEIVER_EXPORTED);
        } else {
            registerReceiver(messageBroadcastReceiver, intentFilter);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(messageBroadcastReceiver);
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

    @Override
    protected void onDestroy() {
        Intent serviceIntent = new Intent(this, PracticalTest01Var04Service.class);
        stopService(serviceIntent);
        super.onDestroy();
    }

}
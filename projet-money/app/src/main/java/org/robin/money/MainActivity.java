package org.robin.money;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private Currency currency;
    private EditText numberEditText;
    private TextView resultTextView;
    private String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currency = getIntent().getParcelableExtra("currency");

        final ImageView flagImageView = findViewById(R.id.flagImageView);
        flagImageView.setImageResource(currency.flagId);

        numberEditText = findViewById(R.id.numberEditText);
        resultTextView = findViewById(R.id.resultTextView);
        final Button convertButton = findViewById(R.id.convertButton);

        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numberStr = numberEditText.getText().toString();

                try {
                    int value = (int) (Integer.parseInt(numberStr) * currency.rate);
                    resultTextView.setText(value + " " + currency.symbol);
                    result = numberStr + " EUR = " + value + " " + currency.symbol;

                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Only numbers can be converted", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (result.isEmpty()) {
            setResult(Activity.RESULT_CANCELED);
            return;
        }

        Intent intent = new Intent();
        intent.putExtra("result", result);
        setResult(Activity.RESULT_OK, intent);

        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");
    }
}

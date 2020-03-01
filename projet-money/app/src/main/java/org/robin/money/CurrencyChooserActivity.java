package org.robin.money;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CurrencyChooserActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_CODE_CONVERT_CURRENCY = 1;

    TextView lastConversionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_chooser);

        findViewById(R.id.usaButton).setOnClickListener(this);
        findViewById(R.id.ukButton).setOnClickListener(this);
        findViewById(R.id.japanButton).setOnClickListener(this);

        lastConversionTextView = findViewById(R.id.lastConversionTextView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CONVERT_CURRENCY) {
            handleConvertCurrencyResult(resultCode, data);
        }
    }

    private void handleConvertCurrencyResult(int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_CANCELED) {
            lastConversionTextView.setText("NONE");
            return;
        }

        String result = data.getStringExtra("result");
        lastConversionTextView.setText(result);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.usaButton:
                navigateToConvertActivity(new Currency(R.drawable.flag_usa, 0.90f, "USD"));
                break;
            case R.id.ukButton:
                navigateToConvertActivity(new Currency(R.drawable.flag_uk, 0.85f, "GBP"));
                break;
            case R.id.japanButton:
                navigateToConvertActivity(new Currency(R.drawable.flag_japan, 120.77f, "YEN"));
                break;
        }
    }

    private void navigateToConvertActivity(Currency currency) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("currency", currency);
        startActivityForResult(intent, REQUEST_CODE_CONVERT_CURRENCY);
    }

}

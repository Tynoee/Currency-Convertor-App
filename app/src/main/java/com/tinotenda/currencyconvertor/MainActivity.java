package com.tinotenda.currencyconvertor;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private EditText editTextAmount;
    private Spinner spinnerFrom, spinnerTo;
    private Button buttonConvert;
    private TextView textViewResult;

    private HashMap<String, Double> exchangeRates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        editTextAmount = findViewById(R.id.editTextAmount);
        spinnerFrom = findViewById(R.id.spinnerFromCurrency);
        spinnerTo = findViewById(R.id.spinnerToCurrency);
        buttonConvert = findViewById(R.id.buttonConvert);
        textViewResult = findViewById(R.id.textViewResult);

        // Setup exchange rates
        setupExchangeRates();

        // Setup spinners
        String[] currencies = {"USD", "EUR", "GBP", "ZWL", "JPY"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, currencies);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFrom.setAdapter(adapter);
        spinnerTo.setAdapter(adapter);

        // Button click listener
        buttonConvert.setOnClickListener(v -> {
            String fromCurrency = spinnerFrom.getSelectedItem().toString();
            String toCurrency = spinnerTo.getSelectedItem().toString();
            String amountStr = editTextAmount.getText().toString();

            if (!amountStr.isEmpty()) {
                double amount = Double.parseDouble(amountStr);
                double result = convertCurrency(fromCurrency, toCurrency, amount);
                textViewResult.setText(String.format("Converted Amount: %.2f %s", result, toCurrency));
            } else {
                textViewResult.setText("Please enter an amount.");
            }
        });
    }

    private void setupExchangeRates() {
        exchangeRates = new HashMap<>();
        exchangeRates.put("USD_EUR", 0.85);
        exchangeRates.put("USD_GBP", 0.75);
        exchangeRates.put("USD_ZWL", 361.90);
        exchangeRates.put("USD_JPY", 110.0);
        exchangeRates.put("EUR_USD", 1.18);
        // Add more rates as needed
    }

    private double convertCurrency(String from, String to, double amount) {
        String key = from + "_" + to;
        if (exchangeRates.containsKey(key)) {
            return amount * exchangeRates.get(key);
        } else {
            return 0.0; // Default if no rate is found
        }
    }
}

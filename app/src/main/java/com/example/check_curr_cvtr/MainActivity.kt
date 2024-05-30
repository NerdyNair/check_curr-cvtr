package com.example.check_curr_cvtr

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var etAmount: EditText
    private lateinit var etResult: EditText
    private lateinit var spFromCurrency: Spinner
    private lateinit var spToCurrency: Spinner
    private lateinit var btnConvert: Button
    private val currencyCodes = arrayOf("USD", "EUR", "GBP", "JPY", "AUD", "CAD", "CHF", "CNY", "HKD", "NZD", "INR")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        etAmount = findViewById(R.id.etAmount)
        etResult = findViewById(R.id.etResult)
        spFromCurrency = findViewById(R.id.spFromCurrency)
        spToCurrency = findViewById(R.id.spToCurrency)
        btnConvert = findViewById(R.id.btnConvert)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencyCodes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spFromCurrency.adapter = adapter
        spToCurrency.adapter = adapter

        // Set up button click listener
        btnConvert.setOnClickListener { convertCurrency() }
    }

    // Add your currency conversion logic here
    private fun convertCurrency() {
        val fromCurrency: String = spFromCurrency.selectedItem.toString()
        val toCurrency: String = spToCurrency.selectedItem.toString()
        val amount: Double = etAmount.text.toString().toDoubleOrNull() ?: run {
            etResult.setText("Invalid amount")
            return
        }

        // Hardcoded exchange rates for demonstration purposes
        val exchangeRates: MutableMap<String, Double> = HashMap()
        exchangeRates["USDEUR"] = 0.92
        exchangeRates["USDGBP"] = 0.81
        exchangeRates["USDJPY"] = 133.85
        exchangeRates["USDAUD"] = 1.4
        exchangeRates["USDCAD"] = 1.34
        exchangeRates["USDCHF"] = 0.93
        exchangeRates["USDCNY"] = 7.1
        exchangeRates["USDHKD"] = 7.85
        exchangeRates["USDNZD"] = 1.5
        exchangeRates["USDINR"] = 82.5
        // Add more exchange rates as needed
        exchangeRates["EURREVERTEDUSD"] = 1 / 0.92
        exchangeRates["GBPUSD"] = 1 / 0.81
        exchangeRates["JPYUSD"] = 1 / 133.85
        exchangeRates["AUDUSD"] = 1 / 1.4
        exchangeRates["CADUSD"] = 1 / 1.34
        exchangeRates["CHFUSD"] = 1 / 0.93
        exchangeRates["CNYUSD"] = 1 / 7.1
        exchangeRates["HKDUSD"] = 1 / 7.85
        exchangeRates["NZDUSD"] = 1 / 1.5
        exchangeRates["INRUSD"] = 1 / 82.5

        val conversionRate = getConversionRate(fromCurrency, toCurrency, exchangeRates)
        if (conversionRate != null) {
            val result = amount * conversionRate
            etResult.setText(String.format("%.2f", result))
        } else {
            etResult.setText("Exchange rate not available")
        }
    }

    // Function to get the conversion rate between two currencies
    private fun getConversionRate(
        fromCurrency: String,
        toCurrency: String,
        exchangeRates: MutableMap<String, Double>
    ): Double? {
        return when {
            fromCurrency == toCurrency -> 1.0
            exchangeRates.containsKey(fromCurrency + toCurrency) -> exchangeRates[fromCurrency + toCurrency]
            exchangeRates.containsKey(toCurrency + fromCurrency) -> 1 / exchangeRates[toCurrency + fromCurrency]!!
            else -> null
        }
    }
}

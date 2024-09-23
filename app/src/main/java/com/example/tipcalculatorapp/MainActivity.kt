package com.example.tipcalculatorapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.NumberFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var billAmountEditText: EditText
    private lateinit var tipPercentageSeekBar: SeekBar
    private lateinit var tipAmountTextView: TextView
    private lateinit var totalAmountTextView: TextView
    private lateinit var tipPercentageTextView: TextView
    private lateinit var splitCountSeekBar: SeekBar
    private lateinit var splitCountTextView: TextView
    private lateinit var totalPerPersonTextView: TextView

    private var billAmount = 0.0
    private var tipPercentage = 15
    private var splitCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        setupListeners()
        updateCalculations()
    }

    private fun initViews() {
        billAmountEditText = findViewById(R.id.billAmountEditText)
        tipPercentageSeekBar = findViewById(R.id.tipPercentageSeekBar)
        tipAmountTextView = findViewById(R.id.tipAmountTextView)
        totalAmountTextView = findViewById(R.id.totalAmountTextView)
        tipPercentageTextView = findViewById(R.id.tipPercentageTextView)
        splitCountSeekBar = findViewById(R.id.splitCountSeekBar)
        splitCountTextView = findViewById(R.id.splitCountTextView)
        totalPerPersonTextView = findViewById(R.id.totalPerPersonTextView)

        tipPercentageSeekBar.progress = tipPercentage
        splitCountSeekBar.progress = splitCount - 1
    }

    private fun setupListeners() {
        billAmountEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                billAmount = s.toString().toDoubleOrNull() ?: 0.0
                updateCalculations()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        tipPercentageSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                tipPercentage = progress
                updateCalculations()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        splitCountSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                splitCount = progress + 1
                updateCalculations()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun updateCalculations() {
        val tipAmount = billAmount * tipPercentage / 100
        val totalAmount = billAmount + tipAmount
        
        // Calculate the total per person by dividing the total amount by the split count
        val totalPerPerson = totalAmount / splitCount

        val currencyFormat = NumberFormat.getCurrencyInstance(Locale("fil", "PH"))
        tipAmountTextView.text = currencyFormat.format(tipAmount)
        totalAmountTextView.text = currencyFormat.format(totalAmount)
        tipPercentageTextView.text = getString(R.string.tip_percentage, tipPercentage)
        splitCountTextView.text = splitCount.toString()
        totalPerPersonTextView.text = currencyFormat.format(totalPerPerson)
    }
}
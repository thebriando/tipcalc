package edu.washington.briando.tipcalc

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.*
import android.view.*
import android.widget.*
import java.text.NumberFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    lateinit var tipAmt: EditText
    lateinit var tipChooser: Spinner
    lateinit var tipBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tipAmt = findViewById(R.id.tipAmt)
        tipChooser = findViewById(R.id.tipChooser)
        tipBtn = findViewById(R.id.tipBtn)

        // array of possible tips
        val tips: Array<String> = arrayOf("10%", "15%", "18%", "20%")

        // array of tips in double form
        val tipDecimals: DoubleArray = doubleArrayOf(0.10, 0.15, 0.18, 0.20)

        // add values to spinner
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tips)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        tipChooser.adapter = adapter

        // changes current tip based on which item is selected
        var tip = 0.0
        tipChooser.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                tip = tipDecimals[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        tipAmt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {
                var tipString = tipAmt.text
                tipAmt.removeTextChangedListener(this)

                // adds dollar sign in front
                if (!tipString.contains("$")) {
                    tipAmt.setText("$" + tipAmt.text)
                }
                // sets text field, enables button if there is valid text in the field
                tipAmt.setSelection(tipAmt.text.length)
                tipBtn.isEnabled = tipAmt.text.isNotEmpty()
                tipAmt.addTextChangedListener(this)
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

        tipBtn.setOnClickListener {
            var tipString = tipAmt.text
            val amount = tipString.substring(1, tipString.length).toDouble()
            // makes a toast of tip
            Toast.makeText(
                this@MainActivity,
                "Your tip is: " + convert(amount * tip) + " USD", Toast.LENGTH_LONG
            ).show()
        }
    }

    // converts double to USD currency format
    private fun convert(amount: Any): String {
        val usDollar = NumberFormat.getCurrencyInstance(Locale.US)
        return usDollar.format(amount.toString().toDouble())
    }
}
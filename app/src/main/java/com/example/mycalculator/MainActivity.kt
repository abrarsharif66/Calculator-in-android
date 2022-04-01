package com.example.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.ArithmeticException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    var dot:Boolean = false
    var num:Boolean = false

    fun onDigit(view: View){                                            // This function gets executed when a number is clicked
        if (tvInput.text == "0"){
            tvInput.text = ""
        }
        tvInput.append((view as Button).text)
        num= true
        dot = false
    }

    fun onBksp(view: View){                                                             // Deletes the previous elements
        if (tvInput.text.isNotBlank()){
            tvInput.text = tvInput.text.substring(0, (tvInput.text.length)-1)
        }
    }

    fun onClear(view: View){                                          // Clears the screen
        tvInput.text= ""
        num=false
        dot=false
    }

    fun decimalPoint(view: View){                                   // Checks whether the decimal is present or not
        if (num && !dot){
            tvInput.append(".")
            dot= true
            num= false
        }
    }

    private fun reducezeroes(result: String) : String{                      // This reduces the unnecessary decimals
        var finalvalue = result

        if (result.contains(".0")){
            finalvalue = result.substring(0, result.length-2)
        }
        return finalvalue
    }

    fun onOperator(view: View){                                     // This function gets executed when operators are clicked
        if(num && !isoperatorthere(tvInput.text.toString())){
            tvInput.append((view as Button).text)
            num=false
            dot=false
        }
    }


    private fun isoperatorthere(value: String) : Boolean{       // This shit checks whether the no. has a - sign before it
        return if (value.startsWith("-")){
            false
        }

        else {
            value.contains("+") || value.contains("-") || value.contains("*") || value.contains("÷") || value.contains("%")
        }
    }

    fun onEqual(view: View) {                       // This is where the calculator does its shit
        if (num) {

            var value = tvInput.text.toString()
            var prefixcheckker = ""

            try {                                      // In try block cuz some people do division by zero....

                if (value.startsWith("-")) {
                    prefixcheckker = "-"
                    value = value.substring(1)
                }

                if (value.contains("-")) {
                    val splitvalue = value.split("-")
                    var num1 = splitvalue[0]
                    var num2 = splitvalue[1]

                    if (!prefixcheckker.isEmpty()) {
                        num1 = prefixcheckker + num1
                    }

                    tvInput.text = reducezeroes((num1.toDouble() - num2.toDouble()).toString())
                }

                else if (value.contains("+")){
                    val splitvalue = value.split("+")
                    var num1 = splitvalue[0]
                    var num2 = splitvalue[1]

                    if (!prefixcheckker.isEmpty()) {
                        num1 = prefixcheckker + num1
                        tvInput.text = reducezeroes((num1.toDouble() + num2.toDouble()).toString())
                    }

                    else{
                        tvInput.text = reducezeroes((num1.toDouble() + num2.toDouble()).toString())
                    }
                }

                else if (value.contains("*")){
                    val splitvalue = value.split("*")
                    var num1 = splitvalue[0]
                    var num2 = splitvalue[1]

                    if (!prefixcheckker.isEmpty()) {
                        num1 = prefixcheckker + num1
                        tvInput.text = reducezeroes((num1.toDouble() * num2.toDouble()).toString())
                    }

                    else{
                        tvInput.text = reducezeroes((num1.toDouble() * num2.toDouble()).toString())
                    }
                }

                else if (value.contains("÷")){
                    val splitvalue = value.split("÷")
                    var num1 = splitvalue[0]
                    var num2 = splitvalue[1]

                    if (!prefixcheckker.isEmpty()) {
                        num1 = prefixcheckker + num1
                        tvInput.text = (num1.toDouble() / num2.toDouble()).toString()
                    }

                    else{
                        tvInput.text = (num1.toDouble() / num2.toDouble()).toString()
                    }
                }

                else if (value.contains("%")){
                    val splitvalue = value.split("%")
                    var num1 = splitvalue[0]
                    val num2 = splitvalue[1]

                    tvInput.text = ((num1.toDouble() * num2.toDouble())/100).toString()
                }
            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }

        }
    }
}
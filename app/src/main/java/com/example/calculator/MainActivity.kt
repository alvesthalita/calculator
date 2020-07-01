package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    var lastNumeric = false
    var lastDot = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /**
     * Print the press number on the screen
     */
    fun onDigit(view: View){
        inputNumbers.append((view as Button).text)
        lastNumeric = true
    }

    /**
     * Clean all the numbers/results from the screen
     */
    fun onClear(view: View){
        inputNumbers.text = ""
        lastNumeric = false
        lastDot = false
    }

    /**
     * Add a dot after a number
     */
    fun onDecimalPoint(view: View){
        if(lastNumeric && !lastDot){
            inputNumbers.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    /**
     * Put the operator between the numbers
     */
    fun onOperator(view: View){
        if(lastNumeric && !addOperator(inputNumbers.text.toString())){
            inputNumbers.append((view as Button).text)
            lastNumeric = false
            lastDot = false
        }
    }

    /**
     * Check if the start value is a - operator
     */
    private fun addOperator(value: String) : Boolean{
        return  if(value.startsWith("-")){
            false
        }else{
            value.contains("/") || value.contains("*") || value.contains("+") || value.contains("-")
        }
    }

    /**
     * Do the calculation and present the result
     */
    fun onEqual(view: View){
        if(lastNumeric){
            var value = inputNumbers.text.toString()
            var prefix = ""
            try{
                if(value.startsWith("-")){
                    prefix = "-"
                    value = value.substring(1)
                }

                if(value.contains("-")){ //SUBTRACTION
                    val splitvalue = value.split("-")
                    var firstNumber = splitvalue[0]
                    val secondNumber = splitvalue[1]

                    if(!prefix.isEmpty()){
                        firstNumber = prefix + firstNumber
                    }

                    inputNumbers.text = removeZeros((firstNumber.toDouble() - secondNumber.toDouble()).toString())

                }else if(value.contains("+")){ //PLUS
                    val splitvalue = value.split("+")
                    var firstNumber = splitvalue[0]
                    val secondNumber = splitvalue[1]

                    if(!prefix.isEmpty()){
                        firstNumber = prefix + firstNumber
                    }

                    inputNumbers.text = removeZeros((firstNumber.toDouble() + secondNumber.toDouble()).toString())

                }else if(value.contains("/")){ //DIVIDE
                    val splitvalue = value.split("/")
                    var firstNumber = splitvalue[0]
                    val secondNumber = splitvalue[1]

                    if(!prefix.isEmpty()){
                        firstNumber = prefix + firstNumber
                    }

                    inputNumbers.text = removeZeros((firstNumber.toDouble() / secondNumber.toDouble()).toString())

                }else if(value.contains("*")){ //MULTIPLICATION
                    val splitvalue = value.split("*")
                    var firstNumber = splitvalue[0]
                    val secondNumber = splitvalue[1]

                    if(!prefix.isEmpty()){
                        firstNumber = prefix + firstNumber
                    }

                    inputNumbers.text = removeZeros((firstNumber.toDouble() * secondNumber.toDouble()).toString())
                }
            }catch (e: ArithmeticException){
                e.printStackTrace()
            }
        }
    }

    private fun removeZeros(result: String) : String {
        var value = result
        if(result.contains(".0")){
            value = result.substring(0, result.length - 2)
        }

        return value;
    }
}
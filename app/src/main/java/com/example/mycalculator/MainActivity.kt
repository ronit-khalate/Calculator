package com.example.mycalculator

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import kotlin.concurrent.timerTask
import kotlin.math.min
import kotlin.ArithmeticException as ArithmeticException

class MainActivity : AppCompatActivity() {

    //- this flag is used for removing default zero from result textview
    //- and appending clicked value

    var enterdDigit:String=""

    // Flags
    var startOverFlag=true
    var lastNumeric :Boolean=true
    var lastDot :Boolean = false
    var lastOperand :Boolean=false

    private var tvResult :TextView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        tvResult= findViewById(R.id.tvResult)
    }

    fun onDigit(view : View)
    {

        changeOperandBtnColor((view as Button))
        if(startOverFlag)
        {
            tvResult?.text=""
            startOverFlag=false

        }
        //- Type casting view as Button
        //? view parameter is out button clicked
        lastNumeric=true
        lastDot=false
        lastOperand=false


        val firstText = enterdDigit
        val secondText = view .text

        enterdDigit = StringBuilder().append(firstText).append(secondText).toString()
        tvResult?.append((view as Button).text)

    }


    fun onClear(view :View)
    {
        changeOperandBtnColor((view as Button))
        startOverFlag=true
        lastDot=false
        lastNumeric=true
        lastOperand=false
        tvResult?.text="0"
    }

    fun onDecimalPoint(view: View)
    {
            if(lastNumeric && ! lastDot && !lastOperand)
            {
                tvResult?.append(".")
                lastNumeric=false
                lastOperand=false
                lastDot=true
            }
    }


    fun onOperator(view: View)
    {

        val firstText = tvResult?.text
        val secondText = (view as Button).text
        changeOperandBtnColor((view as Button))

        if (!lastOperand)
        {

            tvResult?.text?.let {

                if (lastNumeric && !isOperatorAdded(it.toString()))
                {

                    enterdDigit=StringBuilder().append(firstText).append(secondText).toString()
//                tvResult?.append((view as Button).text)

                    lastNumeric = false
                    lastDot=false
                    startOverFlag=true
                    lastOperand=true
                }
            }
        }
        else{

            firstText?.drop(firstText.lastIndex)
            enterdDigit=StringBuilder().append(firstText).append(view.text).toString()
            lastOperand=true

        }


    }

    private  fun isOperatorAdded(value :String) :Boolean{



        return if (value.startsWith("-")){
            false
        }else{

            value.contains("/")
                    || value.contains("*")
                    || value.contains("+")
                    || value.contains("-")
        }

    }


    fun onEqual(view: View)
    {
        var splitValue :List<String>
        var one :Double?
        var two :Double?

        if(lastNumeric)
        {
            var tvValue = enterdDigit

            try {

                if (tvValue.contains("/"))
                {
                    splitValue = tvValue.split("/")
                    one= splitValue[0].toDouble()
                    two = splitValue[1] .toDouble()

                    if(two==0.0)
                    {
                        startOverFlag=true
                    }

                    tvResult?.text=(one / two).toString()


                }
                else if(tvValue.contains("*"))
                {

                    splitValue = tvValue.split("*")
                    one= splitValue[0].toDouble()
                    two = splitValue[1] .toDouble()

                    if(two==0.0)
                    {
                        startOverFlag=true
                    }



                    tvResult?.text=(one * two).toString()

                }
                else if (tvValue.contains("-"))
                {
                    splitValue = tvValue.split("-")
                    one= splitValue[0].toDouble()
                    two = splitValue[1] .toDouble()

                    tvResult?.text=(one - two).toString()
                }
                else if (tvValue.contains("%"))
                {
                    splitValue = tvValue.split("-")
                    one= splitValue[0].toDouble()
                    two = splitValue[1] .toDouble()

                    tvResult?.text=(one % two).toString()
                }
                else {

                    splitValue = tvValue.split("+")
                    one= splitValue[0].toDouble()
                    two = splitValue[1] .toDouble()

                    tvResult?.text=(one + two).toString()

                }
            }catch (e : ArithmeticException)
            {
                e.printStackTrace()
            }
        }

        enterdDigit=""
    }

    fun onDivideBy100(view: View)
    {
        val enteredValue= tvResult?.text.toString().toDouble()

        tvResult?.text =(enteredValue/100).toString()
        startOverFlag= true
        lastDot=false
    }

    fun onDelete(view: View)
    {
        var text = tvResult?.text.toString()

        if (text != null) {
            text=text.dropLast(1)
        }
        if(text.isEmpty())
        {
            text="0"
            startOverFlag=true
        }
        if (text[text.lastIndex]=='.')
        {
            text=text.dropLast(1)
        }

        tvResult?.text=text
    }

    fun changeOperandBtnColor(btn :Button)
    {



        val divideBtn = findViewById<Button>(R.id.btnDivide)
        val plusBtn = findViewById<Button>(R.id.btnPlus)
        val minusBtn = findViewById<Button>(R.id.btnSubstract)
        val multiplyBtn = findViewById<Button>(R.id.btnMultiply)

        if(btn!= divideBtn){

            divideBtn.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.btnOrange))
            divideBtn.setTextColor(resources.getColor(R.color.white))
        }
        else{

            divideBtn.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.white))
            divideBtn.setTextColor(resources.getColor(R.color.btnOrange))
        }

        if(btn!=plusBtn){

            plusBtn.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.btnOrange))
            plusBtn.setTextColor(resources.getColor(R.color.white))

        }
        else{

            plusBtn.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.white))
            plusBtn.setTextColor(resources.getColor(R.color.btnOrange))
        }

        if(btn != multiplyBtn){

            multiplyBtn.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.btnOrange))
            multiplyBtn.setTextColor(resources.getColor(R.color.white))

        }
        else{

            multiplyBtn.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.white))
            multiplyBtn.setTextColor(resources.getColor(R.color.btnOrange))
        }

        if(btn != minusBtn){

            minusBtn.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.btnOrange))
            minusBtn.setTextColor(resources.getColor(R.color.white))

        }
        else{

            minusBtn.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.white))
            minusBtn.setTextColor(resources.getColor(R.color.btnOrange))
        }

    }




}

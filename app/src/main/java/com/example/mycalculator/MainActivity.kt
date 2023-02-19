package com.example.mycalculator

import android.content.res.ColorStateList

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat

import kotlin.ArithmeticException as ArithmeticException

class MainActivity : AppCompatActivity() {

    //- this flag is used for removing default zero from result textview
    //- and appending clicked value

    private var enterdDigit:String=""

    // Flags
    private var startOverFlag=true
    private var lastNumeric :Boolean=true
    private var lastDot :Boolean = false
    private var lastOperand :Boolean=false
    private var startsWithMinus: Boolean =false

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
        tvResult?.append(view.text)

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
                val firstText = enterdDigit
                val secondText ="."

                enterdDigit = StringBuilder().append(firstText).append(secondText).toString()
                lastNumeric=false
                lastOperand=false
                lastDot=true
            }
    }


    fun onOperator(view: View)
    {

        val firstText = tvResult?.text
        val secondText = (view as Button).text
        changeOperandBtnColor(view)

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
            lastDot=false
            lastNumeric=false

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
        val splitValue :List<String>
        val one :Double?
        val two :Double?

        if(lastNumeric)
        {
            var tvValue = enterdDigit

            if (tvValue.startsWith("-"))
            {

                startsWithMinus=true
                tvValue=tvValue.drop(1).trim()


            }
            else{
                startsWithMinus=false
            }



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

                    var result =(one / two).toString().drop(1).trim()
                    if(result[result.lastIndex]=='.')
                    {
                        tvResult?.text=(one / two).toInt().toString()
                    }
                    else
                    {
                        tvResult?.text=(one / two).toString()
                    }





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



                    var result =(one * two).toString().drop(1).trim()
                    if(result[result.lastIndex]=='.')
                    {
                        tvResult?.text=(one * two).toInt().toString()
                    }
                    else
                    {
                        tvResult?.text=(one * two).toString()
                    }


                }
                else if (tvValue.contains("-"))
                {
                        if(!startsWithMinus)
                        {
                            splitValue = tvValue.split("-")
                            one= splitValue[0].toDouble()
                            two = splitValue[1] .toDouble()
                        }
                        else{

                            splitValue = tvValue.split("-")
                            one= StringBuilder().append("-").append(splitValue[0]).toString().toDouble()
                            two = splitValue[1] .toDouble()
                        }

                    var result =(one - two).toString().drop(1)
                    if(result[result.lastIndex]=='.')
                    {
                        tvResult?.text=(one - two).toInt().toString()
                    }
                    else
                    {
                        tvResult?.text=(one - two).toString()
                    }

                }
                else if (tvValue.contains("%"))
                {
                    splitValue = tvValue.split("-")
                    one= splitValue[0].toDouble()
                    two = splitValue[1] .toDouble()

                    tvResult?.text=(one % two).toString()
                }
                else {

                    if(!startsWithMinus)
                    {
                        splitValue = tvValue.split("+")
                        one= splitValue[0].toDouble()
                        two = splitValue[1] .toDouble()
                    }
                    else{

                        splitValue = tvValue.split("+")
                        one= StringBuilder().append("-").append(splitValue[0]).toString().toDouble()
                        two = splitValue[1] .toDouble()
                    }

                    var result =(one + two).toString().drop(1).trim()
                    if(result[result.lastIndex]=='.')
                    {
                        tvResult?.text=(one + two).toInt().toString()
                    }
                    else
                    {
                        tvResult?.text=(one + two).toString()
                    }


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

        text=text.dropLast(1)
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

    private fun changeOperandBtnColor(btn :Button)
    {



        val divideBtn = findViewById<Button>(R.id.btnDivide)
        val plusBtn = findViewById<Button>(R.id.btnPlus)
        val minusBtn = findViewById<Button>(R.id.btnSubstract)
        val multiplyBtn = findViewById<Button>(R.id.btnMultiply)

        //? use ContextCompat.getColor() instead of resource.getColor()
        //! resource.getColor() is deprecated

        if(btn!= divideBtn){

            divideBtn.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this,R.color.btnOrange))
            divideBtn.setTextColor(ContextCompat.getColor(this,R.color.white))
        }
        else{

            divideBtn.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this,R.color.white))
            divideBtn.setTextColor(ContextCompat.getColor(this,R.color.btnOrange))
        }

        if(btn!=plusBtn){

            plusBtn.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this,R.color.btnOrange))
            plusBtn.setTextColor(ContextCompat.getColor(this,R.color.white))

        }
        else{

            plusBtn.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this,R.color.white))
            plusBtn.setTextColor(ContextCompat.getColor(this,R.color.btnOrange))
        }

        if(btn != multiplyBtn){

            multiplyBtn.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this,R.color.btnOrange))
            multiplyBtn.setTextColor(ContextCompat.getColor(this,R.color.white))

        }
        else{

            multiplyBtn.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this,R.color.white))
            multiplyBtn.setTextColor(ContextCompat.getColor(this,R.color.btnOrange))
        }

        if(btn != minusBtn){

            minusBtn.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this,R.color.btnOrange))
            minusBtn.setTextColor(ContextCompat.getColor(this,R.color.white))

        }
        else{

            minusBtn.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this,R.color.white))
            minusBtn.setTextColor(ContextCompat.getColor(this,R.color.btnOrange))
        }

    }




}

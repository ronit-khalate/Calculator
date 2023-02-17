package com.example.mycalculator

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import kotlin.concurrent.timerTask
import kotlin.ArithmeticException as ArithmeticException

class MainActivity : AppCompatActivity() {

    //- this flag is used for removing default zero from result textview
    //- and appending clicked value
    var startOverFlag=true

    var lastNumeric :Boolean=true
    var lastDot :Boolean = false

    private var tvResult :TextView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvResult= findViewById(R.id.tvResult)
    }

    fun onDigit(view : View)
    {

        if(startOverFlag)
        {
            tvResult?.text=""
            startOverFlag=false

        }
        //- Type casting view as Button
        //? view parameter is out button clicked
        lastNumeric=true
        lastDot=false
        tvResult?.append((view as Button).text)

    }


    fun onClear(view :View)
    {
        startOverFlag=true
        lastDot=false
        lastNumeric=true
        tvResult?.text="0"
    }

    fun onDecimalPoint(view: View)
    {
            if(lastNumeric && ! lastDot)
            {
                tvResult?.append(".")
                lastNumeric=false
                lastDot=true
            }
    }


    fun onOperator(view: View)
    {

        tvResult?.text?.let {

            if (lastNumeric && !isOperatorAdded(it.toString()))
            {
                tvResult?.append((view as Button).text)

                lastNumeric = false
                lastDot=false
            }
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
            var tvValue = tvResult?.text.toString()

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
    }

    fun onDivideBy100(view: View)
    {
        val enteredValue= tvResult?.text.toString().toDouble()

        tvResult?.text =(enteredValue/100).toString()
        startOverFlag= true
        lastDot=false
    }




}

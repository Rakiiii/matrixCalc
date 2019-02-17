package com.example.smurf.mtarixcalc

import android.widget.EditText

class polinom( _size : Int)
{
    private val size : Int = _size

    private val cofs : Array<complexNumber> = Array(_size , { s -> complexNumber() })

    private var roots : Array<complexNumber> = Array(size , { i-> complexNumber() } )

    //количество уже найденных корней
    private var number : Int = 0

    constructor(pol : EditText):this(pol.text.toString().countWords())
    {
        var cofLine = pol.text.toString()
        for(i in  0 until size)
        {
            cofs[i] = cofLine.substringBefore(' ').toComplex()
            cofLine = cofLine.substringAfter(' ')
        }
    }

    constructor( _line : String):this(_line.countWords())
    {
        var line = _line
        for(i in 0 until size)
        {
            cofs[i] = line.substringBefore(' ').toComplex()
            line = line.substringAfter(' ')
        }
    }
    constructor(matrix : matrixClass):this(matrix.width + 1)
    {

    }


    //поиск корня перебором
    fun result()
    {
        //перебор всех числителей вещественной части
        for ( i in -100..100)
        {
            //перебов всех знаменателей вещественной части
            for(k in 0..100)
            {
                //перебор все числитетелей мнимой части
                for (j in -100..100)
                {
                    //перебор всех знаменателей мнимой части
                    for(l in  0..100)
                    {
                        if (rightPol(complexNumber( fraction(i,k), fraction(j,l) ) ) && number != size - 1)
                        roots[number++] = complexNumber( fraction(i,k) , fraction(j,l) )

                    }
                }
            }
        }
    }

    //проверка корня
    fun rightPol( test : complexNumber) : Boolean
    {
        var res = complexNumber()
        for ( i in 0..size)
        {
            res+=this.cofs[i]*test.pow(i)
        }
        if(res == complexNumber())return true
        else return false
    }
}
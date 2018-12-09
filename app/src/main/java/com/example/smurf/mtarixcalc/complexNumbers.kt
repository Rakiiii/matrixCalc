package com.example.smurf.mtarixcalc

class complexNumber(private var re : Double ,private  var im : Double)
{
    
    operator  fun plus (secondNumber : complexNumber ):complexNumber
    {
        val newNum = complexNumber(this.re + secondNumber.re , this.im + secondNumber.im)
        return newNum
    }

    operator fun times(secNumber : complexNumber) : complexNumber
    {
        val newNum = complexNumber( (this.re * secNumber.re - this.im*secNumber.im) , (this.re * secNumber.im - secNumber.re * this.im))
        return newNum
    }

    operator fun minus(secNumber: complexNumber) :complexNumber
    {
        val newNum = complexNumber( this.re - secNumber.re , this.im - secNumber.im)
        return newNum
    }

    operator  fun div( secNumber: complexNumber) : complexNumber
    {
        val newNum =  complexNumber( ( (this.re * secNumber.re + this.im*secNumber.im) / (secNumber.re*secNumber.re + secNumber.im*secNumber.im) ) ,
            ( (secNumber.re*this.im - this.re*secNumber.im) / (secNumber.re*secNumber.re + secNumber.im*secNumber.im) ) )
        return newNum
    }

    fun pow( Pow : Int) : complexNumber
    {
        var newNum : complexNumber = this
        for( i in 1..Pow)
        {
            newNum *= this;
        }
        return newNum
    }

}

public fun countLines(text : String) : Int
{
    var counter = 0
    for(i in text)if(i == '\n')counter++
    return counter + 1
}

public fun countWords(text : String): Int
{
    var counter = 0
    for( i in text)if( i == ' ')counter ++
    return counter + 1
}
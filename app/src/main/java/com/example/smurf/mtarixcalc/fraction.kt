package com.example.smurf.mtarixcalc

import kotlin.math.absoluteValue

class fraction (private var upper : Int = 0 , private var lower : Int = 1)
{
    operator fun plus(right : Any?) : fraction
    {
        when(right)
        {
            is fraction ->
            {
                if(right.upper == 0)return this
                if(this.upper == 0)return right
                val res = fraction ( this.upper*right.lower + this.lower*right.upper , this.lower*right.lower)
                if(res.lower < 0)
                {
                    res.upper *= -1
                    res.lower *= -1
                }
                return res.cut()
            }
            is Int ->
            {
                return fraction(this.upper+right*this.lower , this.lower)
            }
            else -> throw Exception("Unnown type for plus opartion")
        }
    }

    operator fun minus(right : Any?) : fraction
    {
        when(right)
        {
            is fraction->
            {
                if (right.upper == 0) return this
                if (this.upper == 0)
                    return right
                val res = fraction(this.upper * right.lower - this.lower * right.upper, this.lower * right.lower)
                if (res.lower < 0) {
                    res.upper *= -1
                    res.lower *= -1
                }
                return res.cut()
            }
            is Int -> return fraction(this.upper-right*this.lower , this.lower)
            else -> throw Exception("Unknown type")
        }
    }

    operator fun div(right: Any?) : fraction
    {
        when(right)
        {
            is fraction->
            {

                if (this.upper == 0) return fraction()
                if (right.upper == 0) throw Exception("Can't divide by zero")
                val res = fraction(this.upper * right.lower, this.lower * right.upper)
                if (res.lower < 0) {
                    res.upper *= -1
                    res.lower *= -1
                }
                return res.cut()
            }
            is Int -> return fraction(this.upper , this.lower*right)
            else -> throw Exception("Unknown type")
        }
    }

    operator fun times(right: Any?) : fraction {
        when (right)
        {
            is fraction ->
            {


                if (this.upper == 0 || right.upper == 0) return fraction()
                val res = fraction(this.upper * right.upper, this.lower * right.lower)
                if (res.lower < 0) {
                    res.upper *= -1
                    res.lower *= -1
                }
                return res.cut()
            }
            is Int -> return fraction(this.upper*right , this.lower)
            else -> throw Exception("Unknown type")
        }
    }

    override operator fun equals(other : Any?) : Boolean
    {
        when(other)
        {
            is fraction ->
            {
                if(this.upper == 0 && other.upper == 0)return true
                else return (this.upper == other.upper && this.lower == other.lower)
            }
            is Int ->
            {
                if ( (this.upper % this.lower) == 0) return ( (this.upper/this.lower) == other )
                else return false
            }
            is Double ->
            {
                val check : Double = (this.upper.toDouble() /  this.lower.toDouble() )
                return (check == other)
            }
            is Float ->
            {
                val check : Float = ( this.upper.toFloat() / this.lower.toFloat() )
                return (check == other)
            }
            else -> return false
        }
    }

    override fun toString(): String
    {
        if (lower == 1)return upper.toString()
        else
        {
            if(upper >= 0) return( "(" + upper.toString() + "/" + lower.toString() + ")" )
            else return("-" + "(" + (-1*upper).toString() + "/" + lower.toString() + ")" )
        }
    }

    operator fun compareTo(other : fraction) : Int
    {
        if( this == other )return 0
        else
        {
            when
            {
                ( other == fraction() )->if(this.upper > 0)return 1 else return -1
                (this.upper >= other.upper && this.lower <= other.lower)->return 1
                (this.upper <= other.upper && this.lower >= other.lower)->return -1
                else -> if ( ( this.upper.toDouble() / this.lower.toDouble() ) > (other.upper.toDouble() / other.lower.toDouble() ) ) return 1
                    else return -1
            }
        }
    }


    private fun cut() : fraction
    {
        val _gcd = gcd(this.upper.absoluteValue , this.lower.absoluteValue)
        this.upper /= _gcd
        this.lower /= _gcd
        return this
    }



}

fun gcd( a : Int , b : Int) : Int
{
    if(b == 0)return a
    else return gcd( b ,a % b )
}

fun String.toFraction():fraction
{
    when
    {
        (this.contains('.'))->return fraction(this.filterNot{s -> (s == '.' )}.filterNot { s -> (s ==')' && (s == '(')) }.toInt() ,
            10.pow( this.substringAfter('.').filterNot { s -> (s == ')') }.length))
        (this.contains('/')) -> return fraction(this.substringBefore('/').filterNot { s -> (s == '(') }.toInt(),
            this.substringAfter('/').filterNot { s -> (s == ')') }.toInt())
        else -> return fraction(this.filterNot { s -> (s == '(' && s == ')' ) }.toInt(), 1)
    }
}

fun Int.pow(x : Int) : Int
{
    if (x == 0)return 1
    var res : Int = 1
    for( i in 1..x)
        res *=this
    return res
}
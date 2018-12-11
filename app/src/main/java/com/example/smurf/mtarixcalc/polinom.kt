package com.example.smurf.mtarixcalc

class polinom(private var cofs : Array<complexNumber> , private var size : Int)
{
    private var roots : Array<complexNumber> = Array(size , { i-> complexNumber( 0.0,0.0) } )

    private var number : Int = 0 ;

    public fun result()
    {
        for ( i in -100..100)
        {
            for(j in -100..100)
            {
                if(rightPol(complexNumber( i.toDouble(),j.toDouble() ) ) && number != size-1)
                {
                    roots[number++] = complexNumber( i.toDouble(),j.toDouble() )
                }
            }
        }
    }

    fun rightPol( test : complexNumber) : Boolean
    {
        var res = complexNumber(0.0,0.0)
        for ( i in 0..size)
        {
            res+=this.cofs[i]*test.pow(i)
        }
        if(res == complexNumber(0.0,0.0))return true
        else return false
    }
}
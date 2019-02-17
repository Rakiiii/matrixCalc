package com.example.smurf.mtarixcalc



class complexNumber(private var re : fraction = fraction(), private  var im : fraction = fraction())
{

    operator  fun plus (secNumber : Any? ):complexNumber
    {
        when(secNumber)
        {
            is complexNumber ->
            {
                return complexNumber(this.re + secNumber.re , this.im + secNumber.im)
            }
            is Int ->
            {
                return complexNumber(this.re +secNumber , this.im)
            }
            is fraction ->
            {
                return complexNumber(this.re + secNumber , this.im)
            }
            else -> throw Exception("Unknown type")
        }
    }


    operator fun times(secNumber : Any?): complexNumber
    {
        when(secNumber)
        {
            is complexNumber->
            {
                val newNum = complexNumber( (this.re * secNumber.re - this.im*secNumber.im) , (this.re * secNumber.im - secNumber.re * this.im))
                return newNum
            }
            is Int ->
            {
                return complexNumber(this.re * secNumber , this.im*secNumber)
            }
            is fraction ->
            {
                return complexNumber(this.re * secNumber , this.im*secNumber)
            }
            else -> throw Exception("Unknown type")
        }
    }

    operator fun minus(secNumber: Any?) :complexNumber
    {
        when(secNumber)
        {
            is complexNumber->
            {
                return  complexNumber( this.re - secNumber.re , this.im - secNumber.im)

            }
            is Int ->
            {
                return complexNumber(this.re - secNumber , this.im)
            }
            is fraction ->
            {
                return complexNumber(this.re - secNumber , this.im)
            }
            else -> throw Exception("Unknown type")
        }
    }

    operator  fun div( secNumber: Any?) : complexNumber
    {
        when(secNumber)
        {
            is complexNumber ->
            {
                return complexNumber( ( (this.re * secNumber.re + this.im*secNumber.im) / (secNumber.re*secNumber.re + secNumber.im*secNumber.im) ) ,
                    ( (secNumber.re*this.im - this.re*secNumber.im) / (secNumber.re*secNumber.re + secNumber.im*secNumber.im) ) )
            }
            is Int ->
            {
                return complexNumber(this.re / secNumber , this.im / secNumber)
            }
            is fraction ->
            {
                return complexNumber(this.re / secNumber , this.im / secNumber)
            }
            else -> throw Exception("Unknown type")
        }

    }

    fun pow( Pow : Int) : complexNumber
    {
        var newNum : complexNumber = this
        if( Pow == 0)return complexNumber(fraction(1))
        for( i in 1..Pow)
        {
            newNum *= this
        }
        return newNum
    }

    override operator fun equals(other : Any?) : Boolean
    {
        when(other) {
            is complexNumber -> return ((this.re == other.re) && (this.im == other.im))
            is Int -> return (this.re == other)
            is Double -> return(this.re == other)
            is Float -> return(this.re == other)
            is String -> return(this == other.toComplex())
            else -> return false
        }
    }


    override fun toString(): String
    {
        when
        {
            //вещественная часть 0 возвращаем только действительную
            im == fraction() -> return re.toString()
            //действительная часть 0 возарвщвем только мнимую
            re == fraction() -> return im.toString() + 'i'

            im < fraction() -> return (re.toString() + im.toString() + 'i')

            else -> return (re.toString() + '+' + im.toString() + 'i')

        }
    }

}

fun translateToComplex(line : String , signIm : Char = '+' , signRe : Char = '+'  ) : complexNumber
{
    if((signIm != '+' && signIm != '-') || (signRe != '+' && signRe != '-'))throw Exception("unnown char in complex number translation")
    //разбиваем текущий столбец на отдельные цифры
    var subIm = fraction()
    if(line.substringAfter(signIm).filterNot { s->(s == 'i') }.isBlank())subIm = fraction(1,1)
    else subIm = line.substringAfter(signIm).filterNot { s -> (s == 'i') }.toFraction()
    var subRe = line.substringBefore(signIm).toFraction()

    if(signRe == '-')subRe*=fraction(-1,1)
    if(signIm == '-')subIm*= fraction(-1,1)
    return complexNumber(subRe , subIm)
}

fun String.toComplex() : complexNumber
{
    var line = this
    var signRe = '+'
    if(line[0] == '-')
    {
        signRe = '-'
        line = line.substringAfter('-')
    }
    when
    {
        line.contains('+') && line.contains('i', true) ->
        {
            return translateToComplex(line , '+' , signRe )
        }
        line.contains('-') && line.contains('i')->
        {
            return translateToComplex(line , '-' , signRe)
        }
        line.contains('i')->
        {
            var subIm = fraction()
            if(line.filterNot { s->(s == 'i') }.isBlank())subIm = fraction(1,1)
            else subIm = line.filterNot { s -> (s == 'i') }.toFraction()
            if(signRe == '-')subIm*= fraction(-1,1)
            return complexNumber(im = subIm)
        }
        else->
        {
            var subRe = line.toFraction()
            if(signRe == '-')subRe *= fraction(-1,1)
            return complexNumber(re = subRe)
        }
    }
}

fun String.countLines() : Int
{
    var counter = 0
    for(i in this)if(i == '\n')counter++
    return counter + 1
}

fun String.countWords() : Int
{
    var counter = 0
    for( i in this)if( i == ' ')counter ++
    return counter + 1
}
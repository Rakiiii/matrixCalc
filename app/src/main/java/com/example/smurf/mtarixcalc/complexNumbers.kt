package com.example.smurf.mtarixcalc

class complexNumber(private var re : Double = 0.0 ,private  var im : Double = 0.0)
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
            is String -> return(this == toComplex(other))
            else -> return false
        }
    }


    override fun toString(): String
    {
        when
        {
            //вещественная часть 0 возвращаем только действительную
            im == 0.0 -> if((re - re.toInt()) != 0.0)return re.toString()else return re.toInt().toString()
            //действительная часть 0 возарвщвем только мнимую
            re == 0.0 -> if((im - im.toInt()) != 0.0)return im.toString() + 'i' else return im.toInt().toString() + 'i'

            im < 0 ->
            {
                when
                {
                    ((im - im.toInt()) != 0.0 && (re - re.toInt()) != 0.0)->return (re.toString() + im.toString() + 'i')
                    ((im - im.toInt()) == 0.0 && (re - re.toInt()) != 0.0)->return (re.toString() + im.toInt().toString() + 'i')
                    ((im - im.toInt()) != 0.0 && (re - re.toInt()) == 0.0)->return (re.toInt().toString() + im.toString() + 'i')
                    else -> return (re.toInt().toString() + im.toInt().toString() + 'i')
                }
            }

            else ->
            {
                when
                {
                    ((im - im.toInt()) != 0.0 && (re - re.toInt()) != 0.0)->return (re.toString() + '+' + im.toString() + 'i')
                    ((im - im.toInt()) == 0.0 && (re - re.toInt()) != 0.0)->return (re.toString() + '+' + im.toInt().toString() + 'i')
                    ((im - im.toInt()) != 0.0 && (re - re.toInt()) == 0.0)->return (re.toInt().toString() + '+' + im.toString() + 'i')
                    else -> return (re.toInt().toString() + '+' + im.toInt().toString() + 'i')
                }
            }
        }
    }

}

fun translateToComplex(line : String , signIm : Char = '+' , signRe : Char = '+'  ) : complexNumber
{
    if((signIm != '+' && signIm != '-') || (signRe != '+' && signRe != '-'))throw Exception("unnown char in complex number translation")
    //разбиваем текущий столбец на отдельные цифры
    var subIm = 0.0
    if(line.substringAfter(signIm).filterNot { s->(s == 'i') }.isBlank())subIm = 1.0
    else subIm = line.substringAfter(signIm).filterNot { s -> (s == 'i') }.toDouble()
    var subRe = line.substringBefore(signIm).toDouble()

    if(signRe == '-')subRe*=-1
    if(signIm == '-')subIm*=-1
    return complexNumber(subRe , subIm)
}

fun toComplex( mainline : String) : complexNumber
{
    var line = mainline
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
            var subIm = 0.0
            if(line.filterNot { s->(s == 'i') }.isBlank())subIm = 1.0
            else subIm = line.filterNot { s -> (s == 'i') }.toDouble()
            if(signRe == '-')subIm*=-1
            return complexNumber(im = subIm)
        }
        else->
        {
            var subRe = line.toDouble()
            if(signRe == '-')subRe *=-1
            return complexNumber(re = subRe)
        }
    }
}

fun countLines(text : String) : Int
{
    var counter = 0
    for(i in text)if(i == '\n')counter++
    return counter + 1
}

fun countWords(text : String): Int
{
    var counter = 0
    for( i in text)if( i == ' ')counter ++
    return counter + 1
}
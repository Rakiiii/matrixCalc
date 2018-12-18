package com.example.smurf.mtarixcalc

import android.widget.EditText

class matrixClass(private var width : Int = 2,
                  private var heigh : Int = 2 )
{
    // width = countWords(txt.text.toString().substringBefore('\n').trim())
    //  heigh = countLines(txt.text.toString())txt : EditText



    //матрца
    private var matrix : Array<Array<complexNumber>>

    //инициализация и первичное заполнение матрицы
    init
    {
        if(heigh == 0 || width == 0)throw Exception("MatrixSizeIs0x0")
        //инициализируем матрицу 0
        matrix  = Array<Array<complexNumber>>(heigh , { m -> Array<complexNumber>(width , { n -> complexNumber() }) })
    }

    constructor(txt : EditText) : this(
        width = countWords(txt.text.toString().substringBefore('\n').trim()) ,
        heigh = countLines(txt.text.toString()))
    {
        //запоминаем содержимое поля ввода
        var info : String = txt.text.toString()

        //проходим все строки матрицы
        for(i in 0 until heigh) {
            //запоминаем текущую строку
            var subLine = info.substringBefore('\n').trim()

            //удаляем текущую строку из скопированного текста
            info = info.substringAfter('\n')

            //проверяем полная ли матрица
            if (width != countWords(subLine))
            //если не полна кидаем ощибку
                throw Exception("amount of elemnts in line is different")

            //проходим все столбцы
            for (j in 0 until width) {
                //запоминаем текущий столбец
                val subWord = subLine.substringBefore(' ').trim()

                //удаляем текущий столбец из текущей строки
                subLine = subLine.substringAfter(' ').trim()

                //добовляем элемент в матрицу
                matrix[i][j] = toComplex(subWord)
            }
        }
    }

    constructor(size : Int, elem : complexNumber) : this(size , size)
    {
        for(i in 0 until size)matrix[i][i] = elem
    }

    //функция посчета определителя
    fun determinant() : complexNumber
    {
        //если матрица не квадратная кидаем ошибку
        if( width == heigh && width != 0)
        {
            return det(width , heigh , matrix)
        }
        else throw Exception("matrix is not square")
    }

    //функция рекурсивного вычисления определителея путем разложения по строке
    private fun det(w : Int , h : Int , m : Array<Array<complexNumber>>) : complexNumber
    {
        //инициализируем определитель
        var deter = complexNumber(0.0 , 0.0)

        //если матрица больше чем 2х2
        if( w > 2)
        {
            //раскладываем по первой строке
            for( k in 0 until w)
            {
                //наш матрица за вычетом строки и столбца
               var mat : Array<Array<complexNumber>> = Array<Array<complexNumber>>( h - 1 , { j -> Array<complexNumber>(w-1 , { n -> complexNumber() }) })

                //заполняем минор
                for( i in 0 until h-1)
                {
                    //создаем пустую строку
                    var row : Array<complexNumber> = Array(w-1 , { z -> complexNumber()})

                    //копируем все элементы из старой строки
                    for( j in 0 until w-1)
                    {
                        row[j] = m[i][j]
                    }
                    mat[i] = row
                }
                //складываем миноры
                //если число нарушений порядка четное то складываем
                if(k % 2 == 0)
                {
                    deter += m[0][k]*det(w - 1, h - 1, mat)
                }
                //если нечетное то вычитаем
                else
                {
                    deter -= m[0][k]*det(w - 1, h - 1, mat)

                }

            }
        }
        else
        {
            //считаем определитель 2х2
            deter = m[0][0]*m[1][1] - m[0][1]*m[1][0]
        }
        return deter
    }


    //пергрузка опратора +
    operator fun plus(secMatrix : matrixClass): matrixClass
    {
        if(this.width != secMatrix.width || this.heigh != secMatrix.heigh)throw Exception("Matrixes have different size")
        var resMatrix = this
        for(i in 0 until heigh)
        {
            for(j in 0 until width)resMatrix.matrix[i][j] += secMatrix.matrix[i][j]
        }
        return resMatrix
    }

    //пергрузка опратора -
    operator fun minus(secMatrix : matrixClass): matrixClass
    {
        if(this.width != secMatrix.width || this.heigh != secMatrix.heigh)throw Exception("Matrixes have different size")
        var resMatrix = this
        for(i in 0 until heigh)
        {
            for(j in 0 until width)resMatrix.matrix[i][j] -= secMatrix.matrix[i][j]
        }
        return resMatrix
    }

    //перегрузка опреатора *
    operator fun times(secMatrix: matrixClass): matrixClass
    {
        if(this.width != secMatrix.heigh)throw Exception("Line length isn't equals to column heigh")
        //создаем результируюшую матрицу
        var res  = matrixClass(width = secMatrix.width , heigh = this.heigh)

        //умножаем матрицы
        for(i in 0 until res.heigh)
        {
            for(j in 0 until res.width)
            {
                for(k in 0 until width)
                {
                    res.matrix[i][j] += this.matrix[i][k]*secMatrix.matrix[k][j]
                }
            }
        }

        return res
    }


    private fun plusLines(firstLine : Int , secondLine : Int , cof : complexNumber)
    {
        for(i in 0 until width)
        {
            matrix[firstLine][i] += matrix[secondLine][i]*cof
        }
    }

    private fun minusLines(firstLine : Int , secondLine : Int , cof : complexNumber)
    {
        for(i in 0 until width)
        {
            matrix[firstLine][i] -= matrix[secondLine][i]*cof
        }
    }

    private fun dividLine(line : Int , cof : complexNumber)
    {
        for(i in 0 until width)matrix[line][i] = matrix[line][i]/cof
    }

    fun invers() : matrixClass
    {
        if(width != heigh)throw Exception("Matrix is not Square")
        if(this.determinant() == complexNumber())throw Exception("Determinatn is 0")
        var res = matrixClass(width , complexNumber(1.0))
        for(i in 0 until heigh)
        {
            for(j in 0 until heigh)
            {
                if(i != j)
                {
                    //minusLines(j , i , matrix[j][i]/matrix[i][i])
                    res.minusLines(j , i , matrix[j][i]/matrix[i][i])
                }
            }
            //dividLine(i , matrix[i][i] )
            res.dividLine(i , matrix[i][i] )
        }
        //return this
        return res
    }


     override fun toString(): String
    {
        var res : String = ""

        for( i in 0 until heigh)
        {
            for(j in 0 until width)
            {
                if(j != width - 1)
                    res += matrix[i][j].toString() + ' '
                else res +=matrix[i][j].toString()
            }
            if(i != heigh-1)
            {
                res += '\n'
            }
        }

        return res
    }

    fun toString(sym : Char = ' '): String
    {
        var res : String = sym.toString()

        for( i in 0 until heigh)
        {
            for(j in 0 until width)
            {
                if(j != width - 1)
                res += matrix[i][j].toString() + ' '
                else res +=matrix[i][j].toString()
            }
            if(i != heigh-1)
            {
                res += sym; res += '\n';res +=sym
            }
            else res+= sym
        }

        return res
    }


}
package com.example.smurf.mtarixcalc

import android.widget.EditText

class matrixClass(txt : EditText)
{
    //количество столбцов матрицы
    private val width : Int

    //иниициализация количества столбцов матрицы
    init
    {
        width = countWords(txt.text.toString().substringBefore('\n'))
    }


    //количество строк матрицы
    private var heigh : Int

    //инициализация количества строк матрицы
    init
    {
            heigh = countLines(txt.text.toString())
    }


    //матрца
    private var matrix : Array<Array<complexNumber>>

    //инициализация и первичное заполнение матрицы
    init
    {
        //запоминаем содержимое поля ввода
        var info : String = txt.text.toString()

        //инициализируем матрицу 0
        matrix  = Array<Array<complexNumber>>(heigh , { m -> Array<complexNumber>(width , { n -> complexNumber() }) })

        //проходим все строки матрицы
        for(i in 0 until heigh)
        {
            //запоминаем текущую строку
            var subLine = info.substringBefore('\n')

            //удаляем текущую строку из скопированного текста
            info = info.substringAfter('\n')

            //проверяем полная ли матрица
            if(width != countWords(subLine))
                //если не полна кидаем ощибку
                throw Exception("amount of elemnts in line is different")

            //проходим все столбцы
            for( j in 0 until width)
            {
                //запоминаем текущий столбец
                val subWord = subLine.substringBefore(' ')

                //удаляем текущий столбец из текущей строки
                subLine = subLine.substringAfter(' ')

                //добовляем элемент в матрицу
                matrix[i][j] = toComplex(subWord)
            }
        }
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
                if(k % 2 != 0)
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
            //throw Exception(m[1][1].toString())
            deter = m[0][0]*m[1][1] - m[0][1]*m[1][0]
        }
        return deter
    }



}
package com.example.smurf.mtarixcalc

import android.widget.EditText

class matrixClass(val txt : EditText)
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
        matrix  = Array<Array<complexNumber>>(heigh , { m -> Array<complexNumber>(width , { n -> complexNumber(0.0,0.0) }) })

        //проходим все строки матрицы
        for(i in 1..heigh)
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
            for( j in 1..width)
            {
                //запоминаем текущий столбец
                var subWord = subLine.substringBefore(' ')

                //удаляем текущий столбец из текущей строки
                subLine = subLine.substringBefore(' ')

                //разбиваем текущий столбец на отдельные цифры
                var subIm = subWord.substringAfter('+').filterNot { s -> (s=='i') }
                var subRe = subWord.substringBefore('+')

                //добовляем элемент в матрицу
                matrix[i][j] = complexNumber(subRe.toDouble() , subIm.toDouble())
            }
        }
    }


    //функция посчета определителя
    fun determinant() : complexNumber
    {
        //если матрица не квадратная кидаем ошибку
        if( width == heigh && width != 0)
        {
            var det : complexNumber = det(width , heigh , matrix)
            return det
        }
        else throw Exception("matrix is not square")
    }

    //функция рекурсивного вычисления определителея путем разложения по строке
    private fun det(w : Int , h : Int , m : Array<Array<complexNumber>>) : complexNumber
    {
        //инициализируем определитель
        var det : complexNumber = complexNumber(0.0 , 0.0)

        //если матрица больше чем 2х2
        if( w != 2)
        {
            //раскладываем по первой строке
            for( k in 1..w)
            {
                //наш матрица за вычетом строки и столбца
               var mat : Array<Array<complexNumber>> = Array<Array<complexNumber>>( h - 1 , { m -> Array<complexNumber>(w-1 , { n -> complexNumber(0.0,0.0) }) })

                //заполняем минор
                for( i in 0..mat.size-1)
                {
                    var row : Array<complexNumber> = Array(w-1 , { z -> complexNumber(0.0,0.0)})
                    for( j in 0..row.size)
                    {
                        row[j] = m[i][j]
                    }
                    mat[i] = row
                }
                //складываем миноры
                //если число нарушений порядка четное то складываем
                if(k % 2 != 0)
                {
                    det += m[1][k]*det(w - 1, h - 1, mat)
                }
                //если нечетное то вычитаем
                else
                {
                    det -= m[1][k]*det(w - 1, h - 1, mat)

                }

            }
        }
        else
        {
            det = m[1][1]*m[2][2] - m[1][2]*m[2][1]
        }
        return det
    }



}
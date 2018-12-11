package com.example.smurf.mtarixcalc

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_matrix_calc.*
import org.jetbrains.anko.toast

class MatrixCalcActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matrix_calc)
    }


    fun countDet(v : View)
    {
        try
        {
            val mat = matrixClass(firstMatrix)

            infoOutput.text = ("Determinant is " + (mat.determinant().toString()))

            toast(mat.determinant().toString())
        }
        catch (e : Exception)
        {
            toast(e.toString())
            infoOutput.text = e.toString()
        }




    }

}

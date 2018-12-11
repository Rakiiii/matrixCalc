package com.example.smurf.mtarixcalc

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_matrix_calc.*
import org.jetbrains.anko.activityManager
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

            infoOutput.text = mat.determinant().toString()
        }
        catch (e : Exception)
        {
            toast(e.toString())
            infoOutput.text = e.toString()
        }




    }

}

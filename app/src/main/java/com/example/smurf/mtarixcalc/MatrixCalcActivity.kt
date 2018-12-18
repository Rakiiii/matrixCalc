package com.example.smurf.mtarixcalc

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.SpannableStringBuilder
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_matrix_calc.*
import org.jetbrains.anko.toast


class MatrixCalcActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matrix_calc)

        registerForContextMenu(infoOutput)

    }


    fun countDet(v : View)
    {
        try
        {
            infoOutput.text = ("Determinant is " + (matrixClass(firstMatrix).determinant().toString()))
        }
        catch (e : Exception)
        {
            toast(e.toString())
            infoOutput.text = e.toString()
        }
    }

    fun matrixPlus( v : View)
    {
        try
        {
            infoOutput.text = (matrixClass(firstMatrix) + matrixClass(secondMatrix)).toString()
        }
        catch (e : Exception)
        {
            toast(e.toString())
        }
    }

    fun matrixMinus( v : View)
    {
        try
        {
            infoOutput.text = (matrixClass(firstMatrix) - matrixClass(secondMatrix)).toString()
        }
        catch (e : Exception)
        {
            toast(e.toString())
        }
    }

    fun firstTimesSecondMatrix(v : View)
    {
        try
        {
            infoOutput.text = (matrixClass(firstMatrix) * matrixClass(secondMatrix)).toString()
        }
        catch(e : Exception)
        {
            toast(e.toString())
        }
    }

    fun secondTimesFirstMatrix(v : View)
    {
        try
        {
            infoOutput.text = (matrixClass(secondMatrix) * matrixClass(firstMatrix) ).toString()
        }
        catch(e : Exception)
        {
            toast(e.toString())
        }
    }

    fun invertMatrix(v : View)
    {
        try
        {
            infoOutput.text = matrixClass(firstMatrix).invers().toString()
        }
        catch (e : Exception)
        {
            toast(e.toString())
        }
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?)
    {
        if(menu != null)
        {
            menu.add(0, 0, 0, "paste to A")
            menu.add(0, 1, 1, "paste to B")
            menu.add(0, 2, 2, "copy")
        }
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean
    {
        when(item!!.itemId)
        {
            0->
            {
                firstMatrix.text = SpannableStringBuilder(infoOutput.text.toString())
                return true
            }
            1->
            {
                secondMatrix.text = SpannableStringBuilder(infoOutput.text.toString())
                return true
            }
            else ->
            {
                val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("some" , infoOutput.text)
                clipboard.primaryClip = clip
                return true
            }
        }
    }

}

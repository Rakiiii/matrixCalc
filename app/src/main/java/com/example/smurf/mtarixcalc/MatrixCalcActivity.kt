package com.example.smurf.mtarixcalc

//import android.support.v7.widget.RecyclerView
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.SpannableStringBuilder
import android.view.View
import kotlinx.android.synthetic.main.activity_matrix_calc.*
import org.jetbrains.anko.toast


class MatrixCalcActivity : AppCompatActivity() {


    private lateinit var matrixRecycler : RecyclerView
    private lateinit var matrixRecyclerLayoutManager: LinearLayoutManager
    private lateinit var matrixRecyclerAdapter: matrixAdapter


    override fun onCreate(savedInstanceState: Bundle?)
    {
        try {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_matrix_calc)

            initRecycleView()
            //matrixRecyclerAdapter.addNewElem(matrixGroup(matrixClass() , matrixClass() , "" , matrixClass()))
            //matrixRecyclerAdapter.addNewElem(matrixGroup(matrixClass() , matrixClass() , "" , matrixClass()))

            registerForContextMenu(matrixRecycler)
            //КОНТЕКСТНОЕ МЕНЮ ДЛЯ ТЕКСТА
            //registerForContextMenu(infoOutput)
        }
        catch (e : Exception)
        {
            toast(e.toString())
            firstMatrix.text = SpannableStringBuilder(e.toString())
        }


    }

    private fun initRecycleView()
    {
        matrixRecyclerLayoutManager = LinearLayoutManager(this)


        matrixRecyclerAdapter =  matrixAdapter(this , firstMatrix , secondMatrix)

        matrixRecycler = findViewById<RecyclerView>(R.id.matrixRecycler)

        matrixRecycler.layoutManager = matrixRecyclerLayoutManager

        matrixRecycler.adapter = matrixRecyclerAdapter

    }


    fun countDet(v : View)
    {
        try
        {
           // infoOutput.text = ("Determinant is " + (matrixClass(firstMatrix).determinant().toString()))
            matrixRecyclerAdapter.addNewElem(
                matrixGroup(
                    leftMatrix = matrixClass(firstMatrix),
                    sign = "Det",
                    rightMatrix = matrixClass(),
                    resMatrix = matrixClass(1 , matrixClass(firstMatrix).determinant()
                    )
                )
            )
        }
        catch (e : Exception)
        {
            toast(e.toString())
        }
    }

    fun matrixPlus( v : View)
    {
        try
        {
           // infoOutput.text = (matrixClass(firstMatrix) + matrixClass(secondMatrix)).toString()
            matrixRecyclerAdapter.addNewElem(
                matrixGroup(
                    leftMatrix = matrixClass(firstMatrix),
                    sign = "+",
                    rightMatrix = matrixClass(secondMatrix),
                    resMatrix = (matrixClass(firstMatrix) + matrixClass(secondMatrix))
                )
            )
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
            //infoOutput.text = (matrixClass(firstMatrix) - matrixClass(secondMatrix)).toString()
            matrixRecyclerAdapter.addNewElem(
                matrixGroup(
                    leftMatrix = matrixClass(firstMatrix),
                    sign = "-",
                    rightMatrix = matrixClass(secondMatrix),
                    resMatrix = (matrixClass(firstMatrix) - matrixClass(secondMatrix))
                )
            )
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
            //infoOutput.text = (matrixClass(firstMatrix) * matrixClass(secondMatrix)).toString()
            matrixRecyclerAdapter.addNewElem(
                matrixGroup(
                    leftMatrix = matrixClass(firstMatrix),
                    sign = "*",
                    rightMatrix = matrixClass(secondMatrix),
                    resMatrix = (matrixClass(firstMatrix) * matrixClass(secondMatrix))
                )
            )
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
            //infoOutput.text = (matrixClass(secondMatrix) * matrixClass(firstMatrix) ).toString()
            matrixRecyclerAdapter.addNewElem(
                matrixGroup(
                    leftMatrix = matrixClass(secondMatrix),
                    sign = "*",
                    rightMatrix = matrixClass(firstMatrix),
                    resMatrix = (matrixClass(secondMatrix) * matrixClass(firstMatrix))
                )
            )
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
            //infoOutput.text = matrixClass(firstMatrix).invers().toString()
            matrixRecyclerAdapter.addNewElem(
                matrixGroup(
                    leftMatrix = matrixClass(firstMatrix),
                    sign = "Inv",
                    rightMatrix = matrixClass(),
                    resMatrix = matrixClass(firstMatrix).invers()
                )
            )

        }
        catch (e : Exception)
        {
            toast(e.toString())
        }
    }


    /*override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?)
    {
        if(menu != null)
        {
            menu.add(0, 0, 0, "paste to A")
            menu.add(0, 1, 1, "paste to B")
            menu.add(0, 2, 2, "copy")
        }
    }*/

    /*override fun onContextItemSelected(item: MenuItem?): Boolean
    {

        var pos : Int = -1
        try
        {
            pos =   matrixRecyclerAdapter.pos

        }catch (e : java.lang.Exception)
        {
            return super.onContextItemSelected(item)
        }
        when (item!!.itemId)
        {
                0 -> {

                    if(item is TextView)
                    firstMatrix.text = SpannableStringBuilder(item.text.toString())
                    return true
                }
                1 -> {
                    if(item is TextView)
                    secondMatrix.text = SpannableStringBuilder(item.text.toString())
                    return true
                }
                else -> {
                    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    if(item is TextView) {
                        val clip = ClipData.newPlainText("some", item.text)
                        clipboard.primaryClip = clip
                        return true
                    }
                    throw Exception("Bad copy")
                }
        }

    }*/

}

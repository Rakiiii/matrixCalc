package com.example.smurf.mtarixcalc

//import android.support.v7.widget.RecyclerView
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.text.SpannableStringBuilder
import android.view.MenuItem
import android.view.View
import com.example.smurf.mtarixcalc.R.id.*
import kotlinx.android.synthetic.main.activity_matrix_calc.*
import org.jetbrains.anko.toast


class MatrixCalcActivity : AppCompatActivity() {


    private lateinit var matrixRecycler : RecyclerView
    private lateinit var matrixRecyclerLayoutManager: LinearLayoutManager
    private lateinit var matrixRecyclerAdapter: matrixAdapter
    private lateinit var mToggler : ActionBarDrawerToggle

    private lateinit var mMatrixRecyclerViewModel : MatrixRecyclerViewModel

    override fun onCreate(savedInstanceState: Bundle?)
    {
        try {

            //val model = ViewModelProviders.of(this).get(OurModel::class.java!!)
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_matrix_calc)

            mMatrixRecyclerViewModel = ViewModelProviders.of(this).get(MatrixRecyclerViewModel::class.java)

            initRecycleView()

            mToggler  = ActionBarDrawerToggle(this , matrixDrawerLayout , R.string.open ,R.string.close)

            matrixDrawerLayout.addDrawerListener(mToggler)

            mToggler.syncState()

            supportActionBar!!.setDisplayHomeAsUpEnabled(true)


            //обработчик нажатия в навигационном дровере
            matrixNavigationView.setNavigationItemSelectedListener( object : NavigationView.OnNavigationItemSelectedListener
            {
                override fun onNavigationItemSelected(item: MenuItem): Boolean {
                    when(item.itemId)
                    {
                        matrixCalcBtn->
                        {
                            item.isChecked = true
                            matrixDrawerLayout.closeDrawers()
                            return true
                        }
                        polinomicCalculationBtn->
                        {
                            item.isChecked = true
                            matrixDrawerLayout.closeDrawers()
                            val polIntent = Intent( this@MatrixCalcActivity , activity_polinom_calc::class.java)
                            startActivity(polIntent)
                            return true
                        }
                        aboutBtn->
                        {
                            return true
                        }
                        else ->return false
                    }
                }
            })


            //удаление и возврат по свайпу
            enableSwipeToDeleteAndUndo()

            if(!mMatrixRecyclerViewModel.isEmpty())matrixRecyclerAdapter.setList(mMatrixRecyclerViewModel.getList())

        }
        catch (e : Exception)
        {
            toast(e.toString())
            firstMatrix.text = SpannableStringBuilder(e.toString())
        }


    }



    override fun onOptionsItemSelected(item: MenuItem?): Boolean
    {
        if(mToggler.onOptionsItemSelected(item)) return true
        return super.onOptionsItemSelected(item)
    }


    //инициализация кecycleView
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
                mMatrixRecyclerViewModel.add(
                matrixGroup(
                    leftMatrix = matrixClass(firstMatrix),
                    sign = "Det",
                    rightMatrix = matrixClass(),
                    resMatrix = matrixClass(1 , matrixClass(firstMatrix).determinant()
                    )
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
                mMatrixRecyclerViewModel.add(
                matrixGroup(
                    leftMatrix = matrixClass(firstMatrix),
                    sign = "+",
                    rightMatrix = matrixClass(secondMatrix),
                    resMatrix = (matrixClass(firstMatrix) + matrixClass(secondMatrix))
                )
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
                mMatrixRecyclerViewModel.add(
                matrixGroup(
                    leftMatrix = matrixClass(firstMatrix),
                    sign = "-",
                    rightMatrix = matrixClass(secondMatrix),
                    resMatrix = (matrixClass(firstMatrix) - matrixClass(secondMatrix))
                )
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
                mMatrixRecyclerViewModel.add(
                matrixGroup(
                    leftMatrix = matrixClass(firstMatrix),
                    sign = "*",
                    rightMatrix = matrixClass(secondMatrix),
                    resMatrix = (matrixClass(firstMatrix) * matrixClass(secondMatrix))
                )
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
                mMatrixRecyclerViewModel.add(
                matrixGroup(
                    leftMatrix = matrixClass(secondMatrix),
                    sign = "*",
                    rightMatrix = matrixClass(firstMatrix),
                    resMatrix = (matrixClass(secondMatrix) * matrixClass(firstMatrix))
                )
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
                mMatrixRecyclerViewModel.add(
                matrixGroup(
                    leftMatrix = matrixClass(firstMatrix),
                    sign = "Inv",
                    rightMatrix = matrixClass(),
                    resMatrix = matrixClass(firstMatrix).invers()
                )
            )
            )
        }
        catch (e : Exception)
        {
            toast(e.toString())
        }
    }


    private fun enableSwipeToDeleteAndUndo()
    {
        val swipeToDeleteCallback = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, i: Int) {


                val position = viewHolder.adapterPosition
                val item = matrixRecyclerAdapter.getData(position)

                matrixRecyclerAdapter.removeElement(position)


                val snackbar = Snackbar
                    .make( matrixFrame , "Item was removed from the list.", Snackbar.LENGTH_LONG)
                snackbar.setAction("UNDO") {
                    matrixRecyclerAdapter.restoreItem(position , item)
                    matrixRecycler.scrollToPosition(position)
                }

                snackbar.setActionTextColor(Color.YELLOW)
                snackbar.show()

                mMatrixRecyclerViewModel.updateList(matrixRecyclerAdapter.getList())

            }
        }

        val itemTouchhelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchhelper.attachToRecyclerView(matrixRecycler)
    }


}

package com.example.smurf.mtarixcalc

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
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_polinom_calc.*
import org.jetbrains.anko.toast


class activity_polinom_calc : AppCompatActivity() {


    private lateinit var polRecycler : RecyclerView
    private lateinit var polAdapter: polAdapter
    private lateinit var polLayoutManager: LinearLayoutManager
    private lateinit var mToggle: ActionBarDrawerToggle

    private lateinit var mPolinomRecyclerViewModel: PolinomRecyclerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_polinom_calc)

        mPolinomRecyclerViewModel = ViewModelProviders.of(this).get(PolinomRecyclerViewModel::class.java)

        mToggle = ActionBarDrawerToggle( this , polDrawerLayout , R.string.open , R.string.close)

        polDrawerLayout.addDrawerListener(mToggle)

        mToggle.syncState()

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        initRecyclerView()

        //слушатель для дравера навигации
        polNavigationView.setNavigationItemSelectedListener( object : NavigationView.OnNavigationItemSelectedListener
        {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when(item.itemId)
                {
                    R.id.matrixCalcBtn ->
                    {
                        item.isChecked = true
                        polDrawerLayout.closeDrawers()
                        val intent = Intent( this@activity_polinom_calc , MatrixCalcActivity::class.java)
                        startActivity(intent)
                        return true
                    }
                    R.id.polinomicCalculationBtn ->
                    {
                        item.isChecked = true
                        polDrawerLayout.closeDrawers()
                        return true
                    }
                    R.id.aboutBtn ->
                    {
                        return true
                    }
                    else ->return false
                }
            }
        })

        btnPolPlus.setOnClickListener( object : View.OnClickListener
        {
            override fun onClick(v: View?)
            {
                try
                {
                    val left = polinom(firstPolinom)
                    val right = polinom(secondPolinom)
                    val result = left + right

                    polAdapter.addElement(
                        mPolinomRecyclerViewModel.add(
                        polGroup(
                            polLeftPolinom = left,
                            polRightPolinom = right,
                            polResPolinom = result,
                            polSignPolinom = "+"
                        )
                    )
                    )
                }
                catch (e : Exception)
                {
                    toast(e.toString())
                }
            }
        })

        btnPolMinus.setOnClickListener( object : View.OnClickListener
        {
            override fun onClick(v: View?) {
                try
                {
                    val left = polinom(firstPolinom)
                    val right = polinom(secondPolinom)
                    val result = left - right

                    polAdapter.addElement(
                        mPolinomRecyclerViewModel.add(
                        polGroup(
                            polLeftPolinom = left,
                            polRightPolinom = right,
                            polResPolinom = result,
                            polSignPolinom = "-"
                        )
                    )
                    )
                }
                catch ( e : Exception)
                {
                    toast( e.toString())
                }
            }
        })

        btnPolTimes.setOnClickListener( object : View.OnClickListener
        {
            override fun onClick(v: View?) {
                try
                {
                    val left = polinom(firstPolinom)
                    val right = polinom(secondPolinom)
                    val result = left * right

                    polAdapter.addElement(
                        mPolinomRecyclerViewModel.add(
                        polGroup(
                            polLeftPolinom = left,
                            polRightPolinom = right,
                            polResPolinom = result,
                            polSignPolinom = "*"
                        )
                    )
                    )
                }
                catch ( e : Exception)
                {
                    toast( e.toString())
                }
            }
        })

        btnPolDiv.setOnClickListener( object : View.OnClickListener
        {
            override fun onClick(v: View?) {
                try
                {
                    val left = polinom(firstPolinom)
                    val right = polinom(secondPolinom)
                    val result = (left / right)

                    polAdapter.addElement(
                        mPolinomRecyclerViewModel.add(
                        polGroup(
                            polLeftPolinom = left,
                            polRightPolinom = right,
                            polResPolinom = result[0],
                            polSignPolinom = "/",
                            polOstPolinom = result[1]
                        )
                    )
                    )
                }
                catch ( e : Exception)
                {
                    toast( e.toString())
                }
            }
        })

        btnPolRootsA.setOnClickListener( object : View.OnClickListener
        {
            override fun onClick(v: View?) {
                try
                {
                    val left = polinom(firstPolinom)
                    left.result()
                    polAdapter.addElement(
                        mPolinomRecyclerViewModel.add(
                        polGroup(
                            polLeftPolinom = left,
                            polRightPolinom = polinom(0),
                            polSignPolinom = "",
                            polResPolinom = polinom(0)
                        )
                    )
                    )
                }
                catch ( e : Exception)
                {
                    toast(e.toString())
                }
            }
        })

        enableSwipeToDeleteAndUndo()

        if(!mPolinomRecyclerViewModel.isEmpty())polAdapter.setList( mPolinomRecyclerViewModel.getList() )

    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean
    {
        if(mToggle.onOptionsItemSelected(item)) return true
        return super.onOptionsItemSelected(item)
    }

    fun initRecyclerView()
    {
        polLayoutManager = LinearLayoutManager(this)
        polAdapter = polAdapter( this , firstPolinom , secondPolinom)
        polRecycler = findViewById(R.id.polinomRecycler)
        polRecycler.adapter = polAdapter
        polRecycler.layoutManager = polLayoutManager
    }


    private fun enableSwipeToDeleteAndUndo()
    {
        val swipeToDeleteCallback = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, i: Int) {


                val position = viewHolder.adapterPosition
                val item = polAdapter.getData(position)

                polAdapter.removeElement(position)


                val snackbar = Snackbar
                    .make( polFrame , "Item was removed from the list.", Snackbar.LENGTH_LONG)
                snackbar.setAction("UNDO") {
                    polAdapter.restoreItem(item, position)
                    polRecycler.scrollToPosition(position)
                }

                snackbar.setActionTextColor(Color.YELLOW)
                snackbar.show()

                mPolinomRecyclerViewModel.updateList( polAdapter.getList())

            }
        }

        val itemTouchhelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchhelper.attachToRecyclerView(polRecycler)
    }
}

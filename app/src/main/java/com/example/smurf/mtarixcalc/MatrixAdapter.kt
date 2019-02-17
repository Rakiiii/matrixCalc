package com.example.smurf.mtarixcalc

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.SpannableStringBuilder
import android.view.*
import android.widget.EditText
import android.widget.TextView

class matrixAdapter(val context: Context , val firstMatrix : EditText , val secondMatrix : EditText ) : RecyclerView.Adapter<matrixAdapter.matrixViewHolder>()
{

        private var listOfMatrix : ArrayList<matrixGroup> = ArrayList()

        var pos : Int = 0

        fun addNewElem(group : matrixGroup)
        {
            listOfMatrix.add(group)
            notifyDataSetChanged()
        }

        fun clearList()
        {
            listOfMatrix.clear()
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): matrixViewHolder
    {
        return matrixViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.matrix_expressions , parent , false ) )
    }

    override fun onBindViewHolder(holder: matrixViewHolder, position: Int)
    {
        holder.bind(listOfMatrix[position])

        holder.itemView.setOnCreateContextMenuListener( object : View.OnCreateContextMenuListener
        {
            override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
                menu!!.add(0, 0, 0, "copy left matrix").
                        setOnMenuItemClickListener( object : MenuItem.OnMenuItemClickListener
                        {
                            override fun onMenuItemClick(item: MenuItem?): Boolean
                            {
                                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                        val clip = ClipData.newPlainText("some", holder.leftMatrix.text)
                                        clipboard.primaryClip = clip

                                return true
                            }
                        })
                menu.add(0,1,1 , "copy right matrix").
                    setOnMenuItemClickListener( object : MenuItem.OnMenuItemClickListener
                    {
                        override fun onMenuItemClick(item: MenuItem?): Boolean {
                                    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                    val clip = ClipData.newPlainText("some", holder.rightMatrix.text.toString().filterNot { s -> (s == '=') } )
                                    clipboard.primaryClip = clip
                            return true
                        }
                    })
                menu.add(0,2,2, "copy result").
                    setOnMenuItemClickListener( object : MenuItem.OnMenuItemClickListener
                    {
                        override fun onMenuItemClick(item: MenuItem?): Boolean {
                                    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                    val clip = ClipData.newPlainText("some", holder.resMatrix.text)
                                    clipboard.primaryClip = clip
                            return true
                        }
                    })
                menu.add(0, 3, 3, "paste left matrix to A").
                        setOnMenuItemClickListener( object : MenuItem.OnMenuItemClickListener
                        {
                            override fun onMenuItemClick(item: MenuItem?): Boolean {
                                when(holder.adapterPosition)
                                {
                                    else -> firstMatrix.text = SpannableStringBuilder(holder.leftMatrix.text.toString())
                                }
                                return true
                            }
                        })
                menu.add(0, 4, 4, "paste right matrix to A").
                    setOnMenuItemClickListener( object : MenuItem.OnMenuItemClickListener
                    {
                        override fun onMenuItemClick(item: MenuItem?): Boolean {
                           firstMatrix.text = SpannableStringBuilder(holder.rightMatrix.text.toString().filterNot { s -> (s == '=') } )
                            return true
                        }
                    })
                menu.add( 0 , 5 ,5 , "paste result to A").
                        setOnMenuItemClickListener( object : MenuItem.OnMenuItemClickListener
                        {
                            override fun onMenuItemClick(item: MenuItem?): Boolean {
                                firstMatrix.text = SpannableStringBuilder(holder.resMatrix.text.toString())
                                return true
                            }
                        })

                menu.add(0, 6, 6, "paste left matrix to B").
                    setOnMenuItemClickListener( object : MenuItem.OnMenuItemClickListener
                    {
                        override fun onMenuItemClick(item: MenuItem?): Boolean {
                            secondMatrix.text = SpannableStringBuilder(holder.leftMatrix.text.toString())
                            return true
                        }
                    })
                menu.add(0, 7, 7, "paste right matrix to B").
                    setOnMenuItemClickListener( object : MenuItem.OnMenuItemClickListener
                    {
                        override fun onMenuItemClick(item: MenuItem?): Boolean {
                            secondMatrix.text = SpannableStringBuilder(holder.rightMatrix.text.toString().filterNot { s -> (s == '=') } )
                            return true
                        }
                    })
                menu.add( 0 , 8 ,8 , "paste result to B").
                    setOnMenuItemClickListener( object : MenuItem.OnMenuItemClickListener
                    {
                        override fun onMenuItemClick(item: MenuItem?): Boolean {
                            secondMatrix.text = SpannableStringBuilder(holder.resMatrix.text.toString())
                            return true
                        }
                    })
            }
        })
    }



    override fun getItemCount(): Int
    {
        return listOfMatrix.size
    }



        class matrixViewHolder constructor(itemView : View ) : RecyclerView.ViewHolder (itemView)
        //, View.OnCreateContextMenuListener
        {
           /* init
            {
                    //itemView.setOnCreateContextMenuListener(this)
            }*/



            var leftMatrix : TextView = itemView.findViewById(R.id.leftMatrix)
                private set
            var rightMatrix : TextView = itemView.findViewById(R.id.rightMatrix)
                private set
            var resMatrix : TextView = itemView.findViewById(R.id.resMatrix)
                private set
            var sign : TextView = itemView.findViewById(R.id.operationSignm)
                private set


            fun bind(group : matrixGroup)
            {
                leftMatrix.text = group.leftMatrix.toString('|')
                if(!group.rightMatrix.isEmpty()) rightMatrix.text =(group.rightMatrix.toString('|').substringBefore('\n') +
                        " =" +
                        "\n" +
                        group.rightMatrix.toString('|').substringAfter('\n'))
                resMatrix.text = group.resMatrix.toString('|')
                sign.text = group.sign
            }

        }
    }
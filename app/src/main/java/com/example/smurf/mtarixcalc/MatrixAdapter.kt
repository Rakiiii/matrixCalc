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

        fun addNewElem(group : matrixGroup)
        {
            listOfMatrix.add(0 ,group)
            notifyDataSetChanged()
        }

        fun clearList()
        {
            listOfMatrix.clear()
            notifyDataSetChanged()
        }

        fun removeElement( position: Int)
        {
            listOfMatrix.removeAt(position)
            notifyDataSetChanged()
        }

        fun getData( position: Int) = listOfMatrix[position]

        fun restoreItem(position: Int , matrixGroup: matrixGroup)
        {
            listOfMatrix.add(position , matrixGroup)
            notifyItemInserted(position)
        }

        fun setList( newArray : ArrayList<matrixGroup>)
        {
            listOfMatrix = newArray
            notifyDataSetChanged()
        }

        fun getList() = listOfMatrix

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): matrixViewHolder
    {
        return matrixViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.matrix_expressions , parent , false ) )
    }

    override fun onBindViewHolder(holder: matrixViewHolder, position: Int)
    {
        holder.bind(listOfMatrix[position])


        //листенер для контекстного меню на левую матрицу
        holder.leftMatrix.setOnCreateContextMenuListener( object : View.OnCreateContextMenuListener
        {
            override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?)
            {
                if(menu != null)
                {
                    //вставка в первую матрицу
                    menu.add(0, 0, 0, "Paste to A")
                        .setOnMenuItemClickListener(object : MenuItem.OnMenuItemClickListener {
                            override fun onMenuItemClick(item: MenuItem?): Boolean {
                                firstMatrix.text = SpannableStringBuilder(holder.leftMatrix.text.toString())
                                return true
                            }
                        })

                    //втсавка вл вторую матрицу
                    menu.add(1, 1, 1, "Paste to B")
                        .setOnMenuItemClickListener(object : MenuItem.OnMenuItemClickListener {
                            override fun onMenuItemClick(item: MenuItem?): Boolean {
                                secondMatrix.text = SpannableStringBuilder(holder.leftMatrix.text.toString())
                                return true
                            }
                        })

                    //вставка в клипбоард
                    menu.add(2,2,2,"Copy").
                        setOnMenuItemClickListener( object : MenuItem.OnMenuItemClickListener
                        {
                            override fun onMenuItemClick(item: MenuItem?): Boolean {
                                val clipboard = context.getSystemService( Context.CLIPBOARD_SERVICE) as ClipboardManager
                                val clip = ClipData.newPlainText("some" , holder.leftMatrix.text.toString())
                                clipboard.primaryClip = clip
                                return true
                            }
                        })
                }
            }
        })



        holder.resMatrix.setOnCreateContextMenuListener( object : View.OnCreateContextMenuListener
        {
            override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
                if(menu != null)
                {
                    menu.add(0,0,0,"Paste to A").
                        setOnMenuItemClickListener( object : MenuItem.OnMenuItemClickListener
                        {
                            override fun onMenuItemClick(item: MenuItem?): Boolean {
                                firstMatrix.text = SpannableStringBuilder(holder.resMatrix.text.toString())
                                return true
                            }
                        })
                    menu.add(1, 1, 1, "Paste to B")
                        .setOnMenuItemClickListener(object : MenuItem.OnMenuItemClickListener {
                            override fun onMenuItemClick(item: MenuItem?): Boolean {
                                secondMatrix.text = SpannableStringBuilder(holder.resMatrix.text.toString())
                                return true
                            }
                        })
                    menu.add(2,2,2,"Copy").setOnMenuItemClickListener( object : MenuItem.OnMenuItemClickListener
                    {
                        override fun onMenuItemClick(item: MenuItem?): Boolean {
                            val clipboardManager  = context.getSystemService( Context.CLIPBOARD_SERVICE) as ClipboardManager
                            val clip = ClipData.newPlainText("some" , holder.resMatrix.text.toString())
                            clipboardManager.primaryClip = clip
                            return true
                        }
                    })
                }
            }

        })

        holder.rightMatrix.setOnCreateContextMenuListener( object  : View.OnCreateContextMenuListener
        {
            override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
                if(menu != null)
                {
                    menu.add(0,0,0,"Paste to A").
                        setOnMenuItemClickListener( object : MenuItem.OnMenuItemClickListener
                        {
                            override fun onMenuItemClick(item: MenuItem?): Boolean {
                                firstMatrix.text = SpannableStringBuilder(holder.rightMatrix.text.toString().filterNot { s -> s == '=' })
                                return true
                            }
                        })
                    menu.add(1, 1, 1, "Paste to B")
                        .setOnMenuItemClickListener(object : MenuItem.OnMenuItemClickListener {
                            override fun onMenuItemClick(item: MenuItem?): Boolean {
                                secondMatrix.text = SpannableStringBuilder(holder.rightMatrix.text.toString().filterNot { s -> s == '=' })
                                return true
                            }
                        })
                    menu.add(2,2,2,"Copy").setOnMenuItemClickListener( object : MenuItem.OnMenuItemClickListener
                    {
                        override fun onMenuItemClick(item: MenuItem?): Boolean {
                            val clipboardManager  = context.getSystemService( Context.CLIPBOARD_SERVICE) as ClipboardManager
                            val clip = ClipData.newPlainText("some" , holder.rightMatrix.text.toString().filterNot { s -> s == '=' })
                            clipboardManager.primaryClip = clip
                            return true
                        }
                    })
                }
            }
        })
    }



    override fun getItemCount(): Int
    {
        return listOfMatrix.size
    }



        class matrixViewHolder constructor(itemView : View ) : RecyclerView.ViewHolder (itemView)
        {




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
                        "    =" +
                        "\n"    +
                        group.rightMatrix.toString('|').substringAfter('\n'))
                resMatrix.text = group.resMatrix.toString('|')
                sign.text = group.sign
            }

        }
    }
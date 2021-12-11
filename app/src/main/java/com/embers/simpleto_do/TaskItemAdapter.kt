package com.embers.simpleto_do

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskItemAdapter(
    private val taskList: List<String>,
    private val clickListener: ClickListener
) :
    RecyclerView.Adapter<TaskItemAdapter.ViewHolder>() {

    interface ClickListener {
        fun onSingleClick(position: Int) {}
        fun onDoubleClick(position: Int) {}
        fun onLongClick(position: Int) {}
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(android.R.id.text1)

        init {
            itemView.setOnLongClickListener {
                clickListener.onLongClick(bindingAdapterPosition)
                true
            }

            itemView.setOnClickListener(object : DoubleClickListener() {
                override fun onDoubleClick(item: View) {
                    clickListener.onDoubleClick(bindingAdapterPosition)
                }
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val taskView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false)

        return ViewHolder(taskView)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task: String = taskList[position]
        val textView = holder.textView
        textView.text = task
    }
}
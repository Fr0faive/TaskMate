package com.lastpro.taskmate.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lastpro.taskmate.R
import com.lastpro.taskmate.model.TaskLabel


class TaskLabelAdapter(private val mList:List<TaskLabel>): RecyclerView.Adapter<TaskLabelAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskLabelAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_tasklabel, parent, false)

        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: TaskLabelAdapter.ViewHolder, position: Int) {
        val ItemsViewModel = mList[position]
        holder.textView.text = ItemsViewModel.name
    }


    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View):RecyclerView.ViewHolder(ItemView) {
        val textView: TextView = itemView.findViewById(R.id.textView)
    }
}


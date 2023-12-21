package com.lastpro.taskmate.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.lastpro.taskmate.R
import com.lastpro.taskmate.model.Task


class TasksAdapter(private val mList:List<Task>, private val onEdit: (Int)->Unit,private val  onDelete: (Int)->Unit): RecyclerView.Adapter<TasksAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_tasks, parent, false)

        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: TasksAdapter.ViewHolder, position: Int) {
        val ItemsViewModel = mList[position]

        holder.judul_task.text = ItemsViewModel.title
        holder.deadline_task.text = ItemsViewModel.dueDate.toString()
        holder.description_task.text = ItemsViewModel.description.toString()

        holder.button_edit_task.setOnClickListener{
            onEdit(ItemsViewModel.id)
        }
        holder.button_delete_task.setOnClickListener{
            onDelete(ItemsViewModel.id)
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View):RecyclerView.ViewHolder(ItemView) {
        val judul_task: TextView = itemView.findViewById(R.id.judul_task)
        val deadline_task: TextView = itemView.findViewById(R.id.deadline_task)
        val description_task: TextView = itemView.findViewById(R.id.description_task)
        val button_edit_task: Button = itemView.findViewById(R.id.button_edit_task)
        val button_delete_task: Button = itemView.findViewById(R.id.button_delete_task)
    }
}


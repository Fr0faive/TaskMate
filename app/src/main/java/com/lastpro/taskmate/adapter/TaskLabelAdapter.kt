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
import com.lastpro.taskmate.MainActivity
import com.lastpro.taskmate.R
import com.lastpro.taskmate.TasklabelEdit
import com.lastpro.taskmate.model.TaskLabel
import kotlinx.coroutines.withContext


class TaskLabelAdapter(private val mList:List<TaskLabel>, private val onEdit: (Int)->Unit,private val  onView: (Int)->Unit,private val  onDelete: (Int)->Unit): RecyclerView.Adapter<TaskLabelAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskLabelAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_tasklabel, parent, false)

        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: TaskLabelAdapter.ViewHolder, position: Int) {
        val ItemsViewModel = mList[position]

        holder.judul_tasklabel.text = ItemsViewModel.name

        holder.button_edit_tasklabel.setOnClickListener{
            onEdit(ItemsViewModel.id)
        }
        holder.button_delete_tasklabel.setOnClickListener{
            onDelete(ItemsViewModel.id)
        }
        holder.button_view_task.setOnClickListener{
            onView(ItemsViewModel.id)
        }

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View):RecyclerView.ViewHolder(ItemView) {
        val judul_tasklabel: TextView = itemView.findViewById(R.id.judul_tasklabel)
        val button_edit_tasklabel: Button = itemView.findViewById(R.id.button_edit_tasklabel)
        val button_delete_tasklabel: Button = itemView.findViewById(R.id.button_delete_tasklabel)
        val button_view_task: Button = itemView.findViewById(R.id.button_view_task)
    }
}


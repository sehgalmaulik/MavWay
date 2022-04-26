package com.project.mavway1.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.mavway1.R
import com.project.mavway1.activities.Fragments.TasksFragment
import com.project.mavway1.models.Tasks

class TasksRVAdapter(private val context: Context, private val listener: ITasksRVAdapter): RecyclerView.Adapter<TasksRVAdapter.TaskViewHolder>() {

    private val allTasks = ArrayList<Tasks>()

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textview = itemView.findViewById<View>(R.id.textView2) as TextView
        val deletebutton = itemView.findViewById<View>(R.id.deleteTask) as ImageButton
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val viewHolder = TaskViewHolder(LayoutInflater.from(context).inflate(R.layout.item_task, parent, false))
        viewHolder.deletebutton.setOnClickListener {
            listener.onItemClick(allTasks[viewHolder.bindingAdapterPosition])
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val currentTask = allTasks[position]
        holder.textview.text = currentTask.todo
    }

    override fun getItemCount(): Int {
        return allTasks.size
    }

    fun updateTasks(tasks: List<Tasks>) {
        allTasks.clear()
        allTasks.addAll(tasks)
        notifyDataSetChanged()
    }

}

interface ITasksRVAdapter {
    fun onItemClick(tasks: Tasks)
}
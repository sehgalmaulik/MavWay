package com.project.mavway1.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.mavway1.R
import com.project.mavway1.models.Tasks

//
//import android.content.Context
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Button
//import android.widget.ImageButton
//import android.widget.TextView
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.project.mavway1.R
//import com.project.mavway1.activities.Fragments.TasksFragment
//import com.project.mavway1.models.Tasks
//
//class TasksRVAdapter(private val context: Context, private val listener: ITasksRVAdapter): RecyclerView.Adapter<TasksRVAdapter.TaskViewHolder>() {
//
//    private val allTasks = ArrayList<Tasks>()
//
//    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val textview = itemView.findViewById<View>(R.id.textView2) as TextView
//        val deletebutton = itemView.findViewById<View>(R.id.deleteTask) as ImageButton
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
//        val viewHolder = TaskViewHolder(LayoutInflater.from(context).inflate(R.layout.item_task, parent, false))
//        viewHolder.deletebutton.setOnClickListener {
//            listener.onItemClick(allTasks[viewHolder.bindingAdapterPosition])
//        }
//
//        return viewHolder
//    }
//
//    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
//        val currentTask = allTasks[position]
//        holder.textview.text = currentTask.todo
//    }
//
//    override fun getItemCount(): Int {
//        return allTasks.size
//    }
//
//    fun updateTasks(tasks: List<Tasks>) {
//        allTasks.clear()
//        allTasks.addAll(tasks)
//        notifyDataSetChanged()
//    }
//
//}
//
//interface ITasksRVAdapter {
//    fun onItemClick(tasks: Tasks)
//}

class TasksRVAdapter(private val tasks : ArrayList<Tasks>, private val listener: OnTaskClick) : RecyclerView.Adapter<TasksRVAdapter.TaskViewHolder>()  {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TaskViewHolder {
//        val itemView = View.inflate(parent.context, R.layout.item_class_list, null)
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        val viewHolder = TaskViewHolder(itemView)
//        itemView.setOnClickListener{
//            val position = itemView.tag as Int
//            listener.onItemClick(tasks[viewHolder.absoluteAdapterPosition])
            val delbutton = itemView.findViewById<View>(R.id.deleteTask) as ImageButton
            delbutton.setOnClickListener {
                listener.onItemClick(tasks[viewHolder.absoluteAdapterPosition])
            }
//        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val classList : Tasks = tasks[position]
        holder.task.text = classList.todo


        //make an click listener for holder here


    }

    override fun getItemCount(): Int {
        return tasks.size
    }



    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val courseName = itemView.findViewById<View>(R.id.class_name) as TextView
//        val courseCode = itemView.findViewById<View>(R.id.class_number) as TextView
//        val timings = itemView.findViewById<View>(R.id.timing) as TextView
//        val days = itemView.findViewById<View>(R.id.days) as TextView
        val task = itemView.findViewById<View>(R.id.textView2) as TextView

    }

//    override fun onItemClick(course: UserCourses) {
//        TODO("Not yet implemented")
//        Toast.makeText(this, course , Toast.LENGTH_SHORT).show()
//    }


}

interface OnTaskClick {
    fun onItemClick(task: Tasks)

}
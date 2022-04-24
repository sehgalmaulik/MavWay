package com.project.mavway1.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.mavway1.R
import com.project.mavway1.models.UserCourses

//maybe change
class CourseAdapter(private val classes : ArrayList<UserCourses>) : RecyclerView.Adapter<CourseAdapter.CourseViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CourseAdapter.CourseViewHolder {
//        val itemView = View.inflate(parent.context, R.layout.item_class_list, null)
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_class_list, parent, false)
        return CourseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CourseAdapter.CourseViewHolder, position: Int) {
        val classList : UserCourses = classes[position]
        holder.courseName.text = classList.course
        holder.courseCode.text = classList.courseCode
        holder.timings.text = classList.timings
        holder.days.text = classList.days


    }

    override fun getItemCount(): Int {
        return classes.size
    }

    class CourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val courseName = itemView.findViewById<View>(R.id.class_name) as TextView
        val courseCode = itemView.findViewById<View>(R.id.class_number) as TextView
        val timings = itemView.findViewById<View>(R.id.timing) as TextView
        val days = itemView.findViewById<View>(R.id.days) as TextView
    }

}
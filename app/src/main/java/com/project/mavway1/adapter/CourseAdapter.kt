package com.project.mavway1.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.project.mavway1.R
import com.project.mavway1.models.UserCourses

//maybe change
class CourseAdapter(private val classes : ArrayList<UserCourses>, private val listener: OnCourseClick) : RecyclerView.Adapter<CourseAdapter.CourseViewHolder>()  {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CourseViewHolder {
//        val itemView = View.inflate(parent.context, R.layout.item_class_list, null)
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_class_list, parent, false)
        val viewHolder = CourseViewHolder(itemView)
        itemView.setOnClickListener{
//            val position = itemView.tag as Int
            listener.onItemClick(classes[viewHolder.absoluteAdapterPosition])
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val classList : UserCourses = classes[position]
        holder.courseName.text = classList.course
        holder.courseCode.text = classList.courseCode
        holder.timings.text = classList.timings
        holder.days.text = classList.days

        //make an click listener for holder here


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

//    override fun onItemClick(course: UserCourses) {
//        TODO("Not yet implemented")
//        Toast.makeText(this, course , Toast.LENGTH_SHORT).show()
//    }


}

interface OnCourseClick {
    fun onItemClick(course: UserCourses)

}

package com.project.mavway1.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.project.mavway1.R
import com.project.mavway1.activities.Fragments.MainActivity
import com.project.mavway1.firebase.FireStoreClass
import com.project.mavway1.models.UserCourses

class CourseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course)
        val course = intent.getSerializableExtra("courseobj") as UserCourses

        val courseName = findViewById<TextView>(R.id.classname)
        val timings = findViewById<TextView>(R.id.timings)
        val days = findViewById<TextView>(R.id.days)
        val professor = findViewById<TextView>(R.id.professor)
        val location = findViewById<TextView>(R.id.location)

        val Class = "${course.course} - ${course.courseCode}"
        courseName.text = Class
        timings.text = course.timings
        days.text = course.days
        professor.text = course.professor
        location.text = course.location

        val editbutton = findViewById<TextView>(R.id.editbutton)
        editbutton.setOnClickListener {
            val intent = Intent(this, EditClassActivity::class.java)
            intent.putExtra("courseobj", course)
            startActivity(intent)
        }

        val deletebutton = findViewById<TextView>(R.id.deletebutton)
        deletebutton.setOnClickListener {
            FireStoreClass().editClasses(course, "${course.course}-${course.courseCode}", false)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}
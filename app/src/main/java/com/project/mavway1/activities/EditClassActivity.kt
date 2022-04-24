package com.project.mavway1.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import com.project.mavway1.R
import com.project.mavway1.activities.Fragments.HomeFragment
import com.project.mavway1.activities.Fragments.MainActivity
import com.project.mavway1.firebase.FireStoreClass
import com.project.mavway1.models.UserCourses

class EditClassActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_class)
        val course = intent.getSerializableExtra("courseobj") as UserCourses

        val courseName = findViewById<EditText>(R.id.classname)
        val courseCode = findViewById<EditText>(R.id.classcode)
        val timings = findViewById<EditText>(R.id.timings)
        val days = findViewById<EditText>(R.id.days)
        val professor = findViewById<EditText>(R.id.professor)
        val location = findViewById<EditText>(R.id.location)

//        val Class = "${course.course} - ${course.courseCode}"
        courseName.setText(course.course)
        courseCode.setText(course.courseCode)
        timings.setText(course.timings)
        days.setText(course.days)
        professor.setText(course.professor)
        location.setText(course.location)


        val buttonedit = findViewById<TextView>(R.id.submitbutton)
        buttonedit.setOnClickListener {
            val newcourse = UserCourses(
                courseName.text.toString(),
                courseCode.text.toString(),
                timings.text.toString(),
                days.text.toString(),
                professor.text.toString(),
                location.text.toString()
            )
            FireStoreClass().editClasses(newcourse, "${course.course}-${course.courseCode}", true)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }




//        courseName.text = Class
//        timings.text = course.timings
//        days.text = course.days
//        professor.text = course.professor
//        location.text = course.location
    }
}
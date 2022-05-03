package com.project.mavway1.activities

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
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
        val location = findViewById<AutoCompleteTextView>(R.id.location)

//        val Class = "${course.course} - ${course.courseCode}"
        courseName.setText(course.course)
        courseCode.setText(course.courseCode)
        timings.setText(course.timings)
        days.setText(course.days)
        professor.setText(course.professor)
        location.setText(course.location)

        val arrayofbuilding = makeBuildingArray()

        val adapter: ArrayAdapter<String> = ArrayAdapter(
            this,
            android.R.layout.select_dialog_item,
            arrayofbuilding
        )
        location.threshold = 1;
        location.setAdapter(adapter);
        location.setTextColor(Color.BLACK);


        val buttonedit = findViewById<Button>(R.id.submitbuttom)
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

        val cancelbutton = findViewById<TextView>(R.id.cancel)
        cancelbutton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }



//        courseName.text = Class
//        timings.text = course.timings
//        days.text = course.days
//        professor.text = course.professor
//        location.text = course.location
    }
        fun makeBuildingArray():ArrayList<String>{
            val arrayofbuilding = ArrayList<String>()

            arrayofbuilding.add("Aerodynamics Research Building (ARB)")
            arrayofbuilding.add("Amphibian and Reptile Diversity Research Center (ARC)")
            arrayofbuilding.add("Arlington Regional Data Center (ARDC)")
            arrayofbuilding.add("Bookstore (BOOK)")
            arrayofbuilding.add("Business Building (COBA)")
            arrayofbuilding.add("C.R. Gilstrap Athletic Center (GILS)")
            arrayofbuilding.add("Campus Center (CMPC)")
            arrayofbuilding.add("CAPPA Building (ARCH)")
            arrayofbuilding.add("CAPPA Community Design Lab")
            arrayofbuilding.add("Carlisle Hall (CARH)")
            arrayofbuilding.add("Center for Addiction and Recovery Studies (CARS)")
            arrayofbuilding.add("Center for Entrepreneurship and Economic Innovation (CEEI)")
            arrayofbuilding.add("Chemistry & Physics Building (CPB)")
            arrayofbuilding.add("Civil Engineering Lab Building (CELB)")
            arrayofbuilding.add("College Hall (CH)")
            arrayofbuilding.add("College Park Center (CPC)")
            arrayofbuilding.add("Continuing Ed & Workforce Development (CEWF)")
            arrayofbuilding.add("DED Technical Training Ct. (DE)")
            arrayofbuilding.add("EH. Hereford University Center (UC)")
            arrayofbuilding.add("Earth & Environmental Sciences (EES)")
            arrayofbuilding.add("Engineering Lab Building (ELAB)")
            arrayofbuilding.add("Engineering Research Building (ERB)")
            arrayofbuilding.add("Environmental Health & Safety (EH)")
            arrayofbuilding.add("Environmental Health & Safety (West) (EHW)")
            arrayofbuilding.add("Finance and Administration Annex (Watson building) (FAAA)")
            arrayofbuilding.add("Fine Arts Building (FA)")
            arrayofbuilding.add("Fort Worth Center (UTASF)")
            arrayofbuilding.add("General Academic Classroom Building (GACB)")
            arrayofbuilding.add("Hammond Hall (HH)")
            arrayofbuilding.add("Health Center (HLTH)")
            arrayofbuilding.add("Library (LIBR)")
            arrayofbuilding.add("Library Collection Depository & OIT Office Building (LCDO)")
            arrayofbuilding.add("Life Science Building (LS)")
            arrayofbuilding.add("Maverick Activities Center (MAC)")
            arrayofbuilding.add("Maverick Parking Garage (GARA)")
            arrayofbuilding.add("Maverick Stadium (STAD)")
            arrayofbuilding.add("Military & Veteran Services (VAC)")
            arrayofbuilding.add("Nanofab Building (NANO)")
            arrayofbuilding.add("Natural History Specimen Annex - now the ARC (NHSB)")
            arrayofbuilding.add("Nedderman Hall (NH)")
            arrayofbuilding.add("Parking & Transportation Services (PATS)")
            arrayofbuilding.add("Physical Education (PE)")
            arrayofbuilding.add("Pickard Hall (PKH)")
            arrayofbuilding.add("Preston Hall (PH)")
            arrayofbuilding.add("Ransom Hall (RH)")
            arrayofbuilding.add("Science & Engineering Innovation & Research Building (SEIR)")
            arrayofbuilding.add("Science Hall (SH)")
            arrayofbuilding.add("Smart Hospital (SMART)")
            arrayofbuilding.add("Social Work Complex - A (SWCA)")
            arrayofbuilding.add("Social Work Complex - B (SWCB)")
            arrayofbuilding.add("Student and Administration Building (SAB)")
            arrayofbuilding.add("Studio Arts Center (SAC)")
            arrayofbuilding.add("SWEET Center - now Military & Veteran Services (SWEET)")
            arrayofbuilding.add("Swift Center (SC)")
            arrayofbuilding.add("Tennis Center (TENN)")
            arrayofbuilding.add("Texas Hall (TEX)")
            arrayofbuilding.add("The Commons (COM)")
            arrayofbuilding.add("Thermal Energy Plant (TEP)")
            arrayofbuilding.add("Transforming Lives Child Development Center (DAYC)")
            arrayofbuilding.add("Trimble Hall (TH)")
            arrayofbuilding.add("Trinity Hall (TRN)")
            arrayofbuilding.add("University Administration Building (UA)")
            arrayofbuilding.add("University Hall (UH)")
            arrayofbuilding.add("University Police Department (UPD)")
            arrayofbuilding.add("UT Arlington Research Institute (UTARI)")
            arrayofbuilding.add("W. A. Baker Chemistry Research Building (CRB)")
            arrayofbuilding.add("Wade Building (WDB)")
            arrayofbuilding.add("Wetsel Service Center (WET)")
            arrayofbuilding.add("Woolf Hall (WH)")

            return arrayofbuilding

        }
}
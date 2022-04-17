package com.project.mavway1.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.project.mavway1.R
import com.project.mavway1.activities.Fragments.MainActivity
import com.project.mavway1.firebase.FireStoreClass
import com.project.mavway1.models.User
import com.project.mavway1.utils.Constants

class InformationForm : AppCompatActivity() {

    private lateinit var mUserDetails: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information_form)
        val submitFormButton = findViewById<View>(R.id.button) as Button
        submitFormButton.setOnClickListener {
            UpdateUserDetails()
        }
    }

    private fun UpdateUserDetails()
    {
        val userHashMap = HashMap<String, Any?>()
        var isAnyChange = false
        val name: String = findViewById<EditText>(R.id.enterName).text.toString()
        val major: String = findViewById<EditText>(R.id.enterMajor).text.toString()
        val minor: String = findViewById<EditText>(R.id.enterMinor).text.toString()
        val year: String = findViewById<EditText>(R.id.enterCollegeYear).text.toString()
        val phonenum: String = findViewById<EditText>(R.id.enterPhoneNumber).text.toString()
        val DOB: String = findViewById<EditText>(R.id.enterDOB).text.toString()
        Toast.makeText(this, "Name: $name, Major: $major, Minor: $minor, Year: $year, Phone: $phonenum", Toast.LENGTH_LONG).show()

//        if(mUserDetails.name != name)
//        {
            userHashMap[Constants.NAME] = name
//            isAnyChange = true
//        }
//        if(mUserDetails.major != major)
//        {
            userHashMap[Constants.MAJOR] = major
//            isAnyChange = true
//        }
//        if(mUserDetails.minor != minor)
//        {
            userHashMap[Constants.MINOR] = minor
//            isAnyChange = true
//        }
//        if(mUserDetails.collegeYear != year)
//        {
            userHashMap[Constants.YEAR] = year
//            isAnyChange = true
//        }
//        if(mUserDetails.phoneNum != phonenum)
//        {
            userHashMap[Constants.PHONE] = phonenum
//            isAnyChange = true
//        }
            userHashMap[Constants.DOB] = DOB
//        userHashMap["name"] = mUserDetails.name
//        it was a comment of how we are gonna update the user details.
//            1. take the value you have as a button
//            2. update the value in the hashmap
//            3. update the user details in the firestore
//            4. Check if it is not the same as before
//        if(isAnyChange)
//        {
            FireStoreClass().updateUserDetails(this, userHashMap)
//        }

    }

    //to be implemented
    fun profileUpdatedSuccess() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
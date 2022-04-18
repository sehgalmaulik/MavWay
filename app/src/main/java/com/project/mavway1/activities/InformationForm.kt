package com.project.mavway1.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.project.mavway1.R.*
import com.project.mavway1.activities.Fragments.MainActivity
import com.project.mavway1.firebase.FireStoreClass
import com.project.mavway1.models.User
import com.project.mavway1.utils.Constants


class InformationForm : AppCompatActivity() {

    private lateinit var mUserDetails: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(layout.activity_information_form)

        val submitFormButton = findViewById<View>(id.button) as Button
        submitFormButton.setOnClickListener {
            UpdateUserDetails()
        }
    }


    private fun UpdateUserDetails()
    {
        val userHashMap = HashMap<String, Any?>()
        var isAnyChange = false
        val name: String = findViewById<EditText>(id.enterName).text.toString()
        val major: String = findViewById<EditText>(id.enterMajor).text.toString()
        val minor: String = findViewById<EditText>(id.enterMinor).text.toString()

        val mySpinner = findViewById<View>(id.spCollegeYear) as Spinner
        val year = mySpinner.selectedItem.toString()

        val phonenum: String = findViewById<EditText>(id.enterPhoneNumber).text.toString()
        val DOB: String = findViewById<EditText>(id.enterDOB).text.toString()
        Toast.makeText(this, "Name: $name, Major: $major, Minor: $minor, Year: $year, Phone: $phonenum", Toast.LENGTH_LONG).show()


            userHashMap[Constants.NAME] = name
            userHashMap[Constants.MAJOR] = major
            userHashMap[Constants.MINOR] = minor
            userHashMap[Constants.YEAR] = year
            userHashMap[Constants.PHONE] = phonenum
            userHashMap[Constants.DOB] = DOB
            FireStoreClass().updateUserDetails(this, userHashMap)

    }

    //to be implemented
    fun profileUpdatedSuccess() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
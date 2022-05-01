package com.project.mavway1.activities

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.project.mavway1.R
import com.project.mavway1.R.*
import com.project.mavway1.activities.Fragments.MainActivity
import com.project.mavway1.firebase.FireStoreClass
import com.project.mavway1.models.User
import com.project.mavway1.utils.Constants
import java.io.File
import java.util.*
import kotlin.collections.HashMap


class InformationForm : AppCompatActivity() {

    private lateinit var mUserDetails: User
    private lateinit var imageUri: Uri
    val database = Firebase.database.reference
    val uid = FireStoreClass().getCurrentUserId()
    var photoselectedflag = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(layout.activity_information_form)

        // code for date picker
        val dobPicker : TextView = findViewById(id.enterDOB)
        val cal : Calendar = Calendar.getInstance()
        val dob_year = cal.get(Calendar.YEAR)
        val dob_month = cal.get(Calendar.MONTH)
        val dob_date =cal.get(Calendar.DAY_OF_MONTH)

        val profile_picture = findViewById<ImageView>(R.id.profile_photo)
        val edit_photo = findViewById<ImageButton>(R.id.edit_profile_photo)

        dobPicker.setOnClickListener{

            val dpd = DatePickerDialog(this,DatePickerDialog.OnDateSetListener { datePicker, year, month, dayOfMonth ->
                val m = month+1
                val temp : String = "$m/$dayOfMonth/$year"
            dobPicker.text=(temp)

            },dob_year,dob_month,dob_date)
            dpd.show()
        }
        val getImage = registerForActivityResult(
            ActivityResultContracts.GetContent(),
            ActivityResultCallback {
                profile_picture.setImageURI(it)
                if (it != null) {
                    imageUri = it
                }
            }
        )
        edit_photo.setOnClickListener{

            getImage.launch("image/*")
            photoselectedflag = 1
        }

        // code for submitting user details
        val submitFormButton = findViewById<View>(id.button) as Button
        submitFormButton.setOnClickListener {
            UpdateUserDetails()
        }
        val storageRef = uid?.let { FirebaseStorage.getInstance().reference.child(it) }
        val localfile : File = File.createTempFile("tempfile",".jpg")

        storageRef?.getFile(localfile)?.addOnSuccessListener {
            val bitmap: Bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
            //val rotatedBitmap = bitmap.rotate(90f)

            profile_picture.setImageBitmap(bitmap)
        }


    }


    private fun UpdateUserDetails()
    {
        val userHashMap = HashMap<String, Any?>()
        var isAnyChange = false
        val name: String = findViewById<EditText>(id.enterName).text.toString()
//        val major: String = findViewById<EditText>(id.enterMajor).text.toString()
        val minor: String = findViewById<EditText>(id.enterMinor).text.toString()

        val mySpinner = findViewById<View>(id.spCollegeYear) as Spinner
        val year = mySpinner.selectedItem.toString()

        val mySpinnerMajor = findViewById<View>(id.spMajor) as Spinner
        val major = mySpinnerMajor.selectedItem.toString()

        val phonenum: String = findViewById<EditText>(id.enterPhoneNumber).text.toString()
        val DOB: String = findViewById<TextView>(id.enterDOB).text.toString()
        Toast.makeText(this, "Name: $name, Major: $major, Minor: $minor, Year: $year, Phone: $phonenum", Toast.LENGTH_LONG).show()


            userHashMap[Constants.NAME] = name
            userHashMap[Constants.MAJOR] = major
            userHashMap[Constants.MINOR] = minor
            userHashMap[Constants.YEAR] = year
            userHashMap[Constants.PHONE] = phonenum
            userHashMap[Constants.DOB] = DOB
            FireStoreClass().updateUserDetails(this, userHashMap)

        if(photoselectedflag == 1)
        {
            uploadImage()
        }

    }

    //to be implemented
    fun profileUpdatedSuccess() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun uploadImage() {

        //get uid and set it to this variable
        val uid = FireStoreClass().getCurrentUserId()

        var imageurl : String

        val storageRef = uid?.let { FirebaseStorage.getInstance().reference.child(it) }

        if (storageRef != null) {
            storageRef.putFile(imageUri).addOnSuccessListener {
                Toast.makeText(this,"Profile Photo uploaded",Toast.LENGTH_LONG).show()

            }.addOnFailureListener{
                Toast.makeText(this,"Profile Photo not uploaded",Toast.LENGTH_LONG).show()
            }
        }

    }
}
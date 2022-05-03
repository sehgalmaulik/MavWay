package com.project.mavway1.activities

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.project.mavway1.R
import com.project.mavway1.activities.Fragments.MainActivity
import com.project.mavway1.activities.Fragments.SettingsFragment
import com.project.mavway1.firebase.FireStoreClass
import com.project.mavway1.models.User
import com.project.mavway1.utils.Constants
import kotlinx.coroutines.tasks.await
import java.io.File
import java.util.*


class EditProfilePage() : AppCompatActivity() {

    private lateinit var imageUri: Uri
    val database = Firebase.database.reference
    val uid = FireStoreClass().getCurrentUserId()
    var photoselectedflag = 0
    lateinit var link : String




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile_page)

        val userObj = intent.getSerializableExtra("userobj") as User
        val enterName = findViewById<androidx.appcompat.widget.AppCompatEditText>(R.id.enterName)
        val enterPhoneNumber = findViewById<androidx.appcompat.widget.AppCompatEditText>(R.id.enterPhoneNumber)
        val enterDOB = findViewById<TextView>(R.id.enterDOB)
        val spCollegeYear = findViewById<Spinner>(R.id.spCollegeYear)
        val spMajor = findViewById<Spinner>(R.id.spMajor)
        val enterMinor = findViewById<androidx.appcompat.widget.AppCompatEditText>(R.id.enterMinor)
        val cancel_bt = findViewById<Button>(R.id.cancel_button)
        val submit_bt =findViewById<Button>(R.id.submit_button)


        var name : String
        var phoneNum :String
        var dob :String
        var collegeYear : String
        var major : String
        var minor : String


        val mj = userObj.major
        enterName.setText(userObj.name)
        enterPhoneNumber.setText(userObj.phoneNum)
        enterDOB.text = userObj.dob
        userObj.collegeYear?.let { returnItemIndex(it) }?.let { spCollegeYear.setSelection(it) }
//        spMajor.setSelection(userObj.major)
        enterMinor.setText(userObj.minor)
        link = userObj.image.toString()



        // code for date picker

        val cal : Calendar = Calendar.getInstance()
        val dob_year = cal.get(Calendar.YEAR)
        val dob_month = cal.get(Calendar.MONTH)
        val dob_date =cal.get(Calendar.DAY_OF_MONTH)

        enterDOB.setOnClickListener{

            val dpd = DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener { datePicker, year, month, dayOfMonth ->
                val m = month+1
                val temp : String = "$m/$dayOfMonth/$year"
                    enterDOB.text=(temp)

            },dob_year,dob_month,dob_date)
            dpd.show()
        }


        mj?.let { selectSpinnerValue(spMajor, it) }


        cancel_bt.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }


        val profile_picture = findViewById<ImageView>(R.id.profile_photo)
        val uid = FireStoreClass().getCurrentUserId()

        val storageRef = uid?.let { FirebaseStorage.getInstance().reference.child(it) }
        val localfile : File = File.createTempFile("tempfile",".jpg")

        storageRef?.getFile(localfile)?.addOnSuccessListener {
            val bitmap: Bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
            //val rotatedBitmap = bitmap.rotate(90f)

            profile_picture.setImageBitmap(bitmap)
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
        val edit_photo = findViewById<ImageButton>(R.id.edit_profile_photo)

        edit_photo.setOnClickListener{

            getImage.launch("image/*")
            photoselectedflag = 1
        }
        submit_bt.setOnClickListener {
             name = enterName.text.toString()
             phoneNum = enterPhoneNumber.text.toString()
             dob = enterDOB.text.toString()
             collegeYear = spCollegeYear.selectedItem.toString()
             major = spMajor.selectedItem.toString()
             minor = enterMinor.text.toString()

            if(photoselectedflag == 1)
            {
               uploadImage()
            }

           Log.d("imageurl", link)

            val user = User(userObj.id, userObj.email , name, phoneNum, dob, major, minor, collegeYear, link)

            FireStoreClass().updateUser(user)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }




    fun Bitmap.rotate(degrees: Float): Bitmap {
        val matrix = Matrix().apply { postRotate(degrees) }
        return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
    }

    private fun uploadImage() {

        //get uid and set it to this variable
        val uid = FireStoreClass().getCurrentUserId()



        val storageRef = uid?.let { FirebaseStorage.getInstance().reference.child(it) }

        storageRef?.putFile(imageUri)?.addOnSuccessListener {
            Toast.makeText(this,"Profile Photo uploaded",Toast.LENGTH_LONG).show()
            val result = it.metadata!!.reference!!.downloadUrl
            result.addOnSuccessListener {
                Log.d("imageurl",it.toString())
                FireStoreClass().updatelink(it.toString())


            }

        }?.addOnFailureListener{
            Toast.makeText(this,"Profile Photo not uploaded",Toast.LENGTH_LONG).show()
        }

    }


    fun getCurrentUserId(): String? {
        return Firebase.auth.currentUser?.uid
    }


    fun returnItemIndex(collegeYear: String): Int
    {
        var itemindex = 0

        if(collegeYear == "Freshman"){
            itemindex = 0
        }
        else if(collegeYear == "Sophomore"){
            itemindex = 1
        }
        else if(collegeYear == "Junior"){
            itemindex = 2
        }
        else if(collegeYear == "Senior"){
            itemindex = 3
        }
        return itemindex
    }

    private fun selectSpinnerValue(spinner: Spinner, myString: String) {
        val index = 0
        for (i in 0 until spinner.count) {
            if (spinner.getItemAtPosition(i).toString() == myString) {
                spinner.setSelection(i)
                break
            }
        }
    }


}




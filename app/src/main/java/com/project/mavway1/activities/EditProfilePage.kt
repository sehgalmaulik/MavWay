package com.project.mavway1.activities

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

import com.project.mavway1.R
import com.project.mavway1.activities.Fragments.SettingsFragment
import com.project.mavway1.firebase.FireStoreClass


class EditProfilePage : AppCompatActivity() {

    lateinit var imageUri: Uri
    val database = Firebase.database.reference

    val uid = FireStoreClass().getCurrentUserId()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile_page)

        val cancel_bt = findViewById<Button>(R.id.cancel_button)
        val profile_picture = findViewById<ImageView>(R.id.profile_photo)
        val getImage = registerForActivityResult(
            ActivityResultContracts.GetContent(),
            ActivityResultCallback {
                profile_picture.setImageURI(it)
                if (it != null) {
                    imageUri = it
                }

            }
        )



        cancel_bt.setOnClickListener{
            val intent = Intent(this, SettingsFragment::class.java)
            startActivity(intent)
        }

        val submit_bt =findViewById<Button>(R.id.submit_button)

        submit_bt.setOnClickListener{

            // update firestore details

            uploadImage()
        }

        val edit_photo = findViewById<ImageButton>(R.id.edit_profile_photo)

        edit_photo.setOnClickListener{

            getImage.launch("image/*")
        }


    }

    private fun uploadImage() {

        //get uid and set it to this variable
        val uid = "maulik"

        var imageurl : String

        val storageRef = FirebaseStorage.getInstance().reference.child(uid)

        storageRef.putFile(imageUri).addOnSuccessListener {
            Toast.makeText(this,"Profile Photo uploaded",Toast.LENGTH_LONG).show()




        }.addOnFailureListener{
            Toast.makeText(this,"Profile Photo not uploaded",Toast.LENGTH_LONG).show()
        }

    }


}
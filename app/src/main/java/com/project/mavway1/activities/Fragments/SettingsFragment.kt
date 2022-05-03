package com.project.mavway1.activities.Fragments

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.project.mavway1.R
import com.project.mavway1.activities.EditProfilePage
import com.project.mavway1.activities.StartScreen
import com.project.mavway1.firebase.FireStoreClass
import com.project.mavway1.models.User
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import java.io.File


class SettingsFragment : Fragment() {

//    private lateinit var mUser: User
    private lateinit var db : FirebaseFirestore
    private var userAccess: User = User()
    private lateinit var auth: FirebaseAuth
    private lateinit var editProfileButton: Button
    private lateinit var signoutButton: Button



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view :View = inflater.inflate(R.layout.fragment_settings, container, false)
        auth = Firebase.auth
        signoutButton = view.findViewById(R.id.signout_button)
        editProfileButton = view.findViewById(R.id.edit_profile_button)
        val pic = view.findViewById<ImageView>(R.id.profile_photo)

        getUserDetails()



        val uid = FireStoreClass().getCurrentUserId()

        val storageRef = uid?.let { FirebaseStorage.getInstance().reference.child(it) }
        val localfile : File = File.createTempFile("tempfile",".jpg")

        storageRef?.getFile(localfile)?.addOnSuccessListener {
            val bitmap: Bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
            //val rotatedBitmap = bitmap.rotate(90f)

            pic.setImageBitmap(bitmap)
        }


        signoutButton.setOnClickListener{

            FirebaseAuth.getInstance().signOut();
            updateUI(auth.currentUser)

        }

        return view
    }

    override fun onResume() {
        super.onResume()
        //Toast.makeText(context, "onResume", Toast.LENGTH_SHORT).show()
        getUserDetails()

    }

    private fun updateUI(user: FirebaseUser?) {
            val intent = Intent(activity,StartScreen::class.java)
            startActivity(intent)


    }
//    fun Bitmap.rotate(degrees: Float): Bitmap {
//        val matrix = Matrix().apply { postRotate(degrees) }
//        return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
//    }


    @OptIn(DelicateCoroutinesApi::class)
    fun getUserDetails()
    {

        GlobalScope.launch {
            userAccess = FireStoreClass().getUser().await().toObject(User::class.java)!!
            val userName = view?.findViewById<TextView>(R.id.enterName)
            val phone = view?.findViewById<TextView>(R.id.enterPhoneNumber)
            val dob = view?.findViewById<TextView>(R.id.enterDOB)
            val collegeYear = view?.findViewById<TextView>(R.id.spCollegeYear)
            val major = view?.findViewById<TextView>(R.id.spMajor)
            val minor = view?.findViewById<TextView>(R.id.enterMinor)
            val image = view?.findViewById<ImageView>(R.id.profile_photo)

            withContext(Dispatchers.Main) {
                if (userName != null) {
                    userName.text = userAccess.name
                }
                if (phone != null) {
                    phone.text = userAccess.phoneNum
                }
                if (dob != null) {
                    dob.text = userAccess.dob

                }
                if (collegeYear != null) {
                    collegeYear.text = userAccess.collegeYear
                }
                if (major != null) {
                    major.text = userAccess.major
                }
                if (minor != null) {
                    minor.text = userAccess.minor
                }
                if (image != null) {
                    activity?.let { Glide.with(it).load(userAccess.image).into(image) }

                }

            }

            editProfileButton.setOnClickListener{
                val intent = Intent(activity ,EditProfilePage::class.java)
                Log.d("user",userAccess.toString())
                intent.putExtra("userobj",userAccess)
                startActivity(intent)
            }




        }


    }


}

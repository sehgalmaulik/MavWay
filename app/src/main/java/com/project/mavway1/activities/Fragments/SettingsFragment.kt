package com.project.mavway1.activities.Fragments

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
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
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.io.File

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SettingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
//    private lateinit var mUser: User
    private lateinit var db : FirebaseFirestore
    private var userAccess: User = User()
    private lateinit var auth: FirebaseAuth
    private lateinit var editProfileButton: Button
    private lateinit var signoutButton: Button
//    val editProfileButton = view.findViewById<Button>(R.id.edit_profile_button)
//    val signoutButton = view.findViewById<Button>(R.id.signout_button)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        auth = Firebase.auth
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view :View = inflater.inflate(R.layout.fragment_settings, container, false)

        signoutButton = view.findViewById(R.id.signout_button)
        editProfileButton = view.findViewById(R.id.edit_profile_button)
        val pic = view.findViewById<ImageView>(R.id.profile_photo)

        getUserDetails()



        val uid = FireStoreClass().getCurrentUserId()

        val storageRef = uid?.let { FirebaseStorage.getInstance().reference.child(it) }
        val localfile : File = File.createTempFile("tempfile",".jpg")

        storageRef?.getFile(localfile)?.addOnSuccessListener {
            val bitmap: Bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
            pic.setImageBitmap(bitmap)
        }

//        signoutButton.setOnClickListener{
//
//            FirebaseAuth.getInstance().signOut();
//    //            Toast.makeText(context,"Signing out $userName",Toast.LENGTH_LONG).show()
//            updateUI(auth.currentUser)
//
//        }



        return view
    }
    private fun updateUI(user: FirebaseUser?) {
        if(user==null){
            val intent = Intent(activity,StartScreen::class.java)
            startActivity(intent)
        }

    }

    @OptIn(DelicateCoroutinesApi::class)
    fun getUserDetails()
    {
        GlobalScope.launch {
            userAccess = FireStoreClass().getUser().await().toObject(User::class.java)!!
            Log.d("user",userAccess.toString())
//            Toast.makeText(context, userAccess.name, Toast.LENGTH_SHORT).show()
            val userName = view?.findViewById<TextView>(R.id.enterName)
            val phone = view?.findViewById<TextView>(R.id.enterPhoneNumber)
            val dob = view?.findViewById<TextView>(R.id.enterDOB)
            val collegeYear = view?.findViewById<TextView>(R.id.spCollegeYear)
            val major = view?.findViewById<TextView>(R.id.spMajor)
            val minor = view?.findViewById<TextView>(R.id.enterMinor)
            userAccess.dob?.let { Log.d("DOB", it) }
            userAccess.image?.let { Log.d("Image", it) }
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

            editProfileButton.setOnClickListener{
                val intent = Intent(activity ,EditProfilePage::class.java)
                Log.d("user",userAccess.toString())
                intent.putExtra("userobj",userAccess)
                startActivity(intent)
            }

            signoutButton.setOnClickListener{

                FirebaseAuth.getInstance().signOut();
                //            Toast.makeText(context,"Signing out $userName",Toast.LENGTH_LONG).show()
                updateUI(auth.currentUser)

            }


        }


    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SettingsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SettingsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }



}

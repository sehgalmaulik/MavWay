package com.project.mavway1.activities.Fragments

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.project.mavway1.R
import com.project.mavway1.activities.EditProfilePage
import com.project.mavway1.activities.StartScreen
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

    private lateinit var auth: FirebaseAuth


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
    ): View? {
        // Inflate the layout for this fragment

        val view :View = inflater.inflate(R.layout.fragment_settings, container, false)

        val signoutButton = view.findViewById<Button>(R.id.signout_button)
        val editProfileButton = view.findViewById<Button>(R.id.edit_profile_button)
        val userName = view.findViewById<TextView>(R.id.enterName).toString()

        val pic = view.findViewById<ImageView>(R.id.profile_photo)

        val uid = "maulik"

        val storageRef = FirebaseStorage.getInstance().reference.child(uid)

        val localfile : File = File.createTempFile("tempfile",".jpg")

        storageRef.getFile(localfile).addOnSuccessListener {
            val bitmap: Bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
            pic.setImageBitmap(bitmap)

        }


        signoutButton?.setOnClickListener{

            FirebaseAuth.getInstance().signOut();
            Toast.makeText(context,"Signing out $userName",Toast.LENGTH_LONG).show()
            updateUI(auth.currentUser)

        }

        editProfileButton.setOnClickListener{

            val intent = Intent(activity,EditProfilePage::class.java)
            startActivity(intent)
        }






        return view
    }
    private fun updateUI(user: FirebaseUser?) {
        val intent = Intent(activity, StartScreen::class.java)
        startActivity(intent)
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
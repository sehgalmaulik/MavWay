package com.project.mavway1.firebase

import android.util.Log
import android.widget.Toast
import androidx.constraintlayout.helper.widget.MotionEffect.TAG
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.project.mavway1.activities.InformationForm
import com.project.mavway1.activities.RegisterUser
import com.project.mavway1.models.User
import com.project.mavway1.utils.Constants

class FireStoreClass {
    private val db = Firebase.firestore

    fun registerUser(activity: RegisterUser, userinfo: User?)
    {
        getCurrentUserId()?.let {
            if (userinfo != null) {
                db.collection(Constants.USERS).document(it).set(userinfo, SetOptions.merge()).addOnSuccessListener {
                    activity.userRegisteredSuccess()
                }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Error adding document", e)
                        Toast.makeText(activity, "Error adding document", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }

    fun updateUserDetails(activity: InformationForm, userHashMap: HashMap<String, Any?>)
    {
//        db.collection(Constants.USERS).document(getCurrentUserId()!!).update(userHashMap).addOnSuccessListener {
//            Log.i(activity.javaClass.simpleName,"Successfully updated user details")
//            Toast.makeText(activity, "Successfully updated user details", Toast.LENGTH_SHORT).show()
//            activity.profileUpdatedSuccess()
//        }
//            .addOnFailureListener { e ->
//                Log.w(TAG, "Error adding document", e)
//                Toast.makeText(activity, "Error adding document", Toast.LENGTH_SHORT).show()
//            }
//        database = Firebase.database.reference
//        val key = database.child("posts").push().key
//        if (key == null) {
//            Log.w(TAG, "Couldn't get push key for posts")
//            Toast.makeText(activity, "Couldn't get push key for posts", Toast.LENGTH_SHORT).show()
//            return
//        }
//        database.updateChildren(userHashMap)
//        Toast.makeText(activity, "Successfully updated user details", Toast.LENGTH_SHORT).show()
//        activity.profileUpdatedSuccess()

        db.collection(Constants.USERS).document(getCurrentUserId()!!)
            .update(userHashMap).addOnSuccessListener {
                Log.i(activity.javaClass.simpleName,"Successfully updated user details")
                Toast.makeText(activity, "Successfully updated user details", Toast.LENGTH_SHORT).show()
                activity.profileUpdatedSuccess()
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
                Toast.makeText(activity, "Error adding document", Toast.LENGTH_SHORT).show()
            }

    }

    private fun getCurrentUserId(): String? {
        return Firebase.auth.currentUser?.uid
    }

}
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
import com.project.mavway1.activities.EditClassActivity
import com.project.mavway1.activities.InformationForm
import com.project.mavway1.activities.RegisterUser
import com.project.mavway1.models.User
import com.project.mavway1.models.UserCourses
import com.project.mavway1.utils.Constants

class FireStoreClass {
    private val db = Firebase.firestore
    private val classCollection = db.collection(Constants.USERS)
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

    fun editClasses(course: UserCourses, prevName: String, flag: Boolean)
    {
        Log.w(TAG, "editClasses: $course $prevName")
        classCollection.document(getCurrentUserId()!!).collection(Constants.CLASSES).document(
            prevName
        ).delete()
            .addOnSuccessListener {
                if(flag)
                    classCollection.document(getCurrentUserId()!!).collection(Constants.CLASSES).document("${course.course}-${course.courseCode}").set(course)
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }

    }

//    fun updateClasses(activity: HomeDialogue , userHashMap: HashMap<String, ArrayList<String>>)
    fun updateClasses(classCode: String, classNum: String, time: String, day: String)
    {


//        db.collection(Constants.CLASSES).document(getCurrentUserId()!!).update(userHashMap as Map<String, Any>).addOnSuccessListener {
//                    Log.i(activity.javaClass.simpleName,"Successfully updated user details")
//                }
//                .addOnFailureListener { e ->
//                    Log.w(TAG, "Error adding document", e)
//                }
        val userClasses = UserCourses(classCode, classNum, time, day)
        classCollection.document(getCurrentUserId()!!).collection(Constants.CLASSES).document("$classCode-$classNum").set(userClasses)
    }

    private fun getCurrentUserId(): String? {
        return Firebase.auth.currentUser?.uid
    }




}





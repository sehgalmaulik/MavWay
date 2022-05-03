package com.project.mavway1.firebase

import android.util.Log
import android.widget.Toast
import androidx.constraintlayout.helper.widget.MotionEffect.TAG
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.project.mavway1.activities.EditClassActivity
import com.project.mavway1.activities.InformationForm
import com.project.mavway1.activities.RegisterUser
import com.project.mavway1.models.Tasks
import com.project.mavway1.models.User
import com.project.mavway1.models.UserCourses
import com.project.mavway1.utils.Constants
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.*
import kotlin.collections.HashMap


class FireStoreClass() {
    private lateinit var mUser: User
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
    fun updateClasses(classCode: String, classNum: String, time: String, day: String, prof: String, location: String)
    {
        val userClasses = UserCourses(classCode, classNum, time, day, prof, location)
        classCollection.document(getCurrentUserId()!!).collection(Constants.CLASSES).document("$classCode-$classNum").set(userClasses)
    }

    fun getUser(): Task<DocumentSnapshot>
    {
        return classCollection.document(getCurrentUserId()!!).get()
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun updateUser(user: User)
    {
        GlobalScope.launch {
            classCollection.document(getCurrentUserId()!!).set(user).await()
        }

    }

    fun getCurrentUserId(): String? {
        return Firebase.auth.currentUser?.uid
    }

    fun addTask(task: String) {
        //generate a random uid for firebase
        val uid = UUID.randomUUID().toString()

        classCollection.document(getCurrentUserId()!!).collection(Constants.TASKS).document(uid).set(
            Tasks(task, uid)
        )


    }

    fun deleteTask(task: Tasks) {
        classCollection.document(getCurrentUserId()!!).collection(Constants.TASKS).document(task.uid).delete()


    }

    fun updatelink(s: String) {

        classCollection.document(getCurrentUserId()!!).update(mapOf(
            "image" to s
        ))

    }


}







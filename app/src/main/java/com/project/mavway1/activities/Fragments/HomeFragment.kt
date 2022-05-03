package com.project.mavway1.activities.Fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.project.mavway1.R
import com.project.mavway1.activities.CourseActivity
import com.project.mavway1.activities.HomeDialogue
import com.project.mavway1.adapter.CourseAdapter
import com.project.mavway1.adapter.OnCourseClick
import com.project.mavway1.firebase.FireStoreClass
import com.project.mavway1.models.User
import com.project.mavway1.models.UserCourses
import com.project.mavway1.utils.Constants
import java.io.File
import java.util.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment(), OnCourseClick{

    private lateinit var recyclerView: RecyclerView
    private lateinit var classesArrayList: ArrayList<UserCourses>
    private lateinit var adapter: CourseAdapter
    private lateinit var db: FirebaseFirestore
    private lateinit var userObj: User

   @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        val addbutton = view.findViewById<Button>(R.id.add_class_button)
        val name = view.findViewById<TextView>(R.id.name_holder)
        val collegeYear = view.findViewById<TextView>(R.id.college_year_holder)
        val major = view.findViewById<TextView>(R.id.major_holder)
        db = FirebaseFirestore.getInstance()

        val docRef = db.collection(Constants.USERS).document(Firebase.auth.currentUser?.uid.toString())
        docRef.get().addOnSuccessListener { documentSnapshot ->
            userObj = documentSnapshot.toObject<User>()!!
            name.text = userObj.name.toString().split(" ").first()
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
            collegeYear.text = userObj.collegeYear
            major.text = userObj.major
            //changed
            val pic = view.findViewById<ImageView>(R.id.profile_photo)
            Glide.with(this).load(userObj.image).into(pic)
            Log.i("imageurl", userObj.image.toString())
        }

        addbutton.setOnClickListener{
            openDialog()
        }

        recyclerView = view.findViewById(R.id.class_list)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        classesArrayList = arrayListOf()
        adapter = CourseAdapter(classesArrayList, this)
        recyclerView.adapter = adapter

        eventChangeListener()


        return view
    }


     fun eventChangeListener(){
        db = FirebaseFirestore.getInstance()
        Firebase.auth.currentUser?.let {
            db.collection(Constants.USERS).document(it.uid).collection(Constants.CLASSES).addSnapshotListener(object : EventListener<QuerySnapshot>{
                @SuppressLint("NotifyDataSetChanged")
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    if(error != null){
                        Toast.makeText(activity, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
                        Log.e("Error", error.message.toString())
                        return
                }
                    for(dc: DocumentChange in value?.documentChanges!!)
                    {
                        if(dc.type == DocumentChange.Type.ADDED){
                            classesArrayList.add(dc.document.toObject(UserCourses::class.java))
//                            refreshFragment()
                        }
                    }
                    adapter.notifyDataSetChanged()
//                    eventChangeListener()
                }
            })


        }

    }



    private fun openDialog() {
        val exampleDialog = HomeDialogue()
        exampleDialog.show(parentFragmentManager, "example dialog")
    }


    override fun onItemClick(course: UserCourses) {
        val intent = Intent(activity, CourseActivity::class.java)
        intent.putExtra("courseobj", course)
        startActivity(intent)
    }


}


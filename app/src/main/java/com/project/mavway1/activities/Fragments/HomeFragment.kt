package com.project.mavway1.activities.Fragments

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.DataCollectionDefaultChange
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.project.mavway1.R
import com.project.mavway1.activities.CourseActivity
import com.project.mavway1.activities.HomeDialogue
import com.project.mavway1.adapter.CourseAdapter
import com.project.mavway1.adapter.OnCourseClick
import com.project.mavway1.firebase.FireStoreClass
import com.project.mavway1.models.User
import com.project.mavway1.models.UserCourses
import com.project.mavway1.utils.Constants
import java.time.LocalTime

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment(), OnCourseClick{
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var recyclerView: RecyclerView
    private lateinit var classesArrayList: ArrayList<UserCourses>
    private lateinit var adapter: CourseAdapter
    private lateinit var db: FirebaseFirestore
    private lateinit var userObj: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

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
            name.text = userObj.name
            collegeYear.text = userObj.collegeYear
            major.text = userObj.major

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


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragement.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onItemClick(course: UserCourses) {
        val intent = Intent(activity, CourseActivity::class.java)
        intent.putExtra("courseobj", course)
        startActivity(intent)
    }



    //write a function to refresh the fragment

    //function to reload the data of the fragment
//    fun reloadData(){
//        classesArrayList.clear()
//        adapter.notifyDataSetChanged()
//        eventChangeListener()
//    }



}


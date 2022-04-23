package com.project.mavway1.activities.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.DataCollectionDefaultChange
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.*
import com.google.firebase.ktx.Firebase
import com.project.mavway1.R
import com.project.mavway1.activities.HomeDialogue
import com.project.mavway1.adapter.CourseAdapter
import com.project.mavway1.firebase.FireStoreClass
import com.project.mavway1.models.UserCourses
import com.project.mavway1.utils.Constants

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment(){
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var recyclerView: RecyclerView
    private lateinit var classesArrayList: ArrayList<UserCourses>
    private lateinit var adapter: CourseAdapter
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        val addbutton = view.findViewById<Button>(R.id.add_class_button)
        addbutton.setOnClickListener{
            openDialog()

        }

        recyclerView = view.findViewById(R.id.class_list)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        classesArrayList = arrayListOf()
        adapter = CourseAdapter(classesArrayList)
        recyclerView.adapter = adapter

        eventChangeListener()


        return view
    }

    private fun eventChangeListener(){
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
                        }
                    }
                    adapter.notifyDataSetChanged()
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
}


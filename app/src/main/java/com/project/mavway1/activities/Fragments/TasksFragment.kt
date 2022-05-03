package com.project.mavway1.activities.Fragments

import android.annotation.SuppressLint
import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.*
import com.google.firebase.ktx.Firebase
import com.project.mavway1.R
import com.project.mavway1.activities.CourseActivity
import com.project.mavway1.adapter.CourseAdapter
import com.project.mavway1.adapter.OnTaskClick
import com.project.mavway1.adapter.TasksRVAdapter
import com.project.mavway1.firebase.FireStoreClass
import com.project.mavway1.models.Tasks
import com.project.mavway1.models.UserCourses
import com.project.mavway1.popup.TaskPopUp
import com.project.mavway1.utils.Constants
import java.util.ArrayList

//import com.project.mavway1.adapter.ITasksRVAdapter
//import com.project.mavway1.adapter.TasksRVAdapter
//import com.project.mavway1.models.Tasks
//import com.project.mavway1.models.TasksViewModel


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TasksFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TasksFragment : Fragment(), OnTaskClick {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TasksRVAdapter
    private lateinit var db: FirebaseFirestore
    private lateinit var taskList: ArrayList<Tasks>




//    private lateinit var viewModel: TasksViewModel

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
        val view: View = inflater.inflate(R.layout.fragment_tasks, container, false)

        val popupButton = view.findViewById<Button>(R.id.addTask)
        popupButton?.setOnClickListener(){
            openPopup()
        }

        recyclerView = view.findViewById(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        taskList = arrayListOf()
        adapter = TasksRVAdapter(taskList, this)
        recyclerView.adapter = adapter
        eventChangeListenerTask()
        return view
    }

    private fun openPopup() {
        val popup = TaskPopUp()
        popup.show(parentFragmentManager, "popup")



    }

    private fun eventChangeListenerTask(){
        db = FirebaseFirestore.getInstance()
        Firebase.auth.currentUser?.let {
            db.collection(Constants.USERS).document(it.uid).collection(Constants.TASKS).addSnapshotListener(object :
                EventListener<QuerySnapshot> {
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
                            taskList.add(dc.document.toObject(Tasks::class.java))
//                            refreshFragment()
                        }
                    }
                    adapter.notifyDataSetChanged()
//                    eventChangeListener()
                }
            })


        }
    }

    override fun onItemClick(task: Tasks) {
//        val intent = Intent(activity, CourseActivity::class.java)
//        intent.putExtra("courseobj", course)
//        startActivity(intent)
//        val ref =
//            FireStoreClass().getCurrentUserId()
//                ?.let { db.collection(Constants.USERS).document(it).collection(Constants.TASKS).document() }
//        val documentId = ref?.id
//        val ref = Firebase.auth.currentUser?.let {
//            db.collection(Constants.USERS).document(it.uid).collection(Constants.TASKS).document(task.uid).del
//        }
//
//        val buttondeltask = view?.findViewById<ImageButton>(R.id.deleteTask)
//        buttondeltask?.setOnClickListener(){
            FireStoreClass().deleteTask(task)
            taskList.remove(task)
            adapter.notifyDataSetChanged()

//        }


    }


//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        val view: View? = inflater.inflate(R.layout.fragment_tasks, container, false)
//
//        //mayble problem
//        val Rview = view?.findViewById<RecyclerView>(R.id.recyclerview)
//        //was this
//        if (Rview != null) {
//            val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity)
//            Rview.layoutManager = layoutManager;
//        }
//        //was this
//        val adapter = activity?.let { TasksRVAdapter(it, this) }
//        Rview?.adapter = adapter
//
////        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application = Application()))[TasksViewModel::class.java]
//        viewModel = ViewModelProvider(this)[TasksViewModel::class.java]
//        viewModel.allTasks.observe(viewLifecycleOwner) {
//            //update UIr
//            adapter?.updateTasks(it)
//        }
//        val button = view?.findViewById<android.widget.Button>(R.id.submittask)
//        button?.setOnClickListener{
////            val inputtext = view.findViewById<EditText>(R.id.input).toString()
//            val inputtext1: EditText? = view.findViewById(R.id.input)
//            val inputtext = inputtext1?.text.toString()
//            if(inputtext.isNotEmpty()){
//                viewModel.insertTask(Tasks(inputtext))
//                Toast.makeText(context, "Task Added", Toast.LENGTH_SHORT).show()
//            }
//            inputtext1?.setText("")
//
//
//        }
//
//        return view
//    }

//    override fun onItemClick(tasks: Tasks) {
//        viewModel.deleteTask(tasks)
//        Toast.makeText(context, "Task Deleted", Toast.LENGTH_SHORT).show()
//    }




    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TasksFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TasksFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


}
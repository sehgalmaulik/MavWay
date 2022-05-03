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


class TasksFragment : Fragment(), OnTaskClick {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TasksRVAdapter
    private lateinit var db: FirebaseFirestore
    private lateinit var taskList: ArrayList<Tasks>


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

            FireStoreClass().deleteTask(task)
            taskList.remove(task)
            adapter.notifyDataSetChanged()

    }







}
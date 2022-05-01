package com.project.mavway1.activities.Fragments

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.mavway1.R
import com.project.mavway1.adapter.ITasksRVAdapter
import com.project.mavway1.adapter.TasksRVAdapter
import com.project.mavway1.models.Tasks
import com.project.mavway1.models.TasksViewModel


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TasksFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TasksFragment : Fragment(), ITasksRVAdapter {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var viewModel: TasksViewModel

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
    ): View? {
        // Inflate the layout for this fragment
        val view: View? = inflater.inflate(R.layout.fragment_tasks, container, false)

        //mayble problem
        val Rview = view?.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.recyclerview)
        //was this
        if (Rview != null) {
            val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity)
            Rview.layoutManager = layoutManager;
        }
        //was this
        val adapter = activity?.let { TasksRVAdapter(it, this) }
        Rview?.adapter = adapter

//        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application = Application()))[TasksViewModel::class.java]
        viewModel = ViewModelProvider(this)[TasksViewModel::class.java]
        viewModel.allTasks.observe(viewLifecycleOwner, Observer{
            //update UIr
            adapter?.updateTasks(it)
        })
        val button = view?.findViewById<android.widget.Button>(R.id.submittask)
        button?.setOnClickListener{
//            val inputtext = view.findViewById<EditText>(R.id.input).toString()
            val inputtext1: EditText? = view.findViewById<EditText>(R.id.input)
            val inputtext = inputtext1?.text.toString()
            if(inputtext.isNotEmpty()){
                viewModel.insertTask(Tasks(inputtext))
                Toast.makeText(context, "Task Added", Toast.LENGTH_SHORT).show()
            }
            inputtext1?.setText("")


        }

        return view
    }

    override fun onItemClick(tasks: Tasks) {
        viewModel.deleteTask(tasks)
        Toast.makeText(context, "Task Deleted", Toast.LENGTH_SHORT).show()
    }




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
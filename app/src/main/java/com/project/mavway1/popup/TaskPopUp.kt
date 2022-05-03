package com.project.mavway1.popup

import android.app.AlertDialog
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import androidx.appcompat.app.AppCompatDialogFragment
import com.project.mavway1.R
import com.project.mavway1.firebase.FireStoreClass

class TaskPopUp : AppCompatDialogFragment() {

    private lateinit var task: EditText
    private lateinit var priority: EditText

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = LayoutInflater.from(activity)
        val view = inflater.inflate(R.layout.activity_task_pop_up, null)
        builder.setView(view)
            .setTitle("Add Task").setNegativeButton("cancel", null).setPositiveButton("add", null)
        val dialog = builder.create()
        task = view.findViewById(R.id.Task)
        dialog.setOnShowListener {
            val okButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            okButton.setOnClickListener()
            {
                val task = dialog.findViewById<EditText>(R.id.Task).text.toString()

                sendInput(task)
                dialog.dismiss()

            }
            val cancelButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
            cancelButton.setOnClickListener {
                // dialog won't close by default
                dialog.dismiss()
            }
        }

        return dialog
    }


    private fun sendInput(task: String) {
        FireStoreClass().addTask(task)

    }

}
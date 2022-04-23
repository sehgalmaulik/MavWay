package com.project.mavway1.activities

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import androidx.appcompat.app.AppCompatDialogFragment
import com.project.mavway1.R
import com.project.mavway1.firebase.FireStoreClass
import com.project.mavway1.utils.Constants

class HomeDialogue: AppCompatDialogFragment() {

    private lateinit var classCode: EditText
    private lateinit var classNum: EditText
    private lateinit var timeClass: EditText
    private lateinit var dayClass: EditText
    //make instance of the interface

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = LayoutInflater.from(activity)
        val view = inflater.inflate(R.layout.add_class_dialog, null)
        builder.setView(view)
            .setTitle("Add Class")
            .setNegativeButton("cancel") { dialog, which ->
                    dialog.dismiss()
            }.setPositiveButton("add") { dialog, which ->
                    sendInput(classCode.text.trim().toString(), classNum.text.trim().toString(), timeClass.text.trim().toString(), dayClass.text.trim().toString())
                    dialog.dismiss()
            }
        classCode = view.findViewById(R.id.classCodeInput)
        classNum = view.findViewById(R.id.editClassNumber)
        timeClass = view.findViewById(R.id.time)
        dayClass = view.findViewById(R.id.daysEnter)

        return builder.create()
    }

//    @Override
//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        try {
//            listener = context as OnInputSelected
//        } catch (e: ClassCastException) {
//            throw ClassCastException(context.toString() + "must implement OnInputSelected")
//        }
//    }

//    interface OnInputSelected {
    //TODO: update the professors as well.
        fun sendInput(classCode: String, classNum: String, time: String, day: String)
        {
            //send input to firebase database
            //make a hashmap with key as "classes" and value as an arrayList of classCode, classNum, time, day
//            val classes = HashMap<String, ArrayList<String>>()
//            classes[classCode+classNum] =  makearraylist(classCode, classNum, time, day)
            FireStoreClass().updateClasses(this , classCode, classNum, time, day)
        }

//        fun makearraylist(classCode: String, classNum: String, time: String, day: String): ArrayList<String>
//        {
//            //make an arraylist of classCode, classNum, time, day
//            val classes = ArrayList<String>()
//            classes.add(classCode)
//            classes.add(classNum)
//            classes.add(time)
//            classes.add(day)
//            return classes
//        }
//    }

}
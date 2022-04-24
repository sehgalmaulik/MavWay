package com.project.mavway1.activities

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialogFragment
import com.project.mavway1.R
import com.project.mavway1.firebase.FireStoreClass
import com.project.mavway1.utils.Constants
import java.util.*

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
            .setTitle("Add Class").setNegativeButton("cancel", null).setPositiveButton("add", null)
//            .setNegativeButton("cancel") { dialog, _ ->
//                    dialog.dismiss()
//            }.setPositiveButton("add") { dialog, _ ->
//                classCode = view.findViewById(R.id.classCodeInput)
//                classNum = view.findViewById(R.id.editClassNumber)
//                timeClass = view.findViewById(R.id.time)
//                dayClass = view.findViewById(R.id.daysEnter)
//                    val classCodeLc = classCode.text.trim().toString().uppercase(Locale.getDefault())
//                    val classNumLc = classNum.text.trim().toString()
//                    val timeClassLc = timeClass.text.trim().toString()
//                    val dayClassLc = dayClass.text.trim().toString()
//                    if(classCodeLc.isEmpty() || classNumLc.isEmpty() || timeClassLc.isEmpty() || dayClassLc.isEmpty()) {
////                        classCode.setBackgroundResource(R.drawable.error_border)
//                        Toast.makeText(activity, "Please fill all the fields", Toast.LENGTH_SHORT).show()
//                    }
//
//                    else{
//                        sendInput(classCodeLc, classNumLc, timeClassLc, dayClassLc)
//                        dialog.dismiss()
//                    }
//
//
//            }
    val dialog = builder.create()

    dialog.setOnShowListener {
        val okButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
        okButton.setOnClickListener {
            // dialog won't close by default
            classCode = view.findViewById(R.id.classCodeInput)
                classNum = view.findViewById(R.id.editClassNumber)
                timeClass = view.findViewById(R.id.time)
                dayClass = view.findViewById(R.id.daysEnter)
                    val classCodeLc = classCode.text.trim().toString().uppercase(Locale.getDefault())
                    val classNumLc = classNum.text.trim().toString()
                    val timeClassLc = timeClass.text.trim().toString()
                    val dayClassLc = dayClass.text.trim().toString()
                    if(classCodeLc.isEmpty() || classNumLc.isEmpty() || timeClassLc.isEmpty() || dayClassLc.isEmpty()) {
                        if(classCodeLc.isEmpty()){
                            classCode.setBackgroundResource(R.drawable.error_border)
                        }
                        if(classNumLc.isEmpty()){
                            classNum.setBackgroundResource(R.drawable.error_border)
                        }
                        if(timeClassLc.isEmpty()){
                            timeClass.setBackgroundResource(R.drawable.error_border)
                        }
                        if(dayClassLc.isEmpty()){
                            dayClass.setBackgroundResource(R.drawable.error_border)
                        }
                    }

                    else{
                        sendInput(classCodeLc, classNumLc, timeClassLc, dayClassLc)
                        dialog.dismiss()
                    }
//            dialog.dismiss()
        }

        val cancelButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
        cancelButton.setOnClickListener {
            // dialog won't close by default
            dialog.dismiss()
        }
    }
        return dialog
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
            FireStoreClass().updateClasses(classCode, classNum, time, day)
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
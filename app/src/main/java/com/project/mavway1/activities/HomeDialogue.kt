package com.project.mavway1.activities

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDialogFragment
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat.CLOCK_12H
import com.project.mavway1.R
import com.project.mavway1.firebase.FireStoreClass
import java.util.*
import android.widget.*
import kotlin.collections.ArrayList
import kotlin.math.log


class HomeDialogue: AppCompatDialogFragment() {

    private lateinit var classCode: EditText
    private lateinit var classNum: EditText
    private lateinit var starttimeClass: TextView
    private lateinit var endtimeClass : TextView
    private lateinit var dayClass: TextView
    private lateinit var profName: EditText
    private lateinit var location: AutoCompleteTextView

    private var s_time : String = ""
    private var e_time : String = ""
    private var daysSelected = ArrayList <String>()

    //make instance of the interface

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder =   AlertDialog.Builder(activity)
        val inflater = LayoutInflater.from(activity)
        //change the layout of the dialog
//        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
//        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        //align the title to center
        val view = inflater.inflate(R.layout.add_class_dialog, null)
        builder.setView(view)
            .setTitle("Add Class").setNegativeButton("cancel", null).setPositiveButton("add", null)

        val dialog = builder.create()
        classCode = view.findViewById(R.id.classCodeInput)
        classNum = view.findViewById(R.id.editClassNumber)
        starttimeClass = view.findViewById(R.id.time_start)
        endtimeClass = view.findViewById(R.id.time_end)
        dayClass = view.findViewById(R.id.daysEnter)
        profName = view.findViewById(R.id.professorname)
        location = view.findViewById(R.id.location)

        val arrayofbuilding = makeBuildingArray()

        val adapter: ArrayAdapter<String> = ArrayAdapter(
            requireActivity(),
            android.R.layout.select_dialog_item,
            arrayofbuilding
        )
        location.threshold = 1
        location.setAdapter(adapter)
        location.setTextColor(Color.BLACK)

        starttimeClass.setOnClickListener {

            OpenTimePicker(0)
            starttimeClass.text =s_time
        }
        endtimeClass.setOnClickListener {

            OpenTimePicker(1)
            endtimeClass.text = e_time
        }


        val daysoptions = arrayOf("Monday ", "Tuesday ", "Wednesday ", "Thursday ", "Friday")
        val booldays = booleanArrayOf(false,false,false,false,false)
        val classdays = AlertDialog.Builder(context).setTitle("Class Days")
            .setMultiChoiceItems(daysoptions ,booldays ) {_, i, ischecked ->


            }
            .setPositiveButton("Ok"){_,_ ->
                //Toast.makeText(context,"you selected OK",Toast.LENGTH_SHORT).show()
                dayClass.text = " "
                for (i in booldays.indices) {
                    val checked = booldays[i]
                    if (checked) {
                        val temp = dayClass.text.toString() + daysoptions[i]
                        dayClass.text = temp
                    }
                }
            }
//
            .setNegativeButton("Cancel"){_,_ ->
                //Toast.makeText(context,"you selected CANCEL",Toast.LENGTH_SHORT).show()
            }.create()

        dayClass.setOnClickListener {
            classdays.show()

        }


        dialog.setOnShowListener {
            val okButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            okButton.setOnClickListener {
                // dialog won't close by default


                val classCodeLc = classCode.text.trim().toString().uppercase(Locale.getDefault())
                val classNumLc = classNum.text.trim().toString()
                val timeClassLcS = starttimeClass.text.trim().toString()
                val timeClassLcE = endtimeClass.text.trim().toString()
                val dayClassLc = dayClass.text.trim().toString()
                val profNameLc = profName.text.trim().toString()
                val locationLc = location.text.trim().toString()
                if(classCodeLc.isEmpty() || classNumLc.isEmpty() || timeClassLcS.isEmpty() ||timeClassLcE.isEmpty() || dayClassLc.isEmpty() || profNameLc.isEmpty() || locationLc.isEmpty()) {
                    if(classCodeLc.isEmpty()){
                        classCode.setBackgroundResource(R.drawable.error_border)
                    }
                    if(classNumLc.isEmpty()){
                        classNum.setBackgroundResource(R.drawable.error_border)
                    }
                    if(timeClassLcS.isEmpty()){
                        starttimeClass.setBackgroundResource(R.drawable.error_border)
                    }
                    if(timeClassLcE.isEmpty()){
                        endtimeClass.setBackgroundResource(R.drawable.error_border)
                    }
                    if(dayClassLc.isEmpty()){
                        dayClass.setBackgroundResource(R.drawable.error_border)
                    }
                    if(profNameLc.isEmpty()){
                        profName.setBackgroundResource(R.drawable.error_border)
                    }
                    if(locationLc.isEmpty()){
                        location.setBackgroundResource(R.drawable.error_border)
                    }
                }

                else{
                    val t = "$timeClassLcS - $timeClassLcE"
                    sendInput(classCodeLc, classNumLc, t, dayClassLc, profNameLc, locationLc)
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





    private fun OpenTimePicker(i: Int) {

        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(CLOCK_12H)
            .setHour(8)
            .setMinute(0)
            .setTitleText("Enter class start time")
            .build()

        picker.show(childFragmentManager,"TAG")

        picker.addOnPositiveButtonClickListener {
            if (i==0)
            {
                starttimeClass.text = converttocorrectformat(picker.hour,picker.minute)
            }
            else
                endtimeClass.text = converttocorrectformat(picker.hour,picker.minute)

        }

    }

    private fun converttocorrectformat(h: Int, m: Int) : String {

        var hour= h
        //val min = m.toString()
        val time : String
        val pm = "PM"
        val am = "AM"

        val min = String.format("%02d",m)

        if(h>12)
        {
            hour -= 12
            time = "$hour:$min $pm"
        }

        else if (h==12 && m>=0)
        {
            time = "$hour:$min $pm"
        }

        else if(h==0)
        {
            hour += 12
            time = "$hour:$min $am"
        }


        else
        {
            time = "$hour:$min $am"
        }

        return time

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
    fun sendInput(classCode: String, classNum: String, time: String, day: String, profName: String, location: String)
    {
        //send input to firebase database
        //make a hashmap with key as "classes" and value as an arrayList of classCode, classNum, time, day
//            val classes = HashMap<String, ArrayList<String>>()
//            classes[classCode+classNum] =  makearraylist(classCode, classNum, time, day)
        FireStoreClass().updateClasses(classCode, classNum, time, day, profName, location)

    }

    //
    fun makeBuildingArray():ArrayList<String>{
        val arrayofbuilding = ArrayList<String>()

        arrayofbuilding.add("Aerodynamics Research Building (ARB)")
        arrayofbuilding.add("Amphibian and Reptile Diversity Research Center (ARC)")
        arrayofbuilding.add("Arlington Regional Data Center (ARDC)")
        arrayofbuilding.add("Bookstore (BOOK)")
        arrayofbuilding.add("Business Building (COBA)")
        arrayofbuilding.add("C.R. Gilstrap Athletic Center (GILS)")
        arrayofbuilding.add("Campus Center (CMPC)")
        arrayofbuilding.add("CAPPA Building (ARCH)")
        arrayofbuilding.add("CAPPA Community Design Lab")
        arrayofbuilding.add("Carlisle Hall (CARH)")
        arrayofbuilding.add("Center for Addiction and Recovery Studies (CARS)")
        arrayofbuilding.add("Center for Entrepreneurship and Economic Innovation (CEEI)")
        arrayofbuilding.add("Chemistry & Physics Building (CPB)")
        arrayofbuilding.add("Civil Engineering Lab Building (CELB)")
        arrayofbuilding.add("College Hall (CH)")
        arrayofbuilding.add("College Park Center (CPC)")
        arrayofbuilding.add("Continuing Ed & Workforce Development (CEWF)")
        arrayofbuilding.add("DED Technical Training Ct. (DE)")
        arrayofbuilding.add("EH. Hereford University Center (UC)")
        arrayofbuilding.add("Earth & Environmental Sciences (EES)")
        arrayofbuilding.add("Engineering Lab Building (ELAB)")
        arrayofbuilding.add("Engineering Research Building (ERB)")
        arrayofbuilding.add("Environmental Health & Safety (EH)")
        arrayofbuilding.add("Environmental Health & Safety (West) (EHW)")
        arrayofbuilding.add("Finance and Administration Annex (Watson building) (FAAA)")
        arrayofbuilding.add("Fine Arts Building (FA)")
        arrayofbuilding.add("Fort Worth Center (UTASF)")
        arrayofbuilding.add("General Academic Classroom Building (GACB)")
        arrayofbuilding.add("Hammond Hall (HH)")
        arrayofbuilding.add("Health Center (HLTH)")
        arrayofbuilding.add("Library (LIBR)")
        arrayofbuilding.add("Library Collection Depository & OIT Office Building (LCDO)")
        arrayofbuilding.add("Life Science Building (LS)")
        arrayofbuilding.add("Maverick Activities Center (MAC)")
        arrayofbuilding.add("Maverick Parking Garage (GARA)")
        arrayofbuilding.add("Maverick Stadium (STAD)")
        arrayofbuilding.add("Military & Veteran Services (VAC)")
        arrayofbuilding.add("Nanofab Building (NANO)")
        arrayofbuilding.add("Natural History Specimen Annex - now the ARC (NHSB)")
        arrayofbuilding.add("Nedderman Hall (NH)")
        arrayofbuilding.add("Parking & Transportation Services (PATS)")
        arrayofbuilding.add("Physical Education (PE)")
        arrayofbuilding.add("Pickard Hall (PKH)")
        arrayofbuilding.add("Preston Hall (PH)")
        arrayofbuilding.add("Ransom Hall (RH)")
        arrayofbuilding.add("Science & Engineering Innovation & Research Building (SEIR)")
        arrayofbuilding.add("Science Hall (SH)")
        arrayofbuilding.add("Smart Hospital (SMART)")
        arrayofbuilding.add("Social Work Complex - A (SWCA)")
        arrayofbuilding.add("Social Work Complex - B (SWCB)")
        arrayofbuilding.add("Student and Administration Building (SAB)")
        arrayofbuilding.add("Studio Arts Center (SAC)")
        arrayofbuilding.add("SWEET Center - now Military & Veteran Services (SWEET)")
        arrayofbuilding.add("Swift Center (SC)")
        arrayofbuilding.add("Tennis Center (TENN)")
        arrayofbuilding.add("Texas Hall (TEX)")
        arrayofbuilding.add("The Commons (COM)")
        arrayofbuilding.add("Thermal Energy Plant (TEP)")
        arrayofbuilding.add("Transforming Lives Child Development Center (DAYC)")
        arrayofbuilding.add("Trimble Hall (TH)")
        arrayofbuilding.add("Trinity Hall (TRN)")
        arrayofbuilding.add("University Administration Building (UA)")
        arrayofbuilding.add("University Hall (UH)")
        arrayofbuilding.add("University Police Department (UPD)")
        arrayofbuilding.add("UT Arlington Research Institute (UTARI)")
        arrayofbuilding.add("W. A. Baker Chemistry Research Building (CRB)")
        arrayofbuilding.add("Wade Building (WDB)")
        arrayofbuilding.add("Wetsel Service Center (WET)")
        arrayofbuilding.add("Woolf Hall (WH)")

        return arrayofbuilding

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
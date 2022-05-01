package com.project.mavway1.popup


import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.constraintlayout.helper.widget.MotionEffect.TAG
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.project.mavway1.R


class ForgotPasswordPopup : AppCompatDialogFragment() {
    private var email: EditText? = null
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
//        val inflater = requireActivity().layoutInflater
        val builder = AlertDialog.Builder(activity)
        val inflater = LayoutInflater.from(activity)
        val view: View = inflater.inflate(R.layout.activity_forgot_password_popup, null)
        builder.setView(view).setTitle("Enter Email for Verification Link").setNegativeButton("cancel", null).setPositiveButton("submit", null)


//        builder.setView(view)
//            .setTitle("Enter email for verification")
//            .setNegativeButton("cancel",
//                DialogInterface.OnClickListener { dialogInterface, i -> })
//            .setPositiveButton("ok", DialogInterface.OnClickListener { dialogInterface, i ->
//                val username = editTextUsername!!.text.toString()
//                val password: String = editTextPassword.getText().toString()
//                listener.applyTexts(username, password)
//            })
//        editTextUsername = view.findViewById(R.id.edit_username)
//        return builder.create()
        val dialog = builder.create()

        dialog.setOnShowListener{
            val okButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            okButton.setOnClickListener{
                email = view.findViewById(R.id.email_fp)
                val email_fp = email!!.text.trim().toString()
                if(email_fp.isEmpty()){
                    email!!.setBackgroundResource(R.drawable.error_border)
                    Toast.makeText(activity, "Please enter email", Toast.LENGTH_SHORT).show()
                }
                else
                {
//                    const auth = getAuth();
                    Firebase.auth.sendPasswordResetEmail(email_fp).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d(TAG, "Email sent. ($email_fp)")
                            Toast.makeText(activity, "Email sent ($email_fp)", Toast.LENGTH_LONG).show()
                            dialog.dismiss()
                        }
                    }


                }
            }
            val cancelButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
            cancelButton.setOnClickListener{
                dialog.dismiss()
            }

        }

        return dialog
    }

}
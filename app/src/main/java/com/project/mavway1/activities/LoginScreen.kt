package com.project.mavway1.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.project.mavway1.R
import com.project.mavway1.activities.Fragments.MainActivity
import com.project.mavway1.popup.ForgotPasswordPopup

class LoginScreen : AppCompatActivity(){

    private lateinit var auth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_screen)
        auth = Firebase.auth
        val signin = findViewById<View>(R.id.signInButton) as Button
        signin.setOnClickListener {
            signInRegisteredUser()
        }

        val registerbutton = findViewById<View>(R.id.registerinlogin) as Button
        registerbutton.setOnClickListener {
            val intent = Intent(this, RegisterUser::class.java)
            startActivity(intent)
        }

        val forgotpassword = findViewById<View>(R.id.forgot_password) as TextView
        forgotpassword.setOnClickListener {
            openForgotPopup()
        }
    }

    private fun openForgotPopup() {
            val forgotpassdialogue = ForgotPasswordPopup()
        forgotpassdialogue.show(supportFragmentManager, "forgot pass dialog")

    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            updateUI(currentUser)
        }
    }

    private fun signInRegisteredUser()
    {
        val email: String = findViewById<EditText>(R.id.EmailAddress2).text.toString().trim{it <= ' '}
        val password: String = findViewById<EditText>(R.id.editTextTextPassword2).text.toString().trim{it <= ' '}

        if(validateForm(email, password))
        {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener{task ->
                    if(task.isSuccessful)
                    {
                        updateUI(auth.currentUser)
                    }
                    else
                    {
                        Toast.makeText(this, "Authentication Failed", Toast.LENGTH_SHORT).show()
                        updateUI(null)
                    }
                }
        }

    }

    private fun validateForm(email: String, password: String): Boolean {
        return if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email address", Toast.LENGTH_SHORT).show()
            false
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show()
            false
        } else {
            true
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        if(user != null)
        {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        else
        {
            Toast.makeText(baseContext, "Authentication failed.",
                Toast.LENGTH_SHORT).show()
        }
    }


}
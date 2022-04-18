package com.project.mavway1.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.constraintlayout.helper.widget.MotionEffect.TAG
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.project.mavway1.R
import com.project.mavway1.firebase.FireStoreClass
import com.project.mavway1.models.User

class RegisterUser : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_user)
        auth = Firebase.auth
        //submit to register the user
        val register = findViewById<View>(R.id.register) as Button
        register.setOnClickListener {
            registerUser()
        }

        //redirect to login page
        val login = findViewById<View>(R.id.logininregistration) as Button
        login.setOnClickListener {
            val intent = Intent(this, LoginScreen::class.java)
            startActivity(intent)
        }



    }

    private fun registerUser()
    {
        //get email email and password as strings
        val email: String = findViewById<EditText>(R.id.EmailAddressregister).text.toString().trim {it <= ' '}
        val password: String = findViewById<EditText>(R.id.passwordregister).text.toString().trim {it <= ' '}
        val confirmpass: String = findViewById<EditText>(R.id.confirmpassword).text.toString().trim {it <= ' '}

        if(validateForm(email, password, confirmpass))
        {
            if(confirmpass == password)
            {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success")
                            val user = auth.currentUser
                            val firebaseemail = user?.email
                            val userdatadoc = auth.uid?.let { User(it, firebaseemail.toString() ) }
                            FireStoreClass().registerUser(this , userdatadoc)

                            updateUI(user)

                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
                            Toast.makeText(baseContext, "Registration failed.",
                                Toast.LENGTH_SHORT).show()
                            updateUI(null)
                        }
                    }
            }
            else
            {
                Toast.makeText(baseContext, "Password and confirm password do not match.",
                    Toast.LENGTH_SHORT).show()
            }

        }

    }

    private fun updateUI(user: FirebaseUser?) {
        if(user != null)
        {
            val intent = Intent(this, InformationForm::class.java)
            startActivity(intent)
            finish()
        }
        else
        {
            Toast.makeText(baseContext, "Authentication failed.",
                Toast.LENGTH_SHORT).show()
        }
    }

    fun userRegisteredSuccess()
    {
        Toast.makeText(this, "User Successfully registered", Toast.LENGTH_SHORT).show()

    }

    private fun validateForm(email: String, password: String, confirmpass: String): Boolean {
        return if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email address", Toast.LENGTH_SHORT).show()
            false
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show()
            false
        } else if (TextUtils.isEmpty(confirmpass)) {
            Toast.makeText(this, "Please enter the password again", Toast.LENGTH_SHORT).show()
            false
        } else {
            true
        }
    }
}
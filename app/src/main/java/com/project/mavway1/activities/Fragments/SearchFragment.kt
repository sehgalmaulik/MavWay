package com.project.mavway1.activities.Fragments

import android.content.Intent
import android.net.Uri
import com.project.mavway1.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.project.mavway1.activities.Item_display_activity
import com.project.mavway1.activities.StartScreen


class SearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View? = inflater.inflate(R.layout.fragment_search, container, false)

        val food_bt = view?.findViewById<Button>(R.id.food_button)
        val printing_bt = view?.findViewById<Button>(R.id.printing_button)
        val vending_bt = view?.findViewById<Button>(R.id.vending_button)
        val helpdesk_bt = view?.findViewById<Button>(R.id.helpdesk_button)
        val buildings_bt = view?.findViewById<Button>(R.id.buildings_button)
        val maps_bt = view?.findViewById<Button>(R.id.maps_button)


        food_bt?.setOnClickListener{
//
            //Toast.makeText(context,"Item button clicked", Toast.LENGTH_LONG).show()
            val intent = Intent(activity, Item_display_activity::class.java)
            intent.putExtra("category","Food")
            startActivity(intent)

        }

        printing_bt?.setOnClickListener{
//
           // Toast.makeText(context,"Item button clicked", Toast.LENGTH_LONG).show()
            val intent = Intent(activity, Item_display_activity::class.java)
            intent.putExtra("category","Printing")
            startActivity(intent)

        }
        vending_bt?.setOnClickListener{
//
            //Toast.makeText(context,"Item button clicked", Toast.LENGTH_LONG).show()
            val intent = Intent(activity, Item_display_activity::class.java)
            intent.putExtra("category","Vending")
            startActivity(intent)

        }
        helpdesk_bt?.setOnClickListener{
//
            //Toast.makeText(context,"Item button clicked", Toast.LENGTH_LONG).show()
            val intent = Intent(activity, Item_display_activity::class.java)
            intent.putExtra("category","Helpdesk")
            startActivity(intent)

        }
        buildings_bt?.setOnClickListener{
//
            //Toast.makeText(context,"Item button clicked", Toast.LENGTH_LONG).show()
            val intent = Intent(activity, Item_display_activity::class.java)
            intent.putExtra("category","Building")
            startActivity(intent)

        }
        maps_bt?.setOnClickListener{
//
            val builder = CustomTabsIntent.Builder()
            val customTabsIntent = builder.build()

            context?.let { it1 -> customTabsIntent.launchUrl(it1, Uri.parse("https://www.uta.edu/maps")) }

        }



        return view
    }


}
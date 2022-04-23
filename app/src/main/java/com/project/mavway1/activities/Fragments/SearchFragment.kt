package com.project.mavway1.activities.Fragments

import android.content.Intent
import com.project.mavway1.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.project.mavway1.activities.Item_display_activity
import com.project.mavway1.activities.StartScreen

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

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
        val view: View? = inflater.inflate(R.layout.fragment_search, container, false)

        val food_bt = view?.findViewById<Button>(R.id.food_button)
        val printing_bt = view?.findViewById<Button>(R.id.printing_button)
        val vending_bt = view?.findViewById<Button>(R.id.vending_button)
        val helpdesk_bt = view?.findViewById<Button>(R.id.helpdesk_button)
        val buildings_bt = view?.findViewById<Button>(R.id.buildings_button)
        val maps_bt = view?.findViewById<Button>(R.id.maps_button)


        food_bt?.setOnClickListener{
//
            Toast.makeText(context,"Item button clicked", Toast.LENGTH_LONG).show()
            val intent = Intent(activity, Item_display_activity::class.java)
            intent.putExtra("category","Food")
            startActivity(intent)

        }

        printing_bt?.setOnClickListener{
//
            Toast.makeText(context,"Item button clicked", Toast.LENGTH_LONG).show()
            val intent = Intent(activity, Item_display_activity::class.java)
            intent.putExtra("category","Printing")
            startActivity(intent)

        }
        vending_bt?.setOnClickListener{
//
            Toast.makeText(context,"Item button clicked", Toast.LENGTH_LONG).show()
            val intent = Intent(activity, Item_display_activity::class.java)
            intent.putExtra("category","Vending")
            startActivity(intent)

        }
        helpdesk_bt?.setOnClickListener{
//
            Toast.makeText(context,"Item button clicked", Toast.LENGTH_LONG).show()
            val intent = Intent(activity, Item_display_activity::class.java)
            intent.putExtra("category","Helpdesk")
            startActivity(intent)

        }
        buildings_bt?.setOnClickListener{
//
            Toast.makeText(context,"Item button clicked", Toast.LENGTH_LONG).show()
            val intent = Intent(activity, Item_display_activity::class.java)
            intent.putExtra("category","Buildings")
            startActivity(intent)

        }
        maps_bt?.setOnClickListener{
//
            Toast.makeText(context,"Item button clicked", Toast.LENGTH_LONG).show()
            val intent = Intent(activity, Item_display_activity::class.java)
            intent.putExtra("category","Maps")
            startActivity(intent)

        }





        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SearchFragment.
         */

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
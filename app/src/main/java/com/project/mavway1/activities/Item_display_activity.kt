package com.project.mavway1.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.project.mavway1.R
import com.project.mavway1.activities.Fragments.MainActivity
import com.project.mavway1.adapter.ItemListAdapter
import com.project.mavway1.adapter.numItemClicked
import com.project.mavway1.models.Item

class Item_display_activity : AppCompatActivity(), numItemClicked {

    private lateinit var database: DatabaseReference
    private lateinit var adapter : ItemListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_diplay)

        database = Firebase.database.reference

        val recyclerView = findViewById<RecyclerView>(R.id.rv)
        val tv = findViewById<TextView>(R.id.item_placeholder_heading)
        val category = intent.getStringExtra("category").toString()

        tv.text = category

        recyclerView.layoutManager = LinearLayoutManager(this)
        fetchData(category)
        adapter = ItemListAdapter(this)

        recyclerView.adapter = adapter
    }

    private fun fetchData(category: String) {

        database.child(category).get().addOnSuccessListener{

            //Log.i("firebase", "Got value ${it.value}")
            val itemArray = ArrayList<Item>()
            for (i in it.children)
            {
                Log.i("firebase",i.child("name").value.toString())
                val item = Item(i.child("name").value.toString(),
                    i.child("description").value.toString(),
                    i.child("imageURL").value.toString(),
                    i.child("location").value.toString(),
                    i.child("locationURL").value.toString()
                )

                itemArray.add(item)
            }
            adapter.updateItems(itemArray)
        }
    }

    override fun OnItemClicked(item: Item) {
        Toast.makeText(this,"Clicked : Item ${item.name}", Toast.LENGTH_LONG).show()

        val intent = Intent(this, ItemInfo::class.java)
        intent.putExtra("item_selected",item)
        startActivity(intent)
    }


}
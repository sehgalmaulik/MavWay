package com.project.mavway1.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.mavway1.R
import com.project.mavway1.adapter.ItemListAdapter
import com.project.mavway1.adapter.numItemClicked

class Item_display_activity : AppCompatActivity(), numItemClicked {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_diplay)

        val recyclerView = findViewById<RecyclerView>(R.id.rv)
        val tv = findViewById<TextView>(R.id.item_placeholder_heading)
        val category = intent.getStringExtra("category").toString()

        tv.text = category

        recyclerView.layoutManager = LinearLayoutManager(this)
        val items = fetchData(category)
        val adapter = ItemListAdapter(items,this)
        recyclerView.adapter = adapter
    }

    private fun fetchData(category: String) : ArrayList<String> {
        val list  = ArrayList<String>()

        if (category == "Food")
        {
            list.add("Connection Cafe")
            list.add("The Arlington Eatery")
            list.add("Subway")
            list.add("Starbucks at University Center")
            list.add("Panda Express")
            list.add("Chick-fil-A")
            list.add("Kalachandji's at University Center Market")
            list.add("Sushic")
            list.add("TEACo at University Center")
            list.add("The Halal Shack")
            list.add("Market at University Center")
            list.add("Maverick Cafe'")
            list.add("Starbucks at Commons")
            list.add("TEACo at The Commons")
            list.add("Market @ The Library")
            list.add("The University Club")
            list.add("Market at Fine Arts Building")
            list.add("Market @ SEIR")
            list.add("Market @ The MAC")
            list.add("Panera Bread")
        }
        else
        {
            for ( i in 0 until 100)
                list.add("Item $i")
        }


        return list
    }

    override fun OnItemClicked(item: String) {
        Toast.makeText(this,"Clicked : Item $item", Toast.LENGTH_LONG).show()
    }
}
package com.project.mavway1.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.project.mavway1.R
import com.project.mavway1.models.Item


class ItemInfo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_info)


        val item = intent.getSerializableExtra("item_selected") as Item

        val title = findViewById<TextView>(R.id.item_title)
        val desc = findViewById<TextView>(R.id.item_description)
        val location = findViewById<TextView>(R.id.item_location)
        val image = findViewById<ImageView>(R.id.item_image)

        val navigate_bt = findViewById<Button>(R.id.item_button)

        title.text = item.name
        desc.text = item.description
        location?.text = item.location

        Glide.with(this).load(item.imageURL).into(image)


        navigate_bt?.setOnClickListener{

            Toast.makeText(this,"Opening Maps for : ${item.name}",Toast.LENGTH_LONG).show()
            val googleMapsUrl = item.locationURL
            val uri = Uri.parse(googleMapsUrl)

            val googleMapsPackage = "com.google.android.apps.maps"
            val intent = Intent(Intent.ACTION_VIEW, uri).apply {
                setPackage(googleMapsPackage)
            }
            startActivity(intent)
        }


    }
}
package com.project.mavway1.models

import java.io.Serializable


data class Item(

    var name : String,
    val description: String,
    val imageURL : String,
    val location : String,
    val locationURL : String) : Serializable
{

}

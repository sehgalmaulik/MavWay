package com.project.mavway1.models

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class User (
    val id: String ?= "",
    val email: String ?= "",
    val name: String ?= "",
    val phoneNum: String ?= "",
    val dob: String ?= "",
    val major: String ?= "",
    val minor: String ?= "",
    val collegeYear: String ?= "",
    var image: String ?= ""


): Serializable

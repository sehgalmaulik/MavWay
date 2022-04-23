package com.project.mavway1.models

import android.os.Parcel
import android.os.Parcelable

data class User (
    val id: String ?= "",
    val email: String ?= "",
    val name: String ?= "",
    val phoneNum: String ?= "",
    val DOB: String ?= "",
    val major: String ?= "",
    val minor: String ?= "",
    val collegeYear: String ?= "",
    val image: String ?= ""


): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(email)
        parcel.writeString(name)
        parcel.writeString(phoneNum)
        parcel.writeString(DOB)
        parcel.writeString(major)
        parcel.writeString(minor)
        parcel.writeString(collegeYear)
        parcel.writeString(image)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}

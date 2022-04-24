package com.project.mavway1.models
import java.io.Serializable

data class UserCourses  (

    val course: String ?= "",
    val courseCode: String ?= "",
    val timings: String ?= "",
    val days: String ?= "",
    val professor: String ?= "",
    val location: String ?= "",
): Serializable


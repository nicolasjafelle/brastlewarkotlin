package com.brastlewar.kotlin.domain

import com.google.gson.annotations.SerializedName
import org.parceler.Parcel

/**
 * Created by nicolas on 11/9/17.
 */
@Parcel
data class Citizen(val id: Long,
                   val name: String,
                   val thumbnail: String,
                   val age: Int,
                   val weight: Double,
                   val height: Double,
                   @SerializedName("professions") val professionList: List<String>,
                   @SerializedName("friends") val friendList: List<String>) {

    constructor() : this(0, "", "", 0, 0.0, 0.0, ArrayList<String>(), ArrayList<String>()) //for parceler library...
}


package com.brastlewar.kotlin.domain

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


/**
 * Created by nicolas on 11/9/17.
 */
@SuppressLint("ParcelCreator")
@Parcelize
data class Citizen(val id: Long,
                   val name: String,
                   val thumbnail: String,
                   val age: Int,
                   val weight: Double,
                   val height: Double,
                   @SerializedName("professions") val professionList: List<String>,
                   @SerializedName("friends") val friendList: List<String>) : Parcelable {

}


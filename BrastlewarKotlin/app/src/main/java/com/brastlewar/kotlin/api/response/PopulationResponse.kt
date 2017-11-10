package com.brastlewar.kotlin.api.response

import com.brastlewar.kotlin.domain.Citizen
import com.google.gson.annotations.SerializedName

/**
 * Created by nicolas on 11/9/17.
 */
data class PopulationResponse(@SerializedName("Brastlewark") val citizenList: List<Citizen>)

package com.brastlewar.kotlin.ui.main

import com.brastlewar.kotlin.api.response.PopulationResponse
import com.brastlewar.kotlin.domain.Citizen
import com.brastlewar.kotlin.mvp.RestHttpView

/**
 * Created by nicolas on 11/9/17.
 */
interface MainView : RestHttpView {

    fun onGetData(response: PopulationResponse?)

    fun onSearchResult(filteredList: List<Citizen>?)

}
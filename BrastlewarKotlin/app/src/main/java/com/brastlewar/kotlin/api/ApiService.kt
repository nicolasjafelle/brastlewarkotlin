package com.brastlewar.kotlin.api

import com.brastlewar.kotlin.api.response.PopulationResponse
import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by nicolas on 11/9/17.
 */
interface ApiService {

    @GET(Endpoints.DATA)
    fun populationResponse(): Call<PopulationResponse>
//    fun populationResponse(): Observable<PopulationResponse>
}
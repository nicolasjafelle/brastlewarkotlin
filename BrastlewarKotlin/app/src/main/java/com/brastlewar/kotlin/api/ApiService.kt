package com.brastlewar.kotlin.api

import com.brastlewar.kotlin.api.response.PopulationResponse
import retrofit2.http.GET
import rx.Observable

/**
 * Created by nicolas on 11/9/17.
 */
interface ApiService {

    @GET(Endpoints.DATA)
    fun populationResponse(): Observable<PopulationResponse>
}
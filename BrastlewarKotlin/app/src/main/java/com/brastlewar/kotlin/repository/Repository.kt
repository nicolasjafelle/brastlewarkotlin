package com.brastlewar.kotlin.repository


/**
 * Created by nicolas on 11/9/17.
 */
class Repository {

//    lateinit var apiClient: ApiClient

    public fun populationResponse() = ApiClient.instance.populationResponse()
}
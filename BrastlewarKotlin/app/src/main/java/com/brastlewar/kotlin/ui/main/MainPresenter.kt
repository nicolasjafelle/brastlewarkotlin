package com.brastlewar.kotlin.ui.main

import com.brastlewar.kotlin.api.response.PopulationResponse
import com.brastlewar.kotlin.domain.Citizen
import com.brastlewar.kotlin.mvp.BasePresenter
import com.brastlewar.kotlin.mvp.ViewState
import com.brastlewar.kotlin.repository.Repository
import com.brastlewar.kotlin.utils.RestHttpExceptionHandler
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.asReference
import org.jetbrains.anko.coroutines.experimental.bg

/**
 * Created by nicolas on 11/9/17.
 */
class MainPresenter : BasePresenter<MainView>() {

    private val MIN_LENGHT = 2

    var response: PopulationResponse? = null

    var filteredList: List<Citizen>? = null

    var lastQuery: String? = null

//    val job = Job()

    val repository: Repository by lazy {
        Repository()
    }


    fun getPopulationList() {
//        launch(UI + job) {
//
//            try {
//                val background = async(CommonPool, CoroutineStart.LAZY) {
//                    repository.populationResponse().execute().body()
//                }
//
//                response = background.await()
//                mvpView?.onGetData(response)
//                setCurrentState(ViewState.State.FINISH)
//
//            }catch (e: Exception) {
//                RestHttpExceptionHandler().handle(UI, e, this@MainPresenter)
//            }
//        }

        val ref = asReference()

//        async(UI + job) {
        async(UI) {
            try {
                ref().setCurrentState(ViewState.State.LOADING)
                val background = bg {
                    repository.populationResponse().execute().body()
                }

                ref().let {
                    it.response = background.await()
                    it.mvpView?.onGetData(it.response)
                    it.setCurrentState(ViewState.State.FINISH)
                }
            } catch (e: Exception) {
                RestHttpExceptionHandler().handle(UI, e, ref())
            }
        }
    }

    fun showAll() {
        this.filteredList = null
        this.lastQuery = null
        mvpView?.onGetData(response!!)
    }


    fun searchCitizen(textToSearch: String) {
        val ref = asReference()

        async(UI) {
            try {
                setCurrentState(ViewState.State.LOADING)

                val background = bg {
                    val citizenList: List<Citizen>
                    lastQuery = textToSearch

                    if (lastQuery!!.length <= MIN_LENGHT) {
                        citizenList = response?.citizenList!!
//                        return@bg response?.citizenList!!
                    } else {
                        citizenList = filterByCitizenName(lastQuery!!)
//                        return@bg filterByCitizenName(lastQuery!!)
                    }

                    return@bg citizenList //just to know how to implicit return the value
                }

                ref().let {
                    it.filteredList = background.await()
                    it.mvpView?.onSearchResult(it.filteredList)
                    it.setCurrentState(ViewState.State.FINISH)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                ref().mvpView?.onError(e)
            }
        }
    }

    private fun filterByCitizenName(name: String): List<Citizen> {
        val filteredList = ArrayList<Citizen>(0)

        response?.citizenList?.forEach { citizen ->
            if (citizen.name.toLowerCase().contains(name.toLowerCase())) {
                filteredList.add(citizen)
            }
        }
        return filteredList
    }

}



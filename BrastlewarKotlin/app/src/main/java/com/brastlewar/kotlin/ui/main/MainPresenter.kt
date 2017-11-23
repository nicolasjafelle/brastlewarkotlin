package com.brastlewar.kotlin.ui.main

import com.brastlewar.kotlin.api.response.PopulationResponse
import com.brastlewar.kotlin.domain.Citizen
import com.brastlewar.kotlin.mvp.BasePresenter
import com.brastlewar.kotlin.mvp.ViewState
import com.brastlewar.kotlin.repository.Repository
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

    val repository: Repository by lazy {
        Repository()
    }


    fun getPopulationList() {
        val ref = asReference()

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
                e.printStackTrace()
                mvpView?.onError(e)
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
                    lastQuery = textToSearch
                    if (lastQuery!!.length <= MIN_LENGHT) {
                        response?.citizenList
                    } else {
                        filterByCitizenName(lastQuery!!)
                    }
                }

                ref().let {
                    it.filteredList = background.await()
                    it.mvpView?.onSearchResult(it.filteredList)
                    it.setCurrentState(ViewState.State.FINISH)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                mvpView?.onError(e)
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



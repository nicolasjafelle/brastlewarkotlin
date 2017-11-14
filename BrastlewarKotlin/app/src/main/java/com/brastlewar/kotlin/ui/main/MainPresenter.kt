package com.brastlewar.kotlin.ui.main

import com.brastlewar.kotlin.api.response.PopulationResponse
import com.brastlewar.kotlin.domain.Citizen
import com.brastlewar.kotlin.mvp.BasePresenter
import com.brastlewar.kotlin.mvp.ViewState
import com.brastlewar.kotlin.repository.Repository
import com.brastlewar.kotlin.utils.RestHttpObserver
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.concurrent.TimeUnit

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
        setCurrentState(ViewState.State.LOADING)

        val subscription = repository.populationResponse()
                .delay(1000, TimeUnit.MILLISECONDS) //just to simulate a big transaction...
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : RestHttpObserver<PopulationResponse>(this) {

                    override fun onNext(response: PopulationResponse) {
                        setCurrentState(ViewState.State.FINISH)
                        this@MainPresenter.response = response
                        mvpView?.onGetData(response)
                    }
                })

        super.compositeSubscription?.add(subscription)

    }


    fun showAll() {
        this.filteredList = null
        this.lastQuery = null
        mvpView?.onGetData(response!!)
    }


    fun searchCitizen(textToSearch: String) {
        setCurrentState(ViewState.State.LOADING)

        val subscription = Observable.just(textToSearch)
                .subscribeOn(Schedulers.io())
                .debounce(500, TimeUnit.MILLISECONDS)
                .flatMap { search ->
                    lastQuery = search
                    if (search.length <= MIN_LENGHT) {
                        Observable.just(response?.citizenList)
                    } else {
                        val filteredList = filterByCitizenName(search)
                        Observable.just(filteredList)
                    }
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : RestHttpObserver<List<Citizen>?>(this) {

                    override fun onNext(citizenList: List<Citizen>?) {
                        setCurrentState(ViewState.State.FINISH)
                        this@MainPresenter.filteredList = citizenList
                        mvpView?.onSearchResult(filteredList)
                    }
                })

        super.compositeSubscription?.add(subscription)
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



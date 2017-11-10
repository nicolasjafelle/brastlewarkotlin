package com.brastlewar.kotlin.ui.main

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.brastlewar.kotlin.domain.Citizen
import com.brastlewar.kotlin.ui.view.CitizenItemView

/**
 * Created by nicolas on 11/10/17.
 */
class CitizenAdapter(val onItemClick: (Int) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val citizenList: MutableList<Citizen> = ArrayList()

    fun addList(citizenList: List<Citizen>) {
        clearList()
        this.citizenList.addAll(citizenList)
        notifyDataSetChanged()
    }

    fun clearList() {
        citizenList.clear()
    }


    fun getItemAt(position: Int): Citizen? {
        return if (!citizenList.isEmpty()) {
            citizenList[position]
        } else {
            null
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (holder is ItemViewHolder) {
            holder.populateData(getItemAt(position))
        }
    }

    override fun getItemCount() = citizenList.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return ItemViewHolder(CitizenItemView(parent!!.context))
    }

    /**
     * Inner class
     */
    private inner class ItemViewHolder(itemView: CitizenItemView) : RecyclerView.ViewHolder(itemView) {

        fun populateData(citizen: Citizen?) {
            citizen?.let {
                (itemView as CitizenItemView).loadData(it) // it means the class using it...
                itemView.setOnClickListener { onItemClick(adapterPosition) }
            }
        }
    }
}
package com.brastlewar.kotlin.ui.detail

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.brastlewar.kotlin.ui.view.ChipView

/**
 * Created by nicolas on 11/14/17.
 */
class ChipAdapter(val list: List<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return ItemViewHolder(ChipView(parent?.context))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (holder is ItemViewHolder) {
            holder.populateData(getItemAt(position))
        }
    }

    override fun getItemCount() = list.size


    fun getItemAt(position: Int) = list[position]


    inner class ItemViewHolder(itemView: ChipView) : RecyclerView.ViewHolder(itemView) {

        fun populateData(text: String) {
            (itemView as ChipView).text = text
        }
    }


}
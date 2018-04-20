package com.chahine.calendarview

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.chahine.calendarview.MainAdapter.MainViewHolder

class MainAdapter : RecyclerView.Adapter<MainViewHolder>() {

  private val items: MutableList<String> = mutableListOf()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
    return MainViewHolder(LayoutInflater.from(parent.context)
        .inflate(R.layout.item_day_number, parent, false))
  }

  override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
    holder.bind(items[holder.adapterPosition])
  }

  override fun getItemCount() = items.size

  fun swapData(data: Collection<String>) {
    items.clear()
    items.addAll(data)
    notifyDataSetChanged()
  }

  class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val dayNumber: TextView = itemView.findViewById(R.id.dayNumber)

    fun bind(text: String) {
      dayNumber.text = text
    }
  }
}

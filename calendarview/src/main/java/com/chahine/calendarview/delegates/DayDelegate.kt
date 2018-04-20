package com.chahine.calendarview.delegates

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.chahine.calendarview.CalendarAdapter
import com.chahine.calendarview.R
import org.threeten.bp.LocalDate

class DayDelegate {
  class Delegate : CalendarAdapter.Delegate {
    override fun layoutId() = R.layout.item_day_number

    override fun create(parent: ViewGroup) = Holder(itemView(parent))

    override fun bind(item: CalendarAdapter.Item, holder: RecyclerView.ViewHolder) {
      if (holder is Holder && item is Item) {
        holder.bind(item)
      }
    }
  }

  class Holder(itemView: View) : ViewHolder(itemView) {
    private val dayNumber: TextView = itemView.findViewById(R.id.dayNumber)

    fun bind(item: Item) {
      dayNumber.text = item.date.dayOfMonth.toString()
    }
  }

  data class Item(val date: LocalDate) : CalendarAdapter.Item {
    override fun itemViewType() = CalendarAdapter.DAY
  }
}

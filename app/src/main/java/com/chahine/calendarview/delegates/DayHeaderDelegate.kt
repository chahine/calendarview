package com.chahine.calendarview.delegates

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.chahine.calendarview.CalendarAdapter
import com.chahine.calendarview.R
import org.threeten.bp.DayOfWeek
import org.threeten.bp.format.TextStyle.SHORT
import java.util.Locale

class DayHeaderDelegate {
  class Delegate : com.chahine.calendarview.CalendarAdapter.Delegate {
    override fun layoutId() = R.layout.item_day_header

    override fun create(parent: ViewGroup) = Holder(itemView(parent))

    override fun bind(item: CalendarAdapter.Item, holder: RecyclerView.ViewHolder) {
      if (holder is Holder && item is Item) {
        holder.bind(item)
      }
    }
  }

  class Holder(itemView: View) : ViewHolder(itemView) {
    private val dayHeader: TextView = itemView.findViewById(R.id.dayHeader)

    fun bind(item: Item) {
      dayHeader.text = item.dayOfWeek.getDisplayName(SHORT, Locale.getDefault())
    }
  }

  data class Item(val dayOfWeek: DayOfWeek) : CalendarAdapter.Item {
    override fun itemViewType() = CalendarAdapter.DAY_HEADER
  }
}

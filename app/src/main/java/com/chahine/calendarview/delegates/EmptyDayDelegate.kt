package com.chahine.calendarview.delegates

import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.View
import android.view.ViewGroup
import com.chahine.calendarview.CalendarAdapter
import com.chahine.calendarview.R
import org.threeten.bp.LocalDate

class EmptyDayDelegate {
  class Delegate : CalendarAdapter.Delegate {
    override fun layoutId() = R.layout.item_day_empty

    override fun create(parent: ViewGroup) = Holder(itemView(parent))
  }

  class Holder(itemView: View) : ViewHolder(itemView)

  data class Item(val date: LocalDate) : CalendarAdapter.Item {
    override fun itemViewType() = CalendarAdapter.EMPTY_DAY
  }
}

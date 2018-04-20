package com.chahine.calendarview

import android.support.v7.widget.RecyclerView.ViewHolder
import com.chahine.calendarview.CalendarAdapter.Item
import com.chahine.calendarview.delegates.DayDelegate
import com.chahine.calendarview.delegates.DayHeaderDelegate
import com.chahine.calendarview.delegates.EmptyDayDelegate
import com.chahine.calendarview.rv.RvAdapter
import com.chahine.calendarview.rv.RvDelegate
import com.chahine.calendarview.rv.RvItem

class CalendarAdapter : RvAdapter<Item>() {

  init {
    delegates[DAY] = DayDelegate.Delegate()
    delegates[DAY_HEADER] = DayHeaderDelegate.Delegate()
    delegates[EMPTY_DAY] = EmptyDayDelegate.Delegate()
  }

  interface Delegate : RvDelegate<Item, ViewHolder>
  interface Item : RvItem

  companion object {
    const val DAY = 1
    const val DAY_HEADER = 2
    const val EMPTY_DAY = 3
  }
}

package com.chahine.calendarview

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import com.chahine.calendarview.CalendarAdapter.Item
import com.chahine.calendarview.decoration.EdgeSpacingDecoration
import com.chahine.calendarview.delegates.DayDelegate
import com.chahine.calendarview.delegates.DayHeaderDelegate
import com.chahine.calendarview.delegates.EmptyDayDelegate
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate

class CalendarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

  val calendarAdapter: CalendarAdapter get() = this.adapter as CalendarAdapter

  init {
    layoutManager = GridLayoutManager(context, 7, HORIZONTAL, false)
    val adapter = CalendarAdapter()
    this.adapter = adapter
    addItemDecoration(EdgeSpacingDecoration(context))

    val today = LocalDate.now()
    val firstDayOfYear = LocalDate.ofYearDay(today.year, 1)

    val data = mutableListOf<Item>()
    (0 until 12).forEach {
      data.addAll(itemsForMonth(firstDayOfYear.plusMonths(it.toLong())))
    }
    adapter.swapData(data)
  }

  private fun itemsForMonth(firstDayOfMonth: LocalDate): List<Item> {

    val result = mutableListOf<Item>()

    DayOfWeek.values().forEachIndexed { index, day ->
      // offsetting by one to get Sunday as first day of week
      result.add(DayHeaderDelegate.Item(day.minus(1)))

      val dayDate = if (isFirstDayOfWeek(firstDayOfMonth)) {
        firstDayOfMonth
            .minusDays(firstDayOfMonth.dayOfWeek.value.toLong())
            .plusDays(index.toLong())
      } else {
        firstDayOfMonth.plusDays(index.toLong())
      }

      (0 until 6)
          .map { dayDate.plusDays(7L * it) }
          .forEach {
            if (it.monthValue != firstDayOfMonth.monthValue) {
              result.add(EmptyDayDelegate.Item(it))
            } else {
              result.add(DayDelegate.Item(it))
            }
          }
    }

    return result
  }

  private fun isFirstDayOfWeek(firstDayOfMonth: LocalDate): Boolean {
    return firstDayOfMonth.dayOfWeek != DayOfWeek.SUNDAY
  }
}

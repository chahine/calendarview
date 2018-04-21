package com.chahine.calendarview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.chahine.calendarview.decoration.EdgeSpacingDecoration
import com.chahine.calendarview.delegates.DayDelegate
import com.chahine.calendarview.delegates.DayHeaderDelegate
import com.chahine.calendarview.delegates.EmptyDayDelegate
import com.jakewharton.threetenabp.AndroidThreeTen
import kotlinx.android.synthetic.main.activity_main.list
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import timber.log.Timber
import timber.log.Timber.DebugTree

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    Timber.plant(DebugTree())
    AndroidThreeTen.init(this)
    setContentView(R.layout.activity_main)

    val mainAdapter = CalendarAdapter()
    list.apply {
      layoutManager = GridLayoutManager(this@MainActivity, 7, RecyclerView.HORIZONTAL, false)
      adapter = mainAdapter
      addItemDecoration(EdgeSpacingDecoration(this@MainActivity))
    }

    val today = LocalDate.now()
    val firstDayOfYear = LocalDate.ofYearDay(today.year, 1)

    val data = mutableListOf<CalendarAdapter.Item>()
    (0 until 12).forEach {
      data.addAll(itemsForMonth(firstDayOfYear.plusMonths(it.toLong())))
    }
    mainAdapter.swapData(data)
  }

  private fun itemsForMonth(firstDayOfMonth: LocalDate): List<CalendarAdapter.Item> {

    val result = mutableListOf<CalendarAdapter.Item>()

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

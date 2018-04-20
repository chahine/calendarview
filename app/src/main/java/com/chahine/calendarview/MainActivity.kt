package com.chahine.calendarview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.jakewharton.threetenabp.AndroidThreeTen
import kotlinx.android.synthetic.main.activity_main.list
import org.threeten.bp.DayOfWeek
import org.threeten.bp.DayOfWeek.SUNDAY
import org.threeten.bp.LocalDate
import timber.log.Timber
import timber.log.Timber.DebugTree

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    Timber.plant(DebugTree())
    AndroidThreeTen.init(this)
    setContentView(R.layout.activity_main)

    val mainAdapter = MainAdapter()
    list.apply {
      layoutManager = GridLayoutManager(this@MainActivity, 7, RecyclerView.HORIZONTAL, false)
      adapter = mainAdapter
    }

    val today = LocalDate.now()
    val firstDayOfMonth = today.minusDays(today.dayOfMonth.toLong() - 1)

    Timber.d("--> $today")
    Timber.d("--> $firstDayOfMonth")

    mainAdapter.swapData(itemsForMonth(firstDayOfMonth.plusMonths(1)))
  }

  fun itemsForMonth(firstDayOfMonth: LocalDate): List<String> {

    val result = mutableListOf<String>()
    val days = listOf("S", "M", "T", "W", "T", "F", "S")

    days.forEachIndexed { index, day ->
      val dayDates = mutableListOf<String>()
      dayDates.add(day)

      var dayDate = if (firstDayOfMonth.dayOfWeek != SUNDAY) {
        firstDayOfMonth
            .minusDays(firstDayOfMonth.dayOfWeek.value.toLong())
            .plusDays(index.toLong())
      } else {
        firstDayOfMonth.plusDays(index.toLong())
      }

      Timber.d("--> $day: $dayDate: ${dayDate.dayOfWeek}")

      while (dayDate.monthValue <= firstDayOfMonth.monthValue) {
        if (dayDate.monthValue != firstDayOfMonth.monthValue) {
          dayDates.add("")
        } else {
          dayDates.add(dayDate.dayOfMonth.toString())
        }
        dayDate = dayDate.plusDays(7)
      }
      while (dayDates.size < 7) {
        dayDates.add("")
      }
      result.addAll(dayDates)
    }

    return result
  }
}

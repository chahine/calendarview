package com.chahine.calendarview.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jakewharton.threetenabp.AndroidThreeTen
import timber.log.Timber
import timber.log.Timber.DebugTree

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    Timber.plant(DebugTree())
    AndroidThreeTen.init(this)
    setContentView(R.layout.activity_main)
  }
}

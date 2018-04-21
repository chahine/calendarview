package com.chahine.calendarview.decoration

import android.content.Context
import android.graphics.Point
import android.graphics.Rect
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ItemDecoration
import android.support.v7.widget.RecyclerView.State
import android.view.View
import android.view.WindowManager
import com.chahine.calendarview.R.dimen

class EdgeSpacingDecoration(context: Context) : ItemDecoration() {

  private val cellSize: Int
  private val edgeSpacing: Int

  init {
    val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val display = wm.defaultDisplay
    val size = Point()
    display.getSize(size)

    cellSize = context.resources.getDimensionPixelSize(dimen.cell_size)
    edgeSpacing = (size.x - cellSize * 7) / 2
  }

  override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: State) {
    super.getItemOffsets(outRect, view, parent, state)

    val position = parent.getChildAdapterPosition(view)
    val layoutManager: GridLayoutManager = parent.layoutManager as GridLayoutManager
    val column = position / layoutManager.spanCount

    if (column == 0) {
      outRect.left = edgeSpacing
    }
  }
}

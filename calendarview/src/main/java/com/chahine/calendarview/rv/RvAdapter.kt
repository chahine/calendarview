package com.chahine.calendarview.rv

import android.os.Handler
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.ViewGroup
import java.util.ArrayList
import java.util.HashMap

/**
 * [RvAdapter] is a generic implementation of [RecyclerView.Adapter] that contains and helper
 * methods to swap and append data in the [RecyclerView]. Your customRvAdapter is what get passed
 * into RecyclerView's [setAdapter]. It also has what we call a delegate. This allows us to
 * declare multiple view types in the adapter.
 */
open class RvAdapter<E : RvItem> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

  protected val delegates: MutableMap<Int, RvDelegate<E, ViewHolder>> = HashMap()
  protected val items: MutableList<E> = ArrayList()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    return delegates[viewType]?.create(parent)
        ?: throw IllegalStateException("cant find delegate for view type #$viewType")
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val adapterPosition = holder.adapterPosition
    val viewType = getItemViewType(adapterPosition)
    delegates[viewType]?.bind(getItemAt(adapterPosition), holder)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: List<Any>) {
    val adapterPosition = holder.adapterPosition
    val viewType = getItemViewType(adapterPosition)
    delegates[viewType]?.bind(getItemAt(adapterPosition), holder, payloads)
  }

  override fun getItemCount() = items.count()

  override fun getItemViewType(position: Int) = getItemAt(position).itemViewType()

  fun adapterData(): List<E> = items.toList()

  open fun swapData(data: Collection<E>) {
    items.clear()
    items.addAll(data)
    notifyDataSetChanged()
  }

  open fun appendData(data: Collection<E>) {
    if (data.isEmpty()) {
      return
    }

    // Scroll callbacks might be run during a measure & layout pass where you cannot change the
    // RecyclerView data. e.g Scrolling while adding items to an infinite list. Any method call that
    // might change the structure of the RecyclerView or the adapter contents should be postponed to
    // the next frame.
    Handler().post {
      val itemSize = items.size
      items.addAll(data)
      notifyItemRangeInserted(itemSize, data.size)
    }
  }

  fun prependData(data: Collection<E>) {
    if (data.isEmpty()) {
      return
    }

    items.addAll(0, data)
    // Scroll callbacks might be run during a measure & layout pass where you cannot change the
    // RecyclerView data. e.g Scrolling while adding items to an infinite list. Any method call that
    // might change the structure of the RecyclerView or the adapter contents should be postponed to
    // the next frame.
    Handler().post {
      notifyItemRangeInserted(0, data.size)
    }
  }

  fun swapItem(position: Int, item: E, notifyChange: Boolean = true) {
    items.removeAt(position)
    items.add(position, item)
    if (notifyChange) {
      notifyItemChanged(position)
    }
  }

  open fun getItemAt(adapterPosition: Int) = items[adapterPosition]
}

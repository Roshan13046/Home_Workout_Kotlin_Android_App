package com.example.daily_workout_app

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_history_row.view.*

class HistoryAdapter(val context : Context , val items: ArrayList<String>): RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val llHistoryItem = view.ll_history_item_main
        val tvItem = view.tvItem
        val tvPosition = view.tvPosition
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_history_row,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val date : String = items.get(position)

        holder.tvPosition.text = (position).toString()
        holder.tvItem.text = date

        //adding color in background of history table

        if(position % 2 == 0){
            holder.llHistoryItem.setBackgroundColor(
                Color.parseColor("#212121")
            )
        }else{
            holder.llHistoryItem.setBackgroundColor(
                Color.parseColor("#FFFFFF")
            )
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }
}
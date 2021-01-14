package com.example.daily_workout_app

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_history_row.view.*

//items are what we get when an object of the History adapter created.
class HistoryAdapter(val context : Context , val items: ArrayList<String>):
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val llHistoryItem = view.ll_history_item_main
        val tvItem = view.tvItem
        val tvPosition = view.tvPosition
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_history_row,parent,false))
    }

    //This method is use to bind the data into the listView/ RecyclerView
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val date : String = items.get(position)

        //Note : array data is store from position 1 and not from 0
        holder.tvPosition.text = (position + 1).toString()
        holder.tvItem.text = date

        //adding color in background of history table
        if(position % 2 == 0){
            holder.llHistoryItem.setBackgroundColor(
                Color.parseColor("#EBEBEB")
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
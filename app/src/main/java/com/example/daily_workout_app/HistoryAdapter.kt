package com.example.daily_workout_app

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_history_row.view.*

/**
 * Creating the RecyclerView Adapter class.
 *
 * HistoryAdapter class is for displaying the past workout history in the context of History screen
 */

class HistoryAdapter(val context : Context , val items: ArrayList<String>):
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val llHistoryItem = view.ll_history_item_main
        val tvItem = view.tvItem
        val tvPosition = view.tvPosition
    }

    /**
     * Inflates the item views which is designed in xml layout file
     *
     * crate a new {@link ViewHolder} and initializes some private fields to
     * be used by RecyclerView
     */

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_history_row,parent,false))
    }

    /**
     *This method is use to bind the data into the listView/ RecyclerView.
     *
     * Binds each item in the ArrayList to a View
     *
     * Called when RecyclerView needs a new {@link ViewHodler} of given item type
     *
     * This new ViewHolder should be constructed with a new View that can represent of given type.
     * Can either create a new View manually or inflate a layout file.
     */

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

    //Gets the number of otems in the list
    override fun getItemCount(): Int {
        return items.size
    }
}
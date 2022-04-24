package com.project.mavway1.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.mavway1.R

class ItemListAdapter(val items: ArrayList<String>, private val listener: numItemClicked) : RecyclerView.Adapter<FoodViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.item_common,parent,false)
        val viewHolder = FoodViewHolder(view)
        view.setOnClickListener{
            listener.OnItemClicked(items[viewHolder.absoluteAdapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val currentItem =items[position]
        holder.food_item_view.text = currentItem
    }

    override fun getItemCount(): Int {
        return items.size
    }

}

class FoodViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView)
{
    val food_item_view : TextView = itemView.findViewById(R.id.item_text_view)

}

interface numItemClicked{
    fun OnItemClicked(item :String)
}
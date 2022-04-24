package com.project.mavway1.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.mavway1.R
import com.project.mavway1.models.Item

class ItemListAdapter( private val listener: numItemClicked) : RecyclerView.Adapter<ItemViewHolder>() {

    private val items : ArrayList<Item> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.item_common,parent,false)
        val viewHolder = ItemViewHolder(view)
        view.setOnClickListener{
            listener.OnItemClicked(items[viewHolder.absoluteAdapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem =items[position]
        holder.item_view.text = currentItem.name
        Glide.with(holder.item_view.context).load(currentItem.imageURL).into(holder.image)
    }

    override fun getItemCount(): Int {
        return items.size
    }
    fun updateItems(updatedItems : ArrayList<Item>)
    {
        items.clear()
        items.addAll(updatedItems)

        notifyDataSetChanged()
    }

}

class ItemViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView)
{
    val item_view : TextView = itemView.findViewById(R.id.item_text_view)
    val image : ImageView = itemView.findViewById(R.id.item_image_view)

}

interface numItemClicked{

    fun OnItemClicked(item :Item)
}
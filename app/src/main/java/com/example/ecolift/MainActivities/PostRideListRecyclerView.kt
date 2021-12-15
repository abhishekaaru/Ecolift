package com.example.ecolift.MainActivities

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ecolift.Data_Classes.AllPostedRide
import com.example.ecolift.R

class PostRideListRecyclerView: RecyclerView.Adapter<PostRideListRecyclerView.PostListAdapter>() {

    private val allPostRideList = ArrayList<AllPostedRide>()

    inner class PostListAdapter(itemView: View): RecyclerView.ViewHolder(itemView) {

        val pickup: TextView = itemView.findViewById(R.id.pickup_name)
        val destination:TextView = itemView.findViewById(R.id.destination_name)
        val date:TextView = itemView.findViewById(R.id.date)
        val time:TextView = itemView.findViewById(R.id.time)
        val seats:TextView = itemView.findViewById(R.id.seats)
        val amount:TextView = itemView.findViewById(R.id.amount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostListAdapter {
        val viewHolder = PostListAdapter(LayoutInflater.from(parent.context).inflate(R.layout.allposted_ride_recyclerview_format,parent,false))
        return viewHolder
    }

    override fun onBindViewHolder(holder:PostListAdapter, position: Int) {
        val positionOnScreen = allPostRideList[position]
        holder.pickup.text = "Pickup: " + positionOnScreen.pickup
        holder.destination.text = "Destination: " +   positionOnScreen.destination
        holder.date.text = "Date: " +  positionOnScreen.date
        holder.time.text = "Time: " + positionOnScreen.time
        holder.seats.text = "Seats: " + positionOnScreen.seats
        holder.amount.text = "Amount: " + positionOnScreen.amount
    }

    override fun getItemCount(): Int {
        return allPostRideList.size
    }

    fun updateAll(itemList:List<AllPostedRide>){
        allPostRideList.clear()
        Log.d("succes it",itemList.toString())
        allPostRideList.addAll(itemList)
        notifyDataSetChanged()
    }

}
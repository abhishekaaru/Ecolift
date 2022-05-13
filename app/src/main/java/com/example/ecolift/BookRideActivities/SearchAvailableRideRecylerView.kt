package com.example.ecolift.BookRideActivities

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ecolift.Data_Classes.AllPostedRide
import com.example.ecolift.MainActivities.PostRideListRecyclerView
import com.example.ecolift.R

class SearchAvailableRideRecylerView(val context: Context): RecyclerView.Adapter<SearchAvailableRideRecylerView.SearchListAdapter>() {


    private val allPostRideList = ArrayList<AllPostedRide>()

    inner class SearchListAdapter(itemView: View): RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener{
                val intent = Intent(context, BookActivity::class.java)
                intent.putExtra("_id",owner_id.text)
                intent.putExtra("pickup",pickup.text)
                intent.putExtra("destination",destination.text)
                context.startActivity(intent)
            }
        }

        val pickup: TextView = itemView.findViewById(R.id.pickup_name)
        val owner_id:TextView = itemView.findViewById(R.id._id)
        val destination: TextView = itemView.findViewById(R.id.destination_name)
        val date: TextView = itemView.findViewById(R.id.date)
        val time: TextView = itemView.findViewById(R.id.time)
        val seats: TextView = itemView.findViewById(R.id.seats)
        val amount: TextView = itemView.findViewById(R.id.amount)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchListAdapter {
        val viewHolder = SearchListAdapter(LayoutInflater.from(parent.context).inflate(R.layout.allposted_ride_recyclerview_format,parent,false))
        return viewHolder
    }

    override fun onBindViewHolder(holder:SearchListAdapter, position: Int) {
        val positionOnScreen = allPostRideList[position]
        holder.pickup.text = "Pickup: " + positionOnScreen.pickup
        holder.destination.text = "Destination: " +   positionOnScreen.destination
        holder.date.text = "Date: " +  positionOnScreen.date
        holder.time.text = "Time: " + positionOnScreen.time
        holder.seats.text = "Seats: " + positionOnScreen.seats
        holder.amount.text = "Amount: " + positionOnScreen.amount
        holder.owner_id.text = positionOnScreen._id
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
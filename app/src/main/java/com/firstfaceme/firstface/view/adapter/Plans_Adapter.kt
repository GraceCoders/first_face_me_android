package com.firstfaceme.firstface.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.firstfaceme.firstface.R
import com.firstfaceme.firstface.model.subscription.Data
import com.firstfaceme.firstface.view.activity.GoPremiumActivity
import kotlinx.android.synthetic.main.item_plan_details.view.*

class Plans_Adapter(val activity: GoPremiumActivity,var  data: List<Data>) : RecyclerView.Adapter<Plans_Adapter.MyViewHolderFav>() {

    var mPosition=-1
    var onItemClick: ((pos: Int, view: View,data:Data) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderFav {
        return MyViewHolderFav(LayoutInflater.from(activity).inflate(R.layout.item_plan_details, parent, false))
    }

    override fun getItemCount(): Int {
        return  data.size
    }

    override fun onBindViewHolder(holder: MyViewHolderFav, position: Int) {
holder.itemView.tvValidity.text=data[position].name
holder.itemView.tvPrice.text="$ "+data[position].price.toString()

        if(mPosition==position)
        {
            holder.itemView.liPlan.setBackgroundDrawable(ContextCompat.getDrawable(activity.applicationContext, R.drawable.boarder_plan_selected));

        }
        else
        {
            holder.itemView.liPlan.setBackgroundDrawable(ContextCompat.getDrawable(activity.applicationContext, R.drawable.boarder_plan));

        }
    }
    inner class MyViewHolderFav(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {


        override fun onClick(v: View) {
          mPosition=adapterPosition

            onItemClick?.invoke(adapterPosition, v,data[adapterPosition])
            notifyDataSetChanged()
        }

        init {
            itemView.setOnClickListener(this)
        }
    }
}

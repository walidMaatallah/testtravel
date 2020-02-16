package com.travelcar.test.ui.main.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.travelcar.test.R
import com.travelcar.test.model.Car
import kotlinx.android.synthetic.main.car_item.view.*


class CarsAdapter(
    private val context: Context,
    private val carListClickEventListener: CarListClickEventListener
) : RecyclerView.Adapter<CarsAdapter.CarViewHolder>() {
    private  var carsList: List<Car> = listOf()

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): CarViewHolder {

        return CarViewHolder(LayoutInflater.from(context).inflate(R.layout.car_item, parent, false))
    }

    override fun onBindViewHolder(@NonNull holder: CarViewHolder, position: Int) {
        val car = carsList[position]
        holder.carMake.text = car.make
        holder.carModel.text = car.model
        holder.carYear.text = car.year.toString()
    }

    fun setData(newData: List<Car>) {
        carsList = newData
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return carsList.size
    }

    //ViewHolder
    class CarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val carMake: TextView = itemView.car_make
        val carModel: TextView = itemView.car_model
        val carYear: TextView = itemView.car_year
    }
}
package com.travelcar.test.ui.main.car

import android.content.Context
import android.graphics.Typeface
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.travelcar.test.R
import com.travelcar.test.model.Car
import kotlinx.android.synthetic.main.car_item.view.*


class CarsAdapter(
    private val context: Context,
    private val carListClickEventListener: CarListClickEventListener
) : RecyclerView.Adapter<CarsAdapter.CarViewHolder>() {
    private var carsList: List<Car> = listOf()
    private var filter: String = ""

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): CarViewHolder {

        return CarViewHolder(LayoutInflater.from(context).inflate(R.layout.car_item, parent, false))
    }

    override fun onBindViewHolder(@NonNull holder: CarViewHolder, position: Int) {
        val car = carsList[position]
        holder.displayData(car)
    }

    fun setData(newData: List<Car>, filter: String) {
        this.filter = filter
        carsList = newData
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return carsList.size
    }

    //ViewHolder
    inner class CarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val carMake: TextView = itemView.car_make
        private val carYear: TextView = itemView.car_year
        private val image: AppCompatImageView = itemView.image
        private val equipmentView: TextView = itemView.equipement


        fun displayData(car: Car) {
            carMake.text = getHighlightText(car)

            //carMake.text = car.make.plus(" - ${car.model}")
            carYear.text = context.getString(R.string.year, car.year.toString())

            var equipment = ""
            car.equipments?.forEach {
                equipment = equipment.plus("- ").plus(it).plus("\n")
            }
            equipmentView.text = equipment

            Glide.with(context).load(car.picture).into(image)

            itemView.item_car.setOnClickListener {
                carListClickEventListener.onCarItemClick(car, image)
            }

        }

        private fun getHighlightText(car: Car): CharSequence {
            val name = car.make.plus(" - ${car.model}")
            return if (filter.isNotBlank()) {
                val startPos = name.toUpperCase().indexOf(filter.toUpperCase())
                val endPos = startPos + filter.length
                val spannable = SpannableString(name)
                val styleSpan = StyleSpan(Typeface.BOLD)
                if (startPos >= 0)
                    spannable.setSpan(styleSpan, startPos, endPos, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                spannable
            } else {
                name
            }
        }
    }


}
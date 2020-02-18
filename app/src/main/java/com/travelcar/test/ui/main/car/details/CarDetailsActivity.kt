package com.travelcar.test.ui.main.car.details

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.travelcar.test.R
import com.travelcar.test.model.Car
import kotlinx.android.synthetic.main.activity_car_details.*

class CarDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_details)

        if (intent.hasExtra(EXTRA_CAR)) {
            val car = intent?.run { extras?.getSerializable(EXTRA_CAR) } as Car
            showDataInUi(car)
        }

        car_details_back_arrow.setOnClickListener {
            supportFinishAfterTransition()
        }
    }

    private fun showDataInUi(car: Car) {
        Glide.with(this).load(car.picture).into(car_details_image_view_car)
        car_make.text = car.make.plus(" - ${car.model}")
        car_year.text = getString(R.string.year, car.year.toString())
        var equipment = ""
        car.equipments?.forEach {
            equipment = equipment.plus("- ").plus(it).plus("\n")
        }
        equipement.text = equipment
    }

    companion object {
        const val EXTRA_CAR = "extra_car"
    }

}

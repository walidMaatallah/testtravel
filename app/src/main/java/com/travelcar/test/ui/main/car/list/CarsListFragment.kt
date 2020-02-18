package com.travelcar.test.ui.main.car.list

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.travelcar.test.R
import com.travelcar.test.model.Car
import com.travelcar.test.ui.main.MainActivity
import com.travelcar.test.ui.main.car.details.CarDetailsActivity
import kotlinx.android.synthetic.main.cars_list_fragment.*


class CarsListFragment : Fragment(),
    CarListClickEventListener,
    CarsListFragmentCallback {


    private lateinit var viewModel: CarsListViewModel
    private lateinit var carsAdapter: CarsAdapter
    private lateinit var list: List<Car>
    private var filter = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as MainActivity).setOnDataListener(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.cars_list_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(CarsListViewModel::class.java)
        carsAdapter =
            CarsAdapter(context!!, this)
        cars_recycler.layoutManager = LinearLayoutManager(activity)
        cars_recycler.adapter = carsAdapter
        viewModel.getLatestNews().observe(viewLifecycleOwner, Observer<List<Car>> { listCars ->
            list = listCars
            Toast.makeText(activity, "zise = ${listCars.size}", Toast.LENGTH_SHORT).show()
            carsAdapter.setData(listCars.filter { car -> car.make.contains(filter, true) }, filter)
        })
    }


    override fun onCarItemClick(car: Car, sharedElementView: AppCompatImageView) {
        Intent(activity, CarDetailsActivity::class.java).apply {
            activity?.let { activity ->
                putExtra(CarDetailsActivity.EXTRA_CAR, car)
                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    activity,
                    sharedElementView,
                    getString(R.string.shared_element_car_key)
                )
                startActivity(this, options.toBundle())
            }
        }

    }


    companion object {
        fun newInstance() =
            CarsListFragment()
    }

    override fun onFilter(text: String) {
        carsAdapter.setData(list.filter { car -> car.make.contains(text, true) }, text)

    }
}

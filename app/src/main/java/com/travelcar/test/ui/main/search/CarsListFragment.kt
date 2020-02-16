package com.travelcar.test.ui.main.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.travelcar.test.R
import com.travelcar.test.model.Car
import kotlinx.android.synthetic.main.cars_list_fragment.*

class CarsListFragment : Fragment(), CarListClickEventListener {


    private lateinit var viewModel: CarsListViewModel
    private lateinit var carsAdapter: CarsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.cars_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CarsListViewModel::class.java)
        viewModel.getLatestNews().observe(viewLifecycleOwner, Observer<List<Car>> { listCars ->
            Toast.makeText(activity, "zise = ${listCars.size}", Toast.LENGTH_SHORT).show()
            carsAdapter.setData(listCars)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        carsAdapter = CarsAdapter(context!!, this)
        cars_recycler.layoutManager = LinearLayoutManager(activity)
        cars_recycler.adapter = carsAdapter
    }

    companion object {
        fun newInstance() = CarsListFragment()
    }

    override fun onCarItemClick(car: Car) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

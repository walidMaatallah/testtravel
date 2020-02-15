package com.travelcar.test.ui.main.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.travelcar.test.R
import com.travelcar.test.model.Car

class CarsListFragment : Fragment() {


    private lateinit var viewModel: CarsListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.cars_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CarsListViewModel::class.java)
       viewModel.getLatestNews()
        viewModel.carsLiveData.observe(viewLifecycleOwner, Observer<MutableList<Car>> {
            Toast.makeText(activity, "zise = ${it.size}" , Toast.LENGTH_SHORT).show()
        })
    }

    companion object {
        fun newInstance() = CarsListFragment()
    }
}

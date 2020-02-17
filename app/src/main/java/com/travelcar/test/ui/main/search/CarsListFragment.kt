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
import com.travelcar.test.ui.main.MainActivity
import kotlinx.android.synthetic.main.cars_list_fragment.*


class CarsListFragment : Fragment(), CarListClickEventListener, FragmentCallback {


    private lateinit var viewModel: CarsListViewModel
    private lateinit var carsAdapter: CarsAdapter
    private lateinit var list : List<Car>
    private  var filter= ""

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



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CarsListViewModel::class.java)
        carsAdapter = CarsAdapter(context!!, this)
        cars_recycler.layoutManager = LinearLayoutManager(activity)
        cars_recycler.adapter = carsAdapter
        viewModel.getLatestNews().observe(viewLifecycleOwner, Observer<List<Car>> { listCars ->
            list = listCars
            Toast.makeText(activity, "zise = ${listCars.size}", Toast.LENGTH_SHORT).show()
            carsAdapter.setData(listCars.filter { car -> car.make.contains(filter, true) }, filter)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    override fun onCarItemClick(car: Car) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }



    companion object {
        fun newInstance() = CarsListFragment()
    }

    override fun onFilter(text : String) {
        carsAdapter.setData(list.filter { car -> car.make.contains(text, true) }, text)

    }
}

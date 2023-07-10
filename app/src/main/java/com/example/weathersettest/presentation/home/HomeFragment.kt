package com.example.weathersettest.presentation.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapptest.tools.location.LocationManagerInteraction
import com.example.weathersettest.R
import com.example.weathersettest.databinding.FragmentHomeBinding
import com.example.weathersettest.presentation.home.action.HomeAction
import com.example.weathersettest.tools.ui.AsyncState
import com.example.weathersettest.tools.ui.adapater.WeatherAdapter
import com.example.weathersettest.tools.ui.adapater.WeatherAdapterInteraction
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(), WeatherAdapterInteraction {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val MP_REQUEST_PERMISSION_LOCATION = 412
    private val viewModel: HomeViewModel by viewModels()

    @Inject
    lateinit var locationManagerInteraction: LocationManagerInteraction

    @Inject
    lateinit var weatherAdapter: WeatherAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.addCityBtn.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_searchFragment)
        }
        lifecycleScope.launchWhenStarted {
            observeUpdateWeathers()
            setUpAdapter()
            setup()
            delay(2000)
        }
        return binding.root
    }

    private fun setUpAdapter() {
        weatherAdapter.setInteraction(this)
        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerCity.layoutManager = layoutManager
        binding.recyclerCity.adapter = weatherAdapter
    }

    private fun setup() {
        if (locationManagerInteraction.checkLocationPermissions()) {
            getLocationPermissions()
        } else {
            viewModel.handleAction(HomeAction.UpdateWeather)
            viewModel.handleAction(HomeAction.LoadWeather)
        }
    }

    private fun observeUpdateWeathers() {
        viewModel.dataState.onEach {
            val (homeUi) = it
            homeUi?.let {
                when (it) {
                    is AsyncState.Success -> {
                        weatherAdapter.setWeathers(it.data?:return@onEach)
                        binding.progressBar.visibility = View.GONE
                        binding.addCityBtn.isEnabled = true
                    }
                    is AsyncState.Failure -> {
                        binding.progressBar.visibility = View.GONE
                        binding.addCityBtn.isEnabled = true

                    }
                    is AsyncState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                }
            }
        }.launchIn(lifecycleScope)
    }

    private fun getLocationPermissions() {
        requestPermissions(
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            MP_REQUEST_PERMISSION_LOCATION
        )
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == MP_REQUEST_PERMISSION_LOCATION) {
            if (grantResults.size == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                grantResults[1] == PackageManager.PERMISSION_GRANTED
            ) {

            } else {
                binding.progressBar.visibility = View.GONE
                binding.addCityBtn.isEnabled = true
            }
            viewModel.handleAction(HomeAction.UpdateWeather)
            viewModel.handleAction(HomeAction.LoadWeather)
        }
    }

    override fun onItemClicked(cityName: String?) {
        val args = Bundle().apply {
            putString("CityName", cityName)
        }
        findNavController().navigate(R.id.action_home_to_detailWeatherFragment, args)
    }

}
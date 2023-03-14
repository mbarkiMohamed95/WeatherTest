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
import com.example.data.utils.DataState
import com.example.weatherapptest.tools.location.LocationManagerInteraction
import com.example.weathersettest.R
import com.example.weathersettest.databinding.FragmentHomeBinding
import com.example.weathersettest.presentation.home.action.HomeAction
import com.example.weathersettest.tools.ui.adapater.WeatherAdapter
import com.example.weathersettest.tools.ui.adapater.WeatherAdapterInteraction
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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
            binding.progressBar.visibility = View.VISIBLE
        }
    }

    private fun observeUpdateWeathers() {
        viewModel.dataState.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Success -> {
                    weatherAdapter.setWeathers(it.data)
                    binding.progressBar.visibility = View.GONE
                    binding.addCityBtn.isEnabled = true
                }
                is DataState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.addCityBtn.isEnabled = true

                }
                else -> {}
            }
        }
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
                viewModel.handleAction(HomeAction.UpdateWeather)
                viewModel.handleAction(HomeAction.LoadWeather)
                lifecycleScope.launch {
                    delay(1000)
                    binding.progressBar.visibility = View.VISIBLE

                }
            }else{
                viewModel.handleAction(HomeAction.UpdateWeather)
                viewModel.handleAction(HomeAction.LoadWeather)
                binding.progressBar.visibility = View.GONE
                binding.addCityBtn.isEnabled = true

            }
        }
    }

    override fun onItemClicked(cityName: String?) {
        val args = Bundle().apply {
            putString("CityName", cityName)
        }
        findNavController().navigate(R.id.action_home_to_detailWeatherFragment, args)
    }

}
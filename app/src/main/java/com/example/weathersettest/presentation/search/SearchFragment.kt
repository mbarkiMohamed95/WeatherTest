package com.example.weathersettest.presentation.search

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.data.utils.DataState
import com.example.weathersettest.presentation.search.action.SearchViewActions
import com.example.weathersettest.databinding.FragmentSearchBinding
import com.example.weathersettest.tools.ui.adapater.WeatherAdapter
import com.example.weathersettest.tools.ui.adapater.WeatherAdapterInteraction
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class SearchFragment : BottomSheetDialogFragment(), WeatherAdapterInteraction {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private var behavior: BottomSheetBehavior<*>? = null
    private val viewModel: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("SearchBottomSheetDialog", "onCreate: ")
    }

    @Inject
    lateinit var weatherAdapter: WeatherAdapter

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        context?.let {
            dialog.setOnShowListener { dialogInterface ->
                val bottomSheetDialog = dialogInterface as BottomSheetDialog?
                bottomSheetDialog ?: return@setOnShowListener
                view ?: return@setOnShowListener
                setupFullHeight(bottomSheetDialog) {
                    val bottomSheet =
                        bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout?
                    behavior = BottomSheetBehavior.from(bottomSheet ?: return@setupFullHeight)
                    behavior?.setPeekHeight(0, false)
                    behavior?.state = BottomSheetBehavior.STATE_EXPANDED
                    behaviorStateChangeListener()
                }
            }
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        binding.closeSearchBtn.setOnClickListener {
                findNavController().navigateUp()
        }
        setUpAdapter()
        setUpEditText()
        observeWeathers()
        return binding.root

    }


    private fun setUpAdapter() {
        val layoutManager = LinearLayoutManager(requireContext())
        weatherAdapter.setInteraction(this)
        binding.listItem.layoutManager = layoutManager
        binding.listItem.adapter = weatherAdapter
    }

    private fun observeWeathers() {
        viewModel.dataState.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Success -> {
                    weatherAdapter.setWeathers(it.data)
                    binding.progressBar.visibility = View.GONE
                }
                is DataState.Error -> {
                    binding.progressBar.visibility = View.GONE
                }
                else -> {}
            }
        }
    }

    private fun setUpEditText() {
        binding.searchEdittext.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                weatherAdapter.setWeathers(emptyList())
                lifecycleScope.launch {
                    viewModel.handleAction(SearchViewActions.LoadSearchedCityWeather(binding.searchEdittext.text.toString()))
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                binding.progressBar.visibility = View.VISIBLE
            }
        })
    }

    private fun setupFullHeight(bottomSheetDialog: BottomSheetDialog, callback: () -> Unit) {
        val bottomSheet =
            bottomSheetDialog.findViewById<View>(
                com.google.android.material.R.id.design_bottom_sheet
            ) as FrameLayout?
        val layoutParams = bottomSheet!!.layoutParams
        val windowHeight = getWindowHeight()
        if (layoutParams != null) {
            layoutParams.height = windowHeight
        }

        bottomSheet.layoutParams = layoutParams
        if (bottomSheetDialog.window != null)
            bottomSheetDialog.window!!.setDimAmount(0f);

        callback()
    }

    private fun getWindowHeight(): Int {
        // Calculate window height for fullscreen use
        val displayMetrics = DisplayMetrics()
        requireActivity()?.let {
            it.windowManager.defaultDisplay.getMetrics(displayMetrics)
        }
        return displayMetrics.heightPixels
    }

    private fun behaviorStateChangeListener() {
        behavior?.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                }
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    dismiss();
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }

        })


    }

    override fun onItemClicked(cityName: String?) {
        lifecycleScope.launch {
            viewModel.handleAction(SearchViewActions.SavePlace)
            delay(500)
            findNavController().navigateUp()
        }
    }


}
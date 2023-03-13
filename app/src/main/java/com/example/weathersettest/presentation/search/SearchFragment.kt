package com.example.weathersettest.presentation.search

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.viewModels
import com.example.weathersettest.databinding.FragmentSearchBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private var behavior: BottomSheetBehavior<*>? = null
    private val viewModel: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("SearchBottomSheetDialog", "onCreate: ")
    }

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
        return binding.root

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

}
package com.parul.imdbapplication.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.parul.imdbapplication.R
import com.parul.imdbapplication.databinding.FragmentFirstBinding
import androidx.databinding.DataBindingUtil
import com.parul.imdbapplication.viewModel.FirstFragmentViewModel
import androidx.lifecycle.Observer
import com.parul.imdbapplication.viewModel.FirstFragmentViewModel.Companion.NAV_BACK
import com.parul.imdbapplication.viewModel.FirstFragmentViewModel.Companion.NAV_NEXT
import com.parul.imdbapplication.viewModel.FirstFragmentViewModel.Companion.NAV_TO_TASK_DETAILS_PAGE

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : BaseFragment(), FirstFragmentEventListener {

    private lateinit var binding: FragmentFirstBinding
    private lateinit var viewModel: FirstFragmentViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_first, container, false)
        viewModel = ViewModelProvider(this, mViewModelFactory).get(FirstFragmentViewModel::class.java)
        subscribeToNavigationChanges()
        //loadRecyclerView()
        setupBackPress()
        return with(binding) {
            firstFragmentEventListener = this@FirstFragment
            root
        }
    }

    private fun subscribeToNavigationChanges() =
        viewModel.navigationCommand.observe(viewLifecycleOwner, Observer {
            when (it) {
                NAV_TO_TASK_DETAILS_PAGE -> {

                }
                NAV_NEXT -> {
                    findNavController().navigate(
                        R.id.action_FirstFragment_to_SecondFragment,
                        Bundle().apply {
                            putString("CITY NAME", "delhi")
                        })

                }
                NAV_BACK -> {
                    navigateUp()
                }

            }
        })

    /*
    * Handle devices back button press
    */
    private fun setupBackPress() {
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    viewModel.onBackPressed()
                }
            })
    }

    override fun onNextButtonClicked() {
        viewModel.onNextButtonClicked()
    }
}

interface FirstFragmentEventListener {
    fun onNextButtonClicked()
}



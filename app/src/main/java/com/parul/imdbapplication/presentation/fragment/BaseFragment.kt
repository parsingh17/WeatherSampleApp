package com.parul.imdbapplication.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.parul.imdbapplication.R

import dagger.android.support.AndroidSupportInjection

import javax.inject.Inject

open class BaseFragment : Fragment() {

    @Inject
    lateinit var mViewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("WEATHER Base activity","$this onCreate")
    }

    override fun onStart() {
        super.onStart()

    }

    override fun onResume() {
        super.onResume()

    }

    override fun onPause() {
        super.onPause()

    }

    override fun onStop() {
        super.onStop()

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("WEATHER Base activity","$this onDestroy")
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("WEATHER Base activity","$this onViewCreated")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("WEATHER Base activity","$this onDestroyView")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        performDependencyInjection()
    }

    private fun performDependencyInjection() {
        AndroidSupportInjection.inject(this)
    }

    fun navigateAdd(destination: Fragment, tag: String, containerViewId: Int = R.id.add_fragment_container,
                    addToBackStack: Boolean = true, enterAnimRes: Int = -1, exitAnimRes: Int = -1,
                    popEnterAnimRes: Int = -1, popExitAnimRes: Int = -1) {
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(enterAnimRes, exitAnimRes, popEnterAnimRes, popExitAnimRes)
        transaction.add(containerViewId, destination, tag)
        if (addToBackStack) transaction.addToBackStack(tag)
        transaction.commit()
    }

    fun navigateUp(forceBypassNavController: Boolean = false) {
        if (forceBypassNavController || !findNavController().navigateUp()) {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

}
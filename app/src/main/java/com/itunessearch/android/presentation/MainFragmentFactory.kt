package com.itunessearch.android.presentation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import javax.inject.Inject

class MainFragmentFactory
@Inject
constructor(): FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {

        return when(className) {
            MainFragment::class.java.name -> {
                MainFragment()
            }
            else -> super.instantiate(classLoader, className)
        }
    }
}
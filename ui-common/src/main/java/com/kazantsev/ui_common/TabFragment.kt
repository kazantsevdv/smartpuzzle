package com.kazantsev.ui_common

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigationrail.NavigationRailView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TabFragment : Fragment(R.layout.fragment_main) {

    private val viewModel: TabViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       val  bottomNavigationView:BottomNavigationView? = view.findViewById(R.id.bottom_navigation)
        val navigationRail:NavigationRailView?=view.findViewById(R.id.navigation_rail)
        val navHost =
            childFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHost.navController

        navigationRail?.let{NavigationUI.setupWithNavController(it, navController)  }
        bottomNavigationView?.let { NavigationUI.setupWithNavController(it, navController) }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.favoriteCountUseCase()
                .flowWithLifecycle(lifecycle)
                .collectLatest {
                    bottomNavigationView?.getOrCreateBadge(R.id.favorite_flow)?.let {badge->
                        badge.isVisible = it > 0
                        badge.number = it
                    }
                    navigationRail?.getOrCreateBadge(R.id.favorite_flow)?.let {badge->
                        badge.isVisible = it > 0
                        badge.number = it
                    }
                }
        }
    }

}
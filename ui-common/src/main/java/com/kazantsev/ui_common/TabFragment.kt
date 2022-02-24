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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TabFragment : Fragment(R.layout.fragment_main) {

    private val viewModel: TabViewModel by viewModels()
    lateinit var bottomNavigationView: BottomNavigationView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomNavigationView = view.findViewById(R.id.bottom_navigation)
        val navHost =
            childFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHost.navController
        NavigationUI.setupWithNavController(bottomNavigationView, navController)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.favoriteCountUseCase()
                .flowWithLifecycle(lifecycle)
                .collectLatest {
                    val badge = bottomNavigationView.getOrCreateBadge(R.id.favorite_flow)
                    badge.isVisible = it > 0
                    badge.number = it
                }
        }
    }
}
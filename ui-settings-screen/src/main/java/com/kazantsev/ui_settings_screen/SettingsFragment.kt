package com.kazantsev.ui_settings_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.kazantsev.domain.model.Difficult
import com.kazantsev.ui_common.base.BaseFragment
import com.kazantsev.ui_settings_screen.databinding.SettingsFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingsFragment : BaseFragment<SettingsFragmentBinding>() {
    private val viewModel: SettingsViewModel by viewModels()
    private var difficult: Difficult = Difficult.All
    private var notShowSolved: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.preferenceFlowUseCase().collect {
                    difficult = Difficult.valueOf(it.difficult)
                    notShowSolved = it.notShowSolved
                    renderView()
                }
            }
        }
        setClickListener()
    }

    private fun setClickListener() {
        binding.btClear.setOnClickListener { viewModel.clearSolved() }
              binding.notShowSolved.setOnClickListener { viewModel.setShowSolved(notShowSolved.not()) }
    }

    private fun renderView() {
        binding.all.isSelected = difficult == Difficult.All
        binding.easy.isSelected = difficult == Difficult.Easy
        binding.medium.isSelected = difficult == Difficult.Medium
        binding.hard.isSelected = difficult == Difficult.Hard
        when (difficult) {
            Difficult.Easy -> binding.easy.isChecked = true
            Difficult.Medium -> binding.medium.isChecked = true
            Difficult.Hard -> binding.hard.isChecked = true
            else -> binding.all.isChecked = true
        }
        binding.notShowSolved.isChecked = notShowSolved
        binding.rgFilter.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.all -> viewModel.setFilter(Difficult.All)
                R.id.easy -> viewModel.setFilter(Difficult.Easy)
                R.id.medium -> viewModel.setFilter(Difficult.Medium)
                R.id.hard -> viewModel.setFilter(Difficult.Hard)
            }
        }
    }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = SettingsFragmentBinding.inflate(inflater, container, false)
}
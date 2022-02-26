package com.kazantsev.category_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.kazantsev.category_screen.model.CategoryUi
import com.kazantsev.category_screen.databinding.CategoryFragmentBinding
import com.kazantsev.navigation.MainNavGraphDirections
import com.kazantsev.navigation.RootNavDirections
import com.kazantsev.ui_common.base.BaseFragment
import com.kazantsev.ui_common.util.findTopNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoryFragment : BaseFragment<CategoryFragmentBinding>() {

    private val viewModel: CategoryViewModel by viewModels()
    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        CategoryAdapter(onListItemClickListener)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.list.adapter = adapter
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loadCategory().collect {
                    adapter.submitList(it)
                }
            }
        }
    }

    private val onListItemClickListener: OnListItemClickListener =
        object : OnListItemClickListener {
            override fun onItemClick(category: CategoryUi) {
                findNavController().navigate(
                    MainNavGraphDirections.actionPuzzleFlow(
                        category.id
                    )
                )
            }
        }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = CategoryFragmentBinding.inflate(inflater, container, false)
}
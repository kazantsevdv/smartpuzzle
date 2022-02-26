package com.kazantsev.detail_screen

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kazantsev.detail_screen.databinding.DetailFragmentBinding
import com.kazantsev.domain.model.Puzzle
import com.kazantsev.ui_common.base.BaseFragment
import com.kazantsev.ui_common.util.Util
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : BaseFragment<DetailFragmentBinding>() {
    private val args: DetailFragmentArgs by navArgs()
    private val viewModel: DetailViewModel by viewModels()
    private lateinit var puzzle: Puzzle
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbarNavigation()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loadPuzzle(args.id).collect {
                    puzzle = it
                    renderUi(it)
                }
            }
        }
        binding.ivFavorite.setOnClickListener { viewModel.setFavorite(puzzle) }
        binding.btSolved.setOnClickListener { viewModel.setSolved(puzzle) }
    }

    private fun renderUi(data: Puzzle) {
        binding.wvAnswer.isVisible = data.solved
        binding.tvName.text = data.name
        binding.wvQuestion.loadDataWithBaseURL(null, data.question, "text/html", "UTF-8", null)
        binding.wvAnswer.loadDataWithBaseURL(null, data.answer, "text/html", "UTF-8", null)
        binding.ivFavorite.setColorFilter(Util.favoriteColor(data.favorite))
        binding.btSolved.isVisible = !data.solved
        binding.tvAnswer.isVisible = data.solved
        binding.ivInfo.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(data.link))
            startActivity(browserIntent)
        }
    }

    private fun initToolbarNavigation() {
        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = DetailFragmentBinding.inflate(inflater, container, false)
}
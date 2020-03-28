package com.chesire.nekome.app.discover

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.chesire.lifecyklelog.LogLifecykle
import com.chesire.nekome.app.discover.databinding.FragmentDiscoverBinding
import com.chesire.nekome.app.discover.trending.TrendingAdapter
import com.chesire.nekome.core.flags.AsyncState
import com.chesire.nekome.core.viewmodel.ViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

/**
 * Fragment to aid with Series discovery.
 */
@LogLifecykle
class DiscoverFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by viewModels<DiscoverViewModel> { viewModelFactory }
    private var _binding: FragmentDiscoverBinding? = null
    private val binding get() = requireNotNull(_binding) { "Binding not set" }
    private val animeTrendingAdapter = TrendingAdapter()
    private val mangaTrendingAdapter = TrendingAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentDiscoverBinding.inflate(inflater, container, false).also { _binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.discoverTrendingAnimeList.apply {
            adapter = animeTrendingAdapter
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
            setHasFixedSize(true)
        }
        binding.discoverTrendingMangaList.apply {
            adapter = mangaTrendingAdapter
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
            setHasFixedSize(true)
        }

        observeTrendingAnime()
        observeTrendingManga()
    }

    private fun observeTrendingAnime() {
        viewModel.trendingAnime.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is AsyncState.Success -> animeTrendingAdapter.submitList(state.data)
                is AsyncState.Error -> {
                    // Show snackbar
                    // Show error view on trending anime
                }
                is AsyncState.Loading -> {
                    // Show loading view
                }
            }
        })
    }

    private fun observeTrendingManga() {
        viewModel.trendingManga.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is AsyncState.Success -> mangaTrendingAdapter.submitList(state.data)
                is AsyncState.Error -> {
                    // Show snackbar
                    // Show error view on trending manga
                }
                is AsyncState.Loading -> {
                    // Show loading view
                }
            }
        })
    }
}

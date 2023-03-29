package com.work.english.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.database.DatabaseReference
import com.work.english.adapter.ArticleAdapter
import com.work.english.adapter.ArticleClickListener
import com.work.english.adapter.RecentArticleAdapter
import com.work.english.data.Article
import com.work.english.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment(), ArticleClickListener {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel by viewModels<HomeViewModel>()

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val articlesListView: RecyclerView = binding.articlesList
       articlesListView.layoutManager = LinearLayoutManager(context)
        val articlesAdapter = ArticleAdapter(emptyList(), this)
        articlesListView.adapter = articlesAdapter

        val recentPager: ViewPager2 = binding.pager
        val recentArticleAdapter = RecentArticleAdapter(emptyList(), this)
        recentPager.adapter = recentArticleAdapter

        homeViewModel.responseLiveData.observe(viewLifecycleOwner) {
            articlesAdapter.articles = it.articles ?: emptyList()
            recentArticleAdapter.articles = it.articles?.take(3) ?: emptyList()
            articlesAdapter.notifyDataSetChanged()
            recentArticleAdapter.notifyDataSetChanged()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onArticleClicked(article: Article) {
        val action = HomeFragmentDirections.actionFragmentHomeToDetail(article.key ?: "1")
        findNavController().navigate(action)
    }
}
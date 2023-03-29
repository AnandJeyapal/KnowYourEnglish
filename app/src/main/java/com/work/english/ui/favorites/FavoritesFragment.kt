package com.work.english.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.work.english.adapter.ArticleAdapter
import com.work.english.adapter.ArticleClickListener
import com.work.english.data.Article
import com.work.english.databinding.FragmentFavoritesBinding
import com.work.english.ui.home.HomeFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment(), ArticleClickListener {

    private var _binding: FragmentFavoritesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val favoritesViewModel =
            ViewModelProvider(this).get(FavoritesViewModel::class.java)

        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val articlesListView: RecyclerView = binding.articlesList
        articlesListView.layoutManager = LinearLayoutManager(context)
        val articlesAdapter = ArticleAdapter(emptyList(), this)
        articlesListView.adapter = articlesAdapter

        favoritesViewModel.responseLiveData.observe(viewLifecycleOwner) {
            articlesAdapter.articles = it.articles ?: emptyList()
            articlesAdapter.notifyDataSetChanged()
        }
        favoritesViewModel.listenFavoriteArticles()
//        favoritesViewModel.listenArticles()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onArticleClicked(article: Article) {
        val action = FavoritesFragmentDirections.actionFragmentFavoritesToDetail(article.key ?: "1")
        findNavController().navigate(action)
    }
}
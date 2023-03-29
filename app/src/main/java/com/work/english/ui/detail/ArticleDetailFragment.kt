package com.work.english.ui.detail

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.work.english.R
import com.work.english.databinding.FragmentArticleDetailBinding
import com.work.english.utils.formatArticle
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ArticleDetailFragment : Fragment() {

    private var _binding: FragmentArticleDetailBinding? = null
    val args: ArticleDetailFragmentArgs by navArgs()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel by viewModels<ArticleDetailViewModel>()

        _binding = FragmentArticleDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val articleImageView: ImageView = binding.articleImage
        val articleTextView: TextView = binding.articleDetail
        val markFavorite: FloatingActionButton = binding.fabFavorite


        val articleId = args.articleId
        homeViewModel.getArticle(articleId)
        markFavorite.setOnClickListener { homeViewModel.toggleFavorite(articleId) }

       homeViewModel.articleLiveDate.observe(viewLifecycleOwner) {
           articleTextView.text = formatArticle( it.article)
           articleImageView.load(it.article?.imageURL) {
               crossfade(true)
               placeholder(R.drawable.placeholder)
           }
           val favoriteIcon = if(it.article?.favorite == true) R.drawable.ic_favorite else R.drawable.ic_not_favorite
           markFavorite.load(favoriteIcon) {
               crossfade(500)
           }
       }
        homeViewModel.statusLiveData.observe(viewLifecycleOwner) {
            if(!it.isNullOrEmpty()) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.work.english.ui.detail

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.work.english.R
import com.work.english.adapter.ArticleAdapter
import com.work.english.databinding.FragmentArticleDetailBinding
import com.work.english.databinding.FragmentHomeBinding
import com.work.english.utils.formatArticle
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ArticleDetailFragment : Fragment() {

    private var _binding: FragmentArticleDetailBinding? = null
    val args: ArticleDetailFragmentArgs by navArgs()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        // The usage of an interface lets you inject your own implementation
//        val menuHost: MenuHost = requireActivity()
//
//        // Add menu items without using the Fragment Menu APIs
//        // Note how we can tie the MenuProvider to the viewLifecycleOwner
//        // and an optional Lifecycle.State (here, RESUMED) to indicate when
//        // the menu should be visible
//        menuHost.addMenuProvider(object : MenuProvider {
//            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
//                // Add menu items here
//                menuInflater.inflate(R.menu.fragment_favorite_menu, menu)
//            }
//
//            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
//                // Handle the menu selection
//                return when (menuItem.itemId) {
//                    R.id.action_favorite -> {
//                        // clearCompletedTasks()
//                        true
//                    }
//                    else -> false
//                }
//            }
//        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
//    }
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

        val articleId = args.articleId
        homeViewModel.getArticle(articleId)

       homeViewModel.articleLiveDate.observe(viewLifecycleOwner) {
           articleTextView.text = formatArticle( it.article)
           articleImageView.load(it.article?.imageURL) {
               crossfade(true)
               placeholder(R.drawable.placeholder)
           }
       }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
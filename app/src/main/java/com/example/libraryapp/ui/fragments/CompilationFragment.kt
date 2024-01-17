package com.example.libraryapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.libraryapp.LibraryApp
import com.example.libraryapp.presentation.viewmodel.CompilationViewModel
import com.example.libraryapp.ui.compose.CompilationBookRow

class CompilationFragment : Fragment() {

    private val viewModel: CompilationViewModel by viewModels {
        (requireActivity().application as LibraryApp).appComponent.viewModelsFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MaterialTheme {
                    CompilationScreen()
                }
            }
        }
    }

    @Composable
    fun CompilationScreen() {
        val categories = viewModel.categoryList.observeAsState()
        if (categories.value.isNullOrEmpty()) {
            viewModel.getCategories()
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                items(categories.value!!) { category ->
                    val books = viewModel.categoryMap[category]!!.observeAsState()
                    CompilationBookRow(
                        title = category,
                        list = books.value,
                        onLoad = viewModel::getBooks
                    )
                }
            }
        }

    }

}
package com.example.libraryapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import com.example.libraryapp.LibraryApp
import com.example.libraryapp.databinding.BottomSheetMenuBinding
import com.example.libraryapp.presentation.viewmodel.BookViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject

private const val COLLAPSED_HEIGHT = 120

class BottomSheetMenuDialogFragment @Inject constructor() : BottomSheetDialogFragment() {

    private var _binding: BottomSheetMenuBinding? = null
    private val binding: BottomSheetMenuBinding
        get() = _binding!!


    private val bookViewModel: BookViewModel by activityViewModels {
        (requireActivity().application as LibraryApp).appComponent.viewModelsFactory()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        val density = requireContext().resources.displayMetrics.density


        bookViewModel.book.observe(viewLifecycleOwner) { book ->
            binding.title.text = book.title
        }

        binding.apply {
            addToFavouriteButton.setOnClickListener {
                setFragmentResult(REQUEST_KEY, bundleOf(MENU_ITEM_KEY to 1))
            }

            addToBookshelfButton.setOnClickListener {
                setFragmentResult(REQUEST_KEY, bundleOf(MENU_ITEM_KEY to 2))
            }

            createBookshelfButton.setOnClickListener {
                setFragmentResult(REQUEST_KEY, bundleOf(MENU_ITEM_KEY to 3))
            }
        }

        dialog?.let {
            val bottomSheet =
                it.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
            val behavior = BottomSheetBehavior.from(bottomSheet)

            behavior.peekHeight = (COLLAPSED_HEIGHT * density).toInt()
            behavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        val TAG = BottomSheetMenuDialogFragment::class.toString()
        const val REQUEST_KEY = "bottom sheet  menu request key"
        const val MENU_ITEM_KEY = "menu item key"
    }
}
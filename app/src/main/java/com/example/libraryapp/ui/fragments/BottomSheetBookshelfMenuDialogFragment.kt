package com.example.libraryapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.viewModels
import com.example.libraryapp.LibraryApp
import com.example.libraryapp.databinding.BottomSheetMenuBinding
import com.example.libraryapp.presentation.adapter.BookshelfAdapter
import com.example.libraryapp.presentation.viewmodel.BookshelfMenuViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

private const val COLLAPSED_HEIGHT = 120

class BottomSheetBookshelfMenuDialogFragment : BottomSheetDialogFragment() {

    private var _binding: BottomSheetMenuBinding? = null
    private val binding: BottomSheetMenuBinding
        get() = _binding!!

    private val viewModel: BookshelfMenuViewModel by viewModels {
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = BookshelfAdapter { bookshelf ->
            viewModel.selectBookshelf(bookshelf)
            parentFragmentManager.popBackStack()
        }
        binding.menuItemsList.adapter = adapter
        viewModel.bookshelfList.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
        }
    }

    override fun onStart() {
        super.onStart()

        val density = requireContext().resources.displayMetrics.density

        binding.title.text = "Select bookshelf"

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
        val TAG = BottomSheetBookshelfMenuDialogFragment::class.toString()
    }
}
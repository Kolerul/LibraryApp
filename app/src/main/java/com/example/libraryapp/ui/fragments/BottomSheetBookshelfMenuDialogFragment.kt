package com.example.libraryapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import com.example.libraryapp.LibraryApp
import com.example.libraryapp.R
import com.example.libraryapp.databinding.BottomSheetBookshelfMenuBinding
import com.example.libraryapp.presentation.adapter.BookshelfAdapter
import com.example.libraryapp.presentation.viewmodel.BookshelfMenuViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject

private const val COLLAPSED_HEIGHT = 120

class BottomSheetBookshelfMenuDialogFragment @Inject constructor() : BottomSheetDialogFragment() {

    private var _binding: BottomSheetBookshelfMenuBinding? = null
    private val binding: BottomSheetBookshelfMenuBinding
        get() = _binding!!

    private val viewModel: BookshelfMenuViewModel by activityViewModels {
        (requireActivity().application as LibraryApp).appComponent.viewModelsFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetBookshelfMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = BookshelfAdapter { bookshelf ->
            parentFragmentManager.setFragmentResult(
                CreateBookshelfDialogFragment.REQUEST_KEY, bundleOf(
                    CreateBookshelfDialogFragment.RESPONSE_KEY to bookshelf.bookshelfTitle
                )
            )
            dialog?.dismiss()
        }
        binding.menuItemsList.adapter = adapter
        viewModel.bookshelfList.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
        }
    }

    override fun onStart() {
        super.onStart()

        val density = requireContext().resources.displayMetrics.density

        binding.title.text = getString(R.string.select_bookshelf)

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
        const val REQUEST_KEY = "choice bookshelf request key"
        const val RESPONSE_KEY = "response key"
    }
}
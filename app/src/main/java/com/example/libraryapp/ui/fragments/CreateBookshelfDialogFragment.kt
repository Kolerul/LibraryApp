package com.example.libraryapp.ui.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.example.libraryapp.R
import com.example.libraryapp.databinding.FragmentNewBookshelfBinding
import javax.inject.Inject

class CreateBookshelfDialogFragment @Inject constructor() : DialogFragment() {

    private var _binding: FragmentNewBookshelfBinding? = null
    private val binding: FragmentNewBookshelfBinding
        get() = _binding!!


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = FragmentNewBookshelfBinding.inflate(layoutInflater)

        val listener = DialogInterface.OnClickListener { _, which ->
            if (which == DialogInterface.BUTTON_POSITIVE) {
                parentFragmentManager.setFragmentResult(
                    REQUEST_KEY, bundleOf(
                        RESPONSE_KEY to binding.bookshelfTitleEditText.text.toString()
                    )
                )
            }
            dismiss()
        }

        val dialog = AlertDialog.Builder(requireContext())
            .setPositiveButton(R.string.create, listener)
            .setNeutralButton(R.string.cancel, listener)
            .setView(binding.root)

        return dialog.create()
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        val TAG = CreateBookshelfDialogFragment::class.java.simpleName
        const val REQUEST_KEY = "create bookshelf request key"
        const val RESPONSE_KEY = "response key"
    }
}
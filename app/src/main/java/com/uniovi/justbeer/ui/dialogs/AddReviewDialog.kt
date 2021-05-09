package com.uniovi.justbeer.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.google.firebase.auth.FirebaseAuth
import com.uniovi.justbeer.R
import com.uniovi.justbeer.databinding.DialogAddReviewBinding
import com.uniovi.justbeer.model.domain.Review
import com.uniovi.justbeer.ui.activities.details.ReviewsViewModel

class AddReviewDialog : DialogFragment() {
    companion object {
        const val TAG = "AddReviewDialog"
        const val BEERID = "beerId"

        fun newInstance(beerId: Long) =
            AddReviewDialog().apply {
                arguments = Bundle().apply {
                    putLong(BEERID, beerId)
                }
            }
    }

    private var _binding: DialogAddReviewBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ReviewsViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogAddReviewBinding.inflate(LayoutInflater.from(context))
        setLimits(0, 5)
        val builder = AlertDialog.Builder(activity)
        builder.setView(binding.root)
            .setPositiveButton(getString(R.string.save)) { dialog: DialogInterface, _: Int ->
                val review = Review(
                    FirebaseAuth.getInstance().currentUser.email,
                    binding.commentEditText.text.toString(),
                    binding.scoreNumberPicker.value,
                    requireArguments().getLong(BEERID)
                )
                viewModel.requestAddReview(review)
            }
            .setNegativeButton(getString(R.string.cancel)) { dialog: DialogInterface, _: Int ->
                dialog.cancel()
            }
        return builder.create()
    }

    private fun setLimits(from: Int, to: Int) {
        binding.scoreNumberPicker.minValue = from
        binding.scoreNumberPicker.maxValue = to
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
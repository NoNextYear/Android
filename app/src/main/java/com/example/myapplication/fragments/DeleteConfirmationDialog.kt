package com.example.myapplication.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.myapplication.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.myapplication.fragments.DeleteConfirmationDialog2

class DeleteConfirmationDialog : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_delete_confirmation, container, false)

        view.findViewById<Button>(R.id.btn_yes).setOnClickListener {
            val nextDialog = DeleteConfirmationDialog2()
            nextDialog.show(parentFragmentManager, "DeleteConfirmationDialog2")
            dismiss()
        }

        view.findViewById<Button>(R.id.btn_no).setOnClickListener {
            dismiss()
        }

        return view
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(requireActivity(), R.style.CustomDialogTheme)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }
}

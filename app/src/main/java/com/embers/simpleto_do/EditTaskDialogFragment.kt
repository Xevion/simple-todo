package com.embers.simpleto_do

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import java.lang.IllegalStateException

class EditTaskDialogFragment(private val dismissListener: OnDismissListener) : DialogFragment() {
    private val TAG: String = this::class.java.name

    interface OnDismissListener {
        fun onDismiss(text: String, submitted: Boolean)
    }

    private var text: String = ""
    private var submitted: Boolean = false

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val input = EditText(it)
            input.inputType = InputType.TYPE_CLASS_TEXT

            val builder = AlertDialog.Builder(it)
            builder.setMessage(R.string.dialog_edit_task)
                    .setView(input)
                .setNegativeButton(R.string.dialog_negative) {_, _ ->}
                .setPositiveButton(R.string.dialog_positive) {_, _ -> text = input.text.toString(); submitted = true}

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)

        dismissListener.onDismiss(text, submitted)
    }
}

package com.awesome.zach.projectolympus

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import kotlinx.android.synthetic.main.dialog_filename.*
import java.lang.ClassCastException

class FilenameDialogFragment : DialogFragment(), AdapterView.OnItemSelectedListener{

    companion object {
        const val TXT: String = "txt"
        const val MD: String = "md"
    }

    var fileType: String = TXT
    var filename: String = ""

    // Use this instance of the interface to deliver action events
    internal lateinit var mListener: FilenameDialogListener

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    interface FilenameDialogListener {
        fun onDialogPositiveClick(dialog: DialogFragment)
        fun onDialogNegativeClick(dialog: DialogFragment)
    }

    // Override the Fragment.onAttach() method to instantiate the FilenameDialogListener
    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the FilenameDialogListener so we can send events to the host
            mListener = context as FilenameDialogListener
        } catch (e: ClassCastException) {
            // The activity doesn't implement the interface, throw exception
            throw ClassCastException((context.toString() + " must implement FilenameDialogListener"))
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)

            val view: View = activity!!.layoutInflater.inflate(R.layout.dialog_filename, null)
            val editText = view.findViewById<EditText>(R.id.filename)
            editText.setText(filename)

            initializeSpinner(view)

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(view)
                // Add action buttons
                .setPositiveButton(
                    R.string.save
                ) { dialog, id ->
                    filename = editText.text.toString()
                    mListener.onDialogPositiveClick(this)
                }
                .setNegativeButton(
                    R.string.cancel
                ) { dialog, id ->
                    mListener.onDialogNegativeClick(this)
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private fun initializeSpinner(view: View) {
        val spinner: Spinner = view.findViewById(R.id.fileType)

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            context,
            R.array.filetypes_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        val item: String = parent.getItemAtPosition(position) as String
        fileType = item
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        fileType = MD
    }


}
package com.awesome.zach.projectolympus

import android.content.Context
import android.content.Intent
import android.content.Intent.EXTRA_TITLE
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.MultiAutoCompleteTextView
import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedReader
import java.io.File

class MainActivity : AppCompatActivity(), FilenameDialogFragment.FilenameDialogListener {

    var openFilename = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        if (intent.hasExtra(EXTRA_TITLE)) {
            val iFilename = intent.getStringExtra(EXTRA_TITLE)
            open(fullFilename(iFilename))
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_save -> {
                showFilenameDialog()
                return true
            }
            R.id.action_open -> {
                launchOpenActivity()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun launchOpenActivity() {
        val openIntent = Intent(this, OpenActivity::class.java)
        startActivity(openIntent)
    }

    fun save(filename: String) {
        // Get the text area
        val textArea = findViewById<EditText>(R.id.et1)

        // Get the text
        val outString = textArea.text.toString()

        applicationContext.openFileOutput(filename, Context.MODE_PRIVATE).use {
            it.write(outString.toByteArray())
        }
    }



    private fun fileExists(filename: String) : Boolean {
//        val absolutePath = File(filename).absolutePath
//        Log.d(this.localClassName, "$absolutePath attempted open")
//        val success = File(filename).exists()
//        return success

        val success = File(filename).exists()
        return success
    }

    fun open(filename: String) {
        if (fileExists(filename)) {
            val bufferedReader: BufferedReader = File(filename).bufferedReader()
            val inputString = bufferedReader.use { it.readText() }

            // Set the open filename param
            openFilename = File(filename).name

            // Get the text area and set the text
            findViewById<EditText>(R.id.et1).setText(inputString)
        }
    }

    private fun fullFilename(filename: String): String {
        val base_path = filesDir.path
        return "$base_path/$filename"
    }

    private fun showFilenameDialog() {
        // Create an instance of the dialog fragment and show it
        val dialog = FilenameDialogFragment()
        val fParts = openFilename.split(""".""")
        val base = fParts[0]
        dialog.filename = base
        dialog.show(supportFragmentManager, "FilenameDialogFragment")
    }

    override fun onDialogPositiveClick(dialog: DialogFragment) {
        if (dialog is FilenameDialogFragment) {
            val fDialog: FilenameDialogFragment = dialog
            val fName: String = fDialog.filename
            val fType: String = fDialog.fileType

            val sb = java.lang.StringBuilder()
            sb.append(fName).append(".").append(fType)

            save(sb.toString())
        }
    }

    override fun onDialogNegativeClick(dialog: DialogFragment) {
        dialog.dismiss()
    }
}

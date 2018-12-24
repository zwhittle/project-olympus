package com.awesome.zach.projectolympus

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_open.*

class OpenActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private val mNotesList = mutableListOf<Note>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open)
        setSupportActionBar(toolbar)

        prepareNotes()

        viewManager = LinearLayoutManager(this)

        viewAdapter = NotesAdapter(mNotesList)

        recyclerView = findViewById<RecyclerView>(R.id.notes).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter

        }

    }

    private fun prepareNotes() {
        val files = filesDir.listFiles()
        for (f in files) {
            if (!f.isDirectory) {
                val name = f.name
                val note = Note(name, open(name))
                mNotesList.add(note)
                Log.d(this.localClassName, f.absolutePath + " added to mNotesList")
            }
        }
    }

    private fun open(filename: String): String {
//        val bufferedReader: BufferedReader = File(filename).bufferedReader()
//        val inputString = bufferedReader.use { it.readText() }
        return openFileInput(filename).bufferedReader().use { it.readText() }
    }

    private fun openInMain(position: Int) {
        val filename = mNotesList[position].title

        Toast.makeText(this, filename, Toast.LENGTH_SHORT).show()

        // Pass back to the main activity
        val openIntent = Intent(this, MainActivity::class.java)
        openIntent.putExtra(Intent.EXTRA_TEXT, filename)
        openIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(openIntent)
    }

}

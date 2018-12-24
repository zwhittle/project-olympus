package com.awesome.zach.projectolympus

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView

class NotesViewHolder(val view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
    private var note: Note? = null
    private var title: TextView = view.findViewById(R.id.title)
    private var content: TextView = view.findViewById(R.id.content)

    init {
        view.setOnClickListener(this)
    }

    fun bindNote(note: Note) {
        this.note = note
        title.text = note.title
        content.text = note.content
    }

    override fun onClick(v: View) {
        val context = itemView.context
        val openFileIntent = Intent(context, MainActivity::class.java)
        val bundle = Bundle()
        openFileIntent.putExtra(Intent.EXTRA_TITLE, note?.title)
        openFileIntent.putExtra(Intent.EXTRA_TEXT, note?.content)
        context.startActivity(openFileIntent)
    }

    companion object {
        private val NOTE_TITLE = "NOTE_TITLE"
        private val NOTE_CONTENT = "NOTE_CONTENT"
    }

}
package com.awesome.zach.projectolympus

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

class NotesAdapter(private val mNotesList: List<Note>): RecyclerView.Adapter<NotesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val inflatedView = parent.inflate(R.layout.list_row, false)
        return NotesViewHolder(inflatedView)
    }

    override fun getItemCount() = mNotesList.size

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
//        val note = mNotesList[position]
//        holder.title.text = note.title
//        holder.content.text = note.content

        val itemNote = mNotesList[position]
        holder.bindNote(itemNote)
    }

}
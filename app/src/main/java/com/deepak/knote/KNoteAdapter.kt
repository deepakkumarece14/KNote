package com.deepak.knote

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.deepak.knote.db.Note
import org.jetbrains.anko.find

class KNoteAdapter(private val noteList: List<Note>, private val listener: (Note) -> Unit) : RecyclerView.Adapter<KNoteAdapter.KNoteViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): KNoteViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_note,viewGroup,false)
        return KNoteViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: KNoteViewHolder, position: Int) {
        val note = noteList[position]
        viewHolder.noteTitle?.text = note.noteTitle
        viewHolder.noteContent?.text = note.noteContent
        viewHolder.itemView.setOnClickListener { listener(note) }
    }

    override fun getItemCount(): Int = noteList.size

    fun removeNote(position: Int) {
        noteList.drop(position)
        notifyItemRemoved(position)
    }

    class KNoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var noteTitle : TextView? = itemView.find(R.id.item_note_title) as TextView
        var noteContent : TextView? = itemView.find(R.id.item_note_content) as TextView
    }
}
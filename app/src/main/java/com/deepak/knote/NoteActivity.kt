package com.deepak.knote

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import com.deepak.knote.db.Note
import com.deepak.knote.db.NotesRepository
import kotlinx.android.synthetic.main.activity_note.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class NoteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.note_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.action_save_note -> insertNote()
            else -> return false
        }
        return  super.onOptionsItemSelected(item)
    }

    private fun insertNote() {
        val title = noteTitle.text.toString()
        val content = noteContent.text.toString()
        val note = Note(noteTitle = title,noteContent = content)
        if (validateInput(title,content)) {
            NotesRepository(applicationContext).insertNote(note)
            toast("Notes inserted successfully...")
            finish()
            startActivity<MainActivity>()
        }else {
            toast("Field is Empty...")
        }
    }

    private fun validateInput(title: String,content: String): Boolean {
        return !(TextUtils.isEmpty(title) && TextUtils.isEmpty(content))
    }
}

package com.deepak.knote

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import com.deepak.knote.db.MyNoteDatabase
import com.deepak.knote.db.Note
import com.deepak.knote.db.NotesRepository
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: KNoteAdapter
    private lateinit var noteList : List<Note>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        database = MyNoteDatabase.getInstance(this@MainActivity)
        noteList = NotesRepository(applicationContext).getAllNotes()

        val itemDecoration = DividerItemDecoration(applicationContext, DividerItemDecoration.VERTICAL)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.addItemDecoration(itemDecoration)
        adapter = KNoteAdapter(noteList)
        recyclerView.adapter = KNoteAdapter(noteList)
        recyclerView.hasFixedSize()

        val simpleCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.layoutPosition
                if (direction == ItemTouchHelper.LEFT) {
                    val swipedNote = viewHolder.itemView.tag as Note
                    val id = swipedNote.id
                    swipedNote.id = id
                    adapter.removeNote(position)
                    NotesRepository(applicationContext).deleteNote(swipedNote)
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        fab.onClick { startActivity<NoteActivity>() }

    }

    private fun refreshRecyclerView() {
        noteList = NotesRepository(applicationContext).getAllNotes()
        adapter = KNoteAdapter(noteList)
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        refreshRecyclerView()
    }

    override fun onDestroy() {
        super.onDestroy()
        MyNoteDatabase.destroyInstance()
    }

}
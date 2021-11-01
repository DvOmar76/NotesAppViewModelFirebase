package com.example.notesappviewmodel2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notesappviewmodel2.databinding.ItemRowBinding


class RVAdapter( val mainActivity: MainActivity):RecyclerView.Adapter<RVAdapter.RVHolder>() {
    private var notes= emptyList<Note>()
    class RVHolder(view: View):RecyclerView.ViewHolder(view){
        val binding= ItemRowBinding.bind(view)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVAdapter.RVHolder {
        return RVHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.item_row,
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: RVAdapter.RVHolder, position: Int) {

       with(holder) {
           val id =notes[position].id
           val note =notes[position].note
           binding.textView.text=note

           binding.imageButton.setOnClickListener {

             mainActivity.mainViewModel.deleteNote(id)
             Toast.makeText(mainActivity.applicationContext , "item is deleted", Toast.LENGTH_SHORT).show()
           }

           binding.imageButton2.setOnClickListener {
                mainActivity.alertDialog(id,note!!)
           }

        }
    }

    override fun getItemCount()=notes.size

    fun update(notes: List<Note>){
        println("UPDATING DATA")
        this.notes = notes
        mainActivity.binding.recyclerView.adapter = mainActivity.rvAdapter
        mainActivity.binding.recyclerView.layoutManager = LinearLayoutManager(mainActivity)
    }
}
package com.example.notesapp
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.databinding.ItemNoteBinding

class NotesAdapter(
    private var items: List<Note>,
    private val onEdit: (Note) -> Unit,
    private val onDelete: (Note) -> Unit
) : RecyclerView.Adapter<NotesAdapter.NoteVH>() {

    inner class NoteVH(val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteVH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemNoteBinding.inflate(inflater, parent, false)
        return NoteVH(binding)
    }

    override fun onBindViewHolder(holder: NoteVH, position: Int) {
        val note = items[position]
        with(holder.binding) {
            tvTitle.text = note.title
            btnEdit.setOnClickListener { onEdit(note) }
            btnDelete.setOnClickListener { onDelete(note) }
        }
    }

    override fun getItemCount(): Int = items.size

    fun submitList(newItems: List<Note>) {
        items = newItems
        notifyDataSetChanged()
    }
}
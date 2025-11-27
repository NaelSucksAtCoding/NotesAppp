package com.example.notesapp

import android.os.Bundle
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notesapp.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val vm: NotesViewModel by viewModels()
    private lateinit var adapter: NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecycler()
        setupActions()
        observeData()
    }

    private fun setupRecycler() {
        adapter = NotesAdapter(
            items = emptyList(),
            onEdit = { note -> showEditDialog(note) }, // Panggil dialog edit
            onDelete = { note -> vm.deleteNote(note) } // Langsung hapus
        )
        binding.rvNotes.layoutManager = LinearLayoutManager(this)
        binding.rvNotes.adapter = adapter
    }

    private fun setupActions() {
        binding.btnAdd.setOnClickListener {
            val text = binding.etInput.text?.toString().orEmpty()
            vm.addNote(text)
            binding.etInput.setText("")
        }
    }

    private fun showEditDialog(note: Note) {
        val input = EditText(this).apply {
            setText(note.title)
            setSelection(text.length)
        }
        AlertDialog.Builder(this)
            .setTitle("Edit Catatan")
            .setView(input)
            .setPositiveButton("Simpan") { dialog, _ ->
                val newText = input.text.toString().trim()
                if (newText.isNotEmpty()) {
                    vm.updateNote(note.copy(title = newText)) // Update data
                }
                dialog.dismiss()
            }
            .setNegativeButton("Batal") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun observeData() {
        lifecycleScope.launch {
            vm.notes.collectLatest { list ->
                adapter.submitList(list) // Update list di layar
            }
        }
    }
}
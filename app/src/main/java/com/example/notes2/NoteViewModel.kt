package com.example.notes2

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel // Эта аннотация используется для ViewModel, чтобы Hilt мог автоматически внедрять зависимости через конструктор.
class NoteViewModel @Inject constructor(
    private val noteDataAccessObject: NoteDataAccessObject
) : ViewModel() {

    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> = _notes

    val itemTextState = mutableStateOf("")

    init {
        viewModelScope.launch {
            noteDataAccessObject.getAll().collect { employees ->
                _notes.value = employees
            }
        }
    }

    fun addNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            if (itemTextState.value.isNotEmpty()) {
                noteDataAccessObject.insert(note)
                itemTextState.value = ""
            }
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            noteDataAccessObject.update(note)
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            noteDataAccessObject.delete(note)
        }
    }

}
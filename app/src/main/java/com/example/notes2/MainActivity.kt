package com.example.notes2

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint // Эта аннотация используется для Activity, Fragment и других Android-компонентов, чтобы Hilt мог внедрять зависимости.
class MainActivity : ComponentActivity() {

    private val noteViewModel: NoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NotesPage(noteViewModel)
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun NotesPage(noteViewModel: NoteViewModel) {

    val notes by noteViewModel.notes.collectAsState()
    val itemTextState = noteViewModel.itemTextState

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .padding(start = 8.dp, end = 8.dp, top = 8.dp)
                .fillMaxWidth()
                .height(55.dp)
                .background(
                    color = Color.DarkGray,
                    shape = RoundedCornerShape(topStart = 5.dp, topEnd = 5.dp)
                )
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "Заметки",
                fontSize = 20.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        OutlinedTextField(
            value = itemTextState.value,
            onValueChange = { noteViewModel.itemTextState.value = it },
            textStyle = TextStyle(fontSize = 20.sp),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp)
                .align(alignment = Alignment.CenterHorizontally)
        )

        Box(
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
                .fillMaxSize()
                .background(
                    color = Color.LightGray,
                    shape = RoundedCornerShape(bottomEnd = 5.dp, bottomStart = 5.dp)
                )
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
            ) {
                items(notes) { note: Note ->
                    ItemList(
                        note = note,
                        onDeleteClick = {
                            noteViewModel.deleteNote(note)
                        }
                    )
                    Spacer(modifier = Modifier.padding(3.dp))
                }
            }

            FloatingActionButton(
                onClick = { noteViewModel.addNote(
                    Note(text = itemTextState.value)
                ) },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                containerColor = Color.DarkGray,
                contentColor = Color.White
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add Item")
            }
        }
    }
}

@Composable
fun ItemList(note: Note, onDeleteClick: () -> Unit) {

    var showDialog by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .background(
                Color.White,
                shape = RoundedCornerShape(22.dp)
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        ) {
            Text(
                text = note.text,
                fontSize = 20.sp,
                color = Color.Black,
                modifier = Modifier
                    .weight(1f)
            )

            IconButton(
                onClick = {
                    showDialog = true
                },
                modifier = Modifier.size(24.dp)

            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Удалить",
                    tint = Color.Black
                )
            }
        }
    }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                Text(text = "Вы уверены?")
            },
            text = {
                Spacer(modifier = Modifier.height(16.dp))
                Text("Вы действительно хотите удалить эту заметку?")
            },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog = false
                        onDeleteClick()
                    }
                ) {
                    Text("Удалить")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        showDialog = false
                    }
                ) {
                    Text("Отмена")
                }
            }
        )
    }
}

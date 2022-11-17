package com.example.elearningapp.ui.views.notes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.elearningapp.R
import com.example.elearningapp.models.entities.NoteEntity
import com.example.elearningapp.navigation.MenuNavDestination
import com.example.elearningapp.navigation.bottomNavScreens
import com.example.elearningapp.ui.theme.ELearningAppTheme
import com.example.elearningapp.ui.views.components.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.format.DateTimeFormatter

@Composable
fun NotesOverviewScreen(
    allNotesState: StateFlow<List<NoteEntity>>,
    saveNote: (String, String) -> Unit,
    editNote: (Long, String, String) -> Unit,
    deleteNote: (Long) -> Unit,
    filterNotes: (List<NoteEntity>, String) -> List<NoteEntity>
) {
    //MutableState for search bar
    var search by remember { mutableStateOf("") }

    //Content column
    Column(modifier = Modifier
        .fillMaxSize()) {

        //Card content
        Card(
            modifier = Modifier
                .padding(start = 20.dp, top = 20.dp, end = 20.dp)
                .wrapContentSize(),
            shape = RoundedCornerShape(5.dp),
            elevation = 12.dp
        ) {
            //Search bar text field
            OutlinedTextField(modifier = Modifier
                .fillMaxWidth(),
                value = search,
                placeholder = { Text(text = stringResource(R.string.search_for_a_note)) },
                onValueChange = { search = it },
                singleLine = true,
                leadingIcon = {
                    IconButton(onClick = {
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = stringResource(R.string.search),
                            tint = MaterialTheme.colors.primary
                        )
                    }
                },
                trailingIcon = {
                    IconButton(onClick = {
                        search = ""
                    }) {
                        if (search != "") {
                            Icon(
                                imageVector = Icons.Filled.Clear,
                                contentDescription = stringResource(R.string.clear_search)
                            )
                        }
                    }
                }
            )
        }
        //Notes as collected as state
        val notes by allNotesState.collectAsState()
        if (notes.isNotEmpty()) {

            //Filtered notes
            val filteredNotes = filterNotes(notes,search)

            //If filtered notes is not empty, a lacy column of NoteCard is shown
            if (filteredNotes.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier.padding(start = 20.dp, top = 20.dp, end = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    contentPadding = PaddingValues(bottom = 20.dp)
                ) {
                    items(filteredNotes) { note ->
                        NoteCard(note, editNote, deleteNote)
                    }
                }
            } else {
                NoResultsMessage(stringResource(R.string.no_notes_found),Icons.Filled.SearchOff)
            }
        //If there are no notes, icon is shown
        } else {
            Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Icon(
                    imageVector = Icons.Filled.StickyNote2,
                    modifier = Modifier.size(128.dp),
                    contentDescription = stringResource(R.string.notes_icon),
                    tint = MaterialTheme.colors.primary.copy(alpha = 0.5f)
                )
                Text(
                    text = stringResource(R.string.notes_list_empty),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Light,
                    color = MaterialTheme.colors.primary.copy(alpha = 0.5f)
                )
            }
        }
    }
    //Add Note button
    AddNoteButton(saveNote)
}


@Composable
fun NoteCard(
    note: NoteEntity,
    editNote: (Long, String, String) -> Unit,
    deleteNote: (Long) -> Unit
) {
    //MutableState for edit note dialog
    var showEditNoteDialog by remember { mutableStateOf(false) }

    //Note card content
    Card(modifier = Modifier.height(120.dp),
        shape = RoundedCornerShape(5.dp),
        elevation = 12.dp) {

        //Note column content
        Column(modifier = Modifier.padding(start = 20.dp, top= 10.dp, end = 20.dp), verticalArrangement = Arrangement.SpaceBetween) {
            Column(modifier = Modifier.weight(1f)) {

                //Row for title and edit icon
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = note.title,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Icon(modifier = Modifier.clickable( onClick = { showEditNoteDialog = true } ),
                        imageVector = Icons.Outlined.Edit,
                        contentDescription = stringResource(R.string.edit_note),
                        tint = MaterialTheme.colors.primary
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))

                //Note text
                Text(
                    text = note.text,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                    fontWeight = FontWeight.Light
                )
            }
            //Last edited text
            Text(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 4.dp, bottom = 4.dp),
                text = note.lastEdited.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                overflow = TextOverflow.Ellipsis,
                fontSize = 14.sp,
                fontWeight = FontWeight.Light,
                color = Color.LightGray
            )
        }
    }
    //conditional for edit note dialog
    if (showEditNoteDialog) {
        EditNoteDialog({ showEditNoteDialog = false }, note, editNote, deleteNote)
    }
}


@Preview(showBackground = true)
@Composable
fun NotesOverviewScreenPreview() {
    val longText = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut " +
            "labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi " +
            "ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum " +
            "dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia " +
            "deserunt mollit anim id est laborum."
    val notes = listOf(NoteEntity(title = "Title With a Long Long Long Long Long Name", text = longText), NoteEntity(title = "Title", text = "Hello World"), NoteEntity(title = "Title", text = "Hello World"), NoteEntity(title = "Title", text = "Hello World"), NoteEntity(title = "Title", text = "Hello World"))
    //val notes = emptyList<NoteEntity>()

    ELearningAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Scaffold(
                topBar = { TopBar("Notes", {}, {}, {}, {}) },
                bottomBar = {
                    BottomNavBar(
                        bottomNavScreens, {}, MenuNavDestination.NotesOverview
                    )
                }
            ) {
                innerPadding -> Box(modifier = Modifier.padding(innerPadding)) {
                    NotesOverviewScreen(MutableStateFlow(notes), {_,_->}, {_,_,_->}, {}, {_,_-> notes})
                }
            }
        }
    }
}
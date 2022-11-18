package com.example.elearningapp.ui.views.notes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.NoteAdd
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.elearningapp.R
import com.example.elearningapp.models.entities.NoteEntity
import com.example.elearningapp.ui.theme.ELearningAppTheme
import java.time.format.DateTimeFormatter

@Composable
fun EditNoteDialog(
    onDismiss: () -> Unit,
    noteEntity: NoteEntity,
    editNote: (Long, String, String) -> Unit,
    deleteNote: (Long) -> Unit
) {
    //Mutable state for user interaction
    var title by remember { mutableStateOf(noteEntity.title) }
    var noteText by remember { mutableStateOf(noteEntity.text) }

    //Last edited and max car values
    val dateTime = noteEntity.lastEdited
    val maxNoteChars = 500

    AlertDialog(
        text = {
            //Column content
            Column(modifier = Modifier.fillMaxWidth()) {

                //Row for title text field and delete icon
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                    BasicTextField(
                        modifier = Modifier.fillMaxWidth().weight(1f),
                        value = title,
                        singleLine = true,
                        textStyle = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium
                        ),
                        onValueChange = { title = it},
                        decorationBox = { innerTextField ->
                            if (title.isEmpty()) {
                                Text(
                                    text = stringResource(R.string.title),
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                            innerTextField()
                        }
                    )
                    Icon(modifier = Modifier.clickable( onClick = {deleteNote(noteEntity.id); onDismiss.invoke()} ),
                        imageVector = Icons.Outlined.Delete,
                        contentDescription = stringResource(R.string.delete_note),
                        tint = MaterialTheme.colors.primary
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                Divider(color = Color.LightGray, thickness = 1.dp)
                Spacer(modifier = Modifier.height(4.dp))

                //Row for last edited date and max chars
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Light,
                        color = Color.LightGray
                    )
                    Text(
                        text = "${noteText.length}/$maxNoteChars",
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Light,
                        color = Color.LightGray
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))

                //Text field for note message
                BasicTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    value = noteText,
                    singleLine = false,
                    onValueChange = { if (it.length <= maxNoteChars) noteText = it },
                    decorationBox = { innerTextField ->
                        if (noteText.isEmpty()) {
                            Text(text = stringResource(R.string.write_your_note))
                        }
                        innerTextField()
                    }
                )
            }
        },
        onDismissRequest = onDismiss,
        buttons = {
            //Row for bottom Close and Save Note buttons
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp), verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = stringResource(R.string.close),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier.clickable(onClick = onDismiss)
                )
                Button(
                    onClick = {editNote(noteEntity.id,title,noteText)
                                onDismiss.invoke()
                    }) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Outlined.NoteAdd,
                            contentDescription = stringResource(R.string.save_note),
                            tint = MaterialTheme.colors.secondary
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = stringResource(R.string.save_note))
                    }
                }
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun EditNotePreview() {
    val note = NoteEntity(
        title = "Machine Learning Notes",
        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt " +
                "ut labore et dolore magna aliqua. \n" +
                "\n" +
                "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea " +
                "commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum " +
                "dolore eu fugiat nulla pariatur."
    )

    ELearningAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            EditNoteDialog({}, note, {_,_,_->}, {})
        }
    }
}
package com.example.elearningapp.ui.views.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
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
import com.example.elearningapp.ui.theme.ELearningAppTheme
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

@Composable
fun AddNoteButton(saveNote: (String, String) -> Unit) {
    //MutableState for AddNoteDialog
    var showAddNoteDialog by remember { mutableStateOf(false) }

    //Note button box
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
        Button(
            modifier = Modifier.padding(end = 40.dp, bottom = 15.dp),
            onClick = {showAddNoteDialog = true}) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.Edit,
                    contentDescription = stringResource(R.string.add_note),
                    tint = MaterialTheme.colors.secondary
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = stringResource(R.string.add_a_note))
            }
        }
    }
    //AddNoteDialog conditional
    if (showAddNoteDialog) {
        AddNoteDialog({ showAddNoteDialog = false }, saveNote)
    }
}


@Composable
fun AddNoteDialog(onDismiss: () -> Unit, saveNote: (String, String) -> Unit) {
    //MutableState for user interaction
    var title by remember { mutableStateOf("") }
    var noteText by remember { mutableStateOf("") }
    val dateTime = OffsetDateTime.now()
    val maxNoteChars = 500

    //AddNoteDialog
    AlertDialog(
        text = {
            //Dialog content column
            Column(modifier = Modifier.fillMaxWidth()) {
                BasicTextField(
                    modifier = Modifier.fillMaxWidth(),
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
                Spacer(modifier = Modifier.height(20.dp))
                Divider(color = Color.LightGray, thickness = 1.dp)
                Spacer(modifier = Modifier.height(4.dp))

                //Information row for date and number of char
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
            //Bottom row for buttons
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
                    onClick = {saveNote(title,noteText)
                                onDismiss.invoke()}) {
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
fun AddNoteDialogPreview() {
    ELearningAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            AddNoteDialog({},{_,_->})
        }
    }
}
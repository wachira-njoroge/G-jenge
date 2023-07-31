package com.awesome.g_jenge.compose

import android.app.Application
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.awesome.g_jenge.entities.Projects
import com.awesome.g_jenge.entities.Tasks
import com.awesome.g_jenge.viewmodel.AppViewModel

interface Display{
    //Gridview card item
    @Composable
    fun gridItem(text: String) {
        Card(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            backgroundColor = Color.LightGray
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(16.dp)
                    .height(100.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = text, textAlign = TextAlign.Center)
            }
        }
    }
    //Grid view display
    @Composable
    fun gridView(appViewModel: AppViewModel) {
        val savedProjects by appViewModel.allProjects.collectAsState(initial = emptyList())
        Column(modifier = Modifier.fillMaxSize()) {
            // LazyColumn to make the grid items scrollable
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.weight(1f),

                ) {
                items(savedProjects.size) { index ->
                    gridItem(text = savedProjects[index].name)
                }
            }
            // Add Button to add new goal at the bottom
            Button(
                onClick = {
                    Toast.makeText(appViewModel.getApplication(), "Clicked today", Toast.LENGTH_SHORT).show()
                    // 1.Open a dialog for the user to input project details
                    // 2.Validate input
                    // 3.Save project

                    val newProject = Projects(null, "PR-23-07-002","Bills","New")
                    appViewModel.insertProject(newProject)
                },
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(text = "Add A Goal")
            }
        }
    }
    //Task card view
    @Composable
    fun tasksCard(task: Tasks) {
        Card(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .fillMaxWidth(),
            elevation = 2.dp,
            backgroundColor = MaterialTheme.colors.secondary,
            shape = RoundedCornerShape(corner = CornerSize(16.dp))

        ) {

            Row(modifier = Modifier.padding(20.dp)) {
                Column(modifier = Modifier.weight(1f),
                    Arrangement.Center) {
                    Text(
                        text = task.description,
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    )
                    Text(
                        text = "Due on :- " + task.due_date,
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 15.sp
                        )
                    )
                }
            }
        }
    }
    //Tasks by project display
    @Composable
    fun tasksByProject(appviewmodel:AppViewModel,project: String) {
        val tasks by appviewmodel.getProjectTasks(project).collectAsState(initial = emptyList())
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            items(
                tasks
            ) {
                tasksCard(task = it)
            }
        }
    }
}
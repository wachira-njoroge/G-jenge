package com.awesome.g_jenge.compose

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.awesome.g_jenge.R
import com.awesome.g_jenge.entities.Projects
import com.awesome.g_jenge.entities.Tasks
import com.awesome.g_jenge.viewmodel.AppViewModel
import java.time.LocalDateTime
import kotlin.random.Random

interface Display{
    data class Flowers(
        val name: String,
        val price: String
    )
    //Create the entry point to the displayed screens
    @Composable
    fun mainscreen(appViewModel: AppViewModel){
        val navController = rememberNavController()
        val projects by rememberUpdatedState(appViewModel.allProjects.collectAsState(initial = emptyList()))
        NavHost(navController = navController, startDestination = "gridView"){
            composable("gridView"){
                gridView(appViewModel, projects, navController)
            }
            composable(
                "tasksByProject/{projectName}",
                arguments = listOf(
                    navArgument("projectName")
                    {
                        type= NavType.StringType

                    }
                )){
                    backStackEntry ->
                run {
                    val projectName = backStackEntry.arguments?.getString("projectName")
                    val tasks by rememberUpdatedState(appViewModel.getProjectTasks(projectName!!).collectAsState(initial = emptyList()))
                    tasksByProject(appViewModel, tasks)
                }
            }
        }
    }

    //Grid view display
    @Composable
    fun gridView(appViewModel: AppViewModel, projects: State<List<Projects>>, navController: NavController) {
        val options = listOf("New", "In progress","Future")
        var showDialog by remember { mutableStateOf(false) }
        var projectName by remember { mutableStateOf("") }
        var projectOptions = remember { mutableStateOf(false) }
        var currentOption = remember { mutableStateOf(options[0]) }
        //
        Column(modifier = Modifier.fillMaxSize()) {
            // LazyColumn to make the grid items scrollable
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.weight(1f),

                ) {
                items(projects.value) { projects ->
                    gridItem(projects,navController)
                }
            }
            // Button to add new goal at the bottom
            Button(
                onClick = {
                    //Toast.makeText(appViewModel.getApplication(), "Clicked today", Toast.LENGTH_SHORT).show()
                    // 1.Open a dialog for the user to input project details
                    showDialog = true

                },
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(text = "Add A Goal")
            }
            //
            // Dialog for adding a new goal
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = {
                        // Handle when the user dismisses the dialog (e.g., tapping outside)
                        showDialog = false
                    },
                    title = {
                        Text(text = "Add a New Goal")
                    },
                    text = {
                        Column {
                            Text("Project Details:")
                            Spacer(modifier = Modifier.height(4.dp))
                            OutlinedTextField(value = projectName, onValueChange = {
                                projectName = it
                            },
                                label = {
                                    Text("Project Name")
                                },
                                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),

                            )
                            Spacer(modifier = Modifier.height(4.dp))

                        Row(modifier = Modifier.clickable {
                            projectOptions.value = !projectOptions.value
                        }) {
                            Text(text = currentOption.value)
                            DropdownMenu(expanded = projectOptions.value, onDismissRequest = {
                                projectOptions.value = false
                            }) {
                                options.forEach {
                                    DropdownMenuItem(
                                        text = {it},
                                        onClick = {
                                            currentOption.value = it
                                            projectOptions.value = false
                                        })

                                }
                            }
                        }
                    }

                    },

                    confirmButton = {
                        // Handle the action when the user confirms
                        Button(onClick = {
                            // 2.Validate input
                            // 3.Save project

                            val newProject = Projects(null, "PR-${Random.nextInt(1000, 10000)}",projectName,currentOption.value)
                            appViewModel.insertProject(newProject)
                            showDialog = false
                        }) {
                            Text("OK")
                        }
                    },
                    dismissButton = {
                        // Handle the action when the user dismisses
                        Button(onClick = {
                            // Close the dialog
                            showDialog = false
                        }) {
                            Text("Cancel")
                        }
                    }
                )
            }
        }
    }
    //Gridview card item
    @Composable
    fun gridItem(project: Projects, navController: NavController) {

        Card(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .clickable {
                    navController.navigate("tasksByProject/${project.name}")
                },
            shape = RoundedCornerShape(8.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(8.dp)
                    .height(100.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(project.name, textAlign = TextAlign.Center)
            }
        }
    }
    //Task card view
    @Composable
    fun tasksCard(task: Tasks) {
        Card(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.secondary),
//            elevation = 2.dp,
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
    fun tasksByProject(appViewModel: AppViewModel, tasks: State<List<Tasks>>) {
        //
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            LazyColumn(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(bottom = 64.dp),
                //contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                items(
                    tasks.value
                ) {
                    tasksCard(task = it)
                }
            }

            // Button to add new goal at the bottom
            Button(
                onClick = {
                    Toast.makeText(appViewModel.getApplication(), "Clicked today", Toast.LENGTH_SHORT).show()
                    // 1.Open a dialog for the user to input project details
                    // 2.Validate input
                    // 3.Save project

                    val newTask = Tasks(null, "TK-23-07-002","Bills","Pending",
                        LocalDateTime.now().toString(),LocalDateTime.now().toString(),false,1)
                    appViewModel.insertTask(newTask)
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(4.dp)
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(text = "Schedule something")
            }
        }
    }

    //
 
    @Composable
    private fun FlowerCard(flower: Flowers) {
        Card(
            modifier = Modifier
                .padding(5.dp)
                .width(170.dp),
            shape = RoundedCornerShape(14.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Image(
                    painter = painterResource(id = R.mipmap.pic2_foreground),
                    contentDescription = null,
                    modifier = Modifier.width(180.dp)
                )

                Row(modifier = Modifier.padding(10.dp)) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = flower.name,
                            style = TextStyle(
                                fontSize = 16.sp
                            )
                        )
                        Text(
                            text = flower.price,
                            style = TextStyle(
                                fontSize = 16.sp
                            )
                        )
                    }
                    IconButton(
                        onClick = { },
                        modifier = Modifier.background(
                            color = Color.Green,
                            shape = RoundedCornerShape(10.dp)
                        )
                    ) {
                        Icon(Icons.Default.Add, tint = Color.White, contentDescription = null)
                    }
                }
            }
        }
    }
}
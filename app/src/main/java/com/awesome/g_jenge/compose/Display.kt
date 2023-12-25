package com.awesome.g_jenge.compose

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.Card
//import androidx.compose.material.ExperimentalMaterialApi
//import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.awesome.g_jenge.entities.Projects
import com.awesome.g_jenge.entities.Tasks
import com.awesome.g_jenge.viewmodel.AppViewModel
import java.time.LocalDateTime

interface Display{
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
                    .padding(16.dp)
                    .height(100.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(project.name, textAlign = TextAlign.Center)
            }
        }
    }
    //Grid view display
    @Composable
    fun gridView(appViewModel: AppViewModel, projects: State<List<Projects>>, navController: NavController) {

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
//            elevation = 2.dp,
//            backgroundColor = MaterialTheme.colors.secondary,
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
                modifier = Modifier.align(Alignment.TopStart).padding(bottom = 64.dp),
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
}
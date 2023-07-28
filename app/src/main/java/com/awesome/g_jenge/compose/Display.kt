package com.awesome.g_jenge.compose

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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class Display() {

    data class EmployDetails(val id: Int,
                             val title: String,
                             val sex: String,
                             val age: Int,
                             val description: String)
    //
    object Details {

        val EmployDetailsList = listOf(
            EmployDetails(
                id = 1,
                title = "Rohan",
                sex = "Male",
                age = 24,
                description = " Don't judge each day by the harvest you reap but by the seeds that you plant.” - ...",
                //ImageId = R.drawable.rohan
            ),
            EmployDetails(
                id = 2,
                title = "Roy",
                sex = "male",
                age = 25,
                description = " Don't judge each day by the harvest you reap but by the seeds that you plant.” - ...",
                //ImageId = R.drawable.roy
            ),
            EmployDetails(
                id = 3,
                title = "Vishal",
                sex = "Male",
                age = 29,
                description = " Don't judge each day by the harvest you reap but by the seeds that you plant.” - ...",
                //ImageId = R.drawable.vishal
            ),
            EmployDetails(
                id = 4,
                title = "Nikhil",
                sex = "Male",
                age = 27,
                description = " Don't judge each day by the harvest you reap but by the seeds that you plant.” - ...",
                //ImageId = R.drawable.nikhil
            ),
            EmployDetails(
                id = 2,
                title = "Roy Ray",
                sex = "male",
                age = 25,
                description = " Don't judge each day by the harvest you reap but by the seeds that you plant.” - ...",
                //ImageId = R.drawable.roy
            )
        )
    }
    @Composable
    fun EmployeeCard(emp: EmployDetails) {
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
                        text = emp.title,
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    )
                    Text(
                        text = "Age :- "+emp.age.toString(),
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 15.sp
                        )
                    )
                    Text(
                        text = "Sex :- "+emp.sex,
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 15.sp
                        )
                    )

                    Text(
                        text = "Description :- "+emp.description,
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 15.sp
                        )
                    )
                }
//            Image(painter = painterResource(emp.ImageId), contentDescription = "Profile Image",
//                contentScale = ContentScale.FillHeight,
//                modifier = Modifier
//                    .padding(8.dp)
//                    .size(110.dp)
//                    .clip((CircleShape)  ))
            }
        }
    }
    @Composable
    fun DetailsContent() {

        val employees = remember { Details.EmployDetailsList }
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            items(
                employees
            ) {
                EmployeeCard(emp = it)
            }

        }

    }
    //
    //.............................new

    @Composable
    fun GridViewWithAddButton() {
        // Sample list of grid items
        val gridItems = remember {
            mutableStateListOf(
                "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6", "Item 7", "Item 8"
            )
        }

        Column(modifier = Modifier.fillMaxSize()) {
            // LazyColumn to make the grid items scrollable
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.weight(1f),

            ) {
                items(gridItems.size) { index ->
                    GridItem(text = gridItems[index])
                }
            }

            // Add Button at the bottom
            Button(
                onClick = {
                    // Add a new grid item when the button is clicked
                    val newItem = "Item ${gridItems.size + 1}"
                    gridItems.add(newItem)
                },
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(text = "Add Item")
            }
        }
    }

    @Composable
    fun GridItem(text: String) {
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
                    .padding(16.dp)
                    .height(100.dp)
            ) {
                Text(text = text, modifier = Modifier, textAlign = TextAlign.Center)
            }
        }
    }
}
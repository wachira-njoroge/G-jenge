package com.awesome.g_jenge

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.awesome.g_jenge.ui.theme.GjengeTheme
import com.awesome.g_jenge.viewmodel.AppViewModel

class MainActivity : ComponentActivity() {
    private lateinit var appViewModel: AppViewModel
    companion object {

        lateinit  var appContext: Context

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appContext = applicationContext
        appViewModel = AppViewModel(appContext)
        appViewModel.allProjects.observe(this){
                projects ->
            run {
                Log.i("Projects", "onCreate: Projects in db -->> ${projects.size}")
            }
        }
        setContent {
            GjengeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    DetailsContent()
                }
            }
        }

    }

data class EmployDetails(val id: Int,
                         val title: String,
                         val sex: String,
                         val age: Int,
                         val description: String,
                         val ImageId: Int = 0)
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
}

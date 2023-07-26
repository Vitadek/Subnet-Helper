package com.example.subnethelper
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainScreen.route){
        composable(route = Screen.MainScreen.route){
            MainScreen(navController = navController)
        }
        composable(
            route = Screen.DetailScreen.route + "/{ip}/{cidr}",
            arguments = listOf(
                navArgument("ip"){
                    type = NavType.StringType
                    nullable = false
                },
                navArgument("cidr"){
                    type = NavType.StringType
                    nullable = false
                }
            )
        ) { entry ->
            DetailScreen(ip = entry.arguments?.getString("ip"), cidr = entry.arguments?.getString("cidr"))
        }
    }
}

@Composable
fun MainScreen(navController: NavController) {
    val cidrItems = listOf(
        "/32",
        "/31",
        "/30",
        "/29",
        "/28",
        "/27",
        "/26",
        "/25",
        "/24",
        "/23",
        "/22",
        "/21",
        "/20",
        "/19",
        "/18",
        "/17",
        "/16",
        "/15",
        "/14",
        "/13",
        "/12",
        "/11",
        "/10",
        "/9",
        "/8",
        "/7",
        "/6",
        "/5",
        "/4",
        "/3",
        "/2",
        "/1",
        "/0"
    )
    var text by remember { mutableStateOf("192.168.0.1") }
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(cidrItems[0]) }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun IPInputField() {

        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(0.5f)
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun CIDRDropDown() {

        val context = LocalContext.current
        //val coffeeDrinks = arrayOf("Americano", "Cappuccino", "Espresso", "Latte", "Mocha")


        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = {
                    expanded = !expanded
                }
            ) {
                TextField(
                    value = selectedText,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    cidrItems.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(text = item) },
                            onClick = {
                                selectedText = item
                                expanded = false
                                Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun SubmitNetworks(
        modifier: Modifier = Modifier.size(20.dp)
    ) {
        Button(
            onClick = {
                navController.navigate(Screen.DetailScreen.withArgs(text, selectedText))
            }) {
            Text("Networks")
        }
    }

    @Composable
    fun LayoutInputField() {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
            ) {
                IPInputField()
                CIDRDropDown()
            }
            SubmitNetworks()
        }

    }
    LayoutInputField()
}

@Composable
fun DetailScreen(ip: String?, cidr: String?){
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Text(text = "IP: " + ip + " cidr: " + cidr)
    }
}

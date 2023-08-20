package com.example.subnethelper
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import java.io.File
//import java.net.InetAddress
import kotlin.math.pow


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
                    defaultValue = "19216811"
                },
                navArgument("cidr"){
                    type = NavType.StringType
                    nullable = false
                    defaultValue = "32"
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
    val ipaddress = changeIP(ip = text)
    val cidrnote = changeCIDR(cidr = selectedText)

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
    ) {
        Button(
            onClick = {
                navController.navigate(Screen.DetailScreen.withArgs(ipaddress,cidrnote))
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

fun changeIP(ip: String?): String? {
    return ip?.replace(".","-")
}

//These next functions are necessary due to Screens not taking "." and "/" as the resource value lol
@Composable
fun changeCIDR(cidr: String?): String? {
    return cidr?.replace("/","-")
}

fun originIP(ip: String?): String? {
    return ip?.replace("-",".")
}


fun originCIDR(cidr: String?): String? {
    return cidr?.replace("-","/")
}
// I need the next function to make CIDR into INT
fun toIntCIDR(cidr: String): Int {
    return cidr.replace("/","").toInt()
}

fun calculateIPAddresses(cidr: Int): Int{
    // Convert IP address to a 32-bit integer
    return when (cidr) {
        1 -> 2147483392
        2 -> 107741696
        3 -> 536870848
        4 -> 268435424
        5 -> 134217712
        6 -> 67108856
        7 -> 33554428
        8 -> 16777214
        9 -> 8388352
        10 -> 4194176
        11 -> 2097088
        12 -> 1048544
        13 -> 524272
        14 -> 262136
        15 -> 131068
        16 -> 65024
        17 -> 32512
        18 -> 16256
        19 -> 8128
        20 -> 4064
        21 -> 2032
        22 -> 1016
        23 -> 508
        24 -> 254
        25 -> 124
        26 -> 62
        27 -> 30
        28 -> 14
        29 -> 6
        30 -> 2
        31 -> 0
        32 -> 1
        else -> throw IllegalArgumentException("Invalid CIDR notation: $cidr")
    }

}

// Helper function to convert IP address bytes to an integer value
private fun convertBytesToInt(ipBytes: ByteArray): Int {
    var ipAddressAsInt = 0

    for (i in 0 until ipBytes.size) {
        ipAddressAsInt = (ipAddressAsInt shl 8) or (ipBytes[i].toInt() and 0xff)
    }

    return ipAddressAsInt
}

@Composable
fun TextCard(label: String, text: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
    ) {
        Column(
            modifier = Modifier.padding(15.dp)
        ) {
            Text(
                buildAnnotatedString {
                    append(label)
                    withStyle(style = SpanStyle(fontWeight = FontWeight.W900, color = Color(0xFF4552B8))
                    ) {
                        append(text)
                    }
                }
            )
        }
    }
}
fun findNetworkID(ipAddress: String, cidr: Int): String {
    // Split the IP address into octets
    val octets = ipAddress.split("-")
    val cidr = cidr * -1
    // Convert the octets to binary format
    val binaryIP = StringBuilder()
    for (octet in octets) {
        val binaryOctet = Integer.toBinaryString(octet.toInt())
        val paddedOctet = binaryOctet.padStart(8, '0')
        binaryIP.append(paddedOctet)
    }
    // Find the network ID by applying the CIDR mask
    val networkID = binaryIP.substring(0, cidr).padEnd(32, '0')

    // Convert the network ID back to decimal format
    val networkOctets = mutableListOf<String>()
    for (i in 0 until 32 step 8) {
        val octet = networkID.substring(i, i + 8)
        val decimalOctet = Integer.parseInt(octet, 2).toString()
        networkOctets.add(decimalOctet)
    }

    // Return the network ID as a string
    return networkOctets.joinToString(".")
}

fun CalcAvailableIPs (ip: Long): Any {
    if ((ip - 2) < 0 ) {
        return 0
    }
    else return (ip - 2)
}

fun toBinary(n: Int): String {
    return if (n != 0) toBinary(n / 2) + n % 2 else { "" }
}
/*
fun calculateLastIPAddress(networkID: String, cidr: Int): String {

    // Split the network ID into octets
    val octets = networkID.split(".")

    // Convert each octet to an integer
    var octet1 = octets[0].toInt()
    var octet2 = octets[1].toInt()
    var octet3 = octets[2].toInt()
    var octet4 = octets[3].toInt()

    val octet1binary = toBinary(octet1)
    val octet2binary = toBinary(octet2)
    val octet3binary = toBinary(octet3)
    val octet4binary = toBinary(octet4)

    // Calculate the number of host bits
    val hostBits = 32 - cidr
    val numberOfIPs = Math.pow(hostBits.toDouble(), 2.0).toInt()
    when (numberOfIPs) {
        in 0..255 -> octet4 = octet4 + numberOfIPs
        in 256 .. 64000 -> {octet4 = 255; octet3 }
    } // damn - I thought I had something - but I'll scrap this

    // Calculate the last IP address
    Log.d("hostBits", "HostBits: $hostBits")
    val lastOctet = (octet4 shr hostBits) + (1 shl hostBits) - 1
    val lastOctetBinary = toBinary(lastOctet)
    Log.d("Display Octets","First Octet $octet1 | Second Octet $octet2 | Third Octet $octet3 | Fourth Octet $lastOctet");
    Log.d("Display Octets in Binary", "1 $octet1binary | 2 $octet2binary | 3 $octet3binary | 4 $octet4binary | Last $lastOctetBinary" )
    val lastIP = "$octet1.$octet2.$octet3.$lastOctet"

    return lastIP
}
*/
fun calcRange(octet: Int, cidr: Int): Int {
    when (octet){
        4 -> {
            return (((2.0.pow(32-cidr)) - 1.0).toInt())
        }
        3 -> {
            return (((2.0.pow(24-cidr)) - 1.0).toInt())
        }
        2 -> {
            return (((2.0.pow(16-cidr)) - 1.0).toInt())
        }
        1 -> {
            return (((2.0.pow(8-cidr)) - 1.0).toInt())

        }
    }
    return 0
}
fun getLastIPAddress(networkId: String, cidr: Int): String {
   val networkParts = networkId.split(".")
    var octet = networkParts.map{it.toInt()}.toMutableList()
    when (cidr){
        in 24..32 -> {octet[3] = (octet[3].toInt()+ calcRange(4, cidr))}
        in 16 .. 23 -> {
            octet[3] = octet[3].toInt() + 255
            octet[2] = (octet[2].toInt() + calcRange(3, cidr))
        }
        in 8..15 -> {
            octet[3] = octet[3].toInt() + 255
            octet[2] = octet[2].toInt() + 255
            octet[1] = (octet[1].toInt() + calcRange(2, cidr))
        }
        in 0..7 -> {
            octet[3] = octet[3].toInt() + 255
            octet[2] = octet[2].toInt() + 255
            octet[1] = octet[1].toInt() + 255
            octet[0] = (octet[0] + calcRange(1,cidr))
        }
    }
    return octet.joinToString(".")
}


fun createFileWithText(fileName: String, text: String, context: Context) {
    val path = context.filesDir
    val file = File("$path$fileName")
    file.writeText(text)
}
@Composable
fun DetailScreen(ip: String?, cidr: String?){
    val context = LocalContext.current
    val CIDR = (toIntCIDR(cidr = cidr!!) * -1)
    val numberOfIPs = calculateIPAddresses(cidr = CIDR)
    val networkID = findNetworkID(ipAddress = ip!!, cidr = cidr.toInt())
    val lastIPAddress = getLastIPAddress(cidr = ((cidr.toInt() * -1)), networkId = networkID)
    val path = context.filesDir
    Column (modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center ){
        TextCard(label = "The number of 'usable' IP addresses is ", text = numberOfIPs.toString())
        TextCard(label ="The IP range is: " , text = "$networkID through $lastIPAddress" )
        Button(
        onClick = {
            createFileWithText(fileName = "SubnetHelp.txt","Number of usable IP address is $numberOfIPs \n The IP rage is $networkID through $lastIPAddress", context)
            Toast.makeText(context, "Generated a text file called $path SubnetHelp.txt!", Toast.LENGTH_SHORT).show()
        }
        ) {
            Text(text = "Create a txt file")
        }

    }
}

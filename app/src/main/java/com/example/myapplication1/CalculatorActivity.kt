package com.example.myapplication1

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication1.view.theme.MyApplication1Theme

class CalculatorActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val intent: Intent = Intent(this, SignInActivity::class.java)

        startActivityForResult(intent, 200)
        setContent {
            MyApplication1Theme {
                MainFunction("Android")
            }
        }
    }
}

@Composable
fun Greeting(name: String, count: Int) {
    Text(
        text = if (count==0) "Welcome $name" else "Thanks for clicking $count times!",
    )
    Spacer(modifier = Modifier.height(26.dp))


}

@Composable
fun ClickableButtons(count: Int, onIncrement: () -> Unit, onReset: () -> Unit){
    Row (verticalAlignment = Alignment.CenterVertically){
        Button(
            modifier= Modifier.padding(10.dp),
            onClick = onIncrement,
            colors = ButtonColors(contentColor = Color.White, containerColor = Color.Blue, disabledContentColor = Color.Unspecified, disabledContainerColor = Color.Unspecified)) {
            Text("Clicked $count times")
        }
        Spacer(modifier = Modifier.padding(10.dp))
        Button(onClick = onReset, colors = ButtonDefaults.buttonColors(containerColor = Color.Red)) { Text("Reset") }
    }
}

fun operation(type:String, num1:Int, num2:Int):Int{
    return when(type){
        "*"-> num1 * num2
        "-"-> num2 - num1
        "+"-> num1 + num2
        "/"-> {
            if(num1!=0)(num2/num1)
            else 0
        }
        else-> 0
    }
}

fun checkForPriority(nums: MutableList<String>):Boolean{
    return nums[(nums.lastIndex)-2]!="*" && nums[(nums.lastIndex)-2]!="/"
}

fun doCalculation(stack: MutableList<String>):Int{
    var first = stack.removeAt(stack.lastIndex)
    var second = ""
    var operation = "+"
    for(x in (stack.size-1) downTo 0){
        if(stack.size>0){
            if(stack[stack.lastIndex]=="+"||stack[stack.lastIndex]=="-"||stack[stack.lastIndex]=="/"||stack[stack.lastIndex]=="*"){
                if(stack.size>2 && ((stack[stack.lastIndex]=="+")||(stack[stack.lastIndex]=="-"))){
                    if(!checkForPriority(stack)){
                        val multi = if(stack[(stack.lastIndex)-2]=="*")
                            stack[stack.lastIndex-1].toInt()*stack[stack.lastIndex-3].toInt()
                        else
                        {
                            (stack[stack.lastIndex-3].toInt())/(stack[stack.lastIndex-1].toInt())
                        }
                        stack[stack.lastIndex-1] = multi.toString()
                        stack.removeAt(stack.lastIndex-2)
                        stack.removeAt(stack.lastIndex-2)
                        continue
                    }
                }
                operation = stack.removeAt(stack.lastIndex)
                second = stack.removeAt(stack.lastIndex)
                first = operation(operation, first.toInt(), second.toInt()).toString()
            }else{
                first = stack.removeAt(stack.lastIndex)
            }
        }
    }
    return first.toInt()
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CalculatorFace(){
    var stack =  remember { mutableStateListOf<String>() }
    var number by remember { mutableStateOf("") }
    var numbers = remember { mutableStateListOf("") }
    var result by remember { mutableStateOf("") }

    val listOfOperands = listOf<Int>(1,2,3,4,5,6,7,8,9,0)
    val listOfOperators = listOf<String>("+","-","*","/")

    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)
            .wrapContentHeight()
            .background(color = Color.Black, RoundedCornerShape(16.dp)),
        horizontalAlignment = Alignment.CenterHorizontally


    ) {
        Button(onClick = {
            val intent = Intent(context, SignInActivity::class.java)
            context.startActivity(intent)
        }) {
            Text("Return")
        }
        DefaultSpacer()

        Text(
            "Result = ${if(result.length>0)result else "0"}",
            Modifier
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(5.dp),
                )
                .fillMaxWidth()
                .border(color = Color.Black, width = 12.dp)
                .padding(12.dp),
            style = MaterialTheme.typography.headlineSmall,
            fontFamily = FontFamily.Cursive
        )

        DefaultSpacer()

        for (i in 0..listOfOperands.size step 3){
            val m = i +2
            if(i%3==0) {
                Row {
                    for (x in i..m) {
                        if((x < listOfOperands.size)){
                            Button(onClick = {
                                // Toast.makeText(, "Clicked", Toast.LENGTH_SHORT)
                                number += listOfOperands[x]
                                result += listOfOperands[x]
                            }, modifier = Modifier
                                .background(
                                    color = Color.Transparent,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .padding(horizontal = 20.dp)) {
                                Text("${listOfOperands[x]}")
                            }
                        }
                    }
                }
            }

            else
                Row {
                    for (x in (i-1)..m){
                        if((x < listOfOperands.size) && x>0){
                            Button(onClick = {
                                number += listOfOperands[x - 1]
                                result += listOfOperands[x - 1]
                            }) {

                                Text("${listOfOperands[x - 1]}")
                            }
                        }
                    }
                }
        }



        for (i in 0..(listOfOperators.size-1) step 3){
            val m = i+2
            if(i%3==0) {
                if(i==0){
                    Row {
                        Button(onClick = {
                            result = ""
                            stack.clear()
                            // result = "${sumOfNumbers(numbers)}"
                            numbers.clear()
                            number = ""
                        },
                            modifier = Modifier.padding(horizontal = 10.dp)

                        ) {
                            Text("C")
                        }

                        Button(onClick = {
                            if(!(number.isEmpty()))  stack.add(number)
                            if(!(stack.isNotEmpty() && listOfOperators.contains(stack[stack.lastIndex])))
                            {
                                result = doCalculation(stack).toString()
                                numbers.add(number)
                                // result = "${sumOfNumbers(numbers)}"
                                numbers.clear()
                                number = result}
                        },
                            modifier = Modifier.padding(horizontal = 10.dp)
                        ) {
                            Text("=")
                        }
                    }
                }
                Row {
                    for (x in i..m) {
                        if(x < listOfOperators.size){
                            Button(onClick = {

                                if(!(stack.isNotEmpty() && number.isEmpty() &&listOfOperators.contains(stack[stack.lastIndex])))
                                { stack.add(number)
                                    stack.add(listOfOperators[x])
                                    number = ""
                                    result+=listOfOperators[x]
                                }
                            }, modifier = Modifier
                                .background(
                                    color = Color.Transparent,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .padding(horizontal = 4.dp, vertical = 2.dp)) {
                                Text(listOfOperators[x])
                            }
                        }
                    }
                }
            }

            else
                Row {
                    for (x in i..m){
                        if(i < listOfOperators.size){
                            Button(onClick = {
                                if(!(stack.isNotEmpty()&& number.isEmpty() && listOfOperators.contains(stack[stack.lastIndex])))
                                { stack.add(number)
                                    stack.add(listOfOperators[x])
                                    number = ""
                                    result+=listOfOperators[x]
                                }
                            }) {
                                Text(listOfOperators[x - 1])
                            }
                        }

                    }

                }

        }

    }
}

@Composable
fun Logger(input:String="Nothing to log yet"){
    Text(input)
}

@Composable
fun DefaultSpacer(){
    return Spacer(modifier = Modifier.height(20.dp))
}

@Composable
fun MainFunction(name: String, modifier: Modifier= Modifier){
    var count by remember { mutableIntStateOf(0) }
    Column(horizontalAlignment = Alignment.CenterHorizontally,modifier = Modifier
        .padding(50.dp)
        .background(color = Color.Transparent)
        .fillMaxSize()) {
        //Greeting(name = name, count = count)

        DefaultSpacer()
        CalculatorFace()
        DefaultSpacer()

        //  ClickableButtons(count = count, { count++ }, onReset = { count = 0 })

        DefaultSpacer()
        //  Logger()
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplication1Theme {
        MainFunction("Android")
    }
}
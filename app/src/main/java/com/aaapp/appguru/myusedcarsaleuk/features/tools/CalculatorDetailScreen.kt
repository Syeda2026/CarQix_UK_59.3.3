package com.aaapp.appguru.myusedcarsaleuk.features.tools

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aaapp.appguru.myusedcarsaleuk.common.*
import com.aaapp.appguru.myusedcarsaleuk.core.ads.AdMobNativeAdCard
import com.aaapp.appguru.myusedcarsaleuk.core.config.ConfigManager
import com.aaapp.appguru.myusedcarsaleuk.data.local.AppRepository
import com.aaapp.appguru.myusedcarsaleuk.data.local.SavedItemEntity
import com.aaapp.appguru.myusedcarsaleuk.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun CalculatorDetailScreen(
    calculatorId: String,
    onNavigateRoute: (String) -> Unit
) {
    val context = LocalContext.current
    val repository = remember { AppRepository.getInstance(context) }
    val scope = rememberCoroutineScope()
    var isSaved by remember { mutableStateOf(false) }

    val toolName = when (calculatorId) {
        "road_tax" -> "Road Tax (VED) Calculator"
        "fuel_cost" -> "Fuel Cost Calculator"
        "loan" -> "Car Loan Calculator"
        "mileage" -> "Annual Mileage Estimator"
        "running_cost" -> "Running Cost Calculator"
        "journey_cost" -> "Journey Cost Calculator"
        "fuel_economy" -> "Fuel Economy Converter"
        "monthly_budget" -> "Monthly Car Budget"
        "depreciation" -> "Vehicle Depreciation"
        "insurance_estimate" -> "Insurance Group Estimator"
        else -> "Smart Calculator"
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = toolName,
                subtitle = "UK Formulaes & Rates",
                showBackButton = true,
                onBackClick = { onNavigateRoute("smart_tools") },
                onNavigateRoute = onNavigateRoute
            )
        },
        bottomBar = {
            AppBottomBar(
                currentRoute = "smart_tools",
                onNavigate = onNavigateRoute
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(SlateBackground),
            contentPadding = PaddingValues(
                top = padding.calculateTopPadding() + 4.dp,
                bottom = padding.calculateBottomPadding() + 8.dp,
                start = 16.dp,
                end = 16.dp
            ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { Spacer(modifier = Modifier.height(4.dp)) }

            when (calculatorId) {
                "road_tax" -> item { RoadTaxCalcView { title, subtitle ->
                    scope.launch {
                        repository.saveItem(SavedItemEntity("calc_${System.currentTimeMillis()}", "CALCULATION", title, subtitle, "calculator_detail/road_tax"))
                        isSaved = true
                    }
                } }
                "fuel_cost" -> item { FuelCostCalcView { title, subtitle ->
                    scope.launch {
                        repository.saveItem(SavedItemEntity("calc_${System.currentTimeMillis()}", "CALCULATION", title, subtitle, "calculator_detail/fuel_cost"))
                        isSaved = true
                    }
                } }
                "loan" -> item { LoanCalcView { title, subtitle ->
                    scope.launch {
                        repository.saveItem(SavedItemEntity("calc_${System.currentTimeMillis()}", "CALCULATION", title, subtitle, "calculator_detail/loan"))
                        isSaved = true
                    }
                } }
                "mileage" -> item { MileageCalcView { title, subtitle ->
                    scope.launch {
                        repository.saveItem(SavedItemEntity("calc_${System.currentTimeMillis()}", "CALCULATION", title, subtitle, "calculator_detail/mileage"))
                        isSaved = true
                    }
                } }
                "running_cost" -> item { RunningCostCalcView { title, subtitle ->
                    scope.launch {
                        repository.saveItem(SavedItemEntity("calc_${System.currentTimeMillis()}", "CALCULATION", title, subtitle, "calculator_detail/running_cost"))
                        isSaved = true
                    }
                } }
                "journey_cost" -> item { JourneyCostCalcView { title, subtitle ->
                    scope.launch {
                        repository.saveItem(SavedItemEntity("calc_${System.currentTimeMillis()}", "CALCULATION", title, subtitle, "calculator_detail/journey_cost"))
                        isSaved = true
                    }
                } }
                "fuel_economy" -> item { FuelEconomyCalcView { title, subtitle ->
                    scope.launch {
                        repository.saveItem(SavedItemEntity("calc_${System.currentTimeMillis()}", "CALCULATION", title, subtitle, "calculator_detail/fuel_economy"))
                        isSaved = true
                    }
                } }
                "monthly_budget" -> item { MonthlyBudgetCalcView { title, subtitle ->
                    scope.launch {
                        repository.saveItem(SavedItemEntity("calc_${System.currentTimeMillis()}", "CALCULATION", title, subtitle, "calculator_detail/monthly_budget"))
                        isSaved = true
                    }
                } }
                "depreciation" -> item { DepreciationCalcView { title, subtitle ->
                    scope.launch {
                        repository.saveItem(SavedItemEntity("calc_${System.currentTimeMillis()}", "CALCULATION", title, subtitle, "calculator_detail/depreciation"))
                        isSaved = true
                    }
                } }
                "insurance_estimate" -> item { InsuranceEstimateCalcView { title, subtitle ->
                    scope.launch {
                        repository.saveItem(SavedItemEntity("calc_${System.currentTimeMillis()}", "CALCULATION", title, subtitle, "calculator_detail/insurance_estimate"))
                        isSaved = true
                    }
                } }
                else -> item { FuelCostCalcView { title, subtitle -> } }
            }

            item {
                AdMobNativeAdCard(
                    isPlacementEnabled = ConfigManager.adsConfig.isCalculatorDetailNativeAdEnabled
                )
            }

            if (isSaved) {
                item {
                    Surface(color = SuccessGreen.copy(0.12f), shape = RoundedCornerShape(8.dp)) {
                        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = SuccessGreen)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Calculation saved to Saved Items tab!", color = SuccessGreen, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

// 1. Road Tax Calc View
@Composable
private fun RoadTaxCalcView(onSave: (String, String) -> Unit) {
    var fuelType by remember { mutableStateOf("Petrol") }
    var emissions by remember { mutableStateOf("135") }
    var year by remember { mutableStateOf("2020") }
    var isOver40k by remember { mutableStateOf(false) }

    val result = CalculatorLogic.calculateRoadTax(fuelType, emissions.toIntOrNull() ?: 135, year.toIntOrNull() ?: 2020, isOver40k)

    Card(
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Road Tax (VED) Inputs", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = NavyDark)
            Spacer(modifier = Modifier.height(12.dp))
            AppTextField(value = fuelType, onValueChange = { fuelType = it }, label = "Fuel Type (Petrol/Diesel/Hybrid/Electric)", modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            AppTextField(value = emissions, onValueChange = { emissions = it }, label = "CO2 Emissions (g/km)", keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            AppTextField(value = year, onValueChange = { year = it }, label = "Registration Year", keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth())
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = isOver40k, onCheckedChange = { isOver40k = it })
                Text("List price over £40,000 when new?", fontSize = 12.sp, color = NavyDark)
            }
            Spacer(modifier = Modifier.height(16.dp))
            ResultCard("Annual VED Road Tax", "£${result.totalAnnualCost.toInt()}/yr") {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("First Year Rate: £${result.firstYearRate.toInt()}", fontSize = 11.sp, color = TextSecondaryLight)
                    Text("Standard Rate: £${result.standardAnnualRate.toInt()}", fontSize = 11.sp, color = TextSecondaryLight)
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            BlinkButton(onClick = { onSave("Road Tax: £${result.totalAnnualCost.toInt()}/yr", "$fuelType • $emissions g/km • Year $year") }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = RoyalBlue)) {
                Icon(imageVector = Icons.Default.Bookmark, contentDescription = null)
                Spacer(modifier = Modifier.width(6.dp))
                Text("Save Calculation")
            }
        }
    }
}

// 2. Fuel Cost Calc View
@Composable
private fun FuelCostCalcView(onSave: (String, String) -> Unit) {
    var miles by remember { mutableStateOf("300") }
    var mpg by remember { mutableStateOf("45") }
    var pricePerLitre by remember { mutableStateOf("1.45") }

    val result = CalculatorLogic.calculateFuelCost(miles.toDoubleOrNull() ?: 300.0, mpg.toDoubleOrNull() ?: 45.0, pricePerLitre.toDoubleOrNull() ?: 1.45)

    Card(
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Fuel Cost Inputs", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = NavyDark)
            Spacer(modifier = Modifier.height(12.dp))
            AppTextField(value = miles, onValueChange = { miles = it }, label = "Trip Distance (Miles)", keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            AppTextField(value = mpg, onValueChange = { mpg = it }, label = "Vehicle MPG", keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            AppTextField(value = pricePerLitre, onValueChange = { pricePerLitre = it }, label = "Fuel Price per Litre (£)", keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal), modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(16.dp))
            ResultCard("Total Trip Fuel Cost", "£${String.format("%.2f", result.totalCostGbp)}") {
                Text("${String.format("%.1f", result.totalFuelLitresNeeded)} Litres needed • ${String.format("%.1f", result.costPerMileGbp * 100)}p per mile", fontSize = 11.sp, color = TextSecondaryLight)
            }
            Spacer(modifier = Modifier.height(12.dp))
            BlinkButton(onClick = { onSave("Fuel Cost: £${String.format("%.2f", result.totalCostGbp)}", "$miles miles • $mpg MPG • £$pricePerLitre/L") }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = RoyalBlue)) {
                Icon(imageVector = Icons.Default.Bookmark, contentDescription = null)
                Spacer(modifier = Modifier.width(6.dp))
                Text("Save Calculation")
            }
        }
    }
}

// 3. Loan Calc View
@Composable
private fun LoanCalcView(onSave: (String, String) -> Unit) {
    var price by remember { mutableStateOf("12000") }
    var deposit by remember { mutableStateOf("2000") }
    var apr by remember { mutableStateOf("8.9") }
    var months by remember { mutableStateOf("48") }

    val result = CalculatorLogic.calculateLoan(price.toDoubleOrNull() ?: 12000.0, deposit.toDoubleOrNull() ?: 2000.0, apr.toDoubleOrNull() ?: 8.9, months.toIntOrNull() ?: 48)

    Card(
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Car Loan Inputs", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = NavyDark)
            Spacer(modifier = Modifier.height(12.dp))
            AppTextField(value = price, onValueChange = { price = it }, label = "Vehicle Price (£)", keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            AppTextField(value = deposit, onValueChange = { deposit = it }, label = "Deposit (£)", keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                AppTextField(value = apr, onValueChange = { apr = it }, label = "APR (%)", keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal), modifier = Modifier.weight(1f))
                AppTextField(value = months, onValueChange = { months = it }, label = "Term (Months)", keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(16.dp))
            ResultCard("Monthly Loan Payment", "£${String.format("%.2f", result.monthlyPaymentGbp)}/mo") {
                Text("Total Interest: £${String.format("%.2f", result.totalInterestGbp)} • Total Payable: £${String.format("%.2f", result.totalCostGbp)}", fontSize = 11.sp, color = TextSecondaryLight)
            }
            Spacer(modifier = Modifier.height(12.dp))
            BlinkButton(onClick = { onSave("Loan: £${String.format("%.2f", result.monthlyPaymentGbp)}/mo", "£$price car • £$deposit dep • $apr% APR • $months mos") }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = RoyalBlue)) {
                Icon(imageVector = Icons.Default.Bookmark, contentDescription = null)
                Spacer(modifier = Modifier.width(6.dp))
                Text("Save Calculation")
            }
        }
    }
}

// 4. Mileage Calc View
@Composable
private fun MileageCalcView(onSave: (String, String) -> Unit) {
    var commute by remember { mutableStateOf("15") }
    var days by remember { mutableStateOf("5") }
    var weekend by remember { mutableStateOf("50") }

    val result = CalculatorLogic.calculateMileage(commute.toDoubleOrNull() ?: 15.0, days.toIntOrNull() ?: 5, weekend.toDoubleOrNull() ?: 50.0, 1000.0)

    Card(
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Mileage Inputs", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = NavyDark)
            Spacer(modifier = Modifier.height(12.dp))
            AppTextField(value = commute, onValueChange = { commute = it }, label = "Daily Commute (Miles)", keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            AppTextField(value = days, onValueChange = { days = it }, label = "Commute Days per Week", keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            AppTextField(value = weekend, onValueChange = { weekend = it }, label = "Weekend Miles per Week", keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(16.dp))
            ResultCard("Projected Annual Mileage", "${result.totalAnnualMiles.toInt()} Miles/yr") {
                Text("Commute: ${result.annualCommuteMiles.toInt()} mi • Personal: ${result.annualPersonalMiles.toInt()} mi • Est Servicing: £${result.estimatedServicingCostGbp.toInt()}", fontSize = 11.sp, color = TextSecondaryLight)
            }
            Spacer(modifier = Modifier.height(12.dp))
            BlinkButton(onClick = { onSave("Annual Mileage: ${result.totalAnnualMiles.toInt()} mi/yr", "Commute $commute mi x $days days • Weekend $weekend mi") }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = RoyalBlue)) {
                Icon(imageVector = Icons.Default.Bookmark, contentDescription = null)
                Spacer(modifier = Modifier.width(6.dp))
                Text("Save Calculation")
            }
        }
    }
}

// 5. Running Cost View
@Composable
private fun RunningCostCalcView(onSave: (String, String) -> Unit) {
    var loan by remember { mutableStateOf("220") }
    var fuel by remember { mutableStateOf("120") }
    var insurance by remember { mutableStateOf("650") }
    var tax by remember { mutableStateOf("190") }

    val result = CalculatorLogic.calculateRunningCost(loan.toDoubleOrNull() ?: 220.0, insurance.toDoubleOrNull() ?: 650.0, tax.toDoubleOrNull() ?: 190.0, fuel.toDoubleOrNull() ?: 120.0, 250.0, 30.0)

    Card(
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Running Cost Inputs", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = NavyDark)
            Spacer(modifier = Modifier.height(12.dp))
            AppTextField(value = loan, onValueChange = { loan = it }, label = "Monthly Finance Loan (£)", keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            AppTextField(value = fuel, onValueChange = { fuel = it }, label = "Monthly Fuel (£)", keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            AppTextField(value = insurance, onValueChange = { insurance = it }, label = "Annual Insurance (£)", keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            AppTextField(value = tax, onValueChange = { tax = it }, label = "Annual Road Tax (£)", keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(16.dp))
            ResultCard("Total Monthly Running Cost", "£${result.monthlyTotalGbp.toInt()}/month") {
                Text("Total Annual Ownership: £${result.annualTotalGbp.toInt()}/year", fontSize = 11.sp, color = TextSecondaryLight)
            }
            Spacer(modifier = Modifier.height(12.dp))
            BlinkButton(onClick = { onSave("Running Cost: £${result.monthlyTotalGbp.toInt()}/mo", "Loan £$loan • Fuel £$fuel • Ins £$insurance") }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = RoyalBlue)) {
                Icon(imageVector = Icons.Default.Bookmark, contentDescription = null)
                Spacer(modifier = Modifier.width(6.dp))
                Text("Save Calculation")
            }
        }
    }
}

// 6. Journey Cost View
@Composable
private fun JourneyCostCalcView(onSave: (String, String) -> Unit) {
    var miles by remember { mutableStateOf("150") }
    var mpg by remember { mutableStateOf("40") }
    var tolls by remember { mutableStateOf("12") }

    val result = CalculatorLogic.calculateJourneyCost(miles.toDoubleOrNull() ?: 150.0, mpg.toDoubleOrNull() ?: 40.0, 1.45, tolls.toDoubleOrNull() ?: 12.0, 0.0, 2)

    Card(
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Journey Cost Inputs", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = NavyDark)
            Spacer(modifier = Modifier.height(12.dp))
            AppTextField(value = miles, onValueChange = { miles = it }, label = "Distance (Miles)", keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            AppTextField(value = mpg, onValueChange = { mpg = it }, label = "MPG", keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            AppTextField(value = tolls, onValueChange = { tolls = it }, label = "Tolls & Congestion Charges (£)", keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(16.dp))
            ResultCard("Total Journey Cost", "£${String.format("%.2f", result.totalJourneyCostGbp)}") {
                Text("Fuel: £${String.format("%.2f", result.fuelCostGbp)} • Tolls: £${String.format("%.2f", result.tollsAndCongestionGbp)}", fontSize = 11.sp, color = TextSecondaryLight)
            }
            Spacer(modifier = Modifier.height(12.dp))
            BlinkButton(onClick = { onSave("Journey Cost: £${String.format("%.2f", result.totalJourneyCostGbp)}", "$miles miles • Tolls £$tolls") }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = RoyalBlue)) {
                Icon(imageVector = Icons.Default.Bookmark, contentDescription = null)
                Spacer(modifier = Modifier.width(6.dp))
                Text("Save Calculation")
            }
        }
    }
}

// 7. Fuel Economy View
@Composable
private fun FuelEconomyCalcView(onSave: (String, String) -> Unit) {
    var miles by remember { mutableStateOf("320") }
    var litres by remember { mutableStateOf("38") }

    val result = CalculatorLogic.calculateFuelEconomy(litres.toDoubleOrNull() ?: 38.0, miles.toDoubleOrNull() ?: 320.0)

    Card(
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Fuel Economy Converter", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = NavyDark)
            Spacer(modifier = Modifier.height(12.dp))
            AppTextField(value = miles, onValueChange = { miles = it }, label = "Miles Driven", keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            AppTextField(value = litres, onValueChange = { litres = it }, label = "Litres Fuel Filled", keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(16.dp))
            ResultCard("UK Fuel Economy", "${String.format("%.1f", result.mpg)} MPG") {
                Text("${String.format("%.1f", result.litresPer100Km)} L/100km • ${String.format("%.1f", result.kmPerLitre)} km/L", fontSize = 11.sp, color = TextSecondaryLight)
            }
            Spacer(modifier = Modifier.height(12.dp))
            BlinkButton(onClick = { onSave("Fuel Economy: ${String.format("%.1f", result.mpg)} MPG", "$miles miles on $litres litres") }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = RoyalBlue)) {
                Icon(imageVector = Icons.Default.Bookmark, contentDescription = null)
                Spacer(modifier = Modifier.width(6.dp))
                Text("Save Calculation")
            }
        }
    }
}

// 8. Monthly Budget View
@Composable
private fun MonthlyBudgetCalcView(onSave: (String, String) -> Unit) {
    var pay by remember { mutableStateOf("2400") }

    val result = CalculatorLogic.calculateMonthlyBudget(pay.toDoubleOrNull() ?: 2400.0)

    Card(
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Monthly Budget Inputs", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = NavyDark)
            Spacer(modifier = Modifier.height(12.dp))
            AppTextField(value = pay, onValueChange = { pay = it }, label = "Monthly Take-Home Income (£)", keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(16.dp))
            ResultCard("Max Recommended Monthly Budget", "£${result.maxCarBudgetMonthlyGbp.toInt()}/month") {
                Text("Loan Payment target: £${result.recommendedLoanPaymentGbp.toInt()} • Running Costs: £${result.recommendedRunningCostsGbp.toInt()}", fontSize = 11.sp, color = TextSecondaryLight)
            }
            Spacer(modifier = Modifier.height(12.dp))
            BlinkButton(onClick = { onSave("Budget Target: £${result.maxCarBudgetMonthlyGbp.toInt()}/mo", "Based on £$pay monthly income") }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = RoyalBlue)) {
                Icon(imageVector = Icons.Default.Bookmark, contentDescription = null)
                Spacer(modifier = Modifier.width(6.dp))
                Text("Save Calculation")
            }
        }
    }
}

// 9. Depreciation View
@Composable
private fun DepreciationCalcView(onSave: (String, String) -> Unit) {
    var price by remember { mutableStateOf("18000") }
    var age by remember { mutableStateOf("3") }
    var miles by remember { mutableStateOf("30000") }

    val result = CalculatorLogic.calculateDepreciation(price.toDoubleOrNull() ?: 18000.0, age.toIntOrNull() ?: 3, miles.toDoubleOrNull() ?: 30000.0)

    Card(
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Depreciation Inputs", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = NavyDark)
            Spacer(modifier = Modifier.height(12.dp))
            AppTextField(value = price, onValueChange = { price = it }, label = "Original Price New (£)", keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            AppTextField(value = age, onValueChange = { age = it }, label = "Vehicle Age (Years)", keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            AppTextField(value = miles, onValueChange = { miles = it }, label = "Total Mileage", keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(16.dp))
            ResultCard("Current Estimated Value", "£${result.currentEstimatedValueGbp.toInt()}") {
                Text("Value Retained: ${String.format("%.1f", result.retainedValuePercent)}% • Future 3yr Value: £${result.futureValue3YearsGbp.toInt()}", fontSize = 11.sp, color = TextSecondaryLight)
            }
            Spacer(modifier = Modifier.height(12.dp))
            BlinkButton(onClick = { onSave("Est Value: £${result.currentEstimatedValueGbp.toInt()}", "Original £$price • Age $age yrs • $miles mi") }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = RoyalBlue)) {
                Icon(imageVector = Icons.Default.Bookmark, contentDescription = null)
                Spacer(modifier = Modifier.width(6.dp))
                Text("Save Calculation")
            }
        }
    }
}

// 10. Insurance Estimate View
@Composable
private fun InsuranceEstimateCalcView(onSave: (String, String) -> Unit) {
    var age by remember { mutableStateOf("28") }
    var ncb by remember { mutableStateOf("4") }
    var group by remember { mutableStateOf("18") }

    val result = CalculatorLogic.calculateInsuranceEstimate(age.toIntOrNull() ?: 28, ncb.toIntOrNull() ?: 4, group.toIntOrNull() ?: 18, "DRIVEWAY")

    Card(
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Insurance Estimator Inputs", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = NavyDark)
            Spacer(modifier = Modifier.height(12.dp))
            AppTextField(value = age, onValueChange = { age = it }, label = "Driver Age", keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            AppTextField(value = ncb, onValueChange = { ncb = it }, label = "No Claims Bonus (Years)", keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            AppTextField(value = group, onValueChange = { group = it }, label = "Vehicle Insurance Group (1 to 50)", keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(16.dp))
            ResultCard("Estimated Annual Premium", "£${result.estimatedAnnualPremiumGbp.toInt()}/yr") {
                Text("${result.riskRatingTier} • Est £${result.estimatedMonthlyPremiumGbp.toInt()}/month", fontSize = 11.sp, color = TextSecondaryLight)
            }
            Spacer(modifier = Modifier.height(12.dp))
            BlinkButton(onClick = { onSave("Est Insurance: £${result.estimatedAnnualPremiumGbp.toInt()}/yr", "Age $age • $ncb yrs NCB • Group $group") }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = RoyalBlue)) {
                Icon(imageVector = Icons.Default.Bookmark, contentDescription = null)
                Spacer(modifier = Modifier.width(6.dp))
                Text("Save Calculation")
            }
        }
    }
}


@Composable
private fun ResultCard(title: String, value: String, content: @Composable () -> Unit) {
    Surface(color = RoyalBlue.copy(alpha = 0.08f), shape = RoundedCornerShape(10.dp)) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(text = title, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = RoyalBlue)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = value, fontSize = 24.sp, fontWeight = FontWeight.Black, color = NavyDark)
            Spacer(modifier = Modifier.height(6.dp))
            content()
        }
    }
}

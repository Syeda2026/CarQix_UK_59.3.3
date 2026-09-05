package com.aaapp.appguru.myusedcarsaleuk.features.tools

import kotlin.math.pow

object CalculatorLogic {

    // 1. Road Tax (VED) Calculator
    data class RoadTaxResult(
        val firstYearRate: Double,
        val standardAnnualRate: Double,
        val luxuryCarSurcharge: Double,
        val totalAnnualCost: Double
    )

    fun calculateRoadTax(
        fuelType: String, // "PETROL", "DIESEL", "HYBRID", "ELECTRIC"
        emissionsGkm: Int,
        registrationYear: Int,
        listPriceOver40k: Boolean
    ): RoadTaxResult {
        val isElectric = fuelType.uppercase() == "ELECTRIC"
        val isHybrid = fuelType.uppercase() == "HYBRID"
        val isPre2017 = registrationYear < 2017

        val standardRate = when {
            isElectric -> 0.0
            isHybrid -> 180.0
            isPre2017 -> when {
                emissionsGkm <= 100 -> 0.0
                emissionsGkm <= 120 -> 35.0
                emissionsGkm <= 140 -> 160.0
                emissionsGkm <= 165 -> 210.0
                emissionsGkm <= 185 -> 290.0
                else -> 385.0
            }
            else -> 190.0 // Standard rate post-2017
        }

        val firstYear = when {
            isElectric -> 0.0
            emissionsGkm == 0 -> 0.0
            emissionsGkm <= 50 -> 10.0
            emissionsGkm <= 75 -> 30.0
            emissionsGkm <= 90 -> 135.0
            emissionsGkm <= 100 -> 175.0
            emissionsGkm <= 110 -> 195.0
            emissionsGkm <= 130 -> 220.0
            emissionsGkm <= 150 -> 270.0
            emissionsGkm <= 170 -> 680.0
            emissionsGkm <= 190 -> 1095.0
            else -> 1650.0
        }

        val surcharge = if (listPriceOver40k && !isElectric && registrationYear >= 2017) 410.0 else 0.0
        val total = standardRate + surcharge

        return RoadTaxResult(
            firstYearRate = firstYear,
            standardAnnualRate = standardRate,
            luxuryCarSurcharge = surcharge,
            totalAnnualCost = total
        )
    }

    // 2. Fuel Cost Calculator
    data class FuelCostResult(
        val totalFuelLitresNeeded: Double,
        val totalCostGbp: Double,
        val costPerMileGbp: Double
    )

    fun calculateFuelCost(
        distanceMiles: Double,
        mpg: Double,
        fuelPricePerLitreGbp: Double
    ): FuelCostResult {
        if (mpg <= 0 || distanceMiles <= 0) return FuelCostResult(0.0, 0.0, 0.0)
        val gallonsNeeded = distanceMiles / mpg
        val litresNeeded = gallonsNeeded * 4.54609 // UK Gallons
        val totalCost = litresNeeded * fuelPricePerLitreGbp
        val costPerMile = if (distanceMiles > 0) totalCost / distanceMiles else 0.0

        return FuelCostResult(
            totalFuelLitresNeeded = litresNeeded,
            totalCostGbp = totalCost,
            costPerMileGbp = costPerMile
        )
    }

    // 3. Loan Calculator
    data class LoanResult(
        val monthlyPaymentGbp: Double,
        val totalInterestGbp: Double,
        val totalCostGbp: Double,
        val amountFinancedGbp: Double
    )

    fun calculateLoan(
        vehiclePriceGbp: Double,
        depositGbp: Double,
        aprPercent: Double,
        termMonths: Int
    ): LoanResult {
        val amountFinanced = (vehiclePriceGbp - depositGbp).coerceAtLeast(0.0)
        if (amountFinanced <= 0 || termMonths <= 0) return LoanResult(0.0, 0.0, 0.0, amountFinanced)

        val monthlyRate = (aprPercent / 100.0) / 12.0
        val monthlyPayment = if (monthlyRate > 0) {
            val factor = (1 + monthlyRate).pow(termMonths.toDouble())
            amountFinanced * (monthlyRate * factor) / (factor - 1)
        } else {
            amountFinanced / termMonths
        }

        val totalCost = monthlyPayment * termMonths + depositGbp
        val totalInterest = (monthlyPayment * termMonths) - amountFinanced

        return LoanResult(
            monthlyPaymentGbp = monthlyPayment,
            totalInterestGbp = totalInterest.coerceAtLeast(0.0),
            totalCostGbp = totalCost,
            amountFinancedGbp = amountFinanced
        )
    }

    // 4. Mileage Calculator
    data class MileageResult(
        val annualCommuteMiles: Double,
        val annualPersonalMiles: Double,
        val totalAnnualMiles: Double,
        val estimatedServicingCostGbp: Double
    )

    fun calculateMileage(
        dailyCommuteMiles: Double,
        commuteDaysPerWeek: Int,
        weekendMilesPerWeek: Double,
        annualTripMiles: Double
    ): MileageResult {
        val commute = dailyCommuteMiles * commuteDaysPerWeek * 48 // 48 working weeks
        val personal = (weekendMilesPerWeek * 52) + annualTripMiles
        val total = commute + personal
        val servicing = when {
            total <= 6000 -> 180.0
            total <= 12000 -> 280.0
            total <= 20000 -> 420.0
            else -> 600.0
        }

        return MileageResult(
            annualCommuteMiles = commute,
            annualPersonalMiles = personal,
            totalAnnualMiles = total,
            estimatedServicingCostGbp = servicing
        )
    }

    // 5. Running Cost Calculator
    data class RunningCostResult(
        val monthlyTotalGbp: Double,
        val annualTotalGbp: Double,
        val breakdown: Map<String, Double>
    )

    fun calculateRunningCost(
        monthlyLoanPayment: Double,
        annualInsurance: Double,
        annualRoadTax: Double,
        monthlyFuel: Double,
        annualMotAndServicing: Double,
        monthlyParkingAndTolls: Double
    ): RunningCostResult {
        val annualIns = annualInsurance
        val annualTax = annualRoadTax
        val annualMot = annualMotAndServicing

        val annualLoan = monthlyLoanPayment * 12
        val annualFuelCost = monthlyFuel * 12
        val annualParking = monthlyParkingAndTolls * 12

        val totalAnnual = annualIns + annualTax + annualMot + annualLoan + annualFuelCost + annualParking
        val totalMonthly = totalAnnual / 12.0

        val breakdown = mapOf(
            "Finance Loan" to annualLoan,
            "Fuel" to annualFuelCost,
            "Insurance" to annualIns,
            "MOT & Servicing" to annualMot,
            "Road Tax" to annualTax,
            "Parking & Tolls" to annualParking
        )

        return RunningCostResult(
            monthlyTotalGbp = totalMonthly,
            annualTotalGbp = totalAnnual,
            breakdown = breakdown
        )
    }

    // 6. Journey Cost Calculator
    data class JourneyCostResult(
        val fuelCostGbp: Double,
        val tollsAndCongestionGbp: Double,
        val totalJourneyCostGbp: Double,
        val costPerPassengerGbp: Double
    )

    fun calculateJourneyCost(
        distanceMiles: Double,
        mpg: Double,
        fuelPricePerLitreGbp: Double,
        tollsGbp: Double,
        congestionChargeGbp: Double,
        passengersCount: Int
    ): JourneyCostResult {
        val fuelRes = calculateFuelCost(distanceMiles, mpg, fuelPricePerLitreGbp)
        val extraTolls = tollsGbp + congestionChargeGbp
        val total = fuelRes.totalCostGbp + extraTolls
        val passengers = passengersCount.coerceAtLeast(1)
        val perPerson = total / passengers

        return JourneyCostResult(
            fuelCostGbp = fuelRes.totalCostGbp,
            tollsAndCongestionGbp = extraTolls,
            totalJourneyCostGbp = total,
            costPerPassengerGbp = perPerson
        )
    }

    // 7. Fuel Economy Calculator
    data class FuelEconomyResult(
        val mpg: Double,
        val litresPer100Km: Double,
        val kmPerLitre: Double
    )

    fun calculateFuelEconomy(
        litresFilled: Double,
        distanceMiles: Double
    ): FuelEconomyResult {
        if (litresFilled <= 0 || distanceMiles <= 0) return FuelEconomyResult(0.0, 0.0, 0.0)
        val gallons = litresFilled / 4.54609
        val mpgVal = distanceMiles / gallons
        val km = distanceMiles * 1.60934
        val kmPerL = km / litresFilled
        val l100km = (litresFilled / km) * 100.0

        return FuelEconomyResult(
            mpg = mpgVal,
            litresPer100Km = l100km,
            kmPerLitre = kmPerL
        )
    }

    // 8. Monthly Budget Calculator
    data class MonthlyBudgetResult(
        val maxCarBudgetMonthlyGbp: Double,
        val recommendedLoanPaymentGbp: Double,
        val recommendedRunningCostsGbp: Double
    )

    fun calculateMonthlyBudget(
        monthlyTakeHomePayGbp: Double,
        budgetPercentage: Double = 15.0 // 15% rule of thumb
    ): MonthlyBudgetResult {
        val maxBudget = monthlyTakeHomePayGbp * (budgetPercentage / 100.0)
        val loanPart = maxBudget * 0.65 // 65% towards vehicle loan
        val runningPart = maxBudget * 0.35 // 35% towards fuel & insurance

        return MonthlyBudgetResult(
            maxCarBudgetMonthlyGbp = maxBudget,
            recommendedLoanPaymentGbp = loanPart,
            recommendedRunningCostsGbp = runningPart
        )
    }

    // 9. Vehicle Depreciation Calculator
    data class DepreciationResult(
        val currentEstimatedValueGbp: Double,
        val totalDepreciationGbp: Double,
        val retainedValuePercent: Double,
        val futureValue3YearsGbp: Double
    )

    fun calculateDepreciation(
        originalPriceGbp: Double,
        vehicleAgeYears: Int,
        totalMileage: Double
    ): DepreciationResult {
        if (originalPriceGbp <= 0) return DepreciationResult(0.0, 0.0, 0.0, 0.0)

        // Average UK car loses 20% in year 1, 15% in year 2, 10% each subsequent year
        var valRate = 1.0
        for (year in 1..vehicleAgeYears) {
            val yearDep = when (year) {
                1 -> 0.20
                2 -> 0.15
                else -> 0.10
            }
            valRate *= (1 - yearDep)
        }

        // Mileage adjustment: 10,000 miles/yr average
        val expectedMileage = vehicleAgeYears * 10000.0
        val mileageDiff = totalMileage - expectedMileage
        val mileageFactor = if (mileageDiff > 0) (1.0 - (mileageDiff / 100000.0) * 0.05) else (1.0 + (-mileageDiff / 100000.0) * 0.03)

        val currentValue = (originalPriceGbp * valRate * mileageFactor).coerceAtLeast(500.0)
        val totalDep = originalPriceGbp - currentValue
        val retained = (currentValue / originalPriceGbp) * 100.0
        val future3Yr = (currentValue * 0.70).coerceAtLeast(300.0)

        return DepreciationResult(
            currentEstimatedValueGbp = currentValue,
            totalDepreciationGbp = totalDep,
            retainedValuePercent = retained,
            futureValue3YearsGbp = future3Yr
        )
    }

    // 10. Insurance Estimate Calculator
    data class InsuranceEstimateResult(
        val estimatedAnnualPremiumGbp: Double,
        val estimatedMonthlyPremiumGbp: Double,
        val riskRatingTier: String,
        val tipsToReduce: List<String>
    )

    fun calculateInsuranceEstimate(
        driverAge: Int,
        ncbYears: Int,
        insuranceGroup: Int, // 1 to 50
        overnightParking: String // "GARAGE", "DRIVEWAY", "STREET"
    ): InsuranceEstimateResult {
        val baseGroupCost = insuranceGroup * 28.0
        val ageMultiplier = when {
            driverAge < 21 -> 2.8
            driverAge < 25 -> 1.8
            driverAge < 30 -> 1.2
            driverAge < 65 -> 0.95
            else -> 1.1
        }

        val ncbDiscount = (ncbYears * 0.08).coerceAtMost(0.60) // Up to 60% discount
        val parkingFactor = when (overnightParking.uppercase()) {
            "GARAGE" -> 0.85
            "DRIVEWAY" -> 0.92
            else -> 1.10
        }

        val annual = (baseGroupCost * ageMultiplier * (1 - ncbDiscount) * parkingFactor).coerceAtLeast(280.0)
        val monthly = (annual * 1.10) / 12.0 // Includes interest for monthly install

        val riskTier = when {
            annual < 500 -> "Low Risk Tier"
            annual < 1100 -> "Moderate Risk Tier"
            else -> "High Risk Tier"
        }

        val tips = listOf(
            "Increase voluntary excess to reduce annual premium",
            "Consider adding an experienced named driver with high NCB",
            "Keep vehicle in a driveway or locked garage overnight",
            "Pay annually upfront to save ~10% interest charges"
        )

        return InsuranceEstimateResult(
            estimatedAnnualPremiumGbp = annual,
            estimatedMonthlyPremiumGbp = monthly,
            riskRatingTier = riskTier,
            tipsToReduce = tips
        )
    }
}

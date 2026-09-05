package com.aaapp.appguru.myusedcarsaleuk.features.advice

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aaapp.appguru.myusedcarsaleuk.common.*
import com.aaapp.appguru.myusedcarsaleuk.core.ads.AdMobNativeAdCard
import com.aaapp.appguru.myusedcarsaleuk.core.config.ConfigManager
import com.aaapp.appguru.myusedcarsaleuk.ui.theme.*

@Composable
fun BuyingAdviceScreen(
    onNavigateRoute: (String) -> Unit
) {
    Scaffold(
        topBar = {
            AppTopBar(
                title = "Car Buying Advice & Tips",
                subtitle = "Detailed UK Used Car Buyer Guide",
                showBackButton = true,
                onBackClick = { onNavigateRoute("buy_cars") },
                onNavigateRoute = onNavigateRoute
            )
        },
        bottomBar = {
            AppBottomBar(
                currentRoute = "buy_cars",
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
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item { Spacer(modifier = Modifier.height(2.dp)) }

            // Hero Header
            item {
                HeroBanner(
                    title = "UK Used Car Buying Advice & Tips",
                    subtitle = "Master the inspection process, know your consumer rights, avoid legal traps, and negotiate the best price.",
                    ctaText = "Try Car Loan Calculator",
                    badgeText = "Verified UK Buyer Guide",
                    onCtaClick = { onNavigateRoute("calculator_detail/loan") }
                )
            }

            // Quick Nav Links to Calculators
            item {
                Text(
                    text = "HELPFUL CALCULATORS & TOOLS",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Black,
                    color = NavyDark
                )
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    AdviceToolChip(
                        title = "Loan Calc",
                        icon = Icons.Default.Calculate,
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigateRoute("calculator_detail/loan") }
                    )
                    AdviceToolChip(
                        title = "Road Tax",
                        icon = Icons.Default.ConfirmationNumber,
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigateRoute("calculator_detail/road_tax") }
                    )
                    AdviceToolChip(
                        title = "Fuel Cost",
                        icon = Icons.Default.LocalGasStation,
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigateRoute("calculator_detail/fuel") }
                    )
                    AdviceToolChip(
                        title = "History",
                        icon = Icons.Default.History,
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigateRoute("vehicle_history") }
                    )
                }
            }

            // Section 1: Dealer vs Private
            item {
                AdviceSectionCard(
                    icon = Icons.Default.Storefront,
                    iconTint = RoyalBlue,
                    title = "1. Main Dealer vs Independent vs Private Seller",
                    subtitle = "Understanding warranties & Consumer Rights Act 2015"
                ) {
                    AdviceBulletPoint(
                        title = "Franchised Main Dealers",
                        desc = "Offer manufacturer 'Approved Used' schemes with multi-point checks, minimum 12-month warranties, and full history guarantee. Highest price but maximum peace of mind."
                    )
                    AdviceBulletPoint(
                        title = "Independent Used Dealers",
                        desc = "Competitive prices with standard 3-month warranties. Protected under the Consumer Rights Act 2015 (right to full refund within 30 days if a major fault exists)."
                    )
                    AdviceBulletPoint(
                        title = "Private Sellers",
                        desc = "Cheapest prices, but sold 'as seen'. No statutory warranty rights unless the seller lied or misdescribed the car. Always perform a thorough inspection and history check."
                    )
                }
            }

            // Section 2: 10-Point Pre-Purchase Inspection
            item {
                AdviceSectionCard(
                    icon = Icons.Default.FactCheck,
                    iconTint = SuccessGreen,
                    title = "2. 10-Point Pre-Purchase Inspection Checklist",
                    subtitle = "What to check before paying any deposit"
                ) {
                    AdviceCheckItem("1. Engine & Cold Start", "Start engine cold. Watch for blue smoke (oil leak), white smoke (head gasket), or metallic rattles.")
                    AdviceCheckItem("2. Fluid Levels & Oil Cap", "Remove oil cap. White creamy mayonnaise sludge indicates head gasket failure.")
                    AdviceCheckItem("3. Tires & Brakes", "Ensure tires have at least 3mm tread depth (legal limit 1.6mm) and check brake disc lips.")
                    AdviceCheckItem("4. Clutch & Gearbox", "Test clutch bite point. In automatics, ensure smooth shifts without jerking or delay.")
                    AdviceCheckItem("5. Bodywork & Panel Gaps", "Uneven panel gaps or overspray suggest past accident damage or structural repair.")
                    AdviceCheckItem("6. Suspension & Steering", "Listen for knocks over speed bumps and ensure car drives straight without pulling.")
                    AdviceCheckItem("7. Air Conditioning & Heating", "Test heater and AC controls; non-functional AC can be an expensive compressor repair.")
                    AdviceCheckItem("8. Electricals & Dash Lights", "Ensure warning lights (ABS, Engine, Airbag) turn on at ignition and switch off when running.")
                    AdviceCheckItem("9. Spare Wheel & Jack", "Verify locking wheel nut key, spare tire or inflation kit, and original jack are present.")
                    AdviceCheckItem("10. Road Test", "Drive for at least 15-20 minutes on both local roads and dual carriageways up to 70 mph.")
                }
            }

            item {
                AdMobNativeAdCard(
                    isPlacementEnabled = ConfigManager.adsConfig.isBuyingAdviceNativeAdEnabled
                )
            }

            // Section 3: V5C & Official Paperwork
            item {
                AdviceSectionCard(
                    icon = Icons.Default.Description,
                    iconTint = AccentGoldDark,
                    title = "3. V5C Logbook & Paperwork Verification",
                    subtitle = "Crucial steps to avoid stolen cars & fraud"
                ) {
                    AdviceBulletPoint(
                        title = "V5C Watermark & Serial Number",
                        desc = "Hold the V5C logbook to the light to verify the official 'DVL' watermark. Match the document reference number with the front page."
                    )
                    AdviceBulletPoint(
                        title = "VIN / Chassis Number Matching",
                        desc = "Compare the 17-digit VIN on the V5C logbook against the vehicle's windscreen plate, engine bay stamp, and door pillar sticker."
                    )
                    AdviceBulletPoint(
                        title = "Address & Identity Match",
                        desc = "Ensure you view the car at the registered address listed on the V5C logbook. Ask the seller for photo ID to confirm identity."
                    )
                    AdviceBulletPoint(
                        title = "Service Records & Invoices",
                        desc = "Check stamped service book and receipts for major maintenance like cambelt/timing belt replacement and water pump servicing."
                    )
                }
            }

            // Section 4: HPI & MOT Checks
            item {
                AdviceSectionCard(
                    icon = Icons.Default.Shield,
                    iconTint = RoyalBlue,
                    title = "4. HPI & DVLA MOT History Verification",
                    subtitle = "Spotting mileage rollbacks and write-off categories"
                ) {
                    AdviceBulletPoint(
                        title = "Official DVLA MOT History",
                        desc = "Check past MOT test records on GOV.UK for mileage consistency across years and review recurring advisory notes."
                    )
                    AdviceBulletPoint(
                        title = "Outstanding Finance (HPI)",
                        desc = "If a vehicle has unpaid finance, the finance company legally owns the car. Always verify finance is settled before buying."
                    )
                    AdviceBulletPoint(
                        title = "Insurance Write-Off Categories",
                        desc = "Cat N (Non-structural) and Cat S (Structural) vehicles are significantly cheaper but harder to insure and resell. Cat A & B must be scrapped."
                    )
                }
            }

            // Section 5: Budgeting & Total Cost
            item {
                AdviceSectionCard(
                    icon = Icons.Default.AccountBalanceWallet,
                    iconTint = SuccessGreen,
                    title = "5. Budgeting & Total Ownership Costs",
                    subtitle = "Factoring hidden ongoing costs alongside purchase price"
                ) {
                    AdviceBulletPoint(
                        title = "Road Tax (VED) Surcharges",
                        desc = "Cars registered after April 2017 with a original list price over £40,000 incur a £410/yr luxury car surcharge for 5 years."
                    )
                    AdviceBulletPoint(
                        title = "Insurance Group Ratings",
                        desc = "Check the vehicle insurance group (1 to 50) and obtain insurance quotes before making an offer."
                    )
                    AdviceBulletPoint(
                        title = "Routine Maintenance Fund",
                        desc = "Set aside £500–£1,000 annually for annual MOT, servicing, brake pads, and tire replacements."
                    )
                }
            }

            // Section 6: Negotiation & Payment Safety
            item {
                AdviceSectionCard(
                    icon = Icons.Default.AttachMoney,
                    iconTint = AccentGoldDark,
                    title = "6. Price Negotiation & Safe Payment Tips",
                    subtitle = "How to haggle politely and secure your transaction"
                ) {
                    AdviceBulletPoint(
                        title = "Use Inspection Defects as Haggling Leverage",
                        desc = "Politely point out worn tires, scuffed alloys, or upcoming MOT items to justify a £200–£500 discount."
                    )
                    AdviceBulletPoint(
                        title = "Never Pay Large Cash Amounts",
                        desc = "Use instant bank transfers (Faster Payments) so you have a digital receipt. Avoid carrying thousands in cash."
                    )
                    AdviceBulletPoint(
                        title = "Deposit Safety Rules",
                        desc = "Pay maximum £100–£200 deposit only after inspecting the car in person and getting a written, signed receipt."
                    )
                }
            }

            // Section 7: EV & Hybrid Specifics
            item {
                AdviceSectionCard(
                    icon = Icons.Default.EvStation,
                    iconTint = RoyalBlue,
                    title = "7. Electric Vehicle (EV) & Hybrid Buying Tips",
                    subtitle = "Battery degradation and charging equipment checks"
                ) {
                    AdviceBulletPoint(
                        title = "Battery State of Health (SOH)",
                        desc = "Ask for a battery diagnostic health report showing remaining capacity percentage and maximum full charge range."
                    )
                    AdviceBulletPoint(
                        title = "Charging Cables Included",
                        desc = "Ensure the 3-pin home trickle cable and Type 2 public charging cable are present in the boot."
                    )
                    AdviceBulletPoint(
                        title = "Manufacturer Battery Warranty",
                        desc = "Most EV batteries have a separate 8-year or 100,000-mile manufacturer warranty. Confirm remaining coverage."
                    )
                }
            }

            // Bottom Action Card
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = NavyDark),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp)
                    ) {
                        Text(
                            text = "Ready to Calculate Your Budget?",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Use our suite of 10+ UK automotive calculators to estimate car loan payments, fuel costs, road tax and insurance.",
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.8f),
                            lineHeight = 16.sp
                        )
                        Spacer(modifier = Modifier.height(14.dp))
                        Button(
                            onClick = { onNavigateRoute("smart_tools") },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = AccentGold,
                                contentColor = NavyDark
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Explore All Calculators", fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.width(6.dp))
                            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

@Composable
private fun AdviceToolChip(
    title: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    BlinkCard(
        onClick = onClick,
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp, horizontal = 4.dp)
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = RoyalBlue, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = title, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = NavyDark, maxLines = 1)
        }
    }
}

@Composable
private fun AdviceSectionCard(
    icon: ImageVector,
    iconTint: Color,
    title: String,
    subtitle: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    color = iconTint.copy(alpha = 0.12f),
                    shape = CircleShape,
                    modifier = Modifier.size(36.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(imageVector = icon, contentDescription = null, tint = iconTint, modifier = Modifier.size(20.dp))
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(text = title, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = NavyDark)
                    Text(text = subtitle, fontSize = 11.sp, color = TextSecondaryLight)
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = SlateBackground)
            Spacer(modifier = Modifier.height(12.dp))
            content()
        }
    }
}

@Composable
private fun AdviceBulletPoint(
    title: String,
    desc: String
) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Row(verticalAlignment = Alignment.Top) {
            Text(text = "• ", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = RoyalBlue)
            Text(text = title, fontWeight = FontWeight.Bold, fontSize = 12.5.sp, color = NavyDark)
        }
        Text(
            text = desc,
            fontSize = 11.5.sp,
            color = TextPrimaryLight,
            lineHeight = 16.sp,
            modifier = Modifier.padding(start = 12.dp, top = 2.dp)
        )
    }
}

@Composable
private fun AdviceCheckItem(
    title: String,
    desc: String
) {
    Row(
        modifier = Modifier.padding(vertical = 4.dp),
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            tint = SuccessGreen,
            modifier = Modifier
                .size(16.dp)
                .padding(top = 2.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = title, fontWeight = FontWeight.Bold, fontSize = 12.5.sp, color = NavyDark)
            Text(
                text = desc,
                fontSize = 11.sp,
                color = TextSecondaryLight,
                lineHeight = 15.sp
            )
        }
    }
}

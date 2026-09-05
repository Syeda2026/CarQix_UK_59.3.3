package com.aaapp.appguru.myusedcarsaleuk.model

object AutomotiveDataProvider {

    val marketplaces = listOf(
        MarketplaceInfo(
            id = "autotrader",
            name = "AutoTrader",
            tagLine = "UK's largest car marketplace",
            description = "Search over 400,000 used cars for sale from private sellers and verified UK dealers.",
            rating = 4.8,
            reviewsCount = "120,000+",
            benefits = listOf("Largest choice of vehicles", "Verified dealer badges", "Price indicator comparison"),
            badge = "UK #1 Marketplace",
            webUrlKey = "autotrader"
        ),
        MarketplaceInfo(
            id = "gumtree",
            name = "Gumtree Cars",
            tagLine = "Local car deals near you",
            description = "Connect directly with private buyers and sellers in your local town or region.",
            rating = 4.4,
            reviewsCount = "20,000+",
            benefits = listOf("Direct local communication", "No listing fees for basic ads", "Bargain private sales"),
            badge = "Local Deals",
            webUrlKey = "gumtree"
        ),
        MarketplaceInfo(
            id = "carwow",
            name = "carwow",
            tagLine = "Buy verified used cars with warranty",
            description = "Browse quality checked used cars from top UK main dealers with money-back guarantees.",
            rating = 4.7,
            reviewsCount = "50,000+",
            benefits = listOf("Main dealer approved", "Money back guarantee", "Home delivery options"),
            badge = "Main Dealer Quality",
            webUrlKey = "carwow"
        ),
        MarketplaceInfo(
            id = "arnoldclark",
            name = "Arnold Clark",
            tagLine = "Europe's largest independent dealer group",
            description = "Access thousands of quality used cars across 200+ UK branches with Click & Collect.",
            rating = 4.6,
            reviewsCount = "35,000+",
            benefits = listOf("200+ UK branches", "Click & Collect", "Free warranty included"),
            badge = "Trusted Retailer",
            webUrlKey = "arnoldclark"
        ),
        MarketplaceInfo(
            id = "googlecars",
            name = "Google Car Search",
            tagLine = "Search across all UK car portals",
            description = "Find listings aggregated from all top UK automotive websites and local dealership stocks.",
            rating = 4.9,
            reviewsCount = "500,000+",
            benefits = listOf("All UK sites aggregated", "Real-time Google search", "Instant direct access"),
            badge = "Comprehensive Search",
            webUrlKey = "googlecars"
        ),
        MarketplaceInfo(
            id = "aacars",
            name = "AA Cars",
            tagLine = "Trusted dealer network",
            description = "Every car comes with 12 months free AA Breakdown Cover and dealer inspection.",
            rating = 4.6,
            reviewsCount = "30,000+",
            benefits = listOf("Free 12m AA Breakdown", "AA Inspected vehicles"),
            badge = "AA Approved",
            webUrlKey = "aacars"
        ),
        MarketplaceInfo(
            id = "ebaymotors",
            name = "eBay Motors UK",
            tagLine = "Huge selection from private & trade",
            description = "Fixed-price and auction listings for used cars and rare models across the UK.",
            rating = 4.4,
            reviewsCount = "85,000+",
            benefits = listOf("Auction & Buy-It-Now", "Private bargains"),
            badge = "Auctions & Private",
            webUrlKey = "ebaymotors"
        ),
        MarketplaceInfo(
            id = "pistonheads",
            name = "PistonHeads",
            tagLine = "Sports, luxury & enthusiast cars",
            description = "The UK's premium portal for performance, classic, and enthusiast vehicles.",
            rating = 4.7,
            reviewsCount = "18,000+",
            benefits = listOf("Enthusiast community", "Verified performance cars"),
            badge = "Enthusiasts #1",
            webUrlKey = "pistonheads"
        ),
        MarketplaceInfo(
            id = "exchangeandmart",
            name = "Exchange & Mart",
            tagLine = "Classic & modern used cars",
            description = "One of the UK's longest running classified car advertising services.",
            rating = 4.3,
            reviewsCount = "15,000+",
            benefits = listOf("Established UK classifieds", "Private & trade ads"),
            badge = "Established Classifieds",
            webUrlKey = "exchangeandmart"
        ),
        MarketplaceInfo(
            id = "vertumotors",
            name = "Vertu Motors",
            tagLine = "Major UK franchise dealer group",
            description = "Find manufacturer-approved used cars from official dealership branches.",
            rating = 4.6,
            reviewsCount = "25,000+",
            benefits = listOf("Manufacturer approved", "Full main dealer warranty"),
            badge = "Franchise Dealer",
            webUrlKey = "vertumotors"
        ),
        MarketplaceInfo(
            id = "evanshalshaw",
            name = "Evans Halshaw",
            tagLine = "Nationwide dealer stock & low prices",
            description = "Popular UK dealer network offering competitive pricing on thousands of used cars.",
            rating = 4.5,
            reviewsCount = "32,000+",
            benefits = listOf("Nationwide price match guarantee", "Click & Collect"),
            badge = "Price Match",
            webUrlKey = "evanshalshaw"
        ),
        MarketplaceInfo(
            id = "parkers",
            name = "Parkers Cars",
            tagLine = "Trusted UK valuation & car marketplace",
            description = "Browse thousands of inspected used cars with accurate price guide valuations.",
            rating = 4.7,
            reviewsCount = "40,000+",
            benefits = listOf("Price guide valuations", "Independent reviews", "Inspected dealer cars"),
            badge = "Valuations & Deals",
            webUrlKey = "parkers"
        ),
        MarketplaceInfo(
            id = "raccars",
            name = "RAC Cars",
            tagLine = "RAC Approved used car marketplace",
            description = "Quality used cars with RAC history check & RAC breakdown cover options.",
            rating = 4.6,
            reviewsCount = "38,000+",
            benefits = listOf("RAC Approved inspection", "RAC history check", "Patrol support"),
            badge = "RAC Approved",
            webUrlKey = "raccars"
        ),
        MarketplaceInfo(
            id = "motorpoint",
            name = "Motorpoint",
            tagLine = "Nearly new & used car superstores",
            description = "Unbeatable prices on nearly new and low mileage cars with nationwide delivery.",
            rating = 4.5,
            reviewsCount = "34,000+",
            benefits = listOf("Nearly new low mileage", "Price promise guarantee", "Nationwide delivery"),
            badge = "Car Superstore",
            webUrlKey = "motorpoint"
        ),
        MarketplaceInfo(
            id = "marshall",
            name = "Marshall Motor Group",
            tagLine = "Top 10 UK dealership group",
            description = "Manufacturer approved used vehicles across major UK dealership locations.",
            rating = 4.6,
            reviewsCount = "22,000+",
            benefits = listOf("Manufacturer trained techs", "Comprehensive warranty", "Flexible finance"),
            badge = "Top Dealership",
            webUrlKey = "marshall"
        )
    )

    val sellingProviders = listOf(
        ProviderInfo(
            id = "motorway",
            name = "Motorway",
            category = "SELL",
            description = "Get offer bids from 5,000+ verified UK dealers. 100% free, fast home collection and instant payment.",
            rating = 4.8,
            reviewsCount = "65,000+",
            keyRateOrFeature = "Instant Dealer Bids",
            benefits = listOf("Free home collection", "5,000+ dealer network", "Instant bank transfer"),
            badge = "Best for Highest Price",
            partnerKey = "motorway"
        ),
        ProviderInfo(
            id = "webuyanycar",
            name = "webuyanycar",
            category = "SELL",
            description = "Quick 60-second online valuation and over 500 local branch locations nationwide for fast sale.",
            rating = 4.6,
            reviewsCount = "250,000+",
            keyRateOrFeature = "60-Second Valuation",
            benefits = listOf("500+ branches across UK", "Sell in under an hour", "Guaranteed valuation offer"),
            badge = "Fastest Sale",
            partnerKey = "webuyanycar"
        ),
        ProviderInfo(
            id = "carwow",
            name = "carwow",
            category = "SELL",
            description = "Sell your car directly to verified UK dealers through carwow's online bidding platform.",
            rating = 4.6,
            reviewsCount = "40,000+",
            keyRateOrFeature = "Dealer Direct Bidding",
            benefits = listOf("Free home collection", "No admin fees", "Competitive dealer offers"),
            partnerKey = "carwow_sell"
        ),
        ProviderInfo(
            id = "arnoldclark",
            name = "Arnold Clark",
            category = "SELL",
            description = "Sell or part-exchange your vehicle with one of the UK's largest automotive retailer networks.",
            rating = 4.5,
            reviewsCount = "18,000+",
            keyRateOrFeature = "Part-Exchange Specialist",
            benefits = listOf("Over 200 UK branches", "Instant part-exchange quote", "Trusted retail group"),
            partnerKey = "arnoldclark_sell"
        )
    )

    val financeProviders = listOf(
        ProviderInfo(
            id = "autotrader_finance",
            name = "AutoTrader Finance",
            category = "FINANCE",
            description = "Compare personalized finance quotes from multiple top UK lenders with zero impact on credit score.",
            rating = 4.7,
            reviewsCount = "35,000+",
            keyRateOrFeature = "8.9% APR Representative",
            benefits = listOf("Soft credit check only", "HP & PCP options available", "Fast online decision"),
            badge = "Most Popular",
            partnerKey = "autotrader_finance"
        ),
        ProviderInfo(
            id = "close_brothers",
            name = "Close Brothers Motor Finance",
            category = "FINANCE",
            description = "Specialist UK motor finance provider offering tailored Hire Purchase and PCP packages.",
            rating = 4.5,
            reviewsCount = "15,000+",
            keyRateOrFeature = "9.9% APR Representative",
            benefits = listOf("Flexible term lengths", "FCA regulated lender", "UK based support team"),
            badge = "Flexible Terms",
            partnerKey = "close_brothers"
        ),
        ProviderInfo(
            id = "motonovo",
            name = "MotoNovo Finance",
            category = "FINANCE",
            description = "Award-winning car finance lender helping UK motorists fund new and used cars easily.",
            rating = 4.6,
            reviewsCount = "22,000+",
            keyRateOrFeature = "9.4% APR Representative",
            benefits = listOf("No hidden admin fees", "Finance for all credit ratings", "Direct dealer settlement"),
            partnerKey = "motonovo"
        )
    )

    val insuranceProviders = listOf(
        ProviderInfo(
            id = "comparethemarket",
            name = "comparethemarket",
            category = "INSURANCE",
            description = "Compare 100+ UK car insurance brands in 5 minutes and save on your annual premium.",
            rating = 4.8,
            reviewsCount = "110,000+",
            keyRateOrFeature = "Compare 100+ Insurers",
            benefits = listOf("Save up to £450*", "Meerkat Meals & Movies rewards", "Comprehensive & Third Party cover"),
            badge = "UK #1 Comparison",
            partnerKey = "comparethemarket"
        ),
        ProviderInfo(
            id = "moneysupermarket",
            name = "MoneySuperMarket",
            category = "INSURANCE",
            description = "Find cheap car insurance quotes from trusted UK insurers with Defaqto 5-star ratings.",
            rating = 4.7,
            reviewsCount = "95,000+",
            keyRateOrFeature = "Save up to £472*",
            benefits = listOf("SuperSave Price Guarantee", "Defaqto rated options", "Quick policy setup"),
            badge = "Price Guarantee",
            partnerKey = "moneysupermarket"
        ),
        ProviderInfo(
            id = "confused",
            name = "Confused.com",
            category = "INSURANCE",
            description = "The UK's longest-running car insurance comparison website offering fast quotes and rewards.",
            rating = 4.6,
            reviewsCount = "80,000+",
            keyRateOrFeature = "Quick 3-Min Quotes",
            benefits = listOf("Free driver rewards", "Clear price breakdown", "Coverage filter tool"),
            partnerKey = "confused"
        ),
        ProviderInfo(
            id = "gocompare",
            name = "Go.Compare",
            category = "INSURANCE",
            description = "Free £250 excess cover included automatically when you buy car insurance through Go.Compare.",
            rating = 4.6,
            reviewsCount = "75,000+",
            keyRateOrFeature = "Free £250 Excess Cover",
            benefits = listOf("Free £250 excess protection", "Wide UK panel", "Easy modification tracker"),
            badge = "Free Excess Cover",
            partnerKey = "gocompare"
        )
    )

    val historyProviders = listOf(
        ProviderInfo(
            id = "gov_mot",
            name = "GOV.UK MOT & History",
            category = "HISTORY",
            description = "Official UK government database check for MOT test status, pass/fail history, advisory notes & mileage records.",
            rating = 4.9,
            reviewsCount = "200,000+",
            keyRateOrFeature = "Free Official DVLA Data",
            benefits = listOf("100% Free official records", "Advisory note breakdown", "Odometer mileage verification"),
            badge = "Official GOV.UK",
            partnerKey = "gov_mot"
        ),
        ProviderInfo(
            id = "hpicheck",
            name = "HPI Check",
            category = "HISTORY",
            description = "The original trusted vehicle history check. Identifies hidden dangers before you buy a used car.",
            rating = 4.7,
            reviewsCount = "50,000+",
            keyRateOrFeature = "Original HPI Guarantee",
            benefits = listOf("Mileage verification check", "Stolen vehicle alert", "Number plate change history"),
            badge = "Industry Standard",
            partnerKey = "hpicheck"
        ),
        ProviderInfo(
            id = "carvertical",
            name = "carVertical",
            category = "HISTORY",
            description = "Blockchain-backed vehicle history reports with photos from previous auctions, accident records, and mileage timelines.",
            rating = 4.6,
            reviewsCount = "35,000+",
            keyRateOrFeature = "Auction Photos & Mileage Graph",
            benefits = listOf("Historical auction pictures", "Odometer rollback detection", "Equipment & spec list"),
            partnerKey = "carvertical"
        )
    )

    val breakdownProviders = listOf(
        ProviderInfo(
            id = "theaa_breakdown",
            name = "AA Breakdown Cover",
            category = "BREAKDOWN",
            description = "UK's largest breakdown organization with 2,500+ dedicated patrols responding 24/7.",
            rating = 4.7,
            reviewsCount = "85,000+",
            keyRateOrFeature = "24/7 Patrol Network",
            benefits = listOf("Fix 4 out of 5 cars at roadside", "Unlimited callouts", "Cover as driver or passenger"),
            badge = "Largest Patrol Network",
            partnerKey = "theaa_breakdown"
        ),
        ProviderInfo(
            id = "rac_breakdown",
            name = "RAC Breakdown Cover",
            category = "BREAKDOWN",
            description = "Complete peace of mind with RAC patrols fixing 4 out of 5 roadside breakdowns in an average of 30 minutes.",
            rating = 4.6,
            reviewsCount = "70,000+",
            keyRateOrFeature = "Average 30-Min Arrival",
            benefits = listOf("Universal Spare Wheel provided", "EV mobile charging patrol", "At-home recovery options"),
            badge = "Fast Arrival",
            partnerKey = "rac_breakdown"
        ),
        ProviderInfo(
            id = "greenflag",
            name = "Green Flag",
            category = "BREAKDOWN",
            description = "Great value UK breakdown cover with a national network of local breakdown mechanics.",
            rating = 4.5,
            reviewsCount = "30,000+",
            keyRateOrFeature = "Affordable Value Cover",
            benefits = listOf("50% cheaper than renewal quotes", "Local expert mechanics", "Discounts for low mileage"),
            badge = "Best Value",
            partnerKey = "greenflag"
        ),
        ProviderInfo(
            id = "britannia",
            name = "Britannia Rescue (LV=)",
            category = "BREAKDOWN",
            description = "Award-winning breakdown cover backed by LV= insurance with 4,000+ recovery technicians.",
            rating = 4.6,
            reviewsCount = "20,000+",
            keyRateOrFeature = "Defaqto 5-Star Rated",
            benefits = listOf("Onward travel & hotel cover", "UK & European options", "LV= customer discounts"),
            partnerKey = "britannia"
        )
    )

    val articles = listOf(
        GuideArticle(
            id = "guide_1",
            title = "How to Buy a Used Car Safely in the UK",
            summary = "10 essential inspection steps, test drive tips, paperwork verification, and fraud prevention advice.",
            fullContent = """
                Buying a used car in the UK is an exciting milestone, but thorough preparation is vital to avoid costly surprises.
                
                1. Always Inspect in Daylight
                Never inspect a car in the dark, in heavy rain, or under artificial garage lighting. Water droplets and shadows can hide scratches, bodywork dents, or uneven paint lines that indicate past accident repairs.
                
                2. Verify the V5C Logbook
                Ensure the seller's V5C registration document is genuine. Check that the document watermark is present when held to the light, and verify that the VIN (Vehicle Identification Number) on the logbook matches the VIN stamped on the car chassis and dashboard.
                
                3. Perform an Independent History Check
                A V5C alone does not prove the car is free of debt. Run a Vehicle History Check using Experian, HPI, or carVertical to confirm the car has no outstanding finance, has never been declared a total loss write-off (Category A, B, S, or N), and is not reported stolen.
                
                4. Check MOT History Online on GOV.UK
                Use the free GOV.UK MOT history tool to review past test results. Pay close attention to recurring advisories such as corroded brake pipes, worn suspension bushes, or oil leaks that could require expensive maintenance soon.
                
                5. Take a 20-Minute Cold Test Drive
                Insist on starting the engine when it is completely cold. Watch for unusual exhaust smoke, check that all dashboard warning lights illuminate and turn off properly, and test all gears, clutch biting point, air conditioning, and electrical accessories.
            """.trimIndent(),
            category = "Buying Guides",
            readTimeMinutes = 8,
            isFeatured = true,
            tag = "Essential Guide"
        ),
        GuideArticle(
            id = "guide_2",
            title = "PCP vs Hire Purchase (HP) Car Finance Explained",
            summary = "Understand monthly payment structures, balloon payments, mileage limits, and ownership at the end of agreement.",
            fullContent = """
                Choosing between Personal Contract Purchase (PCP) and Hire Purchase (HP) can save you thousands of pounds over your finance term.
                
                What is Hire Purchase (HP)?
                With HP, you pay a deposit followed by equal monthly payments over 2 to 5 years. Once the final payment (plus an option-to-purchase fee around £10) is made, you own the car outright. There are no annual mileage restrictions or wear-and-tear penalties.
                
                What is Personal Contract Purchase (PCP)?
                PCP offers lower monthly payments than HP because a large portion of the car's value is deferred until the end of the contract as a "Balloon Payment" (Guaranteed Minimum Future Value / GMFV).
                
                At the end of a PCP agreement, you have 3 options:
                1. Pay the balloon payment to keep the car.
                2. Hand the car back to the finance company with nothing more to pay (provided mileage and condition terms are met).
                3. Part-exchange the car using any equity above the balloon payment as a deposit for your next vehicle.
                
                Which should you choose?
                - Choose HP if you want to own the car long-term with no mileage limits.
                - Choose PCP if you prefer lower monthly costs and like changing your car every 3 years.
            """.trimIndent(),
            category = "Finance",
            readTimeMinutes = 6,
            isFeatured = true,
            tag = "Finance Guide"
        ),
        GuideArticle(
            id = "guide_3",
            title = "Best Reliable Used Cars Under £10,000 in 2026",
            summary = "Top dependable family hatchbacks, SUVs, and commuter cars with low maintenance costs and solid resale value.",
            fullContent = """
                With a £10,000 budget, you can buy a well-maintained, reliable used vehicle in the UK with modern tech, low emissions, and great fuel economy.
                
                1. Toyota Yaris / Corolla (Hybrid)
                Renowned for bulletproof reliability, low road tax, and incredible urban fuel economy (60+ MPG). Toyota's self-charging hybrid system is low-maintenance and highly durable.
                
                2. Ford Fiesta 1.0 EcoBoost (Post-2018)
                The UK's best-selling hatchback offers sharp handling, cheap spare parts, and excellent availability. Ensure wet timing belt service intervals have been strictly adhered to.
                
                3. Honda Civic 1.8 i-VTEC or 1.6 i-DTEC
                Spacious boot, futuristic dashboard, and class-leading mechanical dependability make the Civic an outstanding long-distance cruiser.
                
                4. Volkswagen Golf Mk7 / Mk7.5
                Refined build quality, quiet highway ride, and timeless styling. The 1.4 TSI and 2.0 TDI engines offer the perfect balance of performance and efficiency.
                
                5. Kia Sportage / Hyundai Tucson (2016-2019)
                Practical family SUVs with high seating positions, generous equipment levels, and strong manufacturer reliability ratings.
            """.trimIndent(),
            category = "Reviews",
            readTimeMinutes = 7,
            tag = "Top Picks"
        ),
        GuideArticle(
            id = "guide_4",
            title = "How to Lower Your UK Car Insurance Premium",
            summary = "Proven strategies to reduce annual premiums: job title tweaks, voluntary excess, named drivers, and security devices.",
            fullContent = """
                UK car insurance costs can be optimized by following these industry-tested recommendations:
                
                - Buy 20-26 Days Before Renewal: Insurers view drivers who renew last-minute as higher risk. Purchasing quotes 3 weeks in advance can lower quotes significantly.
                - Add an Experienced Named Driver: Adding a parent or spouse with a clean driving record and high NCB can reduce risk profiling for young drivers.
                - Tweak Your Job Title Legitimately: Using "Administrator" instead of "Clerk", or "Kitchen Staff" instead of "Chef" can alter insurance band risk calculations while remaining accurate.
                - Increase Voluntary Excess Reasonably: Agreeing to a slightly higher voluntary excess lowers the insurer's potential payout, reducing your annual premium.
            """.trimIndent(),
            category = "Insurance",
            readTimeMinutes = 5,
            tag = "Money Saving"
        )
    )
}

package com.aaapp.appguru.myusedcarsaleuk.core.config

data class PartnerUrlConfig(
    val partnerId: String,
    val name: String,
    val publicUrl: String,
    val affiliateUrl: String,
    val isEnabled: Boolean = true,
    val priority: Int = 1
)

data class AffiliateConfig(
    val partnerUrls: Map<String, PartnerUrlConfig> = mapOf(
        // Buy Cars / Marketplaces
        "autotrader" to PartnerUrlConfig("autotrader", "AutoTrader", "https://www.autotrader.co.uk/car-search", "https://www.autotrader.co.uk/car-search?aff=usedcarsuk", true, 1),
        "gumtree" to PartnerUrlConfig("gumtree", "Gumtree Cars", "https://www.gumtree.com/cars", "https://www.gumtree.com/cars?aff=usedcarsuk", true, 2),
        "carwow" to PartnerUrlConfig("carwow", "carwow", "https://www.carwow.co.uk/used-cars", "https://www.carwow.co.uk/used-cars?aff=usedcarsuk", true, 3),
        "arnoldclark" to PartnerUrlConfig("arnoldclark", "Arnold Clark", "https://www.arnoldclark.com/used-cars", "https://www.arnoldclark.com/used-cars?aff=usedcarsuk", true, 4),
        "googlecars" to PartnerUrlConfig("googlecars", "Google Car Search", "https://www.google.co.uk/search?q=used+cars+for+sale+uk", "https://www.google.co.uk/search?q=used+cars+for+sale+uk", true, 5),
        "aacars" to PartnerUrlConfig("aacars", "AA Cars", "https://www.theaa.com/cars", "https://www.theaa.com/cars", true, 6),
        "ebaymotors" to PartnerUrlConfig("ebaymotors", "eBay Motors UK", "https://www.ebay.co.uk/b/Cars/9801", "https://www.ebay.co.uk/b/Cars/9801", true, 7),
        "pistonheads" to PartnerUrlConfig("pistonheads", "PistonHeads", "https://www.pistonheads.com/", "https://www.pistonheads.com/", true, 8),
        "exchangeandmart" to PartnerUrlConfig("exchangeandmart", "Exchange & Mart", "https://www.exchangeandmart.co.uk/", "https://www.exchangeandmart.co.uk/", true, 9),
        "vertumotors" to PartnerUrlConfig("vertumotors", "Vertu Motors", "https://www.vertumotors.com/used-cars/", "https://www.vertumotors.com/used-cars/", true, 10),
        "evanshalshaw" to PartnerUrlConfig("evanshalshaw", "Evans Halshaw", "https://www.evanshalshaw.com/search/", "https://www.evanshalshaw.com/search/", true, 11),
        "parkers" to PartnerUrlConfig("parkers", "Parkers Cars", "https://www.parkers.co.uk/cars-for-sale/", "https://www.parkers.co.uk/cars-for-sale/?aff=usedcarsuk", true, 12),
        "raccars" to PartnerUrlConfig("raccars", "RAC Cars", "https://www.rac.co.uk/cars", "https://www.rac.co.uk/cars?aff=usedcarsuk", true, 13),
        "motorpoint" to PartnerUrlConfig("motorpoint", "Motorpoint", "https://www.motorpoint.co.uk/used-cars", "https://www.motorpoint.co.uk/used-cars", true, 14),
        "marshall" to PartnerUrlConfig("marshall", "Marshall Motor Group", "https://www.marshall.co.uk/used-cars/", "https://www.marshall.co.uk/used-cars/", true, 16),

        // Sell Cars & Valuations
        "motorway" to PartnerUrlConfig("motorway", "Motorway", "https://motorway.co.uk", "https://motorway.co.uk?aff=usedcarsuk", true, 1),
        "webuyanycar" to PartnerUrlConfig("webuyanycar", "webuyanycar", "https://www.webuyanycar.com", "https://www.webuyanycar.com?aff=usedcarsuk", true, 2),
        "autotrader_sell" to PartnerUrlConfig("autotrader_sell", "AutoTrader Sell My Car", "https://www.autotrader.co.uk/sell-my-car", "https://www.autotrader.co.uk/sell-my-car?aff=usedcarsuk", true, 3),
        "carwow_sell" to PartnerUrlConfig("carwow_sell", "carwow Sell My Car", "https://www.carwow.co.uk/sell-my-car", "https://www.carwow.co.uk/sell-my-car?aff=usedcarsuk", true, 4),
        "arnoldclark_sell" to PartnerUrlConfig("arnoldclark_sell", "Arnold Clark Sell My Car", "https://www.arnoldclark.com/sell-my-car", "https://www.arnoldclark.com/sell-my-car?aff=usedcarsuk", true, 5),
        "gumtree_sell" to PartnerUrlConfig("gumtree_sell", "Gumtree Sell Car", "https://www.gumtree.com/post", "https://www.gumtree.com/post?aff=usedcarsuk", true, 6),
        "autotrader_valuation" to PartnerUrlConfig("autotrader_valuation", "AutoTrader Free Car Valuation", "https://www.autotrader.co.uk/car-valuation", "https://www.autotrader.co.uk/car-valuation?aff=usedcarsuk", true, 7),

        // Vehicle History Checks
        "gov_mot" to PartnerUrlConfig("gov_mot", "GOV.UK MOT History", "https://www.gov.uk/check-mot-history", "https://www.gov.uk/check-mot-history", true, 1),
        "hpicheck" to PartnerUrlConfig("hpicheck", "HPI Check", "https://www.hpicheck.com", "https://www.hpicheck.com?aff=usedcarsuk", true, 2),
        "carvertical" to PartnerUrlConfig("carvertical", "carVertical", "https://www.carvertical.com/uk", "https://www.carvertical.com/uk?aff=usedcarsuk", true, 3),

        // Car Finance
        "autotrader_finance" to PartnerUrlConfig("autotrader_finance", "AutoTrader Finance", "https://www.autotrader.co.uk/car-finance", "https://www.autotrader.co.uk/car-finance?aff=usedcarsuk", true, 1),
        "carwow_finance" to PartnerUrlConfig("carwow_finance", "carwow Finance", "https://www.carwow.co.uk/car-finance", "https://www.carwow.co.uk/car-finance?aff=usedcarsuk", true, 2),
        "moneysupermarket_finance" to PartnerUrlConfig("moneysupermarket_finance", "MoneySuperMarket Car Finance", "https://www.moneysupermarket.com/car-finance/", "https://www.moneysupermarket.com/car-finance/?aff=usedcarsuk", true, 3),
        "close_brothers" to PartnerUrlConfig("close_brothers", "Close Brothers Motor Finance", "https://www.closemotorfinance.co.uk", "https://www.closemotorfinance.co.uk?aff=usedcarsuk", true, 4),
        "motonovo" to PartnerUrlConfig("motonovo", "MotoNovo Finance", "https://www.motonovofinance.com", "https://www.motonovofinance.com?aff=usedcarsuk", true, 5),
        "zuto" to PartnerUrlConfig("zuto", "Zuto Car Finance", "https://www.zuto.com", "https://www.zuto.com?aff=usedcarsuk", true, 6),

        // Breakdown Cover
        "theaa_breakdown" to PartnerUrlConfig("theaa_breakdown", "AA Breakdown Cover", "https://www.theaa.com/breakdown-cover", "https://www.theaa.com/breakdown-cover?aff=usedcarsuk", true, 1),
        "rac_breakdown" to PartnerUrlConfig("rac_breakdown", "RAC Breakdown Cover", "https://www.rac.co.uk/breakdown-cover", "https://www.rac.co.uk/breakdown-cover?aff=usedcarsuk", true, 2),
        "greenflag" to PartnerUrlConfig("greenflag", "Green Flag", "https://www.greenflag.com", "https://www.greenflag.com?aff=usedcarsuk", true, 3),
        "britannia" to PartnerUrlConfig("britannia", "Britannia Rescue", "https://www.lv.com/breakdown-cover", "https://www.lv.com/breakdown-cover?aff=usedcarsuk", true, 4),

        // Car Insurance
        "comparethemarket" to PartnerUrlConfig("comparethemarket", "comparethemarket", "https://www.comparethemarket.com/car-insurance", "https://www.comparethemarket.com/car-insurance?aff=usedcarsuk", true, 1),
        "moneysupermarket" to PartnerUrlConfig("moneysupermarket", "MoneySuperMarket", "https://www.moneysupermarket.com/car-insurance", "https://www.moneysupermarket.com/car-insurance?aff=usedcarsuk", true, 2),
        "confused" to PartnerUrlConfig("confused", "Confused.com", "https://www.confused.com/car-insurance", "https://www.confused.com/car-insurance?aff=usedcarsuk", true, 3),
        "gocompare" to PartnerUrlConfig("gocompare", "Go.Compare", "https://www.gocompare.com/car-insurance", "https://www.gocompare.com/car-insurance?aff=usedcarsuk", true, 4),

        // Tyres, Alloys, Accessories & Parts (Amazon UK Affiliate)
        "amazon_tyres" to PartnerUrlConfig("amazon_tyres", "Amazon UK - Tyres & Alloys", "https://www.amazon.co.uk/s?k=car+tyres+and+wheels", "https://www.amazon.co.uk/s?k=car+tyres+and+wheels&tag=usedcarsuk-21", true, 1),
        "amazon_accessories" to PartnerUrlConfig("amazon_accessories", "Amazon UK - Accessories & Parts", "https://www.amazon.co.uk/s?k=car+accessories+and+parts", "https://www.amazon.co.uk/s?k=car+accessories+and+parts&tag=usedcarsuk-21", true, 2)
    )
)

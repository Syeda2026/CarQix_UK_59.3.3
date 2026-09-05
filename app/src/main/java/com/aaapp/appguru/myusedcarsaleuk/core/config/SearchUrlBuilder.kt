package com.aaapp.appguru.myusedcarsaleuk.core.config

import java.net.URLEncoder

object SearchUrlBuilder {
    fun buildSearchUrl(
        partnerId: String,
        make: String,
        model: String,
        maxPrice: Int = 0,
        minYear: Int = 0,
        fuelType: String = "",
        transmission: String = ""
    ): String {
        val cleanMake = if (make == "Any Make") "" else make.trim()
        val cleanModel = if (model == "Any Model") "" else model.trim()
        val cleanFuel = if (fuelType == "Any") "" else fuelType.trim()

        val encodedMake = try { URLEncoder.encode(cleanMake, "UTF-8") } catch (e: Exception) { cleanMake }
        val encodedModel = try { URLEncoder.encode(cleanModel, "UTF-8") } catch (e: Exception) { cleanModel }
        val encodedFuel = try { URLEncoder.encode(cleanFuel, "UTF-8") } catch (e: Exception) { cleanFuel }

        val rawQuery = listOf(cleanMake, cleanModel).filter { it.isNotEmpty() }.joinToString(" ")
        val encodedQuery = try { URLEncoder.encode(rawQuery, "UTF-8") } catch (e: Exception) { rawQuery }

        return when (partnerId.lowercase()) {
            "autotrader" -> {
                val sb = StringBuilder("https://www.autotrader.co.uk/car-search?")
                if (cleanMake.isNotEmpty()) sb.append("make=$encodedMake&")
                if (cleanModel.isNotEmpty()) sb.append("model=$encodedModel&")
                if (maxPrice > 0) sb.append("price-to=$maxPrice&")
                if (minYear > 0) sb.append("year-from=$minYear&")
                if (cleanFuel.isNotEmpty()) sb.append("fuel-type=$encodedFuel&")
                sb.append("postcode=SW1A1AA&sort=relevance")
                sb.toString()
            }
            "gumtree" -> {
                if (encodedQuery.isNotEmpty()) {
                    "https://www.gumtree.com/search?search_category=cars&q=$encodedQuery"
                } else {
                    "https://www.gumtree.com/cars"
                }
            }
            "carwow" -> {
                if (cleanMake.isNotEmpty()) {
                    val makeSlug = cleanMake.lowercase().replace(" ", "-")
                    if (cleanModel.isNotEmpty()) {
                        val modelSlug = cleanModel.lowercase().replace(" ", "-")
                        "https://www.carwow.co.uk/used-cars/$makeSlug/$modelSlug"
                    } else {
                        "https://www.carwow.co.uk/used-cars/$makeSlug"
                    }
                } else if (encodedQuery.isNotEmpty()) {
                    "https://www.carwow.co.uk/used-cars?q=$encodedQuery"
                } else {
                    "https://www.carwow.co.uk/used-cars"
                }
            }
            "arnoldclark" -> {
                if (encodedQuery.isNotEmpty()) {
                    "https://www.arnoldclark.com/used-cars/search?q=$encodedQuery"
                } else {
                    "https://www.arnoldclark.com/used-cars"
                }
            }
            "googlecars", "google" -> {
                val queryStr = if (rawQuery.isNotEmpty()) "$rawQuery used cars for sale uk" else "used cars for sale uk"
                val encodedGoogleQuery = try { URLEncoder.encode(queryStr, "UTF-8") } catch (e: Exception) { queryStr }
                "https://www.google.co.uk/search?q=$encodedGoogleQuery"
            }
            "motorway" -> "https://motorway.co.uk"
            "webuyanycar" -> "https://www.webuyanycar.com"
            else -> {
                val baseUrl = ConfigManager.getResolvedUrl(partnerId)
                if (encodedQuery.isNotEmpty()) {
                    "$baseUrl?q=$encodedQuery"
                } else {
                    baseUrl
                }
            }
        }
    }
}

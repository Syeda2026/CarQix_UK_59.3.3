package com.aaapp.appguru.myusedcarsaleuk.core.utils

import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.regex.Pattern

data class VehicleMetadata(
    val title: String,
    val imageUrl: String,
    val brand: String,
    val makeModel: String
)

object VehicleMetadataResolver {

    // Curated high quality automotive photography for specific makes and body types
    private val makeImages = mapOf(
        "bmw" to listOf(
            "https://images.unsplash.com/photo-1555215695-3004980ad54e?w=700&q=80", // BMW 3 Series / M
            "https://images.unsplash.com/photo-1580273916550-e323be2ae537?w=700&q=80", // BMW Saloon
            "https://images.unsplash.com/photo-1556189250-72ba954cfc2b?w=700&q=80"  // BMW M Sport
        ),
        "audi" to listOf(
            "https://images.unsplash.com/photo-1603584173870-7f23fdae1b7a?w=700&q=80", // Audi A4 / RS
            "https://images.unsplash.com/photo-1606664515524-ed2f786a0bd6?w=700&q=80", // Audi Sportback
            "https://images.unsplash.com/photo-1541348263662-e0c86629c983?w=700&q=80"  // Audi Saloon
        ),
        "mercedes" to listOf(
            "https://images.unsplash.com/photo-1618843479313-40f8afb4b4d8?w=700&q=80", // Mercedes C-Class
            "https://images.unsplash.com/photo-1617814076367-b759c7d7e738?w=700&q=80", // Mercedes AMG
            "https://images.unsplash.com/photo-1563720223185-11003d516935?w=700&q=80"  // Mercedes Luxury
        ),
        "ford" to listOf(
            "https://images.unsplash.com/photo-1551830820-330a71b99659?w=700&q=80", // Ford Fiesta / Focus
            "https://images.unsplash.com/photo-1584345604476-8ec5e12e42dd?w=700&q=80", // Ford Mustang / Sport
            "https://images.unsplash.com/photo-1549399542-7e3f8b79c341?w=700&q=80"  // Ford SUV
        ),
        "volkswagen" to listOf(
            "https://images.unsplash.com/photo-1541899481282-d53bffe3c35d?w=700&q=80", // VW Golf / R
            "https://images.unsplash.com/photo-1503376780353-7e6692767b70?w=700&q=80", // VW Hatch
            "https://images.unsplash.com/photo-1471444668990-396775158883?w=700&q=80"  // VW SUV
        ),
        "vw" to listOf(
            "https://images.unsplash.com/photo-1541899481282-d53bffe3c35d?w=700&q=80",
            "https://images.unsplash.com/photo-1503376780353-7e6692767b70?w=700&q=80"
        ),
        "vauxhall" to listOf(
            "https://images.unsplash.com/photo-1542282088-72c9c27ed0cd?w=700&q=80", // Corsa / Astra Hatch
            "https://images.unsplash.com/photo-1552519507-da3b142c6e3d?w=700&q=80"  // Modern Hatch
        ),
        "toyota" to listOf(
            "https://images.unsplash.com/photo-1621007947382-bb3c3994e3fb?w=700&q=80", // Toyota Yaris / GR
            "https://images.unsplash.com/photo-1590362891988-f778047831d2?w=700&q=80"  // Toyota RAV4
        ),
        "nissan" to listOf(
            "https://images.unsplash.com/photo-1568605117036-5fe5e7bab0b7?w=700&q=80", // Nissan Qashqai
            "https://images.unsplash.com/photo-1617469767053-d3b523a0b982?w=700&q=80"  // Nissan GTR / Sport
        ),
        "land-rover" to listOf(
            "https://images.unsplash.com/photo-1563720223523-491ff04651de?w=700&q=80", // Range Rover Sport
            "https://images.unsplash.com/photo-1541348263662-e0c86629c983?w=700&q=80"  // Defender
        ),
        "range-rover" to listOf(
            "https://images.unsplash.com/photo-1563720223523-491ff04651de?w=700&q=80",
            "https://images.unsplash.com/photo-1541348263662-e0c86629c983?w=700&q=80"
        ),
        "tesla" to listOf(
            "https://images.unsplash.com/photo-1560958089-b8a1929cea89?w=700&q=80", // Tesla Model 3
            "https://images.unsplash.com/photo-1617788138017-80ad40651399?w=700&q=80"  // Tesla Model Y
        ),
        "porsche" to listOf(
            "https://images.unsplash.com/photo-1503376780353-7e6692767b70?w=700&q=80", // 911 / Cayman
            "https://images.unsplash.com/photo-1614162692292-7ac56d7f7f1e?w=700&q=80"  // Taycan
        ),
        "mini" to listOf(
            "https://images.unsplash.com/photo-1583121274602-3e2820c69888?w=700&q=80", // Mini Cooper
            "https://images.unsplash.com/photo-1553440569-bcc63803a83d?w=700&q=80"  // Mini Hatch
        ),
        "hyundai" to listOf(
            "https://images.unsplash.com/photo-1609521263047-f8f205293f24?w=700&q=80", // Tucson / i20
            "https://images.unsplash.com/photo-1583121274602-3e2820c69888?w=700&q=80"
        ),
        "kia" to listOf(
            "https://images.unsplash.com/photo-1619682817481-e994891cd1f5?w=700&q=80", // Sportage / EV6
            "https://images.unsplash.com/photo-1552519507-da3b142c6e3d?w=700&q=80"
        ),
        "honda" to listOf(
            "https://images.unsplash.com/photo-1590362891988-f778047831d2?w=700&q=80", // Civic Type R
            "https://images.unsplash.com/photo-1621007947382-bb3c3994e3fb?w=700&q=80"
        ),
        "volvo" to listOf(
            "https://images.unsplash.com/photo-1617469767053-d3b523a0b982?w=700&q=80", // XC60 / XC90
            "https://images.unsplash.com/photo-1542282088-72c9c27ed0cd?w=700&q=80"
        ),
        "jaguar" to listOf(
            "https://images.unsplash.com/photo-1541348263662-e0c86629c983?w=700&q=80", // F-Pace / XE
            "https://images.unsplash.com/photo-1503376780353-7e6692767b70?w=700&q=80"
        ),
        "peugeot" to listOf(
            "https://images.unsplash.com/photo-1542282088-72c9c27ed0cd?w=700&q=80", // 208 / 3008
            "https://images.unsplash.com/photo-1552519507-da3b142c6e3d?w=700&q=80"
        ),
        "renault" to listOf(
            "https://images.unsplash.com/photo-1503376780353-7e6692767b70?w=700&q=80", // Clio / Megane
            "https://images.unsplash.com/photo-1542282088-72c9c27ed0cd?w=700&q=80"
        ),
        "skoda" to listOf(
            "https://images.unsplash.com/photo-1541899481282-d53bffe3c35d?w=700&q=80", // Octavia / Superb
            "https://images.unsplash.com/photo-1503376780353-7e6692767b70?w=700&q=80"
        ),
        "seat" to listOf(
            "https://images.unsplash.com/photo-1541899481282-d53bffe3c35d?w=700&q=80", // Leon / Ibiza
            "https://images.unsplash.com/photo-1551830820-330a71b99659?w=700&q=80"
        )
    )

    // Distinct diverse gallery for generic or unmatched vehicles
    private val fallbackCarGallery = listOf(
        "https://images.unsplash.com/photo-1552519507-da3b142c6e3d?w=700&q=80", // Sports Coupe
        "https://images.unsplash.com/photo-1555215695-3004980ad54e?w=700&q=80", // Performance Saloon
        "https://images.unsplash.com/photo-1603584173870-7f23fdae1b7a?w=700&q=80", // Premium Hatchback
        "https://images.unsplash.com/photo-1618843479313-40f8afb4b4d8?w=700&q=80", // Luxury Estate
        "https://images.unsplash.com/photo-1563720223523-491ff04651de?w=700&q=80", // Prestige SUV
        "https://images.unsplash.com/photo-1541899481282-d53bffe3c35d?w=700&q=80", // Hot Hatch
        "https://images.unsplash.com/photo-1560958089-b8a1929cea89?w=700&q=80", // Electric EV Saloon
        "https://images.unsplash.com/photo-1583121274602-3e2820c69888?w=700&q=80", // Compact City Car
        "https://images.unsplash.com/photo-1551830820-330a71b99659?w=700&q=80", // Blue Fastback
        "https://images.unsplash.com/photo-1621007947382-bb3c3994e3fb?w=700&q=80"  // Hybrid Crossover
    )

    /**
     * Resolves metadata for any deep link URL.
     * First attempts to extract real OpenGraph image & title from the webpage.
     * Falls back to deep URL slug and parameter analysis with rich automotive matching.
     */
    suspend fun resolve(url: String, fallbackTitle: String? = null): VehicleMetadata = withContext(Dispatchers.IO) {
        val parsedUri = try { Uri.parse(url) } catch (e: Exception) { null }
        val host = parsedUri?.host?.removePrefix("www.")?.lowercase() ?: "uk"

        // Step 1: Detect Marketplace / Brand
        val marketplaceName = when {
            host.contains("autotrader") -> "AutoTrader UK"
            host.contains("gumtree") -> "Gumtree Cars"
            host.contains("carwow") -> "carwow UK"
            host.contains("arnoldclark") -> "Arnold Clark"
            host.contains("cargurus") -> "CarGurus UK"
            host.contains("cinch") -> "Cinch UK"
            host.contains("cazoo") -> "Cazoo UK"
            host.contains("pistonheads") -> "PistonHeads UK"
            host.contains("motors") -> "Motors.co.uk"
            host.contains("ebay") -> "eBay Motors UK"
            host.contains("webuyanycar") -> "webuyanycar UK"
            host.contains("theaa") -> "The AA Cars"
            host.contains("rac") -> "RAC Cars"
            host.contains("gov.uk") -> "GOV.UK MOT History"
            else -> host.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
        }

        // Step 2: Try fetching OpenGraph metadata over network
        var scrapedTitle: String? = null
        var scrapedImage: String? = null

        try {
            val fetched = fetchOpenGraphData(url)
            scrapedTitle = fetched.first
            scrapedImage = fetched.second
        } catch (e: Exception) {
            // Scrape failed (timeout or bot check), proceed to URL semantic parser
        }

        // Step 3: Analyze URL keywords for Make, Model, Year, Body Style
        val urlAnalysis = analyzeUrlKeywords(url)

        // Determine Final Title
        val finalTitle = when {
            !scrapedTitle.isNullOrBlank() -> cleanScrapedTitle(scrapedTitle, marketplaceName)
            !fallbackTitle.isNullOrBlank() && fallbackTitle != "Used Car Listing" && fallbackTitle != "Used Cars UK" -> fallbackTitle
            urlAnalysis.makeModel.isNotBlank() -> {
                val yearPart = if (urlAnalysis.year.isNotBlank()) "${urlAnalysis.year} " else ""
                "$yearPart${urlAnalysis.makeModel} ($marketplaceName)"
            }
            else -> "$marketplaceName Used Car Listing"
        }

        // Determine Final Image
        val finalImage = when {
            !scrapedImage.isNullOrBlank() && (scrapedImage.startsWith("http://") || scrapedImage.startsWith("https://")) -> scrapedImage
            urlAnalysis.matchedMake.isNotBlank() && makeImages.containsKey(urlAnalysis.matchedMake) -> {
                val images = makeImages[urlAnalysis.matchedMake] ?: fallbackCarGallery
                val idx = Math.abs((url + urlAnalysis.matchedMake).hashCode()) % images.size
                images[idx]
            }
            else -> {
                val idx = Math.abs(url.hashCode()) % fallbackCarGallery.size
                fallbackCarGallery[idx]
            }
        }

        VehicleMetadata(
            title = finalTitle,
            imageUrl = finalImage,
            brand = marketplaceName,
            makeModel = urlAnalysis.makeModel
        )
    }

    private fun fetchOpenGraphData(urlString: String): Pair<String?, String?> {
        var connection: HttpURLConnection? = null
        try {
            val url = URL(urlString)
            connection = url.openConnection() as HttpURLConnection
            connection.connectTimeout = 3500
            connection.readTimeout = 3500
            connection.instanceFollowRedirects = true
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Linux; Android 14; Pixel 8) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/125.0.0.0 Mobile Safari/537.36")
            connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
            connection.setRequestProperty("Accept-Language", "en-GB,en-US;q=0.9,en;q=0.8")

            if (connection.responseCode in 200..299) {
                val reader = BufferedReader(InputStreamReader(connection.inputStream))
                val sb = StringBuilder()
                var line: String?
                var linesRead = 0
                // Read the HTML head (up to 150 lines or </head>)
                while (reader.readLine().also { line = it } != null && linesRead < 200) {
                    sb.append(line).append("\n")
                    if (line?.contains("</head>", ignoreCase = true) == true) break
                    linesRead++
                }
                reader.close()

                val html = sb.toString()

                // Extract OpenGraph Image
                val ogImage = extractMetaTag(html, "property", "og:image")
                    ?: extractMetaTag(html, "name", "twitter:image")
                    ?: extractMetaTag(html, "property", "twitter:image")
                    ?: extractMetaTag(html, "name", "og:image")

                // Extract OpenGraph Title
                val ogTitle = extractMetaTag(html, "property", "og:title")
                    ?: extractMetaTag(html, "name", "twitter:title")
                    ?: extractMetaTag(html, "name", "title")
                    ?: extractHtmlTag(html, "title")

                return Pair(ogTitle, ogImage)
            }
        } catch (e: Exception) {
            // Ignored
        } finally {
            connection?.disconnect()
        }
        return Pair(null, null)
    }

    private fun extractMetaTag(html: String, attrName: String, attrValue: String): String? {
        val pattern1 = Pattern.compile("<meta\\s+[^>]*$attrName=[\"']$attrValue[\"'][^>]*content=[\"'](.*?)[\"']", Pattern.CASE_INSENSITIVE)
        val matcher1 = pattern1.matcher(html)
        if (matcher1.find()) {
            return decodeHtml(matcher1.group(1))
        }

        val pattern2 = Pattern.compile("<meta\\s+[^>]*content=[\"'](.*?)[\"'][^>]*$attrName=[\"']$attrValue[\"']", Pattern.CASE_INSENSITIVE)
        val matcher2 = pattern2.matcher(html)
        if (matcher2.find()) {
            return decodeHtml(matcher2.group(1))
        }
        return null
    }

    private fun extractHtmlTag(html: String, tagName: String): String? {
        val pattern = Pattern.compile("<$tagName[^>]*>(.*?)</$tagName>", Pattern.CASE_INSENSITIVE or Pattern.DOTALL)
        val matcher = pattern.matcher(html)
        if (matcher.find()) {
            return decodeHtml(matcher.group(1)?.trim())
        }
        return null
    }

    private fun decodeHtml(input: String?): String? {
        if (input.isNullOrBlank()) return null
        return input
            .replace("&amp;", "&")
            .replace("&quot;", "\"")
            .replace("&#39;", "'")
            .replace("&lt;", "<")
            .replace("&gt;", ">")
            .replace("&#x27;", "'")
            .replace("&#x2F;", "/")
            .trim()
    }

    private fun cleanScrapedTitle(title: String, siteName: String): String {
        var clean = title.trim()
        // Remove trailing " | Auto Trader UK", " - Gumtree", etc.
        val pipeIndex = clean.lastIndexOf('|')
        if (pipeIndex > 10) {
            clean = clean.substring(0, pipeIndex).trim()
        }
        val dashIndex = clean.lastIndexOf(" - ")
        if (dashIndex > 10) {
            clean = clean.substring(0, dashIndex).trim()
        }
        return "$clean ($siteName)"
    }

    private data class UrlAnalysis(
        val matchedMake: String = "",
        val makeModel: String = "",
        val year: String = ""
    )

    private fun analyzeUrlKeywords(url: String): UrlAnalysis {
        val lowerUrl = url.lowercase()
        val decoded = try { Uri.decode(lowerUrl) } catch (e: Exception) { lowerUrl }

        // Check for Year in URL (e.g. 2017 to 2026)
        val yearRegex = Regex("""\b(201[5-9]|202[0-6])\b""")
        val yearMatch = yearRegex.find(decoded)?.value ?: ""

        val makes = listOf(
            "bmw", "audi", "mercedes", "ford", "volkswagen", "vw", "vauxhall",
            "toyota", "nissan", "range-rover", "land-rover", "tesla", "porsche",
            "mini", "hyundai", "kia", "honda", "volvo", "jaguar", "peugeot",
            "renault", "skoda", "seat", "fiat", "mazda", "lexus", "cupra", "alfa-romeo"
        )

        var matchedMake = ""
        for (make in makes) {
            if (decoded.contains("/$make") || decoded.contains("-$make") || decoded.contains("make=$make") || decoded.contains("brand=$make") || decoded.contains(make)) {
                matchedMake = make
                break
            }
        }

        val models = mapOf(
            "bmw" to listOf("3-series", "1-series", "5-series", "x5", "x3", "x1", "m3", "m4", "4-series", "2-series"),
            "audi" to listOf("a3", "a4", "a1", "a6", "q5", "q3", "q7", "tt", "rs3", "rs6"),
            "mercedes" to listOf("a-class", "c-class", "e-class", "gla", "glc", "gle", "amg", "cla"),
            "ford" to listOf("fiesta", "focus", "puma", "kuga", "mustang", "mondeo", "ecosport"),
            "volkswagen" to listOf("golf", "polo", "tiguan", "t-roc", "passat", "arteon", "id.3", "id.4"),
            "vw" to listOf("golf", "polo", "tiguan", "t-roc", "passat"),
            "vauxhall" to listOf("corsa", "astra", "mokka", "grandland", "insignia", "crossland"),
            "nissan" to listOf("qashqai", "juke", "micra", "leaf", "x-trail"),
            "toyota" to listOf("yaris", "corolla", "c-hr", "rav4", "aygo", "prius"),
            "kia" to listOf("sportage", "ceed", "niro", "picanto", "ev6", "stonic"),
            "hyundai" to listOf("tucson", "i10", "i20", "i30", "kona", "ioniq"),
            "mini" to listOf("cooper", "countryman", "clubman", "hatch"),
            "tesla" to listOf("model-3", "model-y", "model-s", "model-x")
        )

        var matchedModel = ""
        if (matchedMake.isNotBlank() && models.containsKey(matchedMake)) {
            for (mod in models[matchedMake] ?: emptyList()) {
                if (decoded.contains(mod)) {
                    matchedModel = mod.replace("-", " ").replaceFirstChar { it.uppercase() }
                    break
                }
            }
        }

        val displayMake = when (matchedMake) {
            "bmw" -> "BMW"
            "vw", "volkswagen" -> "Volkswagen"
            "mercedes" -> "Mercedes-Benz"
            "land-rover" -> "Land Rover"
            "range-rover" -> "Range Rover"
            else -> matchedMake.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
        }

        val makeModel = when {
            displayMake.isNotBlank() && matchedModel.isNotBlank() -> "$displayMake $matchedModel"
            displayMake.isNotBlank() -> displayMake
            else -> ""
        }

        return UrlAnalysis(
            matchedMake = matchedMake,
            makeModel = makeModel,
            year = yearMatch
        )
    }
}

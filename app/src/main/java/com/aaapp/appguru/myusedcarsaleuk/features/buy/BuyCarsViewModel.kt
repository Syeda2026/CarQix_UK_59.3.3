package com.aaapp.appguru.myusedcarsaleuk.features.buy

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.aaapp.appguru.myusedcarsaleuk.core.config.ConfigManager
import com.aaapp.appguru.myusedcarsaleuk.data.local.AppRepository
import com.aaapp.appguru.myusedcarsaleuk.data.local.SavedItemEntity
import com.aaapp.appguru.myusedcarsaleuk.model.AutomotiveDataProvider
import com.aaapp.appguru.myusedcarsaleuk.model.MarketplaceInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class BuyCarsFilterState(
    val make: String = "BMW",
    val model: String = "3 Series",
    val maxPrice: Int = 15000,
    val minYear: Int = 2018,
    val maxYear: Int = 2024,
    val maxMileage: Int = 60000,
    val fuelType: String = "Diesel",
    val transmission: String = "Automatic",
    val bodyType: String = "Any",
    val ulezCompliant: Boolean = true
) {
    val makeModel: String
        get() = if (make == "Any Make") "Any Car" else if (model == "Any Model") make else "$make $model"
}

data class CompareListingItem(
    val id: String,
    val siteName: String,
    val siteKey: String,
    val carTitle: String,
    val year: Int,
    val mileage: Int,
    val price: Int,
    val rating: Double,
    val warranty: String,
    val breakdownCover: String,
    val adminFees: String,
    val dealRating: String,
    val searchUrl: String = ""
)

data class InAppSearchResult(
    val id: String,
    val siteId: String,
    val siteName: String,
    val title: String,
    val price: Int,
    val year: Int,
    val mileage: Int,
    val fuelType: String,
    val transmission: String,
    val sellerType: String,
    val rating: Double,
    val dealTag: String,
    val directUrl: String
)

object CarOptionsData {
    val makes = listOf(
        "Any Make", "BMW", "Audi", "Mercedes-Benz", "Ford", "Volkswagen",
        "Toyota", "Nissan", "Honda", "Hyundai", "Kia", "Land Rover",
        "Volvo", "Vauxhall", "Peugeot", "Tesla"
    )

    fun getModelsForMake(make: String): List<String> {
        return when (make) {
            "BMW" -> listOf("Any Model", "1 Series", "2 Series", "3 Series", "4 Series", "5 Series", "X1", "X3", "X5")
            "Audi" -> listOf("Any Model", "A1", "A3", "A4", "A5", "A6", "Q2", "Q3", "Q5", "Q7")
            "Mercedes-Benz" -> listOf("Any Model", "A-Class", "C-Class", "E-Class", "CLA", "GLA", "GLC")
            "Ford" -> listOf("Any Model", "Fiesta", "Focus", "Kuga", "Puma", "Ecosport", "Mustang")
            "Volkswagen" -> listOf("Any Model", "Golf", "Polo", "Tiguan", "T-Roc", "Passat", "ID.3", "ID.4")
            "Toyota" -> listOf("Any Model", "Yaris", "Corolla", "C-HR", "RAV4", "Aygo", "Prius")
            "Nissan" -> listOf("Any Model", "Qashqai", "Juke", "Micra", "Leaf", "X-Trail")
            "Honda" -> listOf("Any Model", "Civic", "Jazz", "CR-V", "HR-V")
            "Hyundai" -> listOf("Any Model", "Tucson", "i10", "i20", "i30", "Kona", "Ioniq")
            "Kia" -> listOf("Any Model", "Sportage", "Ceed", "Picanto", "Niro", "Stonic")
            "Land Rover" -> listOf("Any Model", "Range Rover Evoque", "Discovery Sport", "Range Rover Velar")
            "Volvo" -> listOf("Any Model", "XC40", "XC60", "XC90", "V40", "S60")
            "Vauxhall" -> listOf("Any Model", "Corsa", "Astra", "Mokka", "Crossland")
            "Peugeot" -> listOf("Any Model", "208", "308", "2008", "3008", "5008")
            "Tesla" -> listOf("Any Model", "Model 3", "Model Y", "Model S", "Model X")
            else -> listOf("Any Model")
        }
    }

    val priceOptions = listOf(3000, 5000, 8000, 10000, 12000, 15000, 20000, 25000, 30000, 40000, 50000, 100000)
    val yearOptions = (2010..2026).toList()
    val mileageOptions = listOf(10000, 20000, 30000, 50000, 70000, 100000, 150000)
    val fuelTypes = listOf("Any", "Petrol", "Diesel", "Hybrid", "Electric")
    val transmissions = listOf("Any", "Automatic", "Manual")
    val bodyTypes = listOf("Any", "Hatchback", "SUV", "Saloon", "Estate", "Coupe", "Convertible")
}

class BuyCarsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = AppRepository.getInstance(application)

    val savedItems = repository.allSavedItems.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    private val _filterState = MutableStateFlow(BuyCarsFilterState())
    val filterState: StateFlow<BuyCarsFilterState> = _filterState.asStateFlow()

    private val _selectedMarketplaces = MutableStateFlow<Set<String>>(
        setOf("autotrader", "gumtree", "carwow", "arnoldclark", "googlecars")
    )
    val selectedMarketplaces: StateFlow<Set<String>> = _selectedMarketplaces.asStateFlow()

    private val _comparisonListings = MutableStateFlow<List<CompareListingItem>>(emptyList())
    val comparisonListings: StateFlow<List<CompareListingItem>> = _comparisonListings.asStateFlow()

    private val _inAppResults = MutableStateFlow<List<InAppSearchResult>>(emptyList())
    val inAppResults: StateFlow<List<InAppSearchResult>> = _inAppResults.asStateFlow()

    val marketplaces: List<MarketplaceInfo> = AutomotiveDataProvider.marketplaces

    init {
        updateAllStates(_filterState.value, _selectedMarketplaces.value)
    }

    fun updateFilters(newFilters: BuyCarsFilterState) {
        _filterState.value = newFilters
        updateAllStates(newFilters, _selectedMarketplaces.value)
    }

    fun toggleMarketplace(id: String) {
        val current = _selectedMarketplaces.value.toMutableSet()
        if (current.contains(id)) {
            if (current.size > 1) current.remove(id)
        } else {
            current.add(id)
        }
        _selectedMarketplaces.value = current
        updateAllStates(_filterState.value, current)
    }

    fun selectAllMarketplaces() {
        val all = marketplaces.map { it.id }.toSet()
        _selectedMarketplaces.value = all
        updateAllStates(_filterState.value, all)
    }

    private fun updateAllStates(filter: BuyCarsFilterState, selectedSites: Set<String>) {
        val carName = filter.makeModel
        val maxP = filter.maxPrice
        val minY = filter.minYear
        val fuel = filter.fuelType
        val trans = filter.transmission

        // 1. Update Comparison Listings
        val compList = mutableListOf<CompareListingItem>()
        if (selectedSites.contains("autotrader")) {
            val url = ConfigManager.getSearchUrl("autotrader", filter.make, filter.model, maxP, minY, fuel, trans)
            compList.add(CompareListingItem("1", "AutoTrader", "autotrader", "$carName M Sport", minY, filter.maxMileage - 10000, (maxP * 0.95).toInt(), 4.8, "3 Months Warranty", "Optional", "£0 Fee", "Great Deal", url))
        }
        if (selectedSites.contains("gumtree")) {
            val url = ConfigManager.getSearchUrl("gumtree", filter.make, filter.model, maxP, minY, fuel, trans)
            compList.add(CompareListingItem("2", "Gumtree Cars", "gumtree", "$carName Local Deal", minY, filter.maxMileage, (maxP * 0.88).toInt(), 4.4, "Private Sale", "Optional", "£0 Fee", "Best Price", url))
        }
        if (selectedSites.contains("carwow")) {
            val url = ConfigManager.getSearchUrl("carwow", filter.make, filter.model, maxP, minY, fuel, trans)
            compList.add(CompareListingItem("3", "carwow", "carwow", "$carName Main Dealer", minY + 1, filter.maxMileage - 12000, (maxP * 0.96).toInt(), 4.7, "12m Main Dealer Warranty", "Included", "£0 Fee", "Verified Stock", url))
        }
        if (selectedSites.contains("arnoldclark")) {
            val url = ConfigManager.getSearchUrl("arnoldclark", filter.make, filter.model, maxP, minY, fuel, trans)
            compList.add(CompareListingItem("4", "Arnold Clark", "arnoldclark", "$carName Retailer Checked", minY, filter.maxMileage - 5000, (maxP * 0.93).toInt(), 4.6, "6m Arnold Clark Warranty", "12m Breakdown", "£0 Fee", "Top Warranty", url))
        }
        if (selectedSites.contains("googlecars")) {
            val url = ConfigManager.getSearchUrl("googlecars", filter.make, filter.model, maxP, minY, fuel, trans)
            compList.add(CompareListingItem("5", "Google Car Search", "googlecars", "$carName All UK Portals", minY, filter.maxMileage - 8000, (maxP * 0.91).toInt(), 4.9, "Portal Aggregated", "Direct Seller", "£0 Fee", "All Listings", url))
        }
        _comparisonListings.value = compList

        // 2. Update Aggregated In-App Search Results
        val resultsList = mutableListOf<InAppSearchResult>()
        marketplaces.filter { selectedSites.contains(it.id) }.forEachIndexed { index, m ->
            val siteUrl = ConfigManager.getSearchUrl(m.id, filter.make, filter.model, maxP, minY, fuel, trans)
            val priceOffset = when (index % 3) {
                0 -> (maxP * 0.94).toInt()
                1 -> (maxP * 0.88).toInt()
                else -> (maxP * 0.97).toInt()
            }
            val specTag = when (index % 3) {
                0 -> "M Sport / High Spec"
                1 -> "SE Tech Edition"
                else -> "Sport Line Auto"
            }
            resultsList.add(
                InAppSearchResult(
                    id = "result_${m.id}_$index",
                    siteId = m.id,
                    siteName = m.name,
                    title = "$carName $specTag",
                    price = priceOffset,
                    year = minY + (index % 2),
                    mileage = filter.maxMileage - (index * 5000),
                    fuelType = if (fuel == "Any") "Petrol/Diesel" else fuel,
                    transmission = if (trans == "Any") "Automatic" else trans,
                    sellerType = if (index % 2 == 0) "Verified Franchise Dealer" else "AA Inspected Dealer",
                    rating = m.rating,
                    dealTag = if (index % 2 == 0) "Great Price" else "Fair Market",
                    directUrl = siteUrl
                )
            )
        }
        _inAppResults.value = resultsList
    }

    fun addCompareListing(item: CompareListingItem) {
        _comparisonListings.value = _comparisonListings.value + item
    }

    fun removeCompareListing(id: String) {
        _comparisonListings.value = _comparisonListings.value.filterNot { it.id == id }
    }

    fun saveCurrentFilterProfile(profileName: String) {
        viewModelScope.launch {
            val filter = _filterState.value
            val item = SavedItemEntity(
                id = "search_profile_${System.currentTimeMillis()}",
                itemType = "SEARCH",
                title = profileName,
                subtitle = "${filter.makeModel} • Max £${filter.maxPrice} • ${filter.fuelType}",
                detailDataJson = "${filter.makeModel}, ${filter.maxPrice}, ${filter.fuelType}"
            )
            repository.saveItem(item)
        }
    }
}

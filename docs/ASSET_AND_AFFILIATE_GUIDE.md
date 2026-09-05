# MyUsedCarSaleUK - Complete Asset Customization & Affiliate Integration Guide

---

## Part 1: How to Update / Replace All Images, Icons & Branding

This section explains step-by-step how to replace every image, icon, hero banner, logo, and launcher asset in your Android application.

---

### 1. App Launcher Icon & Round Icon
* **Location in Project:**
  - `app/src/main/res/drawable/ic_launcher_foreground.xml` (Foreground Vector/Image)
  - `app/src/main/res/drawable/ic_launcher_background.xml` (Background color/gradient)
  - `app/src/main/res/mipmap-anydpi-v26/ic_launcher.xml` (Adaptive Icon config)
  - `app/src/main/res/mipmap-hdpi/`, `mipmap-mdpi/`, `mipmap-xhdpi/`, `mipmap-xxhdpi/`, `mipmap-xxxhdpi/` (Legacy raster icons)
* **How to replace:**
  1. Prepare your logo as a square PNG (1024x1024 px) or SVG vector.
  2. In **Android Studio**, right-click the `app` module -> **New** -> **Image Asset**.
  3. Select **Launcher Icons (Adaptive and Legacy)**.
  4. Browse to your custom logo file for the **Foreground Layer**.
  5. Choose your desired background color or asset in the **Background Layer**.
  6. Click **Next** -> **Finish**. Android Studio will generate all resolution folders (`mipmap-*`) automatically.

---

### 2. Splash Screen & App Logo
* **Location in Project:**
  - `app/src/main/res/drawable/ic_used_cars_logo_car.xml` (Vector logo used in app bars & splash)
  - `app/src/main/res/drawable/splash_background.xml` (Splash background layout)
  - `app/src/main/java/com/aaapp/appguru/myusedcarsaleuk/ui/theme/Type.kt` & `Theme.kt` (Branding colors)
* **How to replace:**
  1. Export your custom logo as a Vector Drawable (`.xml`) or PNG file.
  2. Save it to `app/src/main/res/drawable/` as `ic_used_cars_logo_car.xml` (or your custom name).
  3. If you use a custom PNG image, place high-res versions in `drawable-xxhdpi` or load it using Compose `painterResource(R.drawable.your_logo)`.

---

### 3. Hero Banners & Section Illustrations
* **Location in Project:**
  - `app/src/main/res/drawable/ic_car_hero_bg.xml` (Vector background decorative shape)
  - Composables in `com/aaapp/appguru/myusedcarsaleuk/common/Components.kt`:
    - `HeroBanner`
    - `CompactHeroBanner`
    - `FeatureHeroCard`
* **How to replace with custom photos or vectors:**
  1. Add image files (PNG, WEBP, or SVG converted to Vector XML) into `app/src/main/res/drawable/`.
  2. Replace references in `Components.kt` or individual screens (`HomeScreen.kt`, `BuyCarsScreen.kt`, `SellCarScreen.kt`, etc.) using:
     ```kotlin
     Image(
         painter = painterResource(id = R.drawable.your_custom_hero_image),
         contentDescription = "Hero Image",
         contentScale = ContentScale.Crop,
         modifier = Modifier.fillMaxSize()
     )
     ```
  3. If using network images (e.g. from CDN or Firebase Storage), use Coil's `AsyncImage`:
     ```kotlin
     AsyncImage(
         model = "https://yourdomain.com/images/hero_banner.jpg",
         contentDescription = "Hero Banner",
         modifier = Modifier.fillMaxWidth()
     )
     ```

---

### 4. Card Icons & Material Symbols
* **Location in Project:**
  - Material Icons are imported dynamically in Kotlin composables across `common/` and `features/` using `Icons.Filled.*`, `Icons.Outlined.*`, and `Icons.Default.*`.
* **How to replace with Custom Icons:**
  1. Download SVG icons from [Google Fonts / Material Symbols](https://fonts.google.com/icons) or your designer.
  2. Import into Android Studio as Vector Drawables (`res/drawable/ic_my_custom_icon.xml`).
  3. Replace `Icon(imageVector = Icons.Default.Search, ...)` with:
     ```kotlin
     Icon(
         painter = painterResource(id = R.drawable.ic_my_custom_icon),
         contentDescription = "Search Icon",
         tint = Color.Unspecified // keeps original SVG colors
     )
     ```

---

## Part 2: Affiliate Programs & Marketplaces Guide

This section outlines every marketplace, loan provider, insurer, and breakdown service integrated into the app, along with their respective affiliate networks and registration links.

---

### 1. Buy Cars & Marketplace Partners

| Partner Name | Service Category | Affiliate Network | Direct Sign-Up Link | Config Key in App |
| :--- | :--- | :--- | :--- | :--- |
| **AutoTrader UK** | Used Car Search | Awin / Direct Partner | [AutoTrader Partner Program](https://www.autotrader.co.uk/partners) | `autotrader` |
| **carwow** | New & Used Deals | Impact / Awin | [carwow Affiliate Program](https://www.carwow.co.uk/) | `carwow` |
| **Gumtree Cars** | Classified Ads | Awin (Merchant ID: 3122) | [Gumtree Partner Network](https://www.awin.com/) | `gumtree` |
| **Arnold Clark** | Dealership Group | Direct Dealer Network | [Arnold Clark Contact](https://www.arnoldclark.com/) | `arnoldclark` |
| **AA Cars** | Inspected Used Cars | Awin / AA Partner | [AA Affiliate Hub](https://www.theaa.com/) | `aacars` |
| **eBay Motors UK** | Vehicle Auctions | eBay Partner Network (EPN) | [eBay Partner Network](https://partnernetwork.ebay.com/) | `ebaymotors` |
| **PistonHeads** | Specialist & Performance | Direct / CarGurus Network | [PistonHeads Advertising](https://www.pistonheads.com/) | `pistonheads` |
| **Parkers Cars** | Buying & Reviews | Bauer Media / Awin | [Bauer Media Affiliate](https://www.awin.com/) | `parkers` |
| **RAC Cars** | Trusted Used Vehicles | Awin / RAC Network | [RAC Affiliate Program](https://www.rac.co.uk/) | `raccars` |
| **Motorpoint** | Car Supermarket | Awin / Direct | [Motorpoint Affiliates](https://www.motorpoint.co.uk/) | `motorpoint` |
| **Vertu Motors** | Franchised Dealer | Direct / CJ Affiliate | [Vertu Motors Partner](https://www.vertumotors.com/) | `vertumotors` |
| **Evans Halshaw** | Pendragon Group | Direct / Awin | [Evans Halshaw Partner](https://www.evanshalshaw.com/) | `evanshalshaw` |

---

### 2. Sell My Car & Valuation Services

| Partner Name | Service Category | Affiliate Network | Direct Sign-Up Link | Config Key in App |
| :--- | :--- | :--- | :--- | :--- |
| **Motorway** | Online Dealer Bidding | Awin / Impact | [Motorway Affiliate Program](https://motorway.co.uk/) | `motorway` |
| **webuyanycar** | Instant Car Buying | Awin (Merchant ID: 2263) | [webuyanycar Publisher Network](https://www.awin.com/) | `webuyanycar` |
| **AutoTrader Sell** | Instant Offer / Private | Awin | [AutoTrader Publisher Hub](https://www.autotrader.co.uk/sell-my-car) | `autotrader_sell` |
| **carwow Sell** | Dealer Auction | Impact | [carwow Partners](https://www.carwow.co.uk/sell-my-car) | `carwow_sell` |
| **AutoTrader Valuation** | Free Market Valuation | Awin | [AutoTrader Valuation](https://www.autotrader.co.uk/car-valuation) | `autotrader_valuation` |

---

### 3. Vehicle History & MOT Checks

| Partner Name | Service Category | Affiliate Network | Direct Sign-Up Link | Config Key in App |
| :--- | :--- | :--- | :--- | :--- |
| **HPI Check** | Outstanding Finance & Stolen Check | Awin / Cap HPI | [HPI Check Affiliate Program](https://www.hpicheck.com/) | `hpicheck` |
| **carVertical** | Global Vin History & Damage | carVertical Partners / Impact | [carVertical Partner Program](https://www.carvertical.com/uk/affiliate) | `carvertical` |
| **GOV.UK MOT Check** | Free Official MOT Data | Public Service (Free / Non-Affiliate) | [GOV.UK MOT Portal](https://www.gov.uk/check-mot-history) | `gov_mot` |

---

### 4. Car Finance & Loan Providers

| Partner Name | Service Category | Affiliate Network | Direct Sign-Up Link | Config Key in App |
| :--- | :--- | :--- | :--- | :--- |
| **Zuto Car Finance** | Broker & Comparison | Awin (Merchant ID: 3591) | [Zuto Affiliate Program](https://www.awin.com/) | `zuto` |
| **MoneySuperMarket Finance**| Loan Comparison | Awin / Finance Ads | [MoneySuperMarket Affiliate](https://www.moneysupermarket.com/) | `moneysupermarket_finance` |
| **AutoTrader Finance** | Dealer PCP/HP Deals | Awin | [AutoTrader Finance Hub](https://www.autotrader.co.uk/car-finance) | `autotrader_finance` |
| **MotoNovo Finance** | Point of Sale Finance | Direct Dealer Portal | [MotoNovo Dealer Network](https://www.motonovofinance.com/) | `motonovo` |
| **Close Brothers** | Specialist Auto Finance | Direct Partner | [Close Brothers Motor Finance](https://www.closemotorfinance.co.uk/) | `close_brothers` |

---

### 5. Breakdown Cover & Roadside Assistance

| Partner Name | Service Category | Affiliate Network | Direct Sign-Up Link | Config Key in App |
| :--- | :--- | :--- | :--- | :--- |
| **AA Breakdown Cover** | Roadside Recovery | Awin (Merchant ID: 1121) | [AA Affiliate Program](https://www.awin.com/) | `theaa_breakdown` |
| **RAC Breakdown Cover** | Roadside & At Home | Awin (Merchant ID: 1240) | [RAC Publisher Network](https://www.awin.com/) | `rac_breakdown` |
| **Green Flag** | Rescue & Recovery | Awin (Merchant ID: 1968) | [Green Flag Partner Hub](https://www.awin.com/) | `greenflag` |
| **Britannia Rescue (LV=)**| Roadside Cover | Awin (Merchant ID: 1533) | [LV= Britannia Rescue Affiliate](https://www.lv.com/) | `britannia` |

---

### 6. Car Insurance Comparison

| Partner Name | Service Category | Affiliate Network | Direct Sign-Up Link | Config Key in App |
| :--- | :--- | :--- | :--- | :--- |
| **Compare the Market** | Price Comparison | Direct / Awin | [Compare the Market Partners](https://www.comparethemarket.com/) | `comparethemarket` |
| **MoneySuperMarket** | Insurance Aggregator | Awin / Finance Ads | [MoneySuperMarket Affiliate](https://www.moneysupermarket.com/) | `moneysupermarket` |
| **Confused.com** | Quote Comparison | Awin (Merchant ID: 1852) | [Confused.com Affiliate Hub](https://www.awin.com/) | `confused` |
| **Go.Compare** | Insurance Comparison | CJ Affiliate / Awin | [Go.Compare Partner Network](https://www.gocompare.com/) | `gocompare` |

---

### 7. Tyres, Parts & Car Accessories

| Partner Name | Service Category | Affiliate Network | Direct Sign-Up Link | Config Key in App |
| :--- | :--- | :--- | :--- | :--- |
| **Amazon UK Tyres** | Wheels & Replacement Tyres | Amazon Associates UK | [Amazon Associates UK Portal](https://affiliate-program.amazon.co.uk/) | `amazon_tyres` |
| **Amazon UK Accessories** | Car Care & Gadgets | Amazon Associates UK | [Amazon Associates UK Portal](https://affiliate-program.amazon.co.uk/) | `amazon_accessories` |

---

## Part 3: How to Update Your Affiliate Tracking Links in the App

All affiliate links are centrally managed in `AffiliateConfig.kt` and can also be dynamically overridden via Firebase Remote Config without rebuilding the app!

### Method A: Local Code Configuration
Open `/app/src/main/java/com/aaapp/appguru/myusedcarsaleuk/core/config/AffiliateConfig.kt` and replace the `affiliateUrl` field with your approved affiliate tracking link:

```kotlin
"autotrader" to PartnerUrlConfig(
    partnerId = "autotrader",
    name = "AutoTrader",
    publicUrl = "https://www.autotrader.co.uk/car-search",
    affiliateUrl = "https://www.awin1.com/cread.php?awinmid=YOUR_MID&awinaffid=YOUR_AFFILIATE_ID&p=https://www.autotrader.co.uk/car-search",
    isEnabled = true,
    priority = 1
)
```

### Method B: Firebase Remote Config (Instant Live Updates)
If Remote Config is enabled, publish a JSON string parameter named `affiliate_config` in your Firebase Console containing your updated mapping. The app will fetch the updated links dynamically!

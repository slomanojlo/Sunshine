package rs.sloman.sunshine.util

import java.util.*

class MetricUtil {

    companion object {

        fun Locale.unit(): String {
            return when (country.toUpperCase(this)) {
                "US", "LR", "MM" -> Constants.IMPERIAL
                else -> Constants.METRIC
            }
        }

        fun Locale.symbol() : String{

            return when (this.unit()){
                Constants.IMPERIAL -> "°F"
                else -> "°C"
            }

        }

    }

}
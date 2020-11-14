package rs.sloman.sunshine.viewmodels

import rs.sloman.sunshine.Constants
import java.util.*

class MetricUtil {

    companion object {

        fun Locale.unit(): String {
            return when (country.toUpperCase(this)) {
                "US", "LR", "MM" -> Constants.IMPERIAL
                else -> Constants.METRIC
            }
        }

    }

}
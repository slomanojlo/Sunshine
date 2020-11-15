package rs.sloman.sunshine.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Favorite (
    @PrimaryKey
    val city : String)
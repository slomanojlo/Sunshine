package rs.sloman.sunshine.db

import androidx.room.*
import rs.sloman.sunshine.model.Favorite

@Dao
interface FavoriteDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavorite(favorite: Favorite)

    @Delete()
    suspend fun removeFavorite(favorite: Favorite)

    @Query("SELECT * from favorite where city =:city")
    suspend fun findFavoriteCity(city: String) : Favorite
}
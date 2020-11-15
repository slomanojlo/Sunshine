package rs.sloman.sunshine.db

import androidx.lifecycle.LiveData
import androidx.room.*
import rs.sloman.sunshine.model.Favorite

@Dao
interface FavoriteDAO {

    @Query("SELECT * from favorite")
    fun getAllFavorites() : LiveData<List<Favorite>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavorite(favorite: Favorite)

    @Delete()
    suspend fun removeFavorite(favorite: Favorite)

    @Query("SELECT * from favorite where city =:city")
    suspend fun findFavoriteCity(city: String) : Favorite
}
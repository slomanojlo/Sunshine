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

    @Query("UPDATE favorite set isFavorite = 0 where isFavorite = 1")
    suspend fun removeFavoriteCity()

    @Query("UPDATE favorite set isFavorite = 1 where city = :city")
    suspend fun setFavoriteCity(city: String)

    @Query("SELECT * from favorite where isFavorite = 1 LIMIT 1")
    fun getFavoriteCity() : LiveData<Favorite?>


    @Transaction
    suspend fun updateFavoriteCity(favorite: Favorite){
        removeFavoriteCity()
        setFavoriteCity(favorite.city)
    }



}
package rs.sloman.sunshine.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import rs.sloman.sunshine.model.Favorite

@Dao
interface FavoriteDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavorite(favorite: Favorite)
}
package rs.sloman.sunshine.db

import androidx.room.Database
import androidx.room.RoomDatabase
import rs.sloman.sunshine.model.Favorite

@Database (entities = [Favorite::class], version = 1, exportSchema = false)
abstract class FavoriteDB : RoomDatabase () {

    abstract fun getFavoriteDao() : FavoriteDAO

}
package com.cis4030.pokedex.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PokemonDao {
    @Query("select * from databasepokemon")
    fun getPokemon():LiveData<List<DatabasePokemon>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(pokemon:List<DatabasePokemon>)
}

@Database(entities = [DatabasePokemon::class], version = 1)
@TypeConverters(ListTypeConverters::class)
abstract class PokemonDatabase:RoomDatabase(){
    abstract val pokemonDao:PokemonDao
}

private lateinit var INSTANCE:PokemonDatabase

/**
 * This function gets the database instance. It creates it if it does not exist.
 */
fun getDatabase(context: Context):PokemonDatabase{
    synchronized(PokemonDatabase::class.java){
        if(!::INSTANCE.isInitialized){
            INSTANCE=Room.databaseBuilder(context.applicationContext,
            PokemonDatabase::class.java,
            "pokemon").build()
        }
    }
    return INSTANCE
}
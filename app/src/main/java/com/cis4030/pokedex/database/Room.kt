package com.cis4030.pokedex.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * These Data access object interfaces are the APIs we use to interact with the Room database.
 * They are implemented automatically by Room using the annotations and arguments supplied.
 */

@Dao
interface PokemonDao {
    @Query("select * from databasepokemon order by id asc")
    fun getPokemon():LiveData<List<DatabasePokemon>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pokemon:List<DatabasePokemon>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOne(pokemon:DatabasePokemon)
}

@Dao
interface AbilityDao {
    @Query("select * from databaseability")
    fun getAbilities():LiveData<List<DatabaseAbility>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(ability: List<DatabaseAbility>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOne(ability: DatabaseAbility)
}

@Dao
interface MoveDao {
    @Query("select * from databasemove")
    fun getMoves():LiveData<List<DatabaseMove>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(move: List<DatabaseMove>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOne(move: DatabaseMove)
}

@Dao
interface TypeDao {
    @Query("select * from databasetype")
    fun getTypes():LiveData<List<DatabaseType>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(type: List<DatabaseType>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOne(type: DatabaseType)
}

/**
 * This is the database class. Here we define the data types to store in the database, as well as references to the Daos.
 */
@Database(entities = [DatabasePokemon::class, DatabaseAbility::class, DatabaseMove::class, DatabaseType::class], version = 11)
@TypeConverters(ListTypeConverters::class)
abstract class PokemonDatabase:RoomDatabase(){
    abstract val pokemonDao:PokemonDao
    abstract val abilityDao: AbilityDao
    abstract val moveDao: MoveDao
    abstract val typeDao: TypeDao
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
            "pokemon").fallbackToDestructiveMigration().build()
        }
    }
    return INSTANCE
}
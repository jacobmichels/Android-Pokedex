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
    @Query("select * from databasepokemon where custom order by id asc")
    fun getCustomPokemon():LiveData<List<DatabasePokemon>>

    @Query("select * from databasepokemon where not custom order by id asc")
    fun getPokemonByIdAsc():LiveData<List<DatabasePokemon>>

    @Query("select * from databasepokemon where name like :query and not custom order by id asc")
    fun getPokemonByIdAsc(query: String):LiveData<List<DatabasePokemon>>

    @Query("select * from databasepokemon where not custom order by id desc")
    fun getPokemonByIdDsc():LiveData<List<DatabasePokemon>>

    @Query("select * from databasepokemon where name like :query and not custom order by id desc")
    fun getPokemonByIdDsc(query: String):LiveData<List<DatabasePokemon>>

    @Query("select * from databasepokemon where generation in (:generationFilters) and type1 in (:typeFilters) and not custom order by id asc")
    fun getPokemonByIdAsc(generationFilters:List<Int>, typeFilters: List<String>):LiveData<List<DatabasePokemon>>

    @Query("select * from databasepokemon where name like :query and generation in (:generationFilters) and type1 in (:typeFilters) and not custom order by id asc")
    fun getPokemonByIdAsc(query: String, generationFilters:List<Int>, typeFilters: List<String>):LiveData<List<DatabasePokemon>>

    @Query("select * from databasepokemon where generation in (:generationFilters) and type1 in (:typeFilters) and not custom order by id desc")
    fun getPokemonByIdDsc(generationFilters:List<Int>, typeFilters: List<String>):LiveData<List<DatabasePokemon>>

    @Query("select * from databasepokemon where name like :query and generation in (:generationFilters) and type1 in (:typeFilters) and not custom order by id desc")
    fun getPokemonByIdDsc(query: String,generationFilters:List<Int>, typeFilters: List<String>):LiveData<List<DatabasePokemon>>

    @Query("select * from databasepokemon where type1 in (:typeFilters) and not custom order by id asc")
    fun getPokemonByIdAsc(typeFilters: List<String>):LiveData<List<DatabasePokemon>>

    @Query("select * from databasepokemon where name like :query and type1 in (:typeFilters) and not custom order by id asc")
    fun getPokemonByIdAsc(query:String, typeFilters: List<String>):LiveData<List<DatabasePokemon>>

    @Query("select * from databasepokemon where type1 in (:typeFilters) and not custom order by id desc")
    fun getPokemonByIdDsc(typeFilters: List<String>):LiveData<List<DatabasePokemon>>

    @Query("select * from databasepokemon where name like :query and type1 in (:typeFilters) and not custom order by id desc")
    fun getPokemonByIdDsc(query: String,typeFilters: List<String>):LiveData<List<DatabasePokemon>>

    @Query("select * from databasepokemon where generation in (:generationFilters) and not custom order by id asc")
    fun getPokemonByIdAscGenerationFilter(generationFilters:List<Int>):LiveData<List<DatabasePokemon>>

    @Query("select * from databasepokemon where name like :query and generation in (:generationFilters) and not custom order by id asc")
    fun getPokemonByIdAscGenerationFilter(query: String, generationFilters:List<Int>):LiveData<List<DatabasePokemon>>

    @Query("select * from databasepokemon where generation in (:generationFilters) and not custom order by id desc")
    fun getPokemonByIdDscGenerationFilter(generationFilters:List<Int>):LiveData<List<DatabasePokemon>>

    @Query("select * from databasepokemon where name like :query and generation in (:generationFilters) and not custom order by id desc")
    fun getPokemonByIdDscGenerationFilter(query: String,generationFilters:List<Int>):LiveData<List<DatabasePokemon>>

    @Query("select * from databasepokemon where not custom order by name asc")
    fun getPokemonByName():LiveData<List<DatabasePokemon>>

    @Query("select * from databasepokemon where name like :query and not custom order by name asc")
    fun getPokemonByName(query: String):LiveData<List<DatabasePokemon>>

    @Query("select * from databasepokemon where not custom order by name desc")
    fun getPokemonByNameDsc():LiveData<List<DatabasePokemon>>

    @Query("select * from databasepokemon where name like :query and not custom order by name desc")
    fun getPokemonByNameDsc(query: String):LiveData<List<DatabasePokemon>>

    @Query("select * from databasepokemon where generation in (:generationFilters) and type1 in (:typeFilters) and not custom order by name asc")
    fun getPokemonByName(generationFilters:List<Int>, typeFilters: List<String>):LiveData<List<DatabasePokemon>>

    @Query("select * from databasepokemon where name like :query and generation in (:generationFilters) and type1 in (:typeFilters) and not custom order by name asc")
    fun getPokemonByName(query: String, generationFilters: List<Int>, typeFilters: List<String>):LiveData<List<DatabasePokemon>>

    @Query("select * from databasepokemon where generation in (:generationFilters) and type1 in (:typeFilters) and not custom order by name desc")
    fun getPokemonByNameDsc(generationFilters:List<Int>, typeFilters: List<String>):LiveData<List<DatabasePokemon>>

    @Query("select * from databasepokemon where name like :query and generation in (:generationFilters) and type1 in (:typeFilters) and not custom order by name desc")
    fun getPokemonByNameDsc(query: String, generationFilters:List<Int>, typeFilters: List<String>):LiveData<List<DatabasePokemon>>

    @Query("select * from databasepokemon where type1 in (:typeFilters) and not custom order by name asc")
    fun getPokemonByName(typeFilters: List<String>):LiveData<List<DatabasePokemon>>

    @Query("select * from databasepokemon where name like :query and type1 in (:typeFilters) and not custom order by name asc")
    fun getPokemonByName(query: String, typeFilters: List<String>):LiveData<List<DatabasePokemon>>

    @Query("select * from databasepokemon where type1 in (:typeFilters) and not custom order by name desc")
    fun getPokemonByNameDsc(typeFilters: List<String>):LiveData<List<DatabasePokemon>>

    @Query("select * from databasepokemon where name like :query and type1 in (:typeFilters) and not custom order by name desc")
    fun getPokemonByNameDsc(query: String, typeFilters: List<String>):LiveData<List<DatabasePokemon>>

    @Query("select * from databasepokemon where generation in (:generationFilters) and not custom order by name asc")
    fun getPokemonByNameGenerationFilter(generationFilters:List<Int>):LiveData<List<DatabasePokemon>>

    @Query("select * from databasepokemon where name like :query and generation in (:generationFilters) and not custom order by name asc")
    fun getPokemonByNameGenerationFilter(query: String, generationFilters:List<Int>):LiveData<List<DatabasePokemon>>

    @Query("select * from databasepokemon where generation in (:generationFilters) and not custom order by name desc")
    fun getPokemonByNameDscGenerationFilter(generationFilters:List<Int>):LiveData<List<DatabasePokemon>>

    @Query("select * from databasepokemon where name like :query and generation in (:generationFilters) and not custom order by name desc")
    fun getPokemonByNameDscGenerationFilter(query: String, generationFilters:List<Int>):LiveData<List<DatabasePokemon>>

//    @Query("select * from databasepokemon where generation in (:generationFilters) and type1 in (:typeFilters) order by id desc")
//    fun getPokemonByType():LiveData<List<DatabasePokemon>>
//
//    @Query("select * from databasepokemon where generation in (:generationFilters) and type1 in (:typeFilters) order by types desc")
//    fun getPokemonByTypeDsc():LiveData<List<DatabasePokemon>>

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

@Dao
interface CustomPokemonDao {
    @Query("select * from databasecustompokemon")
    fun getPokemon():LiveData<List<DatabaseCustomPokemon>>

    @Query("select * from databasecustompokemon")
    suspend fun getPokemonList():List<DatabaseCustomPokemon>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertOne(pokemon: DatabaseCustomPokemon)
}

@Dao
interface CustomMoveDao {
    @Query("select * from databasecustommove")
    fun getMoves():List<DatabaseCustomMove>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(moves: List<DatabaseCustomMove>)
}

@Dao
interface TeamDao{
    @Query("select * from databaseteam")
    fun getTeams():LiveData<List<DatabaseTeam>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOne(team: DatabaseTeam)
}

/**
 * This is the database class. Here we define the data types to store in the database, as well as references to the Daos.
 */
@Database(entities = [DatabasePokemon::class, DatabaseAbility::class, DatabaseMove::class, DatabaseType::class, DatabaseCustomPokemon::class,DatabaseCustomMove::class, DatabaseTeam::class], version = 15)
@TypeConverters(ListTypeConverters::class)
abstract class PokemonDatabase:RoomDatabase(){
    abstract val pokemonDao:PokemonDao
    abstract val abilityDao: AbilityDao
    abstract val moveDao: MoveDao
    abstract val typeDao: TypeDao
    abstract val customPokemonDao: CustomPokemonDao
    abstract val customMoveDao: CustomMoveDao
    abstract val teamDao: TeamDao
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
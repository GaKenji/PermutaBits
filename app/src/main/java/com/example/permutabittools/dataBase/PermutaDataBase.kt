package com.example.permutabittools.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ConversoesDataBase::class], version = 1)
abstract class PermutaDataBase: RoomDatabase() {
    abstract fun conversaoDao(): ConversoesDAO
    companion object{
        //Permite com que o banco de dados não seja instanciado várias vezes

        @Volatile
        private var instancia: PermutaDataBase? = null

        fun getDataBase(context: Context): PermutaDataBase{

            return instancia ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context, PermutaDataBase::class.java, "permutaDataBase"
                ).build()

                instancia = instance
                instance
            }
        }
    }

}
package com.example.permutabittools.dataBase

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ConversoesDataBase::class], version = 1)
abstract class PermutaDataBase: RoomDatabase() {
    abstract fun conversaoDao(): ConversoesDAO

}
package com.example.planner

/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.planner.EventProperty
import com.example.planner.TypeEvent

/**
 * Defines methods for using the SleepNight class with Room.
 */
@Dao
interface EventDatabaseDAO {

    @Insert
    suspend fun insert(night: EventProperty)

    @Update
    suspend fun update(night:EventProperty)

    @Query("SELECT * FROM event_table WHERE month = :month AND year = :year ORDER BY day ASC")
    suspend fun getByMonth(month: Int , year : Int): List<EventProperty>

    @Query("SELECT * FROM event_table WHERE month = :month AND year = :year AND category = :cat ORDER BY day ASC")
    suspend fun getByCat(month: Int , year : Int,cat : String): List<EventProperty>

    @Query("SELECT * FROM event_table WHERE id = :id")
    suspend fun getById(id: Int): EventProperty

    @Query("DELETE FROM event_table WHERE month = :month AND year = :year")
    suspend fun clearMonth(month : Int , year : Int)

    @Query("DELETE FROM event_table WHERE id = :id")
    fun deleteEvent(id : Int)

    @Query("DELETE FROM event_table")
    suspend fun clearAll()

    @Query("DELETE FROM event_table WHERE category =:cat")
    suspend fun clearCat(cat:String)
}

@Dao
interface CatDatabaseDAO {

    @Insert
    suspend fun insert(cat: Category)

    @Query("SELECT * FROM cat_table")
    suspend fun getAll() : List<Category>

    @Query("SELECT * FROM cat_table WHERE cat=:cat")
    suspend fun checkExist(cat:String) : Category?

    @Query("DELETE FROM cat_table WHERE cat = :cat")
    suspend fun delete(cat:String)

}


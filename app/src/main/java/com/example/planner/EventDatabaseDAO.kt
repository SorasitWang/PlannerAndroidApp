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
    suspend fun update(night: EventProperty)

    @Query("SELECT * FROM event_table WHERE month = :month AND year = :year ORDER BY day ASC")
    fun getByMonth(month: Int , year : Int): LiveData<List<EventProperty>>

    @Query("SELECT * FROM event_table WHERE month = :month AND year = :year AND category = :cat ORDER BY day ASC")
    fun getByCat(month: Int , year : Int,cat : String): LiveData<List<EventProperty>>

    @Query("DELETE FROM event_table WHERE month = :month AND year = :year")
    suspend fun clearMonth(month : Int , year : Int)

    @Query("DELETE FROM event_table WHERE id = :id")
    fun deleteEvent(id : Int)


}

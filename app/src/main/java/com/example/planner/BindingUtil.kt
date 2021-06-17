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

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import org.w3c.dom.Text


@BindingAdapter("typeImage")
fun ImageView.setEventImage(item: EventProperty) {
    setImageResource(when (item.type) {
        0 -> R.drawable.info
        1 -> R.drawable.warning
        else -> R.drawable.emergency
    })
}

@BindingAdapter("deadlineFormatted")
fun TextView.setDeadlineFormatted(item: EventProperty?) {
    item?.let {
        text = "${item.day} /  ${item.month} / ${item.year}"
    }
}

@BindingAdapter("toString")
fun TextView.intToString(data : Int){
    text = data.toString()
}


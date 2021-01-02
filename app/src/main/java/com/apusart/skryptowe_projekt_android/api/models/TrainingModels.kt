package com.apusart.skryptowe_projekt_android.api.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


data class Training(
    val training_id: Int,
    val name: String,
    val about: String,
    val is_public: Boolean,
    val training_type: String,
    val created_by: Int,
    val series: List<Series>
)

data class Series(
    val series_id: Int,
    val iteration: Int,
    val rest_time: Int,
    val training_id: Int,
    val exercises: List<Exercise>
)

data class Exercise(
    val exercise_id: Int,
    val name: String,
    val about: String,
    val number: Int,
    val yt_link: String,
    val photo: String?,
    val exercise_type: String,
    val exercise_calories: Int,
    val series_id: Int
)

data class TrainingForList (
    val training_id: Int,
    val created_by: User,
    val name: String,
    val about: String,
    val is_public: String,
    val training_calories: Int,
    val training_type: String
)

@Parcelize
data class TrainingSafeArg(
    val training_id: Int,
    val creator_nick: String,
    val name: String
): Parcelable

@Parcelize
data class ExerciseSafeArg(
    val name: String,
    val about: String,
    val yt_link: String,
    val photo: String?,
    val exercise_type: String,
): Parcelable

data class SeriesAddRequest(
    val iteration: Int,
    val rest_time: Int,
    val exercises: List<ExerciseAddRequest>
)

data class ExerciseAddRequest(
    val name: String,
    val about: String,
    val number: Int,
    val yt_link: String?,
    val photo: String?,
    val exercise_type: String,
    val exercise_calories: Int
)
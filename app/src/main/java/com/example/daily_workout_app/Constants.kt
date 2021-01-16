package com.example.daily_workout_app

/**
 * Constants class : This class contains the information and properties of
 * each exercises.
 *
 * It has a companion object that is used to return the  list of
 * Exercises in the form of ArrayList ie ArrayList<ExerciseModel>.
 *
 */

class Constants{
    //companion object concept is similar to static variables in java
    companion object{
        fun defaultExerciseList() : ArrayList<ExerciseModel>{
            val exerciseList = ArrayList<ExerciseModel>()

            val jumpingJacks = ExerciseModel(
                    1,
                    "Jumping Jacks",
                    R.drawable.ic_jumping_jacks,
                    false,
                    false
            )
            exerciseList.add(jumpingJacks)


            val abdominalCrunch = ExerciseModel(
                    2,
                    "Abdominal Crunch",
                    R.drawable.ic_abdominal_crunch ,
                    false,
                    false
            )
            exerciseList.add(abdominalCrunch)


            val  highKneesRunningInPlace= ExerciseModel(
                    3,
                    "High Knees Running in Place",
                    R.drawable.ic_high_knees_running_in_place ,
                    false,
                    false
            )
            exerciseList.add(highKneesRunningInPlace)

            val  lunge = ExerciseModel(
                    4,
                    "Lunge",
                    R.drawable.ic_lunge ,
                    false,
                    false
            )
            exerciseList.add(lunge)

            val  pushUp = ExerciseModel(
                    5,
                    "Push Up",
                    R.drawable.ic_push_up ,
                    false,
                    false
            )
            exerciseList.add(pushUp)

            val  pushUpAndRotation= ExerciseModel(
                    6,
                    "Push Up and Rotation",
                    R.drawable.ic_push_up_and_rotation ,
                    false,
                    false
            )
            exerciseList.add(pushUpAndRotation)

            val  plank = ExerciseModel(
                    7,
                    "Plank",
                    R.drawable.ic_plank,
                    false,
                    false
            )
            exerciseList.add(plank)

            val  sidePlank = ExerciseModel(
                    8,
                    "Side Plank",
                    R.drawable.ic_side_plank ,
                    false,
                    false
            )
            exerciseList.add(sidePlank)


            val  squat = ExerciseModel(
                    9,
                    "Squat",
                    R.drawable.ic_squat ,
                    false,
                    false
            )
            exerciseList.add(squat)

            val  wallSit = ExerciseModel(
                    10,
                    "Wall Sit",
                    R.drawable.ic_wall_sit ,
                    false,
                    false
            )
            exerciseList.add(wallSit)

            val tricepsDipOnChair = ExerciseModel(
                    11,
                    "Triceps Dip on Chair",
                    R.drawable.ic_triceps_dip_on_chair ,
                    false,
                    false
            )
            exerciseList.add(tricepsDipOnChair)


            val  stepUpOnChair = ExerciseModel(
                    12,
                    "Step Up on Chair",
                    R.drawable.ic_step_up_onto_chair ,
                    false,
                    false
            )
            exerciseList.add(stepUpOnChair)

            return exerciseList
        }
    }
}
package com.example.daily_workout_app

/**
 * ExerciseModel is a class which contains the properties of a exercise
 * like
 * id : Exercise id,
 * name : Name of exercise,
 * Image :  image of exercise
 * isCompleted : completed status of the exercise
 * isSelected : Exercise is selected or not.
 *
 * This class has getters and setters for each exercise property.
 */


class ExerciseModel(
    private var id: Int,
    private var name: String,
    private var image: Int,
    private var isCompleted: Boolean,
    private var isSelected: Boolean){

    fun getId(): Int{
        return id
    }

    fun setId(id: Int){
        this.id = id
    }

    fun getName(): String{
        return name
    }

    fun setName(name: String){
        this.name = name
    }

    fun getImage() : Int{
        return image
    }

    fun setImage(image: Int){
        this.image = image
    }

    fun getIsCompleted(): Boolean{
        return isCompleted
    }

    fun setIsCompleted(isCompleted: Boolean){
        this.isCompleted = isCompleted
    }

    fun getIsSelected(): Boolean{
        return isSelected
    }

    fun setIsSelected(isSelected: Boolean){
        this.isSelected = isSelected
    }
}
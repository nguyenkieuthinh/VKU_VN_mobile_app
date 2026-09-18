package com.example.woof

object DogsRepository {
    fun getDogs(): List<Dog> {
        return listOf(
            Dog(R.drawable.img_bella, R.string.dog_name_1, 3, R.string.dog_hobbies_1),
            Dog(R.drawable.img_rocky, R.string.dog_name_2, 5, R.string.dog_hobbies_2),
            Dog(R.drawable.img_tinkerbell, R.string.dog_name_3, 2, R.string.dog_hobbies_3),
            Dog(R.drawable.img_fido, R.string.dog_name_4, 4, R.string.dog_hobbies_4),
            Dog(R.drawable.img_lulu, R.string.dog_name_5, 8, R.string.dog_hobbies_5),
            Dog(R.drawable.img_rex, R.string.dog_name_6, 1, R.string.dog_hobbies_6)
        )
    }
}

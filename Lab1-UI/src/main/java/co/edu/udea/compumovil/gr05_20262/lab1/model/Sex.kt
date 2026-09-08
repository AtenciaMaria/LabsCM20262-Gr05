package co.edu.udea.compumovil.gr05_20262.lab1.model

import androidx.annotation.StringRes
import co.edu.udea.compumovil.gr05_20262.lab1.R

enum class Sex(@StringRes val labelRes: Int) {
    MALE(R.string.sex_male),
    FEMALE(R.string.sex_female),
    OTHER(R.string.sex_other);

    companion object {
        fun fromKey(key: String?): Sex? = entries.firstOrNull { it.name == key }
    }
}
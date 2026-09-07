package co.edu.udea.compumovil.gr05_20262.lab1.model

import androidx.annotation.StringRes
import co.edu.udea.compumovil.gr05_20262.lab1.R

enum class EducationLevel(@StringRes val labelRes: Int) {
    PRIMARY(R.string.edu_primary),
    SECONDARY(R.string.edu_secondary),
    UNDERGRADUATE(R.string.edu_undergraduate),
    POSTGRADUATE(R.string.edu_postgraduate);

    companion object {
        fun fromKey(key: String?): EducationLevel? = entries.firstOrNull { it.name == key }
    }
}
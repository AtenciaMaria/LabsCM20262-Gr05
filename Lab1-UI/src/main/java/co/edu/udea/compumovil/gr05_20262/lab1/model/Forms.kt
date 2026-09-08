package co.edu.udea.compumovil.gr05_20262.lab1.model

import android.content.Context
import co.edu.udea.compumovil.gr05_20262.lab1.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

data class PersonalData(
    val names: String = "",
    val surnames: String = "",
    val sex: Sex? = null,
    val birthDateMillis: Long? = null,
    val education: EducationLevel? = null
) {
    fun isValid(): Boolean =
        names.isNotBlank() && surnames.isNotBlank()

    fun toLog(context: Context): String {
        val dateText = birthDateMillis?.let { formatDate(context, it) } ?: "-"
        val sexText = sex?.label(context) ?: "-"
        val eduText = education?.label(context) ?: "-"
        return buildString {
            append(context.getString(labelHeader))
            append("\n")
            append("${names.trim()} ${surnames.trim()}")
            append("\n")
            append(sexText)
            append("\n")
            append(context.getString(R.string.log_born_on, dateText))
            append("\n")
            append(eduText)
        }
    }

    companion object {
        val labelHeader: Int = R.string.log_header_personal

        fun formatDate(context: Context, millis: Long): String {
            val locale = context.resources.configuration.locales[0]
            val pattern = if (locale.language == Locale("es").language) "dd/MM/yyyy" else "MM/dd/yyyy"
            return SimpleDateFormat(pattern, locale).format(Date(millis))
        }
    }
}

data class ContactData(
    val phone: String = "",
    val address: String = "",
    val email: String = "",
    val country: String = "",
    val city: String = ""
) {
    fun isValid(): Boolean =
        phone.isNotBlank() && isEmailValid(email) && country.isNotBlank()

    fun toLog(context: Context): String = buildString {
        append(context.getString(R.string.log_header_contact))
        append("\n")
        append(context.getString(R.string.log_phone, phone))
        append("\n")
        if (address.isNotBlank()) {
            append(context.getString(R.string.log_address, address))
            append("\n")
        }
        append(context.getString(R.string.log_email, email))
        append("\n")
        append(context.getString(R.string.log_country, country))
        if (city.isNotBlank()) {
            append("\n")
            append(context.getString(R.string.log_city, city))
        }
    }

    companion object {
        fun isEmailValid(email: String): Boolean =
            android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

        fun isPhoneValid(phone: String): Boolean =
            phone.trim().length in 7..15 && phone.all { it.isDigit() || it in "+- ()" }
    }
}

fun Sex.label(context: Context): String = context.getString(labelRes)
fun EducationLevel.label(context: Context): String = context.getString(labelRes)

fun todayMinus(years: Int): Long = Calendar.getInstance().apply {
    add(Calendar.YEAR, -years)
}.timeInMillis
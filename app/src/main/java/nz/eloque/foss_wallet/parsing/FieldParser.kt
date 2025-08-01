package nz.eloque.foss_wallet.parsing

import nz.eloque.foss_wallet.model.field.PassContent
import nz.eloque.foss_wallet.model.field.PassField
import nz.eloque.foss_wallet.utils.stringOrNull
import org.json.JSONObject
import java.time.format.FormatStyle

object FieldParser {

    fun parse(field: JSONObject): PassField {
        val key = field.getString("key")
        val label = field.stringOrNull("label")
        val value = field.getString("value")
        val changeMessage = if (field.has("changeMessage")) field.getString("changeMessage") else null

        val content = when {
            field.has("currencyCode") -> PassContent.Currency(value, field.getString("currencyCode"))
            field.hasDateStyle() && field.hasTimeStyle() -> PassContent.DateTime(InstantParser.parse(value), chooseBetter(field.getDateStyle(), field.getTimeStyle()))
            field.hasDateStyle() -> PassContent.Date(InstantParser.parse(value), field.getDateStyle())
            field.hasTimeStyle() -> PassContent.Time(InstantParser.parse(value), field.getTimeStyle())
            else -> PassContent.Plain(value)
        }

        return PassField(key, label, content, changeMessage)
    }

    private fun String.toFormatStyle(): FormatStyle {
        return when (this) {
            "PKDateStyleShort" -> FormatStyle.SHORT
            "PKDateStyleMedium" -> FormatStyle.MEDIUM
            "PKDateStyleLong" -> FormatStyle.LONG
            "PKDateStyleFull" -> FormatStyle.FULL
            else -> FormatStyle.FULL
        }
    }

    private fun JSONObject.hasDateStyle() = hasStyle("dateStyle")
    private fun JSONObject.hasTimeStyle() = hasStyle("timeStyle")
    private fun JSONObject.getDateStyle() = getString("dateStyle").toFormatStyle()
    private fun JSONObject.getTimeStyle() = getString("timeStyle").toFormatStyle()

    private fun JSONObject.hasStyle(key: String): Boolean {
        return this.has(key) && this.getString(key) != "PKDateStyleNone"
    }

    private fun chooseBetter(left: FormatStyle, right: FormatStyle): FormatStyle {
        return if (left.ordinal >= right.ordinal) left else right
    }
}
package nz.eloque.foss_wallet.model

import androidx.annotation.StringRes
import androidx.compose.runtime.saveable.Saver
import nz.eloque.foss_wallet.R
import java.time.ZonedDateTime


const val TIME_ADDED = "TimeAdded"
const val RELEVANT_DATE_NEWEST = "RelevantDateNewest"
const val RELEVANT_DATE_OLDEST = "RelevantDateOldest"
const val PUBLISHER_A_Z = "PublisherAZ"
const val PUBLISHER_Z_A = "PublisherZA"

private val oldestFirst = Comparator.comparing<LocalizedPassWithTags, ZonedDateTime?>(
    { it.pass.relevantDates.firstOrNull()?.startDate() },
    Comparator.nullsLast(Comparator.naturalOrder())
)

private val newestFirst = Comparator.comparing<LocalizedPassWithTags, ZonedDateTime?>(
    { it.pass.relevantDates.firstOrNull()?.startDate() },
    Comparator.nullsLast(Comparator.reverseOrder())
)

private val publisherAZ = Comparator.comparing<LocalizedPassWithTags, String>(
    { it.pass.organization.ifEmpty{it.pass.logoText?:""}.lowercase() },
    Comparator.naturalOrder()
)

private val publisherZA = Comparator.comparing<LocalizedPassWithTags, String>(
    { it.pass.organization.ifEmpty{it.pass.logoText?:""}.lowercase() },
    Comparator.reverseOrder()
)


sealed class SortOption(val name: String, @param:StringRes val l18n: Int, val comparator: Comparator<LocalizedPassWithTags>) {
    object TimeAdded : SortOption(TIME_ADDED, R.string.date_added, Comparator { left, right ->
        -left.pass.addedAt.compareTo(right.pass.addedAt)
    })
    object RelevantDateNewest : SortOption(RELEVANT_DATE_NEWEST, R.string.relevant_date_newest, newestFirst)
    object RelevantDateOldest : SortOption(RELEVANT_DATE_OLDEST, R.string.relevant_date_oldest, oldestFirst)
    object PublisherAZ : SortOption(PUBLISHER_A_Z, R.string.publisher_a_z, publisherAZ)
    object PublisherZA : SortOption(PUBLISHER_Z_A, R.string.publisher_z_a, publisherZA)

    companion object {
        fun all(): List<SortOption> {
            return listOf(TimeAdded, RelevantDateNewest, RelevantDateOldest, PublisherAZ, PublisherZA)
        }
    }
}

val SortOptionSaver: Saver<SortOption, String> = Saver(
    save = { it.name },
    restore = { SortOption.all().find { option -> option.name == it } }
)

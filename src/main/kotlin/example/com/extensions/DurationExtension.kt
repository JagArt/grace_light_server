package example.com.extensions

import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

fun Duration.Companion.ofSeconds(i: Int): Duration {
    return i.toDuration(DurationUnit.SECONDS)
}
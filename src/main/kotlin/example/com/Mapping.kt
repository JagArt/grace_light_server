package example.com

import example.com.model.SpeakerDto
import example.com.plugins.SpeakerEntity

fun SpeakerEntity.toModel(): SpeakerDto = SpeakerDto(
    id,
    firstName = firstName,
    lastName = lastName,
    description = description,
    age = age
)

fun SpeakerDto.fromModel(): SpeakerEntity = SpeakerEntity(
    id,
    firstName = firstName,
    lastName = lastName,
    description = description,
    age = age
)
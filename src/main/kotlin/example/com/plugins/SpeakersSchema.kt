package example.com.plugins

import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

@Serializable
data class SpeakerEntity(
    val id: Int? = null,
    val firstName: String,
    val lastName: String,
    val age: Int,
    val description: String
)

class SpeakersService(private val database: Database) {

    object Speakers : IntIdTable() {
        val firstName = varchar("firstName", length = 50)
        val lastName = varchar("lastName", length = 50)
        val description = varchar("description", length = 50)
        val age = integer("age")
    }

    init {
        transaction(database) {
            SchemaUtils.create(Speakers)
        }
    }

    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }

    suspend fun create(speakerEntity: SpeakerEntity): Int = dbQuery {
        Speakers.insert {
            it[firstName] = speakerEntity.firstName
            it[lastName] = speakerEntity.lastName
            it[description] = speakerEntity.description
            it[age] = speakerEntity.age
        }[Speakers.id].value
    }

    suspend fun read(id: Int): SpeakerEntity? {
        return dbQuery {
            Speakers.selectAll().where { Speakers.id eq id }
                .map {
                    SpeakerEntity(
                        firstName = it[Speakers.firstName],
                        lastName = it[Speakers.lastName],
                        description = it[Speakers.description],
                        age = it[Speakers.age],
                    )
                }
                .singleOrNull()
        }
    }

    suspend fun update(id: Int, speakerEntity: SpeakerEntity) {
        dbQuery {
            Speakers.update({ Speakers.id.eq(id) }) {
                it[firstName] = speakerEntity.firstName
                it[lastName] = speakerEntity.lastName
                it[description] = speakerEntity.description
                it[age] = speakerEntity.age
            }
        }
    }

    suspend fun delete(id: Int) {
        dbQuery {
            Speakers.deleteWhere { Speakers.id.eq(id) }
        }
    }
}

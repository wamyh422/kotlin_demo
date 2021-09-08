package demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.annotation.Id
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class DemoApplication

fun main(args: Array<String>) {
	runApplication<DemoApplication>(*args)
}

// repository and database operation
interface MessageRepository: CrudRepository<Message, String> {
	@Query("select * from messages")
	fun findMessages():List<Message>
}

// service
@Service
class MessageService (val db:MessageRepository) {
	fun findMessages(): List<Message> = db.findMessages()
}


// data class, using database
@Table("MESSAGE")
data class Message (@Id val id: String?, val text: String)


// Restful web api, root url test
@RestController
class MessageResource {
	@GetMapping
	fun index(): List<Message> = listOf(
		Message("Charlie", "Hello"),
		Message("Pancake", "Hey"),
		Message("Salmon", "Hello World")
	)
}
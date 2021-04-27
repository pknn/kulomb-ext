package jsonBodies

import play.api.libs.json.JsonNaming.SnakeCase
import play.api.libs.json.{Format, Json, JsonConfiguration}

case class TaskCreationResponse(id: Int,
                                name: String,
                                note: String,
                                source: String,
                                language: String,
                                htmlTemplate: String,
                                disabled: Boolean,
                                isPrivate: Boolean)

object TaskCreationResponse {

	implicit val jsonConfiguration: JsonConfiguration = JsonConfiguration(SnakeCase)
	implicit val jsonFormat: Format[TaskCreationResponse] = Json.format[TaskCreationResponse]
}

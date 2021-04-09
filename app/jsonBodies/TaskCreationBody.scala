package jsonBodies

import play.api.libs.json.{Format, Json, JsonConfiguration}
import play.api.libs.json.JsonNaming.SnakeCase

case class TaskCreationBody(name: String, language: String, source: String)

object TaskCreationBody {
	implicit val jsonConfiguration: JsonConfiguration = JsonConfiguration(SnakeCase)
	implicit val jsonFormat: Format[TaskCreationBody] = Json.format[TaskCreationBody]
}

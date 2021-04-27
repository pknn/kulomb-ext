package jsonBodies

import play.api.libs.json.JsonNaming.SnakeCase
import play.api.libs.json.{Format, Json, JsonConfiguration}

case class TaskPadCreationResponse(id: Int,
                                   task: TaskCreationResponse,
                                   accessKey: String,
                                   secretKey: String,
                                   createdAt: String,
                                   updatedAt: String)


object TaskPadCreationResponse {

	implicit val jsonConfiguration: JsonConfiguration = JsonConfiguration(SnakeCase)
	implicit val jsonFormat: Format[TaskPadCreationResponse] = Json.format[TaskPadCreationResponse]

}
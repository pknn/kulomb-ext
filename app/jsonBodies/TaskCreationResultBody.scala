package jsonBodies

import play.api.libs.json.JsonNaming.SnakeCase
import play.api.libs.json.{Format, Json, JsonConfiguration, Reads}
import presenters.{TaskPresenter, UserPresenter}

case class TaskCreationResultBody(id: String,
                                  task: TaskPresenter,
                                  creator: UserPresenter,
                                  accessKey: String,
                                  secretKey: String,
                                  createdAt: String,
                                  updatedAt: String)

object TaskCreationResultBody {
	implicit val jsonConfiguration: JsonConfiguration = JsonConfiguration(SnakeCase)
	implicit val jsonFormat: Format[TaskCreationResultBody] = Json.format[TaskCreationResultBody]
	implicit val jsonReads: Reads[TaskCreationResultBody] = Json.reads[TaskCreationResultBody]
}

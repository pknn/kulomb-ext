package presenters

import models.TaskPad
import play.api.libs.json.JsonNaming.SnakeCase
import play.api.libs.json.{Format, Json, JsonConfiguration}

case class TaskPadPresenter(id: Int,
                            name: String,
                            htmlTemplate: String,
                            language: String,
                            accessKey: String,
                            secretKey: String)

object TaskPadPresenter {
	implicit val jsonConfiguration: JsonConfiguration = JsonConfiguration(SnakeCase)
	implicit val jsonFormat: Format[TaskPadPresenter] = Json.format[TaskPadPresenter]

	def from(taskPad: TaskPad): TaskPadPresenter =
		TaskPadPresenter(id = taskPad.id,
		                 name = taskPad.name,
		                 htmlTemplate = taskPad.htmlTemplate,
		                 language = taskPad.language,
		                 accessKey = taskPad.accessKey,
		                 secretKey = taskPad.secretKey)
}

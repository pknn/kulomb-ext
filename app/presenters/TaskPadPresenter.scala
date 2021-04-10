package presenters

import models.TaskPad
import play.api.libs.json.JsonNaming.SnakeCase
import play.api.libs.json.{Format, Json, JsonConfiguration}

case class TaskPadPresenter(id: String,
                            task: TaskPresenter,
                            creator: UserPresenter,
                            accessKey: String,
                            secretKey: String,
                            createdAt: String,
                            updatedAt: String)


object TaskPadPresenter {

	implicit val jsonConfiguration: JsonConfiguration = JsonConfiguration(SnakeCase)
	implicit val jsonFormat: Format[TaskPadPresenter] = Json.format[TaskPadPresenter]

	def from(taskPad: TaskPad): TaskPadPresenter = TaskPadPresenter(id = taskPad.id,
	                                                                task = TaskPresenter.apply(taskPad.task),
	                                                                creator = UserPresenter.apply(taskPad.creator),
	                                                                accessKey = taskPad.accessKey,
	                                                                secretKey = taskPad.secretKey,
	                                                                createdAt = taskPad.createdAt.toString,
	                                                                updatedAt = taskPad.updatedAt.toString)

}
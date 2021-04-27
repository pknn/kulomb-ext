package models

import jsonBodies.TaskPadCreationResponse

case class TaskPad(id: Int,
                   name: String,
                   htmlTemplate: String,
                   language: String,
                   accessKey: String,
                   secretKey: String)

object TaskPad {
	def from(taskPadCreationResponse: TaskPadCreationResponse, taskHtmlTemplate: String): TaskPad =
		TaskPad(taskPadCreationResponse.id,
		        taskPadCreationResponse.task.name,
		        taskHtmlTemplate,
		        taskPadCreationResponse.task.language,
		        taskPadCreationResponse.accessKey,
		        taskPadCreationResponse.secretKey)
}

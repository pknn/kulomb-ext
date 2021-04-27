package models

import jsonBodies.TaskPadCreationResponse

case class TaskPad(id: Int,
                   name: String,
                   answerKeys: Seq[String],
                   language: String,
                   accessKey: String,
                   secretKey: String)

object TaskPad {
	def from(taskPadCreationResponse: TaskPadCreationResponse, answerKeys: Seq[String]): TaskPad =
		TaskPad(taskPadCreationResponse.id,
		        taskPadCreationResponse.task.name,
		        answerKeys,
		        taskPadCreationResponse.task.language,
		        taskPadCreationResponse.accessKey,
		        taskPadCreationResponse.secretKey)
}

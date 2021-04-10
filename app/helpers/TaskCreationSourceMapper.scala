package helpers

import jsonBodies.TaskCreationBody

object TaskCreationSourceMapper {
	def map(taskCreationBody: TaskCreationBody): TaskCreationBody =
		taskCreationBody.copy(source = taskCreationBody.source + "mapped")
}

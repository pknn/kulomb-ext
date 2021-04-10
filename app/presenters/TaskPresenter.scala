package presenters

import commons.Types.Version
import models.Task
import play.api.libs.json.JsonNaming.SnakeCase
import play.api.libs.json.{Format, Json, JsonConfiguration}

case class TaskPresenter(id: String,
                         name: String,
                         note: String,
                         source: String,
                         language: String,
                         languageOptions: Seq[String],
                         htmlTemplate: String,
                         code: CodePresenter,
                         disabled: Boolean,
                         isPrivate: Boolean,
                         tags: Seq[String],
                         generator: String,
                         textGrader: String,
                         testCases: String,
                         textBlanks: String,
                         creator: UserPresenter,
                         owner: UserPresenter,
                         version: Version,
                         previousVersion: Version)

object TaskPresenter {

	implicit val jsonConfiguration: JsonConfiguration = JsonConfiguration(SnakeCase)
	implicit val jsonFormat: Format[TaskPresenter] = Json.format[TaskPresenter]

	def apply(task: Task): TaskPresenter =
		TaskPresenter(id = task.id,
		              name = task.name,
		              note = task.note,
		              source = task.source,
		              language = task.language,
		              languageOptions = task.languageOptions,
		              htmlTemplate = task.htmlTemplate,
		              code = CodePresenter.apply(task.code),
		              disabled = task.disabled,
		              isPrivate = task.isPrivate,
		              tags = task.tags,
		              generator = task.generator,
		              textGrader = task.textGrader,
		              testCases = task.testCases,
		              textBlanks = task.textBlanks,
		              creator = UserPresenter.apply(task.creator),
		              owner = UserPresenter.apply(task.owner),
		              version = task.version,
		              previousVersion = task.previousVersion)
}

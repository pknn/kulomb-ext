package presenters

import models.Code
import play.api.libs.json.JsonNaming.SnakeCase
import play.api.libs.json.{Format, Json, JsonConfiguration}

case class CodePresenter(content: String)

object CodePresenter {

	implicit val jsonConfiguration: JsonConfiguration = JsonConfiguration(SnakeCase)
	implicit val jsonFormat: Format[CodePresenter] = Json.format[CodePresenter]

	def apply(code: Code): CodePresenter = CodePresenter(content = code.content)
}

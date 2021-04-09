package presenters

import play.api.libs.json.{Format, Json, JsonConfiguration}
import play.api.libs.json.JsonNaming.SnakeCase

case class VersionPresenter(version: String)

object VersionPresenter {
	implicit val jsonConfiguration: JsonConfiguration = JsonConfiguration(SnakeCase)
	implicit val jsonFormat: Format[VersionPresenter] = Json.format[VersionPresenter]

	def mock: VersionPresenter = VersionPresenter("1.0.0")
}

package presenters

import models.User
import play.api.libs.json.JsonNaming.SnakeCase
import play.api.libs.json.{Format, Json, JsonConfiguration}

case class UserPresenter(name: String)

object UserPresenter {

	implicit val jsonConfiguration: JsonConfiguration = JsonConfiguration(SnakeCase)
	implicit val jsonFormat: Format[UserPresenter] = Json.format[UserPresenter]

	def apply(user: User): UserPresenter = UserPresenter(user.name)
}

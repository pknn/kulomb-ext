package commons

import play.api.libs.json.{Json, JsValue, Writes}
import play.api.mvc.Result
import play.api.mvc.Results._

import scala.concurrent.{ExecutionContext, Future}

object ApiResults {

	def json[C](content: C)(implicit writes: Writes[C]): Result = Ok(successResultJsonContent(content))

	def async[C](futureContent: Future[C])(implicit writes: Writes[C], ec: ExecutionContext): Future[Result] =
		futureContent
			.map(content => Ok(successResultJsonContent(content)))
			.recover {
				case throwable: Throwable => BadRequest(failResultJsonContent(throwable))
			}

	private def successResultJsonContent[C](content: C)(implicit writes: Writes[C]): JsValue = Json.toJson(content)

	private def failResultJsonContent(throwable: Throwable): JsValue = Json.obj("message" -> throwable.toString)

}
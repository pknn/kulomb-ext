package controllers

import clients.ProxyClient
import com.google.inject.{Inject, Singleton}
import commons.ApiResults
import jsonBodies.TaskCreationBody
import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}
import presenters.VersionPresenter
import sttp.client3.Response

import scala.concurrent.{ExecutionContext, Future}

@Singleton()
class VersionController @Inject()(val controllerComponents: ControllerComponents, proxyClient: ProxyClient)
                                 (implicit ec: ExecutionContext) extends BaseController {
	def get: Action[AnyContent] = Action.async {
		val versionPresenter = VersionPresenter.mock

		ApiResults.async(Future(versionPresenter))
	}

	def test: Action[TaskCreationBody] = Action(parse.json[TaskCreationBody]).async { implicit request =>
		val body = request.body

		val result: Future[Response[String]] = proxyClient.execute(request.uri,
		                                                           request.method,
		                                                           request.headers.toSimpleMap,
		                                                           Json.toJson(body.copy(source = body
			                                                           .source + "asdfbababu")))

		ApiResults.async(result.map(_.body))
	}
}

package controllers

import com.google.inject.{Inject, Singleton}
import commons.ApiResults
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}
import presenters.VersionPresenter

import scala.concurrent.{ExecutionContext, Future}

@Singleton()
class VersionController @Inject()(val controllerComponents: ControllerComponents)
                                 (implicit ec: ExecutionContext) extends BaseController {
	def get: Action[AnyContent] = Action.async {
		val versionPresenter = VersionPresenter.mock

		ApiResults.async(Future(versionPresenter))
	}
}

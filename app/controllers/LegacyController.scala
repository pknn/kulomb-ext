package controllers

import clients.ProxyClient
import com.google.inject.{Inject, Singleton}
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}

@Singleton
class LegacyController @Inject()(val controllerComponents: ControllerComponents,
                                 proxyClient: ProxyClient) extends BaseController {
	def proxy: Action[AnyContent] = Action.async { implicit request =>
		proxyClient.proxy(request)
	}
}

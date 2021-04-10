package routers

import com.google.inject.Inject
import controllers.VersionController
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._

class ApiRouter @Inject()(versionRouter: VersionRouter, versionController: VersionController) extends SimpleRouter {
	override def routes: Routes =
		versionRouter.withPrefix("/version").routes.orElse {
			case POST(p"/tasks") => versionController.test
		}
}

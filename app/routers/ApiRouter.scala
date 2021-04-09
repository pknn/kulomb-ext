package routers

import com.google.inject.Inject
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter

class ApiRouter @Inject()(versionRouter: VersionRouter) extends SimpleRouter {
	override def routes: Routes =
		versionRouter.withPrefix("/version").routes
}

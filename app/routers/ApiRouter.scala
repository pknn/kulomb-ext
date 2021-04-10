package routers

import com.google.inject.Inject
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter

class ApiRouter @Inject()(legacyRouter: LegacyRouter,
                          versionRouter: VersionRouter,
                          taskRouter: TaskRouter) extends SimpleRouter {
	override def routes: Routes =
		versionRouter.withPrefix("/version").routes
			.orElse(taskRouter.withPrefix("/tasks").routes)
			.orElse(legacyRouter.routes)
}

package routers

import com.google.inject.Inject
import controllers.VersionController
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._

class VersionRouter @Inject()(versionController: VersionController) extends SimpleRouter {
	override def routes: Routes = {
		case GET(p"/") => versionController.get
	}
}

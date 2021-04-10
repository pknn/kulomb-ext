package routers

import com.google.inject.Inject
import controllers.LegacyController
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter

class LegacyRouter @Inject()(legacyController: LegacyController) extends SimpleRouter {
	override def routes: Routes = {
		case _ => legacyController.proxy
	}
}

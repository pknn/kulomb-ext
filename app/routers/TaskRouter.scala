package routers

import com.google.inject.Inject
import controllers.TaskController
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._

class TaskRouter @Inject()(taskController: TaskController) extends SimpleRouter {
	override def routes: Routes = {
		case GET(p"/") => taskController.get
		case POST(p"/") => taskController.create
	}
}

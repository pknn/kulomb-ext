package controllers

import com.google.inject.{Inject, Singleton}
import commons.ApiResults
import jsonBodies.TaskCreationBody
import play.api.libs.json.JsValue
import play.api.mvc.{Action, BaseController, ControllerComponents}
import useCases.TaskPadUseCase

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class TaskController @Inject()(val controllerComponents: ControllerComponents, taskPadUseCase: TaskPadUseCase)
                              (implicit ec: ExecutionContext) extends BaseController {
	def create: Action[TaskCreationBody] = Action(parse.json[TaskCreationBody]).async { implicit request =>
		val result: Future[JsValue] = taskPadUseCase.create(request.uri,
		                                                    request.method,
		                                                    request.headers.toSimpleMap,
		                                                    request.body)

		ApiResults.async(result)
	}

}

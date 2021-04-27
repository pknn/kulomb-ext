package controllers

import com.google.inject.{Inject, Singleton}
import commons.ApiResults
import helpers.TaskCreationResponseMapper
import jsonBodies.{TaskCreationBody, TaskPadCreationResponse}
import models.TaskPad
import play.api.libs.json.JsValue
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}
import presenters.TaskPadPresenter
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

		val taskPad: Future[Option[TaskPad]] = result.map(_.validate[TaskPadCreationResponse]
			                                                  .asOpt
			                                                  .map(TaskCreationResponseMapper.map)
		                                                  )

		val taskPadPresenter = taskPad.map(_.map(TaskPadPresenter.from))

		ApiResults.async(taskPadPresenter)
	}

	def get: Action[AnyContent] = Action.async { implicit request =>
		val result = taskPadUseCase.get(request.uri,
		                                request.method,
		                                request.headers.toSimpleMap)

		ApiResults.async(result)
	}
}

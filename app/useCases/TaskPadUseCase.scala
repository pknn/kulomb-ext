package useCases

import clients.ProxyClient
import com.google.inject.{Inject, Singleton}
import helpers.TaskCreationSourceMapper
import jsonBodies.{TaskCreationBody, TaskCreationResultBody}
import play.api.libs.json.{JsValue, Json}
import sttp.client3.Response

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class TaskPadUseCase @Inject()(proxyClient: ProxyClient) {
	def create(uri: String, method: String, headers: Map[String, String], taskCreationBody: TaskCreationBody)
	          (implicit ec: ExecutionContext): Future[Option[TaskCreationResultBody]] = {
		val mappedBody: TaskCreationBody = TaskCreationSourceMapper.map(taskCreationBody)
		val jsonBody: JsValue = Json.toJson(mappedBody)
		val result: Future[Response[String]] = proxyClient.execute(uri, method, headers, jsonBody)
		result.map(response => Json
			.parse(response.body)
			.validate[TaskCreationResultBody]
			.asOpt)


	}
}

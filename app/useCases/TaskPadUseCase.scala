package useCases

import clients.ProxyClient
import com.google.inject.{Inject, Singleton}
import helpers.TaskCreationSourceMapper
import jsonBodies.TaskCreationBody
import play.api.libs.json.{JsValue, Json}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class TaskPadUseCase @Inject()(proxyClient: ProxyClient) {
	def create(uri: String, method: String, headers: Map[String, String], taskCreationBody: TaskCreationBody)
	          (implicit ec: ExecutionContext): Future[JsValue] = {
		val mappedBody: TaskCreationBody = TaskCreationSourceMapper.map(taskCreationBody)
		val jsonBody: JsValue = Json.toJson(mappedBody)

		val result = proxyClient.execute(uri, method, headers, jsonBody)

		result
	}

	def get(uri: String, method: String, headers: Map[String, String])(implicit ec: ExecutionContext): Future[JsValue] = {
		proxyClient.execute(uri, method, headers)
	}

}

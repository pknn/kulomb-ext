package clients

import akka.util.ByteString
import com.google.inject.{Inject, Singleton}
import play.api.http.HttpEntity
import play.api.libs.json.JsObject
import play.api.libs.ws.WSClient
import play.api.mvc.{AnyContent, Request, ResponseHeader, Result}
import sttp.model.Method

import scala.concurrent.{ExecutionContext, Future}


@Singleton
class ProxyClient @Inject()(ws: WSClient)
                           (implicit ec: ExecutionContext) {
	private val proxyUrl = "http://localhost:8000"

	private def getMethod(method: String): Method = method match {
		case "GET" => Method.GET
		case "POST" => Method.POST
		case "PUT" => Method.PUT
		case "DELETE" => Method.DELETE
		case "HEAD" => Method.HEAD
	}

	def proxy(request: Request[AnyContent]): Future[Result] = {
		val body = request.body.asJson match {
			case Some(value) => value
			case None => JsObject.empty
		}

		ws.url(proxyUrl + request.uri)
			.withMethod(request.method)
			.withHttpHeaders(request.headers.toSimpleMap.toList: _*)
			.withBody(body)
			.execute()
			.recoverWith {
				case e => println(e)
					Future.failed(e)
			}
			.map { response =>
				val body: String = response.body
				val headers: Map[String, String] = response.headers.map { kv =>
					(kv._1,
						if (kv._1 == "Content-Length") body.length.toString
						else kv._2.head
					)
				}
				Result(ResponseHeader(response.status, headers), HttpEntity.Strict(ByteString(body), None))
			}
	}
}

package clients

import akka.util.ByteString
import com.google.inject.{Inject, Singleton}
import commons.AkkaBackend
import play.api.http.HttpEntity
import play.api.libs.json.{JsObject, JsValue, Json}
import play.api.libs.ws.WSClient
import play.api.mvc.{AnyContent, Request, ResponseHeader, Result}
import sttp.client3.{Identity, RequestT, Response, quickRequest}
import sttp.model.{MediaType, Method, Uri}

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Properties


@Singleton
class ProxyClient @Inject()(ws: WSClient, akkaBackend: AkkaBackend)
                           (implicit ec: ExecutionContext) {
	private val proxyPort = Properties.envOrElse("PROXY_PORT", "8000").toInt
	private val proxyHost = Properties.envOrElse("PROXY_HOST", "localhost")
	private val proxyUrl = s"http://$proxyHost:$proxyPort"

	private def getMethod(method: String): Method = method match {
		case "GET" => Method.GET
		case "POST" => Method.POST
		case "PUT" => Method.PUT
		case "DELETE" => Method.DELETE
		case "HEAD" => Method.HEAD
	}

	def proxy(request: Request[AnyContent]): Future[Result] = {
		val body: JsValue = request.body.asJson match {
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

	def akkaProxy(request: Request[AnyContent]): Future[Response[String]] = {
		val uri = request.uri
		val method = request.method
		val headers = request.headers.toSimpleMap

		(request.body.asJson match {
			case Some(body) => requestFor(uri, method, headers).body(Json.stringify(body))
			case None => requestFor(uri, method, headers)
		})
			.send(akkaBackend.getInstance)
	}

	def execute(uri: String, method: String, headers: Map[String, String]): Future[JsValue] =
		requestFor(uri, method, headers)
			.send(akkaBackend.getInstance)
			.map(response => Json.parse(response.body))


	def execute[B](uri: String, method: String, headers: Map[String, String], body: JsValue): Future[JsValue] =
		requestFor(uri, method, headers)
			.body(Json.stringify(body))
			.send(akkaBackend.getInstance)
			.map(response => Json.parse(response.body))


	private def requestFor[B](uri: String,
	                          method: String,
	                          headers: Map[String, String]): RequestT[Identity, String, Any] = {
		val uris: Uri = Uri(host = proxyHost, port = proxyPort, uri.split("/").toSeq.filter(_.nonEmpty))
		val methods: Method = getMethod(method)

		quickRequest.method(methods, uris)
			.headers(headers)
			.contentType(MediaType.ApplicationJson)
			.followRedirects(true)

	}
}

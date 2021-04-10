package commons

import akka.actor.ActorSystem
import com.google.inject.{Inject, Singleton}
import sttp.capabilities
import sttp.capabilities.akka.AkkaStreams
import sttp.client3.SttpBackend
import sttp.client3.akkahttp.AkkaHttpBackend

import scala.concurrent.Future

@Singleton
class AkkaBackend @Inject()(actor: ActorSystem) {
	def getInstance: SttpBackend[Future, AkkaStreams with capabilities.WebSockets] =
		AkkaHttpBackend.usingActorSystem(actor)
}

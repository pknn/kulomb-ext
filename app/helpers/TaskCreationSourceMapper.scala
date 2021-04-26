package helpers

import jsonBodies.TaskCreationBody

import scala.xml.XML.loadString

object TaskCreationSourceMapper {
	private def parse(source: String): String = {
		val replacedSource = source.replaceAll("&nbsp;", " ")

		loadString(replacedSource).child
			.map {
				case <br/> => "\n"
				case elem if elem.child.nonEmpty => s" {{ ${elem.child.head} }} "
				case elem => elem.toString()
			}
			.mkString("")
	}

	def map(taskCreationBody: TaskCreationBody): TaskCreationBody =
		taskCreationBody.copy(source = parse(taskCreationBody.source))
}

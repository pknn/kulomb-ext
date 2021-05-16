package helpers

import jsonBodies.TaskPadCreationResponse
import models.TaskPad

import scala.xml.Node
import scala.xml.XML.loadString

object TaskCreationResponseMapper {
	private def getInputName(elem: Node): String = (elem \ "@name").toString()

	private def unescape(xmlString: String): String =
		xmlString
			.replaceAll("&lt;", "<")
			.replaceAll("&gt;", ">")
			.replaceAll("&amp;quot;", "\"")
			.replaceAll("&quot;", "\"")
			.replaceAll("\n", "<br />")
			.replaceAll("\t", "&nbsp;")

	private def toInputName(elem: Node): Option[String] = elem match {
		case elem if elem.label == "input" || elem.label == "textarea" => Some(getInputName(elem))
		case _ => None
	}

	private def parse(htmlTemplate: String): Seq[String] = {
		if (htmlTemplate.isEmpty) Seq.empty
		else
			loadString(htmlTemplate)
				.child
				.flatMap(_.child.flatMap(_.child.flatMap(_.child.map(toInputName).filter(_.isDefined).map(_.get))))
	}

	def map(taskPadCreationResponse: TaskPadCreationResponse): TaskPad =
		TaskPad.from(taskPadCreationResponse, parse(taskPadCreationResponse.task.htmlTemplate))
}

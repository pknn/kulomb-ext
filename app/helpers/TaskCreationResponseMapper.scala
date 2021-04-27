package helpers

import jsonBodies.TaskPadCreationResponse
import models.TaskPad

import scala.xml.XML.loadString
import scala.xml.{Node, Text}

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

	private def nodeToElabNode(elem: Node): Node = elem match {
		case elem if elem.label == "span" => Text(unescape(elem.text))
		case elem if elem.label == "input" => <span class="sourcespan" name={getInputName(elem)}></span>
		case elem if elem.label == "textarea" => <span class="boxspan" name={getInputName(elem)}></span>
		case elem => elem
	}

	private def parse(htmlTemplate: String): String = {
		val mappedTemplate = loadString(htmlTemplate)
			.child
			.flatMap(_.child.flatMap(_.child.flatMap(_.child.map(nodeToElabNode))))
			.mkString("")


		val element = <div class="sourcecode">
			{mappedTemplate}
		</div>

		unescape(element.toString())
	}

	def map(taskPadCreationResponse: TaskPadCreationResponse): TaskPad =
		TaskPad.from(taskPadCreationResponse, parse(taskPadCreationResponse.task.htmlTemplate))
}

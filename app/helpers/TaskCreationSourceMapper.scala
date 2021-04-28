package helpers

import jsonBodies.TaskCreationBody

import scala.xml.Node
import scala.xml.XML.loadString

object TaskCreationSourceMapper {

	def map(taskCreationBody: TaskCreationBody): TaskCreationBody =
		taskCreationBody.copy(source = parse(taskCreationBody.source, taskCreationBody.testCases))

	private def getClassName(node: Node) = (node \ "@class").toString()

	private def getEnclosure(code: String, modifier: String): String =
		"::elab:endcode\n"
			.concat(s"::elab:begincode $modifier=True\n")
			.concat(code)
			.concat("\n::elab:endcode\n")
			.concat("::elab:begincode\n")

	private def parseSpan(elem: Node) = getClassName(elem) match {
		case "sourcespan" =>
			if (elem.child.length > 1) getEnclosure(elem.child.map(nodeToText).mkString(""), "blank")
			else s"{{ ${elem.child.map(nodeToText).mkString("")} }}"
		case "hidespan" => getEnclosure(elem.child.map(nodeToText).mkString(""), "hidden")
	}

	private def nodeToText(node: Node): String = node match {
		case <br/> => "\n"
		case elem if elem.label == "span" => parseSpan(elem)
		case elem => elem.text
	}

	private def testCaseToText(testCase: String): String =
		"::elab:begintest\n"
			.concat(testCase.replaceAll(" ", "\n"))
			.concat("\n::elab:endtest\n")


	private def parse(source: String, testCases: Seq[String]): String = {
		val replacedSource = source.replaceAll("&nbsp;", " ")

		loadString(replacedSource)
			.child
			.flatMap(_.child.map(nodeToText).prepended("::elab:begincode\n").concat("\n::elab:endcode\n"))
			.concat(testCases.map(testCaseToText))
			.mkString("")
	}
}

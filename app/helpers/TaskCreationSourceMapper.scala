package helpers

import jsonBodies.TaskCreationBody

import scala.xml.Node
import scala.xml.XML.loadString

object TaskCreationSourceMapper {

	private def getClassName(node: Node) = (node \ "@class").toString()

	private def getEnclosure(code: String, modifier: String): String =
		"::elab:endcode\n"
			.concat(s"::elab:begincode $modifier=True\n")
			.concat(code)
			.concat("\n::elab:endcode\n")
			.concat("::elab:begincode\n")

	private def nodeToText(node: Node): String = node match {
		case <br/> => "\n"
		case elem if elem.label == "span" && getClassName(elem) == "sourcespan" =>
			s"{{ ${elem.child.map(nodeToText).mkString("")} }}"
		case elem if elem.label == "span" && getClassName(elem) == "hidespan" =>
			getEnclosure(elem.child.map(nodeToText).mkString(""), "hidden")
		case elem if elem.label == "span" && getClassName(elem) == "boxspan" =>
			getEnclosure(elem.child.map(nodeToText).mkString(""), "blank")
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
			.flatMap(_.child.map(nodeToText).prepended("::elab:begincode\n").concat("::elab:endcode\n"))
			.concat(testCases.map(testCaseToText))
			.mkString("")
	}

	def map(taskCreationBody: TaskCreationBody): TaskCreationBody =
		taskCreationBody.copy(source = parse(taskCreationBody.source, taskCreationBody.testCases))
}

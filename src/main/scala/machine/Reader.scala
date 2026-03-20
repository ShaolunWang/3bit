package machine
import scala.io
import scala.io.Source

type Input = (Registers, Vector[Int])

object InputParser:
  def parse(path: String): Input =
    val lines = Source.fromFile(path).getLines().toList
    val X = parseRegs(lines, "Register X")
    val Y = parseRegs(lines, "Register Y")
    val Z = parseRegs(lines, "Register Z")
    val program: Vector[Int] = parseProg(lines)
    (Registers(X, Y, Z), program)

  private def parseRegs(lines: List[String], regName: String): Int =
    lines
      .find(_.trim.startsWith(regName)) // requires to trim to make tests happy
      .getOrElse(sys.error(s"$regName not found"))
      .split(":")(1) // getting idx=1: ("Register X", " 1234")
      .trim // there is a space afterwards so we trim
      .toInt
  private def parseProg(lines: List[String]): Vector[Int] =
    lines
      .find(_.trim.startsWith("Program"))
      .getOrElse(s"Program not found")
      .split(":")(1)
      .split(",")
      .toVector
      .map(_.trim) // trim just in case we got spaces
      .map(_.toInt)

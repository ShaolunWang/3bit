package machine

import munit.FunSuite
import java.nio.file.{Files, Paths}
import java.nio.charset.StandardCharsets

class InputParserTest extends FunSuite {

  def makeTempFile(content: String)(test: String => Unit): Unit = {
    val file = Files.createTempFile("input", ".3bit")
    Files.write(file, content.getBytes(StandardCharsets.UTF_8))
    test(file.toString)
    Files.deleteIfExists(file)
  }

  test("parses input correctly") {
    val content =
      """Register X: 3729
      Register Y: 0
      Register Z: 0
      Program: 0,1,5,4,3,0
      """.stripMargin

    makeTempFile(content) { path =>
      val (regs, prog) = InputParser.parse(path)

      assertEquals(regs.x, 3729)
      assertEquals(regs.y, 0)
      assertEquals(regs.z, 0)
      assertEquals(prog, Vector(0, 1, 5, 4, 3, 0))
    }
  }

  test("parses program with spaces") {
    val content =
      """Register X: 1
      Register Y: 2
      Register Z: 3
      Program: 0, 1, 5, 4, 3, 0
      """
    makeTempFile(content) { path =>
      val (_, prog) = InputParser.parse(path)

      assertEquals(prog, Vector(0, 1, 5, 4, 3, 0))
    }
  }

  test("fails when no register Z") {
    val content =
      """Register X: 1
      Register Y: 2
      Program: 1,2,3
      """.stripMargin

    makeTempFile(content) { path =>
      intercept[RuntimeException] {
        InputParser.parse(path)
      }
    }
  }

  test("fails when no program") {
    val content =
      """Register X: 1
      Register Y: 2
      Register Z: 3
      """.stripMargin

    makeTempFile(content) { path =>
      intercept[Exception] {
        InputParser.parse(path)
      }
    }
  }

}

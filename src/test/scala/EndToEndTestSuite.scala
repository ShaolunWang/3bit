package machine

import munit.FunSuite

// examples given by the instructions
class EndToEndTest extends FunSuite {

  def load(name: String) =
    InputParser.parse(getClass.getResource(s"/input/$name").getPath)

  test("Starting state 1") {
    val (regs, prog) = load("test1.3bit")

    val finalMachine = run(Machine(regs, prog))

    assertEquals(
      finalMachine.regs.out,
      Vector(0, 4, 2, 1, 4, 2, 5, 6, 7, 3, 1, 0)
    )
  }

  test("Starting state 2") {
    val (regs, prog) = load("test2.3bit")

    val finalMachine = run(Machine(regs, prog))

    assertEquals(
      finalMachine.regs.out,
      Vector(5, 7, 6, 5, 7, 0, 4, 0)
    )
  }
}

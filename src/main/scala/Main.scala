import machine.Machine
import machine.Registers
import machine.step3
import machine.fetch
import machine.execute
import machine.InputParser

@main def main(file: String): Unit =
  val (reg, prog) = InputParser.parse(file)
  var m = Machine(
    regs = reg,
    program = prog,
    opcode = 0,
    operand = 0,
    aluRes = 0,
    ip = 0
  )

  // NOTE: not using the runner because
  // I'd like to pretty print for debugging here
  while (m.ip < prog.length) {
    m = step3(m)
    PPrint.pretty(m)
  }

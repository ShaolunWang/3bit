package machine;
// NOTE:: No need of ID or MEM because
// 1. instr is trivial
// 2. no memory access
enum Phase { case IF, EX, WB }

case class Machine(
    regs: Registers,
    program: Vector[Int],
    phase: Phase = Phase.IF, // we start with IF as initial state
    opcode: Int,
    operand: Int,
    aluRes: Int
)

object Machine {
  def step(m: Machine): Machine = {
    m.phase match {
      case Phase.IF =>
        val parsedOpcode = m.program(m.regs.ip)
        val parsedOperand = m.program(m.regs.ip + 1)
        m.copy(opcode = parsedOpcode, operand = parsedOperand, phase = Phase.EX)
      case Phase.EX =>
        // FIXME: ex currently update ip
        val updatedRegs = Instructions.execute(m.opcode, m.operand, m.regs)
        m.copy(regs = updatedRegs, phase = Phase.WB)
      case Phase.WB =>
        // TODO: after fixed this case should have proper
        // write backs
        m.copy(phase = Phase.IF)
    }
  }
}

package machine;

import machine.Instructions
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
    aluRes: Int,
    ip: Int
)

type Step = Machine => Machine

def fetch: Step = m =>
  val parsedOpcode = m.program(m.ip)
  val parsedOperand = m.program(m.ip + 1)
  m.copy(
    opcode = parsedOpcode,
    operand = parsedOperand,
    phase = Phase.EX,
    ip = m.ip + 2
  )

def execute: Step = m =>
  val r = m.regs
  m.opcode match
    case 0 => // xdv
      val res = Instructions.XDV(m.opcode, m.operand, m.ip, m.regs)
      m.copy(aluRes = res, phase = Phase.WB)
    case 1 => // yxl
      val res = Instructions.YXL(m.opcode, m.operand, m.ip, m.regs)
      m.copy(aluRes = res, phase = Phase.WB)
    case 2 => // yst
      val res = Instructions.YST(m.opcode, m.operand, m.ip, m.regs)
      m.copy(aluRes = res, phase = Phase.WB)

    case 3 => // jnz
      val res = Instructions.JNZ(m.opcode, m.operand, m.ip, m.regs)
      m.copy(aluRes = res, ip = res, phase = Phase.WB)

    case 4 => // yxz
      val res = Instructions.YXZ(m.opcode, m.operand, m.ip, m.regs)
      m.copy(aluRes = res, phase = Phase.WB)

    case 5 => // out
      val res = Instructions.OUT(m.opcode, m.operand, m.ip, m.regs)
      m.copy(aluRes = res, phase = Phase.WB)

    case 6 => // ydv
      val res = Instructions.YDV(m.opcode, m.operand, m.ip, m.regs)
      m.copy(aluRes = res, phase = Phase.WB)

    case 7 => // zdv
      val res = Instructions.YDV(m.opcode, m.operand, m.ip, m.regs)
      m.copy(aluRes = res, phase = Phase.WB)

def writeBack: Step = m =>
  val r = m.regs

  val newRegs = m.opcode match
    case 0 => r.copy(x = m.aluRes)
    case 1 => r.copy(y = m.aluRes)
    case 2 => r.copy(y = m.aluRes)
    case 3 => r
    case 4 => r.copy(y = m.aluRes) // yxz
    case 5 => r.copy(out = r.out.appended(m.aluRes)) // out
    case 6 => r.copy(y = m.aluRes)
    case 7 => r.copy(z = m.aluRes)

  m.copy(regs = newRegs, phase = Phase.IF)

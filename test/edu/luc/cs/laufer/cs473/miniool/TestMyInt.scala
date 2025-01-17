package edu.luc.cs.laufer.cs473.miniool

import junit.framework.TestCase
import org.scalatest.junit.AssertionsForJUnit

class TestMyInt extends TestCase with AssertionsForJUnit {

/*
 * class myInt {
 *     var value;
 *     init(that) { value = that; }
 *     itimes(that) { // returns an Integer
 *         if (that) {
 *             return value + this.itimes(that - 1);
 *         } else {
 *             return 0;
 *         }
 *     }
 *     plus(that) { // returns a myInt
 *         local result;
 *         result = new myInt;
 *         result.init(value + that);
 *         return result;
 *     }
 *     intValue() { // returns an Integer
 *     		return this.value;
 *     }
 *     minus(that) { // returns a myInt
 *     		local result;
 *     		result = new myInt;
 *     		result.init(this.intvalue() - that)
 *     		return result;
 *     }
 *     uminus() { // returns a myInt
 *     		local result;
 *     		result = new myInt;
 *     		result.init(0 - this.intvalue())
 *     		return result;
 *     }
 *     times(that) { // returns a myInt
 *     		local result;
 *     		result = new myInt;
 *     		result.init(this.itimes(that))
 *     		return result;
 *     }
 *     
 * }
 */

val MyInt: Clazz = new Clazz(
  Seq("value"),
  Seq(
    "init" -> (Seq(),
      Assignment(Selection(Variable("this"), "value"), Variable("0"))),
    "itimes" -> (Seq(),
   	  If(Variable("0"),
		Plus(
		  Selection(Variable("this"), "value"),
		  Message(Variable("this"), "itimes", Minus(Variable("0"), Constant(1)))),
		Constant(0)
      )),
    "plus" -> (Seq("result"),
      Sequence(
    	Assignment(Variable("result"), New(MyInt)),
    	Message(Variable("result"), "init", Plus(Selection(Variable("this"), "value"), Variable("0"))),
    	Variable("result")
    )),
    "intValue" -> (Seq(),
        Sequence(
            Selection(Variable("this"),"value")
    )),
    "minus" -> (Seq("result"),
        Sequence(
            Assignment(Variable("result"), New(MyInt)),
            Message(Variable("result"), "init", Minus(Message(Variable("this"), "intValue"), Variable("0"))),
            Variable("result")        
    )),
    "uminus" -> (Seq("result"),
        Sequence(
            Assignment(Variable("result"), New(MyInt)),
            Message(Variable("result"), "init", Minus(Constant(0), Message(Variable("this"), "intValue"))),
            Variable("result")        
    )),
    "times" -> (Seq("result"),
        Sequence(
            Assignment(Variable("result"), New(MyInt)),
            Message(Variable("result"), "init", Message(Variable("this"), "itimes", Variable("0"))),
            Variable("result")        
    ))
  ))

  /*
   * var u, v, x, y, z;
   */
  val store = Map[String, Cell](
    "u" -> Cell(0),
    "v" -> Cell(0),
    "x" -> Cell(0),
    "y" -> Cell(0),
    "z" -> Cell(0)
  )

  /*
   * x = new myInt;
   * x.init(5);
   * y = x.itimes(7);
   * z = new myInt;
   * z.init(6);
   * u = z.itimes(8);
   * v = z.minus(10).times(4).uminus().times(3).minus(7).intValue(); // your job: see below
   */
  val c = Sequence(
    Assignment(Variable("x"), New(MyInt)),
    Message(Variable("x"), "init", Constant(5)),
    Assignment(Variable("y"), Message(Variable("x"), "itimes", Constant(7))),
    Assignment(Variable("z"), New(MyInt)),
    Message(Variable("z"), "init", Constant(6)),
    Assignment(Variable("u"), Message(Variable("z"), "itimes", Constant(8))),
    Assignment(Variable("v"),
        Message(
            Message(
                Message(
                    Message(
                        Message(
                            Message(
                                Variable("z"), "minus", Constant(10))
                            , "times", Constant(4))
                        , "uminus")
                    , "times", Constant(3))
                , "minus", Constant(7))
            , "intValue")
            )
  )

  def testMain() {
    Execute(store)(c)
    assert(store("y").get.left.get === 35)
    assert(store("u").get.left.get === 48)
    assert(store("v").get.left.get === 41)
  }
}

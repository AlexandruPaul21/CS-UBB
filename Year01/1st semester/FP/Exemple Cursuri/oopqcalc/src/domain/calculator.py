from domain.rational import RationalNumber

class Calculator:
    """
      TAD: Calculator with rational numbers

    """
    def __init__(self):
        self.__calc_total = RationalNumber(0, 1)
        self.__undo_list = []

    def get_total(self):
        """
          return the current total as a rational number
        """
        return self.__calc_total

    def add(self, a, b):
        """
          Add the number a/b to the current total
          a,b integer numbers, b!=0
          raise ValueError if b == 0
          post: current total will be changed
        """
        #store the current total before the add, we need this for the undo
        self.__undo_list.append(self.__calc_total)
        self.__calc_total = self.__calc_total.add (RationalNumber(a, b))

    def clear(self):
        """
          Clear the calculator
          post: current total will be 0/1
        """
        self.__calc_total = RationalNumber(0, 1)
        self.__undo_list = []

    def undo(self):
        """
          Undo the last operation
          post: the last operation is reverted
          raise ValueError if there are no pending operations
        """
        if len(self.__undo_list)==0:
            raise ValueError("Unable to Undo. No pending operations")
        self.__calc_total = self.__undo_list.pop()

def test_calculator_add():
    """
     test function for add
    """
    #create a new calculator instance
    calc = Calculator()
    assert calc.get_total() == RationalNumber(0, 1)
    calc.add(1, 2)
    assert calc.get_total() == RationalNumber(1, 2)
    calc.add(1, 3)
    assert calc.get_total() == RationalNumber(5, 6)
    calc.add(1, 6)
    assert calc.get_total() == RationalNumber(1, 1)
    try:
        calc.add(1, 0)
        assert False
    except ValueError:
        assert True

def testClear():
    """
      Test function for clear calculator
    """
    #create a new calculator instance
    calc = Calculator()
    assert calc.get_total()== RationalNumber(0, 1)

    calc.add(1, 3)
    calc.add(1, 3)
    calc.clear()
    assert calc.get_total()== RationalNumber(0, 1)
    calc.clear()
    assert calc.get_total()== RationalNumber(0, 1)

def testUndo():
    """
      Test function for undo
    """
    calc = Calculator()
    #test case for undo without pending operations
    try:
        calc.undo()
        assert False
    except ValueError:
        assert True
    #test case for undo an operation
    calc.add(1, 3)
    calc.add(1, 3)
    calc.add(1, 3)
    calc.undo()
    assert calc.get_total()== RationalNumber(2, 3)
    calc.undo()
    assert calc.get_total()== RationalNumber(1, 3)
    calc.undo()
    assert calc.get_total()== RationalNumber(0, 1)
    try:
        calc.undo()
        assert False
    except ValueError:
        assert True

    #test case to clarify that after a clear we can not undo
    calc.clear()
    calc.add(1, 3)
    calc.add(1, 3)
    calc.clear()
    try:
        calc.undo()
        assert False
    except ValueError:
        assert True

test_calculator_add()
testClear()
testUndo()

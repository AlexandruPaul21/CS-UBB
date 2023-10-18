"""
   A teacher (client) needs a program for students (users) who learn or use rational numbers.
  The program shall help students to make basic arithmetic operation
"""
from domain.rational import rational_add

def calc_get_total(calc):
    """
      Current total
      domain - calculator
      return a list with 2 elements representing a rational number
    """
    return calc[0]

def undo(calc):
    """
      Undo the last user operation
      domain - calculator
      post: restore the previous current total
    """
    undolist = calc[1]
    if undolist==[]: raise ValueError("No operation to undo")
    calc_total = undolist[-1]
    undolist = undolist[:-1]
    calc[0] = calc_total
    calc[1] = undolist

def calc_add(calc, a, b):
    """
      add a rational number to the current total
      domain - calculator
      a, b integer number, b<>0
      post: add a/b to the current total
    """
    undolist = calc[1]
    calc_total = calc_get_total(calc)
    # add the current total to the undo list
    undolist.append(calc_total)
    # set the current rational number in the domain
    calc[0] = rational_add (calc_total[0], calc_total[1], a, b)
    


def reset_calc():
    """
      Create a new calculator
      post: the curent total equal 0/1
      return calculator
    """
    # we store here the curent total
    calc_total = [0, 1]
    # we store here a history of current totals (for undo)
    undolist = []
    return [calc_total, undolist]
    
# ## test cases

def test_calculator_add():
    """
      Test function for calculator_add
    """
    calc = reset_calc()
    assert calc_get_total(calc) == [0, 1]
    calc_add(calc, 1, 2)
    assert calc_get_total(calc) == [1, 2]
    calc_add(calc, 1, 3)
    assert calc_get_total(calc) == [5, 6]
    calc_add(calc, 1, 6)
    assert calc_get_total(calc) == [1, 1]

def test_undo():
    """
      Test function for undo
    """
    calc = reset_calc()
    calc_add(calc, 1, 3)
    undo(calc)
    assert calc_get_total(calc) == [0, 1]
    calc = reset_calc()
    calc_add(calc, 1, 3)
    calc_add(calc, 1, 3)
    calc_add(calc, 1, 3)
    undo(calc)
    assert calc_get_total(calc) == [2, 3]


# run the test - invoke the test function

test_calculator_add()
test_undo()



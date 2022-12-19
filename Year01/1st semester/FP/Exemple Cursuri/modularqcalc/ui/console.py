from domain.calculator import calc_get_total, reset_calc, calc_add, undo

def addToCalc(calc):
    """
      Read a rational number and add to the current total
      domain - calculator
    """
    m = input("Give nominator:")
    n = input("Give denominator:")
    try:
        calc_add (calc, int(m), int(n))
    except ValueError:
        print ("Enter integers for m, n, with not null n")

def undoCalc(calc):
    try:
        undo(calc)
    except ValueError as e:
        print (e)



def printMenu():
    """
      Print out the main menu of the calculator
    """
    print ("Calculator menu:")
    print ("   + for adding a rational number")
    print ("   c to clear the calculator")
    print ("   u to undo the last operation")
    print ("   x to close the calculator")

def printCurrent(calc):
    """
      Print the current total
      domain - calculator
    """
    print ("Total:", calc_get_total(calc))
    
def run():
    """
      Implement the user interface
    """
    calc = reset_calc()
    finish = False    
    while not finish:
        printCurrent(calc)
        printMenu()
        m = input().strip()
        if (m == 'x'):
            finish = True
        elif (m == '+'):
            addToCalc(calc)
        elif (m == 'c'):
            calc = reset_calc()
        elif (m == 'u'):
            undoCalc(calc)
        else:
            print ("Invalid command")

    print ("By!!!")

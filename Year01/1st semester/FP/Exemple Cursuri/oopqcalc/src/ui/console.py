
class Console:
    """
     Implement the user interface
    """
    def __init__(self,calc):
        """
          Initialise the UI
          calc - a calculator instance
        """
        self.calc = calc
        self.__initCommands()

    def __initCommands(self):
        """
          Initialise the command - function pairs
        """
        self.__dict = {}
        self.__dict["+"] = self.__addOperations
        self.__dict["c"] = self.__clearCalc
        self.__dict["u"] = self.__undoCalc

    def __addOperations(self):
        """
          read a rational number and add the number to the calculator
          raise ValueError if the user do not introduce numbers
          raise ValueError if the denominator is 0
        """
        a = input("Give a:").strip()
        b = input("Give b:").strip()
        self.calc.add(int(a),int(b))

    def __clearCalc(self):
        """
          Clear the calculator
        """
        self.calc.clear()

    def __undoCalc(self):
        """
          Undo the last operations
        """
        self.calc.undo()

    def __readCommand(self):
        """
          Print the menu and read a command
          return c - a string representing th user command
        """
        print ("""
        Menu:
        x - to exit
        + - to add a rational number
        c - to clear
        u - to undo
        """)
        return input("Give a command:").strip()

    def run(self):
        """
          Start the UI
        """
        while True:
            print ("Total:",self.calc.get_total())
            c = self.__readCommand()
            if (c == 'x'):
                break            
            try:
                f = self.__dict[c]
                f()
            except KeyError:
                print ("Unknown command:",c)
            except ValueError as msg:
                print (msg)

        print ("By...")

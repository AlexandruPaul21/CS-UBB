import ui.console
import domain.calculator

calc = domain.calculator.Calculator()
ui = ui.console.Console(calc)
ui.run()
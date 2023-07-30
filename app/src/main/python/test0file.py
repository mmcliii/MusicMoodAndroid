import numpy
def myFunc(number1, number2):
    num1 = int (number1)
    num2 = int (number2)
    sum = num1 + num2
    return "sum of the numbers" + str (sum)

def myFunc1():
    out = numpy.zeros(5)
    return "Zero array on numpy: " + str (out)
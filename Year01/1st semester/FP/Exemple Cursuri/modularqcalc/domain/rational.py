'''
Created on Oct 24, 2016

@author: istvan
'''
from utils.numericlib import gcd


def rational_add(a1, a2, b1, b2):
    """
    Return the sum of two rational numbers.
    a1,a2,b1,b2 integer numbers, a2<>0 and b2<>0
    return a list with 2 integer numbers, representing a rational number a1/b2 + b1/b2
    Raise ValueError if the denominators are zero.
    """
    if a2 == 0 or b2 == 0:
        raise ValueError("0 denominator not allowed")
    c = [a1 * b2 + a2 * b1, a2 * b2]
    d = gcd(c[0], c[1])
    c[0] = c[0] // d
    c[1] = c[1] // d
    return c
        

def test_rational_add():
    """
      Test function for rational_add
    """
    assert rational_add(1, 2, 1, 3) == [5, 6]
    assert rational_add(1, 2, 1, 2) == [1, 1]    
    assert rational_add(0, 1, 1, 2) == [1, 2]
    assert rational_add(1, 3, 0, 1) == [1, 3]
    assert rational_add(1, 3, -1, 3) == [0, 1]    


test_rational_add()
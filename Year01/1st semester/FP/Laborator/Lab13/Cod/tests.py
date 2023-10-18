from main import is_collinear
import unittest

class GenTest(unittest.TestCase):
    def test_is_collinear(self):
        self.assertTrue(is_collinear([1,2],[2,4],[3,6]))
        self.assertFalse(is_collinear([1,5],[2,4],[3,6]))

import unittest

from utils.utils import getYear_from_CNP,getMonth_from_CNP,getDay_from_CNP,bubble_sort,shell_sort

class UtilsTest(unittest.TestCase):
    def test_dates_from_CNP(self):
        #CNP = [5, 0, 2, 0, 4, 2, 1, 3, 1, 4, 0, 0, 2]
        CNP= [2,0,0,4,1,3,1,2,4,0,2,0,5]
        self.assertEqual(getYear_from_CNP(CNP),2)
        self.assertEqual(getMonth_from_CNP(CNP),4)
        self.assertEqual(getDay_from_CNP(CNP),21)

        #CNP = [5, 1, 2, 1, 0, 0, 1, 3, 1, 4, 0, 0, 2]
        CNP= [2,0,0,4,1,3,1,0,0,1,2,1,5]
        self.assertEqual(getYear_from_CNP(CNP),12)
        self.assertEqual(getMonth_from_CNP(CNP),10)
        self.assertEqual(getDay_from_CNP(CNP),1)

    def test_bubble_sort(self):
        the_list=[1,3,5,6,7,4,3,2]
        bubble_sort(the_list)
        self.assertEqual(the_list,[1,2,3,3,4,5,6,7])

        the_list=[1,3,5,6,7,4,3,2]
        bubble_sort(the_list,reverse=True)
        self.assertEqual(the_list,[7,6,5,4,3,3,2,1])

        the_list=[[1,0],[3,5],[5,7],[6,8],[7,9],[4,3],[3,5],[2,1]]
        bubble_sort(the_list,key=lambda x: x[0],reverse=True)
        self.assertEqual(the_list,[[7,9],[6,8],[5,7],[4,3],[3,5],[3,5],[2,1],[1,0]])

    def test_shell_sort(self):
        the_list=[1,3,5,6,7,4,3,2]
        shell_sort(the_list)
        self.assertEqual(the_list,[1,2,3,3,4,5,6,7])

        the_list=[1,3,5,6,7,4,3,2]
        shell_sort(the_list,reverse=True)
        self.assertEqual(the_list,[7,6,5,4,3,3,2,1])

        the_list=[[1,0],[3,5],[5,7],[6,8],[7,9],[4,3],[3,5],[2,1]]
        shell_sort(the_list,key=lambda x: x[0],reverse=True)
        self.assertEqual(the_list,[[7,9],[6,8],[5,7],[4,3],[3,5],[3,5],[2,1],[1,0]])


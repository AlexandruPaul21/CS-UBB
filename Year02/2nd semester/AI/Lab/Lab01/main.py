from problems import *

if __name__ == '__main__':
    # 1
    s = "Ana are mere rosii si galbene"
    if lastLexi(s) == "si":
        print("Pb01: passed")
    else:
        print("Pb01: failed")

    # 2
    x = [1, 5]
    y = [4, 1]
    if euclidDistance(x, y) == 5.0:
        print("Pb02: passed")
    else:
        print("Pb02: failed")

    # 3
    x = [1, 0, 2, 0, 3]
    y = [1, 2, 0, 3, 1]
    if dotProd(x, y) == 4:
        print("Pb03: passed")
    else:
        print("Pb03: failed")

    # 4
    x = "ana are ana are mere rosii ana"
    if notDuplicates(x) == ["mere", "rosii"]:
        print("Pb04: passed")
    else:
        print("Pb04: failed")

    # 5
    x = [1, 2, 3, 4, 2]
    if getDuplicate(x) == 2:
        print("Pb05: passed")
    else:
        print("Pb05: failed")

    # 6
    x = [2, 8, 7, 2, 2, 5, 2, 3, 1, 2, 2]
    if majElement(x) == 2:
        print("Pb06: passed")
    else:
        print("Pb06: failed")

    # 7
    x = [7, 4, 6, 3, 9, 1]
    if kthElement(x, 2) == 7:
        print("Pb07: passed")
    else:
        print("Pb07: failed")

    # 8
    if solve(4) == ['0', '1', '10', '11', '100']:
        print("Pb08: passed")
    else:
        print("Pb08: failed")

    # 9
    matrix = [[0, 2, 5, 4, 1],
              [4, 8, 2, 3, 7],
              [6, 3, 4, 6, 2],
              [7, 3, 1, 8, 3],
              [1, 5, 7, 9, 4]]
    sumMat = getMatrixSum(matrix)
    if query(sumMat, (2, 2), (4, 4)) == 44 and query(sumMat, (1, 1), (3, 3)) == 38:
        print("Pb09: passed")
    else:
        print("Pb09: failed")

    # 10
    matrix = [[0, 0, 0, 1, 1],
              [0, 1, 1, 1, 1],
              [0, 0, 1, 1, 1]]

    if getMostPopulatedLine(matrix) == 2:
        print("Pb10: passed")
    else:
        print("Pb10: failed")

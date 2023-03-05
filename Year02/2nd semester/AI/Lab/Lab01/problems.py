import math
import copy


def lastLexi(phrase):  # pb1
    words = phrase.split(" ")
    last = ""
    if len(words) > 0:
        last = words[0]

    for word in words:
        if word.lower() > last:
            last = word.lower()
    return last


def euclidDistance(x, y) -> float:  # pb2
    first = x[0] - y[0]
    second = x[1] - y[1]
    return math.sqrt((first * first) + (second * second))


def dotProd(x, y):  # pb3
    return sum(a * b for a, b in zip(x, y))


def notDuplicates(phrase):  # pb4
    words = phrase.split(" ")
    words.sort()
    words.append("")

    ans = []
    i = 0
    while i < len(words) - 1:
        if words[i] == words[i + 1]:
            while words[i] == words[i + 1] and i < len(words) - 1:
                i += 1
        else:
            ans.append(words[i])
        i += 1

    return ans


def getDuplicate(lst):  # pb5
    n = len(lst)
    expectedSum = (n * (n - 1)) / 2
    actualSum = sum(lst)

    return actualSum - expectedSum


def majElement(lst) -> int:  # pb6
    cnt = 0
    el = 0
    for elem in lst:
        if cnt == 0:
            el = elem
            cnt = 1
        else:
            if elem == el:
                cnt += 1
            else:
                cnt -= 1
    return el


def kthElement(lst, k):  # pb7
    lst.sort()
    for el in reversed(lst):
        if k == 1:
            return el
        k -= 1


def binaryIncrement(number):  # pb8
    if not number:
        return [0]
    if number[0] == 1:
        t = 1
        number[0] = 0
        for i in range(1, len(number)):
            number[i] += t
            t = number[i] // 2
            number[i] %= 2
        if t != 0:
            number.append(1)
    else:
        number[0] = 1

    return number


def getBinary(nr):
    res = []
    while nr != 0:
        res.append(nr % 2)
        nr //= 2

    return res


def formatBin(lst):
    res = ""
    for el in reversed(lst):
        res += str(el)

    return res


def solve(n):
    res = []
    binN = getBinary(n)
    act = []
    while act != binN:
        act = binaryIncrement(act)
        res.append(copy.deepcopy(act))

    ans = [formatBin(x) for x in res]

    return ans


def getMatrixSum(matrix):  # pb9
    res = []
    for i in range(len(matrix)):
        aux = []
        for j in range(len(matrix)):
            aux.append(0)
        res.append(aux)

    for i in range(len(matrix)):
        for j in range(len(matrix)):
            if i == 0 and j == 0:
                res[i][j] = matrix[i][j]
            elif i == 0:
                res[i][j] = res[i][j - 1] + matrix[i][j]
            elif j == 0:
                res[i][j] = res[i - 1][j] + matrix[i][j]
            else:
                res[i][j] = res[i - 1][j] + res[i][j - 1] - res[i - 1][j - 1] + matrix[i][j]

    return res


def query(mat, p, q):
    return mat[q[0]][q[1]] - mat[q[0]][p[1] - 1] - mat[p[0] - 1][q[1]] + mat[p[0] - 1][p[1] - 1]


def getMostPopulatedLine(matrix):
    index = 1
    maximum = -1
    savedIndex = -1
    for line in matrix:
        no1 = sum(line)
        if no1 > maximum:
            savedIndex = index
            maximum = no1
        index += 1

    return savedIndex

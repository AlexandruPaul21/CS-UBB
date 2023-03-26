import sys

INF = sys.maxsize


def readGraphFromFile(fileName):
    f = open(fileName, "r")
    n = int(f.readline())
    matrix = []
    for i in range(n):
        matrix.append([])
        line = f.readline()
        elems = line.split(",")
        for j in range(n):
            matrix[-1].append(int(elems[j]))
    noEdges = 0
    for i in range(n):
        for j in range(n):
            if matrix[i][j] != 0:
                if j > i:
                    noEdges += 1
            else:
                matrix[i][j] = INF
    graph = {'noNodes': n,
             'matrix': matrix,
             'noEdges': noEdges,
             'source': int(f.readline()),
             'destination': int(f.readline())}
    f.close()
    return graph


def calculateDistance(graph, path):
    matrix = graph['matrix']
    length = 0
    for index in range(len(path) - 1):
        length += matrix[path[index] - 1][path[index + 1] - 1]
    return length


def calculateDistanceCyclic(graph, path):
    matrix = graph['matrix']
    length = 0
    for index in range(len(path) - 1):
        length += matrix[path[index] - 1][path[index + 1] - 1]

    length += matrix[path[0] - 1][path[-1] - 1]
    return length


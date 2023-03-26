import networkx as nx
import numpy as np
import matplotlib.pyplot as plt
from random import uniform


def readNetworkFromGml(file):
    gmlNet = nx.read_gml(file, label="id")
    mat = nx.adj_matrix(gmlNet)
    matrix = [[0] * len(gmlNet.nodes) for _ in range(len(gmlNet.nodes))]
    tmp = mat.nonzero()
    for i in range(len(tmp[0])):
        matrix[tmp[0][i]][tmp[1][i]] = 1
    net = {
        'noNodes': len(gmlNet.nodes),
        'mat': matrix,
        'noEdges': len(gmlNet.edges),
        'degrees': [degree[1] for degree in gmlNet.degree()]
    }

    return net


def plotNetwork(net, com=None):
    if com is None:
        com = [1] * net['noNodes']
    np.random.seed(123)
    A = np.matrix(net["mat"])
    G = nx.from_numpy_matrix(A)
    pos = nx.spring_layout(G)
    plt.figure(figsize=(5, 5))
    nx.draw_networkx_nodes(G, pos, node_size=50, cmap=plt.cm.cool, node_color=com)
    nx.draw_networkx_edges(G, pos, alpha=0.2)
    plt.show()


def modularity(communities, param):
    noNodes = param['noNodes']
    mat = param['mat']
    degrees = param['degrees']
    noEdges = param['noEdges']
    M = 2 * noEdges
    Q = 0.0
    for i in range(0, noNodes):
        for j in range(0, noNodes):
            if communities[i] == communities[j]:
                Q += (mat[i][j] - degrees[i] * degrees[j] / M)
    return Q * 1 / M


def generateNewValue(lim1, lim2):
    return uniform(lim1, lim2)


def binToInt(x):
    val = 0
    # x.reverse()
    for bit in x:
        val = val * 2 + bit
    return val


# Diferenta dintre modularitatea simpla si aceasta este faptul ca aceasta incearca sa gaseasca si comunitatile mici
# din comunitatile mari. Parametrul myLambda, care este default 0.5, ne spune cat de dens este graful:  0 - nu exista
# muchii, 1 - e graf complet.
def modularity_density(communities, param, myLambda=0.5):
    G = nx.from_numpy_matrix(np.matrix(param['mat']))
    my_communities = [[] for _ in range(param['noNodes'])]
    for i in range(param['noNodes']):
        my_communities[communities[i] - 1].append(i)
    Q = 0.0
    for community in my_communities:
        sub = nx.subgraph(G, community)
        sub_n = sub.number_of_nodes()
        interior_degrees = []
        exterior_degrees = []
        for node in sub:
            interior_degrees.append(sub.degree(node))
            exterior_degrees.append(G.degree(node) - sub.degree(node))
        try:
            Q += (1 / sub_n) * (
                        (2 * myLambda * np.sum(interior_degrees)) - (2 * (1 - myLambda) * np.sum(exterior_degrees)))
        except ZeroDivisionError:
            pass
    return Q


# Incearca, de asemenea, sa evite limita de rezolutie, gasind si comunitatile mici din comunitatile mari.
# Se bazeaza pe distributia Student - se calculeaza raritatea statistica a unei comunitati.
def z_modularity(communities, param):
    G = nx.from_numpy_matrix(np.matrix(param['mat']))
    my_communities = [[] for _ in range(param['noNodes'])]
    for i in range(param['noNodes']):
        my_communities[communities[i] - 1].append(i)
    edges = G.number_of_edges()
    Q = 0.0
    mmc = 0
    dc2m = 0
    for community in my_communities:
        sub = nx.subgraph(G, community)
        sub_n = sub.number_of_nodes()
        dc = 0
        for node in sub:
            dc += G.degree(node)
        mmc = sub_n / edges
        dc2m += (dc / (2 * edges)) ** 2
    try:
        Q = (mmc - dc2m) / np.sqrt(dc2m * (1 - dc2m))
    except ZeroDivisionError:
        pass
    return Q

import os
import networkx as nx
import numpy as np
import matplotlib.pyplot as plt
import warnings

warnings.simplefilter("ignore")


# teachers notes
def greedyCommunitiesDetectionByTool(net):
    # Input: a graph
    # Output: list of community index (for every node)

    from networkx.algorithms import community

    A = np.matrix(net["mat"])
    G = nx.from_numpy_matrix(A)
    communitiesGenerator = community.girvan_newman(G)
    topLevelCommunities = next(communitiesGenerator)
    sorted(map(sorted, topLevelCommunities))
    com = [0 for _ in range(net['noNodes'])]
    index = 1
    for community in sorted(map(sorted, topLevelCommunities)):
        for node in community:
            com[node] = index
        index += 1
    return com


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


def fitness(G):
    edges = list(nx.edge_betweenness_centrality(G).items())  # [(edge, Q)]
    killEdge = max(edges, key=lambda item: item[1])[0]
    return killEdge


def greedyCommunitiesDetection(net, expected):
    # Input: a graph
    # Output: list of community index (for every node)

    A = np.matrix(net["mat"])
    G = nx.from_numpy_matrix(A)
    while len(list(nx.connected_components(G))) < expected:
        source, destination = fitness(G)
        G.remove_edge(source, destination)

    com = [1] * net['noNodes']
    color = 0
    for community in nx.connected_components(G):
        color += 1
        for node in community:
            com[node] = color
    return com


def tests():
    crtDir = os.getcwd()
    print("Dolphins")
    path = os.path.join(crtDir, 'data/real', 'dolphins.gml')
    net = readNetworkFromGml(path)
    assert (getDictFromComArray(greedyCommunitiesDetection(net, 2)) == getDictFromComArray(
        greedyCommunitiesDetectionByTool(net)))
    print("Karate")
    path = os.path.join(crtDir, 'data/real', 'karate.gml')
    net = readNetworkFromGml(path)
    assert (getDictFromComArray(greedyCommunitiesDetection(net, 2)) == getDictFromComArray(
        greedyCommunitiesDetectionByTool(net)))
    print("Krebs")
    path = os.path.join(crtDir, 'data/real', 'krebs.gml')
    net = readNetworkFromGml(path)
    assert (getDictFromComArray(greedyCommunitiesDetection(net, 2)) == getDictFromComArray(
        greedyCommunitiesDetectionByTool(net)))
    print("Football")
    path = os.path.join(crtDir, 'data/real', 'football.gml')
    net = readNetworkFromGml(path)
    assert (getDictFromComArray(greedyCommunitiesDetection(net, 2)) == getDictFromComArray(
        greedyCommunitiesDetectionByTool(net)))
    print("Les Miserables")
    path = os.path.join(crtDir, 'data/real', 'lesmis.gml')
    net = readNetworkFromGml(path)
    assert (getDictFromComArray(greedyCommunitiesDetection(net, 2)) == getDictFromComArray(
        greedyCommunitiesDetectionByTool(net)))
    print("Nouns")
    path = os.path.join(crtDir, 'data/real', 'adjnoun.gml')
    net = readNetworkFromGml(path)
    assert (getDictFromComArray(greedyCommunitiesDetection(net, 2)) == getDictFromComArray(
        greedyCommunitiesDetectionByTool(net)))
    print("NetScience")
    path = os.path.join(crtDir, 'data/real', 'netscience.gml')
    net = readNetworkFromGml(path)
    assert (getDictFromComArray(greedyCommunitiesDetection(net, 397)) == getDictFromComArray(
        greedyCommunitiesDetectionByTool(net)))
    print("PolBooks")
    path = os.path.join(crtDir, 'data/real', 'polbooks.gml')
    net = readNetworkFromGml(path)
    assert (getDictFromComArray(greedyCommunitiesDetection(net, 2)) == getDictFromComArray(
        greedyCommunitiesDetectionByTool(net)))
    print("Power")
    path = os.path.join(crtDir, 'data/real', 'power.gml')
    net = readNetworkFromGml(path)
    assert (getDictFromComArray(greedyCommunitiesDetection(net, 25)) == getDictFromComArray(
        greedyCommunitiesDetectionByTool(net)))
    print("MAP")
    path = os.path.join(crtDir, 'data/real', 'map.gml')
    net = readNetworkFromGml(path)
    assert (getDictFromComArray(greedyCommunitiesDetection(net, 2)) == getDictFromComArray(
        greedyCommunitiesDetectionByTool(net)))


def getDictFromComArray(com):
    result = {}
    for color in com:
        if color in result.keys():
            result[color] += 1
        else:
            result[color] = 1
    # print(result)
    return result


if __name__ == "__main__":
    #tests()
    network = readNetworkFromGml("data/real/dolphins.gml")
    communities = greedyCommunitiesDetection(network, 2)
    plotNetwork(network, communities)

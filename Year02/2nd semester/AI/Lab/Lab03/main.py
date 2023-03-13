import random
import warnings
from utils import *
from Chromosome import Chromosome

warnings.simplefilter("ignore")


def gAlgorithm(network, n0Communities):
    problemParameters = {
        "min": 1,
        "max": n0Communities,
        "noDim": network["noNodes"],
        "net": network,
        "function": modularity
    }
    noChromosomes = network["noNodes"]
    chromosomes = []
    for _ in range(noChromosomes):
        ch = Chromosome(problemParameters)
        ch.fitness = problemParameters["function"](ch.repres, network)
        chromosomes.append(ch)

    repeat = 100
    for _ in range(repeat):
        initLen = len(chromosomes)
        for _ in range(initLen):
            a = random.randint(0, initLen - 1)
            b = random.randint(0, initLen - 1)
            ch = chromosomes[a].crossover(chromosomes[b])
            chromosomes.append(ch)

        for ch in chromosomes:
            ch.mutation()

        chromosomes = sorted(chromosomes, key=lambda x: x.fitness, reverse=True)[:initLen]

    ch = max(chromosomes, key=lambda x: x.fitness)
    # print(ch)
    plotNetwork(network, ch.repres)


if __name__ == "__main__":
    net = readNetworkFromGml("data/real/power.gml")
    gAlgorithm(net, 2)

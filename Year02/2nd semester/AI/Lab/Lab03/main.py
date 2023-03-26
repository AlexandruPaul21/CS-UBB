import random
import warnings
from utils import *
from Chromosome import Chromosome
from statistics import mean

warnings.simplefilter("ignore")


def ga(network, no_communities):
    problem_parameters = {
        "min": 1,
        "max": no_communities,
        "noDim": network["noNodes"],
        "net": network,
        "function": modularity
    }
    noChromosomes = network["noNodes"]
    chromosomes = []
    for _ in range(noChromosomes):
        ch = Chromosome(problem_parameters)
        ch.fitness = problem_parameters["function"](ch.repres, network)
        chromosomes.append(ch)

    generations = 1000
    averageValues = []
    minimumValues = []
    maximumValues = []
    yPoints = []
    for i in range(generations):
        initLen = len(chromosomes)
        for _ in range(initLen):
            a = random.randint(0, initLen - 1)
            b = random.randint(0, initLen - 1)
            ch = chromosomes[a].crossover(chromosomes[b])
            chromosomes.append(ch)

        for ch in chromosomes:
            ch.mutation()

        chMax = max(chromosomes, key=lambda x: x.fitness)
        chMin = min(chromosomes, key=lambda x: x.fitness)
        chroms = [x.fitness for x in chromosomes]
        chAvg = mean(chroms)

        yPoints.append(i)
        maximumValues.append(chMax.fitness)
        minimumValues.append(chMin.fitness)
        averageValues.append(chAvg)

        # print(chMin.fitness, chMax.fitness, chAvg)

        chromosomes = sorted(chromosomes, key=lambda x: x.fitness, reverse=True)[:initLen]

    import matplotlib.pyplot as plt

    plt.plot(yPoints, minimumValues, label="Minimum")
    plt.plot(yPoints, averageValues, label="Average")
    plt.plot(yPoints, maximumValues, label="Maximum")
    plt.legend()
    plt.show()

    ch = max(chromosomes, key=lambda x: x.fitness)
    # print(ch)
    plotNetwork(network, ch.repres)


if __name__ == "__main__":
    net = readNetworkFromGml("data/real/dolphins.gml")
    ga(net, 2)

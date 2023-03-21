
from random import *


class Chromosome:
    def __init__(self, problParam=None):
        self.__problParam = problParam
        possible = [i for i in range(problParam['min'], problParam['max'] + 1)]
        self.__repres = [choice(possible) for _ in range(problParam['noDim'])]
        self.__fitness = 0.0

    @property
    def repres(self):
        return self.__repres

    @property
    def fitness(self):
        return self.__fitness

    @repres.setter
    def repres(self, l=[]):
        if l:
            self.__fitness = self.__problParam["function"](l, self.__problParam["net"])
        self.__repres = l

    @fitness.setter
    def fitness(self, fit=0.0):
        self.__fitness = fit

    def crossover(self, c):
        r = randint(0, len(self.__repres) - 1)
        newrepres = []
        for i in range(r):
            newrepres.append(self.__repres[i])
        for i in range(r, len(self.__repres)):
            newrepres.append(c.__repres[i])
        offspring = Chromosome(c.__problParam)
        offspring.repres = newrepres
        return offspring

    def mutation(self):
        pos = randint(0, len(self.__repres) - 1)
        possible = [i for i in range(self.__problParam['min'], self.__problParam['max'] + 1)]
        self.__repres[pos] = choice(possible)
        self.__fitness = self.__problParam["function"](self.__repres, self.__problParam['net'])

    def __str__(self):
        return '\nChromo: ' + str(self.__repres) + ' has fit: ' + str(self.__fitness)

    def __repr__(self):
        return self.__str__()

    def __eq__(self, c):
        return self.__repres == c.__repres and self.__fitness == c.__fitness
from random import randint, uniform


class Chromosome:
    def __init__(self, problem_param=None):
        self.__problem_parameter = problem_param
        self.__representation = [uniform(problem_param['min'], problem_param['max']) for _ in
                                 range(problem_param['noDim'])]
        self.__fitness = 0.0

    @property
    def representation(self):
        return self.__representation

    @property
    def fitness(self):
        return self.__fitness

    @representation.setter
    def representation(self, representation=None):
        if representation is None:
            representation = []
        self.__representation = representation

    @fitness.setter
    def fitness(self, fit=0.0):
        self.__fitness = fit

    def crossover(self, c):
        r = randint(0, len(self.__representation) - 1)
        new_representation = []
        for i in range(r):
            new_representation.append(self.__representation[i])
        for i in range(r, len(self.__representation)):
            new_representation.append(c.__representation[i])
        offspring = Chromosome(c.__problem_parameter)
        offspring.representation = new_representation
        return offspring

    def mutation(self):
        pos = randint(0, len(self.__representation) - 1)
        self.__representation[pos] = uniform(self.__problem_parameter['min'], self.__problem_parameter['max'])

    def __str__(self):
        return 'Chromo: ' + str(self.__representation) + ' has fit: ' + str(self.__fitness)

    def __repr__(self):
        return self.__str__()

    def __eq__(self, c):
        return self.__representation == c.__representation and self.__fitness == c.__fitnes

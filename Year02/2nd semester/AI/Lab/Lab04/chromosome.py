from random import randint, uniform


class Chromosome:
    def __init__(self, problem_param=None):
        self.__problem_parameter = problem_param
        self.__representation = []
        self.__fitness = 0.0
        self._init_representation()

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
        if len(self.__representation) == 2 or len(c.__representation) == 2:
            offspring = Chromosome(c.__problem_parameter)
            offspring.representation = c.__representation
            return offspring

        similarities = []
        for index in range(min(len(self.__representation), len(c.representation))):
            if self.__representation[index] == c.__representation[index]:
                similarities.append(index)

        cut_index = randint(0, len(similarities) - 1)
        cut = similarities[cut_index]

        new_representation = self.__representation[:cut + 1]
        for index in range(cut + 1, len(c.__representation)):
            if c.__representation[index] not in new_representation:
                new_representation.append(c.__representation[index])

        child = Chromosome(self.__problem_parameter)
        child.__representation = new_representation

        return child

    def mutation(self):
        if len(self.__representation) == 2:
            return

        index = randint(1, len(self.__representation) - 2)
        before = self.__representation[index - 1]
        after = self.__representation[index + 1]
        used = self.__representation[:index] + self.__representation[index + 2:]
        new_representation = [before]
        while new_representation[-1] != after:
            new_node = randint(1, self.__problem_parameter['noNodes'])
            while new_node in used:
                new_node = randint(1, self.__problem_parameter['noNodes'])
            new_representation.append(new_node)
            used.append(new_node)
        self.__representation = self.__representation[:index - 1] + new_representation + self.__representation[index + 2:]

    def __str__(self):
        return 'Chromosome: ' + str(self.__representation) + ' has fit: ' + str(self.__fitness)

    def __repr__(self):
        return self.__str__()

    def __eq__(self, c):
        return self.__representation == c.__representation and self.__fitness == c.__fitness

    def _init_representation(self):
        self.__representation.append(self.__problem_parameter["source"])
        while self.__representation[-1] != self.__problem_parameter["destination"]:
            newNode = randint(1, self.__problem_parameter["noNodes"])
            while newNode in self.__representation:
                newNode = randint(1, self.__problem_parameter["noNodes"])
            self.__representation.append(newNode)

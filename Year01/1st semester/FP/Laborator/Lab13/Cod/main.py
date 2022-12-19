import copy
from copy import deepcopy


def is_collinear(x, y, z):
    return (z[1] - y[1]) * (x[0] - y[0]) == (z[0] - y[0]) * (x[1] - y[1])
    # yc-yb/ya-yb=xc-xb/xa-xb


def isSet(lst1):
    if sorted(lst1)!=lst1:
        return False
    lst = copy.deepcopy(lst1)
    lst.sort()
    prec = lst[0]
    lst.pop(0)
    for elem in lst:
        if prec == elem:
            return False
        prec = elem
    return True


def bkt(sol, dim, lst):
    if len(sol) > 2 and isSet(sol):
        found = False
        for index1 in range(len(sol)):
            for index2 in range(len(sol)):
                for index3 in range(len(sol)):
                    if index1 != index2 and index2 != index3 and index1 != index3:
                        if is_collinear(lst[sol[index1]], lst[sol[index2]], lst[sol[index3]]):
                            found = True
                            break
        if found:
            print("[", end="")
            for elem in sol:
                print(lst[elem], end=" ")
            print("]")

    if len(sol) > dim:
        return

    sol.append(0)
    for index in range(0,dim):
        sol[-1] = index
        bkt(sol, dim, lst)
    sol.pop()

def backIter(dim,lst):
    sol=[-1]
    while len(sol)>0:
        found = False
        while not found and sol[-1]<dim-1:
            sol[-1]=sol[-1]+1
            if isSet(sol):
                found = True
        if found:
            if len(sol)>2 and len(sol)<=dim:
                found = False
                for index1 in range(len(sol)):
                    for index2 in range(len(sol)):
                        for index3 in range(len(sol)):
                            if index1 != index2 and index2 != index3 and index1 != index3:
                                if is_collinear(lst[sol[index1]], lst[sol[index2]], lst[sol[index3]]):
                                    found = True
                                    break
                if found:
                    print("[", end="")
                    for elem in sol:
                        print(lst[elem], end=" ")
                    print("]")
            sol.append(-1)
        else:
            sol = sol[:-1]

def main():
    nr_of_points = int(input("Introduceti numarul de puncte: "))
    point_list = []
    print("Introduceti coordonatele punctelor: ")
    for index in range(nr_of_points):
        x, y = [int(token.strip()) for token in input().strip().split(" ")]
        point_list.append([x, y])

    #bkt([], nr_of_points, point_list)
    backIter(nr_of_points,point_list)


if __name__ == '__main__':
    main()

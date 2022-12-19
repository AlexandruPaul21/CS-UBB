from termcolor import colored
from utils.utils import fill_the_list,make_list_copy,make_dic_copy

def print_colored(print_object,color):
    """
    Printeaza un anumit text cu o anumita culoare
    :param print_object: string
    :param color: string
    :return: none
    """
    print(colored(print_object,color))

def get_answer():
    """
    Functia preia inputul utilizatorului si il transforma intr-o lista
    :return: lista de raspunsuri
    :rtype: list
    """
    options_as_string=input("Introduceti optiunea dvs.(numerele trebuie sa fie separate prin punct ex. 1.1):")
    options_as_numeric_list=[]
    options_as_numeric_list=[int(elem) for elem in options_as_string.split(".")]
    return options_as_numeric_list
def first_option(list):
    """
    Functia returneaza prima optiune a utilizatorului
    :param list: lista de raspunsuri
    :return: prima optiune
    :rtype: int
    """
    return list[0]
def second_option(list):
    """
    Functia returneaza a doua optiune a utilizatorului
    :param list: lista de raspunsuri
    :return: a doua optiune
    :rtype: int
    """
    return list[1]

#1 zone
def add_new_score(dict,id,score):
    """
    Add a new element to a dict
    :param dict: dictionarul in care adaugam
    :param id: id-ul la care adaugam
    :param score: scorul
    :return: dictionarul
    """
    dict[str(id)]=[score]
    return dict

#2 zone
def delete_by_id(the_list,id_del,the_undo_list):
    """
    Sterge concurentul cu id-ul id_del
    :param the_list: liste concurentilor
    :param id_del: id-ul concurentului de sters
    :return: lista modificata
    :rtype: list
    """
    the_undo_list.append(make_dic_copy(the_list))
    del the_list[str(id_del)]
    the_list[str(id_del)]=[]
    return the_list
def delete_by_id_interval(the_list,id1,id2,the_undo_list):
    """
    Sterge din lista de concurenti pe cei care au id-ul intre doua valori date
    :param the_list: lista in care trebuie efectuata stergerea
    :param id1: capatul stang al intervalului
    :param id2: capatul drept al intervalului
    :return: lista obtinuta dupa efectuarea operatiei
    :rtype: list
    """
    the_undo_list.append(the_list)
    for index in range(id1,id2+1):
        del the_list[str(index)]
        the_list[str(index)]=[]
    return the_list
def update_score(the_list,id_upd,task_index,new_score,the_undo_list):
    """
    Actualizeaza scorul elevlui dat la o pb data
    :param the_list: lista concurenti
    :param id_upd: id-ul concurentului
    :param task_index: numarul problemei
    :param new_score: noul scor
    :return: lista modificata
    :rtype: list
    """
    the_undo_list.append(make_dic_copy(the_list))
    the_list[str(id_upd)][task_index-1]=new_score
    return the_list

#3 zone
def get_sum(the_list,id):
    """
    Calculeaza suma scorurilor intermediare ale unui concurent
    :param the_list: lista de concurenti
    :param id: id-ul concurentului
    :return: suma obtinuta
    :rtype: int
    """
    sum=0 #id-ul este primul element al listei, deci va fi adunat sumei, fara motiv
    for elem in the_list[str(id)]:
        sum+=elem
    return sum
def lower_than(the_list,value,upper_limit):
    """
    Functia returneaza o lista care contine perechei de forma [id,scor_total] care au scorul total<value
    :param the_list: lista in care se cauta
    :param value: valoarea de comparatie
    :param upper_limit: limita superioara a id-ului
    :return: lista de rezultate
    :rtype: list
    """
    actual_sum=0
    result_list=[]
    for index in range(1000,upper_limit):
        actual_sum=get_sum(the_list,index)
        if actual_sum<value:
            result_list.append([index,actual_sum])
    return result_list
def sort_participants(the_list,upper_limit):
    """
    Sorteaza descrescator dupa scor participantii
    :param the_list: lista participantilor
    :param upper_limit: limita superioara
    :return: lista cu id-urile ordonate dupa scor
    :rtype: list
    """
    #the_list_cpy=make_list_copy(the_list)
    def sort_function(elem):
        return get_sum(the_list,elem)
    list_to_sort=[elem for elem in range(1000,upper_limit)]
    list_to_sort.sort(reverse=True,key=sort_function)
    return list_to_sort
def greater_than(the_list,value,upper_limit):
    """
    Creeaza o lista in care sunt doar id-ul participantilor cu scorul mai mare ca value ordonati descrescator
    :param the_list: lista in care se cauta
    :param value: valoare de referinta
    :param upper_limit: limita superioara a id-urilor
    :return: lista cu proprietatea mentionata
    :rtype: list
    """
    new_list=sort_participants(the_list,upper_limit)
    result_list=[]
    for elem in new_list:
        if get_sum(the_list,elem)<=value:
            break
        result_list.append([elem,get_sum(the_list,elem)])
    return result_list

#4zone
def calculate_mean(the_list,left,right):
    """
    Calculeaza media scorurilor partcipantilor dintr-un interval
    :param the_list: lista de participanti
    :param left: capatul stang
    :param right: capatul drept
    :return: media aritmetica
    :rtype: float
    """
    sum=0
    for index in range(left,right+1):
        sum+=get_sum(the_list,index)
    return sum/(right-left+1)
def calculate_lowest(the_list,left,right):
    """
    Calculeaza cel mai mica valoare din intervalul dat
    :param the_list: lista de participanti
    :param left: capatul stang
    :param right: capatul drept
    :return: indicele de concurs
    :rtype: int
    """
    low=[0,101]
    for index in range(left,right+1):
        act_sum=get_sum(the_list,index)
        if act_sum<low[1]:
            low[1]=act_sum
            low[0]=index
    return low[1]
def print_by_score10(the_list,left,right):
    """
    Construieste o lista in care sunt doar id-urile si scorurile participantilor cu scorul divizibil cu 10
     :param the_list: lista de participanti
    :param left: capatul stang
    :param right: capatul drept
    :return: lista cu raspunsuri
    :rtype: list
    """
    result_list=[]
    for index in range(left,right+1):
        act_sum=get_sum(the_list,index)
        if act_sum%10==0:
            result_list.append([index,act_sum])
    return result_list

#5 zone
def filter_by_scorem(the_list,score,upper_limit,the_undo_list):
    """
    Filtreaza partcipantii care au scorurul multiplu de un numar dat
    :param the_list: lista concurentilor
    :param score: scorul de referinta
    :param upper_limit: limita superioara a id-urilor
    :return: lista filtrata
    :rtype: list
    """
    the_undo_list.append(make_dic_copy(the_list))
    result_list=[]
    for index in range(1000,upper_limit):
        sum=get_sum(the_list,index)
        if sum%score==0:
            result_list.append(the_list[str(index)])
    return result_list
def filter_by_lower_score(the_list,score,upper_limit,the_undo_list):
    """
    Functia realizeaza filtrarea tuturor participantilor in functie de cum se compara cu un scor dat
    :param the_list: lista participantilor
    :param score: scorul de referinta
    :param upper_limit: maximul id-urilor
    :param the_undo_list: lista de undo
    :return:  lista finala
    :rtype: list
    """
    result_list=[]
    the_undo_list.append(make_dic_copy(the_list))
    for index in range(1000,upper_limit):
        sum=get_sum(the_list,index)
        if sum<score:
            result_list.append(the_list[str(index)])
    return result_list

#6 zone
def undo_op(the_list,the_undo_list,id):
    """
    Functia realizeaza functionalitatea de undo si actualizeaza id-max
    :param the_list: lista participantilor
    :param the_undo_list: lista de undo
    :param id: id-max
    :return: lista dupa undo
    :rtype: list
    """
    the_list=the_undo_list.pop()
    id=len(the_list)
    return the_list

#default tests
def test_first_option():
    assert(first_option([2,3])==2)
    assert(first_option([3,4])==3)
    assert(first_option([0]  )==0)
def test_second_option():
    assert(second_option([2,3])==3)
    assert(second_option([3,4])==4)
    assert(second_option([1,1])==1)
    assert(second_option([10,5])==5)

def test_add_new_score():
    simple_list=fill_the_list()
    assert add_new_score({'1000':[4]},1002,5)=={'1000':[4],'1002':[5]}

#2 zone tests
def test_delete_by_id():
    simple_list=fill_the_list()
    new_list={}
    new_list=delete_by_id(simple_list,1002, [])
    assert(new_list['1000']==[10,2,3,8,9,10,5,8])
    assert(new_list['1001']==[10,2,7,8,4,10,5,8,9])
    assert(new_list['1002']==[])
def test_delete_by_id_interval():
    simple_list = fill_the_list()
    new_list = {}
    new_list = delete_by_id_interval(simple_list, 1002,1003, [])
    assert (new_list['1000'] == [10, 2, 3, 8, 9, 10, 5, 8])
    assert (new_list['1001'] == [10, 2, 7, 8, 4, 10, 5, 8, 9])
    assert (new_list['1002'] == [])
    assert (new_list['1003'] == [])

    simple_list = fill_the_list()
    new_list = {}
    new_list = delete_by_id_interval(simple_list, 1000, 1002, [])
    assert (new_list['1000'] == [])
    assert (new_list['1001'] == [])
    assert (new_list['1002'] == [])
    assert (new_list['1003'] == [3,5,6,7,8,9,5])
def test_update_score():
    simple_list = fill_the_list()
    new_list = {}
    new_list=update_score(simple_list,1000,5,10,[])
    assert(new_list['1000']==[10,2,3,8,10,10,5,8])
    assert(new_list['1001']==[10,2,7,8,4,10,5,8,9])

    simple_list = fill_the_list()
    new_list = {}
    new_list = update_score(simple_list, 1001, 5, 10,[])
    assert (new_list['1000'] == [10, 2, 3, 8, 9, 10, 5, 8])
    assert (new_list['1001'] == [10, 2, 7, 8, 10, 10, 5, 8, 9])

#3 zone tests
def test_get_sum():
    simple_list=fill_the_list()
    #[1000,10,2,3,8,9,10,5,8],[1001,10,2,7,8,4,10,5,8,9],[1002,10,10,10,10,10,10,10,10,10,10],[1003,3,5,6,7,8,9,5]
    assert(get_sum(simple_list,1000)==55)
    assert(get_sum(simple_list,1001)==63)
    assert(get_sum(simple_list,1002)==100)
    assert(get_sum(simple_list,1003)==43)
def test_lower_than():
    simple_list=fill_the_list()
    assert(len(lower_than(simple_list,150,1004))==4)
    assert(len(lower_than(simple_list,100,1004))==3)
    assert(len(lower_than(simple_list,60,1004))==2)
    assert(len(lower_than(simple_list,50,1004))==1)
    assert(len(lower_than(simple_list,40,1004))==0)
    assert(lower_than(simple_list, 50, 1004)==[[1003,43]])
def test_sort_participants():
    simple_list=fill_the_list()
    assert(sort_participants(simple_list,1004)==[1002,1001,1000,1003])
def test_greater_than():
    simple_list=fill_the_list()
    assert(len(greater_than(simple_list,150,1004))==0)
    assert(len(greater_than(simple_list,99,1004))==1)
    assert(len(greater_than(simple_list,60,1004))==2)
    assert(len(greater_than(simple_list,50,1004))==3)
    assert(len(greater_than(simple_list,40,1004))==4)
    assert(greater_than(simple_list, 99, 1004)==[[1002,100]])

#4 zone tests
def test_calculate_mean():
    simple_list=fill_the_list()
    assert(calculate_mean(simple_list,1000,1000)==55/1)
    assert(calculate_mean(simple_list,1000,1002)==(55+63+100)/3)
def test_calculate_lowest():
    simple_list=fill_the_list()
    assert(calculate_lowest(simple_list,1000,1003)==43)
    assert(calculate_lowest(simple_list,1002,1002)==100)
    assert(calculate_lowest(simple_list,1000,1002)==55)
def test_print_by_score10():
    simple_list=fill_the_list()
    assert(print_by_score10(simple_list,1000,1003)==[[1002,100]])

#5 zone tests
def test_filter_by_scorem():
    simple_list=fill_the_list()
    assert(len(filter_by_scorem(simple_list,10,1004,[]))==1)
    assert(len(filter_by_scorem(simple_list,5,1004,[]))==2)
    assert(len(filter_by_scorem(simple_list,2,1004,[]))==1)
    assert(len(filter_by_scorem(simple_list,3,1004,[]))==1)
def test_filter_by_lower_score():
    simple_list=fill_the_list()
    assert(len(filter_by_lower_score(simple_list,56,1004,[]))==2)
    assert(len(filter_by_lower_score(simple_list,64,1004,[]))==3)
    assert(len(filter_by_lower_score(simple_list,50,1004,[]))==1)

#6 zone tests
def test_undo_op():
    a=0
    assert(undo_op([5,6,7],[[5,6],[6,8,9]],a)==[6,8,9])
    assert(undo_op([5,6,7],[[5,6],[6,8,9],[7,9,10]],a)==[7,9,10])

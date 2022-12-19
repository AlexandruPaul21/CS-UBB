def fill_the_list():
    """
    Genereaza automat o serie de concurenti
    :return: lista de concurenti
    :rtype: list
    """
    return {'1000':[10,2,3,8,9,10,5,8],'1001':[10,2,7,8,4,10,5,8,9],'1002':[10,10,10,10,10,10,10,10,10,10],'1003':[3,5,6,7,8,9,5]}
def make_list_copy(the_list):
    """
    Face o copie listei oferite ca parametru
    :param the_list: lista catre trebuie copiata
    :return: copie a listei
    :rtype: list
    """
    cpy=[]
    for elem in the_list:
        cpy.append(elem)
    return cpy[:]
def make_dic_copy(the_dic):
    the_new_dic={}
    for key,value in the_dic.items():
        the_new_dic[key]=value[:]
    return the_new_dic

def test_fill_the_list():
    assert fill_the_list()=={'1000':[10,2,3,8,9,10,5,8],'1001':[10,2,7,8,4,10,5,8,9],'1002':[10,10,10,10,10,10,10,10,10,10],'1003':[3,5,6,7,8,9,5]}

def test_make_dic_copy():
    simple_dic=fill_the_list()
    assert(make_dic_copy(simple_dic)==simple_dic)

def test_make_list_copy():
    assert(make_list_copy([5,6,7,8])==[5,6,7,8])
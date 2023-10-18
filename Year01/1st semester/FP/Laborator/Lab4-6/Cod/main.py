"""
Creați o aplicație pentru gestiunea concurenților de la un concurs de programare. Programul înregistrează scorul obținut de fiecare concurent la 10 probe diferite, fiecare probă este notată cu un scor de la 1 la 10. Fiecare participant este identificat printr-un număr de concurs, scorul este ținut într-o listă unde concurentul 3 are scorul pe poziția 3 în listă. Programul trebuie sa ofere următoarele funcționalități:
1.	Adaugă un scor la un participant.
	1.Adaugă scor pentru un nou participant
	2.Inserare scor pentru un participant
2.	Modificare scor.
	1.Șterge scorul pentru participant dat
	2.Șterge scorul pentru un interval de participanți
	3.Înlocuiește scorul de la un participant
3.	Tipărește lista de participanți
	1.Tipărește participanții care au scor mai mic decât un scor dat
	2.Tipărește participanții ordonat după scor
	3.Tipărește participanții cu scor mai mare decât un scor dat, ordonat după scor
4.	Operații pe un subset de participanți
	1.Calculează media scorurilor pentru un interval dat (ex. Se da 1 și 5 se tipărește media scorurilor participanților 1,2,3,4 și 5)
	2.Calculează scorul minim pentru un interval de participanți dat
	3.Tipărește participanții dintr-un interval dat care au scorul multiplu de 10
5.	Filtrare
	1.Filtrare participanți care au scorul multiplu unui număr dat. Ex. Se da nr. 10, se elimină scorul de la toți participanții care nu au scorul multiplu de 10
	2.Filtrare participanți care au scorul mai mic decât un scor dat
6.	Undo
	1.Reface ultima operație (lista de scoruri revine la numerele ce existau înainte de ultima operație care a modificat lista)
"""

from termcolor import colored

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

def print_colored(print_object,color):
    """
    Printeaza un anumit text cu o anumita culoare
    :param print_object: string
    :param color: string
    :return: none
    """
    print(colored(print_object,color))

def print_menu():
    print(colored("Meniul principal","blue"))
    print("1.Adaugă un scor la un participant.")
    print("1.1.Adaugă scor pentru un nou participant")
    print("1.2.Inserare scor pentru un participant")
    print("2.Modificare scor.")
    print("2.1.Șterge scorul pentru participant dat")
    print("2.2.Șterge scorul pentru un interval de participanți")
    print("2.3.Înlocuiește scorul de la un participant")
    print("3.Tipareste lista de participanti")
    print("3.1.Tipărește participanții care au scor mai mic decât un scor dat")
    print("3.2.Tipărește participanții ordonat după scor")
    print("3.3.Tipărește participanții cu scor mai mare decât un scor dat, ordonat după scor")
    print("4.Operații pe un subset de participanți")
    print("4.1.Calculează media scorurilor pentru un interval dat (ex. Se da 1 și 5 se tipărește media scorurilor participanților 1,2,3,4 și 5)")
    print("4.2.Calculează scorul minim pentru un interval de participanți dat")
    print("4.3.Tipărește participanții dintr-un interval dat care au scorul multiplu de 10")
    print("5.Filtrare")
    print("5.1.Filtrare participanți care au scorul multiplu unui număr dat. Ex. Se da nr. 10, se elimină scorul de la toți participanții care nu au scorul multiplu de 10")
    print("5.2.Filtrare participanți care au scorul mai mic decât un scor dat")
    print("6.Reface ultima operație (lista de scoruri revine la numerele ce existau înainte de ultima operație care a modificat lista)")
    print("0.Iesirea din aplicatie")

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
def read_new_score(contest_list,new_participant_id,the_undo_list):
    """
    Citeste un nou scor pentru un nou participant
    :param contest_list: lista de concurenti
    :param new_participant_id: id-ul noului participant
    :return: none
    """
    new_score=int(input("Introduceti scorul participantului:"))
    if new_score < 1 or new_score > 10:
        assert(0)
    the_undo_list.append(make_dic_copy(contest_list))
    contest_list[str(new_participant_id)]=[new_score]
def add_new_score(contest_list,idmax,the_undo_list):
    """
    Adauaga un scor unui participant cu id-ul dat
    :param contest_list: lista de participanti
    :param idmax: id-ul
    :return: none
    """
    participant_id=int(input("Introduceti id-ul participantului(existent):"))
    if len(contest_list[str(participant_id)])>10:
        assert(0)
    new_score=int(input("Introduceti scorul participantului(un nr de la 1 la 10):"))
    if new_score<1 or new_score>10 or participant_id<1000 or participant_id>idmax:
        assert(0)
    the_undo_list.append(make_dic_copy(contest_list))
    contest_list[str(participant_id)].append(new_score)

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

def ui():
    """
    Functia reprezinta meniul principal de interactiune cu utilizatorul
    :return:none
    """
    contest_list={}
    undo_list=[]
    id=1000
    contest_list = fill_the_list()
    id=1004#!!!!!!!!!!!!!!!!!!!!!!!!!
    end=True
    while end:
        print_menu()
        options_list=[]
        options_list=get_answer()
        if first_option(options_list)==1:
            if second_option(options_list)==1:
                try:
                    read_new_score(contest_list,id,undo_list)
                    msg="Participantul a fost adaugat cu id-ul "+str(id)
                    print_colored(msg,"green")
                    id+=1
                except:
                    print_colored("A aparut o eroare, va rugam incercati din nou!","red")
            elif second_option(options_list)==2:
                try:
                    add_new_score(contest_list,id,undo_list)
                    print_colored("Scor adaugat cu succes","green")
                except:
                    print_colored("A aparut o eroare, va rugam incercati din nou!", "red")
            else:
                print_colored("Optiune invalida","red")
        elif first_option(options_list)==2:
            if second_option(options_list)==1:
                try:
                    new_id=int(input("Introduceti id-ul de sters(aceasta actiune va sterge toate scorurile sale):"))
                    assert(new_id>=1000 and new_id<id)
                    contest_list=delete_by_id(contest_list,new_id,undo_list)
                    print_colored("Participant sters cu succes!","green")
                except:
                    print_colored("A aparut o eroare, va rugam incercati din nou!", "red")
            elif second_option(options_list)==2:
                try:
                    new_id=[int(elem) for elem in input("Introduceti capetele intervalului de sters separate prin spatiu:").split(" ")]
                    assert(new_id[0]>=1000 and new_id[0]<id)
                    assert(new_id[1]>=1000 and new_id[1]<id)
                    assert(len(new_id)<=2 or new_id[0]>new_id[1])
                    contest_list=delete_by_id_interval(contest_list,new_id[0],new_id[1],undo_list)
                    print_colored("Participanti stersi cu succes!", "green")
                except:
                    print_colored("A aparut o eroare, va rugam incercati din nou!", "red")
            elif second_option(options_list)==3:
                try:
                    new_id=int(input("Introduceti id-ul concurentului:"))
                    new_pb=int(input("Introduceti nr. problemei:"))
                    new_score=int(input("Intreoduceti noul scor:"))
                    assert(new_id >= 1000 and new_id < id)
                    assert(1<=new_pb and new_pb<=10)
                    assert(type(contest_list[new_id-1000][new_pb])==int)
                    assert(1<=new_score and new_score<=10)
                    contest_list=update_score(contest_list,new_id,new_pb,new_score,undo_list)
                    print_colored("Participant actualizat cu succes!", "green")
                except:
                    print_colored("A aparut o eroare, va rugam incercati din nou!", "red")
            else:
                print_colored("Optiune invalida", "red")
        elif first_option(options_list)==3:
            if second_option(options_list)==1:
                try:
                    value=int(input("Introduceti valoarea de refeinta:"))
                    assert(value>=0)
                    rez_list=lower_than(contest_list,value,id)
                    print(rez_list)
                except:
                    print_colored("A aparut o eroare, va rugam incercati din nou!", "red")
            elif second_option(options_list)==2:
                rez_list=sort_participants(contest_list,id)
                print(rez_list)
            elif second_option(options_list)==3:
                try:
                    value=int(input("Introduceti valoarea de refeinta:"))
                    assert(value>=0)
                    rez_list=greater_than(contest_list,value,id)
                    print(rez_list)
                except:
                    print_colored("A aparut o eroare, va rugam incercati din nou!", "red")
        elif first_option(options_list)==4:
            if second_option(options_list)==1:
                try:
                    left=int(input("Introduceti capatul stang al intervalului:"))
                    right=int(input("Introduceti capatul drept al intervalului:"))
                    assert(left<=right)
                    result=calculate_mean(contest_list,left,right)
                    print(result)
                except:
                    print_colored("A aparut o eroare, va rugam incercati din nou!", "red")
            elif second_option(options_list)==2:
                try:
                    left=int(input("Introduceti capatul stang al intervalului:"))
                    right=int(input("Introduceti capatul drept al intervalului:"))
                    assert(left<=right)
                    result=calculate_lowest(contest_list,left,right)
                    print(result)
                except:
                    print_colored("A aparut o eroare, va rugam incercati din nou!", "red")
            elif second_option(options_list)==3:
                try:
                    left=int(input("Introduceti capatul stang al intervalului:"))
                    right=int(input("Introduceti capatul drept al intervalului:"))
                    assert(left<=right)
                    result=print_by_score10(contest_list,left,right)
                    print(result)
                except:
                    print_colored("A aparut o eroare, va rugam incercati din nou!", "red")
        elif first_option(options_list)==5:
            if second_option(options_list)==1:
                try:
                    score=int(input("Introduceti scorul de referinta:"))
                    assert(0<=score and score<=100)
                    contest_list=filter_by_scorem(contest_list,score,id,undo_list)
                    print(contest_list)
                    print_colored("Participanti filtrari cu succes","green")
                except:
                    print_colored("A aparut o eroare, va rugam incercati din nou!", "red")
            elif second_option(options_list)==2:
                try:
                    score=int(input("Introduceti scorul de referinta:"))
                    assert(0<=score and score<=100)
                    contest_list=filter_by_lower_score(contest_list,score,id,undo_list)
                    print(contest_list)
                    print_colored("Participanti filtrari cu succes","green")
                except:
                    print_colored("A aparut o eroare, va rugam incercati din nou!", "red")
        elif first_option(options_list)==6:
            contest_list=undo_op(contest_list,undo_list,id)
        elif first_option(options_list)==0:
            end=False
        else:
            print_colored("Optiunea nu se regaseste in meniu","red")
        #print(contest_list)
        #print(undo_list)
ui()

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
def test_make_dic_copy():
    simple_dic=fill_the_list()
    assert(make_dic_copy(simple_dic)==simple_dic)

def test_make_list_copy():
    assert(make_list_copy([5,6,7,8])==[5,6,7,8])
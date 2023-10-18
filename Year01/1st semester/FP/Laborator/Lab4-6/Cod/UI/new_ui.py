"""
The new UI
"""

from domain.domain import *

def print_menu():
    print(colored("Meniul principal","blue"))
    print("1.Adaugă scor pentru un nou participant(comanda: adauga+' '+scor)")
    print("2.Șterge scorul pentru participant dat(comanda: sterge+' '+id)")
    print("3.Tipărește participanții ordonat după scor(comanda: tipareste)")
    print("4.Calculează scorul minim pentru un interval de participanți dat(comanda: calculeaza+' '+l1+' '+l2)")
    print("5.Filtrare participanți care au scorul mai mic decât un scor dat(comanda: filtrare+' '+scor)")
    print("6.Reface ultima operație (lista de scoruri revine la numerele ce existau înainte de ultima operație care a modificat lista)(comanda: undo)")
    print("0.Iesirea din aplicatie(comanda stop)")

def get_answer_ui(str):
    """
    Primeste un string si il pareseaza returnand o lista de raspunsuri
    :param str: stringul cu raspunsuri
    :return: lista
    :raises: Error if the command is invalid
    """
    ret_list=[elem for elem in str.split(' ')]
    if ret_list[0]!="adauga" and ret_list[0]!="sterge" and ret_list[0]!="tipareste" and ret_list[0]!="calculeaza" and ret_list[0]!="filtrare" and ret_list[0]!="undo" and ret_list[0]!="stop":
        raise ValueError("Optiune invalida")
    return ret_list

def get_main_command(the_list):
    """
    Returneaza argumentul principal
    :param the_list: lista de raspunsuri
    :return: prima comanda
    """
    return the_list[0]
def get_second_command(the_list):
    """
    Returneaza al doilea parametru al comenzii
    :param the_list: lista de raspunsuri
    :return: a doua comanda
    """
    return the_list[1]
def get_argument(the_list):
    """
    Transforma sirul de raspunsuri in numere
    :param the_list: lista de raspunsuri
    :return: lista fara primul element
    """
    try:
        return [int(the_list[index]) for index in range(1,len(the_list))]
    except:
        raise ValueError("Scorul trebuie sa fie corect")


def ui_v2():
    """
    Functia reprezinta meniul principal
    """
    contest_list={}
    undo_list=[]
    id=1000
    #Un-comment this for premade contestant list
    #contest_list=fill_the_list()
    #id=1004#!!!!!!!!!!!!!!!!!!!!!!!!!
    end=True
    while end:
        print_menu()
        try:
            answer=input("Dati comanda: ")
            user_demand=get_answer_ui(answer)
            if get_main_command(user_demand)=="adauga":
                try:
                    arguments=get_argument(user_demand)
                    add_new_score(contest_list,id,get_main_command(arguments))
                    msg="Participantul a fost adaugat cu id-ul "+str(id)
                    print_colored(msg,"green")
                    id+=1
                except ValueError as ve:
                    print_colored(ve,"red")
            elif get_main_command(user_demand)=="sterge":
                try:
                    arguments = get_argument(user_demand)
                    new_id=get_main_command(arguments)
                    assert (new_id >= 1000 and new_id < id)
                    contest_list = delete_by_id(contest_list, new_id, undo_list)
                    print_colored("Participant sters cu succes!", "green")
                except:
                    print_colored("A aparut o eroare, va rugam incercati din nou!", "red")
            elif get_main_command(user_demand)=="tipareste":
                rez_list = sort_participants(contest_list, id)
                print(rez_list)
            elif get_main_command(user_demand)=="calculeaza":
                try:
                    arguments=get_argument(user_demand)
                    left=get_main_command(arguments)
                    right=get_second_command(arguments)
                    assert(left<=right)
                    result=calculate_lowest(contest_list,left,right)
                    print(result)
                except:
                    print_colored("A aparut o eroare, va rugam incercati din nou!", "red")
            elif get_main_command(user_demand)=="filtrare":
                try:
                    arguments=get_argument(user_demand)
                    score=get_main_command(arguments)
                    assert(0<=score and score<=100)
                    contest_list=filter_by_lower_score(contest_list,score,id,undo_list)
                    print(contest_list)
                    print_colored("Participanti filtrari cu succes","green")
                except:
                    print_colored("A aparut o eroare, va rugam incercati din nou!", "red")
            elif get_main_command(user_demand)=="undo":
                try:
                    contest_list = undo_op(contest_list, undo_list, id)
                    print_colored("Undo realizat cu succes", "green")
                except:
                    print_colored("A aparut o eroare, va rugam incercati din nou!", "red")
            elif get_main_command(user_demand)=="stop":
                end = False
            else:
                print_colored("Optiunea nu se regaseste in meniu", "red")
        except ValueError as ve:
            print(ve,"red")

def test_get_answer_ui():
    assert get_answer_ui("adauga 5")==['adauga','5']
    assert get_answer_ui("sterge 1004")==['sterge','1004']
    assert get_answer_ui("tipareste")==['tipareste']
    assert get_answer_ui("filtrare 56")==["filtrare","56"]
    assert get_answer_ui("undo")==["undo"]

def test_get_argument():
    assert get_argument(["adauga","5"])==[5]
    assert get_argument(["sterge","6"])==[6]
    assert get_argument(["mere","6","7"])==[6,7]

def test_get_main_command():
    assert get_main_command(['adauga','5'])=="adauga"
    assert get_second_command(['adauga','5'])=="5"
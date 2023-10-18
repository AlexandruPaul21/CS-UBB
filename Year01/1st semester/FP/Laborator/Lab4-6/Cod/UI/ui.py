from domain.domain import *

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

def read_new_score_ui(contest_list,new_participant_id,the_undo_list):
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
def add_new_score_ui(contest_list,idmax,the_undo_list):
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

def ui():
    """
    Functia reprezinta meniul principal de interactiune cu utilizatorul
    :return:none
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
        options_list=[]
        options_list=get_answer()
        if first_option(options_list)==1:
            if second_option(options_list)==1:
                try:
                    read_new_score_ui(contest_list,id,undo_list)
                    msg="Participantul a fost adaugat cu id-ul "+str(id)
                    print_colored(msg,"green")
                    id+=1
                except:
                    print_colored("A aparut o eroare, va rugam incercati din nou!","red")
            elif second_option(options_list)==2:
                try:
                    add_new_score_ui(contest_list,id,undo_list)
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
            try:
                contest_list=undo_op(contest_list,undo_list,id)
                print_colored("Undo realizat cu succes","green")
            except:
                print_colored("A aparut o eroare, va rugam incercati din nou!", "red")
        elif first_option(options_list)==0:
            end=False
        else:
            print_colored("Optiunea nu se regaseste in meniu","red")
        #print(contest_list)
        #print(undo_list)
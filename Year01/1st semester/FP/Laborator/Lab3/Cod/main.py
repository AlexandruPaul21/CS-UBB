"""
Scrieți o aplicație care are interfața utilizator tip consolă cu un meniu:
1. Citirea unei liste de numere întregi
Afișare subscvenței de lungime maximă cu proprietatea:
2.oricare ar fi un indice j, din subsecvență(jϵ[i,p-2], unde i- indicele de inceput, p-lungimea subsecvenței) expresiile e1=x[j+1]-x[j] și e2=x[j+2]-x[j+1] au semne diferite
3.oricare două elemente consecutive au cel puțin 2 cifre distincte comune
4.ca are suma maxima
4.Ieșire din aplicație.
"""

from termcolor import colored

def print_list(message,lst):
    """
    Afiseaza lista lst cu mesajul specificat
    :param message: mesajul cu care vom afisa sirul
    :param lst: lista pe care trebuie sa o afisam
    :return: - ; va fi afisata lista
    """
    print(message,lst)

def read_list():
    """
    Citeste lista in formatul cerut sub forma de string si o transforma intr-o lista de numere intregi
    :return: lista
    :rtype: list
    """
    the_list_as_string = ""
    the_list_as_string = input("Introduceti lista in formatul cerut:")
    if the_list_as_string.find(".")!=-1:
        print(colored("Nu puteti introduce numere reale sau rationale", 'red'))
        return []
    lst = []
    if the_list_as_string != "":
        lst = [int(elem) for elem in the_list_as_string.split(",")]
    return lst

def print_menu():
    """
    Printeaza meniul de interactiune cu utilizatorul
    :return: -
    """
    print("1. Citirea unei liste de numere întregi")
    print("Afișare subscvenței de lungime maximă cu proprietatea:")
    print("2. (10) oricare ar fi un indice j, din subsecvență(jϵ[i,p-2], unde i- indicele de inceput, p-lungimea subsecvenței) expresiile e1=x[j+1]-x[j] și e2=x[j+2]-x[j+1] au semne diferite")
    print("3. (14) oricare două elemente consecutive au cel puțin 2 cifre distincte comune")
    print("4. (11) are suma maxima")
    print("5. Ieșire din aplicație.")

def same_digits(nr1,nr2,min_d):
    """
    Functia calculeaza numarul de cifre comune al celor doua numere, si verifica daca acesta depaseste o valoare minima
    data ca parametru(F2.T1 - vezi documentatia)
    :param nr1: primul numar de verificat
    :param nr2: al doilea numar de verificat
    :param min_d: numarul minim de cifre distincte
    :return: True daca numarul minim este atins, False- in caz contrar
    :rtype: bool
    """
    nr1=int(nr1)
    nr2=int(nr2)
    freq1=[0]*11
    freq2=[0]*11
    while(nr1):
        freq1[int(nr1%10)]+=1
        nr1//=10
    while(nr2):
        freq2[int(nr2%10)]+=1
        nr2//=10
    same=0
    for i in range(10):
        if (freq1[i]!=0 and freq2[i]!=0):
            same+=1;
    return same>=min_d

def populate(lst):
    """
    Populeaza lista oferita ca parametru cu valori pentru a facilita testarea
    :param lst: list
    :return: -; functia modifica lista, adaugand valori predefinite in ea
    """
    lst.append(-63)
    lst.append(-25)
    lst.append(12)
    lst.append(12652)
    lst.append(26)

def dif_sign(index,lst):
    """
    Calculeaza pentru valoare i, diferenta x[i+1]-x[i] si x[i+2]-x[i+1] si verifica daca au semne diferite(F1.T1-vezi documentatia)
    :param i:indicele de inceput al tripletului
    :param lst:lista in ca
    :return: reurneaza True daca expresiile au semne diferite si False in caz contrar
    :rtype: bool
    """
    e1=lst[index+1]-lst[index]
    e2=lst[index+2]-lst[index+1]
    return e1*e2<=0 and ((e1!=0) or (e2!=0))

def calculate_length_1(lst):
    """
    Functia gaseste cel mai lung sir care respecta proprietatea enuntata la F2
    :param lst: lista in care se cauta
    :return: lungimea, index inceput, index sfarsit
    """
    length=0
    max_length=0
    index_st=0
    for index in range(len(lst)-2):
        if dif_sign(index,lst):
            length+=1
            if length==1:
                index_st=index
        else :
            index_fn=index+2
            length=0
        max_length=max(max_length,length)
    if length!=0 and length>max_length:
        max_length=length
    if max_length==0:
        max_length=-2
    return max_length+2,index_st,index_st+max_length+2


def calculate_length_2(lst):
    """
    Calculeaza lungimea maxima a subsecventei pentru care oricare doua elemente consecutive au cel putin 2 cifre
    distincte egale(F2-vezi documentatia)
    :param: list; lista de numere pentru care se verifica proprietatea
    :return: lungimea subecventei, index inceput, index sfarsit
    :rtype: int
    """
    anterior=-0.5
    max_length=0
    length=0
    index_st=0
    index=0
    for elem in lst:
        if anterior==-0.5:
            anterior=elem
        else:
            if same_digits(abs(elem),abs(anterior),2):
                length+=1
                if(length==1):
                    index_st=index-1
            else:
                length=0
            max_length=max(length,max_length)
            anterior=elem
        index+=1
    if length!=0 and length>max_length:
        max_length=length
    if max_length==0:
        max_length=-1
    return max_length+1,index_st,index_st+max_length+1

def calculate_length_3(lst):
    """
    Functia determina cea mai lunga secventa de lungime maxima
    :param lst: lista in care se cauta
    :return: se returneaza un triplet suma maxima,lungime, index de start
    :rtype: tuple
    """
    sum=0
    index_st=0
    index1=0
    index2=0
    max_sum=0
    length=0
    for index1 in range(len(lst)):
        sum=lst[index1]
        for index2 in range(index1+1,len(lst)):
            sum+=lst[index2]
            if sum>max_sum:
                max_sum=sum
                length=index2-index1
                index_st=index1
            elif sum==max_sum and length<index2-index1:
                index_st=index1
                length=index2-index1
    return max_sum,length,index_st

def start():
    """
    Programul principal, va reprezenta meniul de interactiune cu utilizatorul
    :return: -
    """
    numeric_list=[]
    #testing camp
    while True:
        print_list("Lista este:",numeric_list)
        print_menu()
        option=int(input("Optiunea dumneavoastra este:"))
        if(option==1):
            numeric_list=read_list()
        elif(option==2):
            result=calculate_length_1(numeric_list)
            if result[0]==0:
                print("Nu exista o secventa care sa respecte aceasta proprietate")
            else:
                print('[',end='')
                sep=", "
                for index in range(result[1],result[2]):
                    if index==result[2]-1:
                        sep=""
                    print(numeric_list[index],end=sep)
                print(']')
        elif(option==3):
            result = calculate_length_2(numeric_list)
            if result[0] == 0:
                print("Nu exista o secventa care sa respecte aceasta proprietate")
            else:
                print('[', end='')
                sep = ", "
                for index in range(result[1], result[2]):
                    if index == result[2]-1:
                        sep = ""
                    print(numeric_list[index], end=sep)
                print(']')
        elif option==4:
            result=calculate_length_3(numeric_list)
            if result[0] == 0:
                print("Nu exista o secventa care sa respecte aceasta proprietate")
            else:
                print('[', end='')
                sep = ", "
                #print(result)
                for index in range(result[2], result[2]+result[1]+1):
                    if index == result[2]+result[1]:
                        sep = ""
                    print(numeric_list[index], end=sep)
                print(']')
        elif option==5:
            break
        else:
            print(colored("Introduceti o optiune valida",'red'))

start()
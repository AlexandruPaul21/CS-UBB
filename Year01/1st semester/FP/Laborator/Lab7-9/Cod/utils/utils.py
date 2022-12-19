import random
import string

def getYear_from_CNP(CNP):
    """
    Returneaza anul nasterii obtinut din analizarea CNP-ului
    :param CNP: cnp-ul persoanei
    :return: anul nasterii%100
    """
    year = CNP[11] * 10 + CNP[10]
    return year


def getMonth_from_CNP(CNP):
    """
    Returneaza luna nasterii
    :param CNP: cnp-ul persoanei
    :return: luna nasterii
    """
    month = CNP[9] * 10 + CNP[8]
    return month


def getDay_from_CNP(CNP):
    """
    Returneaza ziua nasterii
    :param CNP: cnp-ul persoanei
    :return: ziua nasterii
    """
    day = CNP[7] * 10 + CNP[6]
    return day

def last_digit_control(CNP):
    """
    Aplicarea algoritmului de validare pe baza ultimei cifre a CNP-ului
    :param CNP: cnp-ul de verificat
    :return: True sau False
    """
    validator=[2,7,9,1,4,6,3,5,8,2,7,9]
    sum=0
    for index in range(12):
        sum+=CNP[12-index]*validator[index]
    sum%=11
    if sum>9:
        sum=1
    return sum==CNP[0]

def string_generator(size):
        return ''.join(random.choice(string.ascii_uppercase) for _ in range(size))

def number_string_generator(size):
        return ''.join(random.choice(string.digits) for _ in range(size))

def bubble_sort(the_list,key=lambda x: x,cmp=lambda x,y: x>y,reverse=False):
    """
    Functie de sortare folosind metoda bulelor
    :param the_list: lista de sortat
    :param key: cheia dupa care se sorteaza
    :param cmp: functia de comparare
    :param reverse: 1 sau 0 daca sirul sa fie invers sau nu
    :return: None
    """
    ok=False
    while not ok:
        ok=True
        for index in range(len(the_list)-1):
            if cmp(key(the_list[index]),key(the_list[index+1])):
                the_list[index],the_list[index+1]=the_list[index+1],the_list[index]
                ok=False

    if reverse:
        the_list.reverse()

def shell_sort(the_list,key=lambda x: x,cmp=lambda x,y: x>y,reverse=False):
    """
    Functie de sortare folosind metoda bulelor
    :param the_list: lista de sortat
    :param key: cheia dupa care se sorteaza
    :param cmp: functia de comparare
    :param reverse: 1 sau 0 daca sirul sa fie invers sau nu
    :return: None
    """
    n=len(the_list)
    gap=n//2

    while gap>0:
        for index in range(gap,n):
            temp=the_list[index]

            ad_index=index
            while ad_index>=gap and cmp(key(the_list[ad_index-gap]),key(temp)):
                the_list[ad_index]=the_list[ad_index-gap]
                ad_index-=gap
            the_list[ad_index]=temp

        gap//=2

    if reverse:
        the_list.reverse()
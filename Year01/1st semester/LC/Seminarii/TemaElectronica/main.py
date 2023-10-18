import copy

#server_side

def get_digit(c):
    """
    Functia returneaza valoare corespunzatoare in format intreg pentru fiecare caracter
    :param c: cifra in format char
    :return: cifra in format numeri
    :raises: ValueError daca cifra nu este corect introdusa
    """
    if '0'<=str(c) and str(c)<='9':
        return int(c)
    if c=='A':
        return 10
    if c=='B':
        return 11
    if c=='C':
        return 12
    if c=='D':
        return 13
    if c=='E':
        return 14
    if c=='F':
        return 15
    raise ValueError("FormatInvalid")
def set_digit(c):
    """
    Functia returneaza in format caracter valoare introdusa ca intreg
    :param c: cifra in format intreg
    :return: caracterul
    :raises: ValueError daca !(0<=c and c<=15)
    """
    if 0<=c and c<=9:
        return str(c)
    if c==10:
        return 'A'
    if c==11:
        return 'B'
    if c==12:
        return 'C'
    if c==13:
        return 'D'
    if c==14:
        return 'E'
    if c==15:
        return 'F'

def cmp(list1,list2):
    """
    Functia compara doua liste
    :param list1: lista 1
    :param list2: lista 2
    :return: 1 daca list1>list2, 0 altfel
    """
    if len(list1)>len(list2):
        return 1
    elif len(list1)<len(list2):
        return 0
    else:
        for index in range(len(list1)):
            if get_digit(list1[index])>get_digit(list2[index]):
                return 1
            elif get_digit(list1[index])<get_digit(list2[index]):
                return 0
        return 1


class number(): #clasa numar ma va ajuta sa retin numarul si baza sa
    def __init__(self,nr,base):
        """
        Constructorul clasei, stocheaza numarul ca si o lista si baza in care e scris
        :param nr:
        :param base:
        :raises: ValueError daca numarul este invalid
        """
        self.__base=base
        self.__nr=[]
        for digit in nr:
            if get_digit(digit)>=base:
                raise ValueError("NumarInvalid")
            self.__nr.append(digit)

    """
    Metode de tip get pentru clasa numar
    """
    def getNr_as_decimal(self):
        number=""
        for elem in self.__nr:
            number+=str(elem)
        # if self.__base!=16:
        #     number=0
        #     for digit in self.__nr:
        #         number=number*10+int(digit)
        return number

    def getNr_as_list(self):
        return self.__nr

    def getBase(self):
        return self.__base

    """
    Operatiile de adunare, scadere, inmultire cu o cifra, impartire cu o cifra
    """

    def add_nr(self,value):
        """
        Realizam adunarea numerelor stocate sub forma de vector
        :param value: valoare pe care o adunam
        :return: rezultatul final
        """
        result=[]
        bs=self.__base
        nr1=copy.deepcopy(self.__nr)
        nr2=copy.deepcopy(value)
        nr1.reverse() #inversam numerele deoarce dorim sa le parcurgem cu un singur index
        nr2.reverse()
        ln=max(len(nr1),len(nr2))
        t=0
        for index in range(ln):
            if index>=len(nr1): #exista posibilitatea ca unul din numere sa fie mai mare ca celalalt, asa ca
                c1=0            #daca un numar e mai mare, completam cu 0 restul cifrelor
            else:
                c1=nr1[index]
            if index>=len(nr2):
                c2=0
            else:
                c2=nr2[index]
            c1=get_digit(c1)
            c2=get_digit(c2)
            if c1+c2+t<bs:             #aplicam algoritmul adunarii, c1 si c2 reprezentand cifrele actuale
                result.append(c1+c2+t) #bs reprezinta baza in care este scris numarul
                t=0
            else:
                result.append((c1+c2+t)%bs)
                t=1
        if t==1:
            result.append(1)
        result.reverse() #la final inversam rezultatul pentru a obtine numarul in ordinea fireasca
        return result

    def sub_nr(self,value):
        """
        Realizam scaderea numerelor stocate sub forma de vector
        :param value: valoare pe care o scadem
        :return: rezultatul final
        :raises: ValueError daca descazutul este mai mic decat scazator
        notatiile pentru c1,c2 si bs raman cele de la algoritmul precedent
        """
        result=[]
        bs=self.__base
        nr1=copy.deepcopy(self.__nr)
        nr2=copy.deepcopy(value)
        nr1.reverse()
        nr2.reverse()
        ln=max(len(nr1),len(nr2))
        t=0
        for index in range(ln):
            if index>=len(nr1):
                c1=0
            else:
                c1=nr1[index]
            if index>=len(nr2):
                c2=0
            else:
                c2=nr2[index]
            c1=get_digit(c1)
            c2=get_digit(c2)
            if c1-t<c2:
                result.append(bs+c1-t-c2) #aplicam algoritmul scaderii
                t=1
            else:
                result.append(c1-t-c2)
                t=0
        while len(result)!=0 and result[len(result)-1]==0:
            result.pop()
        result.reverse()
        if result==[]:
            result=[0]
        if t==1: #in cazul in care descazutul nu a fost mai mare decat scazatorul aruncam o exceptie
            raise ValueError("Descazutul trebuie sa fie mai mare decat scazatorul")
        return result

    def mul_by_digit(self,digit):
        """
        Subprogramul realizeaza inmultirea cu o cifra
        :param digit:cifra cu care inmultim
        :return: numarul obtinut sub forma de lista
        :raise: ValueError daca nu este introdusa o cifra
        """
        if type(digit)==list:
            digit=str(digit[0])
        result=[]
        bs=self.__base
        nr=copy.deepcopy(self.__nr)
        nr.reverse()
        ln=len(nr)
        t=0 #t reprezinta transportul
        #numarul este stocat in vector
        if len(digit)!=1: #in cazul in care utilizatorul nu introduce o cifra, aruncam o eroare
            raise ValueError("Aplicatia suporta inmultirea doar cu o cifra")
        digit=get_digit(digit) #functia get_digit returneaza pentru cifrele corespunzatoare pentru
        for index in range(ln): #toate caracterele ce pot fi interpretate ca cifre(inclusiv cele din baza 16)
            c=get_digit(nr[index])
            result.append((c*digit+t)%bs) #aplicam algoritmul
            t=(c*digit+t)//bs
        if t!=0:
            result.append(t)  #in cazul in care exista transport la ultima operatie, il adaugam la rezultat
        result.reverse()  #inversam numarul pentru a il obtine in forma fireasca
        return result

    def div_by_digit(self,digit):
        """
        Algoritmul calculeaza impartirea cu o cifra
        :param digit: cifra cu care impartim
        :return: catul si restul
        :raises: ValueError pentru input invalid
        """
        if type(digit)==list:
            digit=str(digit[0])
        result=[]
        bs=self.__base
        nr=copy.deepcopy(self.__nr) #cu deepcopy realizam o copie independenta a vectorului
        ln=len(nr)
        t=0 #initializam transportul cu 0
        if len(digit)!=1: #daca digit nu este o cifra aruncam exceptie
            raise ValueError("Aplicatia suporta doar inmultirea cu cifre")
        digit=get_digit(digit)
        for index in range(ln):
            c=get_digit(nr[index])
            result.append((bs*t+c)//digit) #aplicam algoritmul
            t=(bs*t+c)%digit
        while result!=[] and result[0]==0: #eliminam eventualele cifre nule de la inceput
            result.pop(0)
        return [result,t] #returna rezultatul

    """
    Metode speciale:Impartirea cu mai mult de o cifra, inmultirea cu mai mult de o cifra
    """
    def division_with_number(self,value):
        """
        Exitinde ideea de impartirea a unui numar la o cifra, prin niste artificii matematice
        :param value: impartitorul
        :return: catul si restul, ambele sub forma unor vectorii
        """
        cpy=copy.deepcopy(self.__nr)#realizam o copie cu deepcopy a numarului
        cpy_val=copy.deepcopy(value)
        cat=[0]*len(self.__nr) #initializam vectorul in care este catul
        rest=[0]
        ld=-1#initialize
        while True:
            ld=0
            value=copy.deepcopy(cpy_val)
            while cmp(self.__nr,value): #in cadrul acestui algoritm ne vom folosi de faptul ca impartirea este
                ld+=1                   #o scadere repetata, la fiecare pas vom scadea value*10^n( cu n maxim din nr)
                value.append('0')      #inmutlirea cu 10^n este realizata la nivel de string prin adaugarea de 0-uri
            ld-=1
            if ld==-1:
                break
            value.pop()
            self.__nr=self.sub_nr(value) #scadem valoare din rezultatul initial
            cat[ld]+=1                   #incrementam cu 1 valoare din vectorul rezultat
        rest=[int(elem) for elem in self.__nr]
        while len(cat)!=0 and cat[len(cat)-1]==0: #eliminam 0-urile neseminifcative
            cat.pop()
        cat.reverse()
        if len(rest)==1:
            rest.append(0)
            rest.reverse()
        return [[set_digit(elem) for elem in cat],rest]

    def mul_by_number(self,value):
        """
        Subprogramul extinde inmultirea cu o cifra la inmultirea cu doua cifre
        :param value: inmultirorul
        :return: rezultatul
        """
        #algoritmul se bazeaza pe faptul ca daca avem un numar de doua cifre pe care il inmultim cu un altul
        cpy_nr=copy.deepcopy(self.__nr)  #aceasta operatie va fi echivalenta cu inmultirea numarului cu
        cpy_value=copy.deepcopy(value)   #descompunerea in baza 10 a numarului
        cpy_value.reverse()
        new_nr=['0']
        for index in range(len(cpy_value)):
            self.__nr=copy.deepcopy(cpy_nr)
            self.__nr=[set_digit(elem) for elem in self.mul_by_digit(cpy_value[index])] #reducem ideea de inmultire cu
            for i1 in range(index):                                                     #un nr la cea de inmutlire cu o cifra
                self.__nr.append('0') #aici simulam inmultirea cu 10
            self.__nr=[set_digit(elem) for elem in self.add_nr(new_nr)]
            new_nr=copy.deepcopy(self.__nr)
        return new_nr

    """
    Metode de conversie pentru clasa numar
    """
    def convert_by_division(self,new_base):
        """
        Functia exemplifica metoda conversiei prin impartiri repetate
        :param new_base: noua baza
        """
        if new_base==self.__base:
            return
        if new_base>self.__base: #se trateaza doua cazuri, pentru a sti cum vom reprezenta numerele
            new_nr=[]
            new_base_in_base=[str(new_base//self.__base),str(new_base%self.__base)]
            while self.__nr!=[]:
                rest=[]
                [self.__nr,rest]=self.division_with_number(new_base_in_base) #la fiecare pas se imparte numarul actual, cu noua baza
                new_nr.append(rest[0]*10+rest[1]) #se adauga restul intr-o lista
            while new_nr!=[] and new_nr[len(new_nr)-1]==0: #eliminam eventualele cifre nule de la inceput
                new_nr.pop(0)
            new_nr.reverse() #inversam lista, avand in vedere ca obtinem rezultatul intors
            self.__base=new_base
            self.__nr=[set_digit(elem) for elem in new_nr]
        else:
            new_nr=[]#in cazul in care new_base<self.__base conversia se realizeaza trivial
            while self.__nr!=[]:
                rez=[]
                r=0
                [rez,r]=self.div_by_digit(set_digit(new_base)) #impartim cu baza destinatie
                self.__nr=[str(elem) for elem in rez]
                new_nr.append(r)  #adaugam restul intr-o lista
            self.__base=new_base
            new_nr.reverse()      #inversam rezultatul pentru a obtine numarul in ordine fireasca
            self.__nr=[set_digit(elem) for elem in new_nr]

    def convert_by_substitution(self,new_base):
        """
        Functia exemplifica metoda substitutiei
        :param new_base: noua baza in care va fi numarul
        """
        if new_base==self.__base: #caz trivial, cele doua baze sunt egale, nu se face nimic
            return
        if new_base<self.__base: #cazul in care noua baza este mai mica decat vechea baza
            base=self.__base
            cpy_nr=copy.deepcopy(self.__nr)
            cpy_nr.reverse()
            new_nr=[]
            for elem in cpy_nr: #adaugam in new_nr repzentarea in baza new_base a cifrelor numarului
                self.__base=base
                self.__nr=elem
                self.convert_by_division(new_base)
                new_nr.append(self.__nr)

            new_base_in_base=str(base)
            self.__nr=[elem for elem in new_base_in_base]   #reprezentarea in baza new_base a vechii baze
            self.__base=10
            self.convert_by_division(new_base)
            new_base_in_base=self.__nr

            self.__nr=['1']
            ans=[]
            for index in range(len(cpy_nr)):          #se realizeaza substituria propriu-zisa
                put=copy.deepcopy(self.__nr)
                self.mul_by_number(new_nr[index])
                ans.append(self.__nr)
                self.__nr=put
                self.mul_by_number(new_base_in_base)
            self.__nr=['0']

            for elem in ans:
                self.__nr=[set_digit(c) for c in self.add_nr(elem)] #se construieste rezultatul respectand formatul
        else:
            base=self.__base  #celalalt caz este mai usor de tratat, intrucat toate valorile vor fi mai mici ca 10
            self.__base=new_base #in baza in care se efectueaza calculele
            cpy=copy.deepcopy(self.__nr)
            new_nr=[]
            self.__nr=['1']
            cpy.reverse()
            for index in range(len(cpy)):
                lst_power=copy.deepcopy(self.__nr)
                self.__nr=[elem for elem in self.mul_by_number([set_digit(int(cpy[index]))])] #la fiecare pas inmulti puterea bazei
                new_nr.append(self.__nr)           #cu actuala cifra
                self.__nr=copy.deepcopy(lst_power)
                self.__nr=[elem for elem in self.mul_by_number([set_digit(base)])]
            self.__nr=new_nr[0]
            for index in range(1,len(new_nr)):
                self.__nr=[set_digit(elem) for elem in self.add_nr(new_nr[index])] #se construieste rezultatul in formatul dorit

    def conversion_using_intermediary_base(self,new_base):
        """
        Functia exemplifica conversia utilizand o baza intermediara
        :param new_base: baza destinatie
        """
        if self.__base==new_base:
            return
        #trecem prima data prin substitutie in baza 10
        exp=1
        rez=0
        self.__nr.reverse()
        for elem in self.__nr:
            rez+=exp*get_digit(elem)
            exp*=self.__base

        #trecem prin impartiri succesive, rez in baza new_base
        new_nr=[]
        while rez!=0:
            rest=rez%new_base #selectam restul si il adaugam intr-o lista
            rez//=new_base    #imaprtim cu noua baza, in asa fel incat sa trecem la pasul urmator
            new_nr.append(set_digit(rest))
        new_nr.reverse()

        self.__nr=new_nr
        self.__base=new_base #actualizam baza

    def fast_conversions(self,new_base):
        """
        Functia exemplifica conversiile rapide din baza 2 in baza 4,8,16 si din 4,8,16 in baza 2
        :param new_base:
        :return:
        """
        if self.__base==new_base:
            return
        if self.__base==2:
            ord=0
            cpy=new_base
            while cpy!=1: #deteminam putrea la care se afla 2 in descompunerea numaraului
                cpy/=2
                ord+=1
            self.__nr.reverse() #inversam sirul pentru a putea efectua operatiile
            ln=len(self.__nr)
            for _ in range(4):
                self.__nr.append('0') #adaugam 0-uri astfel incat sa nu depasim range-ul
            index=0
            rez=[]
            while index<ln:#tratam fiecare caz, transforamnd biti in valorile corespunzatoare
                if ord==2:
                    rez.append(set_digit(get_digit(self.__nr[index])+2*get_digit(self.__nr[index+1])))
                    index+=2
                if ord==3:
                    rez.append(set_digit(get_digit(self.__nr[index])+2*get_digit(self.__nr[index+1])+4*get_digit(self.__nr[index+2])))
                    index+=3
                if ord==4:
                    rez.append(set_digit(get_digit(self.__nr[index])+2*get_digit(self.__nr[index+1])+4*get_digit(self.__nr[index+2])+8*get_digit(self.__nr[index+3])))
                    index+=4
            while len(rez)!=0 and rez[len(rez)-1]=='0': #eliminam eventualele 0-uri de la inceput de numar
                rez.pop()
            rez.reverse()
            self.__nr=rez
            self.__base=new_base

        rez=[]
        if new_base==2:
            ord=0
            cpy=self.__base
            while cpy!=1:#determinam din nou puterea la care se afla 2 in descompunerea numarului
                cpy/=2
                ord+=1
            for elem in self.__nr:
                partial_rez=[]
                while elem!=0:  #convertim cifra actuala a numarului in corespondentul din baza 2
                    elem=get_digit(elem)
                    partial_rez.append(elem%2)
                    elem//=2
                while len(partial_rez)!=ord: #o aducem la lungimea corespunzatoare
                    partial_rez.append(0)
                partial_rez.reverse()
                rez+=partial_rez
            while len(rez)!=0 and rez[0]==0: #eliminam eventualele 0-uri de la inceputul cuvantului
                rez.pop(0)
            self.__nr=[str(elem) for elem in rez] #modificam valorile
            self.__base=new_base



#partea in care se testeaza automat toate functiile( se foloseste nosettest din pyton)
def test_fast_conversions():
    nr=number("1010001011110000",2)
    nr.fast_conversions(16)

    assert nr.getNr_as_list()==['A','2','F','0']

    nr=number("1010001011110000",2)
    nr.fast_conversions(8)

    assert nr.getNr_as_list()==['1','2','1','3','6','0']
    assert nr.getBase()==8

    nr=number("1010001011110000",2)
    nr.fast_conversions(4)

    assert nr.getNr_as_list()==['2','2','0','2','3','3','0','0']

    nr=number("A2F0",16)
    nr.fast_conversions(2)

    assert nr.getNr_as_list()==['1','0','1','0','0','0','1','0','1','1','1','1','0','0','0','0']

    nr=number("22023300",4)
    nr.fast_conversions(2)

    assert nr.getNr_as_list()==['1','0','1','0','0','0','1','0','1','1','1','1','0','0','0','0']

    nr=number("121360",8)
    nr.fast_conversions(2)

    assert nr.getNr_as_list()==['1','0','1','0','0','0','1','0','1','1','1','1','0','0','0','0']
    assert nr.getBase()==2

def test_convert_using_intermediary_base():
    nr=number("1001110",2)
    nr.conversion_using_intermediary_base(10)

    assert nr.getNr_as_list()==['7','8']
    assert nr.getBase()==10

    nr=number("577195",10)
    nr.conversion_using_intermediary_base(15)

    assert nr.getNr_as_list()==['B','6','0','4','A']
    assert nr.getBase()==15

    nr=number("2101",3)
    nr.conversion_using_intermediary_base(7)

    assert nr.getNr_as_list()==['1','2','1']

    nr=number("12054",6)
    nr.conversion_using_intermediary_base(4)

    assert nr.getNr_as_list()==['1','2','3','2','0','2']
    assert nr.getBase()==4

    nr=number("58936",10)
    nr.conversion_using_intermediary_base(3)

    assert nr.getNr_as_list()==['2','2','2','2','2','1','1','2','1','1']
    assert nr.getBase()==3

    nr=number("266",7)
    nr.conversion_using_intermediary_base(3)

    assert nr.getNr_as_list()==['1','2','1','0','2']

    nr=number("A3B2C1",16)
    nr.conversion_using_intermediary_base(8)

    assert nr.getNr_as_list()==['5','0','7','3','1','3','0','1']

    nr=number("A3B2C1",16)
    nr.conversion_using_intermediary_base(3)

    assert nr.getNr_as_list()==['2','0','2','0','1','2','0','0','1','0','2','0','0','1','0']

    nr=number("A3B2C1",16)
    nr.conversion_using_intermediary_base(2)

    assert nr.getNr_as_list()==['1','0','1','0', '0','0','1','1', '1','0','1','1', '0','0','1','0', '1','1','0','0', '0','0','0','1']

    nr=number("A3B2C1",16)
    nr.conversion_using_intermediary_base(9)

    assert nr.getNr_as_list()==['2','2','1','6','1','2','0','3']

def test_convert_by_substitution():
    nr=number("AB",16)
    nr.convert_by_substitution(10)

    assert nr.getNr_as_list()==['1','7','1']
    assert nr.getBase()==10

    nr=number("A",16)
    nr.convert_by_substitution(10)

    assert nr.getNr_as_list()==['1','0']
    assert nr.getBase()==10

    nr=number("1001110",2)
    nr.convert_by_substitution(10)

    assert nr.getNr_as_list()==['7','8']
    assert nr.getBase()==10

    nr=number("577195",10)
    nr.convert_by_substitution(15)

    assert nr.getNr_as_list()==['B','6','0','4','A']
    assert nr.getBase()==15

    nr=number("2101",3)
    nr.convert_by_substitution(7)

    assert nr.getNr_as_list()==['1','2','1']

    nr=number("12054",6)
    nr.convert_by_substitution(4)

    assert nr.getNr_as_list()==['1','2','3','2','0','2']
    assert nr.getBase()==4

    nr=number("58936",10)
    nr.convert_by_substitution(3)

    assert nr.getNr_as_list()==['2','2','2','2','2','1','1','2','1','1']
    assert nr.getBase()==3

    nr=number("266",7)
    nr.convert_by_substitution(3)

    assert nr.getNr_as_list()==['1','2','1','0','2']

    nr=number("A3B2C1",16)
    nr.convert_by_substitution(8)

    assert nr.getNr_as_list()==['5','0','7','3','1','3','0','1']

    nr=number("A3B2C1",16)
    nr.convert_by_substitution(3)

    assert nr.getNr_as_list()==['2','0','2','0','1','2','0','0','1','0','2','0','0','1','0']

    nr=number("A3B2C1",16)
    nr.convert_by_substitution(2)

    assert nr.getNr_as_list()==['1','0','1','0', '0','0','1','1', '1','0','1','1', '0','0','1','0', '1','1','0','0', '0','0','0','1']

    nr=number("A3B2C1",16)
    nr.convert_by_substitution(9)

    assert nr.getNr_as_list()==['2','2','1','6','1','2','0','3']

def test_convert_by_division():
    nr=number("A",16)
    nr.convert_by_division(10)

    assert nr.getNr_as_list()==['1','0']
    assert nr.getBase()==10

    nr=number("AB",16)
    nr.convert_by_division(10)

    assert nr.getNr_as_list()==['1','7','1']
    assert nr.getBase()==10

    nr=number("12054",6)
    nr.convert_by_division(4)

    assert nr.getNr_as_list()==['1','2','3','2','0','2']
    assert nr.getBase()==4

    nr=number("58936",10)
    nr.convert_by_division(3)

    assert nr.getNr_as_list()==['2','2','2','2','2','1','1','2','1','1']
    assert nr.getBase()==3

    nr=number("266",7)
    nr.convert_by_division(3)

    assert nr.getNr_as_list()==['1','2','1','0','2']

    nr=number("577195",10)
    nr.convert_by_division(15)

    assert nr.getNr_as_list()==['B','6','0','4','A']
    assert nr.getBase()==15

    nr=number("2101",3)
    nr.convert_by_division(7)

    assert nr.getNr_as_list()==['1','2','1']

    nr=number("A3B2C1",16)
    nr.convert_by_division(8)

    assert nr.getNr_as_list()==['5','0','7','3','1','3','0','1']

    nr=number("A3B2C1",16)
    nr.convert_by_division(3)

    assert nr.getNr_as_list()==['2','0','2','0','1','2','0','0','1','0','2','0','0','1','0']

    nr=number("A3B2C1",16)
    nr.convert_by_division(2)

    assert nr.getNr_as_list()==['1','0','1','0', '0','0','1','1', '1','0','1','1', '0','0','1','0', '1','1','0','0', '0','0','0','1']

    nr=number("A3B2C1",16)
    nr.convert_by_division(9)

    assert nr.getNr_as_list()==['2','2','1','6','1','2','0','3']

def test_div_by_digit():
    nr=number("512",10)
    rez=nr.div_by_digit("2")

    assert rez==[[2,5,6],0]

    nr=number("20101",3)
    rez=nr.div_by_digit("2")

    assert rez==[[1,0,0,1,2],0]

    nr=number("120456",8)
    rez=nr.div_by_digit("6")

    assert rez==[[1,5,3,3,5],0]

    nr=number("2A0F86",16)
    rez=nr.div_by_digit("E")

    assert rez==[[3,0,1,1,11],12]

    nr=number("1FED0205",16)
    rez=nr.div_by_digit("9")

    assert rez==[[3,8,12,1,12,10,11],2]

    try:
        rez=nr.div_by_digit("1E")
        assert False
    except ValueError:
        assert True

def test_add_nr():
    nr=number("1234",10)
    nr2=number("1111",10)
    rez=nr.add_nr(nr2.getNr_as_list())

    assert rez==[2,3,4,5]

    nr=number("23045",6)
    nr2=number("100254",6)
    rez=nr.add_nr(nr2.getNr_as_list())

    assert rez==[1,2,3,3,4,3]

    nr=number("54AB6F",16)
    nr2=number("CD097D",16)
    rez=nr.add_nr(nr2.getNr_as_list())

    assert rez==[1,2,1,11,4,14,12]

    nr=number("ABCDE",16)
    nr2=number("D9037F",16)
    rez=nr.add_nr(nr2.getNr_as_list())

    assert rez==[14,3,12,0,5,13]

    nr=number("1100101011",2)
    nr2=number("11101101",2)
    rez=nr.add_nr(nr2.getNr_as_list())

    assert rez==[1,0,0,0,0,0,1,1,0,0,0]

def test_mul_by_digits():
    nr=number("1234",10)
    rez=nr.mul_by_digit("2")

    assert rez==[2,4,6,8]

    nr=number("7023",8)
    rez=nr.mul_by_digit("5")

    assert rez==[4,3,1,3,7]

    nr=number("32001B",16)
    rez=nr.mul_by_digit("6")

    assert rez==[1,2,12,0,0,10,2]

    nr=number("A23F4",16)
    rez=nr.mul_by_digit("B")

    assert rez==[6,15,8,11,7,12]

    nr=number("31203",4)
    rez=nr.mul_by_digit("3")

    assert rez==[2,2,0,2,2,1]

    try:
        rez=nr.mul_by_digit("13")
        assert False
    except ValueError:
        assert True

def test_sub_nr():
    nr=number("501BA",16)
    nr2=number("32ED",16)
    rez=nr.sub_nr(nr2.getNr_as_list())

    assert rez==[4,12,14,12,13]

    nr=number("102387",9)
    nr2=number("64502",9)
    rez=nr.sub_nr(nr2.getNr_as_list())

    assert rez==[2,6,7,8,5]

    nr=number("10001100010",2)
    nr2=number("1110111011",2)
    rez=nr.sub_nr(nr2.getNr_as_list())

    assert rez==[1,0,1,0,0,1,1,1]

    try:
        nr=number("10001100010",2)
        nr2=number("10001110111011",2)
        rez=nr.sub_nr(nr2.getNr_as_list())
        assert False
    except ValueError:
        assert True

def test_number():
    nr=number("12353",7)

    assert nr.getBase()==7
    assert nr.getNr_as_decimal()=="12353"
    assert nr.getNr_as_list()==['1','2','3','5','3']

    try:
        nr=number("1256",5)
        assert False
    except ValueError:
        assert True

def test_cmp():
    assert cmp([1,2,3,4],[9,8,7])==1
    assert cmp([1,2],[1,2])==1
    assert cmp(['1','2','A'],['1','2','B'])==0

def test_division_with_number():
    nr=number("2312",4)
    assert nr.division_with_number(['1','3'])==[['1','2','2'],[0,0]]

    nr=number("2312",4)
    assert nr.division_with_number(['1','1'])==[['2','1','0'],[0,2]]

    nr=number("2320",4)
    assert nr.division_with_number(['1','1'])==[['2','1','0'],[1,0]]

    nr=number("A3B4C2",16)
    rez=nr.division_with_number(['2','A'])

    assert rez[0]==['3','E','5','D','3']

def test_mul_by_number():
    nr=number("1234",10)
    rez=nr.mul_by_number(['2'])

    assert rez==['2','4','6','8']

    nr=number("1234",10)
    rez=nr.mul_by_number(['2','0'])

    assert rez==['2','4','6','8','0']

    nr=number("125",10)
    rez=nr.mul_by_number(['2','3'])

    assert rez==['2','8','7','5']

    nr=number("253",6)
    rez=nr.mul_by_number(['3','5'])

    assert rez==['1','5','1','0','3']

    nr=number("A3B4C2",16)
    rez=nr.mul_by_number(['2','A'])

    assert rez==['1','A','D','B','A','7','D','4']

#user-interface and connected functions
def list_to_string(the_list):
    ret=""
    for elem in the_list:
        ret+=set_digit(elem)
    return ret

def list_to_nr(the_list):
    ret=""
    for elem in the_list:
        ret+=set_digit(elem)
    return ret

def calculator():
    """
    Functia implementeaza interfata cu utilizatorul pentru calculator
    """
    #afisam meniul
    print("Calculatorul permite urmatoarele functionalitati: ")
    print("adunare(simbol: +)")
    print("scadere(simbol: -)")
    print("inmultire(simbol: *)")
    print("impartire(simbol: /)")
    print("Nota: Operatiile de inmultire si impartire sunt posibile doar cu o cifra")
    op=str(input("Simbolul operatiei dorite este: "))

    # avertizam utilizatorul cu privire la anumite restrictii
    if op=='-':
        print("Primul numar trebuie sa fie mai mare decat al doilea!")
    if op=='*' or op=='/':
        print("Al doilea operand nu trebuie sa fie mai mare de o cifra in baza destinatie")

    print("Calculatorul permite lucrul cu 2 numere in baze diferite la un moment dat")
    n1=input("Introduceti primul numar: ")
    base1=input("Introduceti baza numarului(un nr. intre 2 si 10 sau 16): ")
    n2=input("Introduceti al-doilea numar: ")
    base2=input("Introduceti baza numarului(un nr. intre 2 si 10 sau 16): ")
    main_base=int(input("Introduceti baza de lucru(un nr. intre 2 si 10 sau 16): "))

    nr1=number(n1,int(base1))
    print(str(nr1.getNr_as_decimal())+'('+str(nr1.getBase())+')=',end="")
    nr1.conversion_using_intermediary_base(main_base)
    print(str(nr1.getNr_as_decimal()) + '(' + str(nr1.getBase()) + ')')

    nr2=number(n2,int(base2))
    print(str(nr2.getNr_as_decimal())+'('+str(nr2.getBase())+')=',end="")
    nr2.conversion_using_intermediary_base(main_base)
    print(str(nr2.getNr_as_decimal()) + '(' + str(nr2.getBase()) + ')')

    if op=="+":
        print(str(nr1.getNr_as_decimal())+'('+str(nr1.getBase())+')+'+str(nr2.getNr_as_decimal())+'('+str(nr2.getBase())+')=',end="")
        print(list_to_string(nr1.add_nr(nr2.getNr_as_list())),end="")
        print('('+str(main_base)+')')
    elif op=='-':
        if cmp(nr1.getNr_as_list(),nr2.getNr_as_list())==0:
            raise ValueError("Operanzii nu respecta conditiile impuse")
        print(str(nr1.getNr_as_decimal())+'('+str(nr1.getBase())+')-'+str(nr2.getNr_as_decimal())+'('+str(nr2.getBase())+')=',end="")
        print(list_to_string(nr1.sub_nr(nr2.getNr_as_list())),end="")
        print('(' + str(main_base) + ')')
    elif op=='*':
        if len(nr2.getNr_as_list())>1:
            raise ValueError("Nu se poate efectua inmultirea cu mai mult de o cifra")
        print(str(nr1.getNr_as_decimal())+'('+str(nr1.getBase())+')*'+str(nr2.getNr_as_decimal())+'('+str(nr2.getBase())+')=',end="")
        rez=nr1.mul_by_digit(nr2.getNr_as_list())
        print(list_to_nr(rez),end="")
        print('(' + str(main_base) + ')')
    elif op=='/':
        if len(nr2.getNr_as_list())>1:
            raise ValueError("Nu se poate efectua inmultirea cu mai mult de o cifra")
        print(str(nr1.getNr_as_decimal())+'('+str(nr1.getBase())+')/'+str(nr2.getNr_as_decimal())+'('+str(nr2.getBase())+')=',end="")
        rez,rest=nr1.div_by_digit(nr2.getNr_as_list())
        print(list_to_nr(rez),end="")
        print('(' + str(main_base) + ')',end="")
        print(" rest "+set_digit(rest),end="")
        print('(' + str(main_base) + ')')

def convertor():
    """
    Functia relizeaza interfata cu utilizatorul pentru UI
    """
    print("Sunt disponibile uramtoarele tipuri de conversie(pentru accesare, tastati cifra coresp.): ")
    print("1.Conversia prin impartiri succesive")
    print("2.Conversia prin substitutie")
    print("3.Conversia printr-o baza intermediara")
    print("4.Conversii rapide")
    op=input("Optiunea dvs. este: ")

    if op=="4":
        print("Conversiile rapide sunt disponibile doar din baza 2 in 4,8,16 sau din 4,8,16 in baza 2!")

    print("Convertorul permite lucrul cu 1 numar la un moment dat")
    n=input("Introduceti numarul: ")
    base=input("Introduceti baza numarului(un nr. intre 2 si 10 sau 16): ")
    main_base=int(input("Introduceti baza destinatie(un nr. intre 2 si 10 sau 16): "))

    nr=number(n,int(base))

    if op=="1":
        nr.convert_by_division(main_base)
        print(nr.getNr_as_decimal(),end="")
        print('('+str(nr.getBase())+')')
    elif op=='2':
        nr.convert_by_substitution(main_base)
        print(nr.getNr_as_decimal(),end="")
        print('('+str(nr.getBase())+')')
    elif op=='3':
        nr.conversion_using_intermediary_base(main_base)
        print(nr.getNr_as_decimal(),end="")
        print('('+str(nr.getBase())+')')
    elif op=='4':
        if (nr.getBase()==2 and (main_base==4 or main_base==8 or main_base==16)) or ((nr.getBase()==4 or nr.getBase()==8 or nr.getBase()==16) and main_base==2):
            nr.conversion_using_intermediary_base(main_base)
            print(nr.getNr_as_decimal(),end="")
            print('('+str(nr.getBase())+')')
        else:
            print("Datele introduse nu respecta cerintele")


def show_ui():
    """
    Functia prezinta meniul principal de interactiune cu utilizatorul
    """
    end=True
    while end:
        print("Bine ati venit")
        print("Moduri disponibile(pentru accesarea lor, se tasteaza cifra din dreptul lor, fara punct):")
        print("1.Calculator")
        print("2.Convertor",end="\n\n")
        print("0.Exit")

        option=input("Introduceti optiunea: ")
        if option=='1':
            try: #prindem eventualele erori, date de introducerea gresita datelor
                calculator()
            except ValueError as ve:
                print(ve)
        elif option=='2':
            try:
                convertor()
            except ValueError as ve:
                print(ve)
        elif option=='0':
            end=False
        else:
            print("Optiune invalida")

show_ui()
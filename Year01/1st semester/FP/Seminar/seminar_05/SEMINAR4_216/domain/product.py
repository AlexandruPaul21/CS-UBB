def create_product(denumire, unitati_stoc, pret):
    """
    Creeaza un produs dulce cu atributele date
    :param denumire: denumirea data
    :type denumire: string
    :param unitati_stoc: numarul de unitati
    :type unitati_stoc: int
    :param pret: pretul dat
    :type pret: int
    :return: produsul cu atributele date
    :rtype: list (len(lst)=3, lst[0] = denumire, lst[1] = unitati stoc, lst[2]= pret)
    """
    return [denumire, unitati_stoc, pret]


# A.Validarea unui produs inainte de adaugare. Sa se verifice ca numele produsului
# nu este vid, ca numarul de unitati din stoc este un numar natural pozitiv,
# iar pretul este un numar mai mare de 0.

def validate_product(p):
    """
    Valideaza un produs dat
    :param p: produsul dat
    :type p: obiect de tip produs
    :return:
    :rtype:
    :raises: ValueError daca datele produsului sunt invalide
    """
    errors = []
    if get_denumire(p) == '':
        errors.append("Denumirea produsului nu poate fi vida.")
    if get_stoc(p) < 0:
        errors.append("Numarul de unitati din stoc nu poate fi negativ.")
    if get_pret(p) < 0:
        errors.append("Pretul nu poate fi negativ.")

    if len(errors) > 0:
        error_string = '\n'.join(errors)
        raise ValueError(error_string)
        # raise ValidationException


def is_same_product(p1, p2):
    """
    Verifica daca produsele date sunt identice
    :param p1: primul produs
    :type p1: obiect de tip produs (list)
    :param p2: al doilea produs
    :type p2: obiect de tip produs (list)
    :return: True daca produsele sunt identice, False altfel
    :rtype: bool
    """
    if get_denumire(p1) == get_denumire(p2) and get_pret(p1) == get_pret(p2):
        return True
    return False


# getters
def get_denumire(product):
    return product[0]


def get_stoc(product):
    return product[1]


def get_pret(product):
    return product[2]


# setters
def set_denumire(product, new_denumire):
    product[0] = new_denumire


def set_stoc(product, new_stoc):
    product[1] = new_stoc


def set_pret(product, new_pret):
    product[2] = new_pret


def test_create():
    p1 = create_product('acadele', 15, 8.5)
    assert (type(p1) == list)

    assert (p1[0] == 'acadele')
    assert (p1[1] == 15)
    assert (p1[2] == 8.5)

    # in momentul in care vor fi si validari/exceptii
    # aici trebuie verificate si cazurile cu input
    # gresit (exceptiile)


def test_validate_product():
    p1 = create_product('', 15, 8.5)
    try:
        validate_product(p1)
    except ValueError as ve:
        assert (str(ve) == "Denumirea produsului nu poate fi vida.")

    p2 = create_product('acadele', -15, 8.5)
    try:
        validate_product(p2)
    except ValueError as ve:
        assert (str(ve) == "Numarul de unitati din stoc nu poate fi negativ.")

    p3 = create_product('acadele', 15, -8.5)
    try:
        validate_product(p3)
    except ValueError as ve:
        assert (str(ve) == "Pretul nu poate fi negativ.")

    p4 = create_product('', -1, 7.7)
    try:
        validate_product(p4)
    except ValueError as ve:
        assert (str(ve) == "Denumirea produsului nu poate fi vida.\nNumarul de unitati din stoc nu poate fi negativ.")


def test_is_same_product():
    p1 = create_product('acadele', 15, 8.5)
    p2 = create_product('acadele', 15, 8.5)
    assert (is_same_product(p1, p2) == True)

    p3 = create_product('acadele', 15, 9.5)
    assert (is_same_product(p1, p3) == False)

    p4 = create_product('jeleuri', 15, 8.5)
    assert (is_same_product(p1, p4) == False)

    p5 = create_product('jelueri',20, 3.4)
    assert (is_same_product(p1, p5) == False)



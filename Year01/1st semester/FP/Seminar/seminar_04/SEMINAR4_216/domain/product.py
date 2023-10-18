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

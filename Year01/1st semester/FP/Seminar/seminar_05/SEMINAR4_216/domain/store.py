from domain.product import get_stoc, get_denumire, create_product, validate_product, is_same_product
from utils.list_operations import make_list_copy, add_to_list


def generate_products():
    # stoc 0: 2
    # denumire: 'jel':2
    # denumire: 'cioco':1
    # denumire: 'acadele': 0
    return [['jelly beans', 12, 9.5], ['ciocolata', 16, 10.25], ['licorice', 1, 65],
            ['bomboane Halloween', 0, 13], ['vata de zahar', 0, 11], ['dropsuri', 100, 1.25],
            ['jeleuri haribo', 18, 5.7]]


def setup_store(add_predefined):
    """
    Initializeaza un obiect de tip magazin
   :param add_predefined: indicator pentru adaugarea dulciurilor predefinite (daca True
            lista initiala este cea care contine dulciurile predefinite, altfel, lista initiala de produse este goala)
    :type add_predefined: bool
    :return: lista in care primul element reprezinta lista curenta de produse, si al doilea element lista pentru undo
    :rtype: list
    """
    if add_predefined:
        product_list = generate_products()
    else:
        product_list = []
    undo_list = []
    return [product_list, undo_list]


# getters
def get_products_list(store):
    return store[0]


def get_undo_list(store):
    return store[1]


# setters
def set_products_list(store, new_products_list):
    store[0] = new_products_list


def set_undo_list(store, new_undo_list):
    store[1] = new_undo_list


# functions that implement domain logic
def remove_from_list_stock_zero(product_list):
    """
    Elimina din lista produsele care nu sunt in stoc
    :param product_list: lista de produse
    :type product_list: list (of lists)
    :return: lista din care au fost eliminate produsele cu stoc 0
    :rtype: list (of lists)
    """
    new_list = [product for product in product_list if get_stoc(product) != 0]
    return new_list


def filter_by_name(product_list, my_substring):
    """
    Gaseste produsele care contin in denumire un substring dat
    :param product_list: lista de produse
    :type product_list: list (of lists)
    :param my_substring: substring-ul dat
    :type my_substring: string
    :return: lista cu produsele care contin in denumire un substring dat
    :rtype: list (of lists)
    """
    filtered_list = []
    if my_substring != '':
        for el in product_list:
            if get_denumire(el).find(my_substring) != -1:
                filtered_list.append(el)
    return filtered_list


def exists_product(product_list, product):
    """
    Verifica daca exista produsul product in lista
    :param product_list: lista de produse data
    :type product_list: list (of lists)
    :param product: produsul pentru care verificam
    :type product: list
    :return: True daca produsul exista deja in lista, False altfel
    :rtype: bool
    """
    for p in product_list:
        if is_same_product(p, product):
            return True
    return False


def add_product_to_store(store, denumire, unitati, pret):
    """
    Adauga un produs in magazin
    :param store: obiect de tip magazin
    :type store: list (len(store)=2, store[0] - lista curenta de produse, store[1] - lista de undo)
    :param product: produsul care se adauga
    :type product: list
    :return: lista curenta de produse din magazin se modifica prin adaugarea produsului dat
    :rtype:
    :raises: ValueError daca produsul de adaugat este invalid
    """
    # need to save the current state of the product list
    # before modifying it by adding a product
    # I save it in the undo list

    product = create_product(denumire, unitati, pret)
    validate_product(product)

    crt_product_list = get_products_list(store)
    if not exists_product(crt_product_list, product):
        undo_list = get_undo_list(store)

        undo_list.append(make_list_copy(crt_product_list))
        add_to_list(get_products_list(store), product)
    else:
        raise ValueError('Produsul exista deja in magazin.')


def delete_products_from_store(store):
    """
    Sterge produsele cu stoc epuizat din magazin
    :param store: obiect de tip magazin
    :type store: list (len(store)=2, store[0] - lista curenta de produse, store[1] - lista de undo)
    :return: lista de produse curenta se modifica prin eliminarea celor cu stoc 0
    :rtype:
    """
    # similar with add - need to save current state of the list
    # current state of list= state of product list before removing products
    # I save it in the undo list and then do the deletion
    crt_product_list = get_products_list(store)
    undo_list = get_undo_list(store)

    undo_list.append(make_list_copy(crt_product_list))
    # the actual remove
    set_products_list(store, remove_from_list_stock_zero(crt_product_list))


def undo(store):
    """
    Face undo la ultima operatie de adaugare sau stergere
    :param store: magazinul curent
    :type store: list (len(list)=2, list[0] - lista curenta de produse, list[1] - lista de undo
    :return: lista curenta se modifica prin revenire la starea listei inainte de operatie
    :rtype: -;
    :raises: ValueError daca nu se mai poate face undo
    """
    # crt_product_list = get_products_list(store)
    undo_list = get_undo_list(store)
    if len(undo_list) == 0:
        raise ValueError("Nu se mai poate face undo.")
    else:
        previous_list = undo_list[-1]
        set_products_list(store, previous_list)
        set_undo_list(store, undo_list[:-1])


# tests
def test_remove_from_list_stock():
    test_list1 = generate_products()
    initial_length = len(test_list1)
    test_list1 = remove_from_list_stock_zero(test_list1)

    assert (len(test_list1) == initial_length - 2)
    assert (len([product for product in test_list1 if get_stoc(product) == 0]) == 0)
    crt_length = len(test_list1)

    test_list1 = remove_from_list_stock_zero(test_list1)
    assert (len(test_list1) == crt_length)

    test_list2 = []
    test_list2 = remove_from_list_stock_zero(test_list2)
    assert (len(test_list2) == 0)


def test_filter_by_name():
    test_list = []
    filtered_list1 = filter_by_name(test_list, 'jel')
    assert (len(filtered_list1) == 0)

    test_list2 = generate_products()

    filtered_list2 = filter_by_name(test_list2, 'jel')
    assert (len(filtered_list2) == 2)
    assert (get_denumire(filtered_list2[0]) == 'jelly beans')

    filtered_list3 = filter_by_name(test_list2, 'cioco')
    assert (len(filtered_list3) == 1)
    assert (get_denumire(filtered_list3[0]) == 'ciocolata')

    filtered_list4 = filter_by_name(test_list2, 'acadea')
    assert (len(filtered_list4) == 0)

    filtered_list5 = filter_by_name(test_list2, '')
    assert (len(filtered_list5) == 0)


def test_add_product_to_store():
    # need to also test this newly added fn
    test_store = setup_store(False)

    add_product_to_store(test_store, 'acadele', 15, 8.5)
    assert (len(get_products_list(test_store)) == 1)

    add_product_to_store(test_store, 'jeleuri', 26, 5.6)
    assert (len(get_products_list(test_store)) == 2)

    try:
        add_product_to_store(test_store, '', -8, 6.3)
        assert False
    except ValueError:
        assert True

    try:
        add_product_to_store(test_store, 'acadele', 15, 8.5)
        assert False
    except ValueError:
        assert True


def test_exists_product():
    test_store = setup_store(True)
    p1 = create_product('jelly beans', 12, 9.5)
    p2 = create_product('jelly beans portocale', 14, 10.25)
    assert (exists_product(get_products_list(test_store), p1) == True)
    assert (exists_product(get_products_list(test_store), p2) == False)


def test_delete_products_from_store():
    # need to also test this newly added fn
    test_store = setup_store(False)


    add_product_to_store(test_store,'acadele', 0, 8.5)
    add_product_to_store(test_store, 'jeleuri', 26, 5.6)
    assert (len(get_products_list(test_store)) == 2)
    delete_products_from_store(test_store)
    assert (len(get_products_list(test_store)) == 1)


def test_undo():
    # need to test the undo fn
    test_store = setup_store(False)


    add_product_to_store(test_store, 'acadele', 15, 8.5)
    assert (len(get_products_list(test_store)) == 1)
    undo(test_store)
    assert (len(get_products_list(test_store)) == 0)

    test_store2 = setup_store(False)


    add_product_to_store(test_store2, 'acadele', 15, 8.5)

    add_product_to_store(test_store2, 'jeleuri', 26, 5.6)

    undo(test_store2)
    assert (len(get_products_list(test_store2)) == 1)
    undo(test_store2)
    assert (len(get_products_list(test_store2)) == 0)

    try:
        undo(test_store2)
        assert False
    except ValueError:
        assert True

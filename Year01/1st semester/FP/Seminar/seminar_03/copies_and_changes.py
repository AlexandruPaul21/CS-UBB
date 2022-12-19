def make_list_of_lists_copy(lst):
    cpy = []
    for el in lst:
        cpy.append(el[:])
    return cpy

def make_dict_list_copy(lst):
    cpy = []
    for el in lst:
        crt_elem_dict = {}
        for key, value in el.items():
            crt_elem_dict[key] = value
        cpy.append(crt_elem_dict)
        
            
    return cpy
    
my_list = [[1,2,3],[4,5,6]]
my_list_copy1 = my_list.copy()
my_list_copy2 = my_list[:]

#we change a value in the initial list
my_list[0][1] = 88

#does the change reflect in copies?

my_list_my_copy = make_list_of_lists_copy(my_list)
my_list[0][2]=22

#how about if I wrote my own copy function?

list_of_dicts = [{'nume':'a','punctaj':22}, {'nume':'xyz','punctaj':100}]
list_of_dicts_copy1= list_of_dicts.copy()
list_of_dicts_copy2= list_of_dicts[:]

list_of_dicts_my_copy = make_dict_list_copy(list_of_dicts)

list_of_dicts[0]['punctaj']=79

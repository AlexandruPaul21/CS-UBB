"""
Pregatire de examen
"""
arr=[1,3,2,6,5,7,8,10]

def selection_sort(lst):
    for i in range(len(lst)-1):
        ind=i
        for j in range(i,len(lst)):
            if lst[j]<lst[ind]:
                ind=j
        if i<ind:
            lst[i],lst[ind]=lst[ind],lst[i]
    return lst

#print(selection_sort(arr))

def direct_selection_sort(lst):
    for i in range(len(lst)-1):
        for j in range(i+1,len(lst)):
            if lst[i]>lst[j]:
                lst[i],lst[j]=lst[j],lst[i]
    return lst

#print(direct_selection_sort(arr))

def insertion_sort(lst):
    for i in range(len(lst)):
        ind=i-1
        aux=lst[i]
        while ind>=0 and lst[ind]>aux:
            lst[ind+1]=lst[ind]
            ind-=1
        lst[ind+1]=aux
    return lst

#print(insertion_sort(arr))

def bubble_sort(lst):
    sorted=False
    while not sorted:
        sorted=True
        for i in range(len(lst)-1):
            if lst[i]>lst[i+1]:
                lst[i],lst[i+1]=lst[i+1],lst[i]
                sorted=False
    return lst

#print(bubble_sort(arr))

#quick_sort classic

def partition(lst,left,right):
    pivot=lst[left]
    i=left
    j=right
    while i!=j:
        while lst[j]>=pivot and i<j:
            j-=1
        lst[i]=lst[j]
        while lst[i]<=pivot and i<j:
            i+=1
        lst[j]=lst[i]
    lst[i]=pivot
    return i

def qSort(lst,left,right):
    if left>right:
        return
    pos=partition(lst,left,right)

    if left<pos-1:
        qSort(lst,left,pos-1)
    if pos+1<right:
        qSort(lst,pos+1,right)

#qSort(arr,0,len(arr)-1)
#print(arr)

#quick_sort python
def quick_sort(lst):
    if len(lst)<=1:
        return lst
    pivot=lst.pop()
    lesser=quick_sort([x for x in lst if x<pivot])
    greater=quick_sort([x for x in lst if x>=pivot])
    return lesser+[pivot]+greater

#print(quick_sort(arr))

#merge_sort
def merge(lst1,lst2):
    i=0
    j=0
    rez=[]
    while i<len(lst1) and j<len(lst2):
        if lst1[i]<lst2[j]:
            rez.append(lst1[i])
            i+=1
        else:
            rez.append(lst2[j])
            j+=1
    rez.extend(lst1[i:])
    rez.extend(lst2[j:])
    return rez

def merge_sort(lst):
    if len(lst)<=1:
        return lst
    mid=len(lst)//2
    low_part=merge_sort(lst[:mid])
    high_part=merge_sort(lst[mid:])
    return merge(low_part,high_part)

arr=merge_sort(arr)

def suc_Search(lst,value):
    i=0
    found=False
    while i<len(lst) and not found:
        if lst[i]==value:
            found=True
        i+=1
    if i==len(lst):
        return -1
    return i-1

def seq_search(lst,value):
    poz=-1
    for i in range(len(lst)):
        if lst[i]==value:
            poz=i
    return poz

def it_bin_search(lst,value):
    st=0
    dr=len(lst)-1
    while st<=dr:
        mid=(st+dr)//2
        if value==lst[mid]:
            return mid
        if value<lst[mid]:
            dr=mid-1
        else:
            st=mid+1
    return dr

def rec_bin_search(lst,left,right,value):
    if left>=right:
        return right
    mid=(left+right)//2
    if lst[mid]==value:
        return mid
    if value<lst[mid]:
        return rec_bin_search(lst,left,mid-1,value)
    else:
        return rec_bin_search(lst,mid+1,right,value)

def man_bin_Search(lst,value):
    if len(lst)==0:
        return 0
    if value<lst[0]:
        return 0
    if value>lst[len(lst)-1]:
        return len(lst)
    st=0
    dr=len(lst)-1
    while dr-st>1:
        mid=(st+dr)//2
        if value<=lst[mid]:
            dr=mid
        else:
            st=mid
    return dr

def man_coord_binsearch(lst,value):
    if len(lst)==0:
        return 0
    if value<lst[0]:
        return 0
    if value>lst[len(lst)-1]:
        return len(lst)

    return man_rec_bin_search(lst,0,len(lst)-1,value)

def man_rec_bin_search(lst,left,right,value):
    if right-left<=1:
        return right
    mid=(left+right)//2
    if value<=lst[mid]:
        return man_rec_bin_search(lst,left,mid,value)
    else:
        return man_rec_bin_search(lst,mid,right,value)

print(arr)
print(suc_Search(arr,4))
print(seq_search(arr,4))
print(it_bin_search(arr,4))
print(rec_bin_search(arr,0,len(arr)-1,4))
print(man_bin_Search(arr,4))
print(man_coord_binsearch(arr,4))

#backtracking
"""
1.Spatiu de cautare
2.Solutie candidat
3.Verif. Consistent
4.Verif. Solutie
"""
def back(sol,dim):
    sol.append(-1)
    for i in range(dim):
        sol[-1]=i
        if consistent(sol):
            if solutie(sol):
                output(sol)
            back(sol[:])

#Greedy
"""
1.Multime candidat -  multimea in care cautam
2.Functie de selectie - functia care ne ajuta sa selectam la fiecare pas cea mai convenabila solutie
3.Acceptabil - functia care ne spune daca un element poate sau nu conduce la o solutie
4.Functia obiectiv - functia care compara rezultatul pe setul construit cu rezultatul ce trebuie obtinu global
5.Functia solutie -  functia care ne ajuta sa identificam cand am ajuns la o solutie valida 
"""

#Dinamica
"""
Principiul optimalitatii: optimul global  => in optimul local 
Forward, Backword si mixt

"""
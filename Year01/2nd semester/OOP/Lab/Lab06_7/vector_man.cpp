//
// Created by Alexandru-Paul Sirbu on 05.04.2022.
//

#include "vector_man.h"
#include <iostream>

using std::exception;
using std::cout;

template <class ElemType>
Vector_Man<ElemType>::Vector_Man(const int sz) {
    //cout<<"Created "<<&elem<<"\n";
    if(sz<=0){
        throw exception();
    }
    cp=sz;
    sze=0;
    elem=new ElemType[cp];
}

template <class ElemType>
Vector_Man<ElemType>::Vector_Man() {
    //cout<<"Created "<<&elem<<"\n";
    cp=5;
    sze=0;
    elem=new ElemType[cp];
}

template <class ElemType>
Vector_Man<ElemType>::~Vector_Man() {
    //cout<<"destroy "<<&elem<<"\n";
    delete[] elem;
}

template <class ElemType>
void Vector_Man<ElemType>::push_back(const ElemType& elm) {
    ensure_cap();
    elem[sze++]=elm;
}

template <class ElemType>
void Vector_Man<ElemType>::ensure_cap() {
    if(sze<cp){
        return;
    }
    cp*=2;
    auto* temp=new ElemType[cp];
    for(int i=0; i<sze; ++i){
        temp[i]=elem[i];
    }
    delete[] elem;
    elem=temp;
}

template <class ElemType>
bool Vector_Man<ElemType>::empty() const{
    if(sze==0){
        return true;
    }
    return false;
}

template <class ElemType>
ElemType *Vector_Man<ElemType>::begin() const{
    return elem;
}

template <class ElemType>
ElemType *Vector_Man<ElemType>::end() const{
    return elem+sze-1;
}

template <class ElemType>
void Vector_Man<ElemType>::erase(const int& pos) {
    for(int i=pos; i<sze-1; ++i){
        elem[i]=elem[i+1];
    }
    --sze;
}

template <class ElemType>
int Vector_Man<ElemType>::size() const{
    return sze;
}

template<class ElemType>
ElemType& Vector_Man<ElemType>::operator[](int poz) {
    if(poz<0 || poz>sze){
        throw exception();
    }
    return elem[poz];
}

template<class ElemType>
void Vector_Man<ElemType>::copy(Vector_Man<ElemType>& ot) {
    ot.sze=0;
    for(int i=0; i<sze; ++i){
        ot.push_back(elem[i]);
    }
}

template<class ElemType>
Vector_Man<ElemType>& Vector_Man<ElemType>::operator=(const Vector_Man &ot) {
    //cout<<"from: "<<&ot.elem<<" to "<<&elem<<" copied\n";
    if(this==&ot) return *this;
    cp=ot.cp;
    sze=0;
    for(int i=0; i<ot.sze; ++i){
        elem[sze++]=ot.elem[i];
    }
    return *this;
}

//template<class ElemType>
//Vector_Man<ElemType>::Vector_Man(const Vector_Man<ElemType> &ot) {
//    cp=ot.cp;
//    sze=0;
//    for(int i=0; i<ot.sze; ++i){
//        elem[sze++]=ot.elem[i];
//    }
//}



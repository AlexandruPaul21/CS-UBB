#include "MD.h"
#include "IteratorMD.h"
#include <exception>
#include <iostream>

using namespace std;


MD::MD() {
	/* de adaugat */
    start=new Node;
    start=NULL;
}


TCheie MD::cheieMinima() const{
    auto head=start;
    if(head==NULL){
        return NULL_TCHEIE;
    }
    TCheie min1=head->key.first;
    while(head!=NULL){
        min1=min(min1,head->key.first);
        head=head->next;
    }
    return min1;
}

void MD::adauga(TCheie c, TValoare v) {
    Node* elem_nou= new Node;
    elem_nou->key= make_pair(c,v);
    elem_nou->next=NULL;
    elem_nou->prev=NULL;
    if(start==NULL){
        start=elem_nou;
        return;
    }
    auto head=start;
	while(head->next!=NULL){
        head=head->next;
    }
    head->next=elem_nou;
    elem_nou->prev=head;
}


bool MD::sterge(TCheie c, TValoare v) {
	/* de adaugat */
    if(start==NULL){
        return false;
    }
    if(start->next==NULL && start->prev==NULL){
        if(start->key== make_pair(c,v)){
            start=NULL;
            return true;
        }
        return false;
    }
    if(start->key== make_pair(c,v)){
        Node* victim=start;
        start=start->next;
        start->prev=NULL;
        delete victim;
        return true;
    }
    auto head=start;
    while(head->next!=NULL &&  head->next->key!= make_pair(c,v)){
        head=head->next;
    }
    if(head->next==NULL){
        return false;
    } else {
        head=head->next;
        head->prev->next=head->next;
        if(head->next!=NULL)
            head->next->prev=head->prev;
        delete head;
        return true;
    }
}


vector<TValoare> MD::cauta(TCheie c) const {
	/* de adaugat */
    vector<TValoare> ans;
    auto head=start;
    while(head!=NULL){
        if(head->key.first==c){
            ans.push_back(head->key.second);
        }
        head=head->next;
    }
	return ans;
}


int MD::dim() const {
	/* de adaugat */
    int size=0;
    Node* head=start;
    //for(size=0;head!=NULL;head=head->next,++size);
    while(head!=NULL){
        head=head->next;
        ++size;
    }
	return size;
}


bool MD::vid() const {
	/* de adaugat */
    if(start==NULL)
	    return true;
    return false;
}

IteratorMD MD::iterator() const {
	return IteratorMD(*this);
}

void clean_list(Node* &n){
    if(!n) return;
    clean_list(n->next);
    delete n;
    n = NULL;
}

MD::~MD() {
	/* de adaugat */
    auto head=start;
    clean_list(head);
}

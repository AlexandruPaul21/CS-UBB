//
// Created by Alexandru-Paul Sirbu on 10.06.2022.
//

#include "domain.h"

int Task::getId() const {
    return id;
}

string Task::getDesc() const {
    return desc;
}

vector<string> Task::getPro() const {
    return pro;
}

void Task::validate(int id, string desc,string state, vector<string> pro) {
    string rez="";
    if(desc==""){
        rez+="Descrierea nu poate fi vida\n";
    }
    if(!(state=="open" || state=="inprogress" || state=="closed")){
        rez+="Starea poate fi doar open,inprogress sau closed\n";
    }
    if(pro.empty()){
        rez+="Fiecare task trebuie sa aiba un programator assignat\n";
    }
    if(pro.size()>4){
        rez+="La un task nu pot lucra mai mult de 4 programtori\n";
    }
    if(rez!=""){
        throw ValidationException(rez);
    }
}

string Task::getState() const {
    return state;
}

void Task::setState(string nw) {
    state=nw;
}

void test_domain(){
    vector<string> pro{"Alex Sirbu","Liviu Berciu"};
    auto t=Task(1,"Soft Educational","open",pro);

    assert(t.getId()==1);
    assert(t.getDesc()=="Soft Educational");
    assert(t.getState()=="open");
    assert(t.getPro()==pro);

    t.setState("closed");
    assert(t.getState()=="closed");

    try{
        Task::validate(1,"","open",pro);
        assert(false);
    } catch(ValidationException& ve){
        assert(true);
    }

    try{
        Task::validate(1,"Soft","deschis",pro);
        assert(false);
    } catch(ValidationException& ve){
        assert(true);
    }

    try{
        Task::validate(1,"Soft","",pro);
        assert(false);
    } catch(ValidationException& ve){
        assert(true);
    }

    try{
        pro.clear();
        Task::validate(1,"Soft","open",pro);
        assert(false);
    } catch(ValidationException& ve){
        assert(true);
    }


}
//
// Created by Alexandru-Paul Sirbu on 10.06.2022.
//

#include "service.h"

void Service::add(int id, string desc, string state, vector<string> pro) {
    Task::validate(id,desc,state,pro);
    fr.validId(id);
    fr.add(Task(id,desc,state,pro));
    notify();
}

vector<Task> &Service::get_all() {
    return fr.get_all();
}

void Service::validId(int id) {
    fr.validId(id);
}

vector<Task> Service::filter_by_pro(string pat) {
    vector<Task> rez;
    rez.clear();
    for(auto& task:fr.get_all()){
        bool find=false;
        for(auto& pr:task.getPro()){
            if(pr.find(pat)!=string::npos){
                find=true;
            }
        }
        if(find){
            rez.push_back(task);
        }
    }
    return rez;
}

void Service::changeState(int id, string new_state) {
    fr.changeState(id,new_state);
    notify();
}

void test_service(){
    FileRepo fr{"test.txt"};
    Service srv{fr};
    assert(srv.get_all().size()==10);

    auto fl=srv.filter_by_pro("Alex");

    assert(fl.size()==5);

    auto pro=srv.get_all()[3].getPro();
    try{
        srv.add(11,"","open",pro);
        assert(false);
    } catch(ValidationException& ve){
        assert(true);
    }

    try{
        srv.add(11,"Soft","deschis",pro);
        assert(false);
    } catch(ValidationException& ve){
        assert(true);
    }

    try{
        srv.add(11,"Soft","",pro);
        assert(false);
    } catch(ValidationException& ve){
        assert(true);
    }

    try{
        srv.add(1,"Soft bun","open",pro);
        assert(false);
    } catch(DuplicatedIdException& de){
        assert(true);
    }

    try{
        pro.clear();
        srv.add(11,"Soft","open",pro);
        assert(false);
    } catch(ValidationException& ve){
        assert(true);
    }

    try{
        srv.validId(1);
        assert(false);
    } catch(DuplicatedIdException& de){
        assert(true);
    }
}
//
// Created by Alexandru-Paul Sirbu on 10.06.2022.
//

#include "repo.h"

int to_nr(string s){
    int sign=1;
    if(s[0]=='-'){
        sign=-1;
        s.erase(s.begin());
    }
    int rez=0;
    bool valid=true;
    for(auto ch:s){
        if('0'<=ch && ch<='9'){
            rez=rez*10+(ch-'0');
        } else {
            valid=false;
            break;
        }
    }
    if(!valid){
        throw ValidationException("Id neintreg");
    }
    return rez*sign;
}

void FileRepo::add(Task s) {
    tasks.push_back(s);
    save();
}

void FileRepo::load() {
    ifstream fin(filename);
    string line;

    while(getline(fin,line)){
        stringstream s(line);
        vector<string> elem;
        string el;
        while(getline(s,el,';')){
            elem.push_back(el);
        }
        stringstream s2(elem[3]);
        vector<string> pro;
        while(getline(s2,el,',')){
            pro.push_back(el);
        }
        add(Task(to_nr(elem[0]),elem[1],elem[2],pro));
    }
    fin.close();
}

vector<Task> &FileRepo::get_all() {
    return tasks;
}

void FileRepo::save() {
    ofstream fout(filename);
    for(auto& task:tasks){
        fout<<task.getId()<<";"<<task.getDesc()<<";"<<task.getState()<<";";
        bool first=true;
        for(auto& pr: task.getPro()){
            if(first){
                first=false;
            } else {
                fout<<",";
            }
            fout<<pr;
        }
        fout<<"\n";
    }
}

void FileRepo::validId(int id) {
    for(auto& task:tasks){
        if(task.getId()==id){
            throw DuplicatedIdException("Nu sunt permise mai multe elemente cu acelasi ID\n");
        }
    }
}

void FileRepo::changeState(int id, string state) {
    for(auto& task:tasks){
        if(task.getId()==id){
            task.setState(state);
        }
    }
    save();
}

void test_repo(){
    assert(to_nr("1234")==1234);
    assert(to_nr("-123")==-123);
    try{
        to_nr("5h5");
        assert(false);
    } catch(ValidationException& ve){
        assert(true);
    }

    FileRepo fr{"test.txt"};

    auto all=fr.get_all();
    assert(all.size()==10);

    assert(all[0].getId()==1);
    assert(all[0].getDesc()=="Soft educational");
    assert(all[0].getState()=="open");

    fr.validId(11);
    try{
        fr.validId(1);
        assert(false);
    } catch(DuplicatedIdException& ve){
        assert(true);
    }
}
//
// Created by Alexandru-Paul Sirbu on 01.05.2022.
//

#include "service.h"

#include <algorithm>
#include <fstream>
#include <memory>

using std::sort;

Service::Service(AbsRepo* rp, Validator& vd) {
    repo=rp;
    valid=vd;
    undo_act.clear();
}

vector<Medicine>& Service::get_all_ent() {
    return repo->get_elems();
}

void Service::add(const string& cname, const string& cprod, const string& csubst, const int& cprice) {
    Validator::validate(cname,cprod,csubst,cprice);
    auto m=Medicine(cname,cprod,csubst,cprice);
    auto& v=repo->get_elems();
    vector<string> err;
    for(auto& it : v){
        if(it==m){
            err.emplace_back("Element deja existent");
        }
    }
    if(!err.empty()){
        throw RepoException(err);
    }
    repo->add_medicine(m);
    undo_act.push_back(new UndoAdd(repo,m));
}

void Service::modify(const int& poz, const string& cname, const string& cprod, const string& csubst, const int& cprice) {
    Validator::validate(cname,cprod,csubst,cprice);
    vector<string> err;
    if(poz<0 || poz>=repo->get_elems().size()){
        err.emplace_back("Pozitie invalida");
        throw RepoException(err);
    }
    auto m=repo->get_elems()[poz];
    repo->modify_medicine(Medicine(cname,cprod,csubst,cprice),poz);
    undo_act.push_back(new UndoMod(repo,m,poz));
}

void Service::del(const int& poz){
    vector<string> err;

    if(poz<0 || poz>=repo->get_elems().size()){
        err.emplace_back("Pozitie invalida");
        throw RepoException(err);
    }
    auto dlt=repo->get_elems()[poz];
    repo->delete_medicine(poz);
    undo_act.push_back(new UndoDel(repo,dlt,poz));
}

bool Service::search(const string& cname, const string& cprod, const string& csubst, const int& cprice) {
    //vector<string> err;

    Validator::validate(cname,cprod,csubst,cprice);

    auto m=Medicine(cname,cprod,csubst,cprice);

    auto it= find_if(repo->get_elems().begin(),repo->get_elems().end(),[=](Medicine& med){
        return med.get_subst()==m.get_subst() && med.get_price()==m.get_price() && med.get_name()==m.get_name() &&
               med.get_prod()==m.get_prod();
    });

    return it<repo->get_elems().end();

}

void Service::filter(int crit, const string& val,vector<Medicine>& rez) {
    if(crit==0){
        vector <string> err;
        int prc=0;
        bool vld=true;
        for(auto& ch : val){
            if('0'<=ch && ch<='9'){
                prc=prc*10+(ch-'0');
            } else {
                vld=false;
            }
        }
        if(!vld){
            err.emplace_back("Pret invalid");
            throw RepoException(err);
        }

        rez.clear();
        copy_if(repo->get_elems().begin(), repo->get_elems().end(), back_inserter(rez), [=](Medicine& m){
            return m.get_price()==prc;
        });
        return;
    }

    rez.clear();
    copy_if(repo->get_elems().begin(),repo->get_elems().end(), back_inserter(rez), [=](Medicine& m){
        return m.get_subst()==val;
    });

}

bool crit_0(const Medicine& el1, const Medicine& el2){
    return el1.get_name()<el2.get_name();
}

bool crit_1(const Medicine& el1, const Medicine& el2){
    return el1.get_prod()<el2.get_prod();
}

bool crit_2(const Medicine& el1, const Medicine& el2){
    return el1.get_subst()<el2.get_subst() || (el1.get_subst()==el2.get_subst() &&
                                               el1.get_price()<el2.get_price());
}

void Service::sort(int crit, vector<Medicine>& rez) {
    rez=repo->get_elems();
    if(crit==0){
        ::sort(rez.begin(),rez.end(), crit_0);
    } else if(crit==1){
        ::sort(rez.begin(),rez.end(), crit_1);
    } else if(crit==2){
        ::sort(rez.begin(),rez.end(), crit_2);
    }
}

void Service::undo() {
    if(undo_act.empty()){
        vector<string> err;
        err.emplace_back("Nu exista operatii la care sa se faca undo");
        throw RepoException(err);
    }
    ActUndo* act=undo_act.back();
    act->doUndo();
    undo_act.pop_back();
    delete act;
}

void test_service(){
    auto* r=new FileRepo("chaos.txt");
    auto v=Validator();

    auto s=Service(r,v);

    //testam daca urla la noi
//    try{
//        s.undo();
//        assert(false);
//    } catch(RepoException& re){
//        assert(true);
//    }

    //test add
    s.add("Parasinus","Bayer","paracetamol",10);

    vector<Medicine> mv;
    mv=s.get_all_ent();
    assert(mv[0].get_price()==10);
    assert(mv[0].get_name()=="Parasinus");
    assert(mv[0].get_prod()=="Bayer");
    assert(mv[0].get_subst()=="paracetamol");

//    s.undo();
//    assert(s.get_all_ent().empty());
//
//    s.add("Parasinus","Bayer","paracetamol",10);

    try{
        s.add("","Bayer","paracetamol",10);
        assert(false);
    } catch(ValidationException& ex){
        assert(true);
    }

    try{
        s.add("Parasinus","","paracetamol",10);
        assert(false);
    } catch(ValidationException& ex){
        assert(true);
        //cout<<ex;
    }

    try{
        s.add("Parasinus","Bayer","",10);
        assert(false);
    } catch(ValidationException& ex){
        assert(true);
    }

    try{
        s.add("Parasinus","Bayer","paracetamol",-5);
        assert(false);
    } catch(ValidationException& ex){
        assert(true);
    }

    try{
        s.add("57486","Bayer","paracetamol",10);
        assert(false);
    } catch(ValidationException& ex){
        assert(true);
    }

    try{
        s.add("Parasinus","5788","paracetamol",10);
        assert(false);
    } catch(ValidationException& ex){
        assert(true);
    }

    try{
        s.add("Parasinus","Bayer","95874",10);
        assert(false);
    } catch(ValidationException& ex){
        assert(true);
    }

    //test mod
    s.modify(0,"ParaPenta","Dona","paracetamol",15);

    //undo mod
//    s.undo();
//
//    mv=s.get_all_ent();
//    assert(mv[0].get_price()==10);
//    assert(mv[0].get_name()=="Parasinus");
//    assert(mv[0].get_prod()=="Bayer");
//    assert(mv[0].get_subst()=="paracetamol");

    s.modify(0,"ParaPenta","Dona","paracetamol",15);

    mv=s.get_all_ent();

    assert(mv[0].get_price()==15);
    assert(mv[0].get_name()=="ParaPenta");
    assert(mv[0].get_prod()=="Dona");
    assert(mv[0].get_subst()=="paracetamol");

    try {
        s.modify(-5, "ParaPenta", "Dona", "paracetamol", 15);
        assert(false);
    } catch (RepoException& re){
        assert(true);
    }

    try {
        s.modify(8, "ParaPenta", "Dona", "paracetamol", 15);
        assert(false);
    } catch(RepoException&  re){
        assert(true);
    }

    //test del
    s.add("Parasinus","Bayer","paracetamol",10);

    //+test_search
    assert(s.search("Parasinus","Bayer","paracetamol",10)==1);
    assert(s.search("Parasinus","Bayr","paracetamol",10)==0);

    s.del(0);
    mv=s.get_all_ent();

    assert(mv[0].get_price()==10);
    assert(mv[0].get_name()=="Parasinus");
    assert(mv[0].get_prod()=="Bayer");
    assert(mv[0].get_subst()=="paracetamol");

    //undo_del
//    s.undo();
//
//    mv=s.get_all_ent();
//
//    assert(mv[0].get_price()==15);
//    assert(mv[0].get_name()=="ParaPenta");
//    assert(mv[0].get_prod()=="Dona");
//    assert(mv[0].get_subst()=="paracetamol");

    //s.del(0);

    try{
        s.add("Parasinus","Bayer","paracetamol",10);
        assert(false);
    } catch(RepoException& re){
        assert(true);
    }

    s.del(0);
    try {
        s.del(-8);
        assert(false);
    } catch(RepoException&  re){
        assert(true);
    }

    try {
        s.del(7);
        assert(false);
    } catch(RepoException&  re){
        assert(true);
        //cout<<re;
    }

    //filter test
    s.add("Parasinus","Bayer","parasinus",10);
    s.add("ParaPenta","Pharma","parasinus",5);
    s.add("Algocalmin","Pharma1","ibuprofen",10);

    //s.undo();

    //s.add("Algocalmin","Pharma1","ibuprofen",10);

    {
        vector<Medicine> res;
        s.filter(1, "parasinus", res);
        assert(res.size() == 2);
        assert(res[0].get_price() == 10);
        assert(res[0].get_subst() == "parasinus");
        assert(res[0].get_prod() == "Bayer");
        assert(res[0].get_name() == "Parasinus");

        assert(res[1].get_price() == 5);
        assert(res[1].get_subst() == "parasinus");
        assert(res[1].get_prod() == "Pharma");
        assert(res[1].get_name() == "ParaPenta");
    }

    {
        vector<Medicine> res;
        s.filter(0, "10", res);
        assert(res.size() == 2);
        assert(res[0].get_price() == 10);
        assert(res[0].get_subst() == "parasinus");
        assert(res[0].get_prod() == "Bayer");
        assert(res[0].get_name() == "Parasinus");

        assert(res[1].get_price() == 10);
        assert(res[1].get_subst() == "ibuprofen");
        assert(res[1].get_prod() == "Pharma1");
        assert(res[1].get_name() == "Algocalmin");

        try{
            s.filter(0,"a3",res);
            assert(false);
        } catch(...){
            assert(true);
        }
    }

    //sort

    {
        vector<Medicine> res;
        s.sort(0,res);
        assert(res.size()==3);
        assert(res[0].get_name()=="Algocalmin");
        assert(res[1].get_name()=="ParaPenta");
        assert(res[2].get_name()=="Parasinus");
    }

    {
        vector<Medicine> res;
        s.sort(1,res);
        assert(res.size()==3);
        assert(res[0].get_name()=="Parasinus");
        assert(res[1].get_name()=="ParaPenta");
        assert(res[2].get_name()=="Algocalmin");
    }

    {
        vector<Medicine> res;
        s.sort(2,res);
        assert(res.size()==3);
        assert(res[0].get_name()=="Algocalmin");
        assert(res[1].get_name()=="ParaPenta");
        assert(res[2].get_name()=="Parasinus");
    }

    ofstream dll("chaos.txt");
    dll.close();

    delete r;
}
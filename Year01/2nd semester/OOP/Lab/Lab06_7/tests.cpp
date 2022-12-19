//
// Created by Alexandru-Paul Sirbu on 26.03.2022.
//

#include "tests.h"

#include "vector_man.h"
#include "service.h"
#include <iostream>
#include <fstream>
#include "vector_man.cpp"
#include "recipe_class.h"

using namespace std;

void test_all(){
    test_gen();
    test_domain();
    test_repo();
    test_service();
    test_vector();
    test_recipe();
    cout<<"Le-o luat\n";
}

void test_gen(){
    Vector_Man<Medicine> v;
    auto m=Medicine("Parasinus","Bayer","paracetamol",5);
    v.push_back(m);
    Vector_Man<Medicine> v1;
    v1=v;
    assert(v1.size()==1);
    assert(v1[0].get_price()==5);
}

void test_domain(){
    //test constructor
    Medicine mn;
    mn=Medicine("Parasinus","Bayer","paracetamol",5);
    assert(mn.get_price()==5);
    Medicine m=Medicine("Parasinus","Bayer","paracetamol",5);
    assert(m.get_name()=="Parasinus");
    assert(m.get_prod()=="Bayer");
    assert(m.get_subst()=="paracetamol");
    assert(m.get_price()==5);

    //test validator

    try {
        Validator::validate("", "Bayer", "paracetamol", 10);
        assert(false);
    } catch (ValidationException& val){
        //cout<<val;
        assert(true);
    }

    try {
        Validator::validate("", "", "", -5);
        assert(false);
    } catch (ValidationException& val){
        assert(true);
    }

    try{
        Validator::validate("Parasinus","Bayer","paracetamol",-5);
        assert(false);
    } catch(ValidationException& val){
        assert(true);
    }

    try{
        Validator::validate("Parasinus","Bayer","paracetamol",10);
        assert(true);
    } catch(ValidationException& val){
        assert(false);
    }

    try{
        Validator::validate("Parasinus","","paracetamol",10);
        assert(false);
    } catch(ValidationException& val){
        assert(true);
        //cout<<val;
    }

    try{
        Validator::validate("Parasinus","Bayer","",10);
        assert(false);
    } catch(ValidationException& val){
        assert(true);
    }

    try{
        Validator::validate("-5748654","-5748654","-5748654",10);
        assert(false);
    } catch(ValidationException& val){
        assert(true);
    }

    //test has letters
    assert(has_letters("-5748654")==false);
    assert(has_letters("48654")==false);
    assert(has_letters("4d86a54")==true);
    assert(has_letters("a48654")==true);
    assert(has_letters("a48654")==true);
}

void test_repo(){
    Repo repo;
    Medicine m=Medicine("Parasinus","Bayer","paracetamol",5);


    //test add elem & test get elems
    repo.add_medicine(m);
    vector<Medicine> mv;
    mv=repo.get_elems();

    assert(mv.size() == 1);
    assert(mv[0].get_name() == "Parasinus");
    assert(mv[0].get_prod() == "Bayer");
    assert(mv[0].get_subst() == "paracetamol");
    assert(mv[0].get_price() == 5);

    //test modify elem
    Medicine m1=Medicine("Parasinus","Pharma","paracetamol",10);
    repo.modify_medicine(m1,0);

    mv=repo.get_elems();

    assert(mv.size()==1);
    assert(mv[0].get_name()=="Parasinus");
    assert(mv[0].get_prod()=="Pharma");
    assert(mv[0].get_subst()=="paracetamol");
    assert(mv[0].get_price()==10);

    //test delete elems
    repo.add_medicine(m);
    repo.delete_medicine(0);

    mv=repo.get_elems();

    assert(mv.size()==1);
    assert(mv[0].get_name()=="Parasinus");
    assert(mv[0].get_prod()=="Bayer");
    assert(mv[0].get_subst()=="paracetamol");
    assert(mv[0].get_price()==5);


    FileRepo frp{"test_file.txt"};

    frp.add_medicine(Medicine("Parasinus","Pharma","paracetamol",10));

    auto prp=RepoProb(1);
    prp.add_medicine(Medicine("Parasinus","Bayer","paracetamol",5));

    mv=prp.get_elems();

    assert(mv.size() == 1);
    assert(mv[0].get_name() == "Parasinus");
    assert(mv[0].get_prod() == "Bayer");
    assert(mv[0].get_subst() == "paracetamol");
    assert(mv[0].get_price() == 5);

    prp.modify_medicine(Medicine("Nush","Bayer","paranol",10),0);

    mv=prp.get_elems();

    assert(mv.size() == 1);
    assert(mv[0].get_name() == "Nush");
    assert(mv[0].get_prod() == "Bayer");
    assert(mv[0].get_subst() == "paranol");
    assert(mv[0].get_price() == 10);

    prp.add_medicine(Medicine("Parasinus","Bayer","paracetamol",5));

    mv=prp.get_elems();

    assert(mv.size() == 2);

    prp.delete_medicine(0);

    mv=prp.get_elems();

    assert(mv.size() == 1);

    auto noch=RepoProb(0);

    try {
        noch.add_medicine(Medicine("Parasinus", "Bayer", "paracetamol", 5));
        assert(false);
    } catch(BadLuckException& re){
        assert(true);
    }

}

void test_service(){
    auto* r=new FileRepo("chaos.txt");
    auto v=Validator();

    auto s=Service(r,v);

    //testam daca urla la noi
    try{
        s.undo();
        assert(false);
    } catch(RepoException& re){
        assert(true);
    }

    //test add
    s.add("Parasinus","Bayer","paracetamol",10);

    vector<Medicine> mv;
    mv=s.get_all_ent();
    assert(mv[0].get_price()==10);
    assert(mv[0].get_name()=="Parasinus");
    assert(mv[0].get_prod()=="Bayer");
    assert(mv[0].get_subst()=="paracetamol");

    s.undo();
    assert(s.get_all_ent().empty());

    s.add("Parasinus","Bayer","paracetamol",10);

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
        cout<<ex;
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
    s.undo();

    mv=s.get_all_ent();
    assert(mv[0].get_price()==10);
    assert(mv[0].get_name()=="Parasinus");
    assert(mv[0].get_prod()=="Bayer");
    assert(mv[0].get_subst()=="paracetamol");

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
    s.undo();

    mv=s.get_all_ent();

    assert(mv[0].get_price()==15);
    assert(mv[0].get_name()=="ParaPenta");
    assert(mv[0].get_prod()=="Dona");
    assert(mv[0].get_subst()=="paracetamol");

    s.del(0);

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
        cout<<re;
    }

    //filter test
    s.add("Parasinus","Bayer","parasinus",10);
    s.add("ParaPenta","Pharma","parasinus",5);
    s.add("Algocalmin","Pharma1","ibuprofen",10);

    s.undo();

    s.add("Algocalmin","Pharma1","ibuprofen",10);

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

void test_vector(){
    Vector_Man<int> v1(5);
    v1.push_back(10);
    Vector_Man<int> v;

    assert(v.empty());

    v.push_back(5);

    assert(!v.empty());

    for(auto& it : v){
        assert(it==5);
    }
    v.push_back(10);

    Vector_Man<int> cp;
    v.copy(cp);

    assert(cp.size()==2);
    assert(cp[0]==5);
    assert(cp[1]==10);

    assert(v[1]==10);

    assert(v.size()==2);
    v.push_back(15);
    v.push_back(20);

    v.erase(0);
    assert(v.size()==3);

    v.push_back(20);
    v.push_back(20);
    v.push_back(20);
    v.push_back(20);
    v.push_back(20);
    v.push_back(20);
    v.push_back(20);
    v.push_back(20);
    v.push_back(20);
    v.push_back(20);
    v.push_back(20);
    v.push_back(20);
    v.push_back(20);
    v.push_back(20);

    try{
        Vector_Man<int> v5(-5);
        assert(false);
    } catch(...){
        assert(true);
    }

    try{
        v[-3]=5;
        assert(false);
    } catch(...){
        assert(true);
    }

    Vector_Man<Medicine> A;
    auto m=Medicine("Parasinus","Pharma","paracetamol",10);
    A.push_back(m);
}

void test_recipe(){
    Recipe r;
    auto m=Medicine("Parasinus","Bayer","paracetamol",10);

    r.add_to_recipe(m);
    vector<Medicine> res;
    res=r.get_all();
    assert(res.size()==1);
    assert(res[0].get_name()=="Parasinus");
    assert(res[0].get_subst()=="paracetamol");
    assert(res[0].get_prod()=="Bayer");
    assert(res[0].get_price()==10);

    r.add_to_recipe(m);
    res=r.get_all();
    assert(res.size()==2);

    r.empty_recipe();
    res=r.get_all();
    assert(res.empty());

    vector<Medicine> s;
    s.emplace_back(Medicine("Parasinus","Bayer","parasinus",10));
    s.emplace_back(Medicine("ParaPenta","Pharma","parasinus",5));
    s.emplace_back(Medicine("Algocalmin","Pharma1","ibuprofen",10));

    r.random_add(s,2);
    assert(r.get_all().size()==2);

    r.save_to_file("test.out");

    r.random_add(s,5);
    r.save_to_file("file.csv");
}
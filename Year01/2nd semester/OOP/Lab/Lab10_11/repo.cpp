//
// Created by Alexandru-Paul Sirbu on 01.05.2022.
//

#include "repo.h"
#include <fstream>

void Repo::add_medicine(const Medicine &a) {
    this->elems.push_back(a);
}

void Repo::modify_medicine(const Medicine &a, int poz) {
    this->elems[poz]=a;
}

vector<Medicine>& Repo::get_elems(){
    return elems;
}

void Repo::delete_medicine(int poz) {
    //this->elems.erase(poz);
    this->elems.erase(elems.begin()+poz);
}

ostream &operator<<(ostream &out, const RepoException &ex) {
    for(const auto& mesg : ex.msg){
        out<<mesg<<"\n";
    }
    return out;
}

FileRepo::FileRepo(string fn) {
    filename=move(fn);
    load_from_file();
}

void FileRepo::load_from_file() {
    ifstream fin(filename);
    string str;
    while(getline(fin,str)) {
        stringstream ss(str);

        string word;
        vector<string> med;
        while (getline(ss, word, ';')) {
            med.push_back(word);
        }
        int nr=0;
        for(auto& ch : med[3]){
            nr=nr*10+(ch-'0');
        }
        Repo::add_medicine(Medicine(med[0],med[1],med[2],nr));
    }
    fin.close();
}

void FileRepo::save_to_file() {
    ofstream fout(filename);
    int index=0;
    for(auto& it : Repo::get_elems()){
        fout<<it.get_name()<<";"<<it.get_prod()<<";"<<it.get_subst()<<";"<<it.get_price();
        ++index;
        if(index!=Repo::get_elems().size()){
            fout<<"\n";
        }
    }
    fout.close();
}

void RepoProb::add_medicine(const Medicine &m) {
    det_luck();
    elems.insert(make_pair(elems.size(),m));
}

void RepoProb::modify_medicine(const Medicine &m, int poz) {
    det_luck();
    for(auto& it : elems){
        if(it.first==poz){
            elems.erase(poz);
            elems.insert(make_pair(poz,m));
            break;
        }
    }
}

void RepoProb::delete_medicine(int poz) {
    det_luck();
    elems.erase(poz);
    map<int,Medicine> sec;
    sec.clear();
    for(auto& it : elems){
        sec.insert(make_pair(sec.size(),it.second));
    }
    elems=sec;
}
vector<Medicine> all;
vector<Medicine>& RepoProb::get_elems() {
    det_luck();
    all.clear();
    for(auto& it: elems){
        all.push_back(it.second);
    }
    return all;
}

RepoProb::RepoProb(float chance) {
    prob=chance;
    elems.clear();
}

void RepoProb::det_luck() {
    auto prb=int(prob*10);
    int nr=rand()%10+1;
    if(nr<=prb){
        return;
    }
    throw BadLuckException("Teapa cumetre\n");
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
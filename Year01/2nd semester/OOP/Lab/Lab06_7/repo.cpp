//
// Created by Alexandru-Paul Sirbu on 26.03.2022.
//

#include <fstream>
#include <sstream>
#include <utility>
#include <random>
#include "repo.h"
//#include "vector_man.h"
//#include "vector_man.cpp"

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
    for(auto& it : Repo::get_elems()){
        fout<<it.get_name()<<";"<<it.get_prod()<<";"<<it.get_subst()<<";"<<it.get_price()<<"\n";
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
    throw BadLuckException("Teapa cumetre");
}

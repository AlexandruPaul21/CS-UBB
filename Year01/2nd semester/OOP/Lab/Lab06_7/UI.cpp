//
// Created by Alexandru-Paul Sirbu on 26.03.2022.
//

#include <iostream>
#include "UI.h"

using namespace std;

int get_pos_nr(){
    string S;
    getline(cin,S);
    if(S.empty()) getline(cin,S);

    bool ch=(S[0]==0);

    int ret=0;
    for(auto& cha: S){
        if('0'<=cha && cha<='9'){
            ret=ret*10+(cha-'0');
        } else {
            ch=true;
        }
    }
    if(ch){
        ret=-1;
    }
    return ret;
}

void Console::add_ui() const{
    string cname,cprod,csubst;
    int cprice;
    cout<<"Introduceti numele: ";
    getline(cin,cname);

    cout<<"Introduceti producatorul: ";
    getline(cin,cprod);

    cout<<"Introduceti subst. activa: ";
    getline(cin,csubst);

    cout<<"Introduceti pretul: ";
    cprice=get_pos_nr();

    try{
        srv.add(cname,cprod,csubst,cprice);
        cout<<"Adaugare realizata cu succes!\n";
    } catch(ValidationException& re){
        cout<<re;
    } catch(RepoException& exc){
        cout<<exc;
    }

}

void Console::del_ui() const{
    int ind;
    cout<<"Introduceti indicele de sters: ";
    ind=get_pos_nr();

    try {
        srv.del(ind);
        cout<<"Stergere efectuata cu succes!\n";
    } catch(RepoException&  re){
        cout<<re;
    }

}

void Console::cautare() const {
    cout<<"Introduceti datele despre medicamentul cautat!\n";

    string cname,cprod,csubst;

    int cprice;
    cout<<"Introduceti numele: ";
    getline(cin,cname);

    cout<<"Introduceti producatorul: ";
    getline(cin,cprod);

    cout<<"Introduceti subst. activa: ";
    getline(cin,csubst);

    cout<<"Introduceti pretul: ";
    cprice=get_pos_nr();

    try{
        bool res=srv.search(cname,cprod,csubst,cprice);
        if(res){
            cout<<"A fost gasit medicamentul!\n";
        } else {
            cout<<"Nu a fost gasit medicamentul\n";
        }
    } catch (ValidationException& ve){
        cout<<ve;
    }

}

void Console::filter() const {
    cout<<"Selectati criteriul de filtrare\n";
    cout<<"1. Pret\n";
    cout<<"2. Substanta activa\n";
    cout<<"Optiunea dvs. este: ";

    string op;
    getline(cin,op);

    vector<Medicine> rez(srv.get_all_ent().size());

    if(op=="1"){
        cout<<"Introduceti pretul de cautat: ";
        string price;
        getline(cin,price);
        try {
            srv.filter(0, price, rez);
        } catch(...){

        }
        if(rez.empty()){
            cout<<"Nu a fost gasit nici-un rezultat\n";
            return;
        }
        cout<<"\n------------------Medicamente existente------------------\n";
        for(int i=0; i<rez.size(); ++i){
            cout<<i<<". Nume: "<<rez[i].get_name()<<"; Producator: "<<rez[i].get_prod()<<"; Subst.act: "<<rez[i].get_subst();
            cout<<"; Pret: "<<rez[i].get_price()<<"\n";
        }
        cout<<"\n";
    } else if(op=="2"){
        cout<<"Introduceti substanta activa de cautat: ";
        string subs;
        getline(cin,subs);
        try {
            srv.filter(1, subs, rez);
        } catch (...){

        }
        if(rez.empty()){
            cout<<"Nu a fost gasit nici-un rezultat\n";
            return;
        }
        cout<<"\n------------------Medicamente existente------------------\n";
        for(int i=0; i<rez.size(); ++i){
            cout<<i<<". Nume: "<<rez[i].get_name()<<"; Producator: "<<rez[i].get_prod()<<"; Subst.act: "<<rez[i].get_subst();
            cout<<"; Pret: "<<rez[i].get_price()<<"\n";
        }
        cout<<"\n";
    } else {
        cout<<"Criteriu indisponibil momentant\n";
    }
}

void Console::sort() const {
    cout<<"Selectati criteriul de sortare\n";
    cout<<"1. Nume\n";
    cout<<"2. Producator\n";
    cout<<"3. Subst.activa + pret\n";
    cout<<"Optiunea dvs. este: ";

    string op;
    getline(cin,op);

    vector<Medicine> rez(srv.get_all_ent().size());

    if(op=="1" || op=="2" || op=="3"){
        srv.sort((op[0]-'0')-1,rez);
    } else {
        cout<<"Comanda invalida!\n";
        return;
    }
    cout<<"\n------------------Medicamente existente------------------\n";
    for(int i=0; i<rez.size(); ++i){
        cout<<i<<". Nume: "<<rez[i].get_name()<<"; Producator: "<<rez[i].get_prod()<<"; Subst.act: "<<rez[i].get_subst();
        cout<<"; Pret: "<<rez[i].get_price()<<"\n";
    }
    cout<<"\n";
}

void Console::show_all() const {
    vector<Medicine>& farm=srv.get_all_ent();
    if(farm.empty()) return;
    cout<<"\n------------------Medicamente existente------------------\n";
    for(int i=0; i<farm.size(); ++i){
        cout<<i<<". Nume: "<<farm[i].get_name()<<"; Producator: "<<farm[i].get_prod()<<"; Subst.act: "<<farm[i].get_subst();
        cout<<"; Pret: "<<farm[i].get_price()<<"\n";
    }
    cout<<"\n";
}

void Console::mod_ui() const{
    int ind;

    cout<<"Introduceti indicele de sters: ";
    ind=get_pos_nr();

    string cname,cprod,csubst;
    int cprice;
    cout<<"Introduceti numele: ";
    getline(cin,cname);

    cout<<"Introduceti producatorul: ";
    getline(cin,cprod);

    cout<<"Introduceti subst. activa: ";
    getline(cin,csubst);

    cout<<"Introduceti pretul: ";
    cprice=get_pos_nr();

    try {
        srv.modify(ind, cname, cprod, csubst, cprice);
        cout << "Modificare facuta cu succes!\n";
    } catch(ValidationException& ve){
        cout<<ve;
    } catch(RepoException& re){
        cout<<re;
    }
}

void Console::recipe() const {
    cout << "Meniu pentru crearea unei retete\n";
    Recipe r;
    while(true) {
        cout << "Numar de medicamente in reteta: "<< r.get_all().size()<<"\n";
        cout << "Operatii disponibile: \n";
        cout << "1. Adauga medicament\n";
        cout << "2. Goleste reteta\n";
        cout << "3. Genereaza reteta(random)\n";
        cout << "4. Export\n";
        cout << "0. Exit\n";
        cout << "Introduceti optiunea: ";
        string ans;
        cin >> ans;
        if (ans == "1") {
            show_all();
            cout<<"Introduceti indicele medicamentului de adaugat: ";
            int op=get_pos_nr();
            if(op==-1 && op>=srv.get_all_ent().size()){
                cout<<"Indicele trebuie sa fie un numar natural, care poate fi maxim nr de elemente al sirului -1\n";
                continue;
            }
            r.add_to_recipe(srv.get_all_ent()[op]);
        } else if (ans == "2") {
            r.empty_recipe();
        } else if (ans == "3") {
            cout<<"Introduceti numarul de medicamente ce doriti sa fie adaugat aleatoriu: ";
            int q=get_pos_nr();
            if(q==-1 || q==0){
                cout<<"Numarul trebuie sa fie mai mare egal cu 0 si natural\n";
                continue;
            }
            r.random_add(srv.get_all_ent(),q);
        } else if (ans == "4") {
            cout<<"Introduceti numele fisierului: ";
            string file;
            cin>>file;
            r.save_to_file(file);
        } else if (ans == "0") {
            break;
        } else {
            cout<<"Varianta invalida!\n";
        }
    }
}

int to_nr(const string& s){
    int ret=0;
    for(auto& ch: s){
        if('0'<=ch && ch<='9'){
            ret=ret*10+(ch-'0');
        } else {
            ret=-1;
            break;
        }
    }
    return ret;
}

void Console::batch_mode() const{
    cout<<"Introduceti comenzile in forma(primul cuv din optiune dupa argumentele)\n";
    cout<<"adauga nume prod subst pret\n";
    cout<<"sterge pozitie\n";
    cout<<"modifica pozitie nume prod subst pret\n";
    vector<string> arg;
    string args;
    vector<int> unsolved;
    for(int j=0;args!="finish "; ++j){
        getline(cin,args);
        arg.clear();
        bool first=true;
        string cuv;
        string cmd;
        string poz,name,prod,subst,price;
        args+=" ";
        for(auto& ch: args){
            if(ch==' '){
                if(first){
                   cmd=cuv;
                   cuv="";
                   first=false;
                } else {
                    arg.push_back(cuv);
                    cuv="";
                }
            } else {
                cuv+=ch;
            }
        }
        if(cmd=="finish") {
            break;
        } else if(cmd=="adauga") {
            if(arg.size()!=4){
                unsolved.push_back(j);
                continue;
            } else {
                name=arg[0];
                prod=arg[1];
                subst=arg[2];
                int pret= to_nr(arg[3]);
                try {
                    srv.add(name,prod,subst,pret);
                } catch (...){
                    unsolved.push_back(j);
                }
            }
        } else if(cmd=="sterge"){
            if(arg.size()!=1){
                unsolved.push_back(j);
                continue;
            } else {
                int pz= to_nr(arg[0]);
                try{
                    srv.del(pz);
                } catch(...){
                    unsolved.push_back(j);
                }
            }
        } else if(cmd=="modifica"){
            if(arg.size()!=5){
                unsolved.push_back(j);
                continue;
            } else {
                int pz= to_nr(arg[0]);
                name=arg[1];
                prod=arg[2];
                subst=arg[3];
                int pret= to_nr(arg[4]);
                try {
                    srv.modify(pz, name, prod, subst, pret);
                } catch(...){
                    unsolved.push_back(j);
                }
            }
        } else unsolved.push_back(j);
    }
    cout<<"Total comenzi nerezolvate: "<<unsolved.size()<<"(pentru raport complet, adauga -d la finish)\n";
//    if(arg[0]=="-d"){
//        for(auto& it : unsolved){
//            cout<<it<<" ";
//        }
//        cout<<"\n";
//    }
}


void Console::undo_ui() const{
    try{
        srv.undo();
        cout<<"Operatie realizata cu succes\n";
    } catch(RepoException& re){
        cout<<re;
    }

}

void Console::show_ui() const {
    cout<<"Bine ati venit!\n";
    bool end=false;
    while(!end) {
        cout << "Comenzi disponibile:\n";
        cout<<"1. Adauga un element\n";
        cout<<"2. Modifica un elemnt\n";
        cout<<"3. Sterge un element\n";
        cout<<"4. Afiseaza toate medicamentele\n";
        cout<<"5. Cautare medicament\n";
        cout<<"6. Filtrare\n";
        cout<<"7. Sortare\n";
        cout<<"8. Creare reteta\n";
        cout<<"9. Batch Mode\n";
        cout<<"10. Undo\n";
        cout<<"0. Exit\n";
        cout << "Comanda dvs: ";
        string ans;
        getline(cin, ans);
        if(ans.empty()) {
            getline(cin, ans);
        }
        if(ans=="1") {
            add_ui();
        } else if(ans=="2") {
            mod_ui();
        } else if(ans=="3") {
            del_ui();
        } else if(ans=="4") {
            show_all();
        } else if(ans=="5") {
            cautare();
        } else if(ans=="6") {
            filter();
        } else if(ans=="7") {
            sort();
        } else if(ans=="8") {
            recipe();
        } else if(ans=="9") {
            batch_mode();
        } else if(ans=="10") {
            undo_ui();
        } else if(ans=="0") {
            end=true;
        } else {
            cout<<"Comanada introdusa nu este corecta!\n";
        }
    }

}

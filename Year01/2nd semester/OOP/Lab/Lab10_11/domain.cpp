//
// Created by Alexandru-Paul Sirbu on 01.05.2022.
//

#include "domain.h"

Medicine::Medicine(const string& name, const string& prod, const string& subst, const int& price) {
    this->name=name;
    this->prod=prod;
    this->subst=subst;
    this->price=price;
}

bool Medicine::operator==(const Medicine &ot) {
    if(price==ot.price && prod==ot.prod && subst==ot.subst && price==ot.price){
        return true;
    }
    return false;
}

Medicine::Medicine() {
    name="";
    price=-1;
    prod="";
    subst="";
}

Medicine& Medicine::operator=(const Medicine &ot) = default;

bool has_letters(const string& S){
    for(auto& ch : S){
        if('A'<=ch && ch<='Z' || 'a'<=ch && ch<='z'){
            return true;
        }
    }
    return false;
}

ostream &operator<<(ostream &out, const ValidationException &ex) {
    for(const auto& mess : ex.msg){
        out<<mess<<"\n";
    }
    return out;
}

ostream& operator<<(ostream& out,const BadLuckException& ex){
    out<<ex.msg;
    return out;
}

void Validator::validate(
        const string& cname,
        const string& cprod,
        const string& csubst,
        const int& cprice
) {
    //string errors="";

    vector<string> errors;

    bool vname= has_letters(cname);
    if(cname.empty()){
        errors.emplace_back("Numele nu poat fi vid");
    } else if(!vname){
        errors.emplace_back("Numele trebuie sa contina minim o litera");
    }

    bool vprod= has_letters(cprod);
    if(cprod.empty()){
        errors.emplace_back("Producatorul nu poate fi vid");
    } else if(!vprod){
        errors.emplace_back("Producatorul trebuie sa contina litere");
    }

    bool vsubst= has_letters(csubst);
    if(csubst.empty()){
        errors.emplace_back("Substanta activa nu poate fi vida");
    } else if(!vsubst){
        errors.emplace_back("Substanta activa trebuie sa contina litere");
    }

    if(cprice<=0){
        errors.emplace_back("Pretul trebuie sa fie un numar natural nenul");
    }
    if(!errors.empty()){
        throw ValidationException(errors);
    }
}

string toMyString(vector<string> msg){
    string ans="";
    for(const auto& mg : msg){
        ans+=mg;
        ans+="\n";
    }
    return ans;
}

void test_domain(){
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
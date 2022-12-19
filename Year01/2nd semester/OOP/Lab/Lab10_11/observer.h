//
// Created by Alexandru-Paul Sirbu on 23.05.2022.
//

#ifndef LAB10_11V2_OBSERVER_H
#define LAB10_11V2_OBSERVER_H

#include <vector>

using namespace std;

class Observer{
public:
    virtual void update()=0;
};

class Observable{
private:
    vector<Observer*> obs;
public:
    void addObs(Observer* ob){
        obs.push_back(ob);
    }

    void rmObs(Observer* ob){
        obs.erase(remove(obs.begin(),obs.end(),ob),obs.end());
    }

    void notify(){
        for(auto it:obs){
            it->update();
        }
    }
};

#endif //LAB10_11V2_OBSERVER_H

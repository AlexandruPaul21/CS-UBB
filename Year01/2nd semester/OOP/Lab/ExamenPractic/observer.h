//
// Created by Alexandru-Paul Sirbu on 10.06.2022.
//

#ifndef EXAMENPRACTIC_OBSERVER_H
#define EXAMENPRACTIC_OBSERVER_H

#include <vector>

using namespace std;

/**
 * Model ce exemplifica functionarea Observerului
 */
class Observer{
public:
    /**
     * Metoda virtuala ce ajuta la a tine evidenta celor ce urmaresc observabilul
     */
    virtual void update()=0;
    virtual ~Observer(){}
};

/**
 * Clasa Observabil
 */
class Observable{
private:
    vector<Observer*> obs;
public:
    /**
     * Adaauga un observer
     * @param ob observerul
     */
    void addObs(Observer* ob){
        obs.push_back(ob);
    }
    /**
     * Anunta toate entitiatile ce urmaresc obiectul observabil ca a avut loc o schimbare
     */
    void notify(){
        for(auto ob:obs){
            ob->update();
        }
    }
    /**
     * Da remove unui observer
     * @param ob observerul
     */
    void rmObs(Observer* ob){
        obs.erase(remove(obs.begin(),obs.end(),ob),obs.end());
    }

    virtual ~Observable(){}
};

#endif //EXAMENPRACTIC_OBSERVER_H
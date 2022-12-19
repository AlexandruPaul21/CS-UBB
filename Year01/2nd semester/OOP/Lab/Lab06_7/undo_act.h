//
// Created by Alexandru-Paul Sirbu on 25.04.2022.
//

#ifndef LAB06_7_UNDO_ACT_H
#define LAB06_7_UNDO_ACT_H

#include "repo.h"

class ActUndo{
public:
    virtual void doUndo()=0;
    virtual ~ActUndo()=default;
};

class UndoAdd:public ActUndo{
private:
    AbsRepo* fr;
    Medicine last;
public:
    UndoAdd(AbsRepo* f,const Medicine& lst): fr{f}, last{lst} {}
    void doUndo() override{
        int i=0;
        for(auto& it : fr->get_elems()){
            if(it==last){
                break;
            }
            ++i;
        }
        fr->delete_medicine(i);
    }
};

class UndoMod:public ActUndo{
private:
    AbsRepo* fr;
    Medicine last;
    int l_poz;
public:
    UndoMod(AbsRepo* f,const Medicine& m,const int l_p): fr{f}, last{m}, l_poz{l_p} {}
    void doUndo() override{
        fr->modify_medicine(last,l_poz);
    }
};

class UndoDel:public ActUndo{
private:
    AbsRepo* fr;
    Medicine dlt;
    int poz;
public:
    UndoDel(AbsRepo* f, const Medicine& m, const int pz): fr{f}, dlt{m}, poz{pz} {}
    void doUndo() override{
        auto it=fr->get_elems().begin();
        it+=poz;
        fr->get_elems().insert(it,dlt);
    }
};

#endif //LAB06_7_UNDO_ACT_H

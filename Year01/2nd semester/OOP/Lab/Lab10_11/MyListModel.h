//
// Created by Alexandru-Paul Sirbu on 30.05.2022.
//

#ifndef LAB10_11V2_MYLISTMODEL_H
#define LAB10_11V2_MYLISTMODEL_H

#include <QAbstractTableModel>
#include "domain.h"
#include <vector>

using std::vector;

class MyListModel:public QAbstractListModel{
    vector<Medicine> meds;
public:
    MyListModel(const vector<Medicine>& mds): meds{mds}{}


    int rowCount(const QModelIndex &parent=QModelIndex()) const override{
        return meds.size();
    }

    QVariant data(const QModelIndex& index,int role=Qt::DisplayRole) const override{
        if(role==Qt::DisplayRole){
            auto med=meds[index.row()].get_name();
            return QString::fromStdString(med);
        }
        return QVariant{};
    }

    void setMeds(vector<Medicine>& mds){
        meds=mds;
        auto topIndex= createIndex(0,0);
        auto botIndex= createIndex(rowCount(),0);
        emit dataChanged(topIndex,botIndex);
        emit layoutChanged();
    }
};


#endif //LAB10_11V2_MYLISTMODEL_H
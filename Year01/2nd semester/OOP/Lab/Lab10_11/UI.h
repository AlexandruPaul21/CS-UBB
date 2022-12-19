//
// Created by Alexandru-Paul Sirbu on 01.05.2022.
//

#ifndef LAB10_11V2_UI_H
#define LAB10_11V2_UI_H

#include "service.h"
#include "recipe_class.h"
#include <QWidget>
#include <QListWidget>
#include <QBoxLayout>
#include <QPushButton>
#include <QFormLayout>
#include <QLabel>
#include <QLineEdit>
#include <QMessageBox>
#include <QLayout>
#include <QTextBlock>
#include <QTableWidget>
#include <set>
#include <QSlider>
#include <QPainter>
#include <QListView>
#include "observer.h"
#include "MyListModel.h"

class RecipeGUI: public QWidget,public Observer{
private:
    Recipe& rep;
    Service& srv;
    QHBoxLayout *recipe_main_layout=new QHBoxLayout;

    QListWidget *recipe_lst;

    QLineEdit *lne_recipe;
    QPushButton *add_to_rec;
    QPushButton *empty_rec;
    QPushButton *random_add;
    QPushButton *export_recipe;
    QPushButton *help_button;

    void initGUI();
    void connectSignals();
    void reloadMeds(vector<Medicine> meds);
    void update() override{
        reloadMeds(rep.get_all());
    }
public:
    RecipeGUI(Recipe& rep,Service& srv): srv{srv},rep{rep}{
        rep.addObs(this);
        initGUI();
        connectSignals();
        reloadMeds(rep.get_all());
    }

    ~RecipeGUI(){
        rep.rmObs(this);
    }
};


class PaintGUI: public QWidget,public Observer{
private:
    Recipe& rep;
public:
    PaintGUI(Recipe& rep):rep{rep}{
        rep.addObs(this);
    }

    void paintEvent(QPaintEvent*) override{
        QPainter p{this};
        int x;
        int y;
        //p.drawEllipse(QPointF(0,0),5,5);
        for(auto &it: rep.get_all()){
            x= rand() % 400 + 1;
            y = rand() % 400 + 1;
            //p.drawRect(x, y, 10, it.get_price());
            //p.drawEllipse(QPointF(0,0),5,5);
            //qDebug() << x << " " << y;
            QRectF target(x, y, 100, 94);
            QRectF source(0, 0, 732, 720);
            QImage image("ucluj.jpg");

            p.drawImage(target,image, source);
        }
    }

    void update() override{
        repaint();
    }

    ~PaintGUI(){
        rep.rmObs(this);
    }
};

class GUI : public QWidget{
private:
    vector<Medicine> act_list;
    Service srv;
    Recipe rep;
    QListWidget *lst=new QListWidget;

    QListView* lst_view=new QListView;
    MyListModel* model;

    vector<RecipeGUI*> rcp;
    vector<PaintGUI*> pg;
    //QTableWidget *table;

    QHBoxLayout *lyMain = new QHBoxLayout;
    QPushButton *btnSortName;
    QPushButton *btnSortProd;
    QPushButton *btnSortSP;
    QPushButton *btnFilterPrice;
    QPushButton *btnFilterSubst;
    QPushButton *btn_add;
    QPushButton *btn_mod;
    QPushButton *btn_del;
    QPushButton *btn_undo;
    QPushButton *btn_recipe_mst;
    QPushButton *btn_recipe_rdonly;
    QPushButton *btn_reset;
    QLineEdit *txtName;
    QLineEdit *txtProd;
    QLineEdit *txtSubst;
    QLineEdit *txtPrice;
    QLineEdit *txtFilt;
    QListWidget *recipe_lst;
    QSlider* plm;

    QWidget *opt_but;
    QVBoxLayout *lay_opt;

    vector<QPushButton*> subst_but;

    int lb_index;
    void init_GUI();
    void connectSignalsSlots();
    void reloadList(vector<Medicine>& meds);
    void updateBut(vector<Medicine>& all);

    void addMed();
    void delMed();
    void uptMed();
    void undoMed();

    void reset_form();
public:
    GUI(Service& srv) : srv {srv}{
        init_GUI();
        model= new MyListModel{srv.get_all_ent()};
        lst_view->setModel(model);
        connectSignalsSlots();
        reloadList(srv.get_all_ent());
        act_list=srv.get_all_ent();
        updateBut(srv.get_all_ent());
        rcp.push_back(new RecipeGUI{rep,srv});
        rcp[0]->show();
        pg.push_back(new PaintGUI{rep});
        pg[0]->show();
    }

    void paintEvent(QPaintEvent*) override{
        QPainter p{this};
        p.drawEllipse(QPointF(20,20),20,20);
        p.drawEllipse(QPointF(this->width()-20,this->height()-20),20,20);
        p.drawEllipse(QPointF(this->width()-20,20),20,20);
        p.drawEllipse(QPointF(20,this->height()-20),20,20);
    }
};

#endif //LAB10_11V2_UI_H
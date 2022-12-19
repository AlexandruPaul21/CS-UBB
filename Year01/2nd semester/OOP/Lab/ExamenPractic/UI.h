//
// Created by Alexandru-Paul Sirbu on 10.06.2022.
//

#ifndef EXAMENPRACTIC_UI_H
#define EXAMENPRACTIC_UI_H

#include <QWidget>
#include <QPushButton>
#include <QTableWidget>
#include <QLineEdit>
#include <QBoxLayout>
#include <QFormLayout>
#include <QLabel>
#include <algorithm>
#include <QListWidget>
#include <QMessageBox>

#include "service.h"

/**
 * Clasa Window pentru ferestre separarte
 */
class Window:public QWidget,public Observer{
private:
    string title;
    Service& srv;
    QVBoxLayout* lyMain=new QVBoxLayout;
    QListWidget* lst;
    QPushButton* btnOpen;
    QPushButton* btnClosed;
    QPushButton* btnInprog;

    /**
     * Initializeaza GUI()
     */
    void initGUI();
    /**
     * Conecteaza semnalele
     */
    void connectSignals();
    /**
     * Reia lista oferita ca parametru
     * @param all lista
     */
    void reloadList(vector<Task> all);
public:
    /**
     * Constructor
     * @param srv service
     * @param title titlul ferestrei
     */
    Window(Service& srv,string title):srv{srv},title{title}{
        initGUI();
        srv.addObs(this);
        connectSignals();
        reloadList(srv.get_all());
        show();
    }

    /**
     * Suprascrie metodat update fiind, observer
     */
    void update() override{
        reloadList(srv.get_all());
    }

    /**
     * Destructor
     */
    ~Window(){
        srv.rmObs(this);
    }
};

/**
 * Clasa ce modeleaza ideea de GUI
 */
class GUI:public QWidget,public Observer{
private:
    Service& srv;
    QHBoxLayout* lyMain=new QHBoxLayout;
    QTableWidget* tb_wdg;
    QLineEdit* txtId;
    QLineEdit* txtDesc;
    QLineEdit* txtState;
    QLineEdit* txtProg1;
    QLineEdit* txtProg2;
    QLineEdit* txtProg3;
    QLineEdit* txtProg4;
    QLineEdit* search;
    QPushButton* btnAdd;

    /**
     * Initializeaza GUI()
     */
    void initGUI();
    /**
     * Conecteaza semnalele
     */
    void connectSignals();
    /*
     * Da reload Listei
     */
    void reloadList(vector<Task> all);
public:
    GUI(Service& srv):srv{srv}{
        initGUI();
        srv.addObs(this);
        connectSignals();
        reloadList(srv.get_all());
        Window* w1=new Window{srv,"open"};
        Window* w2=new Window{srv,"inprogress"};
        Window* w3=new Window{srv,"closed"};
    }

    /**
     * Suprascrie metoda update
     */
    void update() override{
        reloadList(srv.get_all());
    }

    /**
     * Destructor
     */
    ~GUI(){
        srv.rmObs(this);
    }
};

#endif //EXAMENPRACTIC_UI_H
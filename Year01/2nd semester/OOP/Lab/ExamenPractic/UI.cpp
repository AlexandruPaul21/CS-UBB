//
// Created by Alexandru-Paul Sirbu on 10.06.2022.
//

#include "UI.h"

void GUI::initGUI() {
    setLayout(lyMain);
    tb_wdg=new QTableWidget;
    lyMain->addWidget(tb_wdg);

    QWidget* wdg_r=new QWidget;
    QVBoxLayout* lay_r=new QVBoxLayout;
    wdg_r->setLayout(lay_r);

    QWidget* form=new QWidget;
    QFormLayout* form_lay=new QFormLayout;
    form->setLayout(form_lay);

    QLabel* lblId=new QLabel("Id");
    txtId=new QLineEdit;

    QLabel* lblDesc=new QLabel("Desc");
    txtDesc=new QLineEdit;

    QLabel* lblState=new QLabel("State");
    txtState=new QLineEdit;

    QLabel* lblProg1=new QLabel("Prog1");
    txtProg1=new QLineEdit;

    QLabel* lblProg2=new QLabel("Prog2");
    txtProg2=new QLineEdit;

    QLabel* lblProg3=new QLabel("Prog3");
    txtProg3=new QLineEdit;

    QLabel* lblProg4=new QLabel("Prog4");
    txtProg4=new QLineEdit;

    form_lay->addRow(lblId,txtId);
    form_lay->addRow(lblDesc,txtDesc);
    form_lay->addRow(lblState,txtState);
    form_lay->addRow(lblProg1,txtProg1);
    form_lay->addRow(lblProg2,txtProg2);
    form_lay->addRow(lblProg3,txtProg3);
    form_lay->addRow(lblProg4,txtProg4);

    lay_r->addWidget(form);

    btnAdd=new QPushButton("Add");
    lay_r->addWidget(btnAdd);

    search=new QLineEdit;
    lay_r->addWidget(search);

    lyMain->addWidget(wdg_r);

}

void GUI::connectSignals() {
    QObject::connect(btnAdd,&QPushButton::clicked,[=](){
        try{
            int id= to_nr(txtId->text().toStdString());
            string desc=txtDesc->text().toStdString();
            string state=txtState->text().toStdString();
            string pro1=txtProg1->text().toStdString();
            string pro2=txtProg2->text().toStdString();
            string pro3=txtProg3->text().toStdString();
            string pro4=txtProg4->text().toStdString();
            vector<string> pro;
            if(pro1!=""){
                pro.push_back(pro1);
            }
            if(pro2!=""){
                pro.push_back(pro2);
            }
            if(pro3!=""){
                pro.push_back(pro3);
            }
            if(pro4!=""){
                pro.push_back(pro4);
            }
            Task::validate(id,desc,state,pro);
            srv.add(id,desc,state,pro);
            reloadList(srv.get_all());
        } catch(ValidationException& ve){
            QMessageBox::warning(this,"Warning",QString::fromStdString(ve.getMesg()));
        } catch(DuplicatedIdException& ve){
            QMessageBox::warning(this,"Warning",QString::fromStdString(ve.getMsg()));
        }
    });

    QObject::connect(search,&QLineEdit::editingFinished,[=](){
        if(search->text().toStdString()==""){
            reloadList(srv.get_all());
            return;
        }
        string txt=search->text().toStdString();
        auto rez=srv.filter_by_pro(txt);
        reloadList(rez);
    });



}

void GUI::reloadList(vector<Task> all) {
    tb_wdg->clear();
    sort(all.begin(),all.end(),[](Task& t1,Task& t2){
        if(t1.getState()=="open"){
            return 1;
        } else if(t1.getState()=="inprogress"){
            if(t2.getState()=="closed"){
                return 1;
            }
            return 0;
        }
        return 0;
    });
    tb_wdg->setRowCount(all.size());
    tb_wdg->setColumnCount(4);
    int index=0;
    for(auto& task:all){
        QTableWidgetItem* itm=new QTableWidgetItem;
        itm->setText(QString::number(task.getId()));
        tb_wdg->setItem(index,0,itm);

        itm=new QTableWidgetItem;
        itm->setText(QString::fromStdString(task.getDesc()));
        tb_wdg->setItem(index,1,itm);

        itm=new QTableWidgetItem;
        itm->setText(QString::fromStdString(task.getState()));
        tb_wdg->setItem(index,2,itm);

        itm=new QTableWidgetItem;
        itm->setText(QString::number(task.getPro().size()));
        tb_wdg->setItem(index,3,itm);

        ++index;
    }
}

void Window::initGUI() {
    setLayout(lyMain);

    setWindowTitle(QString::fromStdString(title));

    lst=new QListWidget;
    lyMain->addWidget(lst);

    QWidget* wdg_d=new QWidget;
    QHBoxLayout* lay_d=new QHBoxLayout;
    wdg_d->setLayout(lay_d);

    btnOpen=new QPushButton("Open");
    btnInprog=new QPushButton("In progress");
    btnClosed=new QPushButton("Closed");

    lay_d->addWidget(btnOpen);
    lay_d->addWidget(btnInprog);
    lay_d->addWidget(btnClosed);

    lyMain->addWidget(wdg_d);
}

void Window::connectSignals() {
    QObject::connect(btnOpen,&QPushButton::clicked,[=](){
        if(lst->selectedItems().empty()){
            QMessageBox::warning(this,"Warning","Nu ati selectat nimic");
            return;
        }
        int id=lst->selectedItems().at(0)->data(Qt::UserRole).toInt();
        srv.changeState(id,"open");
    });
    QObject::connect(btnClosed,&QPushButton::clicked,[=](){
        if(lst->selectedItems().empty()){
            QMessageBox::warning(this,"Warning","Nu ati selectat nimic");
            return;
        }
        int id=lst->selectedItems().at(0)->data(Qt::UserRole).toInt();
        srv.changeState(id,"closed");
    });
    QObject::connect(btnInprog,&QPushButton::clicked,[=](){
        if(lst->selectedItems().empty()){
            QMessageBox::warning(this,"Warning","Nu ati selectat nimic");
            return;
        }
        int id=lst->selectedItems().at(0)->data(Qt::UserRole).toInt();
        srv.changeState(id,"inprogress");
    });

}

void Window::reloadList(vector<Task> all) {
    lst->clear();
    for(auto& task:all){
        if(task.getState()==title){
            QListWidgetItem* itm=new QListWidgetItem(QString::fromStdString(task.getDesc()));
            itm->setData(Qt::UserRole,QString::number(task.getId()));
            lst->addItem(itm);
        }
    }
}
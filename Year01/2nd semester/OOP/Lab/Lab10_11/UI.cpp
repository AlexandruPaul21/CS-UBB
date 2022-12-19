//
// Created by Alexandru-Paul Sirbu on 01.05.2022.
//

#include "UI.h"

void GUI::init_GUI() {
    setLayout(lyMain);

    QWidget *w_left = new QWidget;
    QVBoxLayout *l_left = new QVBoxLayout;
    w_left->setLayout(l_left);
    //lst = new QListWidget;
    //table = new QTableWidget(this);

    lst_view->setUniformItemSizes(true);

    l_left->addWidget(lst_view);

    QWidget *wdg=new QWidget;
    QHBoxLayout *lay=new QHBoxLayout;
    wdg->setLayout(lay);
    btnSortName=new QPushButton("Sort by Name");
    btnSortProd=new QPushButton("Sort by Prod");
    btnSortSP  =new QPushButton("Sort by Subst");
    lay->addWidget(btnSortName);
    lay->addWidget(btnSortProd);
    lay->addWidget(btnSortSP);

    l_left->addWidget(wdg);

    QWidget *form_filt=new QWidget;
    QFormLayout *form_filt_lay=new QFormLayout;
    form_filt->setLayout(form_filt_lay);

    QLabel *lbl_filt=new QLabel;
    txtFilt=new QLineEdit;

    form_filt_lay->addRow(lbl_filt,txtFilt);

    l_left->addWidget(form_filt);

    QWidget *flt_btn=new QWidget;
    QHBoxLayout *flt_lay=new QHBoxLayout;
    flt_btn->setLayout(flt_lay);

    btnFilterPrice=new QPushButton("Filter by price");
    btnFilterSubst=new QPushButton("Filter by subst");
    btn_reset=new QPushButton("Reset order");

    flt_lay->addWidget(btn_reset);
    flt_lay->addWidget(btnFilterSubst);
    flt_lay->addWidget(btnFilterPrice);

    l_left->addWidget(flt_btn);

    lyMain->addWidget(w_left);

    //form pentru detalii
    QWidget *w_right=new QWidget;
    QVBoxLayout *lay_right=new QVBoxLayout;
    w_right->setLayout(lay_right);

    QWidget *form=new QWidget;
    QFormLayout *lay_form=new QFormLayout;
    form->setLayout(lay_form);
    QLabel *name=new QLabel("Name");
    txtName=new QLineEdit;
    QLabel *prod=new QLabel("Prod");
    txtProd=new QLineEdit;
    QLabel *subst=new QLabel("Subst");
    txtSubst=new QLineEdit;
    QLabel *price=new QLabel("Price");
    txtPrice=new QLineEdit;

    lay_form->addRow(name,txtName);
    lay_form->addRow(prod,txtProd);
    lay_form->addRow(subst,txtSubst);
    lay_form->addRow(price,txtPrice);

    lay_right->addWidget(form);

    QWidget *but_zone=new QWidget;
    QVBoxLayout *lay_but=new QVBoxLayout;
    but_zone->setLayout(lay_but);

    btn_add=new QPushButton("Add");
    lay_but->addWidget(btn_add);

    QWidget *sec_but=new QWidget;
    QHBoxLayout *sec_lay=new QHBoxLayout;
    sec_but->setLayout(sec_lay);

    btn_del=new QPushButton("Delete");
    btn_mod=new QPushButton("Update");
    sec_lay->addWidget(btn_del);
    sec_lay->addWidget(btn_mod);

    lay_but->addWidget(sec_but);

    btn_undo=new QPushButton("Undo");
    lay_but->addWidget(btn_undo);

    btn_recipe_mst=new QPushButton("Generate Recipe Master");
    lay_but->addWidget(btn_recipe_mst);
    btn_recipe_rdonly=new QPushButton("Generate Recipe Read-Only");
    lay_but->addWidget(btn_recipe_rdonly);

    lay_right->addWidget(but_zone);

    lyMain->addWidget(w_right);
    lb_index=-1;

    opt_but=new QWidget;
    lay_opt=new QVBoxLayout;
    opt_but->setLayout(lay_opt);
    lyMain->addWidget(opt_but);
    plm=new QSlider;
    plm->setMinimum(1);
    plm->setMaximum(100);
    lyMain->addWidget(plm);
}

void GUI::connectSignalsSlots() {
    QObject::connect(plm,&QSlider::valueChanged,[=](){
        txtPrice->setText(QString::number(plm->value()));
    });


//    QObject::connect(lst,&QListWidget::itemSelectionChanged,[&](){
//        auto sel=lst->selectedItems();
//        if(sel.empty()){
//            txtName->setText("");
//            txtProd->setText("");
//            txtSubst->setText("");
//            txtPrice->setText("");
//        } else {
//            auto sel_item=sel.at(0);
//            auto name=sel_item->text();
//            auto prod=sel_item->data(Qt::UserRole).toString();
//            Medicine to_print;
//            int index=0;
//            for(const auto& med : srv.get_all_ent()){
//                if(med.get_name()==name.toStdString() && med.get_prod()==prod.toStdString()){
//                    to_print=med;
//                    lb_index=index;
//                }
//                ++index;
//            }
//            txtName->setText(name);
//            txtProd->setText(prod);
//            txtSubst->setText(QString::fromStdString(to_print.get_subst()));
//            txtPrice->setText(QString::number(to_print.get_price()));
//        }
//    });

//    QObject::connect(table,&QTableWidget::cellClicked,[=](){
//        auto sel=table->selectedItems();
//        if(sel.empty()){
//            txtName->setText("");
//            txtProd->setText("");
//            txtSubst->setText("");
//            txtPrice->setText("");
//        } else {
//            auto sel_item=sel.at(0);
//            string name=sel_item->text().toStdString();
//            Medicine m;
//            lb_index=0;
//            for(auto& med: srv.get_all_ent()){
//                if(med.get_name()==name || med.get_subst()==name){
//                    m=med;
//                    break;
//                }
//                ++lb_index;
//            }
//            txtName->setText(QString::fromStdString(m.get_name()));
//            txtProd->setText(QString::fromStdString(m.get_prod()));
//            txtSubst->setText(QString::fromStdString(m.get_subst()));
//            txtPrice->setText(QString::number(m.get_price()));
//        }
//    });


    QObject::connect(btnSortName,&QPushButton::clicked,[&](){
        vector<Medicine> meds;
        meds.resize(srv.get_all_ent().size());
        srv.sort(0,meds);
        reloadList(meds);
    });

    QObject::connect(btnSortProd,&QPushButton::clicked,[&](){
        vector<Medicine> meds;
        meds.resize(srv.get_all_ent().size());
        srv.sort(1,meds);
        reloadList(meds);
    });

    QObject::connect(btnSortSP,&QPushButton::clicked,[&](){
        vector<Medicine> meds;
        meds.resize(srv.get_all_ent().size());
        srv.sort(2,meds);
        reloadList(meds);
    });

    QObject::connect(btn_add,&QPushButton::clicked,this,&GUI::addMed);
    QObject::connect(btn_del,&QPushButton::clicked,this,&GUI::delMed);
    QObject::connect(btn_mod,&QPushButton::clicked,this,&GUI::uptMed);
    QObject::connect(btn_undo,&QPushButton::clicked,this,&GUI::undoMed);

    QObject::connect(btn_reset,&QPushButton::clicked,[=](){
       reloadList(srv.get_all_ent());
       reset_form();
    });

    QObject::connect(btnFilterPrice,&QPushButton::clicked,[=](){
        string val_str=txtFilt->text().toStdString();
        try{
            vector<Medicine> rez;
            rez.clear();
            srv.filter(0,val_str,rez);
            reloadList(rez);
        } catch(RepoException& re){
            QMessageBox::warning(this,"Warning",QString::fromStdString(toMyString(re.msg)));
        }
    });

    QObject::connect(btnFilterSubst,&QPushButton::clicked,[=](){
        string val_str=txtFilt->text().toStdString();
        try{
            vector<Medicine> rez;
            rez.clear();
            srv.filter(1,val_str,rez);
            reloadList(rez);
        } catch(RepoException& re){
            QMessageBox::warning(this,"Warning",QString::fromStdString(toMyString(re.msg)));
        }
    });

    QObject::connect(btn_recipe_mst,&QPushButton::clicked,[=](){
        auto wind=new RecipeGUI{rep,srv};
        rcp.push_back(wind);
        wind->show();
    });

    QObject::connect(btn_recipe_rdonly,&QPushButton::clicked,[=](){
        auto wind=new PaintGUI{rep};
        pg.push_back(wind);
        wind->show();
    });

    QObject::connect(lst_view->selectionModel(),&QItemSelectionModel::selectionChanged,[=](){
        auto sel=lst_view->selectionModel();
        if(sel->selectedIndexes().empty()){
            txtName->setText("");
            txtSubst->setText("");
            txtPrice->setText("");
            txtProd->setText("");
            return;
        }
        auto sel_index=sel->selectedIndexes().at(0).row();
        auto elem=act_list[sel_index];
        auto name=lst_view->model()->data(lst_view->model()->index(sel_index,0),Qt::DisplayRole).toString();
        lb_index=sel_index;
        txtName->setText(name);
        txtProd->setText(QString::fromStdString(elem.get_prod()));
        txtSubst->setText(QString::fromStdString(elem.get_subst()));
        txtPrice->setText(QString::number(elem.get_price()));
    });
}

void GUI::reloadList(vector<Medicine>& meds) {
    act_list=meds;
    model->setMeds(meds);
//    lst->clear();
//    for(const auto& med : meds){
//        QListWidgetItem *item=new QListWidgetItem(QString::fromStdString(med.get_name()));
//        item->setData(Qt::UserRole,QString::fromStdString(med.get_prod()));
//        if(med.get_price()>=100) {
//            item->setData(Qt::BackgroundRole,QBrush{Qt::green,Qt::SolidPattern});
//        }
//        lst->addItem(item);
//    }

//    table->clear();
//    table->setColumnCount(2);
//    table->setRowCount(meds.size());
//    int i=0;
//    for(const auto&  med :meds){
//        QTableWidgetItem *item=new QTableWidgetItem(QString::fromStdString(med.get_name()));
//        QTableWidgetItem *item1=new QTableWidgetItem(QString::fromStdString(med.get_subst()));
//        table->setItem(i,0,item);
//        table->setItem(i,1,item1);
//        ++i;
//    }
}

void GUI::addMed() {
    try{
        srv.add(txtName->text().toStdString(),txtProd->text().toStdString(),txtSubst->text().toStdString(),txtPrice->text().toInt());
        reloadList(srv.get_all_ent());
        updateBut(srv.get_all_ent());
    } catch (ValidationException& ve){
        QMessageBox::warning(this,"Warning",QString::fromStdString(toMyString(ve.msg)));
    } catch (RepoException& re){
        QMessageBox::warning(this,"Warning",QString::fromStdString(toMyString(re.msg)));
    }
}

void GUI::delMed() {
    auto name=txtName->text().toStdString();
    auto prod=txtProd->text().toStdString();
    auto subst=txtSubst->text().toStdString();
    auto price=txtPrice->text().toInt();
    auto m=Medicine(name,prod,subst,price);
    int index=0;
    for(const auto& med : srv.get_all_ent()){
        if(med==m){
            break;
        }
        ++index;
    }
    if(index==srv.get_all_ent().size()){
        QMessageBox::warning(this,"Warning",QString::fromStdString("Nu exista elementul introdus"));
    } else {
        srv.del(index);
        reloadList(srv.get_all_ent());
        updateBut(srv.get_all_ent());
    }
}

void GUI::uptMed() {
    auto name=txtName->text().toStdString();
    auto prod=txtProd->text().toStdString();
    auto subst=txtSubst->text().toStdString();
    auto price=txtPrice->text().toInt();

    if(lb_index==-1){
        QMessageBox::warning(this,"Warning",QString::fromStdString("Nu ati selectat nici-un element"));
        return;
    }

    try {
        srv.modify(lb_index, name, prod, subst, price);
        reloadList(srv.get_all_ent());
        updateBut(srv.get_all_ent());
    } catch(ValidationException& ve){
        QMessageBox::warning(this,"Warning",QString::fromStdString(toMyString(ve.msg)));
    }
}

void clearLayout(QLayout *layout){
    if (layout == NULL)
        return;
    QLayoutItem *item;
    while((item = layout->takeAt(0))) {
        if (item->layout()) {
            clearLayout(item->layout());
            delete item->layout();
        }
        if (item->widget()) {
            delete item->widget();
        }
        delete item;
    }
}

void GUI::updateBut(vector<Medicine> &all) {
    clearLayout(lay_opt);
    set<string> unique;
    for(const auto& med : srv.get_all_ent()){
        unique.insert(med.get_subst());
    }

    subst_but.clear();

    for(auto& it : unique){
        subst_but.push_back(new QPushButton(QString::fromStdString(it)));
    }

    for(auto btn : subst_but){
        lay_opt->addWidget(btn);
        QObject::connect(btn,&QPushButton::clicked,[=](){
            auto val=btn->text().toStdString();
            int nr=0;
            for(const auto& med : srv.get_all_ent()){
                if(med.get_subst()==val){
                    ++nr;
                }
            }
            QMessageBox::information(nullptr,"Info",QString::number(nr));
        });
    }
}

void GUI::reset_form(){
    txtName->setText("");
    txtProd->setText("");
    txtSubst->setText("");
    txtPrice->setText("");
}

void GUI::undoMed() {
    try {
        srv.undo();
        reloadList(srv.get_all_ent());
        updateBut(srv.get_all_ent());
    } catch(RepoException& re){
        QMessageBox::warning(this,"Warning",QString::fromStdString("Nu se mai poate face undo"));
    }
}

void RecipeGUI::initGUI() {
    setLayout(recipe_main_layout);

    recipe_lst=new QListWidget;
    recipe_main_layout->addWidget(recipe_lst);

    QWidget *recipe_but_zone=new QWidget;
    QVBoxLayout *recipe_but_layout=new QVBoxLayout;
    recipe_but_zone->setLayout(recipe_but_layout);

    QWidget *recipe_form=new QWidget;
    QFormLayout *recipe_form_layout=new QFormLayout;
    recipe_form->setLayout(recipe_form_layout);

    QLabel *lbl_recipe=new QLabel("Input");
    lne_recipe=new QLineEdit;

    recipe_form_layout->addRow(lbl_recipe,lne_recipe);

    recipe_but_layout->addWidget(recipe_form);

    add_to_rec=new QPushButton("Add");
    empty_rec=new QPushButton("Empty recipe");
    random_add=new QPushButton("Add random");
    export_recipe=new QPushButton("Export");
    help_button=new QPushButton("Help");

    recipe_but_layout->addWidget(add_to_rec);
    recipe_but_layout->addWidget(empty_rec);
    recipe_but_layout->addWidget(random_add);
    recipe_but_layout->addWidget(export_recipe);
    recipe_but_layout->addWidget(help_button);

    recipe_main_layout->addWidget(recipe_but_zone);

    reloadMeds(rep.get_all());
}

void RecipeGUI::reloadMeds(vector<Medicine> meds) {
    recipe_lst->clear();
    for(const auto& med : meds){
        QListWidgetItem *item=new QListWidgetItem(QString::fromStdString(med.get_name()));
        recipe_lst->addItem(item);
    }
}

void RecipeGUI::connectSignals() {
    QObject::connect(add_to_rec,&QPushButton::clicked,[=](){
        auto inp=lne_recipe->text().toStdString();
        int nr=0;
        bool valid=true;
        for(auto& ch : inp){
            if('0'<=ch && ch<='9'){
                nr=nr*10+(ch-'0');
            } else {
                valid=false;
            }
        }
        if(nr>srv.get_all_ent().size()){
            valid=false;
        }
        if(valid){
            rep.add_to_recipe(srv.get_all_ent()[nr]);
            reloadMeds(rep.get_all());
        } else {
            QMessageBox::warning(this,"Warning",QString::fromStdString("Index invalid"));
        }
    });

    QObject::connect(empty_rec,&QPushButton::clicked,[=](){
        rep.empty_recipe();
        reloadMeds(rep.get_all());
        //reset_form();
    });

    QObject::connect(random_add,&QPushButton::clicked,[=](){
        auto inp=lne_recipe->text().toStdString();
        int nr=0;
        bool valid=true;
        for(auto& ch : inp){
            if('0'<=ch && ch<='9'){
                nr=nr*10+(ch-'0');
            } else {
                valid=false;
            }
        }
        if(valid) {
            rep.random_add(srv.get_all_ent(), nr);
            reloadMeds(rep.get_all());
        } else {
            QMessageBox::warning(this,"Warning",QString::fromStdString("Numar invalid"));
        }
    });

    QObject::connect(export_recipe,&QPushButton::clicked,[=](){
        auto filename=lne_recipe->text().toStdString();
        rep.save_to_file(filename);
    });

    QObject::connect(help_button,&QPushButton::clicked,[=](){
        string msg="Campul input se foloseste pentru a comunica optiunile\n";
        msg+="Pentru Add in input se va specifica indicele de adaugat\n";
        msg+="Pentru Add random in input se va specifica numarul de entitati de adaugat\n";
        msg+="Pentru Export in input se va specifica numele fisierului in care se face exportul\n";
        QMessageBox::information(this,"Help",QString::fromStdString(msg));
    });
}
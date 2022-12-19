#include <QApplication>
#include "UI.h"

int main(int argc, char *argv[]) {
    test_domain();
    test_repo();
    test_service();

    QApplication a(argc,argv);

    FileRepo *rp=new FileRepo{"data.txt"};
    Validator valid;
    Service srv{rp,valid};

    GUI g{srv};
    g.show();

    return a.exec();
}

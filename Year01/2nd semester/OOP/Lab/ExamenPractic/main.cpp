#include <QApplication>
#include <QPushButton>

#include "UI.h"

/**
 * Masterul aplicatiei
 * @param argc nr de argumente
 * @param argv argumente
 * @return QApplication
 */
int main(int argc, char *argv[]) {

    test_domain();
    test_repo();
    test_service();

    QApplication a(argc, argv);

    FileRepo fr{"data.txt"};
    Service srv{fr};
    GUI gui{srv};

    gui.show();

    return QApplication::exec();
}
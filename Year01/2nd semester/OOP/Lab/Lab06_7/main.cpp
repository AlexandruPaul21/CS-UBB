#include "tests.h"

/**
 * Master
 * @return 0
 */
int main() {
    test_all();
    auto* repo=new FileRepo{"data.txt"};
    Validator valid;
    Service srv{repo,valid};

    auto master=Console(srv);
    master.show_ui();
    delete repo;
    return 0;
}

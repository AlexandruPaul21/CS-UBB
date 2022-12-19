//#define _CRTDBG_MAP_ALLOC
//#include <stdlib.h>
//#include <crtdbg.h>
#include "teste.h"
#include "UI.h"

/**
 * Masterul aplicatiei
 * @return 0
 */
int main() {
    test_all();
    show_ui();
    //_CrtDumpMemoryLeaks();
    return 0;
}

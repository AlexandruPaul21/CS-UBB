cmake_minimum_required(VERSION 3.21)
project(Lab10_11v2)

set(CMAKE_CXX_STANDARD 20)
set(CMAKE_AUTOMOC ON)
set(CMAKE_AUTORCC ON)
set(CMAKE_AUTOUIC ON)

set(CMAKE_PREFIX_PATH "/Users/alex/Qt/6.3.0/macos/lib/cmake")

find_package(Qt6 COMPONENTS
        Core
        Gui
        Widgets
        REQUIRED)

add_executable(Lab10_11v2 main.cpp UI.cpp UI.h service.cpp service.h repo.cpp repo.h domain.cpp domain.h undo_act.h
                recipe_class.cpp recipe_class.h observer.h MyListModel.h)
target_link_libraries(Lab10_11v2
        Qt::Core
        Qt::Gui
        Qt::Widgets
        )


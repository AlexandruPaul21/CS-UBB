cmake_minimum_required(VERSION 3.21)
project(ExamenPractic)

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

add_executable(ExamenPractic main.cpp domain.cpp domain.h repo.cpp repo.h service.cpp service.h UI.cpp UI.h observer.h)
target_link_libraries(ExamenPractic
        Qt::Core
        Qt::Gui
        Qt::Widgets
        )
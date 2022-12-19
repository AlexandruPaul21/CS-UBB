cmake_minimum_required(VERSION 3.21)
project(Lab05)

set(CMAKE_CXX_STANDARD 14)

include_directories("Proiect C++")
include_directories("Proiect C++/Teste")

add_executable(Lab05
        "Proiect C++/Teste/TestExtins.cpp"
        "Proiect C++/Teste/TestExtins.h"
        "Proiect C++/Teste/TestScurt.cpp"
        "Proiect C++/Teste/TestScurt.h"
        "Proiect C++/App.cpp"
        "Proiect C++/IteratorMDO.cpp"
        "Proiect C++/IteratorMDO.h"
        "Proiect C++/MDO.cpp"
        "Proiect C++/MDO.h")

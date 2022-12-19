cmake_minimum_required(VERSION 3.21)
project(Lab02)

set(CMAKE_CXX_STANDARD 14)

include_directories(.)
include_directories(Teste)

add_executable(Lab02
        App.cpp
        Teste/TestExtins.cpp
        Teste/TestExtins.h
        Teste/TestScurt.cpp
        Teste/TestScurt.h
        IteratorMD.cpp
        IteratorMD.h
        MD.cpp
        MD.h)

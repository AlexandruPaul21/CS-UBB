cmake_minimum_required(VERSION 3.21)
project(Lab03)

set(CMAKE_CXX_STANDARD 14)

include_directories(.)
include_directories(Teste)

add_executable(Lab03
        Teste/TestExtins.cpp
        Teste/TestExtins.h
        Teste/TestScurt.cpp
        Teste/TestScurt.h
        App.cpp
        IteratorLP.cpp
        IteratorLP.h
        Lista.cpp
        Lista.h)

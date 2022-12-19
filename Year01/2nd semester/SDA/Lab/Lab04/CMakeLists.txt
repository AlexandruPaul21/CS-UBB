cmake_minimum_required(VERSION 3.21)
project(Lab04)

set(CMAKE_CXX_STANDARD 14)

include_directories(.)
include_directories(Teste)

add_executable(Lab04
        Teste/TestExtins.cpp
        Teste/TestExtins.h
        Teste/TestScurt.cpp
        Teste/TestScurt.h
        App.cpp
        Dictionar.cpp
        Dictionar.h
        IteratorDictionar.cpp
        IteratorDictionar.h)

cmake_minimum_required(VERSION 3.21)
project(Lab01)

set(CMAKE_CXX_STANDARD 14)

include_directories(.)
include_directories(Teste)

add_executable(Lab01
        App.cpp
        Teste/TestExtins.cpp
        Teste/TestExtins.h
        Teste/TestScurt.cpp
        Teste/TestScurt.h
        IteratorVectorDinamic.cpp
        IteratorVectorDinamic.h
        VectorDinamic.cpp
        VectorDinamic.h)

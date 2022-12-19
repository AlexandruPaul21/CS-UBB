cmake_minimum_required(VERSION 3.21)
project(LabDianabun C)

set(CMAKE_C_STANDARD 99)

set (CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -Wall -Wextra")

add_executable(LabDianabun main.c domain.c domain.h repo.c repo.h service.c service.h teste.c teste.h UI.c UI.h)

cmake_minimum_required(VERSION 3.10)
project(Circular_buffer)

set(CMAKE_CXX_STANDARD 14)

include_directories(${PROJECT_SOURCE_DIR})

if(CMAKE_COMPILER_IS_GNUCC OR CMAKE_COMPILER_IS_GNUCXX)
    set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -Wall -std=c++14 -pedantic")
    set(CMAKE_CXX_FLAGS_DEBUG "${CMAKE_CXX_FLAGS_DEBUG} -fsanitize=address,undefined -D_GLIBCXX_DEBUG")
endif()

add_executable(Circular_buffer main.cpp circular_buffer.h
        gtest/gtest-all.cc gtest/gtest.h gtest/gtest_main.cc
        )

target_link_libraries(Circular_buffer -lgmp -lgmpxx -lpthread)
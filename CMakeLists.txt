#cmake_minimum_required(VERSION 3.10)
#project(exam)
#
#set(CMAKE_CXX_STANDARD 14)
#
#if(CMAKE_COMPILER_IS_GNUCC OR CMAKE_COMPILER_IS_GNUCXX)
#    set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -Wall -std=c++14 -pedantic")
#    set(CMAKE_CXX_FLAGS_DEBUG "${CMAKE_CXX_FLAGS_DEBUG} -fsanitize=address,undefined -D_GLIBCXX_DEBUG")
#endif()
#
#add_executable(exam main.cpp list.h test.cpp)

cmake_minimum_required(VERSION 2.5)
project(list)

set(CMAKE_CXX_STANDARD 14)
set(CMAKE_CXX_FLAGS_DEBUG "${CMAKE_CXX_FLAGS_DEBUG} -fsanitize=address,undefined -g -D_GLIBCXX_DEBUG")
set(CMAKE_CXX_FLAGS_RELEASE "-O3")

add_executable(testing
        testing.cpp
        list.h
        gtest/gtest-all.cc
        gtest/gtest.h
        gtest/gtest_main.cc)

include_directories(${CMAKE_SOURCE_DIR})
target_link_libraries(testing -lpthread)
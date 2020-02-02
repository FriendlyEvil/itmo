cmake_minimum_required(VERSION 3.10)
project(Huffman)

set(CMAKE_CXX_STANDARD 14)

add_library(huffman_lib
        bits_seq.h
        bits_seq.cpp
        huf_tree.h
        huf_tree.cpp
        huffman_code.h
        huffman_code.cpp)

install(TARGETS huffman_lib DESTINATION lib)

install (FILES
        bits_seq.h
        huf_tree.h
        huffman_code.h
        DESTINATION include/huffman_lib)

add_executable(Huffman main.cpp)

target_link_libraries(Huffman huffman_lib)
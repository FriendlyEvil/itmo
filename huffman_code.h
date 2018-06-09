#ifndef HUFFMAN_HUFFMAN_COD_H
#define HUFFMAN_HUFFMAN_COD_H

#include <unordered_map>
#include <vector>
#include "bits_seq.h"
#include "huf_tree.h"

struct frequency_counter {
    // 3: это библиотека, должны скрываться детали реализации
    std::vector<uint64_t> file_freqs;

    frequency_counter();

    void add(const uint8_t *data, size_t size);
};

struct huffman_encoder {
    // ^3
    std::unordered_map<uint8_t, bits_sequence> symbol_map;
    freq_tree tree;

    explicit huffman_encoder(const frequency_counter &freqs);

    bits_sequence encoder(const uint8_t *data, size_t size);

    void get_encode(bits_sequence &bits, std::vector<uint8_t> &alpha);
};

struct huffman_decoder {
    //^3
    freq_tree tree;
    std::vector<uint8_t> result;

    huffman_decoder(uint8_t *tree_bits, size_t size_tree, size_t size_alpha);

    std::vector<uint8_t> decode(const bits_sequence &seq);
};


#endif //HUFFMAN_HUFFMAN_COD_H

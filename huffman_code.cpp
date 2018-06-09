#include "huffman_code.h"

frequency_counter::frequency_counter() : file_freqs(256) {
    for (size_t i = 0; i < file_freqs.size(); ++i) {
        file_freqs[i] = 0;
    }
}

void frequency_counter::add(const uint8_t *data, size_t size) {
    for (size_t cur_ind = 0; cur_ind < size; ++cur_ind) {
        file_freqs[data[cur_ind]]++;
    }
}

huffman_encoder::huffman_encoder(const frequency_counter &freqs) : tree(freqs.file_freqs) {
    symbol_map = tree.create_alphabet();
}

bits_sequence huffman_encoder::encoder(const uint8_t *data, size_t size) {
    bits_sequence res;
    for (size_t i = 0; i < size; i++)
        res.merge(symbol_map[data[i]]);
    return res;
}

void huffman_encoder::get_encode(bits_sequence &bits, std::vector<uint8_t> &alpha) {
    tree.create_code(alpha, bits);
}

huffman_decoder::huffman_decoder(uint8_t *tree_expl_bits, size_t size_tree, size_t size_alpha) : tree(tree_expl_bits,
                                                                                                      size_tree,
                                                                                                      size_alpha) {}

std::vector<uint8_t> huffman_decoder::decode(const bits_sequence &seq) {
    std::vector<uint8_t> result;
    size_t cur_ind = 0;
    uint8_t sym;
    while (tree.get(seq, cur_ind, sym)) {
        result.push_back(sym);
    }
    return result;
}
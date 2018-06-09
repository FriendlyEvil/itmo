#include <limits>

#include "huffman_code.h"

frequency_counter::frequency_counter()
    // 5: не используй неименованные литералы
    : file_freqs(std::numeric_limits<uint8_t>::max(), 0)
{}

void frequency_counter::add(const uint8_t *data, size_t size) {
    // 4: используй for_each
    std::for_each(data, data + size, [this] (uint8_t c) { file_freqs[c]++; });
}

huffman_encoder::huffman_encoder(const frequency_counter &freqs)
        : tree(freqs.file_freqs)
{
    symbol_map = tree.create_alphabet();
}

bits_sequence huffman_encoder::encoder(const uint8_t *data, size_t size) {
    bits_sequence res;
    // ^4
    for (size_t i = 0; i < size; i++)
        res.merge(symbol_map[data[i]]);
    return res;
}

void huffman_encoder::get_encode(bits_sequence &bits, std::vector<uint8_t> &alpha) {
    tree.create_code(alpha, bits);
}

huffman_decoder::huffman_decoder(uint8_t *tree_expl_bits, size_t size_tree, size_t size_alpha)
    : tree(tree_expl_bits, size_tree, size_alpha)
{}

std::vector<uint8_t> huffman_decoder::decode(const bits_sequence &seq) {
    std::vector<uint8_t> result;
    size_t cur_ind = 0;
    uint8_t sym;
    while (tree.get(seq, cur_ind, sym)) {
        result.push_back(sym);
    }
    return result;
}
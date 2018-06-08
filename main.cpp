#include <iostream>
#include <fstream>
#include <cstring>
#include "huffman_code.h"
#include "bits_seq.h"

#define BLOCK 1 << 18

void write_seq(const bits_sequence &bs, std::ofstream &out) {
    uint32_t size = bs.size();
    out.write((char *) &size, sizeof(uint32_t));
    size_t ost = bs.size() % bits_sequence::size_of;
    if (ost != 0)
        out.write((char *) bs.data().data(), (bs.size() / bits_sequence::size_of + 1) * sizeof(uint64_t));
    else
        out.write((char *) bs.data().data(), bs.size() / bits_sequence::size_of * sizeof(uint64_t));
}

int main(int argc, char *argv[]) {
    try {

        if (argc != 4)
            throw std::runtime_error("We need 4 arguments");

        std::ifstream in(argv[2], std::ios::in | std::ios::binary);
        std::ofstream out(argv[3], std::ios::out | std::ios::binary);

        if (!in)
            throw std::runtime_error("Incorrect input files");
        if (!out)
            throw std::runtime_error("Incorrect output files");

        if (strcmp(argv[1], "e") == 0) {
            frequency_counter fc;
            std::vector<uint8_t> block(BLOCK);

            while (in) {
                memset(block.data(), 0, block.size());
                if (in.read((char *) block.data(), block.size()).gcount() == 0) {
                    return 0; //empty file
                };
                fc.add(block.data(), (size_t) in.gcount());
            }

            in.clear();
            in.seekg(0);

            huffman_encoder he(fc);

            bits_sequence bs;
            std::vector<uint8_t> alphabet;
            he.get_encode(bs, alphabet);

            uint32_t alphabet_size = alphabet.size();
            out.write((char *) &alphabet_size, sizeof(uint32_t));
            write_seq(bs, out);
            out.write((char *) alphabet.data(), alphabet.size());

            while (in) {
                in.read((char *) block.data(), block.size());
                write_seq(he.encoder(block.data(), (size_t) in.gcount()), out);
            }
        } else if (strcmp(argv[1], "d") == 0) {
            uint32_t size_tree = 0, size_alphabet = 0;

            if (in.read((char *) &size_alphabet, sizeof(uint32_t)).gcount() == 0)
                return 0; // empty file
            if (in.read((char *) &size_tree, sizeof(uint32_t)).gcount() == 0)
                throw std::runtime_error("Incorrect huffman code");

            uint32_t size_mem = (size_tree - 1) / bits_sequence::size_of + 1;

            std::vector<uint8_t> mem_char(size_mem * sizeof(uint64_t) + size_alphabet);
            if ((size_t) in.read((char *) mem_char.data(), mem_char.size()).gcount() != mem_char.size())
                throw std::runtime_error("Incorrect huffman code");

            huffman_decoder hd(mem_char.data(), size_tree, size_alphabet);

            while (in) {
                uint32_t size_bytes = 0, size_bits = 0;
                if (in.read((char *) &size_bits, sizeof(uint32_t)).gcount() == 0)
                    break;

                size_bytes = (size_bits - 1) / bits_sequence::size_of + 1;

                std::vector<uint64_t> mem(size_bytes);
                if ((size_t) in.read((char *) mem.data(), mem.size() * sizeof(uint64_t)).gcount() !=
                    mem.size() * sizeof(uint64_t))
                    throw std::runtime_error("Incorrect huffman code");

                bits_sequence bs(mem);
                if (size_bits % bits_sequence::size_of != 0)
                    bs.remove(bits_sequence::size_of - (size_bits % bits_sequence::size_of));
                std::vector<uint8_t> res = hd.decode(bs);
                out.write((char *) res.data(), res.size());
            }
        } else {
            throw std::runtime_error("Incorrect flag. We need 'e' or 'd'");
        }
    } catch (std::runtime_error &ex) {
        std::cout << "Incorrect file format or program arguments";
    }
}
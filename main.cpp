#include <iostream>
#include <fstream>
#include <cstring>
#include "huffman_code.h"
#include "bits_seq.h"

static const int BLOCK = 1 << 18;

void write_seq(const bits_sequence &bs, std::ofstream &out) {
    uint32_t size = bs.size();
    out.write((char *) &size, sizeof(uint32_t));
    size_t ost = bs.size() % bits_sequence::size_of;
    if (ost != 0)
        out.write((char *) bs.data().data(), (bs.size() / bits_sequence::size_of + 1) * sizeof(uint64_t));
    else
        out.write((char *) bs.data().data(), bs.size() / bits_sequence::size_of * sizeof(uint64_t));
}

void help() {
    std::cout << "Enter 'e input output' for encoding or 'd input output' for decoding\n";
}

int main(int argc, char *argv[]) {
    try {
        if (argc != 4) {
            std::cout << "We need 4 arguments\n";
            help();
            return 1;
        }

        std::ifstream in(argv[2], std::ios::in | std::ios::binary);
        std::ofstream out(argv[3], std::ios::out | std::ios::binary);

        if (!in) {
            std::cout << "Incorrect input files";
            return 1;
        }
        if (!out) {
            std::cout << "Incorrect output files";
            return 1;
        }

        if (std::string(argv[1]) == "e") {
            frequency_counter fc;
            std::vector<uint8_t> block(BLOCK);

            try {
                while (in) {
                    memset(block.data(), 0, block.size());
                    if (in.read((char *) block.data(), block.size()).gcount() == 0) {
                        return 0; //empty file
                    };
                    fc.add(block.data(), (size_t) in.gcount());
                }
            } catch (std::runtime_error &ex) {
                in.close();
                std::cout << "File read error";
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
        } else if (std::string(argv[1]) == "d") {
            uint32_t size_tree = 0, size_alphabet = 0;

            if (in.read((char *) &size_alphabet, sizeof(uint32_t)).gcount() == 0)
                return 0; // empty file
            if (in.read((char *) &size_tree, sizeof(uint32_t)).gcount() == 0) {
                std::cout << "Incorrect huffman code. You encrypt the file with other program\n";
                return 1;
            }

            uint32_t size_mem = (size_tree - 1) / bits_sequence::size_of + 1;

            std::vector<uint8_t> mem_char(size_mem * sizeof(uint64_t) + size_alphabet);
            if ((size_t) in.read((char *) mem_char.data(), mem_char.size()).gcount() != mem_char.size()) {
                std::cout << "Incorrect huffman code. You encrypt the file with other program\n";
                return 1;
            }

            huffman_decoder hd(mem_char.data(), size_tree, size_alphabet);

            while (in) {
                uint32_t size_bytes = 0, size_bits = 0;
                if (in.read((char *) &size_bits, sizeof(uint32_t)).gcount() == 0)
                    break;

                size_bytes = (size_bits - 1) / bits_sequence::size_of + 1;

                std::vector<uint64_t> mem(size_bytes);
                if ((size_t) in.read((char *) mem.data(), mem.size() * sizeof(uint64_t)).gcount() !=
                    mem.size() * sizeof(uint64_t)) {
                    std::cout << "Incorrect huffman code. You encrypt the file with other program\n";
                    return 1;
                }

                bits_sequence bs(mem);
                if (size_bits % bits_sequence::size_of != 0)
                    bs.remove(bits_sequence::size_of - (size_bits % bits_sequence::size_of));
                std::vector<uint8_t> res = hd.decode(bs);
                out.write((char *) res.data(), res.size());
            }
        } else {
            std::cout << "Incorrect flag. We need 'e' or 'd'\n";
            help();
            return 1;
        }
    } catch (std::runtime_error &ex) {
        std::cout << "Incorrect file format or program arguments\n";
        help();
    }
}
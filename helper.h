#include <string>
#include <zconf.h>
#include <wait.h>
#include <vector>
#include <boost/algorithm/string.hpp>

#ifndef OS_FIND_HELPER_H
#define OS_FIND_HELPER_H

const static char *BLUE = "\033[1;34m";
const static char *GREEN = "\033[0;32m";
const static char *RED = "\033[1;31m";
const static char *END = "\033[0m";

void printColorText(const char *color, const char *text) {
    printf("%s%s%s", color, text, END);
}

void printError(const char *text, const char *moreText) {
    printColorText(RED, text);
    printf("%s\n", moreText);
}

void printError(const char *text, std::string &moreText) {
    printColorText(RED, text);
    std::cout << moreText << "\n";
}

char *stringToChar(std::string str) {
    char *cstr = new char[str.length() + 1];
    strcpy(cstr, str.c_str());
    return cstr;
}

std::vector<char *> vectorStringToChar(std::vector<std::string> &vec) {
    std::vector<char *> res;
    for (const std::string &s : vec) {
        res.push_back(stringToChar(s));
    }
    return res;
}

std::vector<char *> parseString(std::string &command) {
    std::vector<std::string> vector;
    boost::split(vector, command, [](char ch) { return ch == ' '; });
    return vectorStringToChar(vector);
}




#endif //OS_FIND_HELPER_H

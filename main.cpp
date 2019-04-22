#include <iostream>
#include "compiler.h"

void help() {
    printf("  Commands:\n");
    printf("-    ex a :  return the result of raising the number 'a' to the power of 3\n");
    printf("-    mod a b :  return the result of raising the number 'a' to the power of 'b'\n");
}

void printRes(int res, int a, int b = 3) {
    printf("%d^%d = %d\n", a, b, res);
}

int main() {
    std::string input;
    std::cin >> input;
    int a, n;

    try {
        compiler comp = compiler();
        if (input == "ex") {
            std::cin >> a;
            printRes(comp.execute(a), a);
        } else if (input == "mod") {
            std::cin >> a >> n;
            printRes(comp.patchExecute(a, n), a, n);
        } else {
            help();
        }
    } catch (std::exception &e) {
        return -1;
    }
}

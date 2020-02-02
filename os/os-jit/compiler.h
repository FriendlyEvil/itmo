#ifndef OS_JIT_FUNCTION_H
#define OS_JIT_FUNCTION_H


#include <cstring>
#include <sys/mman.h>
#include <cstdio>
#include <bits/exception.h>

int pow() {
    const int pow = 2;
    const int num = 3;

    int n = pow;
    int a = num;
    int res = 1;
    while (n != 0)
        if (n & 1) {
            res *= a;
            n--;
        } else {
            a *= a;
            n /= 2;
        }
    return res;
}

// disassembling pow
static constexpr unsigned char code[] = {
        0x55                                         // push   %rbp
        , 0x48, 0x89, 0xe5                           // mov    %rsp,%rbp
        , 0xc7, 0x45, 0xf8, 0x03, 0x00, 0x00, 0x00   //	movl   $0x3,-0x8(%rbp)
        , 0xc7, 0x45, 0xfc, 0x04, 0x00, 0x00, 0x00   //	movl   $0x4,-0x4(%rbp)
        , 0xc7, 0x45, 0xec, 0x03, 0x00, 0x00, 0x00   //	movl   $0x3,-0x14(%rbp)
        , 0xc7, 0x45, 0xf0, 0x04, 0x00, 0x00, 0x00   //	movl   $0x4,-0x10(%rbp)
        , 0xc7, 0x45, 0xf4, 0x01, 0x00, 0x00, 0x00   //	movl   $0x1,-0xc(%rbp)
        , 0x83, 0x7d, 0xec, 0x00                     // cmpl   $0x0,-0x14(%rbp)
        , 0x74, 0x35                                 // je     62 <_Z3powv+0x62>
        , 0x8b, 0x45, 0xec                           // mov    -0x14(%rbp),%eax
        , 0x83, 0xe0, 0x01                           // and    $0x1,%eax
        , 0x85, 0xc0                                 //	test   %eax,%eax
        , 0x74, 0x10                                 //	je     47 <_Z3powv+0x47>
        , 0x8b, 0x45, 0xf4                           // mov    -0xc(%rbp),%eax
        , 0x0f, 0xaf, 0x45, 0xf0                     // imul   -0x10(%rbp),%eax
        , 0x89, 0x45, 0xf4                           // mov    %eax,-0xc(%rbp)
        , 0x83, 0x6d, 0xec, 0x01                     // subl   $0x1,-0x14(%rbp)
        , 0xeb, 0xe0                                 // jmp    27 <_Z3powv+0x27>
        , 0x8b, 0x45, 0xf0                           // mov    -0x10(%rbp),%eax
        , 0x0f, 0xaf, 0x45, 0xf0                     // imul   -0x10(%rbp),%eax
        , 0x89, 0x45, 0xf0                           //	mov    %eax,-0x10(%rbp)
        , 0x8b, 0x45, 0xec                           //	mov    -0x14(%rbp),%eax
        , 0x89, 0xc2                                 //	mov    %eax,%edx
        , 0xc1, 0xea, 0x1f                           // shr    $0x1f,%edx
        , 0x01, 0xd0                                 // add    %edx,%eax
        , 0xd1, 0xf8                                 //	sar    %eax
        , 0x89, 0x45, 0xec                           // mov    %eax,-0x14(%rbp)
        , 0xeb, 0xc5                                 //	jmp    27 <_Z3powv+0x27>
        , 0x8b, 0x45, 0xf4                           // mov    -0xc(%rbp),%eax
        , 0x5d                                       //	pop    %rbp
        , 0xc3                                       // retq
};


struct compiler {
public:
    compiler() {
        ptr = mmap(nullptr, size, PROT_WRITE, MAP_PRIVATE | MAP_ANONYMOUS, -1, 0);
        if (ptr == MAP_FAILED) {
            perror("Error: failed to map memory");
            throw std::exception();
        }
        std::memcpy(ptr, code, size);
    }

    int execute(int a) {
        if (mprotect(ptr, size, PROT_EXEC | PROT_READ | PROT_WRITE) == -1) {
            perror("Error: failed to create execute page");
            throw std::exception();
        }
        setArg(&a);
        auto exec = (int (*)()) (ptr);
        return exec();
    }

    int patchExecute(int a, int n) {
        patch(&n);
        return execute(a);
    }

    ~compiler() {
        if (munmap(ptr, size) == -1) {
            perror("Error: failed to delete memory");
        }
    }

private:
    void *ptr;

    const size_t size = sizeof(code);
    static constexpr size_t powIndex[] = {7, 21};
    static constexpr size_t argIndex[] = {14, 28};

    void setArg(const int *a) {
        std::memcpy(ptr + argIndex[0], a, 4);
        std::memcpy(ptr + argIndex[1], a, 4);
    }

    void patch(const int *n) {
        std::memcpy(ptr + powIndex[0], n, 4);
        std::memcpy(ptr + powIndex[1], n, 4);
    }
};

#endif //OS_JIT_FUNCTION_H

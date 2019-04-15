#include <utility>

#include <utility>

#include <string>
#include <zconf.h>
#include <wait.h>
#include <vector>
#include <sys/stat.h>

#ifndef OS_FIND_PARAMETERS_H
#define OS_FIND_PARAMETERS_H


enum compareCondition {
    NONE, LESS, EQUALS, MORE
};

struct arguments {
    int inodeNum;
    std::string name;
    size_t size;
    int hardLinkNum;

    arguments(int inodeNum, std::string &name, size_t size, int hardLinkNum) : inodeNum(inodeNum),
                                                                               name(std::move(name)), size(size),
                                                                               hardLinkNum(hardLinkNum) {}

    arguments() : inodeNum(-1), name(""), size(0), hardLinkNum(-1) {}

    arguments(const struct stat &st, std::string &name) : inodeNum(st.st_ino), name(name), size(st.st_size),
                                                          hardLinkNum(st.st_nlink) {}
};

class parameters {
private:
    arguments args;
    compareCondition compare;
    std::string execPath;

public:
    parameters(int inodeNum, std::string &name, size_t size, int hardLinkNum, compareCondition compare,
               std::string &execPath) : args(inodeNum, name, size, hardLinkNum),
                                        compare(compare), execPath(std::move(execPath)) {}

    parameters() : args(), compare(NONE), execPath("") {}

    void setInum(int num) {
        args.inodeNum = num;
    }

    void setName(std::string n) {
        args.name = std::move(n);
    }

    void setSize(size_t s, compareCondition cmp) {
        args.size = s;
        compare = cmp;
    }

    void setNLinks(int links) {
        args.hardLinkNum = links;
    }

    void setExec(std::string ex) {
        execPath = std::move(ex);
    }

    bool checkInode(const int otherInodeNum) {
        return (args.inodeNum == -1) || (args.inodeNum == otherInodeNum);
    }

    bool checkName(const std::string &otherName) {
        return (args.name.empty()) || (otherName.find(args.name) != std::string::npos);
    }

    bool checkSize(const size_t otherSize) {
        if (compare == NONE) {
            return true;
        } else if (compare == MORE) {
            return otherSize > args.size;
        } else if (compare == EQUALS) {
            return otherSize == args.size;
        } else {
            return otherSize < args.size;
        }
    }

    bool checkHardLink(const int otherHardLinkNum) {
        return (args.hardLinkNum == -1) || (args.hardLinkNum == otherHardLinkNum);
    }

    bool checkAllParameters(const arguments &other) {
        return checkInode(other.inodeNum) && checkName(other.name) && checkSize(other.size) &&
               checkHardLink(other.hardLinkNum);
    }

    bool checkAllParameters(const struct stat &other, std::string &name) {
        arguments arg(other, name);
        return checkAllParameters(arg);
    }


    void execute(const char *file) {
        if (!execPath.empty()) {
            char *argv[] = {const_cast<char *>(execPath.data()), const_cast<char *>(file), nullptr};
            executeCommand(argv, {});
        }
    }

    void executeCommand(char *argv[], char *envp[]) {
        switch (pid_t pid = fork()) {
            case -1:
                perror("Error: couldn't create child process");
                break;
            case 0:
                if (execve(argv[0], argv, envp) == -1) {
                    perror("Execution error");
                    exit(-1);
                }
                exit(0);
            default:
                int res;
                if (waitpid(pid, &res, 0) == -1) {
                    perror("Wait error");
                }
        }
    }
};

#endif //OS_FIND_PARAMETERS_H

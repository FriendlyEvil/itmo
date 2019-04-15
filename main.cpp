#include <iostream>
#include "helper.h"
#include "parameters.h"
#include <exception>
#include <queue>
#include <dirent.h>
#include <sys/stat.h>

int argsToInt(const char *arg) {
    int ans;
    if (sscanf(arg, "%d", &ans) != 1) {
        printError("Error: it isn't number: ", arg);
        throw std::exception();
    }
    return ans;
}

void help() {
    printf("  Commands:\n");
    printf("-    os_find [dir] :    find files in 'dir' directory\n");
    printf("- Flags:\n");
    printf("-    -inum [num]:       specifies a filter by the inode number\n");
    printf("-    -name [name]:      specifies a filter by the file name\n");
    printf("-    -size [-=+][size]: specifies a filter by the size\n");
    printf("-    -nlinks [num]:     specifies a filter by the hardlinks count\n");
    printf("-    -exec [path]:      calls the program on the path 'path' with the search results\n");
}

parameters parseArguments(int argc, const char *argv[]) {
    parameters par;
    for (int i = 1; i < argc - 1; ++i) {
        if (std::string(argv[i]) == "-inum") {
            if (i + 1 < argc) {
                par.setInum(argsToInt(argv[i + 1]));
            } else {
                printError("Error: flag '-inum' need 1 argument: ", "'-inum [num]'");
                throw std::exception();
            }
        } else if (std::string(argv[i]) == "-name") {
            if (i + 1 < argc) {
                par.setName(std::string(argv[i + 1]));
            } else {
                printError("Error: flag '-name' need 1 argument: ", "'-name [name]'");
                throw std::exception();
            }
        } else if (std::string(argv[i]) == "-size") {
            if (i + 1 < argc) {
                std::string temp = std::string(argv[i + 1]);
                int val = argsToInt(temp.substr(1).c_str());
                compareCondition cmp = NONE;
                switch (temp[0]) {
                    case '-':
                        cmp = LESS;
                        break;
                    case '+':
                        cmp = MORE;
                        break;
                    case '=':
                        cmp = EQUALS;
                        break;
                    default:
                        printError("Error: flag '-size' need 1 argument: ",
                                   "'-size -[size]' or '-size =[size]' or '-size +[size]'");
                        throw std::exception();
                }
                par.setSize(val, cmp);
            } else {
                printError("Error: flag '-size' need 1 argument: ",
                           "'-size -[size]' or '-size =[size]' or '-size +[size]'");
                throw std::exception();
            }
        } else if (std::string(argv[i]) == "-nlinks") {
            if (i + 1 < argc) {
                par.setNLinks(argsToInt(argv[i + 1]));
            } else {
                printError("Error: flag '-nlinks' need 1 argument: ", "'-nlinks [num]'");
                throw std::exception();
            }
        } else if (std::string(argv[i]) == "-exec") {
            if (i + 1 < argc) {
                par.setExec(std::string(argv[i + 1]));
            } else {
                printError("Error: flag '-exec' need 1 argument: ", "'-exec [path]'");
                throw std::exception();
            }
        } else {
            std::string er = "'" + std::string(argv[i]) + "'. Use flag '-h' to get help";
            printError("Incorrect input: ", er);
            throw std::exception();
        }
        i++;
    }
    return par;
}

void find(std::string path, parameters &par) {
    std::queue<std::string> queue;
    queue.push(path);
    while (!queue.empty()) {
        auto temp = queue.front();
        queue.pop();
        const char *name = temp.c_str();
        DIR *dir = opendir(name);
        if (dir == nullptr) {
            perror((std::string("Error: couldn't scan '" + std::string(name) + "'").c_str()));
        } else {
            dirent *dr;

            while (dr = readdir(dir)) {
                char *file = dr->d_name;
                if (file && std::string(file) != "." && std::string(file) != "..") {
                    struct stat st;
                    std::string fullPath = temp + "/" + file;
                    if (lstat(fullPath.c_str(), &st) != -1) {
                        if (S_ISREG(st.st_mode)) {
                            std::string fileName(file);
                            if (par.checkAllParameters(st, fileName)) {
                                printf("%s\n", fullPath.c_str());
                                par.execute(fullPath.c_str());
                            }
                        } else if (S_ISDIR(st.st_mode)) {
                            queue.push(fullPath);
                        }
                    } else {
                        perror("Error: getting file info");
                    }
                }
            }
            closedir(dir);
        }
    }
}

int main(int argc, const char *argv[]) {
    if (argc == 1) {
        printError("Error: we need 1 argument: ", "'os_find [dir]'");
        return 0;
    }
    try {
        if (argc == 2 && std::string(argv[1]) == "-h") {
            help();
            return 0;
        }
        parameters par = parseArguments(argc, argv);
        find(argv[argc - 1], par);
    } catch (std::exception &e) {
        return 1;
    }
}
#ifndef OS_NET_SERVER_H
#define OS_NET_SERVER_H


#include <netinet/in.h>
#include <string>

class server {
public:
    server(std::string &path);
    ~server();

    server(const server& other) = delete;
    server& operator=(const server& other) = delete;

    void start();
private:
    const std::string path;
    int sock;

    static const size_t BUFFER_SIZE = 1 << 16;
};


#endif //OS_NET_SERVER_H

#ifndef OS_NET_CLIENT_H
#define OS_NET_CLIENT_H


#include <netinet/in.h>
#include <string>

class client {
public:
    client(std::string &path);

    ~client();

    client(const client &other) = delete;

    client &operator=(const client &other) = delete;

    void connect_to_server();

    std::string echo(std::string &str);

private:
    std::string path;
    int sock, in, out;

    static const size_t BUFFER_SIZE = 1 << 16;
};


#endif //OS_NET_CLIENT_H

#ifndef CONSTS_H
#define CONSTS_H

#include <QObject>



struct consts {
    static const int max_size_trigram = 20000;
    static const int trigram_size = 3;
    static const int buf_size = 1<<18;
};

#endif // CONSTS_H

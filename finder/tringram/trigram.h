#ifndef TRIGRAM_H
#define TRIGRAM_H

#include <QObject>
#include <QTreeWidget>
#include "trigram_finder.h"
#include <thread>



class trigram: public QObject {
    Q_OBJECT

public:
    trigram(QObject* parent = nullptr);
    trigram(QList<QString> &dir_list, QHash<QString, QHash<QString, QSet<int64_t>>> *map);


public slots:
//    void find_duplicates();
    void calculate_trigrams();
    void update_progess();
    void update_result(QHash<QString, QHash<QString, QSet<int64_t>>> res);
    void end_find();

signals:
    void end_search(int);
    void update_status_bar(int);
    void update_status_bar_range(int);
    void search_result();


private:
    void find_items();
    bool find_trigrams(QSet<int64_t>* set, QString str);
    void create_tread(QList<std::pair<QString, QString>> const &list, int n);



    QList<QString> find_list;
    QHash<QString, QHash<QString, QSet<int64_t>>> *map;
    int thread_count;
    const int thread_max = std::thread::hardware_concurrency();
//    const int thread_max = 4;
};
#endif // TRIGRAM_H

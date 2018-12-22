#ifndef TRIGRAM_FINDER_H
#define TRIGRAM_FINDER_H

#include <QObject>
#include <QTreeWidget>



class trigram_finder: public QObject {
    Q_OBJECT

public:
    trigram_finder(QThread* parent_thread, QList<std::pair<QString, QString>> const &list, QObject* parent = nullptr);
    static bool find_trigrams(QSet<int64_t>* set, QString const &str);
    static void f();

public slots:
    void process();

signals:
    void update_progress();
    void update_result(QHash<QString, QHash<QString, QSet<int64_t>>> *map);

private:
   void calculate_trigram(std::pair<QString, QString> const &file);
   bool check_interruption_request();

    QThread* parent_thread;
    QList<std::pair<QString, QString>> find_list;
    QHash<QString, QHash<QString, QSet<int64_t>>> map;
};

#endif // TRIGRAM_FINDER_H

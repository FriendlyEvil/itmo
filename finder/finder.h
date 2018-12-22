#ifndef FINDER_H
#define FINDER_H

#include <QObject>



class finder : public QObject {
    Q_OBJECT

public:
    finder(QHash<QString, QHash<QString, QSet<int64_t>>> *trigrams, QString const &find_string);

public slots:
    void find();

signals:
    void update_result(QList<QString> list);
    void update_status_bar(int);
    void update_status_bar_range(int);
    void update_message(QString);
    void end_search(int);

private:
    bool check_file(QString const &file);
    bool check_interruption_request();

    QHash<QString, QHash<QString, QSet<int64_t>>> *trigrams;
    QString find_string;
};

#endif // FINDER_H

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

private:
    bool check_file(QString const &file);

    QHash<QString, QHash<QString, QSet<int64_t>>> *trigrams;
    QString find_string;
};

#endif // FINDER_H

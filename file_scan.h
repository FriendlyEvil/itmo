#ifndef FILE_SCAN_H
#define FILE_SCAN_H

#include <QObject>
#include <ui_mainwindow.h>



class file_scan : public QObject {
    Q_OBJECT

public:
    file_scan(QList<QString> &dir_list, QObject* parent = 0);

//    friend class MainWindow;
public slots:
    void find_duplicates();

signals:
    void end_search(int);
    void update_status_bar(int);
    void update_status_bar_range(int);
    void search_result(QList<QTreeWidgetItem*>);
    void update_status(QString const&);

private:
    QList<QTreeWidgetItem*> find();
    QString hash_sha256(QString const& str);
    bool check_interruption_request();

    QList<QString> find_list;
};

#endif // FILE_SCAN_H

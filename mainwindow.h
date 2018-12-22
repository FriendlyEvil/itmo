#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>
#include <memory>
#include <map>
#include <set>
#include <vector>
#include <QFileInfo>
#include <QTreeWidget>


namespace Ui {
class MainWindow;
}

class main_window : public QMainWindow
{
    Q_OBJECT

public:
    explicit main_window(QWidget *parent = 0);
    ~main_window();

private slots:
    void scan_directory_list();
    void open();
    void add_directory();
    void show_results(QList<QString> list);
    void update_result();
    void check_delete_button();
    void delete_directory_from_list();
    void find_string();
    void open_file(QTreeWidgetItem* item);

private:
    void change_path();
    bool check_path(QFileInfo path);
    void remove_subfolder(QFileInfo path);

private:
    std::unique_ptr<Ui::MainWindow> ui;
    QHash<QString, QHash<QString, QSet<int64_t>>> *map;

    const QColor good_scan_background = QColor(0, 255, 0);
    const QString not_found = "Not found element";
};

#endif // MAINWINDOW_H

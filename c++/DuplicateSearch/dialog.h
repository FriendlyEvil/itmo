#ifndef DIALOG_H
#define DIALOG_H

#include <QDialog>
#include <QThread>

namespace Ui {
class Dialog;
}

class dialog : public QDialog
{
    Q_OBJECT

public:
    explicit dialog(QThread* searchThread, QWidget *parent = 0);
    ~dialog();

public slots:
    void stop();
    void update_status_bar(int value);
    void update_status_bar_range(int value);
    void update_message(QString const& message);
private:
    std::unique_ptr<Ui::Dialog> ui;
    std::unique_ptr<QThread> thread;
};

#endif // DIALOG_H

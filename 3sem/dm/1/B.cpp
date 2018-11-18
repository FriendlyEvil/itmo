//#include <iostream>
//#include <fstream>
//#include <vector>
//#include <deque>
//#include <algorithm>
//#include <string>
//
//const std::string file = "chvatal";
//const std::string file_in = file + ".in";
//const std::string file_out = file + ".out";
//
//std::fstream in(file_in);
//std::ofstream out(file_out);
//
//int n;
//
//std::vector<std::vector<bool>> verb;
//
//void fill() {
//    verb = std::vector<std::vector<bool>>(n, std::vector<bool>(n, false));
//}
//
//void read() {
////    std::rand
//
//    in >> n;
//    fill();
//
//    std::string str;
//    for (int i = 1; i < n; i++) {
//        in >> str;
//        for (int j = 0; j < i; j++) {
//            verb[i][j] = str[j] - '0';
//            verb[j][i] = verb[i][j];
//        }
//    }
//}
//
//int main() {
//    read();
//    std::deque<int> queue;
//
//    for (int i = 0; i < n; i++) {
//        queue.push_back(i);
//    }
//
//    start:
//
//    for (int i = 0; i < 14; i++)
//        std::random_shuffle(queue.begin(), queue.end());
//
//    for (int i = 0; i < n * (n - 1); i++) {
//        int k = 1;
//        if (!verb[queue[0]][queue[1]]) {
//            k = 2;
//            while ((!verb[queue[0]][queue[k]] || !verb[queue[1]][queue[k + 1]])) {
//                if (k + 1 == n)
//                    goto start;
//                k++;
//            }
//        }
//        std::reverse(queue.begin() + 1, queue.begin() + k + 1);
//        queue.push_back(queue.front());
//        queue.pop_front();
//    }
//
//    for (int i : queue)
//        out << i + 1 << " ";
//
//}

// Potential_field.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

//#include "pch.h"
#include <iostream>
#include <math.h>
#include <fstream>
#include <limits>
#include <string>
#include <vector>

using namespace std;

struct Cell {
    double dx, dy, theta;
    //Cell() { dx = 0; dy = 0; theta = 0; }//when an array is created the values of velocity in each cell equals zero
};


double vector_cell_x(double, double);
double vector_cell_y(double, double);
double vector_cell_theta(double, double, double, double);
void add_field(Cell**, Cell**, int);
void read(vector<double>& circles);


int main()
{
    vector<double>circles;
    read(circles);
    double dT, Fmax;  //what we start with
    dT = circles.back();
    circles.pop_back();
    Fmax = circles.back();
    circles.pop_back();

    int division = 1000/dT;									/*division is the number of cells in the grib*/
    double Fx, Fy;
    double X, Y, R;  //these are coordinates for obstacles
    double dx, dy, Theta;   //velocity
    double Vmax = 4*dT*Fmax;     //sup of velocity
    int x, y;//coordinates
    //obstacles obs = {X, Y, R};

    Cell** array_division;
    array_division = new Cell *[division];		//Here an array of field
    for (int i = 0; i < division; i++)				// is being
    {
        array_division[i] = new Cell[division];		//  created
    }
    Cell** array_obstacles = new Cell *[division];	//Here anoter array of field
    for (int i = 0; i < division; i++)				// is being
        array_obstacles[i] = new Cell[division];	//	created

    //input the goal's field
    for (int i = 0; i < division; i++)					//the field created by the goal point
        for (int j = 0; j < division; j++)
        {
            double d = sqrt((i - (division - 1))*(i - (division - 1)) + (j - (division - 1))*(j - (division - 1)));
            double angle = atan(((division - 1) - j) / ((division - 1) - i));
            double r = 1.0;
            double s = 2*Vmax*dT;
            if ((r <= d) && (d <= s))// r is the radius of the area where a robot will stay
            {
                array_division[i][j].dx = division * (d - r)*cos(angle);
                array_division[i][j].dy = division * (d - r)*sin(angle);
                array_division[i][j].theta = angle;
            }
            else
            if (d < r)
            {
                array_division[i][j].dx = 0;
                array_division[i][j].dy = 0;
                array_division[i][j].theta = atan(1);
            }
            else
            {
                array_division[i][j].dx = division * s *cos(angle);
                array_division[i][j].dy = division * s *sin(angle);
                array_division[i][j].theta = angle;
            }
        }
    //the field of obstacles
    while (circles.empty())
    {//then input obstacles( X, Y, R)
        X = circles.back();
        circles.pop_back();
        Y = circles.back();
        circles.pop_back();
        R = circles.back();
        circles.pop_back();

        X *= division;
        Y *= division;
        R *= division;
        for(int i = 0; i < division; i++)
            for (int j = 0; j <= division; j++) {
                double d = sqrt((i - X)*(i - X) + (j - Y)*(j - Y));
                double angle = atan((Y - j) / (X - i));
                double p = 5 * R;

                if (d < R)//if we are in a circle then the velocity is equal infinity
                {
                    array_obstacles[i][j].dx = -(cos(angle))*numeric_limits<double>::infinity();
                    array_obstacles[i][j].dy = -(sin(angle))*numeric_limits<double>::infinity();
                }
                else
                if ((R <= d) && (d <= p + R))
                {
                    int l = division * 2;
                    array_obstacles[i][j].dx = -l * (p + R - d)*(cos(angle));
                    array_obstacles[i][j].dy = -l * (p + R - d)*(sin(angle));
                }
                else {
                    array_obstacles[i][j].dx = 0;
                    array_obstacles[i][j].dy = 0;
                }
                add_field(array_division,array_obstacles,division);
            }
    }

    ofstream File_("out.txt");

    double dv;
    double v0 = 0.0;
    dx = (array_division[0][0].dx);
    dy = (array_division[0][0].dy);
    dv = sqrt(dx*dx + dy*dy);
    Theta = (array_division[0][0].theta);
    if (abs(dv) >= Fmax * dT)
    {
        Fx = Fmax * cos(Theta);
        Fy = Fmax * sin(Theta);
    }
    else
    {
        Fx = dx / dT;
        Fy = dy / dT;
    }
    x = dx * dT;
    y = dy * dT;
    v0 = dv;

    File_ << Fx << "," << Fy << "\n";
    /////////////////////////////////////
    while ((dx) || (dy))
    {
        //Theta = find_vector(x, y, array_division);
        Theta = atan(array_division[x][y].dy / array_division[x][y].dy);
        if (abs(dv - v0) >= Fmax * dT)
        {
            Fx = Fmax * cos(Theta);
            Fy = Fmax * sin(Theta);
        }
        else
        {
            dx = (dv - v0) * cos(Theta);
            dy = (dv - v0) * sin(Theta);
            Fx = dx / dT;
            Fy = dy / dT;
        }
        x = dx * dT;
        y = dy * dT;
        v0 = dv;
        File_ << Fx << ',' << Fy << "\n";
    }

    File_.close();
    ///////////////////////////////////////////////////////
    for (int i = 0; i < division; i++)
    {
        delete[] array_division[i];
        delete[] array_obstacles[i];
    }
    delete[] array_division;
    delete[] array_obstacles;

    return 0;


}

double vector_cell_theta(double vx1, double vy1, double vx2, double vy2) {
    double vx3 = vx1 + vx2;
    double vy3 = vy1 + vy2;
    double theta3 = (asin(vy3 / sqrt((vx1 + vx2)*(vx1 + vx2) + (vy1 + vy2)*(vy1 + vy2))) + acos(vx3 / sqrt((vx1 + vx2)*(vx1 + vx2) + (vy1 + vy2)*(vy1 + vy2))) + asin(1 / sqrt((vx1 + vx2)*(vx1 + vx2) / (vy1 + vy2)*(vy1 + vy2) + 1)) + asin(1 / sqrt(1 + (vx1 + vx2)*(vx1 + vx2) / (vy1 + vy2)*(vy1 + vy2)))) / 4;
    return theta3;
}

double vector_cell_y(/*double vx1, */double vy1,/* double vx2,*/ double vy2) {
    //double vx3 = vx1 + vx2;
    double vy3 = vy1 + vy2;
    //double theta3 = (asin(vy3 / sqrt((vx1 + vx2)*(vx1 + vx2) + (vy1 + vy2)*(vy1 + vy2))) + acos(vx3 / sqrt((vx1 + vx2)*(vx1 + vx2) + (vy1 + vy2)*(vy1 + vy2))) + asin(1 / sqrt((vx1 + vx2)*(vx1 + vx2) / (vy1 + vy2)*(vy1 + vy2) + 1)) + asin(1 / sqrt(1 + (vx1 + vx2)*(vx1 + vx2) / (vy1 + vy2)*(vy1 + vy2)))) / 4;
    return vy3;
}

double vector_cell_x(double vx1/*, double vy1*/, double vx2/*, double vy2*/) {
    double vx3 = vx1 + vx2;
    //double vy3 = vy1 + vy2;
    //double theta3 = (asin(vy3 / sqrt((vx1 + vx2)*(vx1 + vx2) + (vy1 + vy2)*(vy1 + vy2))) + acos(vx3 / sqrt((vx1 + vx2)*(vx1 + vx2) + (vy1 + vy2)*(vy1 + vy2))) + asin(1 / sqrt((vx1 + vx2)*(vx1 + vx2) / (vy1 + vy2)*(vy1 + vy2) + 1)) + asin(1 / sqrt(1 + (vx1 + vx2)*(vx1 + vx2) / (vy1 + vy2)*(vy1 + vy2)))) / 4;
    return vx3;
}

void add_field(Cell** main, Cell** second,int division)
{
    for(int i = 0; i < division; i++)
        for (int j = 0; j < division; j++)
        {
            main[i][j].dx = vector_cell_x(main[i][j].dx,second[i][j].dx);
            main[i][j].dy = vector_cell_y(main[i][j].dy, second[i][j].dy);
            main[i][j].theta = vector_cell_theta(main[i][j].dx, main[i][j].dy, second[i][j].dx, second[i][j].dy);
        }
};

double find_arg(std::fstream &in) {
    std::string str;
    in >> str; //название, например "dt":
    double fl;
    in >> fl; //само значение
    in >> str; //запятая, которая в конце или "},"
    return fl;
}

void read(vector<double>&circles) {
    //чисто для удобства, чтобы понять что падает не моя часть кода
    std::cout << "start input\n";


    const std::string file_in = "file.in.txt";
    std::fstream in(file_in); //открываем файл (обрабатывать ошибки для слабых)
    std::string str;

    //скипаем ненужное (в каждой строке то, что скипнули)
    in >> str; // {
    in >> str; // "name":
    in >> str; // "bumps",

    //думаю тут все ясно
    double first = find_arg(in);
    double second = find_arg(in);

    //на всякий случай вывдем для своей проверки
    std::cout << first << " " << second << "\n\n";

    //скипаем ненужное (в каждой строке то, что скипнули)
    in >> str; // "circles":
    in >> str; // [
    while (true) {
        in >> str; // считали {, если дальше есть данные или ], если их нет
        if (str == "]")
            break; // если нет, то заканчиваем
        double x = find_arg(in);
        double y = find_arg(in);
        double r = find_arg(in);
        circles.push_back(r);
        circles.push_back(y);
        circles.push_back(x);

        //вот тут стоит вызвать функцю для этих параметров или выше объявить vector<double> и вызывать метод push_back()

        //на всякий случай вывдем для своей проверки
        std::cout << x << " " << y << " " << r << "\n";
    }


    circles.push_back(second);
    circles.push_back(first);
    //чисто для удобства, чтобы понять что падает не моя часть кода
    std::cout << "end input\n";
}

//double find_vector(double x, double y, Cell** main)
//{
//	double Theta;
//	double a, b, c;
//	double k1, k2, k3, k4, k5, k6, k7, k8;
//	k1 = abs((int)x - x);
//	k2 = 0.5 - abs((int)y - y);
//	k3 = abs((int)x - x);
//	k4 = 0.5 - abs((int)x - x);
//	k5 = abs((int)x - x);
//	k6 = abs((int)x - x);
//	k7 = abs((int)x - x);
//	k8 = abs((int)x - x);
//	a = main[(int)x][(int)y].dx + main[(int)x-1][(int)y-1].dx + main[(int)x][(int)y-1].dx + main[(int)x+1][(int)y-1].dx + main[(int)x-1][(int)y].dx + main[(int)x+1][(int)y].dx + main[(int)x-1][(int)y+1].dx + main[(int)x][(int)y+1].dx + main[(int)x+1][(int)y + 1].dx;
//	b = main[(int)x][(int)y].dy + main[(int)x-1][(int)y-1].dy + main[(int)x][(int)y-1].dy + main[(int)x+1][(int)y-1].dy + main[(int)x-1][(int)y].dy + main[(int)x+1][(int)y].dy + main[(int)x-1][(int)y+1].dy + main[(int)x][(int)y+1].dy + main[(int)x+1][(int)y + 1].dy;
//	c = sqrt(a*a + b * b);
//	Theta = 0.5 * (asin(b / c) + acos(a / c));
//	return Theta;
//}

// Run program: Ctrl + F5 or Debug > Start Without Debugging menu
// Debug program: F5 or Debug > Start Debugging menu

// Tips for Getting Started:
//   1. Use the Solution Explorer window to add/manage files
//   2. Use the Team Explorer window to connect to source control
//   3. Use the Output window to see build output and other messages
//   4. Use the Error List window to view errors
//   5. Go to Project > Add New Item to create new code files, or Project > Add Existing Item to add existing code files to the project
//   6. In the future, to open this project again, go to File > Open > Project and select the .sln file
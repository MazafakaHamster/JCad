package com.rebel.cad.shape;

import com.rebel.cad.collections.ShapeGroup;

import java.util.Random;

/**
 * Created by Slava on 23.12.2015.
 */
public class Fractal extends ShapeGroup {
    // Задаем диапазон значений для точек
    private final static float MinX = -6;
    private final static float MaxX = 6;
    private final static float MinY = 0.1f;
    private final static float MaxY = 10;
    // Количество точек для отрисовки
    private final static int PointNumber = 200000;
    // Массив коэффциентов вероятностей
    private float[] _probability =
            {
                    0.01f,
                    0.06f,
                    0.08f,
                    0.85f
            };
    // Матрица коэффициентов
    private float[][] _funcCoef =
            {
                    //a      b       c      d      e  f
                    {0, 0, 0, 0.16f, 0, 0f}, // 1 функция
                    {-0.15f, 0.28f, 0.26f, 0.24f, 0, 0.44f},// 2 функция
                    {0.2f, -0.26f, 0.23f, 0.22f, 0, 1.6f}, // 3 функция
                    {0.85f, 0.04f, -0.04f, 0.85f, 0, 1.6f}  // 4 функция
            };

    // коэффициент масштабируемости высоты и ширины
    // изображения фрактала для высоты и ширины нашей формы
    private int _width;
    private int _height;

    public void build() {
        // вычисляем коэффициент
        _width = (int) (FernPictureBox.Width / (MaxX - MinX));
        _height = (int) (FernPictureBox.Height / (MaxY - MinY));
        // создаем Bitmap для папоротника
        _fern = new Bitmap(FernPictureBox.Width, FernPictureBox.Height);
        // cоздаем новый объект Graphics из указанного Bitmap
        _graph = Graphics.FromImage(_fern);
        // устанавливаем фон
        _graph.Clear(Color.Black);

        DrawFern();
    }

    private void DrawFern() {
        Random rnd = new Random();
        // будем начинать рисовать с точки (0, 0)
        float xtemp = 0, ytemp = 0;
        // переменная хранения номера функции для вычисления следующей точки
        int numF = 0;
        Random random = new Random();

        for (int i = 1; i <= PointNumber; i++) {
            // рандомное число от 0 до 1
            float num = random.nextFloat();
            // проверяем какой функцией воспользуемся для вычисления следующей точки
            for (int j = 0; j <= 3; j++) {
                // если рандомное число оказалось меньше или равно
                // заданного коэффициента вероятности,
                // задаем номер функции
                num = num - _probability[j];
                if (num <= 0) {
                    numF = j;
                    break;
                }
            }

            // вычисляем координаты
            float x = _funcCoef[numF][0] * xtemp + _funcCoef[numF][1] * ytemp + _funcCoef[numF][4];
            float y = _funcCoef[numF][2] * xtemp + _funcCoef[numF][3] * ytemp + _funcCoef[numF][5];

            // сохраняем значения для следующей итерации
            xtemp = x;
            ytemp = y;
            // вычисляем значение пикселя
            x = (int) (xtemp * _width + FernPictureBox.Width / 2);
            y = (int) (ytemp * _height);
            // устанавливаем пиксель в Bitmap
            _fern.SetPixel((int) x, (int) y, Color.LawnGreen);
        }
        // Отображаем результат
        FernPictureBox.BackgroundImage = _fern;
    }
}

import java.util.Scanner;
import java.util.Arrays;
import java.util.Random;

public class TickTackToe {

    private static char[][] map; // поле
    private static int SIZE = 3; // размер поля

    private static int[] winComb = new int[8]; // для рейтинга каждой выигрышной комбинации

    private static final char DOT_EMPTY = '*'; //пустая клетка
    private static final char DOT_X = 'X'; // крестик
    private static final char DOT_O = 'O'; // нолик

    private static Scanner scanner = new Scanner(System.in);
    private static Random random = new Random();

    public static void main(String[] args) {
        int computer;
        initMap();
        printMap();
        do {
            System.out.println("Введите 0 или 1: 0 - игра с тупым компьютером, 1 - игра с умным компьютером");
            computer = scanner.nextInt();
        } while (computer != 0 && computer != 1);

        while(true) {
            humanTurn();
            if (isEndGame(DOT_X)) {
                break;
            }

            if (computer == 1) {
                smartComputerTurn();
            } else {
                stupidComputerTurn();
            }
            if (isEndGame(DOT_O)) {
                break;
            }
        }
    }

    private static void initMap() { // создание поля
        map = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                map[i][j] = DOT_EMPTY;
            }
        }
    }

    private static void printMap() { // вывод поля
        for (int i = 0; i <= SIZE; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i = 0; i < SIZE; i++) {
            System.out.print((i+1) + " ");
            for (int j = 0; j < SIZE; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static void humanTurn() { //ход пользователя
        int x, y;
        do {
            System.out.println("Введите координату через пробел (строка, столбец): ");
            y = scanner.nextInt() - 1;
            x = scanner.nextInt() - 1;
        } while (isCellValid(x, y) == false);
        map[y][x] = DOT_X;
        winningCombination(DOT_X, y, x); // внесение данных в рейтинг комбинаций
    }

    private static void stupidComputerTurn() {
        int x, y;
        do {
            y = random.nextInt(SIZE);
            x = random.nextInt(SIZE);
        } while (isCellValid(x, y) == false);
        System.out.println("Компьютер ходит: " + (y + 1) + (x + 1));
        map[y][x] = DOT_O;
        winningCombination(DOT_O, y, x); // внесение данных в рейтинг комбинаций
    }

    private static void smartComputerTurn() { // умный компьютер
        int max = 0, min = 0; // для вычисления приоритетных комбинаций
        int mx = 0, mn = 0; // для запоминания номеров приоритетных комбинаций
        int prioritetComb = 0; // номер комбинации, в которую лучше поставить нолик
        int y = 0, x = 0;
        for (int i = 0; i < 8; i++) {
            if (winComb[i] != 4 && winComb[i] >= max) { // если 4 - эта выигрышная комбинация заполнена
                max = winComb[i];
                mx = i; // запомнить номер комбинации
            }
            if (winComb[i] != -1 && winComb[i] < min) { // если -1 - эта выигрышная комбинация заполнена
                min = winComb[i];
                mn = i; // запомнить номер комбинации
            }
        }
            if (max == 1) {
                prioritetComb = mx;
            }
            if (min == -2) {
                prioritetComb = mn;
            }
            if (max == 0) {
                prioritetComb = mx;
            }
            if (max == 3) {
                prioritetComb = mx;
            }
            if (min == -4) {
                prioritetComb = mn;
            }
            if (max == 6) {
                prioritetComb = mx;
            }

        switch (prioritetComb) {
            case 0:
                y = 0;
                if (map[0][0] == DOT_EMPTY) {
                    x = 0;
                } else if (map[0][1] == DOT_EMPTY) {
                    x = 1;
                } else if (map[0][2] == DOT_EMPTY) {
                    x = 2;
                }
                break;
            case 1:
                y = 1;
                if (map[1][0] == DOT_EMPTY) {
                    x = 0;
                } else if (map[1][1] == DOT_EMPTY) {
                    x = 1;
                } else if (map[1][2] == DOT_EMPTY) {
                    x = 2;
                }
                break;
            case 2:
                y = 2;
                if (map[2][0] == DOT_EMPTY) {
                    x = 0;
                } else if (map[2][1] == DOT_EMPTY) {
                    x = 1;
                } else if (map[2][2] == DOT_EMPTY) {
                    x = 2;
                }
                break;
            case 3:
                x = 0;
                if (map[0][0] == DOT_EMPTY) {
                    y = 0;
                } else if (map[1][0] == DOT_EMPTY) {
                    y = 1;
                } else if (map[2][0] == DOT_EMPTY) {
                    y = 2;
                }
                break;
            case 4:
                x = 1;
                if (map[0][1] == DOT_EMPTY) {
                    y = 0;
                } else if (map[1][1] == DOT_EMPTY) {
                    y = 1;
                } else if (map[2][1] == DOT_EMPTY) {
                    y = 2;
                }
                break;
            case 5:
                x = 2;
                if (map[0][2] == DOT_EMPTY) {
                    y = 0;
                } else if (map[1][2] == DOT_EMPTY) {
                    y = 1;
                } else if (map[2][2] == DOT_EMPTY) {
                    y = 2;
                }
                break;
            case 6:
                if (map[0][0] == DOT_EMPTY) {
                    y = 0;
                    x = 0;
                } else if (map[1][1] == DOT_EMPTY) {
                    y = 1;
                    x = 1;
                } else if (map[2][2] == DOT_EMPTY) {
                    y = 2;
                    x = 2;
                }
                break;
            case 7:
                if (map[0][2] == DOT_EMPTY) {
                    y = 0;
                    x = 2;
                } else if (map[1][1] == DOT_EMPTY) {
                    y = 1;
                    x = 1;
                } else if (map[2][0] == DOT_EMPTY) {
                    y = 2;
                    x = 0;
                }
                break;
        }
        System.out.println("Компьютер ходит: " + (y + 1) + (x + 1));
        map[y][x] = DOT_O;
        winningCombination(DOT_O, y, x); // внесение данных в рейтинг комбинаций
    }

    private static void winningCombination (char playerSymbol, int y, int x) {
        int point = (playerSymbol == DOT_X)? -2 : 3; // очки за постановку символа в ячейку,
        if (y == 0) { // которые добавляются в ретинг комбинации
            winComb[0] += point;
            if (x == 0) {
                winComb[3] += point;
                winComb[6] += point;
            }
            if (x == 1) {
                winComb[4] += point;
            }
            if (x == 2) {
                winComb[5] += point;
                winComb[7] += point;
            }
        }
        if (y == 1) {
            winComb[1] += point;
            if (x == 0) {
                winComb[3] += point;
            }
            if (x == 1) {
                winComb[4] += point;
                winComb[6] += point;
                winComb[7] += point;
            }
            if (x == 2) {
                winComb[5] += point;
            }
        }
        if (y == 2) {
            winComb[2] += point;
            if (x == 0) {
                winComb[3] += point;
            }
            if (x == 1) {
                winComb[4] += point;
            }
            if (x == 2) {
                winComb[5] += point;
                winComb[6] += point;
            }
        }
    }

    private static boolean isCellValid(int x, int y) { // проверка ввода в ячейку
        boolean result = true;

        if (x < 0 || x >= SIZE || y < 0 || y >= SIZE) { //проверка номера ячейки
            System.out.println("Нет такой ячейки!");
            result = false;
        }
        if (result) {
            if (map[y][x] != DOT_EMPTY) { // проверка на заполненность
                System.out.println("Ячейка уже заполнена!");
                result = false;
            }
        }
        return result;
    }

    private static boolean isEndGame(char playerSymbol) {
        boolean result = false;
        printMap();

        if (checkWin(playerSymbol)) {
            System.out.println("Победили " + playerSymbol);
            result = true;
        }

        if (isMapFull()) {
            System.out.println("Ничья");
            result = true;
        }
        return result;
    }

    private static boolean isMapFull() {
        boolean result = true;

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (map[i][j] == DOT_EMPTY) {
                    result = false;
                }
            }
        }
        return result;
    }

    private static boolean checkWin(char playerSymbol) {
        boolean result = false;
        for (int i = 0; i < 8; i++) {
            if (winComb[i] == -6) { // поиск комбинации, где все крестики
                result = true;
            } else if (winComb[i] == 9) { // поиск комбинации, где все нолики
                result = true;
            }
        }
        return result;
    }
}
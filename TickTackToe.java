import java.util.Scanner;
import java.util.Arrays;
import java.util.Random;

public class TickTackToe {

    private static char[][] map; // поле
    private static int SIZE = 4; // размер поля
    private static int prioritet_i, prioritet_j;
    /*private static boolean check;*/

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
        System.out.print("  ");
        for (int i = 0; i < SIZE; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i = 0; i < SIZE; i++) {
            System.out.print(i+ " ");
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
            y = scanner.nextInt();
            x = scanner.nextInt();
        } while (isCellValid(x, y) == false);
        map[y][x] = DOT_X;
    }

    private static void stupidComputerTurn() {
        int x, y;
        do {
            y = random.nextInt(SIZE);
            x = random.nextInt(SIZE);
        } while (isCellValid(x, y) == false);
        System.out.println("Компьютер ходит: " + y + " " + x);
        map[y][x] = DOT_O;
    }

    private static void smartComputerTurn() {
        int scored = 0;
        int maxScored = -1;
        if (!proverka(DOT_O)) {
            if (!proverka(DOT_X)) {
                for (int i = 0; i < SIZE; i++) {
                    for (int j = 0; j < SIZE; j++) {
                        scored = 0;
                        if (map[i][j] == DOT_EMPTY) {
                            if ((i - 1 >= 0) && (j - 1 >= 0) && map[i][j] == DOT_O) { // лево-верх
                                scored++;
                            }
                            if ((i - 1 >= 0) && map[i][j] == DOT_O) { // верх
                                scored++;
                            }
                            if ((i - 1 >= 0) && (j + 1 < SIZE) && map[i][j] == DOT_O) { // право-верх
                                scored++;
                            }
                            if ((j + 1 < SIZE) && map[i][j] == DOT_O) { // право
                                scored++;
                            }
                            if ((i + 1 < SIZE) && (j + 1 < SIZE) && map[i][j] == DOT_O) { // право-низ
                                scored++;
                            }
                            if ((i + 1 < SIZE) && map[i][j] == DOT_O) { // низ
                                scored++;
                            }
                            if ((i + 1 < SIZE) && (j - 1 >= 0) && map[i][j] == DOT_O) { // лево-низ
                                scored++;
                            }
                            if ((j - 1 >= 0) && map[i][j] == DOT_O) { // лево
                                scored++;
                            }

                            if (scored >= maxScored) {
                                maxScored = scored;
                                prioritet_i = i;
                                prioritet_j = j;
                            }
                        }
                    }
                }
            }
        }
        System.out.println("Компьютер ходит: " + prioritet_i + " " + prioritet_j);
        map[prioritet_i][prioritet_j] = DOT_O;
    }

    private static boolean proverka(char symbol) {
        boolean result = false, check = false;
        int scored_X_1;
        int scored_X_2;

        for (int i = 0; i < SIZE; i++) { // для строк
            scored_X_1 = 0;
            for (int j = 0; j < SIZE; j++) {
                if (map[i][j] == symbol) scored_X_1++;
            }
            if (scored_X_1 == SIZE - 1) {
                for (int k = 0; k < SIZE; k++) {
                    if (map[i][k] == DOT_EMPTY) {
                        prioritet_i = i;
                        prioritet_j = k;
                        check = true;
                        break;
                    }
                }
            }
        }

        for (int j = 0; j < SIZE; j++) { // для столбцов
            scored_X_1 = 0;
            for (int i = 0; i < SIZE; i++) {
                if (map[i][j] == symbol) scored_X_1++;
            }
            if (scored_X_1 == SIZE - 1) {
                for (int k = 0; k < SIZE; k++) {
                    if (map[k][j] == DOT_EMPTY) {
                        prioritet_i = k;
                        prioritet_j = j;
                        check = true;
                        break;
                    }
                }
            }
        }

        scored_X_1 = 0;
        scored_X_2 = 0;
        for (int i = 0; i < SIZE; i++) { // для диагоналей
            if (map[i][i] == symbol) scored_X_1++; // главная диагональ
            if (map[i][SIZE - i - 1] == symbol) scored_X_2++; // побочная диагональ
        }
        if (scored_X_1 == SIZE - 1) {
            for (int i = 0; i < SIZE; i++) {
                if (map[i][i] == DOT_EMPTY) {
                    prioritet_i = i;
                    prioritet_j = i;
                    check = true;
                    break;
                }
            }
        }
        if (scored_X_2 == SIZE - 1) {
            for (int i = 0; i < SIZE; i++) {
                if (map[i][SIZE - i - 1] == DOT_EMPTY) {
                    prioritet_i = i;
                    prioritet_j = SIZE - i - 1;
                    check = true;
                    break;
                }
            }
        }
        return check;
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
                    break;
                }
            }
            if (!result) {
                break;
            }
        }
        return result;
    }

    private static boolean checkWin(char playerSymbol) {
        boolean result = false;
        int scored;
        int scored_2;
        for (int i = 0; i < SIZE; i++) { // для строк
            scored = 0;
            for (int j = 0; j < SIZE; j++) {
                if (map[i][j] == playerSymbol) scored++;
            }
            if (scored == SIZE) {
                result = true;
                break;// конец игры
            }
        }
        for (int j = 0; j < SIZE; j++) { // стобцы
            scored = 0;
            for (int i = 0; i < SIZE; i++) {
                if (map[i][j] == playerSymbol) scored++;
            }
            if (scored == SIZE) {
                result = true; // конец игры
                break;
            }
        }

        scored = 0;
        scored_2 = 0;
        for (int i = 0; i < SIZE; i++) {
            if (map[i][i] == playerSymbol) scored++; // главная диагональ
            if (map[i][SIZE - i - 1] == playerSymbol) scored_2++; // побочная диагональ
        }
        if (scored == SIZE || scored_2 == SIZE) {
            result = true; // конец игры
        }

        return result;
    }
}
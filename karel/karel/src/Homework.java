import stanford.karel.*;
import java.awt.*;
public class Homework extends SuperKarel {
    void putAndcolor(){putBeeper(); paintCorner(Color.black); numberOfBeepers++;}
    void moveSteps(){move(); steps++;}
    void TrueToFalse(){down = !down; up = !up; left = !left; right = !right;}
    void reset(){ rows = 1 ; columns = 1 ; steps = 0 ; numberOfBeepers = 0; down = true; up = false; left = true; right = false;}
    int rows , columns , steps , numberOfBeepers , x = 0;  boolean down , up , left , right ;
    public void run() {
        x++;
        reset(); findWidthHeight();
        if(checkOf4rooms()) divide4rooms();
        else if(checkOf3rooms()) divide3rooms();
        else if(checkOf2rooms()) divide2rooms();
        else System.out.println("Message : You can't divide it more than 1 room .");
        System.out.println("\n    Run#" + x + "\n- Steps  ->  " + steps + "\n- Number of beepers -> " + numberOfBeepers + "\n- Rows -> " + rows + " , Columns -> " + columns);
    }
    // to find the Width and Height
    void findWidthHeight(){
        boolean isColumn = true , isRow = true;
        if(frontIsBlocked()) {isColumn = false; turnLeft();}
        while( (isRow || isColumn)  && frontIsClear()){
            moveSteps();
            if(isColumn) columns++; else rows++;
            if( frontIsBlocked() && isColumn ) {turnLeft(); isColumn = false;}
            if( frontIsBlocked() && isRow ) {isRow = false; turnLeft();}
        }
    }
    // for ZigZag function's
    void down(){ putAndcolor(); turnLeft(); moveSteps(); putAndcolor(); turnRight(); moveSteps();putAndcolor(); up = true ; down = false;}
    void up() { turnRight();moveSteps();putAndcolor();turnLeft(); up = false ; down = true;}
    void left(){ putAndcolor();moveSteps();putAndcolor();turnLeft();moveSteps();putAndcolor(); left = false; right = true;}
    void right(){turnLeft();moveSteps();putAndcolor();turnRight(); left = true; right = false;}
    void upLeft(){turnRight(); moveSteps();turnLeft();if(frontIsClear()) {moveSteps();putAndcolor();} up = false ; down = true;}
    void downLeftCol(){ turnLeft(); moveSteps();turnRight();if(frontIsClear()) {moveSteps();putAndcolor();} up = true ; down = false;}
    void downLeftRow(){moveSteps();turnRight();if(frontIsClear()) moveSteps();putAndcolor();turnLeft(); right = true; left = false;}
    void downRight(){moveSteps(); turnLeft(); if(frontIsClear()) moveSteps(); putAndcolor(); turnRight(); right = false; left = true;}

    void check_R_L(boolean a){if(a) turnRight(); else turnLeft();}
    void check_L_R(boolean a){if(a) turnLeft(); else turnRight();}
    void divide4rooms(){
        if(rows == 1 || columns == 1){
            if(columns >= 7) { fillLine(columns); }
            else { turnLeft(); fillLine(rows); }
        }
        else if(rows == 2 || columns == 2){
            if(columns >= 4){
                if(ZigZag(columns)) fillZigZagColumn(columns);
                else fill2Line(columns);
            }
            else{
                if(ZigZag(rows)) fillZigZagRow(rows);
                else fill2Line(rows);
            }
        }
        else if(rows == columns && columns%2 == 0 && columns >= 4) fanMove();
        else if(rows%2 == 1 && columns%2 == 1) oddOddMove();
        else if(rows%2 == 0 && columns%2 == 0) evenEven(Math.min(rows , columns) , Math.max(rows , columns));
        else {
            if(rows%2 == 0) evenOdd(columns , rows);
            else evenOdd(rows , columns);
        }
    }
    boolean checkOf4rooms(){
        if((rows == 1 && columns >= 7) || (columns == 1 && rows >= 7)) return true;
        if((rows == 2 && columns >= 4) || (columns == 2 && rows >= 4)) return true;
        if(rows%2 == 1 && columns%2 == 1 && rows >= 3 && columns >= 3) return true;
        if(rows == columns && rows%2 == 0 && rows >= 4) return true;
        if(rows%2 == 0 && columns%2 == 0 && rows >= 4 && columns >= 4) return true;
        return ((rows % 2 == 1 && columns % 2 == 0) || (columns % 2 == 1 && rows % 2 == 0)) && rows >= 3 && columns >= 3;
    }
    void evenOdd(int odd , int even){
        if(odd == rows) turnLeft();
        int step = odd / 2;
        while(step-->0){moveSteps();} putAndcolor();
        if(odd == rows)  turnRight();
        else turnLeft();
        while(frontIsClear()){moveSteps(); if(!beepersPresent()) putAndcolor();} turnAround();
        step = even / 2 - 1;
        while(step-->0){moveSteps(); if(!beepersPresent()) putAndcolor();} turnRight();
        step = (odd / 2); step += step%2; step /= 2;
        while(step-->0){moveSteps(); if(!beepersPresent()) putAndcolor();}
        turnLeft(); moveSteps();
        if(((odd / 2)) % 2 == 1) putAndcolor();
        turnRight();
        while(frontIsClear()){moveSteps(); if(!beepersPresent()) putAndcolor();}
        turnRight(); moveSteps(); turnRight();
        step = odd / 2;
        while(step-->0){moveSteps();}
        step = (odd / 2); step += step%2; step /= 2;
        while(step-->0){moveSteps(); if(!beepersPresent()) putAndcolor();}
        turnRight(); moveSteps(); if(((odd / 2) % 2 == 1)) putAndcolor(); turnLeft();
        while(frontIsClear()){moveSteps(); if(!beepersPresent()) putAndcolor();}
    }
    void evenEven(int n , int m){
        boolean isRows = (rows == n);
        if(isRows) turnLeft();
        int step = n / 2 - 1;
        while(step-->0){moveSteps();}
        check_R_L(isRows);putAndcolor();
        step = m / 2 - 1;
        while(step-->0){moveSteps(); if(!beepersPresent()) putAndcolor();}
        check_R_L(isRows);
        while(frontIsClear()){moveSteps(); if(!beepersPresent()) putAndcolor();}
        check_L_R(isRows); moveSteps(); check_L_R(isRows); putAndcolor();
        step = n/ 2 - 1;
        while(step-->0){moveSteps(); if(!beepersPresent()) putAndcolor();}
        check_R_L(isRows);
        while(frontIsClear()){moveSteps(); if(!beepersPresent()) putAndcolor();}
        check_L_R(isRows); moveSteps(); check_L_R(isRows); putAndcolor();
        step = m / 2 - 1;
        while(step-->0){moveSteps(); if(!beepersPresent()) putAndcolor();}
        check_R_L(isRows);
        if(isRows){
            step = rows/2 - 1;
            while(step-->0){moveSteps(); if(!beepersPresent()) putAndcolor();}
            turnLeft();moveSteps();turnLeft();putAndcolor();
            step = rows/2 - 1;
            while(step-->0){moveSteps(); if(!beepersPresent()) putAndcolor();}
            turnRight();
            while(frontIsClear()){moveSteps(); if(!beepersPresent()) putAndcolor();}
        }
        else{
            while(frontIsClear()){moveSteps(); if(!beepersPresent()) putAndcolor();}
            turnRight(); moveSteps(); turnRight(); putAndcolor();
            step = columns/ 2 - 1;
            while(step-->0){moveSteps(); if(!beepersPresent()) putAndcolor();}
            turnLeft();
            step = rows / 2 - 1;
            while(step-->0){moveSteps(); if(!beepersPresent()) putAndcolor();}
        }
    }
    void oddOddMove(){
        if(rows <= columns){
            turnLeft();
            int step = rows/2;
            while(step-->0){moveSteps();}
            turnRight(); putAndcolor();
            while(frontIsClear()){moveSteps(); if(!beepersPresent()) putAndcolor();}
            turnAround();
            step = columns / 2;
            while(step-->0){moveSteps(); if(!beepersPresent()) putAndcolor();}
            turnLeft();
            step = rows/2;
            while(step-->0){moveSteps(); if(!beepersPresent()) putAndcolor();}
        }
        else{
            int step = columns/2;
            while(step-->0){moveSteps();}
            putAndcolor();
            turnLeft();
            while(frontIsClear()){moveSteps(); if(!beepersPresent()) putAndcolor();}
            turnAround();
            step = rows/2;
            while(step-->0){moveSteps(); if(!beepersPresent()) putAndcolor();}
            turnRight();
            while(frontIsClear()){moveSteps(); if(!beepersPresent()) putAndcolor();}
        }
        turnAround();
        while(frontIsClear()){moveSteps(); if(!beepersPresent()) putAndcolor();}
    }
    void fanMove(){
        int step = columns / 2;
        while(step-->0){ moveSteps(); }
        turnLeft();putAndcolor();
        step = columns/2 - 1;
        while(step-->0){ moveSteps(); if(!beepersPresent())putAndcolor(); }
        turnLeft(); moveSteps(); turnRight();
        while(frontIsClear()){
            moveSteps(); if(!beepersPresent())putAndcolor();
        }
        turnAround();
        step = columns / 2;
        while(step-->0){ moveSteps(); if(!beepersPresent())putAndcolor();}
        turnRight();
        while(frontIsClear()){
            moveSteps(); if(!beepersPresent())putAndcolor();
        }
        turnAround();
        step = columns/2 - 1;
        while(step-->0){ moveSteps(); if(!beepersPresent())putAndcolor(); }
        turnLeft(); moveSteps(); turnRight();
        while(frontIsClear()){
            moveSteps(); if(!beepersPresent())putAndcolor();
        }
    }

    void divide3rooms(){
        if(rows == 1){
            if(columns == 6) { putAndcolor(); moveSteps(); }
            moveSteps(); putAndcolor(); moveSteps();
        }
        else if(columns == 1){
            turnLeft();
            if(rows == 6){ putAndcolor(); moveSteps(); }
            moveSteps(); putAndcolor(); moveSteps();
        }
        else if(rows == 3){
            putAndcolor(); turnLeft(); fillZigZagRow(rows);
        }
        else{ putAndcolor(); fillZigZagColumn(columns); return;}
        if(rows != 3) { moveSteps(); putAndcolor(); }
    }
    boolean checkOf3rooms(){
        if(rows == 1 && (columns == 5 || columns == 6) || (columns == 1 && (rows == 5 || rows == 6)) ) return true;
        return (rows == 2 && columns == 3) || (rows == 3 && columns == 2);
    }

    boolean checkOf2rooms(){
        if(rows == 1 && ( columns <= 4 && columns >= 3) || (columns == 1 && ( rows <= 4 && rows >= 3))) return true;
        return columns == 2 && rows == 2;
    }
    void divide2rooms(){
        if(rows == 1){
            if(columns == 4) {putAndcolor();moveSteps();}
            moveSteps();putAndcolor();
        }
        else if(columns == 1){
            turnLeft();
            if(rows == 4) {putAndcolor(); moveSteps();}
            moveSteps();putAndcolor();
        }
        else{putAndcolor(); fillZigZagColumn(rows);}
    }

    void fill2Line(int a){
        int div = (a+1) / 4 , noRow = a; boolean in = true;
        boolean isCol = (a == columns);
        while(div*4 <= noRow){
            in = false;
            if(noRow == a){
                putAndcolor();
                if(isCol) turnLeft();
                moveSteps(); putAndcolor();
                if(isCol) turnRight();
                else turnLeft();
                moveSteps();TrueToFalse();}
            else {
                putAndcolor();
                if ((down && isCol) || (right && !isCol)) turnLeft();else turnRight();
                moveSteps();putAndcolor();
                if ((down && isCol) || (right && !isCol)) turnRight(); else turnLeft();
                moveSteps(); TrueToFalse();
            }
            --noRow;
        }
        if( in && !isCol) turnLeft();
        while(frontIsClear()){
            int freq = div - 1;
            while(freq-->0 && frontIsClear()){moveSteps();}
            if(frontIsBlocked()) break;
            putAndcolor();
            if ((down && isCol) || (right && !isCol)) turnLeft();else turnRight();
            if(frontIsClear()) {
                moveSteps();
                putAndcolor();
            }
            else break;
            if ((down && isCol) || (right && !isCol)) turnRight(); else turnLeft();
            if(frontIsClear()) {
                moveSteps();
                TrueToFalse();
            }
            else break;
        }
    }
    void fillLine(int a){
        int div = (a + 1) / 4 , noColRow = a;
        while(div*4 <= noColRow) {
            putAndcolor(); moveSteps(); noColRow--;
        }
        while(frontIsClear()){
            int freq = div - 1;
            while(freq-->0 && frontIsClear()) moveSteps();
            if(frontIsClear()) { putAndcolor();moveSteps(); }
        }
    }
    boolean ZigZag(int a){ return a <= 6;}
    void fillZigZagColumn(int a){
        int zigzag = a - 4 ; // fill
        while(zigzag > 0){ if(down) down(); else up(); zigzag--;}
        while(frontIsClear()){ if(up){upLeft();} else downLeftCol();}
    }
    void fillZigZagRow(int a){
        int zigzag = a - 4 ;
        while(zigzag > 0){ if(right) right(); else left(); zigzag--;}
        while(frontIsClear()){ if(right){downRight();} else downLeftRow();}
    }
}
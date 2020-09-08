package com.example.wordsearch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements GridAdapter.OnClickListener {

//    GridView gridView;
    RecyclerView recyclerView;
    RecyclerView wordBank;
    Button checkButton;
    Button replayButton;
    GridAdapter gridAdapter;
    WordBankAdapter bankAdapter;
    TextView wordCounter;

    char[][] board;
    boolean[][] checkWord;
    ArrayList<HashMap<String, HashSet<Point>>> wordBankData;
    HashSet<Point> currWord;
    int wordsFound;


    @Override
    protected void onCreate(Bundle savedInstanceState) { // initialize instance variables, set adapters/layoutmanagers
//        Log.d("MainActivity", "entered main activity!");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fillBoard();
        currWord = new HashSet<>();
        wordsFound = 0;


//        Log.d("MainActivity", "wordBankData: " + wordBankData.toString());

        wordCounter = findViewById(R.id.counter);
        wordCounter.setText(getString(R.string.counter, 0));

        recyclerView = findViewById(R.id.letterBoard);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 10));

        gridAdapter = new GridAdapter(this, board);
        gridAdapter.setClickListener(this);
        recyclerView.setAdapter(gridAdapter);

        wordBank = findViewById(R.id.wordBank);
        wordBank.setLayoutManager(new GridLayoutManager(this, 3));
        bankAdapter = new WordBankAdapter(this, wordBankData);
        wordBank.setAdapter(bankAdapter);


        checkButton = findViewById(R.id.checkButton);
        checkButton.setOnClickListener(new checkButtonClickListener());

    }



    @Override
    public void onLetterClicked(int position) {
        int x = position/10;
        int y = position%10;
        Point curr = new Point(x, y);
        if (currWord.contains(curr)) {
            currWord.remove(curr);
        }
        else {
            currWord.add(new Point(x, y));
        }
//        Log.d("MainActivity", "clicked letter " + board[position / 10][position % 10]);
    }


    public class checkButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
//                Log.d("MainActivity", "clicked on button");
//                Log.d("MainActivity", "currWord: " + currWord.toString());
            boolean foundWord = false;
            for (int i = 0; i < wordBankData.size(); i++) {
                String word = "";
                for (String key : wordBankData.get(i).keySet()) {
                    word = key;
                }
                if (word.length() == currWord.size()) {
                    boolean trigger = false;

                    for (Point wordPoint : wordBankData.get(i).get(word)) {
                        if (! currWord.contains(wordPoint)) {
                            trigger = true;
                            break; //stop searching this word
                        }
                    }
                    if (trigger == false) {
                        foundWord = true;
                        // found matching word
                        wordBank.findViewHolderForLayoutPosition(i);
//                            Log.d("MainActivity", "entered trigger/found word");
                        bankAdapter.markWordFound((WordBankAdapter.ViewHolder) Objects.requireNonNull(wordBank.findViewHolderForLayoutPosition(i)));
                        wordsFound++;
                        if (wordsFound == 1) {
                            wordCounter.setText(getString(R.string.counterOneWord, wordsFound));
                        }
                        else {
                            wordCounter.setText(getString(R.string.counter, wordsFound));
                            if (wordsFound == 6) {
                                Toast.makeText(getApplicationContext(), "Congratulations! All words found", Toast.LENGTH_LONG).show();
                            }
                        }
                        break;
                    }
                }
               //else continue
            }
//                Log.d("MainActivity", "broke out");
            if (foundWord == false) {
                Toast.makeText(getApplicationContext(), "Not a word, try again!", Toast.LENGTH_LONG).show();
            }

            for (Point toClear : currWord) {
                int x = toClear.x;
                int y = toClear.y;
                gridAdapter.clearLetter((GridAdapter.ViewHolder) Objects.requireNonNull(recyclerView.findViewHolderForAdapterPosition((x * 10 + y))));
            }

            // reset collection of points
            currWord = new HashSet<>();

        }
    }

    private void fillBoard() { // assigns places in two d array to characters
        checkWord = new boolean[10][10];
        board = new char[10][10];
        wordBankData = new ArrayList<>();

        Random rand = new Random();

        assignLetter(0, 0, 'O', "OBJECTIVEC", 0);
        assignLetter(1, 1, 'B', "OBJECTIVEC", 0);
        assignLetter(2, 2, 'J', "OBJECTIVEC", 0);
        assignLetter(3, 3, 'E', "OBJECTIVEC",0);
        assignLetter(4, 4, 'C', "OBJECTIVEC",0);
        assignLetter(5, 5, 'T', "OBJECTIVEC",0);
        assignLetter(6, 6, 'I', "OBJECTIVEC",0);
        assignLetter(7, 7, 'V', "OBJECTIVEC",0);
        assignLetter(8, 8, 'E', "OBJECTIVEC",0 );
        assignLetter(9, 9, 'C', "OBJECTIVEC",0 );

        assignLetter(0, 2, 'T', "SWIFT",1);
        assignLetter(1, 3, 'F', "SWIFT",1);
        assignLetter(2, 4, 'I', "SWIFT",1);
        assignLetter(3, 5, 'W', "SWIFT",1);
        assignLetter(4, 6, 'S', "SWIFT",1);

        assignLetter(0, 6, 'A', "JAVA",2);
        assignLetter(1, 6, 'V', "JAVA",2);
        assignLetter(2, 6, 'A', "JAVA",2);
        assignLetter(3, 6, 'J', "JAVA",2);

        assignLetter(0, 5, 'K', "KOTLIN", 3);
        assignLetter(1, 4, 'O', "KOTLIN", 3);
        assignLetter(2, 3, 'T', "KOTLIN", 3);
        assignLetter(3, 2, 'L', "KOTLIN", 3);
        assignLetter(4, 1, 'I', "KOTLIN", 3);
        assignLetter(5, 0, 'N', "KOTLIN", 3);

        assignLetter(6, 0, 'E', "MOBILE", 4);
        assignLetter(6, 1, 'L', "MOBILE", 4);
        assignLetter(6, 2, 'I', "MOBILE", 4);
        assignLetter(6, 3, 'B', "MOBILE", 4);
        assignLetter(6, 4, 'O', "MOBILE", 4);
        assignLetter(6, 5, 'M', "MOBILE", 4);

        assignLetter(0, 8, 'V', "VARIABLE", 5);
        assignLetter(1, 8, 'A', "VARIABLE", 5);
        assignLetter(2, 8, 'R', "VARIABLE", 5);
        assignLetter(3, 8, 'I', "VARIABLE", 5);
        assignLetter(4, 8, 'A', "VARIABLE", 5);
        assignLetter(5, 8, 'B', "VARIABLE", 5);
        assignLetter(6, 8, 'L', "VARIABLE", 5);
        assignLetter(7, 8, 'E', "VARIABLE", 5);


        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (checkWord[i][j] == false) {
                    board[i][j] = (char) (rand.nextInt(26) + 'A');
                }
            }
        }
    }

    private void assignLetter(int x, int y, char letter, String word, int index) {
        board[x][y] = letter;
        checkWord[x][y] = true;
        if (wordBankData.size() <= index) {
            wordBankData.add(new HashMap<String, HashSet<Point>>());
            wordBankData.get(index).put(word, new HashSet<Point>());
        }
        wordBankData.get(index).get(word).add(new Point(x, y));
    }




}
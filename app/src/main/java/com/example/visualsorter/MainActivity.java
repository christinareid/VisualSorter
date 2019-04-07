package com.example.visualsorter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Random;

public class MainActivity extends AppCompatActivity
{
    private ListView sortedLV, unsortedLV;
    private ArrayAdapter<String> sortedAA, unsortedAA;
    private int[] sortedNumbers, unsortedNumbers;
    private String[] sortedStrings, unsortedStrings;
    private final int numberOfElements = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.sortedLV = this.findViewById(R.id.sortedLV);
        this.unsortedLV = this.findViewById(R.id.unsortedLV);

        this.sortedNumbers = new int[this.numberOfElements];
        this.unsortedNumbers = new int[this.numberOfElements];
        this.sortedStrings = new String[this.numberOfElements];
        this.unsortedStrings = new String[this.numberOfElements];

        this.sortedAA = new ArrayAdapter<String>(this, R.layout.simple_listview_row, this.sortedStrings);
        this.unsortedAA = new ArrayAdapter<String>(this, R.layout.simple_listview_row, this.unsortedStrings);

        this.sortedLV.setAdapter(this.sortedAA);
        this.unsortedLV.setAdapter(this.unsortedAA);

        this.initializeArrays();

    }

    private void insertionSort(int[] ar)
    {
        int theFollower, swap;

        for(int currStart = 1; currStart < ar.length; currStart++)
        {
            theFollower = currStart;
            while (theFollower > 0 && ar[theFollower] < ar[theFollower-1])
            {
                swap = ar[theFollower];
                ar[theFollower] = ar[theFollower-1];
                ar[theFollower-1] = swap;
                theFollower--;
            }
        }
    }

    private void mergeSort(int[] ar)
    {
        msHelper(ar, 0, ar.length-1);
    }

    private void msHelper(int ar[], int begin, int end)
    {
        if(begin != end)
        {
            int leftBegin = begin;
            int leftEnd = ((end-begin)/2 + begin);
            int rightBegin = ((end-begin)/2 + begin) + 1;
            int rightEnd = end;
            msHelper(ar, leftBegin, leftEnd);
            msHelper(ar, rightBegin, rightEnd);

            int[] temp = new int[rightEnd - leftBegin + 1];
            int currLeft = leftBegin;
            int currRight = rightBegin;

            for(int tempPos = 0; tempPos < temp.length; tempPos++)
            {
                if(currLeft > leftEnd)
                {
                    temp[tempPos] = ar[currRight];
                    currRight++;
                }
                else if(currRight > rightEnd)
                {
                    temp[tempPos] = ar[currLeft];
                    currLeft++;
                }
                else {
                    if(ar[currLeft] < ar[currRight])
                    {
                        temp[tempPos] = ar[currLeft];
                        currLeft++;
                    }
                    else
                    {
                        temp[tempPos] = ar[currRight];
                        currRight++;
                    }
                }
            }
            int tempPos = 0;
            for(int i = leftBegin; i <= rightEnd; i++)
            {
                ar[i] = temp[tempPos];
                tempPos++;
            }
        }

    }

    /*private int factorial(int n)
    {
      if(n == 1)
      {
          return 1;
      }
      else {
          return n * this.factorial(n - 1);
      }
    }
    */

    public void insertionSortButtonPressed(View v)
    {
        //perform an insertion sort on the unsortedArray
        this.insertionSort(this.unsortedNumbers);
        this.updateStingArrays();
    }

    public void mergeSortButtonPressed(View v)
    {
        //perform a merge sort on the unsortedArray
        this.mergeSort(this.unsortedNumbers);
        this.updateStingArrays();
    }

    public void resetButtonPressed(View v)
    {
        this.initializeArrays();
    }

    private void initializeArrays()
    {
        //fill my unsorted int array
        this.fillRandomIntArray(this.unsortedNumbers);
        this.copyContentsOfIntArrays(this.unsortedNumbers, this.sortedNumbers);
        this.updateStingArrays();
    }

    private void updateStingArrays()
    {
        //fill my string arrays to mimic the int arrays
        this.copyIntArrayToStringArray(this.unsortedNumbers, this.unsortedStrings);
        this.copyIntArrayToStringArray(this.sortedNumbers, this.sortedStrings);
        this.sortedAA.notifyDataSetChanged();
        this.unsortedAA.notifyDataSetChanged();
    }

    private void copyIntArrayToStringArray(int[] arInts, String[] arStrings)
    {
        for(int i=0; i < arInts.length; i++)
        {
            arStrings[i] = "" + arInts[i];
        }
    }

    private void copyContentsOfIntArrays(int[] source, int[] destination)
    {
        for(int i=0; i< source.length; i++)
        {
            destination[i] = source[i];
        }
    }

    private void fillRandomIntArray(int[] ar)
    {
        Random r = new Random();
        for(int i=0; i < ar.length; i++)
        {
            ar[i] = r.nextInt(500);
        }
    }
}

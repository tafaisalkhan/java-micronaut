package com.micronet;

import java.util.Arrays;



//1. Create starts[] and ends[].
//        2. Copy start times into starts[].
//        3. Copy end times into ends[].
//        4. Sort both arrays.
//5. Initialize rooms = 0, endPointer = 0.
//6. For each start time:
//If start < ends[endPointer]
//rooms++
//Else
//endPointer++
//        7. Return rooms.
//
public class meetingscheduler {

    public static  void main(String[] args) {
        int[][] intervals = {
                {0, 30},
                {5, 10},
                {15, 20}
        };
       System.out.println(schedulerMetting(intervals));
    }

    private static int schedulerMetting( int[][] intervals) {
        if (intervals == null || intervals.length == 0){
            return 0;
        }

        int n = intervals.length;

        int[] starts = new int[n];
        int[] ends = new int[n];

        for(int i =0 ;i<n; i++){
            starts[i] = intervals[i][0];
            ends[i]= intervals[i][1];
        }

        Arrays.sort(starts);
        Arrays.sort(ends);

        int room =0;
        int endPointer =0;

        for(int start : starts){
            if(start < ends[endPointer]){
                room++;
            }
            else{
                endPointer++;
            }
        }
        return room;
    }
}

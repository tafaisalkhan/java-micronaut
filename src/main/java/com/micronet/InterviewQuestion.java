package com.micronet;

import io.micronaut.runtime.Micronaut;

import java.util.*;

public class InterviewQuestion {

    public static  void main(String[] args) {

        courseScheduler();
    }

    static Boolean courseScheduler(){
        int numCourses = 4;

        int [][] prerequisites = {
                {1,0},
                {2,0},
                {3,1},
                {3,2}
        };

        // Step 1: Build graph + indegree
        List<Integer>[] graph = new ArrayList[numCourses];
        int[] indegree = new int[numCourses];

        for (int i = 0; i < numCourses; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] pre : prerequisites) {
            int course = pre[0];
            int prereq = pre[1];

            graph[prereq].add(course);
            indegree[course]++;
        }

        // Step 2: Add all 0 indegree nodes to queue
        Queue<Integer> q = new LinkedList<>();

        for (int i = 0; i < numCourses; i++) {
            if (indegree[i] == 0) {
                q.offer(i);
            }
        }

        // Step 3: BFS processing
        int count = 0;
        System.out.println(Arrays.stream(graph).toList());
        System.out.println(Arrays.toString(indegree));
        while (!q.isEmpty()) {
            System.out.println("quese element" + Arrays.toString(q.toArray()));
            int curr = q.poll();
            count++;
            for (int next : graph[curr]) {
                System.out.println("next index" + next);
                System.out.println("before minis " + indegree[next]);
                indegree[next]--;
                System.out.println("after minis " + indegree[next]);
                if (indegree[next] == 0) {
                    System.out.println("adding in queue "+ next);
                    q.offer(next);
                }
            }
        }
        System.out.println(Arrays.stream(graph).toList());
        System.out.println(Arrays.toString(indegree));
        // Step 4: check if all courses processed
        return count == numCourses;


//        List<Integer>[] graph = new ArrayList[numCourses];
//        int[] indegree = new int[numCourses];
//
//        System.out.println(Arrays.stream(graph).toList());
//
//        System.out.println(Arrays.toString(indegree));
//
//        //build graph
//        for(int index=0;index<numCourses;index++){
//            graph[index] = new ArrayList<>();
//        }
//
//        System.out.println(Arrays.stream(graph).toList());
//
//        for (int[] pre: prerequisites){
//            int course = pre[0];
//            int prereq = pre[1];
//
//            graph[prereq].add(course);
//            indegree[course]++;
//        }
//
//        System.out.println(Arrays.stream(graph).toList());
//
//        System.out.println(Arrays.toString(indegree));
//
//        Queue<Integer> q = new LinkedList<>();
//
//        for (int i = 0; i < numCourses; i++) {
//            if (indegree[i] == 0)
//                q.offer(i);
//        }
//
//        System.out.println(Arrays.stream(graph).toList());
//
//        System.out.println(Arrays.toString(indegree));
//        int count = 0;
//
//        while (!q.isEmpty()) {
//            int curr = q.poll();
//            count++;
//
//            for (int next : graph[curr]) {
//                indegree[next]--;
//
//                if (indegree[next] == 0) {
//                    q.offer(next);
//                }
//            }
//        }
//
//        return count == numCourses;
    }
}

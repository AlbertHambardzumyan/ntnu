// Copyright (c) 2016 Albert Hambardzumyan
// All rights reserved.
// This software is released under the BSD license.
package org.tdt4280.mas.scc;


import org.tdt4280.mas.timer.Runtime;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author Albert Hambardzumyan
 */
public class Tarjan {
    List<Integer>[] graph;
    boolean[] visited;
    Stack<Integer> stack;
    int time;
    int[] lowlink;
    List<List<Integer>> components;

    public List<List<Integer>> scc(List<Integer>[] graph) {
        final long startTime = System.nanoTime();

        int n = graph.length;
        this.graph = graph;
        visited = new boolean[n];
        stack = new Stack<Integer>();
        time = 0;
        lowlink = new int[n];
        components = new ArrayList<List<Integer>>();

        for (int u = 0; u < n; u++)
            if (!visited[u])
                dfs(u);

        Runtime.print(System.nanoTime() - startTime, this.getClass().getSimpleName());
        return components;
    }

    void dfs(int u) {
        lowlink[u] = time++;
        visited[u] = true;
        stack.add(u);
        boolean isComponentRoot = true;

        for (int v : graph[u]) {
            if (!visited[v])
                dfs(v);
            if (lowlink[u] > lowlink[v]) {
                lowlink[u] = lowlink[v];
                isComponentRoot = false;
            }
        }

        if (isComponentRoot) {
            List<Integer> component = new ArrayList<Integer>();
            while (true) {
                int x = stack.pop();
                component.add(x);
                lowlink[x] = Integer.MAX_VALUE;
                if (x == u)
                    break;
            }
            components.add(component);
        }
    }

}

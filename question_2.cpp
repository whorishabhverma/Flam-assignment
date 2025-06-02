/*
Q.2 You are building a module loader for a large software system. Each module may depend on one or more other modules. Before loading, you must ensure that the dependencies do not contain any circular dependency, as that would lead to infinite loading loops.
Given a list of N modules and their dependency relationships, determine if the dependency graph contains a cycle.
Function Signature:
bool hasCircularDependency(int n, vector<vector<int>>& edges);
Input:
An integer n representing the number of modules labeled from 0 to n - 1.
A list of edges edges, where each edge[i] = {a, b} means module a depends on module b.
Output:
Return true if there is a circular dependency, otherwise return false.


Constraints:
1 <= n <= 10^4
0 <= edges.length <= 10^5
Dependencies form a directed graph
Self-dependencies like {a, a} are valid and considered a cycle
The graph can have multiple disconnected components


Example 1:
Input:
n = 4
edges = {{0, 1}, {1, 2}, {2, 3}}
Output:
false
Example 2:
Input:
n = 4
edges = {{0, 1}, {1, 2}, {2, 0}}
Output:
True

*/
#include <iostream>
#include <vector>
using namespace std;
class Solution {
public:
    bool hasCircularDependency(int n, vector<vector<int>>& edges) {
        vector<vector<int>> graph(n);
        for (const auto& edge : edges) {
            graph[edge[0]].push_back(edge[1]);
        }

        vector<int> visited(n, 0); // 0 = unvisited, 1 = visiting, 2 = visited

        for (int i = 0; i < n; ++i) {
            if (visited[i] == 0 && dfs(i, graph, visited)) {
                return true; // found a cycle
            }
        }

        return false; // no cycle found
    }

private:
    bool dfs(int node, vector<vector<int>>& graph, vector<int>& visited) {
        visited[node] = 1; // mark as visiting

        for (int neighbor : graph[node]) {
            if (visited[neighbor] == 1) return true; // back edge (cycle)
            if (visited[neighbor] == 0 && dfs(neighbor, graph, visited))
                return true;
        }

        visited[node] = 2; // mark as fully visited
        return false;
    }
};


int main() {
    Solution sol;
    
    int n1 = 4;
    vector<vector<int>> edges1 = {{0, 1}, {1, 2}, {2, 3}};
    cout << boolalpha << sol.hasCircularDependency(n1, edges1) << endl; // false

    int n2 = 4;
    vector<vector<int>> edges2 = {{0, 1}, {1, 2}, {2, 0}};
    cout << boolalpha << sol.hasCircularDependency(n2, edges2) << endl; // true

    return 0;
}

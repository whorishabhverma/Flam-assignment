/*
Q1.The n-queens puzzle is the problem of placing n queens on an n x n chessboard such that no two queens attack each other.
Given an integer n, return all distinct solutions to the n-queens puzzle. You may return the answer in any order.
Each solution contains a distinct board configuration of the n-queens' placement, where 'Q' and '.' both indicate a queen and an empty space, respectively.
Example 1: 
Input: n = 4
Output: [[".Q..","...Q","Q...","..Q."],["..Q.","Q...","...Q",".Q.."]]
Explanation: There exist two distinct solutions to the 4-queens puzzle as shown above
Example 2:
Input: n = 1
Output: [["Q"]]
Constraints:
1 <= n <= 9

*/
#include <vector>
#include <string>
#include<iostream>
using namespace std;

class Solution {
public:
    vector<vector<string>> solveNQueens(int n) {
        vector<vector<string>> result;
        vector<string> board(n, string(n, '.'));
        vector<bool> cols(n, false), diag1(2 * n - 1, false), diag2(2 * n - 1, false);
        backtrack(0, n, board, cols, diag1, diag2, result);
        return result;
    }

private:
    void backtrack(int row, int n, vector<string>& board,
                   vector<bool>& cols, vector<bool>& diag1, vector<bool>& diag2,
                   vector<vector<string>>& result) {
        if (row == n) {
            result.push_back(board);
            return;
        }

        for (int col = 0; col < n; ++col) {
            if (cols[col] || diag1[row - col + n - 1] || diag2[row + col])
                continue;

            board[row][col] = 'Q';
            cols[col] = diag1[row - col + n - 1] = diag2[row + col] = true;

            backtrack(row + 1, n, board, cols, diag1, diag2, result);

            board[row][col] = '.';
            cols[col] = diag1[row - col + n - 1] = diag2[row + col] = false;
        }
    }
};
int main() {
    Solution sol;
    int n;
    cin>>n;
    vector<vector<string>> solutions = sol.solveNQueens(n);
   cout << "[";
    for (size_t i = 0; i < solutions.size(); ++i) {
        cout << "[";
        for (size_t j = 0; j < solutions[i].size(); ++j) {
            cout << "\"" << solutions[i][j] << "\"";
            if (j != solutions[i].size() - 1) cout << ",";
        }
        cout << "]";
        if (i != solutions.size() - 1) cout << ",";
    }
    cout << "]" << endl;
    return 0;
}

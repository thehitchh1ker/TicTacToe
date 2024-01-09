public class Logic {
    Seed human, bot;

    public Logic(Seed currentPlayer) {
        human = currentPlayer;
        bot = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT: Seed.CROSS;
    }

    boolean isMovesLeft(Cell[][] board) { 
        for (int i = 0; i < 3; i++) 
            for (int j = 0; j < 3; j++) 
                if (board[i][j].content == Seed.EMPTY) 
                    return true; 
        return false; 
    } 

    int evaluate(Cell[][] board) { 
        // Checking for Rows for X or O victory. 
        for (int row = 0; row < 3; row++) { 
            if (board[row][0].content == board[row][1].content && board[row][1].content == board[row][2].content) { 
                if (board[row][0].content == bot) 
                    return +10; 
                else if (board[row][0].content == human) 
                    return -10; 
            } 
        } 

        // Checking for Columns for X or O victory. 
        for (int col = 0; col < 3; col++) { 
            if (board[0][col].content == board[1][col].content && board[1][col].content == board[2][col].content) { 
                if (board[0][col].content == bot) 
                    return +10; 
                else if (board[0][col].content == human) 
                    return -10; 
            } 
        } 

        // Checking for Diagonals for X or O victory. 
        if (board[0][0].content == board[1][1].content && board[1][1].content == board[2][2].content) { 
            if (board[0][0].content == bot) 
                return +10; 
            else if (board[0][0].content == human) 
                return -10; 
        } 

        if (board[0][2].content == board[1][1].content && board[1][1].content == board[2][0].content) { 
            if (board[0][2].content == bot) 
                return +10; 
            else if (board[0][2].content == human) 
                return -10; 
        } 

        // Else if none of them have won then return 0 
        return 0; 
    } 

    // This is the minimax function. It considers all 
    // the possible ways the game can go and returns 
    // the value of the board 
    int minimax(Cell[][] board, int depth, boolean isMax) { 
        int score = evaluate(board); 

        // If Maximizer has won the game return his/her 
        // evaluated score 
        if (score == 10) 
            return score - depth; 

        // If Minimizer has won the game return his/her 
        // evaluated score 
        if (score == -10) 
            return score - depth; 

        // If there are no more moves and no winner then 
        // it is a tie 
        if (isMovesLeft(board) == false) 
            return 0 - depth; 

        // If this maximizer's move 
        if (isMax) { 
            int best = -1000; 

            // Traverse all cells 
            for (int i = 0; i < 3; i++) { 
                for (int j = 0; j < 3; j++) { 
                    // Check if cell is empty 
                    if (board[i][j].content == Seed.EMPTY) { 
                        // Make the move 
                        board[i][j].content = bot; 

                        // Call minimax recursively and choose 
                        // the maximum value 
                        best = Math.max( best, minimax(board, depth+1, !isMax) ); 

                        // Undo the move 
                        board[i][j].content = Seed.EMPTY; 
                    } 
                } 
            } 
            return best; 
        } 

        // If this minimizer's move 
        else { 
            int best = 1000; 

            // Traverse all cells 
            for (int i = 0; i < 3; i++) { 
                for (int j = 0; j < 3; j++) { 
                    // Check if cell is empty 
                    if (board[i][j].content == Seed.EMPTY) { 
                        // Make the move 
                        board[i][j].content = human; 

                        // Call minimax recursively and choose 
                        // the minimum value 
                        best = Math.min(best, minimax(board, depth+1, !isMax)); 

                        // Undo the move 
                        board[i][j].content = Seed.EMPTY; 
                    } 
                } 
            } 
            return best; 
        } 
    } 

    // This will return the best possible move for the bot 
    Move getMove(Cell[][] board) { 
        int bestVal = -1000; 
        Move bestMove = new Move(-1, -1); 

        // Traverse all cells, evaluate minimax function for 
        // all empty cells. And return the cell with optimal 
        // value. 
        for (int i = 0; i < 3; i++) { 
            for (int j = 0; j < 3; j++) { 
                // Check if cell is empty 
                if (board[i][j].content == Seed.EMPTY) { 
                    // Make the move 
                    board[i][j].content = bot; 

                    // compute evaluation function for this 
                    // move. 
                    int moveVal = minimax(board, 0, false); 

                    // Undo the move 
                    board[i][j].content = Seed.EMPTY; 

                    // If the value of the current move is 
                    // more than the best value, then update 
                    // best/ 
                    if (moveVal > bestVal) { 
                        bestMove.row = i; 
                        bestMove.col = j; 
                        bestVal = moveVal; 
                    } 
                } 
            } 
        } 

        // System.out.println("\nlogic--- " + bestMove.row + "  " + bestMove.col); 

        return bestMove; 
    }
} 
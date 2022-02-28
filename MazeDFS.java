

package mazedfs;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;


public class MazeDFS extends JFrame {
    
    JButton start = new JButton ("Start") ;//Button to start solving 
    Node current;// current node 
    Node next;   // next node 
    
    final static int F = 0;//free space 
    final static int W = 1;// wall 
    final static int S = 2;//start 
    final static int G = 3;//goal 
    final static int P = 4;// path 
    final static int start_I = 1, start_J = 1; //start point 
    final static int end_I = 6, end_J = 6; // end point .

    int[][] maze = new int[][]{// array for the maze 
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        {1, 2, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 0, 0, 1, 0, 1, 1, 0, 1},
        {1, 0, 1, 1, 1, 0, 1, 1, 0, 1},
        {1, 0, 0, 0, 0, 1, 1, 1, 0, 1},
        {1, 0, 1, 1, 0, 1, 1, 1, 0, 1},
        {1, 0, 0, 1, 0, 1, 3, 0, 0, 1},
        {1, 0, 0, 1, 0, 1, 1, 0, 0, 1},
        {1, 0, 0, 1, 0, 0, 0, 0, 0, 1},
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };
    
    public MazeDFS(){
        // set frame settings
        setTitle("Maze ");
        setSize(660, 490);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setVisible(true);
        
        add (start); // add the Button to the frame  
        start.setBounds(500, 80, 100, 80);
        start.addActionListener(new ActionListener() {
       
        int X=0;//Counter to count how many times button was pressed 
        
        @Override
        public void actionPerformed(ActionEvent ae) {
        if (X<1){// if it is the first press .
         solveStack();
         repaint();
         ++X;
         }
         else{
             //if the Button was press twice , it will show that maze is already solved .
             // if else was not put it will go to solveStack(); and resolved maze which is already solved ,
             //and the result will be incorrect.
            JOptionPane.showMessageDialog(null, "maze is already solved!");
         }
        }
         });
        
    }
    public void solveStack() {//solve DFS with Stack
        // we will push and pop from the stack 
        
        Stack<Node> stack = new Stack<Node>(); // create stack 
        
        Node node = new Node(start_I, start_J);                        
        stack.push(node);// insert the start node 


        while (!stack.empty()) {// while stack is not empty 
           
            current = stack.pop();// get current node  by poping from stack 

            if (Goal(current.i(), current.j())) { // test if the goal is reached break(Exit).
                break;
            }

           
            explored(current.i(), current.j(), P);   // mark the  current node as explored     
            
            // now push the neighbours in the stack .
            
            next = current.down();// go down from the current node
            if (InMaze(next.i(), next.j()) && possible(next.i(), next.j())) {
               
                stack.push(next);

            }
            
            next = current.right();// go right from the current node
            if (InMaze(next.i(), next.j()) && possible(next.i(), next.j())) {
                
                stack.push(next);

            }
            next = current.up();// go up from the current node
            if (InMaze(next.i(), next.j()) && possible(next.i(), next.j())) { // node in the maze and not explored , push in the stack 
              
                stack.push(next);

            }
            
             next = current.left();// go left from the current node
            if (InMaze(next.i(), next.j()) && possible(next.i(), next.j())) {
             
                stack.push(next);
            }
       
        
        }//end of while 
        if (stack.empty()){
             //if  stack is empty and the goal node is not reached
            JOptionPane.showMessageDialog(null, "there is no way to reach the goal !");
        }
        

    }

    public int Size() {// get size of maze
        int s = maze.length;
        return s;
    }
    
    // Goal test : to mack sure if it is reach the goal  
    public boolean Goal(int i, int j) {
        boolean xxx= (i == end_I && j == end_J);                                
        return xxx;                                     
    }
    
    
    //if the node explored mark with value 4 (pink ) 
    public void explored(int i, int j, int value) {
        assert (InMaze(i, j));// use to test if the condition is false it will throw an error 
        maze[i][j] = value; 
       // explored will give the node the value 4 and this will identify the node as visited (colored Pink ) .                       
    }
    
    //return true if cell is within maze 
    public boolean InMaze(int i, int j) {// parameters are position (i,j) of the call

        if (i >= 0 && i < Size() && j >= 0 && j < Size()) {
            return true;
        } else {
            
            return false;
        }
       
    }

    

    // return true if the node is unexplored  (=0) .
    // if the next node is not a wall and not explored then it is possible to be visited next .

    public boolean possible(int i, int j) {
        assert (InMaze(i, j));//use to test if the condition is false it will throw an error 
        boolean yyy = (maze[i][j] != W && maze[i][j] != P);                    
        return yyy;
    }

    

    public void paint(Graphics g) {// drow the maze and path
        super.paint(g);
        g.translate(70, 70);
      
        for (int row = 0; row < Size(); row++) {
            for (int col = 0; col < Size(); col++) {
                Color color;
                switch (maze[row][col]) {

                    case 1://wall
                        color = Color.darkGray;
                        break;

                    case 3://gaol
                        color = Color.RED;
                        break;

                    case 2:// start point 
                        color = Color.YELLOW;

                        break;
                        
                    case 4://path
                        color = Color.pink;
                        break;

                    default:// free space 
                        color = Color.WHITE;
                       
                    
                }
                g.setColor(color);
                g.fillRect(40 * col, 40 * row, 40, 40);

                g.setColor(color.GRAY);
                g.drawRect(40 * col, 40 * row, 40, 40);
                
            }
            
        }
        
            
        

        
        
    }
 
    
        


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {// run the program through swing (GUI)
            @Override
            public void run() {
                MazeDFS maze = new MazeDFS();// create new class which will invoke the constructor 
             
               

            }
        });
    }

}


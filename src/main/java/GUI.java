import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class GUI extends JFrame implements ActionListener{
	//Create some panels
	JPanel pan1 = new JPanel();
	JPanel pan2 = new JPanel();
	JPanel pan3 = new JPanel();
	//Create some GUI components
    JLabel welcomeLabel = new JLabel("Welcome To code Breaker! ", JLabel.CENTER);
    JButton start = new JButton("start");
	// CONSTRUCTOR - Setup your GUI here
    public GUI(){
      FlowLayout layout1 = new FlowLayout();
      GridLayout layout2 = new GridLayout(5,10);
	  setTitle("Code breaker");
	  setSize(500, 400);
	  setResizable(true);
	  Color greyish = new Color(192, 192, 192);
	  pan1.setBackground(greyish);
	  pan2.setBackground(greyish);
	  pan3.setBackground(greyish);
	  setLayout(layout2);//Setting layout for the whole frame
	  pan1.setLayout(layout1);//Layout for Pan1
	  pan1.add(start);
	  pan1.add(welcomeLabel);
	  pan1.setVisible(true);
	  setVisible(true);
	  //
    }
    public static void main(String[] args){
    	GUI frame1 = new GUI();  //Start the GUI
    }

	@Override
	public void actionPerformed(ActionEvent e) {

	}
}
		
		
		

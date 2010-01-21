package Graphique;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {
	public static void main(String[] args) throws IOException {
		SwingUtilities.invokeLater(new Runnable() {
			/**
			 * lance le thread de Swing.
			 */
			public void run() {
				UIManager.put("swing.boldMetal", Boolean.FALSE);

				JFrame frame = new JFrame("Musquash");
		        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        
		        MainWindow main_window = new MainWindow();
		        //Ajoute le menu
		        frame.setJMenuBar(new MenuBar(main_window));
		        //Add content to the window.
		        frame.add(main_window, BorderLayout.CENTER);
		        
		        //Display the window.
		        frame.pack();
		        frame.setVisible(true);
		        frame.setSize(1000, 600);
			}
		});
	}

}

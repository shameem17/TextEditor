import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;






public class TextEditor extends JFrame implements ActionListener {
	
	JTextArea textArea;
	JScrollPane scrollPane;
	JSpinner fontsizeSpinner;
	JLabel fontLabel;
	JButton fontcolorButton;
	JComboBox fontBox;
	JMenuBar menuBar;
	JMenu fileMenu;
	JMenuItem openItem;
	JMenuItem saveItem;
	JMenuItem exitItem;
	
	
	public TextEditor() {
		// TODO Auto-generated constructor stub
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(new Dimension(550,550));
		this.setTitle("Bit2Byte Editor");
		this.setLayout(new FlowLayout());
		this.setLocationRelativeTo(null);
		
		textArea=new JTextArea();
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setFont(new Font("Arial",Font.PLAIN,25));
		
		scrollPane=new JScrollPane(textArea);
		scrollPane.setPreferredSize(new Dimension(500,500));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		fontLabel=new JLabel("Font Size");
		
	  fontsizeSpinner=new JSpinner();
	  fontsizeSpinner.setPreferredSize(new Dimension(50,25));
	  fontsizeSpinner.setValue(25);
	  fontsizeSpinner.addChangeListener(new ChangeListener() {
		
		@Override
		public void stateChanged(ChangeEvent e) {
			// TODO Auto-generated method stub
			textArea.setFont(new Font(textArea.getFont().getFamily(),Font.PLAIN,(int)fontsizeSpinner.getValue()));
			
		}
	});
	  
	  fontcolorButton=new JButton("Color");
	  fontcolorButton.addActionListener(this);
	  
	  String[] fontString=GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
	  fontBox=new JComboBox(fontString);
	  fontBox.setSelectedItem("Arial");
	  
	  fontBox.addActionListener(this);
	  menuBar=new JMenuBar();
	  
	  fileMenu=new JMenu("File");
	  openItem=new JMenuItem("Open File");
	  saveItem=new JMenuItem("Save File");
	  exitItem=new JMenuItem("Exit");
	  
	  fileMenu.add(openItem);
	  fileMenu.add(saveItem);
	  fileMenu.add(exitItem);
	  
	  menuBar.add(fileMenu);
	  
	  openItem.addActionListener(this);
	  saveItem.addActionListener(this);
	  exitItem.addActionListener(this);
	  
	  
		
	  this.setJMenuBar(menuBar);
	    this.add(fontLabel);
	
		this.add(fontsizeSpinner);
		this.add(fontcolorButton);
		this.add(fontBox);
		this.add(scrollPane);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getSource()==fontcolorButton)
		{
			JColorChooser colorChooser=new JColorChooser();
			Color color=null;
			color=colorChooser.showDialog(null,"Choose A color",color.black);
			textArea.setForeground(color);
		}
		if (e.getSource()==fontBox) {
			
			textArea.setFont(new Font((String)fontBox.getSelectedItem(),Font.PLAIN,textArea.getFont().getSize()));
			
		}
		
		if(e.getSource()==exitItem)
		{
			System.exit(0);
		}
		if(e.getSource()==saveItem)
		{
			JFileChooser fileChooser=new JFileChooser();
			fileChooser.setCurrentDirectory(new File("."));
			
			int response=fileChooser.showSaveDialog(null);
			
			if(response==JFileChooser.APPROVE_OPTION)
			{
				File file;
				PrintWriter printWriter=null;
				file=new File(fileChooser.getSelectedFile().getAbsolutePath());
				
				try {
					printWriter=new PrintWriter(file);
					printWriter.println(textArea.getText());
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				finally
				{
					printWriter.close();
				}
			}
		}
		
		if(e.getSource()==openItem)
		{
			JFileChooser fileChooser=new JFileChooser();
			fileChooser.setCurrentDirectory(new File("."));
			FileNameExtensionFilter filter=new FileNameExtensionFilter("Text file", "txt");
			fileChooser.setFileFilter(filter);
			
			int response=fileChooser.showOpenDialog(null);
			if(response==JFileChooser.APPROVE_OPTION)
			{
				File file=new File(fileChooser.getSelectedFile().getAbsolutePath());
				Scanner fileScanner=null;
				try {
					fileScanner= new Scanner(file);
					if(file.isFile())
					{
						while(fileScanner.hasNextLine())
						{
							String lineString=fileScanner.nextLine()+"\n";
							textArea.append(lineString);
						}
					}
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				finally {
					fileScanner.close();
				}
				
			}
		}
		
	}

}

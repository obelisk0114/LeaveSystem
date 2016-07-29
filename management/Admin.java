package management;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.SwingConstants;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Font;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class Admin {
	final int width = 900;
	final int height = 550;
	
	private JFrame mainFrame;
	private JPanel frameTable;
	private JScrollPane containTable;
	private JPopupMenu popupMenu;
	private List<JLabel> item = new ArrayList<JLabel>(50);
	private List<JLabel> personName = new ArrayList<JLabel>();
	private ArrayList<List<JButton>> personLeave = new ArrayList<List<JButton>>();
	private ArrayList<List<JButton>> person = new ArrayList<List<JButton>>();
	private JMenuBar menuBar;
	private JMenu modeMenu, helpMenu, functionMenu;
	private JMenuItem openMenuItem, saveMenuItem, updatefunction, helpMenuItem;
	
	private Font paneFont = new Font(Font.SANS_SERIF, Font.BOLD, 15);
	private Font labelFont = new Font(Font.SANS_SERIF, Font.BOLD, 15);
	
	int tag = -1;
	int maxSize = -1;
	
	public Admin(String title) {
		// Run
		mainFrame = new JFrame(title);
		frameTable = new JPanel();
		containTable = new JScrollPane(frameTable); 
		
		UIManager.put("OptionPane.messageFont", paneFont);
		UIManager.put("OptionPane.buttonFont", paneFont);
		
		menuBar = new JMenuBar();
		modeMenu = new JMenu("File");
		modeMenu.setFont(labelFont);
		functionMenu = new JMenu("Function");
		functionMenu.setFont(labelFont);
		helpMenu = new JMenu("Help");
		helpMenu.setFont(labelFont);
		OpenFile openfile = new OpenFile();
		SaveFile savefile = new SaveFile();
		openMenuItem = new JMenuItem("Open");
		openMenuItem.setFont(labelFont);
		openMenuItem.addActionListener(openfile);
		saveMenuItem = new JMenuItem("Save");
		saveMenuItem.setFont(labelFont);
		saveMenuItem.addActionListener(savefile);
		updatefunction = new JMenuItem("Update");
		updatefunction.setFont(labelFont);
		updatefunction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (personName.isEmpty()) {
					JOptionPane.showMessageDialog(null, 
						"There is no file can be updated", "Warning", 
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				updatePersonLeave();
			}
		});
		helpMenuItem = new JMenuItem("About");
		helpMenuItem.setFont(labelFont);
		helpMenuItem.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JOptionPane.showOptionDialog(null, "簡介:\n    "
						+ "請將 excel 存成 csv 檔案再開啟\n    使用路徑 + 檔名.csv\n"
						+ "路徑範例:\n    C:/Users/U2/Desktop/test.csv", 
						"關於  -- by obelisk0114", JOptionPane.DEFAULT_OPTION,
						JOptionPane.INFORMATION_MESSAGE, null, null, null);
					}
				}
				);
		
		//ArrayList<List<String>> dataTable = readCSVToArrayList("C:/Users/TEMP/Desktop/test.csv");
		//ArrayList<List<String>> dataTable = readCSVToArrayList("C:/Users/15T-J000/Desktop/test.csv");
	}
	
	public ArrayList<List<String>> readCSVToArrayList (String csvpath) {
		ArrayList<List<String>> Table = new ArrayList<List<String>>();
		
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(csvpath));
         // reader.readLine(); If you don't want to save the first row, add this line.
			String line = null;  // 暫存用(測試是否已讀完檔)
			
			 // 讀取資料, 當讀完最後一行時, 再讀取就會傳回 null
            while ((line = reader.readLine()) != null) {
                
                //存放每一列資料內容(橫的)
                ArrayList<String> ticketStr = new ArrayList<String>();
                
                String[] item = line.split(",");  //csv文件為依據逗號切割
                
                //清除上一次存入的資料
                ticketStr.clear();
                
                //System.out.println(item.length);
                // Read file (single data)
                for(int i = 0; i < item.length; i++){
                    
                    ticketStr.add(item[i]);
                    
//                  System.out.println(ticketStr.get(i));
                }
                
                Table.add(ticketStr);
            }
            
            reader.close();
			
		} catch (FileNotFoundException fe) {
			JOptionPane.showMessageDialog(null, 
				"The system cannot find the path specified.", "Warning", 
					JOptionPane.ERROR_MESSAGE);
		} catch (IOException ie) {
			ie.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// print table for test
		printTable(Table);
		
		return Table;
	}
	
	public void filledEmptySpace(ArrayList<List<String>> inputTable) {
		maxSize = inputTable.get(0).size();
		boolean reRun = false;
		
		for (int i = 1; i < inputTable.size(); i++) {
			
			// If the i_th row is smaller than the first row, fill it with "". 
			if (inputTable.get(i).size() < maxSize) {
				for (int j = inputTable.get(i).size(); j < maxSize; j++) {
					inputTable.get(i).add("");
				}				
			}
			
			// If the i_th row is larger than the first row, fill the first row.
			else if (inputTable.get(i).size() > maxSize) {
				for (int j = maxSize; j < inputTable.get(i).size(); j++) {
					inputTable.get(0).add("Missing");
				}
				
				maxSize = inputTable.get(i).size();
				reRun = true;
				break;
			}
		}
		
		if (reRun == true)
			filledEmptySpace(inputTable);
	}
	
	public void setTable(ArrayList<List<String>> inputTable) {
		boolean firstTag = false;
		for (int i = 0; i < inputTable.get(0).size(); i++) {
			String element = inputTable.get(0).get(i);
			if (element.equals(null)) {
				element = "";
			}
			
			if (element.contains("假") && firstTag == false) {
				tag = i;
				firstTag = true;
			}
			
			item.add(new JLabel(element, SwingConstants.CENTER));
		}
		
		for (int i = 1; i < inputTable.size(); i++) {
			personName.add(new JLabel(inputTable.get(i).get(0), 
					SwingConstants.CENTER));
			
			List<JButton> personLeaveRow = new ArrayList<JButton>();
			List<JButton> personRow = new ArrayList<JButton>(31);
			
			for (int j = 1; j < inputTable.get(i).size(); j++) {
				String tempLeaveData = inputTable.get(i).get(j);
				if (tempLeaveData.equals(null)) {
					tempLeaveData = "";
				}
				
				if (j >= tag && tag != -1) {	
					personLeaveRow.add(new JButton(tempLeaveData));
				}
				else {
					personRow.add(new JButton(tempLeaveData));
				}
			}
			
			personLeave.add(personLeaveRow);
			person.add(personRow);
		}
	}
	
	public void paintTable(ArrayList<List<String>> inputTable) {
		frameTable.setLayout(new GridBagLayout());
		Font font = new Font("Serif", Font.BOLD, 15);
		int gridRecordX = 1;
		int gridRecordY = 2;
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0,0,0,0);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.weighty = 1;
		if (item.get(0).equals("")) {
			gbc.ipadx = 10;
			gbc.ipady = 10;
			gbc.gridwidth = gridRecordX;
		    gbc.gridheight = gridRecordY;
		}
		else {
			gridRecordX = 2;
			gridRecordY = 4;
			gbc.ipadx = 10;
			gbc.ipady = 10;
			gbc.gridwidth = gridRecordX;
		    gbc.gridheight = gridRecordY;
		}
		frameTable.add(item.get(0), gbc);
		item.get(0).setVisible(true);
		
		for (int i = 1; i < item.size(); i++) {
			gbc.weightx = 1;
			gbc.weighty = 1;
			gbc.gridwidth = 2;
			gbc.gridheight = 2;
			gbc.ipadx = 10;
			gbc.ipady = 10;
			gbc.gridx = gridRecordX + (i - 1) * 2;
			gbc.gridy = 0;
			
			item.get(i).setFont(font);
			frameTable.add(item.get(i), gbc);
			item.get(i).setVisible(true);
		}

		for (int i = 0; i < personName.size(); i++) {
			gbc.weightx = 1;
			gbc.weighty = 1;
			gbc.gridwidth = 2;
			gbc.gridheight = 2;
			gbc.ipadx = 10;
			gbc.ipady = 10;
			gbc.gridx = 0;
			gbc.gridy = gridRecordY + i * 2;
			
			personName.get(i).setFont(font);
		    frameTable.add(personName.get(i), gbc);
		    personName.get(i).setVisible(true);
		}

		int controlSize = tag;
		boolean tagCondition = true;
		if (controlSize < 0) {
			controlSize = maxSize;
			tagCondition = false;
		}
		System.out.println("tag : " + tag);
		System.out.println("maxSize : " + maxSize);
		System.out.println("controlSize : " + controlSize);
		
		for (int i = 0; i < inputTable.size() - 1; i++) {
			for (int j = 0; j < controlSize - 1; j++) {
				gbc.weightx = 1;
				gbc.weighty = 1;
				gbc.gridwidth = 2;
				gbc.gridheight = 2;
				gbc.ipadx = 10;
				gbc.ipady = 10;
				gbc.gridx = gridRecordX + j * 2;
				gbc.gridy = gridRecordY + i * 2;
				
				person.get(i).get(j).setFont(font);
			    frameTable.add(person.get(i).get(j), gbc);
			    person.get(i).get(j).setVisible(true);
				//person.get(i).get(j).addActionListener(l);
			}
		}

		if (tagCondition == true) {
			for (int i = 0; i < inputTable.size() - 1; i++) {
				for (int j = 0; j < maxSize - controlSize; j++) {
					gbc.weightx = 1;
					gbc.weighty = 1;
					gbc.gridwidth = 2;
					gbc.gridheight = 2;
					gbc.ipadx = 10;
					gbc.ipady = 10;
					gbc.gridx = gridRecordX + (j + tag - 1) * 2;
					gbc.gridy = gridRecordY + i * 2;
					
					personLeave.get(i).get(j).setFont(font);
				    frameTable.add(personLeave.get(i).get(j), gbc);
				    personLeave.get(i).get(j).setEnabled(false);
				    personLeave.get(i).get(j).setVisible(true);
				}
			}
		}
	}
	
	public void updatePersonLeave() {				
		for (int i = 0; i < person.size(); i++) {
			Map<String, Integer> leaveMap = new HashMap<String, Integer>();
			
			for (int j = 0; j < person.get(i).size(); j++) {
				String leaveItem = person.get(i).get(j).getText();
				
				if (leaveItem != "") {	
					if (leaveMap.containsKey(leaveItem)) {
						Integer newValue = leaveMap.get(leaveItem);
						leaveMap.put(leaveItem, newValue + 1);
					}
					else {
						leaveMap.put(leaveItem, 1);
					}
				}
			}
			
			for (int k = 0; k < item.size() - tag; k++) {
				if (leaveMap.containsKey(item.get(k + tag).getText())) {
					int leaveValue = leaveMap.get(item.get(k + tag).getText());
					personLeave.get(i).get(k).setText(leaveValue + "");
				}
				else {
					personLeave.get(i).get(k).setText("");
				}
			}
		}
	}
	
	private class OpenFile implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			if (!personName.isEmpty()) {
				int option = JOptionPane.showConfirmDialog(mainFrame, 
					"Do you want to save the file ?",
					"Confirm", JOptionPane.YES_NO_CANCEL_OPTION);
				if (option == 2 || option == -1) {
					return;
				}
				else if (option == 0) {					
					SaveFile savefile = new SaveFile();
					savefile.actionPerformed(ae);
				}
			}
			String csvPath = JOptionPane
					.showInputDialog(mainFrame, "Enter your csv path, ex :",
						"C:/Users/U2/Desktop/test.csv");
			if (csvPath == null) {
				return;
			}
			ArrayList<List<String>> dataTable = readCSVToArrayList(csvPath);
			try {
				filledEmptySpace(dataTable);
				setTable(dataTable);
				paintTable(dataTable);
			} catch (Exception e) {
				return;
			}
			mainFrame.setVisible(true);
		}
	}
	
	private class SaveFile implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			if (personName.isEmpty()) {
				JOptionPane.showMessageDialog(null, 
					"There is no file can be saved", "Warning", 
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			String csvPath = JOptionPane.showInputDialog(mainFrame, 
				"Enter the path that you want to save the file, ex :",
					"C:/Users/U2/Desktop/test.csv");
			if (csvPath == null) {
				return;
			}
		}
	}
	
	public void launchFrame() {
		mainFrame.setSize(width, height);
		mainFrame.add(menuBar);
		menuBar.add(modeMenu);
		menuBar.add(functionMenu);
		menuBar.add(helpMenu);
		modeMenu.add(openMenuItem);
		modeMenu.add(saveMenuItem);
		functionMenu.add(updatefunction);
		helpMenu.add(helpMenuItem);
		
		mainFrame.add(containTable);
		mainFrame.setJMenuBar(menuBar);
		mainFrame.setLocation(350, 150);
		mainFrame.setVisible(true);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void printTable(ArrayList<List<String>> inputTable) {
		for (int i = 0; i < inputTable.size(); i++) {
			for (int j = 0; j < inputTable.get(i).size(); j++) {
				System.out.print(inputTable.get(i).get(j));
				System.out.print("             ");
				if (j == inputTable.get(i).size() -1 )
					System.out.println();
					
			}
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Admin ad = new Admin("請假管理系統");
		ad.launchFrame();

	}

}

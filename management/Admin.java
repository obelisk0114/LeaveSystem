package management;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JPopupMenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;

import java.util.List;
import java.util.ArrayList;

int maxSize = -1;

public class Admin {
	final int width = 900;
	final int height = 550;
	
	private JFrame mainFrame;
	private JPanel totalTable, headerTable, staffTable;
	private JScrollPane containTable;
	private List<JLabel> item = new ArrayList<JLabel>(50);
	private ArrayList<List<JLabel>> personLeave = new ArrayList<List<JLabel>>();
	private ArrayList<List<JButton>> person = new ArrayList<List<JButton>>();
	
	public Admin(String title) {
		// Run
		mainFrame = new JFrame(title);
		totalTable = new JPanel();
		headerTable = new JPanel();
		staffTable = new JPanel();
		containTable = new JScrollPane(totalTable); 
		
		//ArrayList<List<String>> dataTable = readCSVToArrayList("C:/Users/TEMP/Desktop/test.csv");
		ArrayList<List<String>> dataTable = readCSVToArrayList("C:/Users/15T-J000/Desktop/test.csv");
		filledEmptySpace(dataTable);
	}
	
	public ArrayList<List<String>> readCSVToArrayList (String csvpath) {
		ArrayList<List<String>> Table = new ArrayList<List<String>>();
		
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(csvpath));
//          reader.readLine();// 是否讀取第一行 (加上註解代表會讀取,註解拿掉不會讀取)
			String line = null;// 暫存用(測試是否已讀完檔)
			
			 // read data
            while ((line = reader.readLine()) != null) {
                
                //存放每一列資料內容(橫的)
                ArrayList<String> ticketStr = new ArrayList<String>();
                
                String[] item = line.split(",");//csv文件為依據逗號切割
                
                //清除上一次存入的資料
                ticketStr.clear();
                
                //System.out.println(item.length);
                //讀檔(單列資料)
                for(int i = 0; i < item.length; i++){
                    
                    ticketStr.add(item[i]);
                    
//                  System.out.println(ticketStr.get(i));
                }
                
                Table.add(ticketStr);
            }
            
            reader.close();
			
		} catch (FileNotFoundException fe) {
			fe.printStackTrace();
		} catch (IOException ie) {
			ie.printStackTrace();
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
	
	public void launchFrame() {
		mainFrame.setSize(width, height);
		
		totalTable.setLayout(new GridLayout(2, 1));
		totalTable.add(headerTable);
		totalTable.add(staffTable);
		totalTable.setVisible(true);
		
		mainFrame.add(containTable);
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

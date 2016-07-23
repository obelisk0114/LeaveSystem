package management;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPopupMenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;

import java.util.List;
import java.util.ArrayList;

public class Admin {
	final int width = 900;
	final int height = 800;
	
	private JFrame mainFrame;
	private JPanel mainTable;
	private List<JButton> person;
	
	public Admin(String title) {
		// Run
		readCSVToArrayList("C:/Users/TEMP/Desktop/test.csv");
	}
	
	public ArrayList<List<String>> readCSVToArrayList (String csvpath) {
		ArrayList<List<String>> dataTable = new ArrayList<List<String>>();
		
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(csvpath));
//          reader.readLine();// 是否讀取第一行 (加上註解代表會讀取,註解拿掉不會讀取)
			String line = null;// 暫存用(測試是否已讀完檔)
			
			 // read data
            while ((line = reader.readLine()) != null) {
                
                //存放每一列資料內容(橫的)
                ArrayList<String> ticketStr = new ArrayList<String>();
                
                String item[] = line.split(",");//csv文件為依據逗號切割
                
                //清除上一次存入的資料
                ticketStr.clear();
                
                //讀檔(單列資料)
                for(int i=0; i<item.length; i++){
                    
                    ticketStr.add(i, item[i]);
                    
//                    System.out.println(ticketStr.get(i));
                }
                
                dataTable.add(ticketStr);
            }
			
		} catch (FileNotFoundException fe) {
			fe.printStackTrace();
		} catch (IOException ie) {
			ie.printStackTrace();
		}
		
		// 測試
		for (int i = 0; i < dataTable.size(); i++) {
			for (int j = 0; j < dataTable.get(i).size(); j++) {
				System.out.print(dataTable.get(i).get(j));
				System.out.print("             ");
				if (j == dataTable.get(i).size() -1 )
					System.out.println();
					
			}
		}
		
		return dataTable;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Admin ad = new Admin("請假管理系統");

	}

}

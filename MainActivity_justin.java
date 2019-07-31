/**
 * 
 */
package justin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SpringLayout;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;

/**
 * @author James Fernandes
 * @version 1.0.0
 * @since 19/07/2019
 * @email jfernandes942@gmail.com
 *
 */
public class MainActivity_justin {

	/**
	 * @param args
	 */
	
	//global variables
	static String filePath;
	static String outputFilePath;
	static String attributeName = "data-tracking-datalayer";
	static Model excelModel = new Model();
	public static final List<Integer> passOrFail = new ArrayList<Integer>();
	static boolean headerRowCreated = false;
	public static Logger logger = Logger.getLogger(MainActivity_justin.class.getName());   
	 public static FileHandler fh;
	 static JFrame mainFrame;
	 static JButton downloadBtn;
	 static JLabel imgLabelPb;
	 static popupFrame popup = new popupFrame();
//	 static aTask t;
//	 static JFrame progressFrame;
	 
//	static SetFunctions setFunction= new SetFunctions();
	
	public static void main(String[] args) {
		
		/* SET UP START */
				
		// Setting up OS UI
		try {
	        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    }catch(Exception ex) {
	        ex.printStackTrace();
	    }
		
		// Setting up selenium webdriver for chrome
		String chromeDriverPath = "driver/chromedriver.exe";
		
		System.setProperty("webdriver.chrome.driver", chromeDriverPath);
		
		//Instance of chrome options
		ChromeOptions options = new ChromeOptions();
		
		//addding headless chrome option
		options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200", "--ignore-certificate-errors", "--silent");
		
		//instance of driver with chrome options
		WebDriver driver = new ChromeDriver(options);
		
		/* SET UP END */
		
		/* UI START */
		
		//creating main frame to browse the file
		mainFrame = new JFrame("Justin"); //mainFrame with title
		mainFrame.setSize(450, 450); //Size of mainframe
		mainFrame.getContentPane().setBackground(Color.WHITE); //setting background color
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setLayout(new BorderLayout()); //layout of mainframe
		mainFrame.setLocationRelativeTo(null); //setting mainframe to center
		mainFrame.setResizable(false);
		
		Container container = mainFrame.getContentPane(); //getting mainframe container
		
		//top panel contents starts from here
		
		Container topPane = new Container();
		topPane.setLayout(new BoxLayout(topPane, BoxLayout.X_AXIS));
		
		JLabel imgLabel = new JLabel(new ImageIcon("img/Accenture-main-logo.jpg"));
		
		JLabel header = new JLabel("J.U.S.T.I.N"); //Java Used Selenium Triggered Internally
		header.setFont(new Font("Serif", Font.BOLD, 34));
		header.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		topPane.add(imgLabel);
		topPane.add(header);
		
		container.add(topPane, BorderLayout.PAGE_START);
		
		//middle panel contents starts from here
		
		Container middlePane = new Container(); //adding container in middle pane
		SpringLayout layout = new SpringLayout(); //setting layout of middle pane
		middlePane.setLayout(layout);
		
		JLabel selectExcel = new JLabel("Add Excel Sheet:"); //label for browse button
		JButton browseBtn = new JButton("Browse");
		JLabel selectedExcel = new JLabel(); //show selected excel file name
		JButton runTest = new JButton("Run Test");
		downloadBtn = new JButton("Download");
		downloadBtn.setVisible(false);

		ImageIcon ii = new ImageIcon("img/loader2.gif");
		imgLabelPb = new JLabel();
		imgLabelPb.setIcon(ii);
		imgLabelPb.setSize(100, 100);
		imgLabelPb.setVisible(false);
		
		// adding all components to container
		middlePane.add(selectExcel);
		middlePane.add(browseBtn);
		middlePane.add(selectedExcel);
		middlePane.add(runTest);
		middlePane.add(downloadBtn);
		middlePane.add(imgLabelPb, java.awt.BorderLayout.CENTER);
		
		//setting left margin between components and container
		layout.putConstraint(SpringLayout.WEST, selectExcel, 100, SpringLayout.WEST, middlePane);
		layout.putConstraint(SpringLayout.WEST, browseBtn, 99, SpringLayout.WEST, middlePane);
		layout.putConstraint(SpringLayout.WEST, selectedExcel, 99, SpringLayout.WEST, middlePane);
		layout.putConstraint(SpringLayout.WEST, runTest, 99, SpringLayout.WEST, middlePane);
//		layout.putConstraint(SpringLayout.WEST, progressBar, 99, SpringLayout.WEST, middlePane);
		layout.putConstraint(SpringLayout.WEST, downloadBtn, 175, SpringLayout.WEST, middlePane);
		
		layout.putConstraint(SpringLayout.WEST, imgLabelPb, 30, SpringLayout.WEST, middlePane);
		
		// top margin between components and container
		layout.putConstraint(SpringLayout.NORTH, selectExcel, 15, SpringLayout.NORTH, middlePane);
		layout.putConstraint(SpringLayout.NORTH, browseBtn, 40, SpringLayout.NORTH, middlePane);
		layout.putConstraint(SpringLayout.NORTH, selectedExcel, 44, SpringLayout.NORTH, middlePane);
		layout.putConstraint(SpringLayout.NORTH, runTest, 70, SpringLayout.NORTH, middlePane);
//		layout.putConstraint(SpringLayout.NORTH, progressBar, 74, SpringLayout.NORTH, middlePane);
		layout.putConstraint(SpringLayout.NORTH, downloadBtn, 70, SpringLayout.NORTH, middlePane);
		
		layout.putConstraint(SpringLayout.NORTH, imgLabelPb, 110, SpringLayout.NORTH, middlePane);
		
		//putting components next to each other, setting selectExcel to left of browse button
		layout.putConstraint(SpringLayout.WEST, selectedExcel, 9, SpringLayout.EAST, browseBtn);
//		layout.putConstraint(SpringLayout.WEST, progressBar, 9, SpringLayout.EAST, runTest);
		layout.putConstraint(SpringLayout.WEST, downloadBtn, 9, SpringLayout.EAST, runTest);
		
		container.add(middlePane, BorderLayout.CENTER);
		
		//middle panel contents ends here
		
		mainFrame.setVisible(true);
				
		/* UI END */
		
		/* Button Actions STARTS */
		
		//adding actions to buttons
		browseBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// opening file browser
				
				downloadBtn.setVisible(false);
				
				
				JFileChooser choose = new JFileChooser();
				
				int r = choose.showOpenDialog(new JFrame());
				
				
				if(r == choose.APPROVE_OPTION) {
					filePath = choose.getSelectedFile().getAbsolutePath();
					if(filePath != "") {
						selectedExcel.setText(choose.getSelectedFile().getName());
					}else {
						System.out.println("No File Selected");
					}
				}
			}
			
		});
		
		runTest.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {
				// starting the process of checking links
//				showPopupFrame();
				if(evt.getSource() == runTest) {
	                new ControlInterface();
	            }
//				popup.showPopup();
				try {
					checkDataTags(filePath, driver);
					
				} catch (SecurityException | IOException e) {
					
					e.printStackTrace();
				}
//				popup.hidePopup();
				driver.close();
			}
			
		});		
		
		downloadBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Download Option
		        JFileChooser fileChooser = new JFileChooser();
		        
		        fileChooser.setDialogTitle("Save file");
		        
		        int userSelection = fileChooser.showSaveDialog(fileChooser);
		        if (userSelection == JFileChooser.APPROVE_OPTION) {
		            outputFilePath = fileChooser.getSelectedFile().getAbsolutePath()+".xlsx";
		        }
		        
		        File file = new File(outputFilePath);
		        
		        if (file.exists() == false) {
		        	File tempFile = new File("tempFile/temp.xlsx");
		        	try {
						copyFileUsingStream(tempFile, file);
						JOptionPane.showMessageDialog(null, "Completed");
					} catch (IOException e) {
						e.printStackTrace();
					}
		        } else {
		            // Sheet already exists
		        	JOptionPane.showMessageDialog(null, "File already exist");
		            System.out.println("File already exist");
		        }			
			}
			
		});
		
		/* Button Actions STOPS*/
				
	}


	protected static void checkDataTags(String excelPath, WebDriver driver) throws SecurityException, IOException {
		
		// TODO find analytics tag and get JSON object. Check if JSON object asset_title matches with the url row asset_title in excel
		// TODO If matches then check all the attributes for that row with JSON object
		// TODO if any attribute does not match with json object then highlight that column and in the end put failed
		// TODO if everything matches, show passed.
		try {
			
			File source = new File(excelPath);
	        File dest = new File("tempFile/temp.xlsx");
			
			copyFileUsingStream(source, dest);
			
//			showPopupFrame();
			
			FileInputStream fs = new FileInputStream(excelPath);
			XSSFWorkbook workbook = new XSSFWorkbook(fs);
			XSSFSheet sheet = workbook.getSheetAt(0);
			
			//For output purpose
			FileInputStream outputStream = new FileInputStream("tempFile/temp.xlsx");
	        XSSFWorkbook outputWorkbook = new XSSFWorkbook(outputStream);
	        XSSFSheet outputSheet = outputWorkbook.getSheetAt(0);
	        
	        XSSFCellStyle style = outputWorkbook.createCellStyle();
		    XSSFColor my_background=new XSSFColor(Color.RED);
		    style.setFillForegroundColor(my_background);
		    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
						
			// checking and getting the actual last row number
			excelModel.setLastRowNumber(sheet);
			
			int actualLastRowNum = excelModel.getLastRowNumber();
			
			// looping all the rows from row 1 for url
			for(int i = 1; i <= actualLastRowNum; i++) {
				Row row = sheet.getRow(i);
				Row outputRow = outputSheet.getRow(i);
				
				if(row != null) {
					Cell urlCell = row.getCell(0); //url cell
					
					if(urlCell != null) {
						String url = urlCell.getStringCellValue().trim();
						
						boolean isPreview = url.contains("epublishmerck"); // checking if preview or no
						
						if(isPreview) {
							String previewUrl = url.replace("https://", "https://epublishuser:epublishpassword@");
//							driver.get(previewUrl);
							check(driver, i, row, sheet, previewUrl, outputRow, style);
						}else {
//							driver.get(url);
							check(driver, i, row, sheet, url, outputRow, style);
						}

					}else {
						System.out.println("Line 202: Cell is empty");
					}
				}else {
					System.out.println("Line 203: Row is empty");
				}
				
			}
			headerRowCreated = false;
			workbook.close();
			fs.close();
			
			outputStream.close();
			
	        FileOutputStream outputFile = new FileOutputStream(new File("tempFile/temp.xlsx"));
	        outputWorkbook.write(outputFile);
	        outputFile.close();
	        outputWorkbook.close();
	        
	        
	        downloadBtn.setVisible(true);
	        //download code ends
//	        JOptionPane.showMessageDialog(null, "Completed Successfully");
	        
//	        System.exit(0);
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Some error");
			fh = new FileHandler("logs/LogFile.log");  
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();  
            fh.setFormatter(formatter);  

           // the following statement is used to log any messages
           logger.info("" + e.getLocalizedMessage());
			e.printStackTrace();
		}
	}

	
	private static void showPopupFrame() {
		
		JFrame frame = new JFrame("Please wait..");
		
		
		JPanel panel = new JPanel();
		
		panel.setLayout(new FlowLayout());
		
		panel.add(new JButton("Testing"));
		
		frame.add(panel);
		
		frame.setSize(300, 150);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		frame.setVisible(true);
	}
	


	private static void copyFileUsingStream(File source, File dest) throws IOException {
		InputStream is = null;
	    OutputStream os = null;
	    try {
	        is = new FileInputStream(source);
	        os = new FileOutputStream(dest);
	        byte[] buffer = new byte[1024];
	        int length;
	        while ((length = is.read(buffer)) > 0) {
	            os.write(buffer, 0, length);
	        }
	    } finally {
	        is.close();
	        os.close();
	    }
	    System.out.println("Copied Successfully");
	}

	private static void check(WebDriver driver, int i, Row currentRow, XSSFSheet currentSheet, String url, Row outputRow, XSSFCellStyle style) throws IOException {
		System.out.println(url);
		driver.get(url);
		
		List<WebElement> links = driver.findElements(By.tagName("a")); //taking all links for current row url ex- row1 url
		
		if(links.size() > 0) {
			//looping through all links to get analytics
			
			for(int j = 0; j < links.size(); j++) {
				
				// check if analytics attribute is null or no
				if(links.get(j).getAttribute(attributeName) != null) {
					// if not null then get analytics object
					JSONObject analyticsObject = new JSONObject(links.get(j).getAttribute(attributeName));
					
					// getting header Row
					Row headerRow = currentSheet.getRow(0);
					String headerCell_assetTitle = headerRow.getCell(1).getStringCellValue().trim(); //this should give asset_title
					String assetTitleValue_fromWeb = null;
					try {
						assetTitleValue_fromWeb = analyticsObject.getString(headerCell_assetTitle); //this will give value against key asset_title ex - Asmanex HFA products overview
					}catch(JSONException e) {
						//asset_title key not in json
						e.getStackTrace();
					}
					
					String assetTitleValue_fromExcel = currentRow.getCell(1).getStringCellValue().trim(); //getting the value of currentRow cell 1
					
					if(assetTitleValue_fromWeb != null) {
						//checking if the value of asset_title is same as value of asset_title from web
						if(assetTitleValue_fromExcel.equals(assetTitleValue_fromWeb)) {
							// function to get last cell count of headerRow
							excelModel.setLastCellNumber(headerRow);
							
							int actualLastCellNum = excelModel.getLastCellNumber();
							
							//looping through cell 1 to end of cells and checking each value with web value, setting color if not equal
							for(int k = 1; k <= actualLastCellNum; k++) {
								String headerCellKey = headerRow.getCell(k).getStringCellValue().trim(); //currentCell key
								
								String currentRowCellValue = currentRow.getCell(k).getStringCellValue().trim(); //currentCell value
								
								String analyticsKeyValue = analyticsObject.getString(headerCellKey).trim(); //this will give key's value
								
								if(currentRowCellValue.equals(analyticsKeyValue)) {
									System.out.println(headerCellKey);
									System.out.println(currentRowCellValue + " is equal to "+ analyticsKeyValue);
									passOrFail.add(1);
								}else {
									System.out.println(headerCellKey);
									System.out.println(currentRowCellValue + " is not equal to "+ analyticsKeyValue);
									passOrFail.add(0);
									Cell failedCell = outputRow.getCell(k);
									failedCell.setCellStyle(style);
								}
							}
							//check passOrFail array & make it empty at the end
							headerRowCreated = true;
							if(headerRowCreated) {
								outputRow.getSheet().getRow(0).createCell(actualLastCellNum+1).setCellValue("Status");
//								headerRow.createCell(actualLastCellNum+1).setCellValue("Status");
							}
							
							if(passOrFail.contains(0)) {
								outputRow.createCell(actualLastCellNum+1).setCellValue("Failed");
							}else {
								outputRow.createCell(actualLastCellNum+1).setCellValue("Passed");
							}
							
							passOrFail.clear();
							
							break;
						}
					}		
				}
			}
			
		}else {
			System.out.println("Couldn't get any links from url");
		}
		
	}

}

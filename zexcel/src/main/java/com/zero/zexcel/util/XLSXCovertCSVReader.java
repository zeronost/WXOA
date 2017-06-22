package com.zero.zexcel.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public class XLSXCovertCSVReader {
	/**
	 * The type of the data value is indicated by an attribute on the cell. The
	 * value is usually in a "v" element within the cell.
	 */
	enum xssfDataType {
		BOOL, ERROR, FORMULA, INLINESTR, SSTINDEX, NUMBER,
	}

	/**
	 * 使用xssf_sax_API处理Excel,请参考：
	 * http://poi.apache.org/spreadsheet/how-to.html#xssf_sax_api
	 * <p/>
	 * Also see Standard ECMA-376, 1st edition, part 4, pages 1928ff, at
	 * http://www.ecma-international.org/publications/standards/Ecma-376.htm
	 * <p/>
	 * A web-friendly version is http://openiso.org/Ecma/376/Part4
	 */
	class MyXSSFSheetHandler extends DefaultHandler {

		/**
		 * Table with styles
		 */
		private StylesTable stylesTable;

		/**
		 * Table with unique strings
		 */
		private ReadOnlySharedStringsTable sharedStringsTable;

		/**
		 * Destination for data
		 */
		private final PrintStream output;

		/**
		 * Number of columns to read starting with leftmost
		 */
		private final int minColumnCount;

		// Set when V start element is seen
		private boolean vIsOpen;

		// Set when cell start element is seen;
		// used when cell close element is seen.
		private xssfDataType nextDataType;

		// Used to format numeric cell values.
		private short formatIndex;
		private String formatString;
		private final DataFormatter formatter;

		private int thisColumn = -1;
		// The last column printed to the output stream
		private int lastColumnNumber = -1;

		// Gathers characters as they are seen.
		private StringBuffer value;
		private String[] record;
		private List<String[]> rows = new ArrayList<String[]>();

		private boolean isCellNull = false;

		/**
		 * Accepts objects needed while parsing.
		 * 
		 * @param styles
		 *            Table of styles
		 * @param strings
		 *            Table of shared strings
		 * @param cols
		 *            Minimum number of columns to show
		 * @param target
		 *            Sink for output
		 */
		public MyXSSFSheetHandler(StylesTable styles, ReadOnlySharedStringsTable strings, int cols,
				PrintStream target) {
			this.stylesTable = styles;
			this.sharedStringsTable = strings;
			this.minColumnCount = cols;
			this.output = target;
			this.value = new StringBuffer();
			this.nextDataType = xssfDataType.NUMBER;
			this.formatter = new DataFormatter();
			record = new String[this.minColumnCount];
			rows.clear();// 每次读取都清空行集合
		}

		public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {

			if ("inlineStr".equals(name) || "v".equals(name)) {
				vIsOpen = true;
				value.setLength(0);
			}
			// c => cell
			else if ("c".equals(name)) {
				// Get the cell reference
				String r = attributes.getValue("r");
				int firstDigit = -1;
				for (int c = 0; c < r.length(); ++c) {
					if (Character.isDigit(r.charAt(c))) {
						firstDigit = c;
						break;
					}
				}
				thisColumn = nameToColumn(r.substring(0, firstDigit));

				// Set up defaults.
				this.nextDataType = xssfDataType.NUMBER;
				this.formatIndex = -1;
				this.formatString = null;
				String cellType = attributes.getValue("t");
				String cellStyleStr = attributes.getValue("s");
				if ("b".equals(cellType))
					nextDataType = xssfDataType.BOOL;
				else if ("e".equals(cellType))
					nextDataType = xssfDataType.ERROR;
				else if ("inlineStr".equals(cellType))
					nextDataType = xssfDataType.INLINESTR;
				else if ("s".equals(cellType))
					nextDataType = xssfDataType.SSTINDEX;
				else if ("str".equals(cellType))
					nextDataType = xssfDataType.FORMULA;
				else if (cellStyleStr != null) {
					// It's a number, but almost certainly one
					// with a special style or format
					int styleIndex = Integer.parseInt(cellStyleStr);
					XSSFCellStyle style = stylesTable.getStyleAt(styleIndex);
					this.formatIndex = style.getDataFormat();
					this.formatString = style.getDataFormatString();
					if (this.formatString == null)
						this.formatString = BuiltinFormats.getBuiltinFormat(this.formatIndex);
				}
			}

		}

		public void endElement(String uri, String localName, String name) throws SAXException {

			String thisStr = null;

			// v => contents of a cell
			if ("v".equals(name)) {
				switch (nextDataType) {

				case BOOL:
					char first = value.charAt(0);
					thisStr = first == '0' ? "FALSE" : "TRUE";
					break;

				case ERROR:
					thisStr = "\"ERROR:" + value.toString() + '"';
					break;

				case FORMULA:
					thisStr = value.toString();
					break;

				case INLINESTR:
					XSSFRichTextString rtsi = new XSSFRichTextString(value.toString());
					thisStr = rtsi.toString();
					break;

				case SSTINDEX:
					String sstIndex = value.toString();
					try {
						int idx = Integer.parseInt(sstIndex);
						XSSFRichTextString rtss = new XSSFRichTextString(sharedStringsTable.getEntryAt(idx));
						thisStr = rtss.toString();
					} catch (NumberFormatException ex) {
						output.println("Failed to parse SST index '" + sstIndex + "': " + ex.toString());
					}
					break;

				case NUMBER:
					String n = value.toString();
					if (HSSFDateUtil.isADateFormat(this.formatIndex, n)) {
						Double d = Double.parseDouble(n);
						Date date = HSSFDateUtil.getJavaDate(d);
						thisStr = formateDateToString(date);
					} else if (this.formatString != null)
						thisStr = formatter.formatRawCellContents(Double.parseDouble(n), this.formatIndex,
								this.formatString);
					else
						thisStr = n;
					break;

				default:
					thisStr = "(TODO: Unexpected type: " + nextDataType + ")";
					break;
				}

				if (lastColumnNumber == -1) {
					lastColumnNumber = 0;
				}
				if (thisStr == null || "".equals(isCellNull)) {
					isCellNull = true;
				}
				record[thisColumn] = thisStr;
				if (thisColumn > -1)
					lastColumnNumber = thisColumn;

			} else if ("row".equals(name)) {
				if (minColumns > 0) {
					if (lastColumnNumber == -1) {
						lastColumnNumber = 0;
					}
					if (isCellNull == false)
					{
						rows.add(record.clone());
						isCellNull = false;
						for (int i = 0; i < record.length; i++) {
							record[i] = null;
						}
					}
				}
				lastColumnNumber = -1;
			}

		}

		public List<String[]> getRows() {
			return rows;
		}

		public void setRows(List<String[]> rows) {
			this.rows = rows;
		}

		/**
		 * Captures characters only if a suitable element is open. Originally
		 * was just "v"; extended for inlineStr also.
		 */
		public void characters(char[] ch, int start, int length) throws SAXException {
			if (vIsOpen)
				value.append(ch, start, length);
		}

		/**
		 * Converts an Excel column name like "C" to a zero-based index.
		 * 
		 * @param name
		 * @return Index corresponding to the specified name
		 */
		private int nameToColumn(String name) {
			int column = -1;
			for (int i = 0; i < name.length(); ++i) {
				int c = name.charAt(i);
				column = (column + 1) * 26 + c - 'A';
			}
			return column;
		}

		private String formateDateToString(Date date) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化日期
			return sdf.format(date);

		}

	}

	private OPCPackage xlsxPackage;
	private int minColumns;
	private PrintStream output;

	/**
	 * Creates a new XLSX -> CSV converter
	 * 
	 * @param pkg
	 *            The XLSX package to process
	 * @param output
	 *            The PrintStream to output the CSV to
	 * @param minColumns
	 *            The minimum number of columns to output, or -1 for no minimum
	 */
	public XLSXCovertCSVReader(OPCPackage pkg, PrintStream output, int minColumns) {
		this.xlsxPackage = pkg;
		this.output = output;
		this.minColumns = minColumns;
	}

	/**
	 * Parses and shows the content of one sheet using the specified styles and
	 * shared-strings tables.
	 * 
	 * @param styles
	 * @param strings
	 * @param sheetInputStream
	 */
	public List<String[]> processSheet(StylesTable styles, ReadOnlySharedStringsTable strings,
			InputStream sheetInputStream) throws IOException, ParserConfigurationException, SAXException {

		InputSource sheetSource = new InputSource(sheetInputStream);
		SAXParserFactory saxFactory = SAXParserFactory.newInstance();
		SAXParser saxParser = saxFactory.newSAXParser();
		XMLReader sheetParser = saxParser.getXMLReader();
		MyXSSFSheetHandler handler = new MyXSSFSheetHandler(styles, strings, this.minColumns, this.output);
		sheetParser.setContentHandler(handler);
		sheetParser.parse(sheetSource);
		return handler.getRows();
	}

	/**
	 * @throws IOException
	 * @throws OpenXML4JException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 */
	public Collection<Object> process()
			throws IOException, OpenXML4JException, ParserConfigurationException, SAXException {
		ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(this.xlsxPackage);
		XSSFReader xssfReader = new XSSFReader(this.xlsxPackage);
		StylesTable styles = xssfReader.getStylesTable();
		XSSFReader.SheetIterator iter = (XSSFReader.SheetIterator) xssfReader.getSheetsData();
		Collection<Object> rs = new ArrayList<Object>();
		List<String[]> list = null;
		while (iter.hasNext()) {
			InputStream stream = iter.next();
			list = processSheet(styles, strings, stream);
			stream.close();
			if (list != null && !list.isEmpty())
				rs.add(list);
		}
		return rs;
	}

	/**
	 * 读取Excel
	 * 
	 * @param path
	 *            文件路径
	 * @param sheetName
	 *            sheet名称
	 * @param minColumns
	 *            列总数
	 * @return
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 * @throws OpenXML4JException
	 * @throws IOException
	 */
	public static Collection<Object> readExcel(File file, int minColumns) throws Exception {
		OPCPackage p = OPCPackage.open(file, PackageAccess.READ);
		XLSXCovertCSVReader xlsx2csv = new XLSXCovertCSVReader(p, System.out, minColumns);
		Collection<Object> rs = xlsx2csv.process();
		p.close();
		return rs;
	}

	public static boolean createExcelFile(String path, Collection<Object> data,List<String> header, int maxSize) {
		boolean isCreateSuccess = false;
		SXSSFWorkbook workbook = null;
		try {
			workbook = new SXSSFWorkbook(maxSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (workbook != null) {
			int index = 1;
			for (Object sheet : data) {
				createSheetAndFill(workbook, sheet, header, "matcheResult " + index);
				index++;
			}
			try {
				FileOutputStream outputStream = new FileOutputStream(path);
				workbook.write(outputStream);
				outputStream.flush();
				outputStream.close();
				isCreateSuccess = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return isCreateSuccess;
	}

	@SuppressWarnings("unchecked")
	private static void createSheetAndFill(Workbook workbook, Object data,List<String> header, String name) {
		Sheet sheet = workbook.createSheet(name);
		List<Map<String,StringBuilder>> list = (List<Map<String,StringBuilder>>) data;
		createHeader(sheet, header);
		for (int r = 0; r < list.size(); r++) {
			if(list.get(r) == null)
				continue;
			Row row = sheet.createRow(r+1);
			for(int c = 0; c < header.size(); c++){
				StringBuilder cusor = list.get(r).get(header.get(c));
				if(cusor == null)
					continue;
				Cell cell = row.createCell(c, CellType.STRING);
				cell.setCellValue(cusor.toString());
			}
		}
	}
	
	private static void createHeader(Sheet sheet, List<String> header){
		Row row = sheet.createRow(0);
		CellStyle style = sheet.getWorkbook().createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setFillForegroundColor((short) 13);
		for(int c = 0; c < header.size(); c++){
			Cell cell = row.createCell(c,CellType.STRING);
			cell.setCellStyle(style);
			cell.setCellValue(header.get(c));
		}
	}
}

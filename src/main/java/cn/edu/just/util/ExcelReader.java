package cn.edu.just.util;

import cn.edu.just.pojo.Company;
import cn.edu.just.pojo.Student;
import cn.edu.just.pojo.Teacher;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Excel解析工具类
 */
public class ExcelReader {
    private static Workbook wb;
    private static Sheet sheet;
    private static Row row;

    /**
     * 从Cell中获取对应类型的值的字符串
     * @param cell
     * @return
     */
    // TODO 识别excel中的列名
    private static String getCellFormatValue(Cell cell) {
        String cellValue = "";
        if (cell != null) {
            // 判断当前Cell的Type
            switch (cell.getCellType()) {
                // 如果当前Cell的Type为NUMERIC
                case Cell.CELL_TYPE_NUMERIC:
                    cellValue = String.valueOf(Math.round(cell.getNumericCellValue()));
                    break;
                case Cell.CELL_TYPE_FORMULA: {
                    // 判断当前的cell是否为Date
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        // 方法2：这样子的data格式是不带带时分秒的：2011-10-12
                        Date date = cell.getDateCellValue();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        cellValue = sdf.format(date);
                    } else {
                        // 如果是纯数字取得当前Cell的数值
                        cellValue = String.valueOf(cell.getNumericCellValue());
                    }
                    break;
                }
                // 如果当前Cell的Type为STRIN
                case Cell.CELL_TYPE_STRING:
                    // 取得当前的Cell字符串
                    cellValue = cell.getRichStringCellValue().getString();
                    break;
                default:
                    // 默认的Cell值
                    cellValue = " ";
            }
        } else {
            cellValue = "";
        }
        return cellValue;

    }

    /**
     * 从Excel中读取数据
     * @param file  Excel文件
     * @param actor 角色,教师1，学生2，企业3，根据角色读取数据返回
     * @return
     */
    public static List readExcelFile(File file, int actor) {
        try {
            InputStream is = new FileInputStream(file);

            String extension = file.getName().substring(file.getName().lastIndexOf('.'));

            if (extension.equals(".xls")) {
                // Excel 2003
                wb = new HSSFWorkbook(new POIFSFileSystem(is));
                sheet = wb.getSheetAt(0);
            } else if (extension.equals(".xlsx")) {
                wb = new XSSFWorkbook(is);
                sheet = wb.getSheetAt(0);
            } else {
                return null;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        sheet = wb.getSheetAt(0);
        int rowNum = sheet.getLastRowNum();
        row = sheet.getRow(0);
        int colNum = row.getPhysicalNumberOfCells();

        switch (actor) {
            case 1:
                List<Teacher> teacherList = new ArrayList<>();
                for (int i = 1; i <= rowNum; i++) {
                    Teacher teacher = new Teacher();
                    row = sheet.getRow(i);

                    teacher.setWorkerId(getCellFormatValue(row.getCell(1)));
                    teacher.setName(getCellFormatValue(row.getCell(2)));
                    teacher.setProfession(getCellFormatValue(row.getCell(3)));
                    teacher.setDepart(getCellFormatValue(row.getCell(4)));

                    teacherList.add(teacher);
                }
                return teacherList;
            case 2:
                List<Student> studentList = new ArrayList<>();
                // 标题行和第一列不读取
                for (int i = 1; i <= rowNum; i++) {
                    Student student = new Student();
                    row = sheet.getRow(i);

                    student.setStudentId(getCellFormatValue(row.getCell(1)));
                    student.setName(getCellFormatValue(row.getCell(2)));
                    student.setProfession(getCellFormatValue(row.getCell(3)));
                    student.setDepart(getCellFormatValue(row.getCell(4)));

                    studentList.add(student);
                }
                return studentList;
            case 3:
                List<Company> companyList = new ArrayList<>();

                for (int i = 1; i <= rowNum; i++) {
                    Company company = new Company();
                    row = sheet.getRow(i);

                    company.setName(getCellFormatValue(row.getCell(1)));
                    company.setContact(getCellFormatValue(row.getCell(2)));
                    company.setAddress(getCellFormatValue(row.getCell(3)));

                    companyList.add(company);
                }
                return companyList;
            }
        return null;
    }
}

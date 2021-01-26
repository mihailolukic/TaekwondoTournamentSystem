package app.backend.dataStorage;

import app.backend.dataStorage.model.Fighter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.*;
import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

/**
 * Created by Mihailo on 1/29/2017.
 */
public class ExcelParser {

    private String inputFile;
    private Sheet sheet;
    private Workbook workBook;
    private int rowCount;

    public ArrayList<Fighter> parseExcel(String inputFile) throws IOException {
        this.inputFile = inputFile;
        ArrayList<Fighter> tempList = new ArrayList<>();

        workBook = getWoorkBook(inputFile);
        sheet = workBook.getSheetAt(0);
        rowCount = sheet.getLastRowNum();
        Fighter fighter;
        Iterator<Row> iterator = sheet.iterator();
        iterator.next();
        while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            int cellCounter = 0;
            fighter = new Fighter();
            fighter.setId(nextRow.getRowNum());
            for (int i = 0; i < 21; i++) {
                Cell cell = nextRow.getCell(i);
                switch (i) {
                    case 0:
                        fighter.setTimeMark(convertToString(cell));
                        break;
                    case 1:
                        fighter.setNameAndSurname(convertToString(cell));
                        break;
                    case 2:
                        fighter.setCountry(convertToString(cell));
                        break;
                    case 3:
                        fighter.setClub(convertToString(cell));
                        break;
                    case 4:
                        fighter.setChoose(convertToString(cell));
                        break;
                    case 5:
                        fighter.setGender(convertToString(cell));
                        break;
                    case 6:
                        fighter.setMaleCategory(convertToString(cell));
                        break;
                    case 7:
                        fighter.setFemaleCategory(convertToString(cell));
                        break;
                    case 8:
                        fighter.setDegree(convertToString(cell));
                        break;
                    case 9:
                        fighter.setSparingYouth(convertToString(cell));
                        break;
                    case 10:
                        fighter.setSparingOlderYouthMale(convertToString(cell));
                        break;
                    case 11:
                        fighter.setSparingOlderYouthFemale(convertToString(cell));
                        break;
                    case 12:
                        fighter.setSparingJuniorsMale(convertToString(cell));
                        break;
                    case 13:
                        fighter.setSparingJuniorsFemale(convertToString(cell));
                        break;
                    case 14:
                        fighter.setSparingSeniorsMale(convertToString(cell));
                        break;
                    case 15:
                        fighter.setSparingSeniorsFemale(convertToString(cell));
                        break;
                    case 16:
                        fighter.setDegreeUmpires(convertToString(cell));
                        break;
                    case 17:
                        fighter.setSparingVeterans(convertToString(cell));
                        break;
                    case 18:
                        fighter.setEmail(convertToString(cell));
                        break;
                    case 19:
                        fighter.setPhone(convertToString(cell));
                        break;
                    case 20:
                        fighter.setDateOfBirth(convertToString(cell));
                        break;
                }
            }
            tempList.add(fighter);
        }
        workBook.close();
        return tempList;
    }

    private String convertToString(Cell cell) {
        if (cell == null) {
            return null;
        }
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                return cell.getStringCellValue();
            case Cell.CELL_TYPE_BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case Cell.CELL_TYPE_NUMERIC:
                String cellValue = "";
                try {
                    cellValue = String.valueOf(cell.getDateCellValue());
                } catch (DateTimeException e) {
                    cellValue = String.valueOf((int) cell.getNumericCellValue());
                }
                return cellValue;
            case Cell.CELL_TYPE_BLANK:
                return null;
            default:
                return null;

        }
    }


    private Workbook getWoorkBook(String inputWorkbook) throws IOException {
        Workbook workbook;

        if (inputWorkbook.endsWith("xlsx")) {
            workbook = new XSSFWorkbook(new FileInputStream(new File(inputWorkbook)));
        } else if (inputWorkbook.endsWith("xls")) {
            workbook = new HSSFWorkbook(new FileInputStream(new File(inputWorkbook)));
        } else {
            throw new IllegalArgumentException("The specified file is not Excel file");
        }
        return workbook;
    }

    public int writeFighterIntoFile(Fighter fighter) throws IOException {
        workBook = getWoorkBook(inputFile);
        sheet = workBook.getSheetAt(0);
        Row row = sheet.createRow(++rowCount);
        for (int i = 0; i < 21; i++) {
            Cell cell = row.createCell(i);
            switch (i) {
                case 0:
                    cell.setCellValue(fighter.getTimeMark());
                    break;
                case 1:
                    cell.setCellValue(fighter.getNameAndSurname());
                    break;
                case 2:
                    cell.setCellValue(fighter.getCountry());
                    break;
                case 3:
                    cell.setCellValue(fighter.getClub());
                    break;
                case 4:
                    cell.setCellValue(fighter.getChoose());
                    break;
                case 5:
                    cell.setCellValue(fighter.getGender());
                    break;
                case 6:
                    cell.setCellValue(fighter.getMaleCategory());
                    break;
                case 7:
                    cell.setCellValue(fighter.getFemaleCategory());
                    break;
                case 8:
                    cell.setCellValue(fighter.getDegree());
                    break;
                case 9:
                    cell.setCellValue(fighter.getSparingYouth());
                    break;
                case 10:
                    cell.setCellValue(fighter.getSparingOlderYouthMale());
                    break;
                case 11:
                    cell.setCellValue(fighter.getSparingOlderYouthFemale());
                    break;
                case 12:
                    cell.setCellValue(fighter.getSparingJuniorsMale());
                    break;
                case 13:
                    cell.setCellValue(fighter.getSparingJuniorsFemale());
                    break;
                case 14:
                    cell.setCellValue(fighter.getSparingSeniorsMale());
                    break;
                case 15:
                    cell.setCellValue(fighter.getSparingSeniorsFemale());
                    break;
                case 16:
                    cell.setCellValue(fighter.getDegreeUmpires());
                    break;
                case 17:
                    cell.setCellValue(fighter.getSparingVeterans());
                    break;
                case 18:
                    cell.setCellValue(fighter.getEmail());
                    break;
                case 19:
                    cell.setCellValue(fighter.getPhone());
                    break;
                case 20:
                    cell.setCellValue(fighter.getDateOfBirth());
                    break;
            }
        }
        try (FileOutputStream outputStream = new FileOutputStream(inputFile)) {
            workBook.write(outputStream);
            workBook.close();
            return rowCount;
        } catch (FileNotFoundException e) {
            return -1;
        } catch (IOException e) {
            return -1;
        }
    }

    public void deleteRowFromExcel(int rowNumber) throws IOException {
        workBook = getWoorkBook(inputFile);
        sheet = workBook.getSheetAt(0);
        Row row = sheet.getRow(rowNumber);
        sheet.removeRow(row);
        FileOutputStream outputStream = new FileOutputStream(inputFile);
        workBook.write(outputStream);
        workBook.close();
    }
}

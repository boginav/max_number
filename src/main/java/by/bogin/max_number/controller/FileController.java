package by.bogin.max_number.controller;


import by.bogin.max_number.utils.DoudleLinkedList.DoubleLinkedList;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.PriorityQueue;

@RestController
public class FileController {

    // с использованием PriorityQueue
    @GetMapping("/max-number-first")
    public Integer getMaxNumberFirst(@RequestParam String filePath, @RequestParam int n) {

        PriorityQueue<Integer> minHeap = new PriorityQueue<>(n);

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                int number = (int) row.getCell(0).getNumericCellValue();
                if (minHeap.size() < n) {
                    minHeap.add(number);
                } else if (number > minHeap.peek()) {
                    minHeap.poll();
                    minHeap.add(number);
                }
            }

            if (minHeap.size() < n) {
                throw new IndexOutOfBoundsException("Index out of list range");
            }

            return minHeap.peek();

        } catch (IndexOutOfBoundsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Index out of list range");
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error reading the file", e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", e);
        }
    }

    // c использованием LinkedList
    @GetMapping("/max-number-second")
    public Integer getMaxNumberSecond(@RequestParam String filePath, @RequestParam int n) {

        LinkedList<Integer> list = new LinkedList<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                int number = (int) row.getCell(0).getNumericCellValue();
                list = addToListWithSorting(list, number);
            }

            if (list.size() < n - 1) {
                throw new IndexOutOfBoundsException("Index out of list range");
            }

            return list.get(list.size() - 1 - n);

        } catch (IndexOutOfBoundsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Index out of list range");
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error reading the file", e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", e);
        }
    }

 // метод для добавления числа в LinkedList по возростанию
 // перенесен отдельно для читаемости кода (сокращение else if)
    private static LinkedList<Integer> addToListWithSorting(LinkedList<Integer> list, int number) {
        if (list.isEmpty()) {
            list.add(number);
            return list;
        }

        for (int i = 0; i < list.size(); i++) {
            if (number <= list.get(i)) {
                list.add(i, number);
                return list;
            }
        }

        list.add(list.size(), number);
        return list;
    }

    // используется собственный двусвязный список DoubleLinkedList
    @GetMapping("/max-number-third")
    public Integer getMaxNumberThird(@RequestParam String filePath, @RequestParam int n) {

        DoubleLinkedList list = new DoubleLinkedList();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                int number = (int) row.getCell(0).getNumericCellValue();
                list.addWithSorting(number);
//                System.out.println(list.getSize());
//                list.display();
            }

            return list.getValueByIndex(n - 1);

        } catch (IndexOutOfBoundsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Index out of list range");
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error reading the file", e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", e);
        }
    }


}

package com.company.libmanagementmain;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.company.libmanagementmain.entity.Book;
import com.company.libmanagementmain.entity.LibResultInfo;
import com.company.libmanagementmain.enums.OperationEnum;
import com.company.libmanagementmain.utils.HelperUtil;
import com.company.libmanagementmain.utils.HttpClientUtil;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @Description:
 * @author loda
 * @date 2023/10/22/
 */
@Component
public class UIMonitor {
    private String token;

    @Value("${lms.register}")
    private String registerUrl;

    @Value("${lms.login}")
    private String loginUrl;

    @Value("${lms.book_list}")
    private String bookListUrl;

    @Value("${lms.search_book}")
    private String searchBookUrl;

    @Value("${lms.delete_book}")
    private String deleteBookUrl;

    @Value("${lms.add_book}")
    private String addBookUrl;

    @Value("${lms.order}")
    private String orderUrl;

    private static final Logger log = LoggerFactory.getLogger(UIMonitor.class);

    private Scanner sc = new Scanner(System.in);
    public void showHome(){
        String[] inputArray = handleInput();

        OperationEnum oper = HelperUtil.checkOperation(inputArray[0]);
        if(oper == null){
            System.out.println("Unrecognized operator");
            return;
        }
        switch (oper){
            case LOGIN:
                login(inputArray);
                break;
            case REGISTER:
                register(inputArray);
                break;
//            case LOGOUT:
//                logout();
//                break;
            case BORROW:
                    borrowBook(inputArray);
                break;
            case RETURN:
                    returnBook(inputArray);
                break;
            case ADD:
                addBook(inputArray);
                break;
            case LIST:
                    listBook(inputArray);
                break;
            case DELETE:
                    deleteBook(inputArray);
                break;
            case SEARCH:
                    search(inputArray);
                break;
            default:
                System.out.println("Unrecognized operator");
                break;
        }
    }

    public String[] handleInput(){
        String input = sc.nextLine();;
        String[] inputArray = input.split("\\s(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)");
        for (int i = 0; i < inputArray.length; i++) {
            String temp = inputArray[i];
            if (temp.startsWith("\"") && temp.endsWith("\"")){
                inputArray[i] = temp.substring(1, temp.length() - 1);
            }
        }
        return inputArray;
    }

    public void register(String[] inputArray){
        if(inputArray.length != 4){
            System.out.println("invalidate input");
            return;
        }

        if (!inputArray[1].toLowerCase().equals("admin") && !inputArray[1].toLowerCase().equals("user")){
            System.out.println("invalidate input");
            return;
        }
        boolean isAdmin = inputArray[1].toLowerCase().equals("admin") ? true : false;
        String userName =inputArray[2];
        String password =inputArray[3];

//        parametersBody.add(new BasicHeader("", password));
        List<NameValuePair> parametersBody = new ArrayList<>();
        parametersBody.add(new BasicNameValuePair("userName", userName));
        parametersBody.add(new BasicNameValuePair("pwd", password));
        parametersBody.add(new BasicNameValuePair("isAdmin", String.valueOf(isAdmin)));
        try{
            LibResultInfo libResultInfo = HttpClientUtil.postFormReturnObject(registerUrl, parametersBody);
            if(libResultInfo.isStatus()){
                System.out.println(String.format("%s %s successfully registered", inputArray[1], inputArray[2]));
            }else{
                System.out.println(libResultInfo.getInfo());
            }
        }catch (Exception e){
            log.error(e.getMessage());
            System.out.println("register failed.");
        }
    }

    public void login(String[] inputArray){
        if(inputArray.length != 3){
            System.out.println("invalidate input");
            return;
        }

        String userName =inputArray[1];
        String password =inputArray[2];

        List<NameValuePair> parametersBody = new ArrayList<>();
        parametersBody.add(new BasicNameValuePair("userName", userName));
        parametersBody.add(new BasicNameValuePair("pwd", password));
        try{
            LibResultInfo libResultInfo = HttpClientUtil.postFormReturnObject(loginUrl, parametersBody);
            if (libResultInfo.isStatus()){
                token = libResultInfo.getInfo();
                String roleString = libResultInfo.isAdmin()? "Admin" : "User";
                System.out.println(roleString + " " + userName + " successfully l$ogged in.");
            }else{
                System.out.println(libResultInfo.getInfo());
            }

        }catch (Exception e){
            log.error(e.getMessage());
            System.out.println("login failed.");
        }
    }

    public void addBook(String[] inputArray){
        if(inputArray.length != 4){
            System.out.println("invalidate input");
            return;
        }

        if (!HelperUtil.checkNumber(inputArray[3])){
            System.out.println("inventory please enter the number.");
            return;
        }

        List<NameValuePair> parametersBody = new ArrayList<>();
        parametersBody.add(new BasicNameValuePair("bookName", inputArray[1]));
        parametersBody.add(new BasicNameValuePair("author", inputArray[2]));
        parametersBody.add(new BasicNameValuePair("number", inputArray[3]));
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("token", token);
        try{
            LibResultInfo libResultInfo = HttpClientUtil.postFormReturnObject(addBookUrl, parametersBody, headerMap);
            System.out.println(libResultInfo.getInfo());

        }catch (Exception e){
            log.error(e.getMessage());
            System.out.println("add book failed.");
        }
    }

    public void borrowBook(String[] inputArray){
        if(inputArray.length != 3){
            System.out.println("invalidate input");
            return;
        }

        List<NameValuePair> parametersBody = new ArrayList<>();
        parametersBody.add(new BasicNameValuePair("bookName", inputArray[1]));
        parametersBody.add(new BasicNameValuePair("author", inputArray[2]));
        parametersBody.add(new BasicNameValuePair("isReturn", "false"));
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("token", token);
        try{
            LibResultInfo libResultInfo = HttpClientUtil.postFormReturnObject(orderUrl, parametersBody, headerMap);
            System.out.println(libResultInfo.getInfo());

        }catch (Exception e){
            log.error(e.getMessage());
            System.out.println("borrow book failed.");
        }
    }

    public void returnBook(String[] inputArray){
        if(inputArray.length != 3){
            System.out.println("invalidate input");
            return;
        }

        List<NameValuePair> parametersBody = new ArrayList<>();
        parametersBody.add(new BasicNameValuePair("bookName", inputArray[1]));
        parametersBody.add(new BasicNameValuePair("author", inputArray[2]));
        parametersBody.add(new BasicNameValuePair("isReturn", "true"));
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("token", token);
        try{
            LibResultInfo libResultInfo = HttpClientUtil.postFormReturnObject(orderUrl, parametersBody, headerMap);
            System.out.println(libResultInfo.getInfo());

        }catch (Exception e){
            log.error(e.getMessage());
            System.out.println("return book failed.");
        }
    }

    public void deleteBook(String[] inputArray){
        if (inputArray.length != 3) {
            System.out.println("invalidate input");
            return;
        }

        List<NameValuePair> parametersBody = new ArrayList<>();
        parametersBody.add(new BasicNameValuePair("bookName", inputArray[1]));
        parametersBody.add(new BasicNameValuePair("author", inputArray[2]));
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("token", token);
        try{
            LibResultInfo libResultInfo = HttpClientUtil.postFormReturnObject(deleteBookUrl, parametersBody, headerMap);
            System.out.println(libResultInfo.getInfo());

        }catch (Exception e){
            log.error(e.getMessage());
            System.out.println("delete book failed.");
        }
    }

    public void search(String[] inputArray){
        if (inputArray.length != 3) {
            System.out.println("invalidate input");
            return;
        }

        List<NameValuePair> parametersBody = new ArrayList<>();
        parametersBody.add(new BasicNameValuePair("bookName", inputArray[1]));
        parametersBody.add(new BasicNameValuePair("author", inputArray[2]));
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("token", token);
        try{
            String reponseString = HttpClientUtil.getRequest(searchBookUrl, parametersBody, headerMap);
            Book book = JSON.parseObject(reponseString, new TypeReference<Book>(){});
            book = JSONObject.parseObject(reponseString,Book.class);
            if (book == null){
                System.out.println("Book does not exists.");
            }else{
                System.out.println(String.format("%s - %s - Inventory : %d", book.getBookName(), book.getAuthor(), book.getInventory()));
            }
        }catch (Exception e){
            log.error(e.getMessage());
            System.out.println("search book failed.");
        }
    }

    public void listBook(String[] inputArray){
        if (inputArray.length > 2) {
            System.out.println("invalidate input");
            return;
        }
        String pageNumber = "0";
        if (inputArray.length == 2){
            if (!HelperUtil.checkNumber(inputArray[1])){
                System.out.println("page number is wrong.");
                return;
            }
            pageNumber = inputArray[1];
        }

        String pageSize = "10";
        List<NameValuePair> parametersBody = new ArrayList<>();
        parametersBody.add(new BasicNameValuePair("pageNumber", pageNumber));
        parametersBody.add(new BasicNameValuePair("size", pageSize));
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("token", token);
        try{
            String reponseString = HttpClientUtil.getRequest(bookListUrl, parametersBody, headerMap);
            List<Book> booklist = JSON.parseObject(reponseString,new TypeReference<List<Book>>(){});
            System.out.println("Book list:");
            for (Book book : booklist) {
                System.out.println(String.format("%s - %s - Inventory : %d", book.getBookName(), book.getAuthor(), book.getInventory()));
            }
        }catch (Exception e){
            log.error(e.getMessage());
            System.out.println("search book failed.");
        }

        System.out.println("You could use $list n to choose the page.");
    }
}


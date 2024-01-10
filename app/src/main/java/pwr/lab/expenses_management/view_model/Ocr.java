package pwr.lab.expenses_management.view_model;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Environment;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;

public class Ocr {

    @Data
    @AllArgsConstructor
    public static class Product {
        private String name;
        private String count;
        private String price;
    }

    private static double countSimilarity(String s1, String s2){

        double length = Integer.min(s1.length(), s2.length());
        double similarity = 0;

        if(s1.length() > s2.length()){
            String buf = s1;
            s1 = s2;
            s2 = buf;
        }

        for(int i=0; i < s1.length(); i++){

            char c1 = s1.charAt(i);
            char c2 = s2.charAt(i);

            if(c1 == c2){
                similarity++;
            }
        }

        return similarity / length * 100;
    }

    private static String getDate(int i, String[] lines){

        for(; i < lines.length; i++){

            String line = lines[i];

            if(line.isBlank()){
                continue;
            }

            String words[] = line.split("\\s");

            if(words.length < 2){
                continue;
            }

            if(countSimilarity(words[0], "PARAGON") >= 80 && countSimilarity(words[1], "FISKALNY") >= 80){
                return lines[i - 1].split("\\s")[0];
            }
        }

        return null;
    }

    private static List<String[]> getProductsWords(int i, String[] lines){

        List<String[]> productsStrs = new ArrayList<>();

        for(int j = i; j < lines.length; j++){

            String line = lines[j];

            if(line.isBlank()){
                continue;
            }

            String[] words = line.split("\\s");

            if(countSimilarity(words[0], "SPRZEDAZ") >= 75){
                break;
            }

            productsStrs.add(words);
        }

        return productsStrs;
    }

    private static String extractWhiteSpaceName(int i, String[] lines, String endName, int j){

        String line = lines[i + j + 1];

        int endWordIndex = line.indexOf(endName);

        StringBuilder nameBuilder = new StringBuilder();

        for(int k = endWordIndex ; k >= 0; k--){

            char c = line.charAt(k);

            if(c == '|'){
                break;
            }

            nameBuilder.append(c);
        }

        nameBuilder.reverse();
        nameBuilder.append(endName);

        return nameBuilder.toString();
    }

    private static List<Product> getProducts(int i, String[] lines){

        i++;

        List<String[]> productsStrs = getProductsWords(i, lines);

        List<Product> products = new ArrayList<>();

        for(int j=0; j < productsStrs.size(); j++){

            String[] words = productsStrs.get(j);

            String name, count, price;

            if(words.length < 4){

                if(j + 1 == productsStrs.size()){
                    break;
                }

                name = words[words.length - 1];

                String[] nextWords = productsStrs.get(j + 1);

                price = nextWords[nextWords.length - 2];
                count = nextWords[nextWords.length - 3];
            }
            else{
                name = words[words.length - 4];
                price = words[words.length - 2];
                count = words[words.length - 3];
            }

            price = price.substring(1);
            price = price.replace(',', '.');

            name = extractWhiteSpaceName(i, lines, name, j);

            if(name.charAt(0) == '|'){
                name = name.substring(1);
            }

            Product newProduct = new Product(name, count, price);

            products.add(newProduct);
        }

        i += productsStrs.size();

        return products;
    }

    private static String getPrice(int i, String[] lines){

        for(; i < lines.length; i++) {

            String line = lines[i];

            if (line.isBlank()) {
                continue;
            }

            String words[] = line.split("\\s+");

            if (countSimilarity(words[0], "SUMA") >= 75 && countSimilarity(words[1], "PLN") >= 66) {
                return words[2];
            }
        }

        return null;
    }

    private static void prepareTestData(Context context) throws IOException {

        File dir = context.getExternalFilesDir(Environment.getExternalStorageDirectory().toString() + "/tessdata");

        if(!dir.exists()){
            dir.mkdir();
        }

        String files[] = context.getAssets().list("tessdata");

        for(String fileName : files){

            String dstPathToDataFile = dir + "/" + fileName;
            File dstFile = new File(dstPathToDataFile);

            System.out.println(fileName + "A");
            System.out.println(dstPathToDataFile);

            InputStream in = context.getAssets().open("tessdata/" + fileName);

            Files.copy(in, dstFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
    }

    public static void loadDataFromRecipe(Context context, Bitmap photo) throws IOException {

        prepareTestData(context);

        String dataPath = context.getExternalFilesDir(Environment.getExternalStorageDirectory().toString()).toString();

        System.out.println(dataPath);

        TessBaseAPI tessBaseApi = new TessBaseAPI();
        tessBaseApi.init(dataPath, "pol");

        tessBaseApi.setImage(photo);
        String extractedText = tessBaseApi.getUTF8Text();

        String[] lines = extractedText.split(System.getProperty("line.separator"));
        
        int i = 0;
        
        String date = getDate(i, lines);
        List<Product> products = getProducts(i, lines);
        String totalPrice = getPrice(i, lines);

        System.out.println(date);

        for(Product product : products){
            System.out.println(product);
        }

        System.out.println(totalPrice);

        tessBaseApi.end();
    }
}

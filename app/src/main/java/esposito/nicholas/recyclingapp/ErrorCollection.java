package esposito.nicholas.recyclingapp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nicholasesposito on 08/05/2016.
 */
public class ErrorCollection {
    private static ErrorCollection ourInstance = new ErrorCollection();
    private List<String> errors;

    public static ErrorCollection getInstance() {
        return ourInstance;
    }

    private ErrorCollection() {
        errors = new ArrayList<>();
    }

    public List<String> getErrors(){
        return errors;
    }

    public void addError(String error){
        errors.add(error);
    }

    public void emptyErrors() {
        errors.clear();
    }
}

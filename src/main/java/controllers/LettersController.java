/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author Ast
 */
@RequestScoped
@Named
public class LettersController implements Serializable {
    private ArrayList<String> letters;
    
    public LettersController() {
        this.letters = new ArrayList<String>();
    }

    public ArrayList<String> getLetters() {
        if(letters.size() > 0){
            return letters;
        } else{
            return setLettersAlp();
        }
    }
    public void setLetters(ArrayList<String> letters) {
        this.letters = letters;
    }

    private ArrayList<String> setLettersAlp() {
        letters = new ArrayList<>();
        ResourceBundle bundle = ResourceBundle.getBundle("nls.properties", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        String letter = bundle.getString("all_alphabetic_letters");
        char[] lett = letter.toCharArray();
        for(int i = 0; i < lett.length; i++){
            String str = String.valueOf(lett[i]);
            letters.add(str);
        }
        return letters;
    }
}

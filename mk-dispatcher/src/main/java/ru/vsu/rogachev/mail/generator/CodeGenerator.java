package ru.vsu.rogachev.mail.generator;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class CodeGenerator {

    private Random rnd = new Random();
    public static final int CODE_LENGTH = 10;

    public String generateActivationCode(){
        StringBuilder code = new StringBuilder();

        for(int i = 0; i < CODE_LENGTH; i++){
            int var = rnd.nextInt(2);
            if(var == 0){
                char sym = (char)(rnd.nextInt(26) + 'a');
                code.append(sym);
            }else{
                char sym = (char)(rnd.nextInt(26) + 'A');
                code.append(sym);
            }
        }

        return code.toString();
    }

}

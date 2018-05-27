package http.practicaweb;

import  org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;



public class Main {

    private static final Logger logger = LogManager.getLogger();

    public static void  main(String[] args){
    logger.info("Cargado Programa...");

    new TareaHttp();    //Clase con logica programa
}

}

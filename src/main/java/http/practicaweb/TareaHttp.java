package http.practicaweb;


import org.apache.logging.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import java.util.NoSuchElementException;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.jsoup.nodes.Element;



public class TareaHttp{

    //Inicializar Log de apache y ver en consola.
    private final Logger logger = LogManager.getLogger();

    private final String PREFIX = "http://";

    private final char CAMBIAR_URL = 'J';

    private final char SALIR = 'S';

    //Instanciar el documento
    private Document document;

    //Guardar el url digitado.
    private String url;

    public TareaHttp() {

        logical();
    }

    //Funcion para dibujar el menu
    private void menuOpciones() {
        System.out.println("*----------------------------Menu-----------------------------*");
        System.out.println("*                                                             *");
        System.out.println("*--> Caso           [A]                                       *");
        System.out.println("*--> Caso           [B]                                       *");
        System.out.println("*--> Caso           [C]                                       *");
        System.out.println("*--> Caso           [D]                                       *");
        System.out.println("*--> Caso           [E]                                       *");
        System.out.println("*--> Caso           [F]                                       *");
        System.out.println("*--> Cambiar de URL [J]                                       *");
        System.out.println("*--> Salir          [S]                                       *");
        System.out.println("*                                                             *");
        System.out.println("---------------------------------------------------------------");
    }


    //Funcion retorna la opcion seleccionada.
    private char entradaOpcion() {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite su opcion --> ");
        return scanner.next().toUpperCase().charAt(0);
    }

    //Funcion que me devuelve la URL digitada por el usuario.
    private String entradaURL() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Solo escriba el nombre y el dominio del sitio web: ");
        System.out.print("--> ");
        return PREFIX + scanner.next();
    }

    //Dibujar un separedor para diferenciar las salidas.
    private void separarOpcion() {
        System.out.println("-------------------------------------------------------------");
    }


    private void logical() {
        char opt = 0;

        while (opt != SALIR) {
            try {
                separarOpcion();
                opt = 0;

                //Solicitar el Url
                url = entradaURL();
                System.out.println("Sitio Digitado: " + url);

                System.out.println("Haciendo el request hacia: " + url);
                System.out.println("Espere, por favor...");

                //Extraer el contenido hacia el documento

                //Document doc = Jsoup.connect("https://www.ecured.cu/Protocolo_RIP/").get();
                document = Jsoup.connect(url).get();
                System.out.println("El Request ha terminado");

                //Bucle con los Case de las opciones
                while (opt != CAMBIAR_URL) {

                    menuOpciones();

                    opt = entradaOpcion();

                    switch (opt) {
                        case 'A':
                            separarOpcion();
                            apartadoA();
                            separarOpcion();
                            break;
                        case 'B':
                            separarOpcion();
                            apartadoB();
                            separarOpcion();
                            break;
                        case 'C':
                            separarOpcion();
                            apartadoC();
                            separarOpcion();
                            break;

                        case 'D':
                            separarOpcion();
                            apartadoD();
                            separarOpcion();
                            break;
                        case 'E':
                            separarOpcion();
                            apartadoE();
                            separarOpcion();
                            break;
                        case 'F':
                            separarOpcion();
                            apartadoF();
                            separarOpcion();
                            break;
                        case 'J':
                            break;
                        case 'S':
                            logger.info("El programa ha Finalizado");
                            System.exit(0);
                            break;

                        default:
                            try {
                                throw new InvalidException("Seleccione una Opcion Valida:[A -> F]");
                            } catch (InvalidException e) {
                                logger.debug("Error introduciendo una Opcion", e.getMessage());
                            }
                            break;
                    }
                }
            } catch (IOException e) {
                logger.debug("Error en la Conexion...Intente nuevamente o use otro URL.", e.getMessage());
            }
        }
    }

    //Indicar cantidad de Lineas
    private void apartadoA()
    {
        System.out.println("Cantidad de lineas: " + document.html().split("\n").length);
    }

    //Indicar cantidad de parrafos del documento HTML .
    private void apartadoB() {

        try {
            System.out.println("Cantidad de parrafos: " + document.getElementsByTag("p").size());
        } catch (NoSuchElementException e) {
            logger.debug("[Error] --> el elemento no Existe.", e.getMessage());

        }

    }

    //Indicar cantidad de imagenes dentro de los parrafos.
    private void apartadoC() {
        try {
            int[] count = {0};

            document.getElementsByTag("p").forEach(element -> {
                element.getElementsByTag("img").forEach(element1 -> {
                    count[0]++;
                });
            });

            System.out.println("La cantidad de imagenes dentro de los parrafos: " + count[0]);

        } catch (NoSuchElementException e) {
            logger.debug("[Error] --> el elemento no Existe.", e.getMessage());
        }
    }

    //indicar cantidad de formularios (form), categorizados por el metodo implementado POST o GET.

    private void apartadoD() {
        try {

            int[] sizePost = {0};
            int[] sizeGet = {0};

            document.getElementsByTag("form").forEach(element -> {
                element.getElementsByAttributeValue("method", "post").forEach(element1 -> {
                    sizePost[0]++;
                });
            });

            document.getElementsByTag("form").forEach(element -> {
                element.getElementsByAttributeValue("method", "get").forEach(element1 -> {
                    sizeGet[0]++;
                });
            });
            System.out.println("Cantidad de formularios con GET : " + sizeGet[0]);

            System.out.println("Cantidad de formularios con POST: " + sizePost[0]);

        } catch (NoSuchElementException e) {
            logger.debug("[Error] --> el elemento no Existe.", e.getMessage());
        }

    }

    //En cada formulario mostrar campos de tipo input y su tipo que contiene en el documento HTML.

    private void apartadoE() {
        try {
            for (Element elemento : document.getElementsByTag("form")) {
                System.out.println(elemento);
            }
        } catch (NoSuchElementException e) {

            logger.debug("[Error] --> el elemento no Existe.", e.getMessage());

        }

    }

    //En cada formulario parseado, identificar que el metodo de envío sea el método POST
    // y enviar un petición al servidor, con el parámetro llamado asignatura y valor
    //practica1 y crear un header llamado matricula con el valor correspondiente a matrícula asignada.
    //mostrar la respuesta por la salida estandar.
    private void apartadoF() {

        Document doc = null;
        Map<String, String> parametros = new HashMap<>();

        try {
            for (Element matricula: document.getElementsByTag("form")) {

                String absURL = matricula.absUrl("action");

                if (matricula.attr("method").equals("post")) {
                    parametros.put("asignatura", "practica1");

                    doc = Jsoup.connect(absURL)
                            .method(Connection.Method.POST)
                            .data(parametros)
                            .post();

                    System.out.println(doc.outerHtml());
                }
            }
        } catch (IOException e) {

            logger.debug("[Error] --> Error enviando Peticion " + url, e.getMessage());
        }
    }


}

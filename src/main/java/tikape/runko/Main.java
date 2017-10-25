package tikape.runko;

import java.util.HashMap;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.database.AnnosDao;
import tikape.runko.database.RaakaAineDao;
//On tää saatana työmaa t:petteri
//Lomakkeenlukemisjutut lisätty
public class Main {

    public static void main(String[] args) throws Exception {
        Database database = new Database("jdbc:sqlite:smoothie.db");
        database.init();

        AnnosDao annosDao = new AnnosDao(database);
        RaakaAineDao raakaAineDao = new RaakaAineDao(database);

        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("viesti", "tervehdys");

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());
        
        get("/uusiraakaaine", (req, res) -> {
            return "<form method=\"POST\" action=\"/smoothiet\">\n"
                    + "Raaka-aineen nimi:<br/>\n"
                    + "<input type=\"text\" name=\"raaka-aineen nimi\"/><br/>\n"
                    + "<input type=\"submit\" value=\"Lisää\"/>\n"
                    + "</form>";
        });
       
        get("/uusismoothie", (req, res) -> {
            return "<form method=\"POST\" action=\"/smoothiet\">\n"
                    + "Smoothien nimi:<br/>\n"
                    + "<input type=\"text\" name=\"smoothien nimi\"/><br/>\n"
                    + "<input type=\"submit\" value=\"Lisää\"/>\n"
                    + "</form>";
        });
        
        get("/uusismoothie", (req, res) -> {
            return "<form method=\"POST\" action=\"/smoothiet\">\n"
                    + "Smoothien nimi:<br/>\n"
                    + "<input type=\"text\" name=\"smoothien nimi\"/><br/>\n"
                    + "Järjestys:<br/>\n"
                    + "<input type=\"text\" name=\"raaka-aine\"/><br/>\n"
                    + "Määrä:<br/>\n"
                    + "<input type=\"number\" name=\"maara\"/><br/>\n"
                    + "Ohje:<br/>\n"
                    + "<input type=\"text\" name=\"ohje\"/><br/>\n"
                    + "<input type=\"submit\" value=\"Lisää\"/>\n"
                    + "</form>";
        });
        
        post("/uusismoothie", (req, res) -> {
            String nimi = req.queryParams("smoothien nimi");
            return "Kerrotaan siitä tiedon lähettäjälle: " + nimi;
        });

    }
}

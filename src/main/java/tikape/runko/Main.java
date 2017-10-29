package tikape.runko;

import java.util.ArrayList;
import java.util.HashMap;
import spark.ModelAndView;
import spark.Spark;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.database.AnnosDao;
import tikape.runko.database.RaakaAineDao;
import tikape.runko.domain.RaakaAine;
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
            map.put("annokset", annosDao.findAll());

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());
        
        get("/smoothie/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            Integer id = Integer.parseInt(req.params("id"));
            map.put("annos", annosDao.findOne(id));
            map.put("raakaAineet", raakaAineDao.findRaakaAineetForSmoothie(id));
            return new ModelAndView(map, "smoothie");
        }, new ThymeleafTemplateEngine());
        
        get("/uusiraakaaine", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("annokset", raakaAineDao.findAll());

            return new ModelAndView(map, "uusiraakaaine");
        }, new ThymeleafTemplateEngine());
        
        Spark.post("/uusiraakaaine", (req, res) -> {
            String nimi = req.queryParams("nimi");
            raakaAineDao.lisaaRaakaAine(nimi);
            res.redirect("/uusiraakaaine");
            return "";
        });
        
        Spark.get("/uusiraakaaine/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            raakaAineDao.delete(Integer.parseInt(req.params("id")));
            res.redirect("/uusiraakaaine");
            return new ModelAndView(map, "uusiraakaaine");
        }, new ThymeleafTemplateEngine());
        
        
        get("/uusismoothie", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("annokset", annosDao.findAll());
            map.put("raakaaine", raakaAineDao.findAll());

            return new ModelAndView(map, "uusismoothie");
        }, new ThymeleafTemplateEngine());
        
        Spark.post("/uusismoothie", (req, res) -> {
            String nimi = req.queryParams("smoothieNimi");
            annosDao.lisaaSmoothie(nimi);
            
            int raakaAineId = Integer.parseInt(req.queryParams("smoothienNimi"));
            int annosId = Integer.parseInt(req.queryParams("raaka-aineet"));
            String maara = req.queryParams("maara");
            String ohje = req.queryParams("ohje");
            
            annosDao.lisaaAinekset(raakaAineId, annosId, maara, ohje);
            
            res.redirect("/uusismoothie");
            return "";
        });
        
        Spark.post("/uusismoothie", (req, res) -> {
            
            int raakaAineId = Integer.parseInt(req.queryParams("smoothienNimi"));
            int annosId = Integer.parseInt(req.queryParams("raaka-aineet"));
            String maara = req.queryParams("maara");
            String ohje = req.queryParams("ohje");
            
            annosDao.lisaaAinekset(raakaAineId, annosId, maara, ohje);
            
            res.redirect("/uusismoothie");
            return "";
        });
        
        
        
        
        
//        get("/uusiraakaaine", (req, res) -> {
//            return "<form method=\"POST\" action=\"/uusiraakaaine\">\n"
//                    + "Raaka-aineen nimi:<br/>\n"
//                    + "<input type=\"text\" name=\"raaka-aineen nimi\"/><br/>\n"
//                    + "<input type=\"submit\" value=\"Lisää\"/>\n"
//                    + "</form>";
//        });
//        
//        post("/uusiraakaaine", (req, res) -> {
//            String raakaaine = req.queryParams("raaka-aineen nimi");
//            return "Kerrotaan siitä tiedon lähettäjälle: " + raakaaine;
//        });
//
//       
//        get("/uusismoothie", (req, res) -> {
//            return "<form method=\"POST\" action=\"/uusismoothie\">\n"
//                    + "Smoothien nimi:<br/>\n"
//                    + "<input type=\"text\" name=\"smoothien nimi\"/><br/>\n"
//                    + "<input type=\"submit\" value=\"Lisää\"/>\n"
//                    + "</form>";
//        });
//        
//        get("/uusismoothie", (req, res) -> {
//            return "<form method=\"POST\" action=\"/uusismoothie\">\n"
//                    + "Smoothien nimi:<br/>\n"
//                    + "<input type=\"text\" name=\"smoothien nimi\"/><br/>\n"
//                    + "Järjestys:<br/>\n"
//                    + "<input type=\"text\" name=\"raaka-aine\"/><br/>\n"
//                    + "Määrä:<br/>\n"
//                    + "<input type=\"number\" name=\"maara\"/><br/>\n"
//                    + "Ohje:<br/>\n"
//                    + "<input type=\"text\" name=\"ohje\"/><br/>\n"
//                    + "<input type=\"submit\" value=\"Lisää\"/>\n"
//                    + "</form>";
//        });
//        
//        post("/uusismoothie", (req, res) -> {
//            String nimi = req.queryParams("smoothien nimi");
//            return "Kerrotaan siitä tiedon lähettäjälle: " + nimi;
//        });

    }
}

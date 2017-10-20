/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.database;

/**
 *
 * @author pette
 */
public class AnnosRaakaAine {
    private Integer id;
    private Integer annosId;
    private Integer raakaAineId;
    private Integer jarjestys;
    private Integer maara;
    private String ohje;
    
    public AnnosRaakaAine(Integer id, Integer annosId, Integer raakaAineId, Integer jarjestys, Integer maara, String ohje) {
    this.id = id;
    this.annosId = annosId;
    this.raakaAineId = raakaAineId;
    this.jarjestys = jarjestys;
    this.maara = maara;
    this.ohje = ohje;
    }
    
    public Integer getId() {
        return this.id;
    }
    
    public Integer getAnnosId() {
        return this.annosId;
    }
    
    public Integer getRaakaAineId() {
        return this.raakaAineId;
    }    
    
    public Integer getJarjestys() {
        return this.jarjestys;
    }    
    
    public Integer getMaara() {
        return this.maara;
    }
    
    public String getOhje() {
        return this.ohje;
    }
    
}

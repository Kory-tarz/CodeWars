package com.cyryl.kyu3;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class InspectorTest {



    @Test
    public void allowCitizen(){
        Inspector inspector = new Inspector();

        String bulletin = "Allow Citizens of Obristan";
        inspector.checkAndUpdateNation(bulletin);
        Assert.assertTrue(inspector.allowedNations.contains("Obristan"));

        bulletin = "Deny citizens of Kolechia, Republia, Obristan";
        String[] nations = {"Obristan2", "Kolechia", "Republia"};
        inspector.checkAndUpdateNation(bulletin);
        Assert.assertFalse(inspector.allowedNations.contains("Obristan"));
    }

    @Test
    public void updateDocuments(){
        Inspector inspector = new Inspector();

        String bulletin = "Foreigners require access permit";
        inspector.checkAndUpdateRequirements(bulletin);
        Assert.assertTrue(inspector.requiredDocuments.get("Kolechia").contains("access_permit"));
        Assert.assertFalse(inspector.requiredDocuments.get("Arstotzka").contains("access_permit"));

    }

    @Test
    public void allEntrantsRequirement(){
        Inspector inspector = new Inspector();

        String bulletin = "Citizens of Antegria, Republia, Obristan require polio vaccination";
        String bulletin2 = "Entrants no longer require polio vaccination";

        inspector.checkAndUpdateRequirements(bulletin);
        Assert.assertTrue(inspector.requiredVaccination.get("Antegria").contains("polio"));

        inspector.checkAndUpdateRequirements(bulletin2);
        Assert.assertFalse(inspector.requiredVaccination.get("Antegria").contains("polio"));
        Assert.assertFalse(inspector.requiredVaccination.get("Republia").contains("polio"));

    }

    @Test
    public void updateDocumentsOfMany(){
        Inspector inspector = new Inspector();

        String bulletin = "Citizens of Arstotzka, Republia, Workers require ID card";
        inspector.checkAndUpdateRequirements(bulletin);
        Assert.assertTrue(inspector.requiredDocuments.get("Arstotzka").contains("ID_card"));
        Assert.assertTrue(inspector.requiredDocuments.get("Republia").contains("ID_card"));
        Assert.assertTrue(inspector.requiredDocuments.get("Workers").contains("ID_card"));

    }

    @Test
    public void bannedNations(){
        Inspector inspector = new Inspector();

        String bulletin = "Allow citizens of Antegria, Impor, Kolechia, Obristan, Republia, United Federation";
        inspector.receiveBulletin(bulletin);
        System.out.println(inspector.allowedNations);

    }

    @Test
    public void removeRequirement(){
        Inspector inspector = new Inspector();

        String bulletin = "Foreigners require access permit";
        inspector.checkAndUpdateRequirements(bulletin);
        Assert.assertTrue(inspector.requiredDocuments.get("Republia").contains("access_permit"));

        bulletin = "Foreigners, Workers no longer require access permit";
        inspector.checkAndUpdateRequirements(bulletin);
        Assert.assertFalse(inspector.requiredDocuments.get("Republia").contains("access_permit"));

    }

    @Test
    public void testEntrantsBulletin(){
        Inspector inspector = new Inspector();
        String bulletin = "Entrants require passport vaccination";
        inspector.checkAndUpdateRequirements(bulletin);
        Assert.assertTrue(inspector.requiredVaccination.get("Republia").contains("passport"));

        bulletin = "Foreigners no longer require passport vaccination";
        inspector.checkAndUpdateRequirements(bulletin);
        Assert.assertFalse(inspector.requiredVaccination.get("Republia").contains("passport"));
        Assert.assertTrue(inspector.requiredVaccination.get("Arstotzka").contains("passport"));

    }

    @Test
    public void validDateTest(){
        Inspector inspector = new Inspector();
        String date = "1982.11.25";
        Assert.assertTrue(inspector.isExpireDateValid(date));
    }

    @Test
    public void invalidDateTest(){
        Inspector inspector = new Inspector();
        String date = "1982.11.19";
        Assert.assertFalse(inspector.isExpireDateValid(date));
    }

    @Test
    public void criminalTest(){
        Inspector inspector = new Inspector();
        String bulletin = "Wanted by the State: Hubert Popovic";
        inspector.receiveBulletin(bulletin);

        String name = "Guyovich, Russian";
        Assert.assertFalse(inspector.isWantedCriminal(name));
        name = "Popovic, Hubert";
        Assert.assertTrue(inspector.isWantedCriminal(name));
    }

    @Test
    public void preliminaryTraining() {
        Inspector inspector = new Inspector();
        inspector.receiveBulletin("Entrants require passport\nAllow citizens of Arstotzka, Obristan");

        Map<String, String> josef = new HashMap<>();
        josef.put("passport", "ID#: GC07D-FU8AR\nNATION: Arstotzka\nNAME: Costanza, Josef\nDOB: 1933.11.28\nSEX: M\nISS: East Grestin\nEXP: 1983.03.15");

        Map<String, String> guyovich = new HashMap<>();
        guyovich.put("access_permit", "NAME: Guyovich, Russian\nNATION: Obristan\nID#: TE8M1-V3N7R\nPURPOSE: TRANSIT\nDURATION: 14 DAYS\nHEIGHT: 159cm\nWEIGHT: 60kg\nEXP: 1983.07.13");

        Map<String, String> roman = new HashMap<>();
        roman.put("passport", "ID#: WK9XA-LKM0Q\nNATION: United Federation\nNAME: Dolanski, Roman\nDOB: 1933.01.01\nSEX: M\nISS: Shingleton\nEXP: 1983.05.12");
        roman.put("grant_of_asylum", "NAME: Dolanski, Roman\nNATION: United Federation\nID#: Y3MNC-TPWQ2\nDOB: 1933.01.01\nHEIGHT: 176cm\nWEIGHT: 71kg\nEXP: 1983.09.20");

        Assert.assertEquals("Glory to Arstotzka.", inspector.inspect(josef));
        Assert.assertEquals("Entry denied: missing required passport.", inspector.inspect(guyovich));
        Assert.assertEquals("Detainment: ID number mismatch.", inspector.inspect(roman));
    }

}
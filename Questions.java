import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Questions {

    String rootURL;
    String url;
    Document currentDoc;

    /**
     *  Constructor
     *
     * @param url
     */
    public Questions(String rootURL, String url) {
        this.rootURL = rootURL;
        this.url = url;

        // connect to the given link and get the document as a global var
        try {
            this.currentDoc = Jsoup.connect(this.url).get();
            // System.out.println(this.currentDoc);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * A method that answers question ONE given the two specified colors.
     * @param color1
     * @param color2
     * @return
     */
    public ArrayList<String> questionOne(String color1, String color2) {

        ArrayList<ArrayList<String>> temp = getAllCountryLinks();
        ArrayList<String> countryLinks = temp.get(1);
        ArrayList<String> countryNames = temp.get(0);

        /*
         * Iterate through all countries to check their flag color
         *
         */
        ArrayList<String> countriesWithColors = new ArrayList<>();
        for (int i = 0; i < countryLinks.size(); i++) {
            Document countryDoc;
            try {
                TimeUnit.SECONDS.sleep(1);
                countryDoc = Jsoup.connect(this.rootURL + countryLinks.get(i)).get();

                // Get exactly the flag description
                Elements countryDIV = countryDoc.select("h3:contains(Flag description) + p");

                for (Element e : countryDIV) {
                    // check if the description contains the word entered for color 1
                    Pattern r = Pattern.compile(".*?(" + color1 + ")");
                    Matcher m = r.matcher(e.toString());

                    if (m.find()) {
                        System.out.println(countryNames.get(i));
                        System.out.println(m.group(1));

                        // if color 1 was found then
                        // check if the description contains the word entered for color 2
                        r = Pattern.compile(".*?(" + color2 + ")");
                        m = r.matcher(e.toString());
                        if (m.find()) {
                            System.out.println(m.group(1));

                            // If both colors exist then add to list
                            countriesWithColors.add(countryNames.get(i));
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return countriesWithColors;

    }

    /**
     * A method that answers question TWO given an ocean
     * @param ocean
     * @return
     */
    public String questionTwo(String ocean) {
        // get all the relevant hrefs
        Elements oceanDIV = this.currentDoc.select("a:contains(" + ocean + " Ocean)");

        // Get just the link part from the doc using regex
        Pattern r = Pattern.compile("href=\"(.*)\">");
        Matcher m = r.matcher(oceanDIV.toString());
        String link = "";

        // Get link if a real ocean, o.w. throw exception
        if (m.find()) {
            link = m.group(1);
        } else {
            throw new IllegalArgumentException("Please enter the name of a real ocean!");
        }

        // Connect to the ocean link and find lowest point
        try {

            // get div with ocean's lowest point
            Document oceanDoc = Jsoup.connect(this.rootURL + link).get();
            Elements lowestPointDIV = oceanDoc.select("p:contains(lowest point)");

            // use regex to get number
             r = Pattern.compile("lowest point.*?((?:[0-9]|,)+)");
             m = r.matcher(lowestPointDIV.toString());
             if (m.find()) {

                 // return the found lowest point
                 return m.group(1);
             }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";

    }

    /**
     * A method that answers question THREE given a continent
     * @param continent
     * @return
     */
    public String questionThree(String continent) {
        ArrayList<ArrayList<String>> temp = getCountryLinksInContinent(continent);
        ArrayList<String> countryLinks = temp.get(1);
        ArrayList<String> countryNames = temp.get(0);

        /*
         * Iterate through all countries to get the largest one in terms of electricity production
         *
         */
        String largestCountry = "";
        int elecProduction = 300;
        for (int i = 0; i < countryLinks.size(); i++) {
            Document countryDoc;
            try {
                TimeUnit.SECONDS.sleep(1);
                countryDoc = Jsoup.connect(this.rootURL + countryLinks.get(i)).get();

                // Get the tag of where we are supposed to be
                Elements countryDIV = countryDoc.select("a:contains(Electricity - production)");

                for (Element e : countryDIV) {

                    // find the important information relative to the given tag
                    e = e.parent().nextElementSibling().nextElementSibling();

                    // Get the numbered position
                    Pattern r = Pattern.compile("country comparison to the world.*?((?:[0-9]|,)+)");
                    Matcher m = r.matcher(e.toString());

                    if (m.find()) {
                        if (Integer.parseInt(m.group(1)) < elecProduction) {
                            elecProduction = Integer.parseInt(m.group(1));
                            largestCountry = countryNames.get(i);
                            System.out.println(elecProduction);
                            System.out.println(largestCountry);
                        }

                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Return the largest country in terms of electricity production
        return largestCountry;
    }

    /**
     * A method that answers question FOUR given a continent
     * @param continent
     * @return
     */
    public String questionFour(String continent) {
        ArrayList<ArrayList<String>> temp = getCountryLinksInContinent(continent);
        ArrayList<String> countryLinks = temp.get(1);
        ArrayList<String> countryNames = temp.get(0);

        /*
         * Iterate through all countries to get the largest coastline to land area ratio
         *
         */
        String largestCountry = "";
        double largestRatio = -1;
        for (int i = 0; i < countryLinks.size(); i++) {
            Document countryDoc;
            try {
                TimeUnit.SECONDS.sleep(1);
                countryDoc = Jsoup.connect(this.rootURL + countryLinks.get(i)).get();

                // Get the coastline block
                Elements coastlineDIV = countryDoc.select("a:contains(Coastline)");

                for (Element e : coastlineDIV) {

                    // find the important information relative to the coastline block
                    e = e.parent().nextElementSibling();

                    // Get the value
                    Pattern r = Pattern.compile("((?:[0-9]|\\.|,)+)");
                    Matcher m = r.matcher(e.toString());

                    if (m.find()) {

                        // Get the tag of where we are supposed to be
                        Elements areaDIV = countryDoc.select("h3:matches(Area$)");

                        for (Element a : areaDIV) {

                            // Information in next sibling
                            a = a.nextElementSibling();

                            // Get the area
                            r = Pattern.compile("total.*?((?:[0-9]|\\.|,)+)");
                            Matcher m2 = r.matcher(a.toString());

                            if (m2.find()) {
                                String area = m2.group(1).replaceAll(",","");
                                String coastline = m.group(1).replaceAll(",","");
                                double newRatio = Double.parseDouble(coastline) / Double.parseDouble(area);
                                if (newRatio > largestRatio) {
                                    largestRatio = newRatio;
                                    largestCountry = countryNames.get(i);
                                }
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return largestCountry;
    }

    /**
     * A method that answers question FIVE given a continent
     * @param continent
     * @return
     */
    public int questionFive(String continent) {
        ArrayList<ArrayList<String>> temp = getCountryLinksInContinent(continent);
        ArrayList<String> countryLinks = temp.get(1);
        ArrayList<String> countryNames = temp.get(0);

        String highestCountry = "";
        int meanHeight = 0;
        int population = 0;
        for (int i = 0; i < countryLinks.size(); i++) {
            Document countryDoc;
            try {
                TimeUnit.SECONDS.sleep(1);
                countryDoc = Jsoup.connect(this.rootURL + countryLinks.get(i)).get();

                // Get the elevation block
                Elements elevationDIV = countryDoc.select("a:matchesOwn(Elevation)");

                for (Element e : elevationDIV) {

                    // find the important information relative to the coastline block
                    e = e.parent().nextElementSibling();

                    // Get the value
                    Pattern r = Pattern.compile("mean elevation.*?((?:[0-9]|\\.|,)+)");
                    Matcher m = r.matcher(e.toString());

                    if (m.find()) {

                        int newMeanHeight = Integer.parseInt(m.group(1).replaceAll(",",""));
                        if (newMeanHeight > meanHeight) {

                            meanHeight = newMeanHeight;
                            highestCountry = countryNames.get(i);

                            // Get the population
                            Elements areaDIV = countryDoc.select("h3:matches(Population$)");

                            for (Element a : areaDIV) {

                                // Information in next sibling
                                a = a.nextElementSibling();

                                // Get the population
                                r = Pattern.compile(".*?((?:[0-9]|\\.|,)+)");
                                m = r.matcher(a.toString());

                                if (m.find()) {
                                    population = Integer.parseInt(m.group(1).replaceAll(",", ""));
                                }
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(highestCountry);
        return population;
    }

    /**
     * A method that answers question SIX given a region
     * @param region
     * @return
     */
    public String questionSix(String region) {

        ArrayList<ArrayList<String>> temp = getAllCountryLinks();
        ArrayList<String> countryLinks = temp.get(1);
        ArrayList<String> countryNames = temp.get(0);

        /*
         * Iterate through all countries to check their flag color
         *
         */
        ArrayList<String> regionCountries = new ArrayList<>();
        ArrayList<Integer> countryAreas = new ArrayList<>();
        ArrayList<String> importPartners = new ArrayList<>();
        for (int i = 0; i < countryLinks.size(); i++) {
            Document countryDoc;
            try {
                TimeUnit.MILLISECONDS.sleep(100);
                countryDoc = Jsoup.connect(this.rootURL + countryLinks.get(i)).get();

                // Get the location block
                Elements elevationDIV = countryDoc.select("a:matchesOwn(Location$)");

                for (Element e : elevationDIV) {

                    // find the important information relative
                    e = e.parent().nextElementSibling();

                    // check if it's in the region we're looking for
                    Pattern r = Pattern.compile("^<p>((?:[a-zA-Z]|\\s)+)");
                    Matcher m = r.matcher(e.toString());

                    if (m.find()) {

                        // If it is in the right region
                        if(m.group(1).equals(region)) {
                            regionCountries.add(countryNames.get(i));

                            // Get total area
                            // Get the tag of where we are supposed to be
                            Elements areaDIV = countryDoc.select("h3:matches(Area$)");

                            for (Element a : areaDIV) {

                                // Information in next sibling
                                a = a.nextElementSibling();

                                // Get the area
                                r = Pattern.compile("total.*?((?:[0-9]|\\.|,)+)");
                                m = r.matcher(a.toString());

                                if (m.find()) {
                                    String area = m.group(1).replaceAll(",","");
                                    countryAreas.add(Integer.parseInt(area));
                                }
                            }

                            // Get import partners
                            // Get the tag of where we are supposed to be
                            Elements importsDIV = countryDoc.select("h3:matches(Imports - partners$)");

                            for (Element a : importsDIV) {

                                // Information in next sibling
                                a = a.nextElementSibling();
                                importPartners.add(a.text());
                            }

                            // If this isn't listed then just add empty string
                            if (importsDIV.isEmpty()) {
                                importPartners.add("");
                            }
                        }

                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < regionCountries.size(); i++) {
            System.out.println(regionCountries.get(i));
            System.out.println(countryAreas.get(i));
            System.out.println(importPartners.get(i));
        }

        nameAndNumberLists tempCombo = new nameAndNumberLists(importPartners, countryAreas);
        tempCombo.sort();

        for (int i = 0; i < regionCountries.size(); i++) {
            System.out.println(regionCountries.get(i));
            System.out.println(tempCombo.getNumbers().get(i));
            System.out.println(tempCombo.getNames().get(i));
        }

        return tempCombo.getNames().get(tempCombo.getNames().size() - 3);
    }

    /**
     * A method that answers question SEVEN given a letter
     * @param letter
     * @return
     */
    public ArrayList<String> questionSeven(String letter) {
        ArrayList<ArrayList<String>> temp = getAllCountryLinks();
        ArrayList<String> countryLinks = temp.get(1);
        ArrayList<String> countryNames = temp.get(0);

        // remove all countries that do NOT start with the specified letter
        for (int i = countryLinks.size() - 1; i >= 0 ; i--) {
            if (!countryNames.get(i).startsWith(letter)) {
                countryNames.remove(i);
                countryLinks.remove(i);
            }
        }

        // go through all remaining countries and get their area
        ArrayList<Integer> areas = new ArrayList<>();
        for (int i = 0; i < countryLinks.size(); i++) {
            Document countryDoc;
            try {
                TimeUnit.SECONDS.sleep(1);
                countryDoc = Jsoup.connect(this.rootURL + countryLinks.get(i)).get();

                // Get the tag of where we are supposed to be
                Elements areaDIV = countryDoc.select("h3:matches(Area$)");

                for (Element a : areaDIV) {

                    // Information in next sibling
                    a = a.nextElementSibling();

                    // Get the area
                    Pattern r = Pattern.compile("total.*?((?:[0-9]|\\.|,)+)");
                    Matcher m = r.matcher(a.toString());

                    if (m.find()) {
                        String area = m.group(1).replaceAll(",","");
                        areas.add(Integer.parseInt(area));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        nameAndNumberLists tempCombo = new nameAndNumberLists(countryNames, areas);
        tempCombo.sort();

        return tempCombo.getNames();
    }


    /**
     * A method that answers question Eight given a letter
     * @param continent
     * @param color
     * @return
     */
    public String questionEight(String continent, String color) {
        ArrayList<ArrayList<String>> temp = getCountryLinksInContinent(continent);
        ArrayList<String> countryLinks = temp.get(1);
        ArrayList<String> countryNames = temp.get(0);

        /*
         * Iterate through all countries to check their flag color
         *
         */
        ArrayList<String> countriesWithColor = new ArrayList<>();
        ArrayList<String> countryLinksWithColor = new ArrayList<>();
        for (int i = 0; i < countryLinks.size(); i++) {
            Document countryDoc;
            try {
                TimeUnit.SECONDS.sleep(1);
                countryDoc = Jsoup.connect(this.rootURL + countryLinks.get(i)).get();

                // Get exactly the flag description
                Elements countryDIV = countryDoc.select("h3:contains(Flag description) + p");

                for (Element e : countryDIV) {
                    // check if the description contains the word entered for color 1
                    Pattern r = Pattern.compile(".*?(" + color + ")");
                    Matcher m = r.matcher(e.toString());

                    if (m.find()) {
                        countriesWithColor.add(countryNames.get(i));
                        countryLinksWithColor.add(countryLinks.get((i)));
                    }
                }
             } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return "";
    }






    /**
     ******************************************************************************************************************
     * Helper Functions below here:
     */






    /**
     *
     * Returns TWO array lists (first with names, second with links) in an arraylist of length 2
     *
     * @param continent
     * @return
     */
    private ArrayList<ArrayList<String>> getCountryLinksInContinent(String continent) {
        // get the relevant href
        Elements givenContinentDIV = this.currentDoc.select("a:contains(" + continent + ")");

        // Get just the link part from the doc using regex
        Pattern r = Pattern.compile("href=\"(.*)\">");
        Matcher m = r.matcher(givenContinentDIV.toString());
        String link = "";

        // Get link if a real continent, o.w. throw exception
        if (m.find()) {
            link = m.group(1);
        } else {
            throw new IllegalArgumentException("Please enter the name of a real continent!");
        }

        // Connect to the continent link and get all country links
        ArrayList<String> countryLinks = new ArrayList<>();
        ArrayList<String> countryNames = new ArrayList<>();
        try {

            // get div with ocean's lowest point
            Document continentDoc = Jsoup.connect(this.rootURL + link).get();
            Elements continentDIV = continentDoc.select("a.link-button");
            for (Element e : continentDIV) {
                r = Pattern.compile("href=\"(.*)\">");
                m = r.matcher(e.toString());

                // Add the link part to a list and the name to another
                if (m.find()) {
                    countryNames.add(e.text());
                    countryLinks.add(m.group(1));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Return an arraylist of this stuff
        ArrayList<ArrayList<String>> temp = new ArrayList();
        temp.add(countryNames);
        temp.add(countryLinks);
        return temp;
    }

    /**
     *
     * Returns TWO array lists (first with names, second with links) in an arraylist of length 2
     * of ALL countries.
     *
     * @return
     */
    private ArrayList<ArrayList<String>> getAllCountryLinks() {
        // get all the relevant hrefs
        Elements mainDIV = this.currentDoc.select("div.content-card-teaser");
        Elements hrefs = mainDIV.select("a[href^=/the-world-factbook/]");

        /*
         * Get all continent links from the home page
         *
         */
        ArrayList<String> continentLinks = new ArrayList<>();
        for (Element e : hrefs) {

            // exclude the whole world and oceans
            if (!e.text().contains("World") && !e.text().endsWith("Ocean")) {

                Pattern r = Pattern.compile("<a href=\"(.*)\">");
                Matcher m = r.matcher(e.toString());

                // Add just the link part to a list
                if (m.find()) {
                    continentLinks.add(m.group(1));
                }
            }
        }

        // get all the links and names of each country (in the same order)
        ArrayList<String> countryLinks = new ArrayList<>();
        ArrayList<String> countryNames = new ArrayList<>();

        /*
         * Iterate through all continents to get all country links
         *
         */
        for (String country : continentLinks) {
            Document continentDoc;
            try {
                continentDoc = Jsoup.connect(this.rootURL + country).get();
                Elements countryDIV = continentDoc.select("a.link-button");
                for (Element e : countryDIV) {
                    Pattern r = Pattern.compile("href=\"(.*)\">");
                    Matcher m = r.matcher(e.toString());

                    // Add the link part to a list and the name to another
                    if (m.find()) {
                        countryNames.add(e.text());
                        countryLinks.add(m.group(1));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();            }
        }

        ArrayList<ArrayList<String>> temp = new ArrayList<>();
        temp.add(countryNames);
        temp.add(countryLinks);
        return temp;
    }

}

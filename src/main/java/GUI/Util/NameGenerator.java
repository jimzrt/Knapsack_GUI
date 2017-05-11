package GUI.Util;

import java.util.Random;

/**
 * Created by james on 11.05.2017.
 */
public class NameGenerator {
    public static Random random = new Random();
    private static String[] itemNames = new String[]{"Name", "1/35-Soldat", "203mm Granate", "A-Coupon", "Abführmittel", "Abwehrkapsel", "Antarktischer Wind", "Apothekengutschein", "Äther", "Augentropfen", "Autogramm", "B-Coupon", "Basilisken-Klaue", "Batterie", "Baumwollkleid", "Beruhigungsmittel", "Betäubungspfeil", "Bikinislip", "Blitzrauch", "Blonde Perücke", "Brief an die Ehefrau", "Brief an die Tochter", "C-Coupon", "Chaos", "Code-Karte 60", "Code-Karte 62", "Code-Karte 65", "Code-Karte 66", "Code-Karte 68", "Curiel-Gemüse", "Deodorant", "Desinfektionsmittel", "Diamantendiadem", "Drachenschuppen", "Drachenzahn", "Eau de Cologne", "Echobombe", "Eiskristall", "Elixier", "Engels-Chor", "Erdhammer", "Erdharfe", "Erdtrommel", "Feuer-Cocktail", "Feuerzahn", "Froschschenkel", "Gefärbte Perücke", "Gegenmittel", "Geheimnisvolles Höschen", "Geisterhand", "Gemütskapsel", "Gewitterblitz", "Giftmüll", "Glasdiadem", "Gletscherkarte", "Glückskapsel", "Gold Ticket", "Goldene Nadel", "Goldene Sanduhr", "Granate", "Graviball", "Grosse Materia(1)", "Grosse Materia(2)", "Grosse Materia(3)", "Grosse Materia(4)", "Gysahl-Gemüse", "Heilige Fackel", "Heilmittel", "Heldentrank", "Hexenkessel", "Hi-Trank", "Highwind", "Himmelsfaust", "Hyper", "Impfstoff", "Jungfernkuss", "Karob-Nuss", "Katastrophe", "Kellerschlüssel", "Kosmo-Glut", "Kraftkapsel", "Krakka-Gemüse", "Kriegsgong", "Lasan-Nuss", "Leviathanschuppen", "Loco-Kraut", "Luchil-Nuss", "M-Tentakeln", "Masamune", "Megalixier", "Midgar-Teile", "Mimmet-Gemüse", "Mitgliedskarte", "Molotow", "Mondharfe", "Mondschleier", "Mythril", "Pahsana-Gemüse", "Papiertuch", "Pepio-Nuss", "Perücke", "Phönix-Feder", "PHS", "Porof-Nuss", "Pram-Nuss", "Rauchbombe", "Raum/Zeit-Bombe", "Reagan-Gemüse", "Rechter Arm", "Reiseführer", "Rosenparfum", "Rubindiadem", "S-Mine", "Saraha-Nuss", "Satinkleid", "Schlüssel für Sektor 5", "Schlüssel zum alten Volk", "Schlüsselstein", "Schrapnel", "Schrumpfer", "Schrumpfhorn", "Schwarze Materia", "Schweigemaske", "Schwertwirbel", "Seidenkleid", "Sexy Parfum", "Snowboard", "Sonnenschleier", "Speed-Kapsel", "Speicherkristall", "Spiegel", "Spinnweben", "Sternenstaub", "Supermetall Eraser", "Sylkis-Gemüse", "Tagebuch", "Tagebuch 2", "Tantle-Gemüse", "Teil von Midgar", "Tinte", "Todeskuss", "Trank", "Traumpuder", "Turbo-Äther", "Turbo-Trank", "Unterwäsche", "Vampirzahn", "Vogelflügel", "Wüstenrose", "X-Trank", "Zauberkapsel", "Zelt", "Zeyo-Nuss", "Zorn der Götter", "Item", "Potion", "Hi-Potion", "X-Potion", "Mega-Potion", "Äther", "Turbo-Äther", "Elixir", "Final-Elixir", "Phönixfeder", "Mega-Phönix", "Antidot", "Goldnadel", "Augentropfen", "Echokraut", "Weihwasser", "Allheilmittel", "Kraftzettel", "Wunderzettel", "Zeitzettel", "Ability-Zettel", "Al Bhed-Medizin", "Heilwasser", "Tetra-Element", "Bomber-Splitter", "Bomber-Seele", "Hitzestein", "Elektrokugel", "Blitzkugel", "Donnerstein", "Fischschuppe", "Drachenschuppe", "Nässestein", "Antarktis-Wind", "Polar-Wind", "Kältestein", "Handgranate", "Panzergranate", "Schlafpuder", "Traumpuder", "Silentium-Mine", "Rauchbombe", "Schwarzer Stein", "Lichtstein", "Heiliger Stein", "Himmelsstein", "Giftkralle", "Silberne Sanduhr", "Goldene Sanduhr", "Todeskerze", "Perseus-Granate", "Todesschatten", "Wind des Jenseits", "Dunkelkristall", "Chocobo-Feder", "Chocobo-Daune", "Mondschleier", "Lichtschleier", "Sternenschleier", "Heilquelle", "Magiequelle", "Kraftquelle", "Lebensquelle", "Ablutionssalz", "HP-Pille", "MP-Pille", "HP-Wundermittel", "MP-Wundermittel", "Zweigestirn", "Dreigestirn"};

    public static String getRandomName() {
        return itemNames[random.nextInt(itemNames.length)];
    }

}
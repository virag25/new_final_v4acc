package amigoinn.walkietalkie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maulik on 1/27/2016.
 */
public class Constants
{
    public static ArrayList<String> searchlist=new ArrayList<String>();
    public static String[] arrTemp2;
    public static  String selectedclient="";
    public static List<String> Zonelist=new ArrayList<String>();
    public static List<String> PartyList=new ArrayList<String>();
    public static List<String> countries=new ArrayList<String>();
    public static List<String> Statelist=new ArrayList<String>();
    public static List<String> CityList=new ArrayList<String>();

    public static List<String> ItemgroupList=new ArrayList<String>();
    public static List<String> Reportinggroupcodelist=new ArrayList<String>();
    public static List<String> Productlist=new ArrayList<String>();
    public static List<String> BrandList=new ArrayList<String>();
    public static List<String> sizelist=new ArrayList<String>();
    public static List<String> modellist=new ArrayList<String>();

    public static void initString2(int size)
    {
        arrTemp2=new String[size];
        for(int i=0;i<size;i++)
        {
            arrTemp2[i]="a";
        }
    }
    public static void addItemGroups()
    {
        ItemgroupList.add("Accessories");
        ItemgroupList.add("A/C");
        ItemgroupList.add("Accessories");
        ItemgroupList.add("Audio");


        ItemgroupList.add("Bicycle");
        ItemgroupList.add("Fitness Equipments");
        ItemgroupList.add("Moto Accessories");
        ItemgroupList.add("Two Wheeler");
        ItemgroupList.add("Home Decor/Houselhold");
        ItemgroupList.add("Wellness Products");
        ItemgroupList.add("White Goods");
        ItemgroupList.add("Auto Care");
        ItemgroupList.add("Dog Food");
        ItemgroupList.add("Food & Beverage");
        ItemgroupList.add("Fabric Care");
        ItemgroupList.add("Hair Care");
        ItemgroupList.add("ItemgroupList.add(\"Home Decor/Houselhold\");");




    }
    public static void addSizes()
    {
        sizelist.add("150 cc");
        sizelist.add("50 cc");
        sizelist.add("Az 39");
        sizelist.add("At 338");
        sizelist.add("Hr-1456");
        sizelist.add("We-3006");
        sizelist.add("We-3106");
        sizelist.add("200 cc");
        sizelist.add("25Ml");
        sizelist.add("330 Ml");
        sizelist.add("270Ml");
        sizelist.add("Ni-E200Tatv");
        sizelist.add("50Ml");
        sizelist.add("115Grm");
        sizelist.add("R-Sw-60");
        sizelist.add("R-Ohy-1705");
        sizelist.add("Ovm-8069");
        sizelist.add("650Grm.");
        sizelist.add("Hts3540-98");
        sizelist.add("42x20m");
        sizelist.add("1000Grm");
        sizelist.add("Mk-G1350");
        sizelist.add("Pc-18Kkf");
        sizelist.add("39le440m");
        sizelist.add("Nn-St342");
        sizelist.add("12000 btu");
        sizelist.add("5 Grm");
        sizelist.add("400Ml");
        sizelist.add("60 Pages");
        sizelist.add("72Und");
        sizelist.add("Qt27q07");
        sizelist.add("Hme5-05");
        sizelist.add("Rp-Hje120e-K");
        sizelist.add("Gm-1003");
        sizelist.add("12 k split");
        sizelist.add("Sc-Vkx20gc");
        sizelist.add("Sc-Xh185Gs-K");
        sizelist.add("Hts-9800");
        sizelist.add("650Grm.");
        sizelist.add("Eh-Hw17-P615");
        sizelist.add("Okg-103a");
        sizelist.add("Hr-2027-70");


    }
    public static void addModels()
    {
        modellist.add("Ab Core");
        modellist.add("Active");
        modellist.add("Africa");
        modellist.add("Agrafos");
        modellist.add("Baby");
        modellist.add("Big Gummy Cola");
        modellist.add("Black Cherry");
        modellist.add("Canal Ear Phone");
        modellist.add("Caneca Tq07");
        modellist.add("Ch000265");
        modellist.add("Dark");
        modellist.add("Deep Enrgy");
        modellist.add("Delicious");
        modellist.add("Diable Blue");
        modellist.add("Earphone");
        modellist.add("Electronic Iron");
        modellist.add("Elegance");
        modellist.add("FAn Bike");
        modellist.add("Fanta Laranja");
        modellist.add("Fogao 85s");
        modellist.add("Gel Olive");
        modellist.add("Get Ready");
        modellist.add("GL 249");
        modellist.add("Hair Timmer");
        modellist.add("Harmony");
        modellist.add("Home Gym");
        modellist.add("Ice Gum Cherrymi");
        modellist.add("Indomie Chicken");
        modellist.add("Instant Kill Odouless");
        modellist.add("Jock ");
        modellist.add("Joia Azul");
        modellist.add("Juicer");
        modellist.add("Kids 5yr");
        modellist.add("Kinder Bueno");
        modellist.add("Kscope Vanilla");
        modellist.add("Lace");
        modellist.add("Lcd Stand");
        modellist.add("Legal");
        modellist.add("Mango");
        modellist.add("Marco");
        modellist.add("Masc");
        modellist.add("Neture");
        modellist.add("Normal");
        modellist.add("NSFT Jar");
        modellist.add("OKR-255BF");
        modellist.add("Olive");
        modellist.add("Original");
        modellist.add("Panache");
        modellist.add("Paradise");
        modellist.add("Peace");
        modellist.add("Quantum");
        modellist.add("Rain Fresh");
        modellist.add("Rally Cherry");
        modellist.add("Rally Vanilla");
        modellist.add("S18LS");
        modellist.add("Sandwich Maker");
        modellist.add("Scented");
        modellist.add("Tango");
        modellist.add("The Pride");
        modellist.add("Toaster");
        modellist.add("Ultimate 3 Blue Disp 4+4 Men");
        modellist.add("Ultimate 3 Disp Razor");
        modellist.add("Vanilla");
        modellist.add("Velvet Touch");
        modellist.add("Vidros Salada De Frutas Wm-15");
        modellist.add("Wannabl Display");
        modellist.add("Washer Drive 6 K");
        modellist.add("White Satin Sheer");
        modellist.add("XL-2");
        modellist.add("XL-3");
        modellist.add("XL-4");
        modellist.add("Yog&Almond");
        modellist.add("Zig Zag");
        modellist.add("Zottis Morango");

    }
    public static void addBrands()
    {
        BrandList.add("adidas");
        BrandList.add("Ajax");
        BrandList.add("Alpenliebe");
        BrandList.add("Arrow");
        BrandList.add("Axe");
        BrandList.add("Axion");
        BrandList.add("Bakers");
        BrandList.add("Baygon");
        BrandList.add("Bic");
        BrandList.add("Brut");
        BrandList.add("Caracole");
        BrandList.add("Chiko");
        BrandList.add("Coca Cola");
        BrandList.add("Colgate");
        BrandList.add("Commuter");BrandList.add("Creation Lamis");
        BrandList.add("Cuddlers");BrandList.add("Dawn");
        BrandList.add("Dorel Collection");BrandList.add("Dove");
        BrandList.add("Dt-Mx");BrandList.add("Dujour");
        BrandList.add("Dunar");BrandList.add("Ellise");
        BrandList.add("Escorpiao");BrandList.add("Fanta");










    }
    public static void addProducts()
    {
        Productlist.add("Accessories");
        Productlist.add("Adult Bicycles");
        Productlist.add("Air Care");
        Productlist.add("Cookware");
        Productlist.add("Cofectionaries");
        Productlist.add("Conditioners");
        Productlist.add("Chocolates");
        Productlist.add("Candys");
        Productlist.add("Camera");
        Productlist.add("Dog Food");
        Productlist.add("Diverse");
        Productlist.add("Disposable Razors");
        Productlist.add("Double Edge Blades");
        Productlist.add("Data Traveler");
        Productlist.add("Decoration");
        Productlist.add("Deodarant Roll Ons");
        Productlist.add("Deodarant Sprays");
        Productlist.add("Detergente Pest");
        Productlist.add("Detergente Powder");
        Productlist.add("Establizador");
        Productlist.add("Fabric Cleaning");
        Productlist.add("Fabric Softners");
        Productlist.add("Fan");Productlist.add("Fishing Material");
        Productlist.add("Fitness Equipments");
        Productlist.add("Fridge");
        Productlist.add("Hair Creams");
        Productlist.add("Hair Cutting Machine");
        Productlist.add("Hair Die");
        Productlist.add("Hair Dresser");
        Productlist.add("Hair Dryer");Productlist.add("Hand And Body Creams");
        Productlist.add("Hand And Body Lotions");



    }
    public static void addReportingCode()
    {
        Reportinggroupcodelist.add("Aldor");
        Reportinggroupcodelist.add("Automobile");
        Reportinggroupcodelist.add("Bakers");
        Reportinggroupcodelist.add("Bicycle");
        Reportinggroupcodelist.add("Coca Cola Business");
        Reportinggroupcodelist.add("Colgate Palmolive");
        Reportinggroupcodelist.add("Decorating");
        Reportinggroupcodelist.add("Electronics");
        Reportinggroupcodelist.add("Ellies");
        Reportinggroupcodelist.add("Fogg Fragrance");

    }
    public static void addzones()
    {

        Zonelist.add("A.BRAZIL-COMBATENTE");
        Zonelist.add("ALVALADE MAINGA");
        Zonelist.add("B.C.A, ASA BRANCA, TERA VERMELHA");
        Zonelist.add("BAIRRO POPULAR , AVOQUMBI");
        Zonelist.add("BENFICA , XINGUARI");
        Zonelist.add("BENFICA-PATRIOTA");
        Zonelist.add("CALEMBA -2");
        Zonelist.add("CALEMBA ROUTE -1");
        Zonelist.add("CALEMBA ROUTE-2");
        Zonelist.add("CALEMBA ROUTE-3");
        Zonelist.add("CASSENDA MARTISH");
        Zonelist.add("CONGOLENCE");
        Zonelist.add("ESTELAGEM-1");
        Zonelist.add("KUITO");
        Zonelist.add("SOUTH");
        Zonelist.add("NORTH");


    }
public  static void addParty()
{
    PartyList.add("Client Deverso");
    PartyList.add("Shoprite Benguela");
    PartyList.add("SPAR CABINDA");
    PartyList.add("JFC Comercial Sumbe");
    PartyList.add("Caza Azul Lubango");
    PartyList.add("Martins & Peres Lda (MAPEL) Lubango");
    PartyList.add("Shoprite Kuito ( 83052 )");
    PartyList.add("Marav Supermercado Avenida");
    PartyList.add("Martal Cardoso");
    PartyList.add("Angolatino Catinto");
    PartyList.add("Ibrahim Accounting service");
    PartyList.add("Tielca Comercial-1");
    PartyList.add("Tomen Comercial");
    PartyList.add("Amilear Comercial-2");
    PartyList.add("Dona Domingas");
    PartyList.add("Organizacoes Ribral Limitada");
    PartyList.add("Globle Express Comercial-1");
    PartyList.add("Jose E Filhos Comercial");
    PartyList.add("Angolatino Avokumbi");
    PartyList.add("A.F.J Coemrcial");
    PartyList.add("Landois Comercial De Jose Da Costa Landois");
    PartyList.add("Lives Comercial De Antonio Pascoal Pereira Da Silva");
    PartyList.add("Organizacoes Elie Coemrcial");
    PartyList.add("Sarona Comercial De Amaha Wel De Zionasgo");
    PartyList.add("Solote De General Trading Comercio Geral");PartyList.add("Jeje Angola");PartyList.add("Malumaifa Comercial");
    PartyList.add("Tuxisila Comercial..");
    PartyList.add("X K Coemrcial");
    PartyList.add("Guta Comercial");
    PartyList.add("Tata E Filhos Comercial");
    PartyList.add("Angolatino Calemba II-1");
    PartyList.add("Angolatino Calemba II-2");
    PartyList.add("Saroni Comercial");
}
    public static void  addStates()
    {
        Statelist.add("BENGUELA");
        Statelist.add("CABINDA");
        Statelist.add("CUNZA SUL");
        Statelist.add("HUILA");
        Statelist.add("KUITO");
        Statelist.add("LUANDA");
        Statelist.add("SOYO");
        Statelist.add("CUNZA SUL");
        Statelist.add("HUILA");
        Statelist.add("HUMBO");
        Statelist.add("CUNZA SUL");
    }
    public static void addCity()
    {
        CityList.add("ASA BRANCA");
        CityList.add("ALVALADE");
        CityList.add("AVOQUMBI");
        CityList.add("Benguela City");
        CityList.add("COMBATENTE");
        CityList.add("CONGOLENCE");
        CityList.add("Cabinda City");
        CityList.add("DANJARE");
        CityList.add("SUMBE");
        CityList.add("Lubango");
        CityList.add("KUITO");



    }
}

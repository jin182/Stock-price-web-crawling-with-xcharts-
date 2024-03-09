import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.Styler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    // 주가 정보를 저장할 리스트
    private static List<Double> stockPrices = new ArrayList<>();
    //이 코드는 stockPrices라는 이름의 List 변수를 선언하고 초기화합니다. 이 변수는 Double 타입의 값을 저장합니다.

    // 해당 주식의 주가를 웹에서 가져와 출력하는 메서드
    private static void printStockPrice(String stockName, String url) throws IOException {
        // Jsoup을 사용하여 웹 페이지에서 정보를 가져옵니다.
        Document doc = Jsoup.connect(url).get();
        Elements elements = doc.getElementsByClass("YMlKec fxKbKc");
        String priceWithCurrency = elements.first().text();

        // 통화 기호 제거 후 숫자로 변환
        String priceWithoutCurrency = priceWithCurrency.replace("$", "").replace(",", "");
        double priceDouble = Double.parseDouble(priceWithoutCurrency);

        // 주가 정보를 리스트에 추가하고 출력
        stockPrices.add(priceDouble);
        System.out.println(stockName + " 주가: " + priceWithCurrency);
    }

    // 환율을 웹에서 가져와 출력하는 메서드
    private static void printExchangeRate(String url) throws IOException {
        // Jsoup을 사용하여 웹 페이지에서 정보를 가져옵니다.
        Document doc = Jsoup.connect(url).get();
        Elements elements = doc.getElementsByClass("YMlKec fxKbKc");
        String exchangeRate = elements.first().text();
        System.out.println("현재 1달러 당 환율: " + exchangeRate);
    }

    // XChart를 사용하여 주식 가격의 선 그래프를 생성하고 보여주는 메서드
    private static void createStockPriceChart() {
        // XChart를 사용하여 선 그래프를 생성합니다.
        XYChart chart = new XYChartBuilder() //XYChartBuilder 클래스의 인스턴스를 생성하여 선 그래프를 생성합니다.
                .width(800) //width() 메서드를 사용하여 그래프의 너비를 800으로 설정합니다.
                .height(600)//height() 메서드를 사용하여 그래프의 높이를 600으로 설정합니다.
                .title("Stock Price Chart") //title() 메서드를 사용하여 그래프의 제목을 "Stock Price Chart"로 설정합니다.
                .xAxisTitle("기업(1:AMD, 2:구글, 3:아마존, 4:애플, 5:테슬라, 6:마이크로소프트, 7:엔비디아)") //xAxisTitle() 메서드를 사용하여 x축의 제목을 "기업(1:AMD, 2:구글, 3:아마존, 4:애플, 5:테슬라, 6:마이크로소프트, 7:엔비디아)"로 설정합니다.
                .yAxisTitle("Stock Price")//yAxisTitle() 메서드를 사용하여 y축의 제목을 "Stock Price"로 설정합니다.
                .build(); //..build() 메서드는 XYChartBuilder 클래스의 메서드입니다. 이 메서드는 XYChart 객체를 생성하여 반환합니다.


        // 시리즈 생성
        chart.addSeries("Stock Price", null, stockPrices);
        //addSeries() 메서드를 사용하여 시리즈를 생성합니다.
        //시리즈의 이름은 "Stock Price"이고, 데이터는 stockPrices 리스트에 저장된 값들입니다.

        // 차트 스타일 설정
        //getStyler() 메서드를 사용하여 차트 스타일을 설정합니다.
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW); //setLegendPosition() 메서드를 사용하여 범례의 위치를 InsideNW로 설정합니다.
        chart.getStyler().setAxisTitlesVisible(true); //setAxisTitlesVisible() 메서드를 사용하여 축 제목을 표시하도록 설정합니다.
        chart.getStyler().setXAxisTickMarkSpacingHint(50); //setXAxisTickMarkSpacingHint() 메서드를 사용하여 x축의 눈금 간격을 50으로 설정합니다.

        // 차트를 화면에 보여줍니다.
        new SwingWrapper<>(chart).displayChart(); //SwingWrapper 클래스를 사용하여 차트를 화면에 보여줍니다.
    }

    public static void main(String[] args) throws IOException {
        // 주가 정보를 가져와서 출력
        printStockPrice("AMD", "https://www.google.com/finance/quote/AMD:NASDAQ");
        printStockPrice("구글", "https://www.google.com/finance/quote/GOOGL:NASDAQ");
        printStockPrice("아마존", "https://www.google.com/finance/quote/AMZN:NASDAQ?hl=ko");
        printStockPrice("애플", "https://www.google.com/finance/quote/AAPL:NASDAQ");
        printStockPrice("테슬라", "https://www.google.com/finance/quote/TSLA:NASDAQ");
        printStockPrice("마이크로소프트", "https://www.google.com/finance/quote/MSFT:NASDAQ");
        printStockPrice("엔비디아", "https://www.google.com/finance/quote/NVDA:NASDAQ");

        // 환율 정보를 가져와서 출력
        printExchangeRate("https://www.google.com/finance/quote/USD-KRW");

        // 주식 가격 그래프 생성 및 출력
        createStockPriceChart();

        // 사용자 입력 처리
        Scanner in = new Scanner(System.in);
        String enterprise;
        System.out.println("출력된 기업 중 하나를 선택하세요(뛰어쓰기 대소문자를 조심해주세요). 'stop'을 입력하면 종료됩니다.");
        do {
            enterprise = in.next();
            switch (enterprise.toLowerCase()) {
                case "amd":
                    System.out.println("+121.61 최대");
                    break;
                case "구글":
                    System.out.println("+129.81 최대");
                    break;
                case "애플":
                    System.out.println("+194.60 최대");
                    break;
                case "테슬라":
                    System.out.println("+235.73 최대");
                    break;
                case "마이크로소프트":
                    System.out.println("+374.28 최대");
                    break;
                case "엔비디아":
                    System.out.println("+475.75 최대");
                    break;
                case "아마존":
                    System.out.println("+147.39 최대");
                    break;
                case "stop":
                    System.out.println("프로그램을 종료합니다.");
                    System.exit(0);  // 프로그램 종료
                default:
                    System.out.println("선택한 기업은 목록에 없습니다.");
            }
        } while (true);
    }
}

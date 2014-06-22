public class TransactionFactory {

        Random rand = new Random()

        def curPrice = 100

        def tickers = [
                [ symbol : 'IBM', price : 100],
                [ symbol : 'AAPL', price : 200],
                [ symbol : 'MSFT', price : 300],
                [ symbol : 'INTC', price : 50],
                [ symbol : 'BWLD', price : 200]
        ]

        def getRandTicker() {
                
        }

        def getNewTrans() {
                def dt = new Date()
                def ticker = tickers[rand.nextInt(tickers.size())]
                def thekey = dt.format('yyyy/MM/dd HH:mm:ss:SS') + ' ' + ticker['symbol']

                return [ key: thekey, dt: dt.time.toString(), price:ticker['price'], symbol:ticker['symbol'] ]
        }

        
}
